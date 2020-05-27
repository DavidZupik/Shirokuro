package sk.zupik.shirokuro.Main;

import sk.zupik.shirokuro.Main.Stages.*;
import sk.zupik.shirokuro.Main.Sound.Sounds;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.*;

public class Shirokuro extends Application {

    private static MainMenu mainMenu;
    private static GameStage gameStage;
    private static Sounds sounds;
    private static Help help;
    private static LevelSelection lvlSelection;
    private static WinningStage winningStage;
    private static EditStage editStage;
    public static String directory;

    @Override
    public void start(Stage stage) throws IOException {
        directory = System.getProperty("user.dir");
        setMainMenu(new MainMenu());
        mainMenu.show();
        setSounds(new Sounds());
        sounds.play();
    }

    /**
     * @return vrati stage hry
     */
    public static GameStage getGameStage() {
        return gameStage;
    }

    /**
     *
     * @return vrati stage hlavneho menu
     */
    public static MainMenu getMainMenu() {
        return mainMenu;
    }

    /**
     *
     * @return vrati triedu zo zvukom
     */
    public static Sounds getSounds() {
        return sounds;
    }

    /**
     * @return vrati stage kde je napoveda ako hrat hru
     */
    public static Help getHelp() {
        return help;
    }

    /**
     * @return vrati stage kde sa vyberaju leveli
     */
    public static LevelSelection getLvlSelection() {
        return lvlSelection;
    }

    /**
     * @return vrati stage kde bol level prejdeny
     */
    public static WinningStage getWinningStage() {
        return winningStage;
    }

    /**
     * @return vrati editovaci stage
     */
    public static EditStage getEditStage() {
        return editStage;
    }

    /**
     * nastavenie stagu hry
     */
    public static void setGameStage(GameStage newGameStage) {
        gameStage = newGameStage;
    }

    /**
     * nastavenie stagu hlavneho menu
     */
    public static void setMainMenu(MainMenu newMainMenu) {
        mainMenu = newMainMenu;
    }

    /**
     * nastavenie zvuku
     */
    public static void setSounds(Sounds newSounds) {
        sounds = newSounds;
    }

    /**
     * nastavenie stagu pomoci
     */
    public static void setHelp(Help newHelp) {
        help = newHelp;
    }

    /**
     * nastavenie stagu vyberu levelu
     */
    public static void setLvlSelection(LevelSelection newLvlSelection) {
        lvlSelection = newLvlSelection;
    }

    /**
     * nastavenie stagu kde level bol prejdeny
     */
    public static void setWinningStage(WinningStage newWinningStage) {
        winningStage = newWinningStage;
    }

    /**
     * nastavenie editovacieho stagu
     */
    public static void setEditStage(EditStage newEditStage) {
        editStage = newEditStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

}