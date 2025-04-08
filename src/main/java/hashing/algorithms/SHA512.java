package hashing.algorithms;

import static hashing.AlgorithmManager.bytesToHex;

import hashing.Entry;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA512 implements HashingAlgorithm {
  private static final String ALGORITHM_NAME = "SHA-512";

  @Override
  public Entry hash(String input) {
    try {
      byte[] hashValue = MessageDigest.getInstance("SHA-512").digest(input.getBytes());
      return new Entry(ALGORITHM_NAME, bytesToHex(hashValue));
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }
}
