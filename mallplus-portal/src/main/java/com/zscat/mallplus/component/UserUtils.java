package com.zscat.mallplus.component;

import com.zscat.mallplus.ums.entity.UmsMember;
import com.zscat.mallplus.vo.MemberDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Auther: shenzhuan
 * @Date: 2019/3/30 16:26
 * @Description:
 */
public class UserUtils {
    public static UmsMember getCurrentMember() {
        try {
            SecurityContext ctx = SecurityContextHolder.getContext();
            Authentication auth = ctx.getAuthentication();
            MemberDetails memberDetails = (MemberDetails) auth.getPrincipal();
            return memberDetails.getUmsMember();
        } catch (Exception e) {
            return new UmsMember();
        }
    }
}
