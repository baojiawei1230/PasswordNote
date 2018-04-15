package com.screte.book.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * SecreteBook
 *
 * 密码本对象
 *
 * @Author Alex_Bao
 * @create 2018-04-14
 * create by IntelliJ IDEA
 */
public class SecreteBook {

    /**
     * 网站名称
     */
    private StringProperty siteName;
    /**
     * 用户名
     */
    private StringProperty userName;
    /**
     * 密码
     */
    private StringProperty password;
    /**
     * 网站地址
     */
    private StringProperty siteAddress;

    public SecreteBook() {
        this.siteAddress = new SimpleStringProperty("");
        this.userName = new SimpleStringProperty("");
        this.password = new SimpleStringProperty("");
        this.siteName = new SimpleStringProperty("");
    }

    public SecreteBook(String userName, String password) {
        this.siteAddress = new SimpleStringProperty("");
        this.userName = new SimpleStringProperty(userName);
        this.password = new SimpleStringProperty(password);
        this.siteName = new SimpleStringProperty("");
    }

    public String getSiteName() {
        return siteName.get();
    }

    public StringProperty siteNameProperty() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName.set(siteName);
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

    public String getSiteAddress() {
        return siteAddress.get();
    }

    public StringProperty siteAddressProperty() {
        return siteAddress;
    }

    public void setSiteAddress(String siteAddress) {
        this.siteAddress.set(siteAddress);
    }
}
