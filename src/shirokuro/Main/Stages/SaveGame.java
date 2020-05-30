package shirokuro.Main.Stages;

import shirokuro.Main.Shirokuro;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;

/**
 * trieda ktora vyhodi okno si uzivatel vyberie pod akym menom chce ulozit hru
 */
public class SaveGame extends Stage {


    TextField textField = new TextField();
    OverwriteStage overwrite;
    boolean fileExist = true;
    boolean saveEditGame;

    /**
     * test ktory sa zobrazi v okne
     */
    public static Text screenText = new Text();

    /**
     * konstruktor triedy
     * @param parentStage stage z ktoreho bola tato trieda zavolana
     * @param editGame je true ak sa jedna o ukladanie naeditovanej hry
     */
    public SaveGame(Stage parentStage, boolean editGame){

        Button save = new Button("SAVE");
        Button getBack = new Button("BACK");
        BorderPane root = new BorderPane();
        HBox topPane = new HBox();
        BorderPane bottomPane = new BorderPane();
        saveEditGame = editGame;

        textField.setAlignment(Pos.CENTER);
        textField.setMaxWidth(200);
        textField.getStyleClass().add("text-field");

        screenText.getStyleClass().add("lbTimer");
        topPane.setAlignment(Pos.CENTER);
        topPane.getChildren().add(screenText);

        save.getStyleClass().addAll("bottomButtonStyle", "bottomButtonStyle-save");
        save.setAlignment(Pos.CENTER);
        save.setOnAction(e -> saveButtonAction(saveEditGame));


        getBack.getStyleClass().addAll("bottomButtonStyle", "bottomButtonStyle-save");
        getBack.setAlignment(Pos.CENTER);
        getBack.setOnAction(e-> getBackButtonAction());

        bottomPane.setRight(save);
        bottomPane.setLeft(getBack);

        root.setTop(topPane);
        root.setCenter(textField);
        root.setBottom(bottomPane);
        root.getStyleClass().add("save-game");

        Scene scene = new Scene(root, 400, 300);
        scene.getStylesheets().addAll("shirokuro/Main/Styles_Images/ButtonStyles.css",
                "shirokuro/Main/Styles_Images/Background.css",
                "shirokuro/Main/Styles_Images/PaneBG.css",
                "shirokuro/Main/Styles_Images/TextStyles.css",
                "shirokuro/Main/Styles_Images/ShapesStyles.css",
                "shirokuro/Main/Styles_Images/RectangleStyles.css",
                "shirokuro/Main/Styles_Images/CellStyles.css");

        this.setScene(scene);
        this.initOwner(parentStage);
        this.initModality(Modality.WINDOW_MODAL);
        this.setTitle("Save game");
        this.getIcons().add(new Image("shirokuro/Main/Styles_Images/Images/logo.png"));
        this.setResizable(false);

    }

    private void saveButtonAction(boolean editGame){
        if(textField.getText().equals("")){
            screenText.setText("Empty file name.");
        }
        else{
            String fileName = textField.getText();
            File file;
            if(editGame) {
                file = new File("./Load/" + fileName + ".edit");
            }
            else{
                file = new File("./Load/" + fileName + ".lvl");
            }
            fileExist = file.exists();

            if(!fileExist) {
                screenText.setText("Game was saved");
                if(editGame){
                    try{
                        Shirokuro.getEditStage().editState.editSaveGame(textField.getText());
                    }catch (Exception err){
                        err.printStackTrace();
                    }
                }
                else {
                    try {
                        Shirokuro.getGameStage().state.saveGame(textField.getText());
                    } catch (Exception err) {
                        err.printStackTrace();
                    }
                }
            }else{
                overwrite = new OverwriteStage(this, textField.getText(), editGame);
                overwrite.show();
            }
        }
        fileExist = true;

    }
    private void getBackButtonAction(){
        this.close();
        screenText.setText("");
    }
}
