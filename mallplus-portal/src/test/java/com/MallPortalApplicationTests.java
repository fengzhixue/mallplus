package com;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zscat.mallplus.cms.entity.CmsSubject;
import com.zscat.mallplus.cms.mapper.CmsSubjectMapper;
import com.zscat.mallplus.sys.mapper.SysAreaMapper;
import com.zscat.mallplus.sys.mapper.SysSchoolMapper;
import com.zscat.mallplus.ums.mapper.UmsMemberMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

@SpringBootTest(classes = MallPortalApplicationTests.class)
@RunWith(SpringRunner.class)
@Log4j2
public class MallPortalApplicationTests {

    @Resource
    UmsMemberMapper sysAdminLogMapper;

    @Resource
    CmsSubjectMapper subjectMapper;
    @Resource
    SysSchoolMapper schoolMapper;
    @Resource
    SysAreaMapper sysAreaMapper;

    public static void main(String[] args) {
        String a = "102087102114";
        String b = "101829";
        System.out.println(a.replaceAll("[^" + b + "]", "").length());
    }

    @Test
    public void contextLoads() {
        List<CmsSubject> list = subjectMapper.selectList(new QueryWrapper<>());
        for (CmsSubject subject : list) {
            Random r = new Random();
            Long a = r.nextLong();
            System.out.println(a);
            Long b = r.nextLong();
            subject.setAreaId(a);
            subject.setSchoolId(b);
            //  subjectMapper.updateById(subject);
        }
        // List<UmsMember> log =  sysAdminLogMapper.selectList(new QueryWrapper<UmsMember>().between("create_time","2018-03-03","2018-09-03"));
    }
}
