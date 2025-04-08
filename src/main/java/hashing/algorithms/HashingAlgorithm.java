package hashing.algorithms;

import hashing.Entry;

public interface HashingAlgorithm {
  Entry hash(String input);
}
