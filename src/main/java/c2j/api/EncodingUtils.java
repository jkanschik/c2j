package c2j.api;

import java.nio.charset.Charset;

/**
 * Created by kanschje on 09.03.2017.
 */
public class EncodingUtils {

  public static final Charset CHARSET_EBCDIC = Charset.forName("CP1047");

  public static byte[] decodeString(String str) {
    return str.getBytes(CHARSET_EBCDIC);
  }

  public static String encodeString(byte[] bytes) {
    return new String(bytes, CHARSET_EBCDIC);
  }



}
