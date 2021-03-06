package shirokuro.Main.Stages;

import shirokuro.Main.Shirokuro;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * trieda ktora vyhodi okno vyberu levelu
 */
public class LevelSelection extends Stage {

    BorderPane root;
    GridPane topPane;
    BorderPane bottomPane;
    Stage loadGame;

    /**
     * konstruktor triedy
     */
    public LevelSelection(){

        topPane = new GridPane();
        topPane.getStyleClass().add("paneBG");
        topPane.setAlignment(Pos.CENTER);
        topPane.setHgap(0);
        topPane.setVgap(0);
        topPane.setMinWidth(500);
        topPane.setMinHeight(450);

        //grid-pane with buttons
        makeAndFillGridPane();

        //bottom buttons
        Button getBack = new Button("Back");
        getBack.getStyleClass().addAll("bottomButtonStyle", "bottomButtonStyle-levelSelection");
        getBack.setAlignment(Pos.CENTER);
        getBack.setOnAction(e-> getBackButtonAction());

        Button load = new Button("Load");
        load.getStyleClass().addAll("bottomButtonStyle", "bottomButtonStyle-levelSelection");
        load.setAlignment(Pos.CENTER);
        load.setOnAction(e-> loadBackButtonAction());

        Button exit = new Button("Exit");
        exit.getStyleClass().addAll("bottomButtonStyle", "bottomButtonStyle-levelSelection");
        exit.setAlignment(Pos.CENTER);
        exit.setOnAction(e -> Platform.exit());

        bottomPane = new BorderPane();
        bottomPane.setLeft(getBack);
        bottomPane.setCenter(exit);
        bottomPane.setRight(load);
        bottomPane.getStyleClass().add("paneBG");
        bottomPane.setMinWidth(50);
        bottomPane.setMinHeight(50);

        root = new BorderPane();
        root.getStyleClass().add("bgL");
        root.setCenter(topPane);
        root.setBottom(bottomPane);


        Scene scene = new Scene(root, 700, 500);
        scene.getStylesheets().addAll("shirokuro/Main/Styles_Images/ButtonStyles.css",
                "shirokuro/Main/Styles_Images/Background.css",
                "shirokuro/Main/Styles_Images/PaneBG.css",
                "shirokuro/Main/Styles_Images/TextStyles.css",
                "shirokuro/Main/Styles_Images/ShapesStyles.css");


        this.setTitle("Shirokuro - Level Selection");
        this.getIcons().add(new Image("shirokuro/Main/Styles_Images/Images/logo.png"));
        this.setResizable(false);
        this.setScene(scene);

    }
    private void makeAndFillGridPane(){
        for (int rowIndex = 0; rowIndex < 5; rowIndex++){
            RowConstraints rc = new RowConstraints();
            rc.setVgrow(Priority.ALWAYS);
            rc.setValignment(VPos.CENTER);
            rc.setFillHeight(true);
            topPane.getRowConstraints().add(rc);
        }
        for (int colIndex = 0; colIndex < 5; colIndex++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setHgrow(Priority.ALWAYS) ;
            cc.setFillWidth(true);
            cc.setHalignment(HPos.CENTER);
            topPane.getColumnConstraints().add(cc);
        }
        for (int i = 0; i < 25; i++) {
            Button button = new Button((i+1) + "");
            button.getStyleClass().add("levelButtonStyle");
            int finalI = i+1;
            button.setOnAction(e-> levelButtonAction(finalI));
            topPane.add(button, i % 5, i / 5);
        }
    }
    private void getBackButtonAction(){
        Shirokuro.getMainMenu().show();
        this.close();
    }
    private void loadBackButtonAction(){
        loadGame = new LoadGame(this);
        loadGame.show();
    }
    private void levelButtonAction(int i){
        Shirokuro.setGameStage(new GameStage(i));
        Shirokuro.getGameStage().setUpStage();
        Shirokuro.getGameStage().show();
        this.close();
    }
}
