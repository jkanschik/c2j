package c2j.api;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by kanschje on 09.03.2017.
 */
public class AlphaNumericCobolRecord {
    protected byte[] value;
    protected int size;

  public AlphaNumericCobolRecord() {
    this.size = 20;
    this.value = new byte[this.size];
  }

  public int getSize() {
    return size;
  }

  public byte[] getValue() {
    return value;
  }

  public void write(byte[] src) {
    write(src, 0);
  }

  public void write(byte[] src, int offset) {
    System.arraycopy(src, 0, value, offset, src.length);
  }

  public ReferenceModification getRefMod(int from) {
    return new ReferenceModification(this, from);
  }

  public ReferenceModification getRefMod(int from, int size) {
    return new ReferenceModification(this, from, size);
  }

  @Override
  public String toString() {
    return EncodingUtils.encodeString(this.value);
  }

  public class ReferenceModification extends AlphaNumericCobolRecord {
    private int offset;
    private AlphaNumericCobolRecord parent;

    public ReferenceModification(AlphaNumericCobolRecord parent, int offset) {
      this.parent = parent;
      this.offset = offset;
      this.size = parent.getSize() - offset;
      this.value = null;
    }

    public ReferenceModification(AlphaNumericCobolRecord parent, int offset, int size) {
      this.parent = parent;
      this.offset = offset;
      this.size = size;
      this.value = null;
    }

    public void write(byte[] src) {
      parent.write(src, offset);
    }

    public byte[] getValue() {
      return Arrays.copyOfRange(parent.getValue(), offset, offset + size);
    }
  }
}
