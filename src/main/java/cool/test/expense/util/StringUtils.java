package cool.test.expense.util;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@Slf4j
public class StringUtils {

    public static String compress(String str) throws Exception {
        if (str == null || str.length() == 0) {
            return str;
        }
        log.debug("String length : " + str.length());
        @Cleanup ByteArrayOutputStream obj=new ByteArrayOutputStream();
        @Cleanup GZIPOutputStream gzip = new GZIPOutputStream(obj);
        gzip.write(str.getBytes("UTF-8"));
        String outStr = obj.toString("UTF-8");
        log.debug("Output String length : " + outStr.length());
        return outStr;
    }

    public static String decompress(String str) throws Exception {
        if (str == null || str.length() == 0) {
            return str;
        }
        log.debug("Input String length : " + str.length());
        @Cleanup GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(str.getBytes("UTF-8")));
        @Cleanup BufferedReader bf = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
        String outStr = "";
        String line;
        while ((line=bf.readLine())!=null) {
            outStr += line;
        }
        log.debug("Output String lenght : " + outStr.length());
        return outStr;
    }

    public static String hashPassword(String password, String seed) throws NoSuchAlgorithmException, IOException {
        byte[] data = password.getBytes(StandardCharsets.UTF_8);
        MessageDigest digester = MessageDigest.getInstance("SHA-256");
        digester.update(data);
        digester.update(seed.getBytes(StandardCharsets.UTF_8));

        return Base64.getEncoder().encodeToString(digester.digest());
    }
}
