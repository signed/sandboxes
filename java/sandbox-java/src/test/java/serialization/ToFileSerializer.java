package serialization;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class ToFileSerializer {
    private final Path folder;
    private File file;

    public ToFileSerializer(Path folder){
        this.folder = folder;
    }

    public void write(Object ted) throws IOException {
        file = getFile();
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(ted);
        oos.close();
    }

    public<T> T readObject() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        T object = (T) ois.readObject();
        ois.close();
        return object;
    }

    private File getFile() throws IOException {
        return Files.createFile(
                folder.resolve("serialized")
        ).toFile();
    }
}
