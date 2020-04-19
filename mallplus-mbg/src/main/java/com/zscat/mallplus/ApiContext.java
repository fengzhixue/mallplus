package com.zscat.mallplus;

import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;

@Component
public class ApiContext {

    private static final String KEY_CURRENT_PROVIDER_ID = "KEY_CURRENT_PROVIDER_ID";
    /* public Long getCurrentProviderId() {

         return  threadLocal.get();
     }

     public void setCurrentProviderId(Long providerId) {
         threadLocal.set(providerId);
     }*/
    private static final Map<String, Object> mContext = Maps.newConcurrentMap();
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();
    // private static final Map<String, Object> mContext = Maps.newConcurrentMap();

    /* public Long getCurrentProviderId() {
         ConcurrentMap mContext=contextHolder.get();
         System.out.println("1="+mContext);

         if (mContext==null){
             return 0l;
         }
         System.out.println("2="+mContext.get(KEY_CURRENT_PROVIDER_ID));
         return (Long) mContext.get(KEY_CURRENT_PROVIDER_ID);
     }

     public void setCurrentProviderId(Long providerId) {
         ConcurrentMap mContext=contextHolder.get();
         if (mContext==null){
             mContext= Maps.newConcurrentMap();
         }
         mContext.put(KEY_CURRENT_PROVIDER_ID,providerId);

         System.out.println("3="+mContext);
         contextHolder.set(mContext);
     }*/
    private static ThreadLocal<ConcurrentMap> contextHolder = new ThreadLocal<>();

    public Long getCurrentProviderId() {
        return (Long) mContext.get(KEY_CURRENT_PROVIDER_ID);
    }

    public void setCurrentProviderId(Long providerId) {
        mContext.put(KEY_CURRENT_PROVIDER_ID, providerId);
    }
}
