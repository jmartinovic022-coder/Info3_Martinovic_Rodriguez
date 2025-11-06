import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * CSVParser simple helper used for the exercise. It returns a List of String[] where each array
 * contains the columns of a CSV line split by comma. This is a lightweight parser (does not
 * handle quoted commas) but is sufficient for the demo/testing data assumed here.
 */
public class CSVParser {
    public static List<String[]> parse(String path) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(path));
        List<String[]> rows = new ArrayList<>();
        for (String line : lines) {
            if (line == null) continue;
            line = line.trim();
            if (line.isEmpty()) continue;
            // Simple split by comma
            String[] cols = line.split(",");
            for (int i = 0; i < cols.length; i++) cols[i] = cols[i].trim();
            rows.add(cols);
        }
        return rows;
    }
}
