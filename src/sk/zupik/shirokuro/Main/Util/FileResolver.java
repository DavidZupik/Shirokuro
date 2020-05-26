package sk.zupik.shirokuro.Main.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

public final class FileResolver {
    private FileResolver(){}

    //todo opytat sa jozka co to vlastne robi
    public static URI getUri(String path){
        try {
            try {
                return FileResolver.class.getClassLoader().getResource("../" + path).toURI();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } catch (NullPointerException e){
            return new File("src/sk/zupik/shirokuro/" + path).toURI();
        }
        return null;
    }

    //todo
    public static InputStream getInputStream(String path) throws FileNotFoundException {
        InputStream is = FileResolver.class.getClassLoader().getResourceAsStream("../" + path);

        if (is == null){
            return new FileInputStream(new File("src/sk/zupik/shirokuro/" + path));
        }

        return is;
    }
}
