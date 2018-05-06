package com.secrete.book.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * ResourceUtil
 *
 * @Author Alex_Bao
 * @create 2018-04-15
 * create by IntelliJ IDEA
 */
public class ResourceUtil {

    /**
     * getURL
     *
     * @param resourceLocation
     * @return
     * @throws FileNotFoundException
     */
    public static URL getURL(String resourceLocation) throws FileNotFoundException {
        if (resourceLocation == null || resourceLocation.length() <= 0) {
            System.out.println("resourceLocation must not be null");
            return null;
        }
        if (resourceLocation.startsWith("classpath:")) {
            String path = resourceLocation.substring("classpath:".length());
            ClassLoader cl = ResourceUtil.getDefaultClassLoader();
            URL url = cl != null ? cl.getResource(path) : ClassLoader.getSystemResource(path);
            if (url == null) {
                String description = "class path resource [" + path + "]";
                throw new FileNotFoundException(description + " cannot be resolved to URL because it does not exist");
            } else {
                return url;
            }
        } else {
            try {
                return new URL(resourceLocation);
            } catch (MalformedURLException var6) {
                try {
                    return (new File(resourceLocation)).toURI().toURL();
                } catch (MalformedURLException var5) {
                    throw new FileNotFoundException("Resource location [" + resourceLocation + "] is neither a URL not a well-formed file path");
                }
            }
        }
    }


    /**
     * getFile
     *
     * @param resourceLocation
     * @return
     * @throws FileNotFoundException
     */
    public static File getFile(String resourceLocation){
        try{
            if (resourceLocation == null || resourceLocation.length() <= 0) {
                System.out.println("resourceLocation must not be null");
                return null;
            }
            if (resourceLocation.startsWith("classpath:")) {
                String path = resourceLocation.substring("classpath:".length());
                String description = "class path resource [" + path + "]";
                ClassLoader cl = ResourceUtil.getDefaultClassLoader();
                URL url = cl != null ? cl.getResource(path) : ClassLoader.getSystemResource(path);
                if (url == null) {
                    throw new FileNotFoundException(description + " cannot be resolved to absolute file path " + "because it does not reside in the file system");
                } else {
                    return getFile(url, description);
                }
            } else {
                try {
                    return getFile(new URL(resourceLocation));
                } catch (MalformedURLException var5) {
                    return new File(resourceLocation);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * get File
     *
     * @param resourceUrl
     * @param description
     * @return
     * @throws FileNotFoundException
     */
    public static File getFile(URL resourceUrl, String description) throws FileNotFoundException {
        if (resourceUrl == null) {
            System.out.println("Resource URL must not be null");
            return null;
        }
        if (!"file".equals(resourceUrl.getProtocol())) {
            throw new FileNotFoundException(description + " cannot be resolved to absolute file path " + "because it does not reside in the file system: " + resourceUrl);
        } else {
            try {
                return new File(toURI(resourceUrl).getSchemeSpecificPart());
            } catch (URISyntaxException var3) {
                return new File(resourceUrl.getFile());
            }
        }
    }

    /**
     * get File
     *
     * @param resourceUrl
     * @return
     * @throws FileNotFoundException
     */
    public static File getFile(URL resourceUrl) throws FileNotFoundException {
        return getFile(resourceUrl, "URL");
    }


    /**
     * to URI
     *
     * @param url
     * @return
     * @throws URISyntaxException
     */
    public static URI toURI(URL url) throws URISyntaxException {
        return toURI(url.toString());
    }

    /**
     * to URI
     *
     * @param location
     * @return
     * @throws URISyntaxException
     */
    public static URI toURI(String location) throws URISyntaxException {
        return new URI(ResourceUtil.replace(location, " ", "%20"));
    }

    /**
     * replace for string
     *
     * @param inString
     * @param oldPattern
     * @param newPattern
     * @return
     */
    public static String replace(String inString, String oldPattern, String newPattern) {
        if (hasLength(inString) && hasLength(oldPattern) && newPattern != null) {
            StringBuilder sb = new StringBuilder();
            int pos = 0;
            int index = inString.indexOf(oldPattern);

            for(int patLen = oldPattern.length(); index >= 0; index = inString.indexOf(oldPattern, pos)) {
                sb.append(inString.substring(pos, index));
                sb.append(newPattern);
                pos = index + patLen;
            }

            sb.append(inString.substring(pos));
            return sb.toString();
        } else {
            return inString;
        }
    }

    /**
     * has length for string
     *
     * @param str
     * @return
     */
    public static boolean hasLength(String str) {
        return hasLength((CharSequence)str);
    }

    /**
     * has length for char
     * @param str
     * @return
     */
    public static boolean hasLength(CharSequence str) {
        return str != null && str.length() > 0;
    }

    /**
     * get defaultClassLoader
     *
     * @return
     */
    public static ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;

        try {
            cl = Thread.currentThread().getContextClassLoader();
        } catch (Throwable var3) {
            ;
        }

        if (cl == null) {
            cl = ResourceUtil.class.getClassLoader();
            if (cl == null) {
                try {
                    cl = ClassLoader.getSystemClassLoader();
                } catch (Throwable var2) {
                    ;
                }
            }
        }

        return cl;
    }
}
