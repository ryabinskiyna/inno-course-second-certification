package ext;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class CommonHelper {


    //Получить параметры из файла
    public static Properties getProperties(String path) {
        File propFile = new File(path);
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(propFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }
}
