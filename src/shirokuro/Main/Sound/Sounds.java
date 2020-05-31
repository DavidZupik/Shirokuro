package shirokuro.Main.Sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import shirokuro.Main.Shirokuro;
import shirokuro.Main.Util.FileResolver;

import java.util.Objects;

/**
 * trieda ktora zaobstarava: <br>
 *     spustanie hudby,
 *     pozastavovanie hudby,
 *     znizovanie hlasitosti,
 *     zvacsovanie hlasitosti,
 */
public final class Sounds {

    MediaPlayer player = new MediaPlayer(new Media(Objects.requireNonNull(FileResolver.getUri("Main/Sound/Music1.mp3")).toString()));

    /**
     * spustenie hudby
     */
    public void play(){
        player.setCycleCount(MediaPlayer.INDEFINITE);
        player.setVolume(0.05);
        player.play();
    }
    /**
     * pauznutie hudby
     */
    public void pause(){
        player.pause();
    }
    /**
     * zvysenie hlasitosti hudby<br>
     * ak hudba bola pauznuta alebo hlasitost bola 0<br>
     * tak sa opat spusti
     */
    public void volumeUP(){
        if (player.getVolume() < 0.1) {
            player.setVolume(player.getVolume()+0.01);
        }
        if(player.getVolume() > 0){
            Shirokuro.getMainMenu().sound = true;
            Shirokuro.getMainMenu().getAudio().setGraphic(Shirokuro.getMainMenu().getAudioIMG());
            player.play();
        }
    }
    /**
     * znizenie hlasitosti hudby<br>
     * ak hlasitost hudby je na urovni kedy ho nepocut<br>
     * hudba sa pozastavi
     */
    public void volumeDOWN(){
        if (player.getVolume() > 0) {
            player.setVolume(player.getVolume()-0.01);
        }
        if(player.getVolume() < 0.01){
            Shirokuro.getMainMenu().sound = false;
            Shirokuro.getMainMenu().getAudio().setGraphic(Shirokuro.getMainMenu().getNoAudioIMG());
            player.pause();
        }
    }
}
