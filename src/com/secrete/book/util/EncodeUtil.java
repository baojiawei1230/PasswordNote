package com.secrete.book.util;

/**
 * EncodeUtil
 *
 * @Author Alex_Bao
 * @create 2018-05-17
 * create by IntelliJ IDEA
 */
public class EncodeUtil {

    /**
     * ENCODE_INDEX
     */
    private static final String PREFIX_INDEX = "PREFIX_INDEX";
    /**
     * SUFFIX_INDEX
     */
    private static final String SUFFIX_INDEX = "SUFFIX_INDEX";



    public static String encriptCode(String code){
        if(code == null || "".equals(code.trim())){
            return null;
        }
        final String newCode = PREFIX_INDEX + code + SUFFIX_INDEX;

        return "";

    }


}
