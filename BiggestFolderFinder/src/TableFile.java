import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TableFile {
//   private static String path;
    List<String> lines = new ArrayList<>();
    HashMap<String, Double> csvTable = new HashMap<>();

    public HashMap<String, Double> getCsvTable(String path) {
        {
            try {
                lines = Files.readAllLines(Paths.get(path));
                String firstLine = null;
                for (String line : lines) {
                    if(firstLine == null) {
                        firstLine = line;
                        continue;
                    }
                    //String[] tokens = line.split("\\t"); //табуляция
                    String[] tokens = line.split(","); //запятая
                    //игнорируем, если столбцов не восемь
                    if (tokens.length != 8) {
                        System.out.println("Wrong line: " + line);
                        continue;
                    }
                    double expense = Double.parseDouble(tokens[7]);
                    if(expense == 0) {
                        continue;
                    }
                    String paymentType = getPaymentType(tokens[5]);
                    if(!csvTable.containsKey(paymentType)) {
                        csvTable.put(paymentType, 0.);
                    }
                    csvTable.put(
                            paymentType,
                            csvTable.get(paymentType) + expense
                    );
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return this.csvTable;
    }

    private static String getPaymentType(String info) {
        String regex = "[^a-zA-Z0-9]([a-zA-Z0-9\\s]+)[0-9]{2}\\.[0-9]{2}\\.[0-9]{2}\\s[0-9]{2}\\.[0-9]{2}\\.[0-9]{2}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(info);
        return matcher.find() ? matcher.group(1).trim() : null;
    }

}