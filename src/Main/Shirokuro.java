package Main;

import Main.Stages.*;
import Sound.Sounds;

import javafx.application.Application;
import javafx.stage.Stage;

//todo refactor code ... make documentation

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
    public void start(Stage stage){

        directory = System.getProperty("user.dir")+"\\src\\Load";
        setMainMenu(new MainMenu());
        mainMenu.show();
        setSounds(new Sounds());
        sounds.play();

    }

    public static GameStage getGameStage() {
        return gameStage;
    }
    public static MainMenu getMainMenu() {
        return mainMenu;
    }
    public static Sounds getSounds() {
        return sounds;
    }
    public static Help getHelp() {
        return help;
    }
    public static LevelSelection getLvlSelection() {
        return lvlSelection;
    }
    public static WinningStage getWinningStage() {
        return winningStage;
    }
    public static EditStage getEditStage() {
        return editStage;
    }

    public static void setGameStage(GameStage newGameStage) {
        gameStage = newGameStage;
    }
    public static void setMainMenu(MainMenu newMainMenu) {
        mainMenu = newMainMenu;
    }
    public static void setSounds(Sounds newSounds) {
        sounds = newSounds;
    }
    public static void setHelp(Help newHelp) {
        help = newHelp;
    }
    public static void setLvlSelection(LevelSelection newLvlSelection) {
        lvlSelection = newLvlSelection;
    }
    public static void setWinningStage(WinningStage newWinningStage) {
        winningStage = newWinningStage;
    }
    public static void setEditStage(EditStage newEditStage) {
        editStage = newEditStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

}