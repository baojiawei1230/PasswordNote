package com.secrete.book.function;

import com.secrete.book.model.UserInfo;
import com.secrete.book.util.ResourceUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * UserLoginSource
 *
 * @Author Alex_Bao
 * @create 2018-05-03
 * create by IntelliJ IDEA
 */
public class UserLoginSource {

    /**
     * 监听器集合.
     */
    private Set<LoginListener> listenerSet;

    /**
     * 用户信息
     */
    private UserInfo userInfo;

    private static final String FILE_PATH = "";

    public UserLoginSource(Set<LoginListener> listenerSet, UserInfo userInfo) {
        this.listenerSet = listenerSet;
        this.userInfo = userInfo;
    }

    public UserLoginSource() {
    }

    public Set<LoginListener> getListenerSet() {
        return listenerSet;
    }

    public void setListenerSet(Set<LoginListener> listenerSet) {
        this.listenerSet = listenerSet;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    /**
     * add Listener
     *
     * @param loginListener
     */
    public void addListener(LoginListener loginListener){
        if(listenerSet == null){
            listenerSet = new HashSet<>();
        }
        listenerSet.add(loginListener);
    }

    /**
     * 唤醒监听器
     *
     */
    protected void notifyAllListener(){
        Iterator<LoginListener> iterator = this.listenerSet.iterator();
        while(iterator.hasNext()){
            LoginListener loginListener = iterator.next();
            loginListener.onLoginEvent(new UserLoginEvent(this));
        }
    }

    /**
     * 触发事件
     */
    public void triggerNotifiers(){
        URL url = null;
        try {
            url = ResourceUtil.getURL(FILE_PATH);
            File file = ResourceUtil.getFile(url);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        this.notifyAllListener();
    }



}
