package hashing;

import hashing.algorithms.*;
import java.util.List;

public class AlgorithmManager {
  private static final List<HashingAlgorithm> ALGORITHMS =
      List.of(new MD5(), new SHA256(), new SHA512());

  public static List<Entry> performHash(String input) {
    return ALGORITHMS.stream().map(algorithm -> algorithm.hash(input)).toList();
  }

  public static String bytesToHex(byte[] bytes) {
    StringBuilder hexString = new StringBuilder();
    for (byte b : bytes) {
      String hex = Integer.toHexString(0xff & b);
      if (hex.length() == 1) {
        hexString.append('0');
      }
      hexString.append(hex);
    }
    return hexString.toString();
  }
}
