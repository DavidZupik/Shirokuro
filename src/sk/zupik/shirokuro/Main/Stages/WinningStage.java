package sk.zupik.shirokuro.Main.Stages;

import sk.zupik.shirokuro.Main.Shirokuro;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Random;

public class WinningStage extends Stage {

    Random random = new Random();

    Button next = new Button("Next");
    Button getBack = new Button("Back");
    Text text = new Text();

    BorderPane root = new BorderPane();
    BorderPane bottomPane = new BorderPane();

    public WinningStage(Integer lvl){

        if(lvl != null && lvl != 25){
            bottomPane.setRight(next);
            text.setText("You pass level.\n" +
                    "Your time: " + Shirokuro.getGameStage().state.time + "s\n" +
                    "Click next for next level.\n\n" +

                    "Prešiel si level.\n" +
                    "Tvoj čas bol: " + Shirokuro.getGameStage().state.time + "s\n" +
                    "Klikni na next pre ďalší level.");

        }
        else{
            text.setText("You pass level.\n" +
                    "Your time: " + Shirokuro.getGameStage().state.time + "s\n\n" +
                    "Vyhral si.\n" +
                    "Tvoj čas bol: " + Shirokuro.getGameStage().state.time + "s");
        }

        text.getStyleClass().add("win-text");

        getBack.getStyleClass().addAll("bottomButtonStyle", "bottomButtonStyle-save");
        getBack.setAlignment(Pos.CENTER);
        getBack.setOnAction(e->{
            this.close();
            Shirokuro.getLvlSelection().show();
        });

        next.getStyleClass().addAll("bottomButtonStyle", "bottomButtonStyle-save");
        next.setAlignment(Pos.CENTER);
        next.setOnAction(e->{
            this.close();
            GameStage.timeCounter.stop();
            int level = GameStage.getLvl() + 1;
            Shirokuro.setGameStage(new GameStage(level));
            Shirokuro.getGameStage().setUpStage();
            Shirokuro.getGameStage().show();
        });

        bottomPane.setLeft(getBack);
        root.setCenter(text);
        root.setBottom(bottomPane);
        int bgN = random.nextInt(3)+1;
        root.getStyleClass().add("wbg" + bgN);

        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().addAll("sk/zupik/shirokuro/Main/Styles_Images/ButtonStyles.css",
                "sk/zupik/shirokuro/Main/Styles_Images/Background.css",
                "sk/zupik/shirokuro/Main/Styles_Images/PaneBG.css",
                "sk/zupik/shirokuro/Main/Styles_Images/TextStyles.css",
                "sk/zupik/shirokuro/Main/Styles_Images/ShapesStyles.css");

        this.setTitle("Win");
        this.getIcons().add(new Image("sk/zupik/shirokuro/Main/Styles_Images/Images/logo.png"));
        this.setResizable(false);
        this.setScene(scene);

    }

}
