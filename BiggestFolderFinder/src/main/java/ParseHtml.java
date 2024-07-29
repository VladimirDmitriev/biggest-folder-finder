import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ParseHtml {

    public String getParsePage(String path) {
        StringBuilder builder = new StringBuilder();
        try {
//          List<String> lines = Files.readAllLines(Paths.get("https://skillbox.ru/code/")); //через URI
            List<String> lines = Files.readAllLines(Paths.get(path));     //через файл в папке проекта
            lines.forEach(line -> builder.append(line + "\n"));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return builder.toString();
    }
}
