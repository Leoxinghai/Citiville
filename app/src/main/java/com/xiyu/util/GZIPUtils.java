package com.xiyu.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

/**
 * Created by User on 2017-03-22.
 */
public class GZIPUtils {
    public static String decompress(byte[] buff) throws Exception {
        if (buff == null || buff.length == 0) {
            return null;
        }
        GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(buff));
        BufferedReader bf = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
        String outStr = "";
        String line;
        while ((line=bf.readLine())!=null) {
            outStr += line;
        }
        return outStr;
    }
}
