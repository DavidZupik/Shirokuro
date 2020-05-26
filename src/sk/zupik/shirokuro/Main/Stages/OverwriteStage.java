package sk.zupik.shirokuro.Main.Stages;

import sk.zupik.shirokuro.Main.Shirokuro;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class OverwriteStage extends Stage {


    Button yes = new Button("Yes");
    Button no = new Button("No");

    Text text = new Text("Do you want overwrite existing file?");

    BorderPane root = new BorderPane();
    BorderPane bottomPane = new BorderPane();

    /**
     * konstruktor triedy
     * @param parentStage stage z ktoreho bola tato trieda zavolana
     * @param fileName nazov suboru
     * @param editGame je true ak sa jedna o prepisovanie naeditovanej hry
     */
    public OverwriteStage(Stage parentStage, String fileName, boolean editGame){

        no.getStyleClass().add("overButtonStyle");
        no.setOnAction(e-> noButtonAction());

        yes.getStyleClass().add("overButtonStyle");
        yes.setOnAction(e-> yesButtonAction(fileName, editGame));

        bottomPane.setLeft(no);
        bottomPane.setRight(yes);

        root.getStyleClass().add("save-game");
        root.setCenter(text);
        root.setBottom(bottomPane);

        Scene scene = new Scene(root, 200, 100);
        scene.getStylesheets().addAll("sk/zupik/shirokuro/Main/Styles_Images/ButtonStyles.css",
                "sk/zupik/shirokuro/Main/Styles_Images/Background.css",
                "sk/zupik/shirokuro/Main/Styles_Images/PaneBG.css",
                "sk/zupik/shirokuro/Main/Styles_Images/TextStyles.css",
                "sk/zupik/shirokuro/Main/Styles_Images/ShapesStyles.css");

        this.setScene(scene);
        this.initOwner(parentStage);
        this.initModality(Modality.WINDOW_MODAL);
        this.setTitle("Overwrite");
        this.getIcons().add(new Image("sk/zupik/shirokuro/Main/Styles_Images/Images/logo.png"));
        this.setResizable(false);

    }

    private void noButtonAction(){
        SaveGame.screenText.setText("A file with this name already exists.\n" +
                "Please select different file name.");
        this.close();
    }
    private void yesButtonAction(String fileName, boolean editGame){
        try {
            if(!editGame) {
                Shirokuro.getGameStage().state.saveGame(fileName);
            }
            else{
                Shirokuro.getEditStage().editState.editSaveGame(fileName);
            }
        } catch (Exception ioException) {
            ioException.printStackTrace();
        }
        SaveGame.screenText.setText("File has been overwritten.");
        this.close();
    }

}
