import hashing.AlgorithmManager;
import hashing.Entry;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
  private static final String OUTPUT_FILE_EXTENSION = ".csv";
  private static final String INPUT_STRING_TYPE = "Password";
  private final List<String> inputs = new ArrayList<>();
  private final CsvBuilder csvBuilder = new CsvBuilder();
  private String inputFile;
  private String outputFile;

  public static void main(String[] args) {
    System.out.println("Welcome to Hashify!\n");
    Main main = new Main();
    main.start(args);
  }

  private void start(String[] args) {
    parseInputs(args);
    checkExtension();
    processFile();
    performHashes();
  }

  private void parseInputs(String[] args) {
    switch (args.length) {
      case 0:
        promptForFiles();
        break;
      case 2:
        inputFile = verifyFile(args[0], true);
        outputFile = verifyFile(args[1], false);
        break;
      default:
        System.out.println("Usage: java -jar hashify.jar <inputFile> <outputFile>");
        throw new RuntimeException();
    }
  }

  private void promptForFiles() {
    try (Scanner sc = new Scanner(System.in, Charset.defaultCharset())) {
      System.out.print("Enter input file path: ");
      inputFile = verifyFile(sc.nextLine(), true);
      System.out.print("Enter output file path: ");
      outputFile = verifyFile(sc.nextLine(), false);
    }
  }

  private String verifyFile(String filePath, boolean shouldExist) {
    File file = new File(filePath);
    String fullPath;
    try {
      fullPath = file.getCanonicalPath();
    } catch (IOException e) {
      fullPath = file.getAbsolutePath();
    }
    if (shouldExist && !file.exists()) {
      System.err.println("File does not exist: " + fullPath);
      throw new RuntimeException();
    } else if (!shouldExist && file.exists()) {
      System.err.println("File already exists: " + fullPath);
      throw new RuntimeException();
    } else if (file.isDirectory()) {
      System.err.println("Path is a directory: " + fullPath);
      throw new RuntimeException();
    } else if (shouldExist && !file.canRead()) {
      System.err.println("Cannot read file: " + fullPath);
      throw new RuntimeException();
    }
    return fullPath;
  }

  private void checkExtension() {
    if (!outputFile.endsWith(OUTPUT_FILE_EXTENSION)) {
      System.err.println("Output file must have " + OUTPUT_FILE_EXTENSION + " extension");
      throw new RuntimeException();
    }
    csvBuilder.setOutputFile(outputFile, INPUT_STRING_TYPE);
  }

  private void processFile() {
    try (Scanner sc = new Scanner(new File(inputFile), Charset.defaultCharset())) {
      while (sc.hasNextLine()) {
        String line = sc.nextLine();
        if (!line.isEmpty()) {
          inputs.add(line);
        }
      }
    } catch (Exception e) {
      System.err.println("Error reading input file: " + e.getMessage());
      throw new RuntimeException();
    }
  }

  private void performHashes() {
    System.out.println("Hashing contents of " + inputFile);
    long startTime = System.currentTimeMillis();
    inputs.forEach((input) -> {
      List<Entry> entries = AlgorithmManager.performHash(input);
      csvBuilder.addRow(input, entries);
    });
    csvBuilder.close();
    long timeElapsed = (System.currentTimeMillis() - startTime) / 1000;
    System.out.println("Finished hashing in " + timeElapsed + " seconds!");
    System.out.println("Output written to " + outputFile);
  }
}