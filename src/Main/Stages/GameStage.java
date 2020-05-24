package Main.Stages;

import Main.Game.Pane.GamePane;
import Main.Game.States.GameState;
import Main.Shirokuro;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.Random;


public class GameStage extends Stage {

    BorderPane root;
    SaveGame saveGame;
    public GameState state;
    private static Integer lvl = null;
    String loadLVL;
    Random random = new Random();


    //top pane
    BorderPane topPane;
    public static Label lbTimer = new Label("Time: " + 0 + " sec");
    public static Timeline timeCounter;

    //center-pane
    public GamePane gamePane;
    Pane p1 = new Pane();
    Pane p2 = new Pane();

    //bottom-pane
    BorderPane bottomPane;
    Button getBack = new Button("Back");
    Button exit = new Button("Exit");
    Button save = new Button("Save");

    public GameStage(String fName){
        try {
            this.loadLVL = fName;
            gamePane = new GamePane();
            if(savedLevel(fName)) {
                state = GameState.loadGame(fName);
            }
            else{
                state = new GameState(GameState.loadEditGame(fName));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public GameStage(int lvlN){
        lvl = lvlN;
        this.loadLVL = null;
        gamePane = new GamePane();
        state = new GameState(lvl);
    }

    public void setUpStage(){

        //top-pane, timer
        lbTimer.getStyleClass().add("lbTimer");
        lbTimer.setAlignment(Pos.CENTER);
        lbTimer.getStyleClass().add("timerText");
        timeCounter = new Timeline(new KeyFrame(new Duration(1000), e-> lbTimer.setText("Time: " + (++state.time) + " sec")));
        timeCounter.setCycleCount(Animation.INDEFINITE);
        timeCounter.play();

        topPane = new BorderPane();
        topPane.setPrefHeight(25);
        topPane.setCenter(lbTimer);

        //bottom-pane, buttons
        getBack.getStyleClass().add("bottomButtonStyle");
        getBack.setAlignment(Pos.CENTER);
        getBack.setOnAction(e -> getBackButtonAction());

        save.getStyleClass().add("bottomButtonStyle");
        save.setAlignment(Pos.CENTER);
        save.setOnAction(e -> saveButtonAction() );

        exit.getStyleClass().add("bottomButtonStyle");
        exit.setAlignment(Pos.CENTER);
        exit.setOnAction(e -> Platform.exit());

        bottomPane = new BorderPane();
        bottomPane.setLeft(getBack);
        bottomPane.setCenter(exit);
        bottomPane.setRight(save);
        bottomPane.getStyleClass().add("paneBG");
        bottomPane.setMinWidth(50);
        bottomPane.setMinHeight(50);

        //center-pane, root
        p1.getStyleClass().add("side-pane");
        p2.getStyleClass().add("side-pane");
        root = new BorderPane();
        int bgN = random.nextInt(6)+1;
        root.getStyleClass().add("bg"+bgN);
        root.setTop(topPane);
        root.setLeft(p1);
        root.setCenter(gamePane);
        root.setRight(p2);
        root.setBottom(bottomPane);

        Scene scene = new Scene(root, 700, 500);
        scene.getStylesheets().addAll("Main/Styles_Images/ButtonStyles.css",
                "Main/Styles_Images/Background.css",
                "Main/Styles_Images/PaneBG.css",
                "Main/Styles_Images/TextStyles.css",
                "Main/Styles_Images/ShapesStyles.css",
                "Main/Styles_Images/RectangleStyles.css",
                "Main/Styles_Images/CellStyles.css");

        if(lvl != null){
            this.setTitle("Shirokuro - Level: " + lvl);
        }
        else{
            this.setTitle("Shirokuro - Loaded level: " + loadLVL);
        }
        this.getIcons().add(new Image("./Main/Styles_Images/Images/logo.png"));
        this.setResizable(false);
        this.setScene(scene);

        gamePane.paint();
    }

    public static Integer getLvl() {
        return lvl;
    }
    public static boolean savedLevel(String fName){
        File temp = new File(Shirokuro.directory + "\\"+fName+".lvl");
        return temp.exists();
    }

    void getBackButtonAction(){
        timeCounter.stop();
        Shirokuro.getLvlSelection().show();
        this.close();
    }
    void saveButtonAction(){
        timeCounter.stop();
        saveGame = new SaveGame(this, false);
        saveGame.show();
    }

}
