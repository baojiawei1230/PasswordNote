package com.screte.book.util;

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
     * load information from file
     *
     * @param location
     * @return
     * @throws FileNotFoundException
     */
    public static File loadInformationFromFile(String location) throws FileNotFoundException {
        if(location == null){
            location = "/Users/Alex_Bao/Documents/GitWorkSpace/ScreteBook/out/production/ScreteBook/com/screte/book/file/password.xml";
        }
        URL url = ResourceUtil.getURL(location);
        return ResourceUtil.getFile(url);
    }

}
