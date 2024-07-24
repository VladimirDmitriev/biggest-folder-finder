import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Set;

public class Main {
    public static String ext = ".csv";
    private static final boolean OUT_CONSOLE = false;
    public static void main(String[] args) {
        Set keys = System.getProperties().keySet();
        for (Object key : keys) {
            //System.out.println(key);
        }
        File folders = null;
        //String folderPath = "C:/Users/PC/Desktop/Executor";
        String userHome = System.getProperties().get("user.home").toString();
        String folderPath = userHome + "\\Downloads";
        System.out.println(folderPath);
        try {
            folders = new File(folderPath);
        }catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
        }
        if(!folders.exists()) {
            System.out.println(folderPath + " папка не существует");
        }else{
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
                    HashMap expence2sum = tableFile.getCsvTable(fullName);
                        for(Object paymentType : expence2sum.keySet()) {
                            double sumPayment = (double) expence2sum.get(paymentType);
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
            }else return false;
        };
        File[] files = dir.listFiles(txtFilter);
        for (File file : files) {
            System.out.println(file.getName());
        }
    }
}

