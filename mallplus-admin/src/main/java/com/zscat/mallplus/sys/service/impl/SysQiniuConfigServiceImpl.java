package com.zscat.mallplus.sys.service.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import com.zscat.mallplus.exception.BusinessMallException;
import com.zscat.mallplus.sys.entity.SysQiniuConfig;
import com.zscat.mallplus.sys.entity.SysQiniuContent;
import com.zscat.mallplus.sys.mapper.SysQiniuConfigMapper;
import com.zscat.mallplus.sys.mapper.SysQiniuContentMapper;
import com.zscat.mallplus.sys.service.ISysQiniuConfigService;
import com.zscat.mallplus.util.FileUtil;
import com.zscat.mallplus.util.QiNiuUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-11-30
 */
@Service
public class SysQiniuConfigServiceImpl extends ServiceImpl<SysQiniuConfigMapper, SysQiniuConfig> implements ISysQiniuConfigService {

    @Value("${qiniu.max-size}")
    private Long maxSize;
    @Resource
    private SysQiniuContentMapper qiniuContentMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(SysQiniuConfig qiniuConfig) {
        if (!(qiniuConfig.getHost().toLowerCase().startsWith("http://") || qiniuConfig.getHost().toLowerCase().startsWith("https://"))) {
            throw new BusinessMallException("外链域名必须以http://或者https://开头");
        }
        qiniuConfig.setId(1L);
        return this.baseMapper.insert(qiniuConfig);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysQiniuContent upload(MultipartFile file, SysQiniuConfig qiniuConfig) {
        FileUtil.checkSize(maxSize, file.getSize());
        if (qiniuConfig.getId() == null) {
            throw new BusinessMallException("请先添加相应配置，再操作");
        }
        // 构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(QiNiuUtil.getRegion(qiniuConfig.getZone()));
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(qiniuConfig.getAccessKey(), qiniuConfig.getSecretKey());
        String upToken = auth.uploadToken(qiniuConfig.getBucket());
        try {
            String key = file.getOriginalFilename();
            if (qiniuContentMapper.selectOne(new QueryWrapper<SysQiniuContent>().eq("name", key)) != null) {
                key = QiNiuUtil.getKey(key);
            }
            Response response = uploadManager.put(file.getBytes(), key, upToken);
            //解析上传成功的结果

            DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
            //存入数据库
            SysQiniuContent qiniuContent = new SysQiniuContent();
            qiniuContent.setSuffix(FileUtil.getExtensionName(putRet.key));
            qiniuContent.setBucket(qiniuConfig.getBucket());
            qiniuContent.setType(qiniuConfig.getType());
            qiniuContent.setName(FileUtil.getFileNameNoEx(putRet.key));
            qiniuContent.setUrl(qiniuConfig.getHost() + "/" + putRet.key);
            qiniuContent.setSize(FileUtil.getSize(Integer.parseInt(file.getSize() + "")));
            qiniuContentMapper.insert(qiniuContent);
            return qiniuContent;
        } catch (Exception e) {
            throw new BusinessMallException(e.getMessage());
        }
    }

    @Override
    public SysQiniuContent findByContentId(Long id) {
        SysQiniuContent qiniuContent = qiniuContentMapper.selectById(id);
        return qiniuContent;
    }

    @Override
    public String download(SysQiniuContent content, SysQiniuConfig config) {
        String finalUrl;
        String TYPE = "公开";
        if (TYPE.equals(content.getType())) {
            finalUrl = content.getUrl();
        } else {
            Auth auth = Auth.create(config.getAccessKey(), config.getSecretKey());
            // 1小时，可以自定义链接过期时间
            long expireInSeconds = 3600;
            finalUrl = auth.privateDownloadUrl(content.getUrl(), expireInSeconds);
        }
        return finalUrl;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(SysQiniuContent content, SysQiniuConfig config) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(QiNiuUtil.getRegion(config.getZone()));
        Auth auth = Auth.create(config.getAccessKey(), config.getSecretKey());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(content.getBucket(), content.getName() + "." + content.getSuffix());
            qiniuContentMapper.deleteById(content.getId());
        } catch (QiniuException ex) {
            qiniuContentMapper.deleteById(content.getId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void synchronize(SysQiniuConfig config) {
        if (config.getId() == null) {
            throw new BusinessMallException("请先添加相应配置，再操作");
        }
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(QiNiuUtil.getRegion(config.getZone()));
        Auth auth = Auth.create(config.getAccessKey(), config.getSecretKey());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        //文件名前缀
        String prefix = "";
        //每次迭代的长度限制，最大1000，推荐值 1000
        int limit = 1000;
        //指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
        String delimiter = "";
        //列举空间文件列表
        BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(config.getBucket(), prefix, limit, delimiter);
        while (fileListIterator.hasNext()) {
            //处理获取的file list结果
            SysQiniuContent qiniuContent;
            FileInfo[] items = fileListIterator.next();
            for (FileInfo item : items) {
                if (qiniuContentMapper.selectOne(new QueryWrapper<SysQiniuContent>().eq("name", FileUtil.getFileNameNoEx(item.key))) == null) {
                    qiniuContent = new SysQiniuContent();
                    qiniuContent.setSize(FileUtil.getSize(Integer.parseInt(item.fsize + "")));
                    qiniuContent.setSuffix(FileUtil.getExtensionName(item.key));
                    qiniuContent.setName(FileUtil.getFileNameNoEx(item.key));
                    qiniuContent.setType(config.getType());
                    qiniuContent.setBucket(config.getBucket());
                    qiniuContent.setUrl(config.getHost() + "/" + item.key);
                    qiniuContentMapper.insert(qiniuContent);
                }
            }
        }
    }

    @Override
    public void deleteAll(Long[] ids, SysQiniuConfig config) {
        for (Long id : ids) {
            delete(findByContentId(id), config);
        }
    }


    @Override
    public void downloadList(List<SysQiniuContent> queryAll, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SysQiniuContent content : queryAll) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("文件名", content.getName());
            map.put("文件类型", content.getSuffix());
            map.put("空间名称", content.getBucket());
            map.put("文件大小", content.getSize());
            map.put("空间类型", content.getType());
            map.put("创建日期", content.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
