package com.secrete.book.function;

import java.util.EventObject;

/**
 * LoginEvent
 *
 * @Author Alex_Bao
 * @create 2018-05-03
 * create by IntelliJ IDEA
 */
public abstract class LoginEvent extends EventObject{

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public LoginEvent(Object source) {
        super(source);
    }

}
