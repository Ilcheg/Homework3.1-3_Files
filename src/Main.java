import java.io.*;
import java.util.Date;


public class Main {
    static StringBuilder logs = new StringBuilder();

    public static void main(String[] args) {
        File dir1 = new File("D://Games/src");
        createDir(dir1);
        File dir2 = new File("D://Games/res");
        createDir(dir2);
        File dir3 = new File("D://Games/savegames");
        createDir(dir3);
        File dir4 = new File("D://Games/temp");
        createDir(dir4);
        File dir5 = new File("D://Games/src/main");
        createDir(dir5);
        File dir6 = new File("D://Games/src/test");
        createDir(dir6);

        File myFile1 = new File("D://Games/src/main/Main.java");
        createFile(myFile1);
        File myFile2 = new File("D://Games/src/main/Utils.java");
        createFile(myFile2);

        File dir7 = new File("D://Games/res/drawables");
        createDir(dir7);
        File dir8 = new File("D://Games/res/vectors");
        createDir(dir8);
        File dir9 = new File("D://Games/res/icons");
        createDir(dir9);

        File myFile3 = new File("D://Games/temp/temp.txt");
        createFile(myFile3);


        String text = logs.toString();
        try (FileOutputStream fos = new FileOutputStream("D://Games/temp/temp.txt")) {
            byte[] bytes = text.getBytes();
            fos.write(bytes, 0, bytes.length);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    private static void createFile(File myFile) {
        try {
            if (myFile.createNewFile())
                System.out.println("Файл был создан");
            saveLogs(myFile);
        } catch (IOException ex) {
            System.out.println("Исключение: ");
            System.out.println(ex.getMessage());
            saveFailedLogs(myFile);
        }
    }

    private static void createDir(File dir) {
        if (dir.mkdir()) {
            System.out.println("Директория создана");
            saveLogs(dir);
        } else {
            System.out.println("Ошибка, директория не создана");
            saveFailedLogs(dir);
        }
    }

    public static void saveLogs(File log) {
        Date date = new Date();
        logs.append(log);
        logs.append("\n");
        logs.append("Created at " + "\n" + date);
        logs.append("\n");
        logs.append("\n");
    }

    public static void saveFailedLogs(File log) {
        Date date = new Date();
        logs.append(log);
        logs.append("\n");
        logs.append("Failed to create at" + "\n" + date);
        logs.append("\n");
        logs.append("\n");
    }
}
