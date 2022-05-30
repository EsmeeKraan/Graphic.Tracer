import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileHandler {

    public void createFile(String nameOfFile) {
        try {
            File file = new File(nameOfFile);
            boolean flag = file.createNewFile();
            if (flag) {
                System.out.println("File has been created successfully at the specified location");
            } else {
                System.out.println("File already present at the specified location");
            }
        } catch (IOException e) {
            System.out.println("Exception Occurred:");
            e.printStackTrace();
        }
    }

    public Path readFile(String nameOfFile) {
//        return new FileReader(nameOfFile);
        return Path.of(nameOfFile);
    }

    public FileReader readFileAsReader(String nameOfFile) throws FileNotFoundException {
        return new FileReader(nameOfFile);
    }

    public void writeToFile(String nameOfFile, String context) {
        createFile(nameOfFile);
        var fileToWriteTo = readFile(nameOfFile);
        try {
            Files.writeString(fileToWriteTo, context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeFile(String nameOfFile)
    {
        // todo: remove file.
    }

}