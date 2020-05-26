package Main.Stages;

import Main.Shirokuro;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class OverwriteStage extends Stage {


    Button yes = new Button("Yes");
    Button no = new Button("No");

    Text text = new Text("Do you want overwrite existing file?");

    BorderPane root = new BorderPane();
    BorderPane bottomPane = new BorderPane();

    public OverwriteStage(Stage parentStage, String fileName){

        no.getStyleClass().add("overButtonStyle");
        no.setOnAction(e-> noButtonAction());

        yes.getStyleClass().add("overButtonStyle");
        yes.setOnAction(e-> yesButtonAction(fileName));

        bottomPane.setLeft(no);
        bottomPane.setRight(yes);

        root.getStyleClass().add("save-game");
        root.setCenter(text);
        root.setBottom(bottomPane);

        Scene scene = new Scene(root, 200, 100);
        scene.getStylesheets().addAll("Main/Styles_Images/ButtonStyles.css",
                "Main/Styles_Images/Background.css",
                "Main/Styles_Images/PaneBG.css",
                "Main/Styles_Images/TextStyles.css",
                "Main/Styles_Images/ShapesStyles.css");

        this.setScene(scene);
        this.initOwner(parentStage);
        this.initModality(Modality.WINDOW_MODAL);
        this.setTitle("Overwrite");
        this.getIcons().add(new Image("./Main/Styles_Images/Images/logo.png"));
        this.setResizable(false);

    }

    private void noButtonAction(){
        SaveGame.screenText.setText("A file with this name already exists.\n" +
                "Please select different file name.");
        this.close();
    }
    private void yesButtonAction(String fileName){
        try {
            Shirokuro.getGameStage().state.saveGame(fileName);
        } catch (Exception ioException) {
            ioException.printStackTrace();
        }
        SaveGame.screenText.setText("File has been overwritten.");
        this.close();
    }

}
