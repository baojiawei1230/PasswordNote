package com.secrete.book.function;

import com.secrete.book.model.UserInfo;

/**
 * CheckLoginUtil
 *
 * @Author Alex_Bao
 * @create 2018-05-05
 * create by IntelliJ IDEA
 */
public class CheckLoginUtil {

    /**
     * check is login
     *
     * @return
     */
    public static boolean checkIsLogin(){
        /** get user info **/
        UserInfo userInfo = UserLoginContext.getUserInfo();
        if(userInfo == null){
            return false;
        }
        return true;
    }


}
