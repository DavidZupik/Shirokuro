package shirokuro.Main;

import shirokuro.Main.Stages.*;
import shirokuro.Main.Sound.Sounds;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Hlavna trieda aplikacie<br>
 * Spustanie aplikacie a nacitavanie stagov
 */
public class Shirokuro extends Application {

    private static MainMenu mainMenu;
    private static GameStage gameStage;
    private static Sounds sounds;
    private static Help help;
    private static LevelSelection lvlSelection;
    private static WinningStage winningStage;
    private static EditStage editStage;
    /**
     *  cesta k adresaru projektu
     */
    public static String directory;

    @Override
    public void start(Stage stage) {
        directory = System.getProperty("user.dir");
        setMainMenu(new MainMenu());
        mainMenu.show();
        setSounds(new Sounds());
        sounds.play();
    }
    public static void main(String[] args) {
        launch(args);
    }
    /**
     * @return hlavne okno hry
     */
    public static GameStage getGameStage() {
        return gameStage;
    }
    /**
     *
     * @return okno hlavneho menu
     */
    public static MainMenu getMainMenu() {
        return mainMenu;
    }
    /**
     *
     * @return triedu zo zvukom
     */
    public static Sounds getSounds() {
        return sounds;
    }
    /**
     * @return okno kde je napoveda ako hrat hru
     */
    public static Help getHelp() {
        return help;
    }
    /**
     * @return okno kde sa vyberaju leveli
     */
    public static LevelSelection getLvlSelection() {
        return lvlSelection;
    }
    /**
     * @return okno kde bol level prejdeny
     */
    public static WinningStage getWinningStage() {
        return winningStage;
    }
    /**
     * @return okno editovaci stage
     */
    public static EditStage getEditStage() {
        return editStage;
    }
    /**
     * nastavenie okna hry
     * @param newGameStage nove okno hry
     */
    public static void setGameStage(GameStage newGameStage) {
        gameStage = newGameStage;
    }
    /**
     * nastavenie okna hlavneho menu
     * @param newMainMenu nastavenie noveho okna hlavneho menu
     */
    public static void setMainMenu(MainMenu newMainMenu) {
        mainMenu = newMainMenu;
    }
    /**
     * nastavenie zvuku
     * @param newSounds nastavenie zvuku
     */
    public static void setSounds(Sounds newSounds) {
        sounds = newSounds;
    }
    /**
     * nastavenie okna s napovedou
     * @param newHelp nastavenie noveho okna napovied
     */
    public static void setHelp(Help newHelp) {
        help = newHelp;
    }
    /**
     * nastavenie okna vyberu levelu
     * @param newLvlSelection nastavenie noveho okna vyberu levelu
     */
    public static void setLvlSelection(LevelSelection newLvlSelection) {
        lvlSelection = newLvlSelection;
    }
    /**
     * nastavenie okna kde level bol prejdeny
     * @param newWinningStage nastavenie noveho okna vyhranej hry
     */
    public static void setWinningStage(WinningStage newWinningStage) {
        winningStage = newWinningStage;
    }
    /**
     * nastavenie editovacieho okna
     * @param newEditStage nastavenie noveho okna editovania hry
     */
    public static void setEditStage(EditStage newEditStage) {
        editStage = newEditStage;
    }
}