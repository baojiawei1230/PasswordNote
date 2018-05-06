package com.secrete.book.function;

import com.secrete.book.model.UserInfo;

/**
 * UserLoginListenerImpl
 *
 * @Author Alex_Bao
 * @create 2018-05-03
 * create by IntelliJ IDEA
 */
public class UserLoginListenerImpl implements LoginListener<UserLoginEvent>{

    @Override
    public boolean onLoginEvent(UserLoginEvent loginEvent) {
        if(loginEvent != null){
            UserInfo userInfo = loginEvent.getSource().getUserInfo();
            //TODO 从配置文件读取用户名和密码
            return userInfo.getUserName().equals("baojiawei") && userInfo.getPassword().equals("baojiawei");
        }
        return false;
    }
}
