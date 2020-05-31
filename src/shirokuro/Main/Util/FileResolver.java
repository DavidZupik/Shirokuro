package shirokuro.Main.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

/**
 * trieda ktora obsahuje metody ktore pomahaju k nacitavaniu suborov
 */
public final class FileResolver {

    private FileResolver(){}
    /**
     * @param path ceska k suboru v ktorej ma metoda hladat hudbu
     * @return vrati cestu k hudbe v URI formate
     */
    public static URI getUri(String path){
        try {
            try {
                return Objects.requireNonNull(FileResolver.class.getClassLoader().getResource("shirokuro/" + path)).toURI();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } catch (NullPointerException e){
            System.out.println("nieco2");
            return new File("src/shirokuro/" + path).toURI();
        }
        return null;
    }
    /**
     * @param path cesta k suboru v ktorej ma hladat level
     * @return vrati cestu k levelu ako InputStream
     * @throws FileNotFoundException ak sa dany subor nenajde v zlozke
     */
    public static InputStream getInputStream(String path) throws FileNotFoundException {
        InputStream is = FileResolver.class.getClassLoader().getResourceAsStream("shirokuro/" + path);
        if (is == null){
            return new FileInputStream(new File("src/shirokuro/" + path));
        }
        return is;
    }
}
