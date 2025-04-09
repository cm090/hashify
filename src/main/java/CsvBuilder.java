import com.opencsv.CSVWriter;
import hashing.Entry;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public class CsvBuilder {
  private CSVWriter csvWriter;
  private String inputTitle;
  private boolean hasHeader = false;

  void addRow(String input, List<Entry> entries) {
    if (!hasHeader) {
      String[] header = new String[entries.size() + 1];
      header[0] = inputTitle;
      for (int i = 0; i < entries.size(); i++) {
        header[i + 1] = entries.get(i).name();
      }
      csvWriter.writeNext(header);
      hasHeader = true;
    }
    String[] row = new String[entries.size() + 1];
    row[0] = input;
    for (int i = 0; i < entries.size(); i++) {
      row[i + 1] = entries.get(i).hash();
    }
    csvWriter.writeNext(row);
  }

  void close() {
    try {
      csvWriter.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  void setOutputFile(String outputFile, String inputTitle) {
    try {
      csvWriter = new CSVWriter(
          new FileWriter(new File(outputFile).toPath().toString(), Charset.defaultCharset()));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    this.inputTitle = inputTitle;
  }
}
