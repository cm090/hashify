package hashing.algorithms;

import static hashing.AlgorithmManager.bytesToHex;

import hashing.Entry;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 implements HashingAlgorithm {
  private static final String ALGORITHM_NAME = "MD5";

  @Override
  public Entry hash(String input) {
    try {
      byte[] hashValue =
          MessageDigest.getInstance("MD5").digest(input.getBytes(Charset.defaultCharset()));
      return new Entry(ALGORITHM_NAME, bytesToHex(hashValue));
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }
}
