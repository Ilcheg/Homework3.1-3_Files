import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

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


        GameProgress gameProgress1 = new GameProgress(100, 3, 1, 124.10);
        GameProgress gameProgress2 = new GameProgress(58, 5, 3, 240.57);
        GameProgress gameProgress3 = new GameProgress(28, 2, 10, 160.89);
        saveGame("D://Games/savegames/save1.dat", gameProgress1);
        saveGame("D://Games/savegames/save2.dat", gameProgress2);
        saveGame("D://Games/savegames/save3.dat", gameProgress3);


        File save1 = new File("D://Games/savegames/save1.dat");
        File save2 = new File("D://Games/savegames/save2.dat");
        File save3 = new File("D://Games/savegames/save3.dat");
        ArrayList<String> listPath = new ArrayList<>();
        listPath.add(save1.getPath());
        listPath.add(save2.getPath());
        listPath.add(save3.getPath());

        zipFiles("D://Games/savegames/zip.zip", listPath);

        openZip("D://Games/savegames/zip.zip", "D://Games/savegames/");

        System.out.println(openProgress("D://Games/savegames/save2.dat"));
    }

    private static void zipFiles(String path, ArrayList<String> listPath) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path))) {
            for (String filePath : listPath) {
                FileInputStream fis = new FileInputStream(filePath);
                File fileToZip = new File(filePath);
                ZipEntry entry = new ZipEntry(fileToZip.getName());
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
                fis.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        for (String filepath : listPath) {
            File delFile = new File(filepath);
            try {
                if (delFile.delete()) {
                    System.out.println("Файл " + delFile.getName() + " успешно удалён");
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private static void openZip(String zipPath, String unZipPath) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(zipPath))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fout = new FileOutputStream(unZipPath + name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static GameProgress openProgress(String path) {
        GameProgress gameProgress = null;
        try (FileInputStream fis = new FileInputStream(path);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return gameProgress;
    }

    private static void saveGame(String path, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
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
