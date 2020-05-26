package sk.zupik.shirokuro.Main.Stages;

import sk.zupik.shirokuro.Main.Shirokuro;
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
import java.util.ArrayList;


public class SaveGame extends Stage {

    Button save = new Button("SAVE");
    Button getBack = new Button("BACK");
    BorderPane root = new BorderPane();
    public static Text screenText = new Text();
    HBox topPane = new HBox();
    TextField textField = new TextField();
    BorderPane bottomPane = new BorderPane();
    OverwriteStage overwrite;
    boolean canWrite = true;
    boolean saveEditGame;

    public SaveGame(Stage parentStage, boolean editGame){

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
        scene.getStylesheets().addAll("sk/zupik/shirokuro/Main/Styles_Images/ButtonStyles.css",
                "sk/zupik/shirokuro/Main/Styles_Images/Background.css",
                "sk/zupik/shirokuro/Main/Styles_Images/PaneBG.css",
                "sk/zupik/shirokuro/Main/Styles_Images/TextStyles.css",
                "sk/zupik/shirokuro/Main/Styles_Images/ShapesStyles.css",
                "sk/zupik/shirokuro/Main/Styles_Images/RectangleStyles.css",
                "sk/zupik/shirokuro/Main/Styles_Images/CellStyles.css");

        this.setScene(scene);
        this.initOwner(parentStage);
        this.initModality(Modality.WINDOW_MODAL);
        this.setTitle("Save game");
        this.getIcons().add(new Image("sk/zupik/shirokuro/Main/Styles_Images/Images/logo.png"));
        this.setResizable(false);

    }
    private void saveButtonAction(boolean editGame){
        if(textField.getText().equals("")){
            screenText.setText("Empty file name.");
        }
        else{
            LoadGame.result = new ArrayList<>();
            LoadGame.search(new File(Shirokuro.directory + "\\Load"));
            for (String s : LoadGame.result) {
                if(s.equals(textField.getText())){
                    canWrite = false;
                }
            }

            if(canWrite) {
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
        canWrite = true;

    }
    private void getBackButtonAction(){
        this.close();
        screenText.setText("");
    }
}
