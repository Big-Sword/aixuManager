package com.bao.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by hanruofei on 16/12/6.
 */
public class OrderUtils {
    private static SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式

    public static String generatorOrderNum(){
        return df.format(new Date()) + genRandomLetter(2);
    }

    private static String genRandomLetter(int length){
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
