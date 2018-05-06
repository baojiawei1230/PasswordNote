package com.secrete.book.function;

/**
 * UserLoginEvent
 *
 * @Author Alex_Bao
 * @create 2018-05-03
 * create by IntelliJ IDEA
 */
public class UserLoginEvent extends LoginEvent{

    /**
     * UserLoginSource
     */
    private UserLoginSource userLoginSource;
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public UserLoginEvent(Object source) {
        super(source);
        if(source instanceof UserLoginSource){
            this.userLoginSource = (UserLoginSource) source;
        }
    }

    @Override
    public UserLoginSource getSource() {
        return this.userLoginSource;
    }

}
