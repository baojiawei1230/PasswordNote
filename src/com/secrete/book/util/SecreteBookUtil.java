package com.secrete.book.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

/**
 * SecreteBookUtil
 *
 * @Author Alex_Bao
 * @create 2018-04-15
 * create by IntelliJ IDEA
 */
public class SecreteBookUtil {

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
     * load information from file
     *
     * @param location
     * @return
     * @throws FileNotFoundException
     */
    public static File loadInformationFromFile(String location) {
        if(location == null){
            location = FILE_PATH;
        }
        try {
            URL url = ResourceUtil.getURL(location);
            return ResourceUtil.getFile(url);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
