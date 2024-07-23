import java.io.File;
import java.io.FilenameFilter;
import java.util.Set;

public class Main {
    public static String ext = ".xlsx";
    public static void main(String[] args) {
        Set keys = System.getProperties().keySet();
        for (Object key : keys) {
            System.out.println(key);
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
            System.out.println(getFolderSize(folders));
        }
    }

    public static long getFolderSize(File folders) {
        if (folders.isFile()) {
            return folders.length();
        } else {
            File[] files = folders.listFiles();
            long sum = 0;
            for (File file : files) {
                if (file.getName().toLowerCase().endsWith(ext)) {
                    sum += getFolderSize(file);
                    System.out.println(file.getName());
                }
            }
            return sum;
        }
    }
    private void outFileWithExtension(File dir) {
        FilenameFilter txtFilter = (dir1, name) -> {
            if (dir1.getName().endsWith(".txt")) {
                return true;
            }else return false;
        };
        File[] files = dir.listFiles(txtFilter);
        for (File file : files) {
            System.out.println(file.getName());
        }
    }
}

