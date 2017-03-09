package c2j.api;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static c2j.api.EncodingUtils.decodeString;
import static c2j.api.EncodingUtils.encodeString;

/**
 * Created by kanschje on 09.03.2017.
 */
public class CobolStatements {

  public void move(AlphaNumericCobolRecord from, AlphaNumericCobolRecord to) {
    to.write(from.getValue());
  }

  public void move(String from, AlphaNumericCobolRecord to) {
    byte[] fromBytes = decodeString(from);
    to.write(fromBytes);
  }

  public void display(AlphaNumericCobolRecord record) {
    System.out.println(record);
  }

}
