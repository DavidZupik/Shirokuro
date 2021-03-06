package shirokuro.Main.Stages;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * trieda ktora vyhodi okno kde sa vypise ze,
 * level ktory bol naeditovany nie je riesitelny
 */
public class EditBadSave extends Stage {

    /**
     * konstruktor triedy
     * @param parentStage stage z ktoreho bola tato trieda zavolana
     */
    public EditBadSave(Stage parentStage){
        BorderPane root;
        BorderPane bottomPane;
        Text text;
        Button ok = new Button("Ok");

        root = new BorderPane();
        bottomPane = new BorderPane();

        text = new Text("Please, make solvable level.\n");
        text.getStyleClass().add("lbTimer");

        ok.getStyleClass().add("bottomButtonStyle");
        ok.setAlignment(Pos.BOTTOM_CENTER);
        ok.setOnAction(e-> this.close());

        bottomPane.setCenter(ok);
        bottomPane.setPrefHeight(50);

        root.getStyleClass().add("bad-save");
        root.setCenter(text);
        root.setBottom(bottomPane);

        Scene scene = new Scene(root, 300, 100);
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
}
