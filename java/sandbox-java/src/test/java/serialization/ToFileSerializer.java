package serialization;

import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ToFileSerializer {
    private final TemporaryFolder folder;
    private File file;

    public ToFileSerializer(TemporaryFolder folder){
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
        return folder.newFile("serialized");
    }
}
