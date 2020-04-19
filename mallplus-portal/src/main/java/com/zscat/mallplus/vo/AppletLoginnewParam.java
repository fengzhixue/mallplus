package com.zscat.mallplus.vo;

import lombok.Data;

/**
 * @Auther: shenzhuan
 * @Date: 2019/6/18 14:51
 * @Description:
 */
@Data
public class AppletLoginnewParam {

    private String code;

    private String encryptedData;
    private String iv;

    private String signature;

    private UserInfo userInfo;
    private String cloudID;


}
