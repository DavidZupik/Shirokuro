package Sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Sounds {

    File s = new File(System.getProperty("user.dir") + "\\src\\Sound\\Music1.mp3");
    Media pick = new Media(s.toURI().toString());
    MediaPlayer player = new MediaPlayer(pick);

    public void play(){
        player.setCycleCount(MediaPlayer.INDEFINITE);
        player.setVolume(0.05);
        player.play();
    }

    public void pause(){
        player.pause();
    }

    public void volumeUP(){
        if (player.getVolume() < 0.1) {
            player.setVolume(player.getVolume()+0.01);
        }
    }

    public void volumeDOWN(){
        if (player.getVolume() > 0) {
            player.setVolume(player.getVolume()-0.01);
        }
    }
}
