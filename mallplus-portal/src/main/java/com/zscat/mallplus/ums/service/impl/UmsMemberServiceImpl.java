package com.zscat.mallplus.ums.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.zscat.mallplus.component.UserUtils;
import com.zscat.mallplus.enums.AllEnum;
import com.zscat.mallplus.exception.ApiMallPlusException;
import com.zscat.mallplus.jifen.entity.JifenDonateRule;
import com.zscat.mallplus.oms.mapper.OmsOrderMapper;
import com.zscat.mallplus.oms.vo.OrderStstic;
import com.zscat.mallplus.sys.mapper.SysAreaMapper;
import com.zscat.mallplus.ums.entity.*;
import com.zscat.mallplus.ums.mapper.SysAppletSetMapper;
import com.zscat.mallplus.ums.mapper.UmsIntegrationConsumeSettingMapper;
import com.zscat.mallplus.ums.mapper.UmsMemberMapper;
import com.zscat.mallplus.ums.mapper.UmsMemberMemberTagRelationMapper;
import com.zscat.mallplus.ums.service.*;
import com.zscat.mallplus.util.*;
import com.zscat.mallplus.utils.CommonResult;
import com.zscat.mallplus.utils.MatrixToImageWriter;
import com.zscat.mallplus.utils.ValidatorUtils;
import com.zscat.mallplus.vo.*;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Slf4j
@Service
public class UmsMemberServiceImpl extends ServiceImpl<UmsMemberMapper, UmsMember> implements IUmsMemberService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UmsMemberServiceImpl.class);

    @Autowired
    OssAliyunUtil aliyunOSSUtil;
    Integer regJifen = 100;
    Integer logginJifen = 5;
    @Resource
    private SysAppletSetMapper appletSetMapper;
    @Resource
    private UmsMemberMapper memberMapper;
    @Resource
    private BCryptPasswordEncoder passwordEncoder;
    @Resource
    private RedisService redisService;
    /* @Resource
     private AuthenticationManager authenticationManager;*/
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private SmsService smsService;
    @Resource
    private SysAreaMapper areaMapper;
    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Value("${redis.key.prefix.authCode}")
    private String REDIS_KEY_PREFIX_AUTH_CODE;
    @Value("${authCode.expire.seconds}")
    private Long AUTH_CODE_EXPIRE_SECONDS;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${aliyun.sms.expire-minute:1}")
    private Integer expireMinute;
    @Value("${aliyun.sms.day-count:30}")
    private Integer dayCount;
    @Resource
    private UmsMemberMemberTagRelationMapper umsMemberMemberTagRelationMapper;
    @Resource
    private IUmsMemberLevelService memberLevelService;
    @Resource
    private OmsOrderMapper omsOrderMapper;
    @Resource
    private IUmsMemberBlanceLogService blanceLogService;
    @Resource
    private IUmsIntegrationChangeHistoryService umsIntegrationChangeHistoryService;

    @Resource
    UmsIntegrationConsumeSettingMapper integrationConsumeSettingMapper;

    private OkHttpClient okHttpClient = new OkHttpClient();

    public final static String getPageOpenidUrl = "https://api.weixin.qq.com/sns/jscode2session";
    public final static String GetPageAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    //微信公众号获取用户信息
    public final static String GetPageUserInfoUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
    //微信小程序获取用户信息
    public final static String GetPageUserInfoUrl_XCX = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    @Override
    public UmsMember getNewCurrentMember() {
        return (UmsMember) this.getCurrentMember();
    }

    @Override
    public String getWxPhone(String openid, String keyStr, String ivStr, String encDataStr) {
        //解析手机号
        WxPhoneInfo wxPhoneInfo = null;
        System.err.println("encDataStr=" + encDataStr + ",keyStr=" + keyStr + ",ivStr=" + ivStr);
        try {
            String result = AES.wxDecrypt(encDataStr, keyStr, ivStr);
            Gson gson = new Gson();
            wxPhoneInfo = gson.fromJson(result, WxPhoneInfo.class);
            if (wxPhoneInfo != null) {
                return wxPhoneInfo.getPhoneNumber();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object webLogin(String wxH5Appid, String wxH5Secret, String code) {
        //H5 微信公众号网页登录
        try {
            log.info("https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                    + wxH5Appid + "&secret=" + wxH5Secret + "&code=" + code + "&grant_type=authorization_code");
            String json = okHttpClient.newCall(
                    new Request.Builder().url("https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                            + wxH5Appid + "&secret=" + wxH5Secret + "&code=" + code + "&grant_type=authorization_code").build()).execute().body().string();
            com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(json);
            log.info(jsonObject.toJSONString());
            Integer errcode = jsonObject.getInteger("errcode");
            if (errcode == null || errcode == 0) {
                String openid = jsonObject.getString("openid");
                Map<String, Object> resultObj = new HashMap<String, Object>();
                UmsMember userVo = this.queryByOpenId(openid);
                String token = null;
                if (null == userVo) {
                    UmsMember umsMember = new UmsMember();
                    umsMember.setUsername("wxapplet" + CharUtil.getRandomString(12));
                    umsMember.setSourceType(2);
                    umsMember.setPassword(passwordEncoder.encode("123456"));
                    umsMember.setCreateTime(new Date());
                    umsMember.setStatus(1);
                    umsMember.setBlance(new BigDecimal(10000));
                    umsMember.setIntegration(0);
                    umsMember.setMemberLevelId(4L);
                    umsMember.setWeixinOpenid(openid);
                    memberMapper.insert(umsMember);
                    token = jwtTokenUtil.generateToken(umsMember.getUsername());
                    resultObj.put("userId", umsMember.getId());
                    resultObj.put("userInfo", umsMember);
                    addIntegration(umsMember.getId(), regJifen, 1, "注册添加积分", AllEnum.ChangeSource.register.code(), umsMember.getUsername());

                } else {
                    addIntegration(userVo.getId(), logginJifen, 1, "登录添加积分", AllEnum.ChangeSource.login.code(), userVo.getUsername());

                    token = jwtTokenUtil.generateToken(userVo.getUsername());
                    resultObj.put("userId", userVo.getId());
                    resultObj.put("userInfo", userVo);
                }


                if (StringUtils.isEmpty(token)) {
                    throw new ApiMallPlusException("登录失败");

                }
                resultObj.put("tokenHead", tokenHead);
                resultObj.put("token", token);


                return new CommonResult().success(resultObj);
            } else {
                throw new ApiMallPlusException(jsonObject.toJSONString());
            }
        } catch (ApiMallPlusException e) {
            e.printStackTrace();
            throw new ApiMallPlusException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiMallPlusException(e.getMessage());
        }

    }

    @Override
    public Object getCurrentMember() {
        try {
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            String requestType = ((HttpServletRequest) request).getMethod();
            if ("OPTIONS".equals(requestType)) {
                return null;
            }
            UmsMember member = UserUtils.getCurrentMember();
            if (member != null && member.getId() != null) {
                return member;
            }

            String tokenPre = "authorization";
            String authHeader = request.getParameter(tokenPre);
            if (ValidatorUtils.empty(authHeader)) {
                authHeader = request.getHeader(tokenPre);
            }
            if (authHeader != null && authHeader.startsWith("Bearer")) {
                String authToken = authHeader.substring("Bearer".length());
                String username = jwtTokenUtil.getUserNameFromToken(authToken);
                if (ValidatorUtils.notEmpty(username)) {
                    member = JsonUtils.jsonToPojo(redisService.get( String.format(Rediskey.MEMBER, username)), UmsMember.class);
                    if (member == null || member.getId() == null) {
                        member = getByUsername(username);
                    }
                    return member;
                }
            } else {
                return new CommonResult().fail(100);
            }
            return new CommonResult().fail(100);
        } catch (Exception e) {
            e.printStackTrace();
            return new UmsMember();
        }
    }

    @Override
    public void updataMemberOrderInfo() {
        List<OrderStstic> orders = omsOrderMapper.listOrderGroupByMemberId();
        List<UmsMemberLevel> levelList = memberLevelService.list(new QueryWrapper<UmsMemberLevel>().orderByDesc("price"));
        for (OrderStstic o : orders) {
            UmsMember member = new UmsMember();
            member.setId(o.getMemberId());
            member.setBuyMoney(o.getTotalPayAmount());
            for (UmsMemberLevel level : levelList) {
                if (member.getBuyMoney() != null) {
                    if (member.getBuyMoney().compareTo(level.getPrice()) >= 0) {
                        member.setMemberLevelId(level.getId());
                        member.setMemberLevelName(level.getName());
                        break;
                    }
                }
            }
            member.setBuyCount(o.getTotalCount());
            memberMapper.updateById(member);
        }
    }

    /**
     * 添加余额记录 并更新用户余额
     *
     * @param id
     * @param integration
     */
    @Override
    public void addBlance(Long id, Integer integration, int type, String note) {

        UmsMemberBlanceLog blanceLog = new UmsMemberBlanceLog();
        blanceLog.setMemberId(id);
        blanceLog.setPrice(new BigDecimal(integration));
        blanceLog.setCreateTime(new Date());
        blanceLog.setType(type);
        blanceLog.setNote(note);
        blanceLogService.save(blanceLog);
        UmsMember member = memberMapper.selectById(id);
        member.setBlance(member.getBlance().add(blanceLog.getPrice()));
        memberMapper.updateById(member);

    }

    /**
     * 添加积分记录 并更新用户积分
     *
     * @param id
     * @param integration
     */
    @Override
    public void addIntegration(Long id, Integer integration, int changeType, String note, int sourceType, String operateMan) {
        UmsIntegrationConsumeSetting setting = integrationConsumeSettingMapper.selectById(1);
        if (setting==null){
            return;
        }
        UmsIntegrationChangeHistory history = new UmsIntegrationChangeHistory();
        history.setMemberId(id);
        if (sourceType==AllEnum.ChangeSource.register.code()){
            history.setChangeCount(setting.getRegister());
        }else  if (sourceType==AllEnum.ChangeSource.login.code()){
            history.setChangeCount(setting.getLogin());
        } if (sourceType==AllEnum.ChangeSource.order.code()){
            history.setChangeCount(setting.getOrders()*integration);
        } if (sourceType==AllEnum.ChangeSource.sign.code()){
            history.setChangeCount(setting.getSign());
        }

        history.setCreateTime(new Date());
        history.setChangeType(changeType);
        history.setOperateNote(note);
        history.setSourceType(sourceType);
        history.setOperateMan(operateMan);
        umsIntegrationChangeHistoryService.save(history);
        UmsMember member = memberMapper.selectById(id);
        if (ValidatorUtils.empty(member.getIntegration())) {
            member.setIntegration(0);
        }
        member.setIntegration(member.getIntegration() + integration);
        memberMapper.updateById(member);
        redisService.set( String.format(Rediskey.MEMBER, member.getUsername()), JsonUtils.objectToJson(member));

    }

    @Override
    public UmsMember getByUsername(String username) {
        UmsMember umsMember = new UmsMember();
        umsMember.setUsername(username);

        return memberMapper.selectOne(new QueryWrapper<>(umsMember));
    }

    @Override
    public UmsMember getById(Long id) {
        return memberMapper.selectById(id);
    }

    @Override
    public Object resetPassword(String phone, String password, String confimpassword, String authCode) {
        if (ValidatorUtils.notEmpty(authCode) && !verifyAuthCode(authCode, phone)) {
            return new CommonResult().failed("验证码错误");
        }
        if (!password.equals(confimpassword)) {
            return new CommonResult().failed("密码不一致");
        }
        UmsMember umsMember = new UmsMember();
        umsMember.setPassword(passwordEncoder.encode(password));
        memberMapper.update(umsMember, new QueryWrapper<UmsMember>().eq("phone", phone));
        return true;
    }

    @Override
    public CommonResult register(String phone, String password, String confim, String authCode, String invitecode) {

        //没有该用户进行添加操作
        UmsMember umsMember = new UmsMember();
        umsMember.setUsername(phone);
        umsMember.setPhone(phone);
        umsMember.setSourceType(3);
        umsMember.setPassword(password);
        umsMember.setConfimpassword(confim);
        umsMember.setPhonecode(authCode);
        umsMember.setInvitecode(invitecode);
        if (ValidatorUtils.notEmpty(umsMember.getPhonecode()) && !verifyAuthCode(umsMember.getPhonecode(), umsMember.getPhone())) {
            return new CommonResult().failed("验证码错误");
        }
        return this.register(umsMember);
    }

    @Override
    public SmsCode generateCode(String phone) {
        //生成流水号
        String uuid = UUID.randomUUID().toString();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        Map<String, String> map = new HashMap<>(2);
        map.put("code", sb.toString());
        map.put("phone", phone);

        //短信验证码缓存15分钟，
        redisService.set(REDIS_KEY_PREFIX_AUTH_CODE + phone, sb.toString());
        redisService.expire(REDIS_KEY_PREFIX_AUTH_CODE + phone, expireMinute * 60);


        //存储sys_sms
        saveSmsAndSendCode(phone, sb.toString());
        SmsCode smsCode = new SmsCode();
        smsCode.setKey(uuid);
        return smsCode;
    }

    /**
     * 保存短信记录，并发送短信
     *
     * @param phone
     * @param code
     */
    private void saveSmsAndSendCode(String phone, String code) {
        checkTodaySendCount(phone);

        Sms sms = new Sms();
        sms.setPhone(phone);
        sms.setParams(code);
        Map<String, String> params = new HashMap<>();
        params.put("code", code);
        smsService.save(sms, params);

        //异步调用阿里短信接口发送短信
        CompletableFuture.runAsync(() -> {
            try {
                smsService.sendSmsMsg(sms);
            } catch (Exception e) {
                params.put("err", e.getMessage());
                smsService.save(sms, params);
                e.printStackTrace();
                LOGGER.error("发送短信失败：{}", e.getMessage());
            }

        });

        // 当天发送验证码次数+1
        String countKey = countKey(phone);
        redisService.increment(countKey, 1L);
        redisService.expire(countKey, 1 * 3600 * 24);
    }

    /**
     * 获取当天发送验证码次数
     * 限制号码当天次数
     *
     * @param phone
     * @return
     */
    private void checkTodaySendCount(String phone) {
        String value = redisService.get(countKey(phone));
        if (value != null) {
            Integer count = Integer.valueOf(value);
            if (count > dayCount) {
                throw new IllegalArgumentException("已超过当天最大次数");
            }
        }

    }

    private String countKey(String phone) {
        return "sms:count:" + LocalDate.now().toString() + ":" + phone;
    }

    @Override
    public CommonResult register(UmsMember user) {
        //验证验证码
        if (ValidatorUtils.notEmpty(user.getPhonecode()) && !verifyAuthCode(user.getPhonecode(), user.getPhone())) {
            return new CommonResult().failed("验证码错误");
        }
        if (!user.getPassword().equals(user.getConfimpassword())) {
            return new CommonResult().failed("密码不一致");
        }
        //查询是否已有该用户

        UmsMember queryM = new UmsMember();
        queryM.setUsername(user.getUsername());
        UmsMember umsMembers = memberMapper.selectOne(new QueryWrapper<>(queryM));
        if (umsMembers != null) {
            return new CommonResult().failed("该用户已经存在");
        }
        //没有该用户进行添加操作

        UmsMember umsMember = new UmsMember();
        umsMember.setMemberLevelId(4L);
        umsMember.setMemberLevelName("普通会员");
        umsMember.setUsername(user.getUsername());
        umsMember.setNickname(user.getUsername());
        umsMember.setSourceType(user.getSourceType());
        umsMember.setPhone(user.getPhone());
        umsMember.setPassword(passwordEncoder.encode(user.getPassword()));
        umsMember.setCreateTime(new Date());
        umsMember.setStatus(1);
        umsMember.setBuyCount(0);
        umsMember.setBuyMoney(BigDecimal.ZERO);
        umsMember.setBlance(new BigDecimal(10000));
        umsMember.setIntegration(10000);
        if (ValidatorUtils.notEmpty(user.getInvitecode())) {
            umsMember.setInvitecode(user.getInvitecode());
        }
        try {
            String defaultIcon = "http://yjlive160322.oss-cn-beijing.aliyuncs.com/mall/images/20190830/uniapp.jpeg";
            umsMember.setIcon(defaultIcon);
            //这是要生成二维码的url
            String url = "http://www.yjlive.cn:8082/?invitecode=" + user.getUsername();
            //要添加到二维码下面的文字
            String words = user.getUsername() + "的二维码";
            //调用刚才的工具类
            ByteArrayResource qrCode = MatrixToImageWriter.createQrCode(url, words);
            InputStream inputStream = new ByteArrayInputStream(qrCode.getByteArray());

            umsMember.setAvatar(aliyunOSSUtil.upload("png", inputStream));
        }catch (Exception e){

        }

        memberMapper.insert(umsMember);

        redisService.set(String.format(Rediskey.MEMBER, umsMember.getUsername()), JsonUtils.objectToJson(umsMember));

        addIntegration(umsMember.getId(), regJifen, 1, "注册添加积分", AllEnum.ChangeSource.register.code(), umsMember.getUsername());
        umsMember.setPassword(null);
        return new CommonResult().success("注册成功", null);
    }

    @Override
    public Object simpleReg(String phone, String password, String confimpassword, String invitecode) {
        //没有该用户进行添加操作
        UmsMember user = new UmsMember();
        user.setUsername(phone);
        user.setPhone(phone);
        user.setPassword(password);
        user.setConfimpassword(confimpassword);
        user.setInvitecode(invitecode);
        return this.register(user);
    }

    @Override
    public CommonResult generateAuthCode(String telephone) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        //验证码绑定手机号并存储到redis
        redisService.set(REDIS_KEY_PREFIX_AUTH_CODE + telephone, sb.toString());
        redisService.expire(REDIS_KEY_PREFIX_AUTH_CODE + telephone, AUTH_CODE_EXPIRE_SECONDS);
        return new CommonResult().success("获取验证码成功", sb.toString());
    }

    @Override
    public CommonResult updatePassword(String telephone, String password, String authCode) {
        UmsMember example = new UmsMember();
        example.setPhone(telephone);
        UmsMember member = memberMapper.selectOne(new QueryWrapper<>(example));
        if (member == null) {
            return new CommonResult().failed("该账号不存在");
        }
        //验证验证码
        if (!verifyAuthCode(authCode, telephone)) {
            return new CommonResult().failed("验证码错误");
        }

        member.setPassword(passwordEncoder.encode(password));
        memberMapper.updateById(member);
        return new CommonResult().success("密码修改成功", null);
    }


    @Override
    public void updateIntegration(Long id, Integer integration) {
        UmsMember record = new UmsMember();
        record.setId(id);
        record.setIntegration(integration);
        memberMapper.updateById(record);
    }

    //对输入的验证码进行校验
    private boolean verifyAuthCode(String authCode, String telephone) {
        if (StringUtils.isEmpty(authCode)) {
            return false;
        }
        String realAuthCode = redisService.get(REDIS_KEY_PREFIX_AUTH_CODE + telephone);
        return authCode.equals(realAuthCode);
    }

    @Override
    public UmsMember queryByOpenId(String openId) {
        UmsMember queryO = new UmsMember();
        queryO.setWeixinOpenid(openId);
        return memberMapper.selectOne(new QueryWrapper<>(queryO));
    }

    @Override
    public Map<String, Object> appLogin(String openid, Integer sex, String headimgurl, String unionid, String nickname, String city, Integer source) {
        Map<String, Object> resultObj = new HashMap<String, Object>();
        UmsMember userVo = this.queryByOpenId(openid);
        String token = null;
        if (null == userVo) {
            UmsMember umsMember = new UmsMember();
            umsMember.setUsername("wxapplet" + CharUtil.getRandomString(12));
            umsMember.setSourceType(source);
            umsMember.setPassword(passwordEncoder.encode("123456"));
            umsMember.setCreateTime(new Date());
            umsMember.setStatus(1);
            umsMember.setBlance(new BigDecimal(10000));
            umsMember.setIntegration(0);
            umsMember.setMemberLevelId(4L);
            umsMember.setCity(city);
            umsMember.setAvatar(headimgurl);
            umsMember.setGender(sex);
            umsMember.setHistoryIntegration(0);
            umsMember.setWeixinOpenid(openid);
            if (StringUtils.isEmpty(headimgurl)) {
                //会员头像(默认头像)
                umsMember.setIcon("/upload/img/avatar/01.jpg");
            } else {
                umsMember.setIcon(headimgurl);
            }

            umsMember.setNickname(nickname);
            umsMember.setPersonalizedSignature(unionid);
            memberMapper.insert(umsMember);
            token = jwtTokenUtil.generateToken(umsMember.getUsername());
            resultObj.put("userId", umsMember.getId());
            resultObj.put("userInfo", umsMember);
            addIntegration(umsMember.getId(), regJifen, 1, "注册添加积分", AllEnum.ChangeSource.register.code(), umsMember.getUsername());

        } else {
            addIntegration(userVo.getId(), logginJifen, 1, "登录添加积分", AllEnum.ChangeSource.login.code(), userVo.getUsername());

            token = jwtTokenUtil.generateToken(userVo.getUsername());
            resultObj.put("userId", userVo.getId());
            resultObj.put("userInfo", userVo);
        }
        if (StringUtils.isEmpty(token)) {
            throw new ApiMallPlusException("登录失败");
        }
        resultObj.put("tokenHead", tokenHead);
        resultObj.put("token", token);
        return resultObj;
    }

    @Override
    public Object getAppletOpenId(AppletLoginParam req) {
        SysAppletSet appletSet = appletSetMapper.selectOne(new QueryWrapper<>());
        if (null == appletSet) {
            throw new ApiMallPlusException("没有设置支付配置");
        }
        String webAccessTokenhttps = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";
        String code = req.getCode();
        if (StringUtils.isEmpty(code)) {
            log.error("code ie empty");
            throw new ApiMallPlusException("code ie empty");
        }
        //获取openid
        String requestUrl = String.format(webAccessTokenhttps,
                appletSet.getAppid(),
                appletSet.getAppsecret(),
                code);


        JSONObject sessionData = CommonUtil.httpsRequest(requestUrl, "GET", null);
        String userInfos = req.getUserInfo();

        String signature = req.getSignature();
        if (null == sessionData || StringUtils.isEmpty(sessionData.getString("openid"))) {
            throw new ApiMallPlusException("登录失败openid is empty");
        }
        //验证用户信息完整性
        String sha1 = CommonUtil.getSha1(userInfos + sessionData.getString("session_key"));
        if (!signature.equals(sha1)) {
            throw new ApiMallPlusException("登录失败,验证用户信息完整性 签名验证失败" + sha1 + "，" + signature);

        }
        return new CommonResult().success(sessionData.getString("openid"));
    }

    @Override
    public Object loginByWeixin(AppletLoginParam req) {
        try {
            String userInfos = req.getUserInfo();
            Map<String, Object> me = JsonUtils.readJsonToMap(userInfos);
            if (null == me) {
                throw new ApiMallPlusException("登录失败 userInfos is null");
            }
            Map<String, Object> resultObj = new HashMap<String, Object>();
            UmsMember userVo = new UmsMember();
            // 判断是否绑定了手机号
            if (ValidatorUtils.notEmpty(req.getPhone())) {
                UmsMember queryO = new UmsMember();
                queryO.setPhone(req.getPhone());
                userVo = memberMapper.selectOne(new QueryWrapper<>(queryO));
            } else {
                throw new ApiMallPlusException("登录失败 请先绑定手机号");
            }

            String token = null;
            if (null == userVo) {
                UmsMember umsMember = new UmsMember();
                umsMember.setUsername("wxapplet" + CharUtil.getRandomString(8));
                umsMember.setSourceType(2);
                umsMember.setPassword(passwordEncoder.encode("123456"));
                umsMember.setCreateTime(new Date());
                umsMember.setStatus(1);
                umsMember.setBlance(new BigDecimal(10000));
                umsMember.setIntegration(0);
                umsMember.setMemberLevelId(4L);
                umsMember.setAvatar(req.getCloudID());
                umsMember.setCity(me.get("country").toString() + "-" + me.get("province").toString() + "-" + me.get("city").toString());

                umsMember.setGender((Integer) me.get("gender"));
                umsMember.setHistoryIntegration(0);
                umsMember.setWeixinOpenid(req.getOpenid());
                if (StringUtils.isEmpty(me.get("avatarUrl").toString())) {
                    //会员头像(默认头像)
                    umsMember.setIcon("/upload/img/avatar/01.jpg");
                } else {
                    umsMember.setIcon(me.get("avatarUrl").toString());
                }
                // umsMember.setGender(Integer.parseInt(me.get("gender")));
                umsMember.setNickname(me.get("nickName").toString());
                String defaultIcon = "http://yjlive160322.oss-cn-beijing.aliyuncs.com/mall/images/20190830/uniapp.jpeg";
                umsMember.setIcon(defaultIcon);
                //这是要生成二维码的url
                String url = "http://www.yjlive.cn:8082/?invitecode=" + umsMember.getUsername();
                //要添加到二维码下面的文字
                String words = umsMember.getUsername() + "的二维码";
                //调用刚才的工具类
                ByteArrayResource qrCode = MatrixToImageWriter.createQrCode(url, words);
                InputStream inputStream = new ByteArrayInputStream(qrCode.getByteArray());


                umsMember.setAvatar(aliyunOSSUtil.upload("png", inputStream));
                memberMapper.insert(umsMember);
                token = jwtTokenUtil.generateToken(umsMember.getUsername());
                resultObj.put("userId", umsMember.getId());
                resultObj.put("userInfo", umsMember);
                addIntegration(umsMember.getId(), regJifen, 1, "注册添加积分", AllEnum.ChangeSource.register.code(), userVo.getUsername());


            } else {
                //  userVo = this.queryByOpenId(sessionData.getString("openid"));
                if (ValidatorUtils.notEmpty(userVo.getWeixinOpenid())) {
                    addIntegration(userVo.getId(), logginJifen, 1, "登录添加积分", AllEnum.ChangeSource.login.code(), userVo.getUsername());
                    token = jwtTokenUtil.generateToken(userVo.getUsername());
                    resultObj.put("userId", userVo.getId());
                    resultObj.put("userInfo", userVo);
                } else {
                    userVo.setPassword(passwordEncoder.encode("123456"));
                    userVo.setCreateTime(new Date());
                    userVo.setStatus(1);
                    userVo.setBlance(new BigDecimal(10000));
                    userVo.setIntegration(0);
                    userVo.setMemberLevelId(4L);
                    userVo.setAvatar(req.getCloudID());
                    userVo.setCity(me.get("country").toString() + "-" + me.get("province").toString() + "-" + me.get("city").toString());

                    userVo.setGender((Integer) me.get("gender"));
                    userVo.setHistoryIntegration(0);
                    userVo.setWeixinOpenid(req.getOpenid());
                    if (StringUtils.isEmpty(me.get("avatarUrl").toString())) {
                        //会员头像(默认头像)
                        userVo.setIcon("/upload/img/avatar/01.jpg");
                    } else {
                        userVo.setIcon(me.get("avatarUrl").toString());
                    }
                    // umsMember.setGender(Integer.parseInt(me.get("gender")));
                    userVo.setNickname(me.get("nickName").toString());

                    memberMapper.updateById(userVo);
                }

            }


            if (StringUtils.isEmpty(token)) {
                throw new ApiMallPlusException("登录失败");
            }
            resultObj.put("tokenHead", tokenHead);
            resultObj.put("token", token);


            return new CommonResult().success(resultObj);
        } catch (ApiMallPlusException e) {
            e.printStackTrace();
            throw new ApiMallPlusException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiMallPlusException(e.getMessage());
        }

    }

    @Override
    public Object loginByWeixin2(AppletLoginnewParam req) {
        try {
            SysAppletSet appletSet = appletSetMapper.selectOne(new QueryWrapper<>());
            if (null == appletSet) {
                throw new ApiMallPlusException("没有设置支付配置");
            }
            String code = req.getCode();
            if (StringUtils.isEmpty(code)) {
                log.error("code ie empty");
                throw new ApiMallPlusException("code ie empty");
            }
            UserInfo userInfos = req.getUserInfo();

            String signature = req.getSignature();

            if (null == userInfos) {
                throw new ApiMallPlusException("登录失败 userInfos is null");
            }

            Map<String, Object> resultObj = new HashMap<String, Object>();

            String webAccessTokenhttps = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

            //获取openid
            String requestUrl = String.format(webAccessTokenhttps,
                    appletSet.getAppid(),
                    appletSet.getAppsecret(),
                    code);

            JSONObject sessionData = CommonUtil.httpsRequest(requestUrl, "GET", null);

            if (null == sessionData || StringUtils.isEmpty(sessionData.getString("openid"))) {
                throw new ApiMallPlusException("登录失败openid is empty");
            }
            //验证用户信息完整性
            //  String sha1 = CommonUtil.getSha1(JsonUtils.toJsonStr(userInfos) + sessionData.getString("session_key"));
            //  if (!signature.equals(sha1)) {
            //    throw new ApiMallPlusException("登录失败,验证用户信息完整性 签名验证失败" + sha1 + "，" + signature);
            //  }
            UmsMember userVo = this.queryByOpenId(sessionData.getString("openid"));
            String token = null;
            if (null == userVo) {
                UmsMember umsMember = new UmsMember();
                umsMember.setUsername("wxapplet" + CharUtil.getRandomString(12));
                umsMember.setSourceType(2);
                umsMember.setPassword(passwordEncoder.encode("123456"));
                umsMember.setCreateTime(new Date());
                umsMember.setStatus(1);
                umsMember.setBlance(new BigDecimal(10000));
                umsMember.setIntegration(0);
                umsMember.setMemberLevelId(4L);
                umsMember.setAvatar(req.getCloudID());
                umsMember.setCity(userInfos.getCountry() + "-" + userInfos.getProvince() + "-" +
                        userInfos.getCity());

                umsMember.setGender(Integer.valueOf(userInfos.getGender()));
                umsMember.setHistoryIntegration(0);
                umsMember.setWeixinOpenid(sessionData.getString("openid"));
                if (StringUtils.isEmpty(userInfos.getAvatarUrl())) {
                    //会员头像(默认头像)
                    umsMember.setIcon("/upload/img/avatar/01.jpg");
                } else {
                    umsMember.setIcon(userInfos.getAvatarUrl());
                }
                // umsMember.setGender(Integer.parseInt(me.get("gender")));
                umsMember.setNickname(userInfos.getNickName());
                String defaultIcon = "http://yjlive160322.oss-cn-beijing.aliyuncs.com/mall/images/20190830/uniapp.jpeg";
                umsMember.setIcon(defaultIcon);
                //这是要生成二维码的url
                String url = "http://www.yjlive.cn:8082/?invitecode=" + umsMember.getUsername();
                //要添加到二维码下面的文字
                String words = umsMember.getUsername() + "的二维码";
                //调用刚才的工具类
                ByteArrayResource qrCode = MatrixToImageWriter.createQrCode(url, words);
                InputStream inputStream = new ByteArrayInputStream(qrCode.getByteArray());


                umsMember.setAvatar(aliyunOSSUtil.upload("png", inputStream));
                memberMapper.insert(umsMember);
                token = jwtTokenUtil.generateToken(umsMember.getUsername());
                resultObj.put("userId", umsMember.getId());
                resultObj.put("userInfo", umsMember);
                addIntegration(umsMember.getId(), regJifen, 1, "注册添加积分", AllEnum.ChangeSource.register.code(), umsMember.getUsername());


            } else {
                addIntegration(userVo.getId(), logginJifen, 1, "登录添加积分", AllEnum.ChangeSource.login.code(), userVo.getUsername());
                token = jwtTokenUtil.generateToken(userVo.getUsername());
                resultObj.put("userId", userVo.getId());
                resultObj.put("userInfo", userVo);
            }


            if (StringUtils.isEmpty(token)) {
                throw new ApiMallPlusException("登录失败");
            }
            resultObj.put("tokenHead", tokenHead);
            resultObj.put("token", token);


            return new CommonResult().success(resultObj);
        } catch (ApiMallPlusException e) {
            e.printStackTrace();
            throw new ApiMallPlusException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiMallPlusException(e.getMessage());
        }

    }

    /**
     * 微信访问获取结果
     * @param url
     * @return
     * @throws Exception
     */
    public static String httpClientSend(String url) throws Exception{
        HttpClient client =  new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String response = client.execute(httpget, responseHandler);
        return response;

    }
    @Override
    public Object loginByWeixin1(AppletLoginParam req) {
        try {
            SysAppletSet appletSet = appletSetMapper.selectOne(new QueryWrapper<>());
            if (null == appletSet) {
                throw new ApiMallPlusException("没有设置支付配置");
            }
            String code = req.getCode();
            if (StringUtils.isEmpty(code)) {
                log.error("code ie empty");
                throw new ApiMallPlusException("code ie empty");
            }
            String userInfos = req.getUserInfo();

            String signature = req.getSignature();




            Map<String, Object> resultObj = new HashMap<String, Object>();

            String webAccessTokenhttps = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

            //获取openid
            String requestUrl = String.format(webAccessTokenhttps,
                    appletSet.getAppid(),
                    appletSet.getAppsecret(),
                    code);

            JSONObject sessionData = CommonUtil.httpsRequest(requestUrl, "GET", null);

            if (null == sessionData || StringUtils.isEmpty(sessionData.getString("openid"))) {
                throw new ApiMallPlusException("登录失败openid is empty");
            }
            //验证用户信息完整性
            String sha1 = CommonUtil.getSha1(userInfos + sessionData.getString("session_key"));
           /* if (!signature.equals(sha1)) {
                throw new ApiMallPlusException("登录失败,验证用户信息完整性 签名验证失败" + sha1 + "，" + signature);
            }*/
            UmsMember userVo = this.queryByOpenId(sessionData.getString("openid"));
            String token = null;
            if (null == userVo) {
                UmsMember umsMember = new UmsMember();
                umsMember.setUsername("wxapplet" + CharUtil.getRandomString(12));
                umsMember.setSourceType(2);
                umsMember.setPassword(passwordEncoder.encode("123456"));
                umsMember.setCreateTime(new Date());
                umsMember.setStatus(1);
                umsMember.setBlance(new BigDecimal(10000));
                umsMember.setIntegration(0);
                umsMember.setMemberLevelId(4L);
                umsMember.setAvatar(req.getCloudID());

                if (ValidatorUtils.empty(userInfos)) {
                    //未查询到用户信息，通过微信获取用户tokey信息
                    String requestUrl1 = GetPageAccessTokenUrl.replace("APPID", appletSet.getAppid()).replace("APPSECRET", appletSet.getAppsecret());
                    String openidResponse = httpClientSend(requestUrl1);
                    JSONObject OpenidJSONO = JSONObject.fromObject(openidResponse);
                    String accessToken = String.valueOf(OpenidJSONO.get("access_token"));
                    //获取用户数据信息
                    String requestUserInfoUrl = GetPageUserInfoUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", appletSet.getAppid());
                    String userresponse = httpClientSend(requestUserInfoUrl);
                    JSONObject userJSON = JSONObject.fromObject(userresponse);
                    String nickname = new String(String.valueOf(userJSON.get("nickname")).getBytes("ISO8859-1"),"UTF-8");
                    String avatarUrl = String.valueOf(userJSON.get("avatarUrl"));
                    String city = new String(String.valueOf(userJSON.get("city")).getBytes("ISO8859-1"),"UTF-8");
                    String country = new String(String.valueOf(userJSON.get("country")).getBytes("ISO8859-1"),"UTF-8");
                    String gender = String.valueOf(userJSON.get("gender"));
                    String province = new String(String.valueOf(userJSON.get("province")).getBytes("ISO8859-1"),"UTF-8");
                    String language = String.valueOf(userJSON.get("language"));

                    umsMember.setCity(country + "-" + province + "-" + city);
                    if (StringUtils.isEmpty(avatarUrl)) {
                        //会员头像(默认头像)
                        umsMember.setIcon("/upload/img/avatar/01.jpg");
                    } else {
                        umsMember.setIcon(avatarUrl);
                    }
                    // umsMember.setGender(Integer.parseInt(me.get("gender")));
                    umsMember.setNickname(nickname);
                    if (ValidatorUtils.notEmpty(gender) && !"null".equals(gender)){
                        umsMember.setGender(Integer.valueOf(gender));
                    }
                }else {
                    Map<String, Object> me = JsonUtils.readJsonToMap(userInfos);
                    umsMember.setCity(me.get("country").toString() + "-" + me.get("province").toString() + "-" + me.get("city").toString());
                    if (StringUtils.isEmpty(me.get("avatarUrl").toString())) {
                        //会员头像(默认头像)
                        umsMember.setIcon("/upload/img/avatar/01.jpg");
                    } else {
                        umsMember.setIcon(me.get("avatarUrl").toString());
                    }
                    // umsMember.setGender(Integer.parseInt(me.get("gender")));
                    umsMember.setNickname(me.get("nickName").toString());
                    umsMember.setGender((Integer) me.get("gender"));
                }

                umsMember.setHistoryIntegration(0);
                umsMember.setWeixinOpenid(sessionData.getString("openid"));

                memberMapper.insert(umsMember);
                token = jwtTokenUtil.generateToken(umsMember.getUsername());
                resultObj.put("userId", umsMember.getId());
                resultObj.put("userInfo", umsMember);
                addIntegration(umsMember.getId(), regJifen, 1, "注册添加积分", AllEnum.ChangeSource.register.code(), umsMember.getUsername());

            } else {
                addIntegration(userVo.getId(), logginJifen, 1, "登录添加积分", AllEnum.ChangeSource.login.code(), userVo.getUsername());

                token = jwtTokenUtil.generateToken(userVo.getUsername());
                resultObj.put("userId", userVo.getId());
                resultObj.put("userInfo", userVo);
            }


            if (StringUtils.isEmpty(token)) {
                throw new ApiMallPlusException("登录失败");
            }
            resultObj.put("tokenHead", tokenHead);
            resultObj.put("token", token);


            return new CommonResult().success(resultObj);
        } catch (ApiMallPlusException e) {
            e.printStackTrace();
            throw new ApiMallPlusException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiMallPlusException(e.getMessage());
        }

    }


    @Override
    public String refreshToken(String oldToken) {
        String token = oldToken.substring(tokenHead.length());
        if (jwtTokenUtil.canRefresh(token)) {
            return jwtTokenUtil.refreshToken(token);
        }
        return null;
    }


    @Override
    public Map<String, Object> loginByCode(String phone, String authCode) {
        Map<String, Object> tokenMap = new HashMap<>();
        String token = null;
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(phone);

            //验证验证码
            if (!verifyAuthCode(authCode, phone)) {
                throw new ApiMallPlusException("验证码错误");
            }
            UmsMember member = this.getByUsername(phone);
if (member==null || member.getId()<1){
    throw new ApiMallPlusException("用户不存在");

}
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
            tokenMap.put("userInfo", member);
            addIntegration(member.getId(), logginJifen, 1, "登录添加积分", AllEnum.ChangeSource.login.code(), member.getUsername());
        } catch (AuthenticationException e) {
            LOGGER.warn("登录异常:{}", e.getMessage());

        }
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);

        return tokenMap;
    }

    @Override
    public Map<String, Object> login(String username, String password) {

        Map<String, Object> tokenMap = new HashMap<>();
        String token = null;
        try {
            MemberDetails userDetails = (MemberDetails) userDetailsService.loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            UmsMember member = this.getByUsername(username);
            //验证验证码
           /* if (!verifyAuthCode(user.getCode(), member.getPhone())) {
                throw  new ApiMallPlusException("验证码错误");
            }*/

            //   Authentication authentication = authenticationManager.authenticate(authenticationToken);
            addIntegration(member.getId(), logginJifen, 1, "登录添加积分", AllEnum.ChangeSource.login.code(), member.getUsername());
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
            tokenMap.put("userInfo", member);
        } catch (Exception e) {
            LOGGER.warn("登录异常:{}", e.getMessage());

        }

        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);

        return tokenMap;

    }

    @Override
    public Object initMemberRedis() {
        List<UmsMember> list = memberMapper.selectList(new QueryWrapper<>());
        for (UmsMember member : list) {
            redisService.set(String.format(Rediskey.MEMBER, member.getUsername()), JsonUtils.objectToJson(member));
        }
        return 1;
    }

}

