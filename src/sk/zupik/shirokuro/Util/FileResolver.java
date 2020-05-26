package sk.zupik.shirokuro.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public final class FileResolver {
    private FileResolver(){}

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

    public static InputStream getInputStream(String path) throws FileNotFoundException {
        InputStream is = FileResolver.class.getClassLoader().getResourceAsStream("../" + path);

        if (is == null){
            return new FileInputStream(new File("src/sk/zupik/shirokuro/" + path));
        }

        return is;
    }
}
