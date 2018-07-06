package com.secrete.book.function;

import com.secrete.book.model.UserInfo;

/**
 * UserLoginContext
 *
 * @Author Alex_Bao
 * @create 2018-05-05
 * create by IntelliJ IDEA
 */
public final class UserLoginContext {

    /**
     * live time for need to be input password
     */
    private static final long LIVE_TIME = 1 * 60 * 1000;

    /**
     * user info context
     */
    public static final ThreadLocal<UserInfo> userInfoContext = new ThreadLocal<>();

    /**
     * start time context
     */
    private static final ThreadLocal<Long> startTimeContext  = ThreadLocal.withInitial(() -> System.currentTimeMillis());

    /**
     * private constructor
     */
    private UserLoginContext() { }

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
        //long gapTime = System.currentTimeMillis() - startTimeContext.get();
        //System.out.println(gapTime);
        if(userInfoContext.get() == null || (System.currentTimeMillis() - startTimeContext.get() > LIVE_TIME)){
            return null;
        }
        return userInfoContext.get();
    }


}
