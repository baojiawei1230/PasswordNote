package com.secrete.book.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * UserInfoWrapper
 *
 * @Author Alex_Bao
 * @create 2018-05-03
 * create by IntelliJ IDEA
 */
@XmlRootElement
public class UserInfoWrapper {

    /**
     * user information
     */
    private UserInfo userInfo;

    @XmlElement(name = "userInfo")
    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
