package com.secrete.book.function;

import java.util.EventListener;

/**
 * LoginListener
 *
 * @Author Alex_Bao
 * @create 2018-05-03
 * create by IntelliJ IDEA
 */
public interface LoginListener<E extends LoginEvent> extends EventListener {

    /**
     * listen is login
     *
     * @param loginEvent
     */
    boolean onLoginEvent(E loginEvent);
}
