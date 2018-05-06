package com.secrete.book.util;

import sun.security.provider.MD5;

import java.util.UUID;

/**
 * PasswordGenerator
 *
 * @Author Alex_Bao
 * @create 2018-05-06
 * create by IntelliJ IDEA
 */
public class PasswordGenerator {


    /**
     * generate password.
     *
     * @return
     */
    public static String generatePassword(){
        String uuid = UUID.randomUUID().toString().substring(19).replace("-","");
        System.out.println(uuid);
        return uuid;
    }


    /**
     *  simple test
     */
    public static void main(String[] args){
        for(int i = 0 ; i < 100 ; i++){
            generatePassword();
        }
    }
}
