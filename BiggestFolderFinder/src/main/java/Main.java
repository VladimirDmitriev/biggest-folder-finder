import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dto.Car;
import dto.Person;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;


public class Main {
    public static int variant = 5;
    public static String ext = ".csv";
    private static final boolean OUT_CONSOLE = false;

    public static void main(String[] args) {
        if (variant == 1) {
            parserSymbolFile();
        } else if (variant == 2) {
            String path = "data/code.html";
            String htmlFile = new ParseHtml().getParsePage(path);
            Document doc = Jsoup.parse(htmlFile);
//          System.out.println(doc); // вывод полученного документа
            Elements elements = doc.select("div.ui-product-card-main__wrap > h3");
//          System.out.println(elements.stream().count()); //вывод количества полученных элементов
            elements.forEach(element -> System.out.println(element.text()));
        } else if (variant == 3) {
            String path = "data/MoscowStation.html";
            String htmlFile = new ParseHtml().getParsePage(path);
            Document doc = Jsoup.parse(htmlFile);
            String cssQueryLines = "span[^data-line],.name";
            Elements elements = doc.select(cssQueryLines);
            System.out.println(elements.toString());
            Element a1 = elements.get(1);
            System.out.println(a1.toString());
            System.out.println("Количество полученных элементов: " + elements.stream().count());
            String newStation = "";
            for (Element element : elements) {
                String name = element.text();
                if (name.contains("линия") || name.contains("МЦД")) {
                    if (newStation != name) {
                        newStation = name;
                        System.out.println(newStation);
                        System.out.println("______________________");
                    }
                }else System.out.println(name);
            }
        } else if (variant == 4) {
            String path = "data/MoscowStation.html";
            String htmlFile = new ParseHtml().getParsePage(path);
            Document doc = Jsoup.parse(htmlFile);
//            String cssQueryLines = "span[^data-line],.name";
//            String cssQueryStation = "div.t-text-simple";
            for (int i = 1; i <= 15; i++) {
                Elements elements = doc.getElementsByAttributeValue("data-line", Integer.toString(i));
                if (elements.isEmpty()) {
                    continue;
                }
                //System.out.println(elements.get(0).text());
                //System.out.println(elements.get(0).attribute("data-line"));
                String numberLine = elements.get(0).attr("data-line").toString();
                LineMetro lineMetro = new LineMetro(numberLine, elements.get(0).text());
                System.out.print("\n");
                System.out.println(lineMetro.getNameLine());
                System.out.println("-------------------------");
                //Elements stations  = elements.get(1).getElementsByTag("span");
                Elements stations = elements.get(1).getElementsByClass("name");
                for (Element station : stations) {
//                System.out.println("tag:" + station.tag()+" className:" + station.className() +"  text:"+station.text());
                    Station newstation = new Station(station.text(), lineMetro);
                    System.out.println(newstation.getNameStation());
//                System.out.println(line.children());
                    //                for (Element child: line.select("name")) {
                    //                    System.out.println(child.tagName());
                    //                }
                }
            }
        }else if(variant == 5) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            try {
                String json = Files.readString(Paths.get("data/person.json"));
                Person person = objectMapper.readValue(json, Person.class);
                //Читаем созданный объект
                System.out.println(person.getName());
                System.out.println(person.getJob().getSince());
                //модифицируем объекты
                person.setChildren(List.of("Ольга","Петр"));
                Car car = new Car();
                car.setLicensePlate("A111BB777");
                person.setCar(car);
                String newJson = objectMapper.writeValueAsString(person);
                FileWriter fileWriter = new FileWriter("data/personModified.json");
                fileWriter.write(newJson);
                fileWriter.close();
            }catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void parserSymbolFile() {

        //Set keys = System.getProperties().keySet();
        //for (Object key : keys) {
            //System.out.println(key);
        //}
        File folders = null;
        //String folderPath = "C:/Users/PC/Desktop/Executor";
        String userHome = System.getProperties().get("user.home").toString();
        String folderPath = userHome + "\\Downloads";
        System.out.println(folderPath);
        try {
            folders = new File(folderPath);
        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
        }
        if (!folders.exists()) {
            System.out.println(folderPath + " папка не существует");
        } else {
            System.out.println("------------------------------------");
            System.out.println("Объем папки: " + getFolderSize(folders) + " байт");
        }
    }

    public static long getFolderSize(File folders) {
        if (folders.isFile()) {
            return folders.length();
        } else {
            File[] files = folders.listFiles();
            long sum = 0;
            int num = 0;
            for (File file : files) {
                if (file.getName().toLowerCase().endsWith(ext)) {
                    sum += getFolderSize(file);
                    num += 1;
                    String fullName = file.getAbsoluteFile().toString();
                    String nameFile = file.getName();
                    if (OUT_CONSOLE == true) {
                        System.out.println(num +": " + fullName);
                        System.out.println(nameFile);
                    }
                    if (nameFile.equals("movementList.csv")) {
                    TableFile tableFile  = new TableFile();
                    HashMap expense2sum = tableFile.getCsvTable(fullName);
                        for(Object paymentType : expense2sum.keySet()) {
                            double sumPayment = (double) expense2sum.get(paymentType);
                            System.out.println(paymentType + "\t" + sumPayment);
                        }
                    }
                }
            }
            return sum;
        }
    }
    private void outFileWithExtension(File dir) {
        FilenameFilter txtFilter = (dir1, name) -> {
            if (dir1.getName().endsWith(".cvs")) {
                return true;
            }else {
                return false;
            }
        };
        File[] files = dir.listFiles(txtFilter);
        for (File file : files) {
            System.out.println(file.getName());
        }
    }
}

