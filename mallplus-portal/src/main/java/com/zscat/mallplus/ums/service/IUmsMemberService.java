package com.zscat.mallplus.ums.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zscat.mallplus.ums.entity.UmsMember;
import com.zscat.mallplus.utils.CommonResult;
import com.zscat.mallplus.vo.AppletLoginParam;
import com.zscat.mallplus.vo.AppletLoginnewParam;
import com.zscat.mallplus.vo.SmsCode;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
public interface IUmsMemberService extends IService<UmsMember> {

    void updataMemberOrderInfo();

    /**
     * 获取小程序openid
     *
     * @param req
     * @return
     */
    Object getAppletOpenId(AppletLoginParam req);


    /**
     * 小程序 登录注册
     *
     * @param req
     * @return
     */
    Object loginByWeixin(AppletLoginParam req);

    Object loginByWeixin1(AppletLoginParam req);

    /**
     * 根据用户名获取会员
     */
    UmsMember getByUsername(String username);

    /**
     * 根据会员编号获取会员
     */
    UmsMember getById(Long id);

    /**
     * 用户注册
     */
    @Transactional
    CommonResult register(String phone, String password, String confim, String authCode, String invitecode);

    /**
     * 生成验证码
     */
    CommonResult generateAuthCode(String telephone);


    /**
     * 修改密码
     */
    @Transactional
    CommonResult updatePassword(String telephone, String password, String authCode);


    /**
     * 根据会员id修改会员积分
     */
    void updateIntegration(Long id, Integer integration);


    UmsMember queryByOpenId(String openId);


    Map<String, Object> login(String username, String password);

    String refreshToken(String token);

    Object register(UmsMember umsMember);

    SmsCode generateCode(String phone);

    Map<String, Object> loginByCode(String phone, String authCode);

    Object simpleReg(String phone, String password, String confimpassword, String invitecode);

    /**
     * 添加余额记录 并更新用户余额
     *
     * @param id
     * @param integration
     */
    void addBlance(Long id, Integer integration, int type, String note);

    /**
     * 添加积分记录 并更新用户积分
     *
     * @param id
     * @param integration
     */
    void addIntegration(Long id, Integer integration, int changeType, String note, int sourceType, String operateMan);

    Map<String, Object> appLogin(String openid, Integer sex, String headimgurl, String unionid, String nickname, String city, Integer source);


    Object initMemberRedis();

    Object getCurrentMember();

    UmsMember getNewCurrentMember();

    /**
     * openId，采用 网页授权获取 access_token API：SnsAccessTokenApi获取
     *
     * @return
     */
    Object webLogin(String wxH5Appid, String wxH5Secret, String code);


    /**
     * 小程序获取的手机加密信息进行解密
     *
     * @param openid
     * @param keyStr
     * @param ivStr
     * @param encDataStr
     * @return
     */
    String getWxPhone(String openid, String keyStr, String ivStr, String encDataStr);

    Object resetPassword(String phone, String password, String confimpassword, String authCode);

    Object loginByWeixin2(AppletLoginnewParam param);
}

