package com.secrete.book.util;

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
     * password length
     */
    private static final int PASSWORD_LENGTH = 16;

    /**
     * private constructor
     */
    private PasswordGenerator() {}

    /**
     * generate password.
     *
     * @return
     */
    public static String generateSimplePassword(){
        String uuid = UUID.randomUUID().toString().substring(19).replace("-","");
        System.out.println(uuid);
        return uuid;
    }


    /**
     *  simple test
     */
    public static void main(String[] args){
//        for(int i = 0 ; i < 100 ; i++){
//            generateSimplePassword();
//        }
        String password = getPassword(PASSWORD_LENGTH);
        System.out.println(password);
    }

    /**
     * generateComplexPassword
     *
     * @return password
     */
    public static String generateComplexPassword(){
        return getPassword(16);
    }


    /**
     * get password
     *
     * @param passwordLength
     * @return password of string
     */
    private static String getPassword(int passwordLength) {
        StringBuilder sb = new StringBuilder();
        int countLowerCase = 0;
        int countUpperCase = 0;
        int countDigit = 0;

        while (countLowerCase == 0 || countUpperCase == 0 || countDigit == 0) {
            int countLetter[] = generateToStringBuilder(sb, passwordLength);

            countDigit = countLetter[0];
            countLowerCase = countLetter[1];
            countUpperCase = countLetter[2];

            if (countLowerCase == 0 || countUpperCase == 0 || countDigit == 0)
                sb.delete(0, sb.length());
        }

        return sb.toString();
    }

    /**
     * generate to String builder
     *
     * @param sb string builder
     * @param passwordLength password length
     * @return
     */
    private static int[] generateToStringBuilder(StringBuilder sb, int passwordLength) {
        int countDigit = 0;
        int countLowerCase = 0;
        int countUpperCase = 0;
        while (sb.length() < passwordLength) {
            int i = getRandomInt(42, 122);//48-122 ==> 0 ~ z , 42-122 ==> * ~ z

            if (Character.isLetterOrDigit(i)) {
                if (Character.isDigit(i))
                    countDigit++;
                if (Character.isLowerCase(i))
                    countLowerCase++;
                if (Character.isUpperCase(i))
                    countUpperCase++;

                sb.append((char) i);
            }
        }
        return new int[]{countDigit, countLowerCase, countUpperCase};
    }

    /**
     * get random int
     *
     * @param min
     * @param max
     * @return
     */
    private static int getRandomInt(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

}
