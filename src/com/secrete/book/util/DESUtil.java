package com.secrete.book.util;

import com.secrete.book.model.SecreteBook;
import javafx.collections.ObservableList;
import sample.MainApplication;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.security.Key;

/**
 * DES加密工具类
 *
 * @Author Alex_Bao
 * @create 2018-05-16
 * create by IntelliJ IDEA
 */
public class DESUtil {

    /**
     * can not construct.
     */
    private DESUtil() {}

    /**
     * ROOT PATH
     */
    private static final String ROOT_PATH = System.getProperty("user.home");
    /**
     * PASS_NOT
     */
    private static final String PASS_NOTE_PATH = ROOT_PATH + "/PassNote/file";
    /**
     * FILE_PATH
     */
    private static final String FILE_PATH = PASS_NOTE_PATH + "/password.xml";

    /**
     * prefix index
     */
    private static final String PREFIX_INDEX = "PREFIX_INDEX";

    /**
     *  suffix index
     */
    private static final String SUFFIX_INDEX = "SUFFIX_INDEX";

    /**
     * index length
     */
    private static final int INDEX_LENGTH = PREFIX_INDEX.length();


    /**
     * before encrypt
     *
     * @return
     */
    private static String beforeEncrypt(String code){
        if(code == null || "".equals(code.trim())){
            return null;
        }
        String enCode = PREFIX_INDEX + code + SUFFIX_INDEX;
        return enCode;
    }

    /**
     * after decrypt
     *
     * @param code
     * @return
     */
    private static String afterDecrypt(String code){
        if(code == null || "".equals(code.trim())){
            return null;
        }
        if(code.length() <= 2 * INDEX_LENGTH){
            return null;
        }
        String deCode = code.substring(INDEX_LENGTH).substring(0 , code.substring(INDEX_LENGTH).length() - INDEX_LENGTH);
        return deCode;
    }


    /**
     * 字符串加密
     * <p>
     *     对字符串进行DES加密,返回BASE64编码的加密字符串.
     * </p>
     *
     * @param str 需要加密的字符串
     * @return
     */
    public static String getEncryptString(String str){
        BASE64Encoder base64Encoder = new BASE64Encoder();
        try {
            str = beforeEncrypt(str);
            byte[] strBytes = str.getBytes();
            return base64Encoder.encode(strBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 字符串解密
     * <p>
     *     对BASE64编码的加密字符串进行解密,返回解密后的字符串.
     * </p>
     * @param str
     * @return
     */
    public static String getDecryptString(String str){
        BASE64Decoder base64Decoder = new BASE64Decoder();
        try{
            byte[] strBytes = base64Decoder.decodeBuffer(str);
            String deCode = new String(strBytes, "UTF8");
            return afterDecrypt(deCode);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /** 测试main类 **/
    public static void main(String[] args){
//        String str = "baojiawei";
//        String encryptString = DESUtil.getEncryptString(str);
//        System.out.println("encryptString "+encryptString);
//        String encrypt = DESUtil.getDecryptString(encryptString);
//        System.out.println("encrypt : "+encrypt);

        MainApplication mainApplication = new MainApplication();
        ObservableList<SecreteBook> secreteBooks = mainApplication.loadSecreteBookDataFromFile(new File(FILE_PATH));
        if(secreteBooks != null && secreteBooks.size() > 0){
            for(SecreteBook book : secreteBooks){
                System.out.println("password :"+book.getPassword());
                System.out.println("encrypt password : "+DESUtil.getEncryptString(book.getPassword()));
                //System.out.println("decrypt password : "+DESUtil.getDecryptString(book.getPassword()));
            }
        }
    }

}