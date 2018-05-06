package com.secrete.book.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * UserInfo
 *
 * @Author Alex_Bao
 * @create 2018-05-03
 * create by IntelliJ IDEA
 */
public class UserInfo {

    /**
     * 用户名
     */
    private StringProperty userName;
    /**
     * 密码
     */
    private StringProperty password;

    public UserInfo() {
        this.userName = new SimpleStringProperty("");
        this.password = new SimpleStringProperty("");
    }

    public UserInfo(String userName, String password) {
        this.userName = new SimpleStringProperty(userName);
        this.password = new SimpleStringProperty(password);
    }

    public String getUserName() {
        return userName.get();
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }
}
