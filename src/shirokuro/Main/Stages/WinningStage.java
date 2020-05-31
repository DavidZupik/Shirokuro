package shirokuro.Main.Stages;

import shirokuro.Main.Shirokuro;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Random;

/**
 * trieda ktora vyhodi okno po tom co bol hrany level prejdeny
 */
public class WinningStage extends Stage {

    /**
     * konstruktor levelu
     * @param lvl urcuje ci hrany level bol z predrobenych alebo loadnuty
     */
    public WinningStage(Integer lvl){
        GameStage.timeCounter.stop();
        Random random = new Random();
        final Button next = new Button("Next");
        final Button getBack = new Button("Back");
        final Text text = new Text();
        BorderPane root = new BorderPane();
        BorderPane bottomPane = new BorderPane();

        if(lvl != null && lvl != 25){
            bottomPane.setRight(next);
            text.setText("You pass level.\n" +
                    "Your time: " + Shirokuro.getGameStage().state.time + "s\n" +
                    "Click next for next level.\n\n" +

                    "Presiel si level.\n" +
                    "Tvoj cas bol: " + Shirokuro.getGameStage().state.time + "s\n" +
                    "Klikni na next pre dalsi level.");

        }
        else{
            text.setText("You pass level.\n" +
                    "Your time: " + Shirokuro.getGameStage().state.time + "s\n\n" +
                    "Vyhral si.\n" +
                    "Tvoj cas bol: " + Shirokuro.getGameStage().state.time + "s");
        }

        text.getStyleClass().add("win-text");

        getBack.getStyleClass().addAll("bottomButtonStyle", "bottomButtonStyle-save");
        getBack.setAlignment(Pos.CENTER);
        getBack.setOnAction(e->{
            Shirokuro.getLvlSelection().show();
            this.close();
        });

        next.getStyleClass().addAll("bottomButtonStyle", "bottomButtonStyle-save");
        next.setAlignment(Pos.CENTER);
        next.setOnAction(e->{
            int level = GameStage.getLvl() + 1;
            Shirokuro.setGameStage(new GameStage(level));
            Shirokuro.getGameStage().setUpStage();
            Shirokuro.getGameStage().show();
            this.close();
        });

        bottomPane.setLeft(getBack);
        root.setCenter(text);
        root.setBottom(bottomPane);
        int bgN = random.nextInt(3)+1;
        root.getStyleClass().add("wbg" + bgN);

        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().addAll("shirokuro/Main/Styles_Images/ButtonStyles.css",
                "shirokuro/Main/Styles_Images/Background.css",
                "shirokuro/Main/Styles_Images/PaneBG.css",
                "shirokuro/Main/Styles_Images/TextStyles.css",
                "shirokuro/Main/Styles_Images/ShapesStyles.css");

        this.setTitle("Win");
        this.getIcons().add(new Image("shirokuro/Main/Styles_Images/Images/logo.png"));
        this.setResizable(false);
        this.setScene(scene);

    }
}
