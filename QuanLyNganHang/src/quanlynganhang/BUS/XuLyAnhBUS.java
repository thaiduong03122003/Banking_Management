package quanlynganhang.BUS;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Instant;

public class XuLyAnhBUS {
    private static final String IMAGE_DIRECTORY = System.getProperty("user.dir") + File.separator + "src" + File.separator + "quanlynganhang" + File.separator + "image" + File.separator + "data_image";

    public static String saveImage(File selectedFile) {
        String fileName = selectedFile.getName();
        String newFileName = generateUniqueFileName(fileName);
        File destination = new File(IMAGE_DIRECTORY, newFileName);

        try {
            Files.copy(selectedFile.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Luu anh " + newFileName + " thanh cong!" );
            return newFileName;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static boolean deleteImage(String fileName) {
        File fileToDelete = new File(IMAGE_DIRECTORY, fileName);
        if (fileToDelete.exists()) {
            System.out.println("Xoa anh " + fileName + " thanh cong!" );
            return fileToDelete.delete();
        } else {
            System.out.println("File " + fileName + " does not exist!");
            return false;
        }
    }

    private static String generateUniqueFileName(String fileName) {
        long timestamp = Instant.now().toEpochMilli();
        String extension = "";
        int lastIndexOfDot = fileName.lastIndexOf('.');
        if (lastIndexOfDot > 0) {
            extension = fileName.substring(lastIndexOfDot);
        }

        return timestamp + extension;
    }
}

