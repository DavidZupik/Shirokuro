package sk.zupik.shirokuro.Main.Sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import sk.zupik.shirokuro.Main.Shirokuro;
import sk.zupik.shirokuro.Main.Util.FileResolver;

public final class Sounds {

    MediaPlayer player = new MediaPlayer(new Media(FileResolver.getUri("Main/Sound/Music1.mp3").toString()));

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
     * zvysenie hlasitosti hudby
     */
    public void volumeUP(){
        if (player.getVolume() < 0.1) {
            player.setVolume(player.getVolume()+0.01);
        }
        if(player.getVolume() > 0){
            Shirokuro.getMainMenu().sound = true;
            Shirokuro.getMainMenu().getAudio().setGraphic(Shirokuro.getMainMenu().getAudioIMG());
        }
    }

    /**
     * znizenie hlasitosti hudby
     */
    public void volumeDOWN(){
        if (player.getVolume() > 0) {
            player.setVolume(player.getVolume()-0.01);
        }
        if(player.getVolume() < 0.01){
            Shirokuro.getMainMenu().sound = false;
            Shirokuro.getMainMenu().getAudio().setGraphic(Shirokuro.getMainMenu().getNoAudioIMG());
        }
    }
}
