package com.secrete.book.util;

import com.secrete.book.model.SecreteBook;
import javafx.collections.ObservableList;
import sample.MainApplication;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.io.File;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * DES加密工具类
 *
 * @Author Alex_Bao
 * @create 2018-05-16
 * create by IntelliJ IDEA
 */
public class DESUtil {

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

    private static Key key;
    private static String KEY_STR = "PASS_NOTE";
    private static String INSTANCE_KEY = "DES";

    /** 初始化 **/
    static{
        try {
            KeyGenerator generator = KeyGenerator.getInstance("DES");
            generator.init(new SecureRandom(KEY_STR.getBytes()));
            key = generator.generateKey();
            generator = null;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
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
            byte[] strBytes = str.getBytes();
            Cipher cipher = Cipher.getInstance(INSTANCE_KEY);
            cipher.init(Cipher.ENCRYPT_MODE,key);
            byte[] encryptStrBytes = cipher.doFinal(strBytes);
            return base64Encoder.encode(encryptStrBytes);
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
            Cipher cipher = Cipher.getInstance(INSTANCE_KEY);
            cipher.init(Cipher.DECRYPT_MODE,key);
            byte[] decriptStrBytes = cipher.doFinal(strBytes);
            return new String(decriptStrBytes,"UTF8");
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /** 测试main类 **/
    public static void main(String[] args){
        String str = "baojiawei";
        String encryptString = DESUtil.getEncryptString(str);
        System.out.println("encryptString "+encryptString);
        String encrypt = DESUtil.getDecryptString(encryptString);
        System.out.println("encrypt : "+encrypt);

//        MainApplication mainApplication = new MainApplication();
//        ObservableList<SecreteBook> secreteBooks = mainApplication.loadSecreteBookDataFromFile(new File(FILE_PATH));
//        if(secreteBooks != null && secreteBooks.size() > 0){
//            for(SecreteBook book : secreteBooks){
//                System.out.println("password :"+book.getPassword());
//                System.out.println("encrypt password : "+DESUtil.getEncryptString(book.getPassword()));
//            }
//        }
    }

}