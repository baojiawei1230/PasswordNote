package com.secrete.book.function;

import com.secrete.book.model.UserInfo;

/**
 * UserLoginContext
 *
 * @Author Alex_Bao
 * @create 2018-05-05
 * create by IntelliJ IDEA
 */
public class UserLoginContext {

    /**
     * user info context
     */
    public static final ThreadLocal<UserInfo> userInfoContext = new ThreadLocal<>();

    /**
     * start time context
     */
    private static final ThreadLocal<Long> startTimeContext  = new ThreadLocal<Long>(){
        @Override
        protected Long initialValue() {
            return System.currentTimeMillis();
        }
    };

    private UserLoginContext() {
    }

    /**
     * set start time
     *
     * @param currentTime
     */
    private static void setStartTime(Long currentTime){
        startTimeContext.set(currentTime);
    }

    /**
     * set user info
     *
     * @param userInfo
     */
    public static void setUserInfo(UserInfo userInfo){
        userInfoContext.set(userInfo);
        startTimeContext.set(System.currentTimeMillis());
    }


    /**
     * get user info from threadLocal
     *
     * @return
     */
    public static UserInfo getUserInfo(){
        long gapTime = System.currentTimeMillis() - startTimeContext.get();
        System.out.println(gapTime);
        if(userInfoContext.get() == null || (System.currentTimeMillis() - startTimeContext.get() > 1 * 30 * 1000)){
            return null;
        }
        return userInfoContext.get();
    }


}
