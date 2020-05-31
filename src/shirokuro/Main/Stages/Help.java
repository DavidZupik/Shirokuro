package shirokuro.Main.Stages;

import shirokuro.Main.Shirokuro;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * trieda ktora vyhodi okno s pravidlami hry
 */
public class Help extends Stage {

    /**
     * konstruktor triedy
     */
    public Help(){

        VBox root;
        BorderPane textPane = new BorderPane();
        StackPane sPane;
        Text text = new Text();
        BorderPane bottomPane = new BorderPane();

        //textpane text
        text.setText("\nRules:\n" +
                    "\nEN\n"+
                    "Connect each black circle with a white circle\n" +
                    "Use only straight horizontal or vertical line\n" +
                    "In each circle exactly one line ends\n" +
                    "Lines do not pass through other circles or lines\n" +
                    "Not necessarily each cell is visited by a line\n" +
                    "\nSK\n" +
                    "Prepojte kazdy cierny kruh s bielym kruhom\n" +
                    "Pouzivajte iba priamu vodorovnu alebo zvislu ciaru\n" +
                    "V kazdom kruhu konci presne jedna ciara.\n" +
                    "Ciary neprechadzaju inymi kruhmi alebo ciarami\n" +
                    "Nie kazda bunka musi byt navstivena linkou");

        text.getStyleClass().add("helpText");

        sPane = new StackPane();
        sPane.getChildren().addAll(text);

        //textpane settings
        textPane.setCenter(sPane);
        textPane.getStyleClass().add("paneBG");
        textPane.setMinWidth(450);
        textPane.setMinHeight(450);

        //bottompane buttons
        Button getBack = new Button("Back");
        getBack.getStyleClass().addAll("bottomButtonStyle", "bottomButtonStyle-help");
        getBack.setAlignment(Pos.CENTER);
        getBack.setOnAction(e -> getBackButtonAction() );

        Button play = new Button("Play");
        play.getStyleClass().addAll("bottomButtonStyle", "bottomButtonStyle-help");
        play.setAlignment(Pos.CENTER);
        play.setOnAction(e -> playButtonAction() );

        Button exit = new Button("Exit");
        exit.getStyleClass().addAll("bottomButtonStyle", "bottomButtonStyle-help");
        exit.setAlignment(Pos.CENTER);
        exit.setOnAction(e -> Platform.exit());

        //bottompane settings
        bottomPane.setLeft(getBack);
        bottomPane.setCenter(exit);
        bottomPane.setRight(play);
        bottomPane.getStyleClass().add("paneBG");
        bottomPane.setMinWidth(50);
        bottomPane.setMinHeight(50);

        root = new VBox(textPane, bottomPane);
        root.getStyleClass().add("bgH");

        Scene scene = new Scene(root, 700, 500);
        scene.getStylesheets().addAll("shirokuro/Main/Styles_Images/ButtonStyles.css",
                "shirokuro/Main/Styles_Images/Background.css",
                "shirokuro/Main/Styles_Images/PaneBG.css",
                "shirokuro/Main/Styles_Images/TextStyles.css",
                "shirokuro/Main/Styles_Images/ShapesStyles.css");

        this.setTitle("Shirokuro - Help");
        this.getIcons().add(new Image("shirokuro/Main/Styles_Images/Images/logo.png"));
        this.setResizable(false);
        this.setScene(scene);

    }
    private void getBackButtonAction(){
        Shirokuro.getMainMenu().show();
        this.close();
    }
    private void playButtonAction(){
        Shirokuro.getLvlSelection().show();
        this.close();
    }

}
