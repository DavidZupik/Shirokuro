package sk.zupik.shirokuro.Main.Stages;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class EditBadSave extends Stage {

    BorderPane root;
    BorderPane bottomPane;
    Text text;
    Button ok = new Button("Ok");

    /**
     * konstruktor triedy
     * @param parentStage stage z ktoreho bola tato trieda zavolana
     */
    public EditBadSave(Stage parentStage){
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

}
