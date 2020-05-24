package Main.Stages;

import Main.Shirokuro;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class LoadGame extends Stage {

    Button back = new Button("Back");
    final File folder = new File(Shirokuro.directory);
    static ArrayList<String> result;
    VBox textBox = new VBox();
    VBox root;
    BorderPane bottomPane;

    LoadGame(Stage parentStage){

        result = new ArrayList<>();

        search(folder);
        ScrollPane scrollPane = new ScrollPane();

        for (String s : result) {
            Text lfile = new Text(s);
            FlowPane fp = new FlowPane(lfile);
            fp.getStyleClass().add("fp");
            lfile.getStyleClass().add("lfile");
            lfile.setOnMouseClicked(e -> {
                parentStage.close();
                try {
                    Shirokuro.setGameStage(new GameStage(lfile.getText()));
                    Shirokuro.getGameStage().setUpStage();
                    Shirokuro.getGameStage().show();
                } catch (Exception err) {
                    err.printStackTrace();
                }
            });
            textBox.getChildren().add(fp);
        }
        textBox.setAlignment(Pos.CENTER);

        scrollPane.setContent(textBox);
        scrollPane.setPrefSize(500, 450);
        scrollPane.setFitToWidth(true);

        back.getStyleClass().add("loadButtonStyle");
        back.setAlignment(Pos.CENTER);
        back.setOnAction(e -> this.close());

        bottomPane = new BorderPane();
        bottomPane.setCenter(back);

        root = new VBox(scrollPane, bottomPane);
        root.getStyleClass().add("bgLoad");

        Scene scene = new Scene(root, 300, 500);
        scene.getStylesheets().addAll("Main/Styles_Images/ButtonStyles.css",
                "Main/Styles_Images/Background.css",
                "Main/Styles_Images/PaneBG.css",
                "Main/Styles_Images/TextStyles.css",
                "Main/Styles_Images/ShapesStyles.css");

        this.initOwner(parentStage);
        this.initModality(Modality.WINDOW_MODAL);
        this.setTitle("Shirokuro - Saved files");
        this.getIcons().add(new Image("./Main/Styles_Images/Images/logo.png"));
        this.setResizable(false);
        this.setScene(scene);

    }

    public static void search(final File folder) {

        for (final File f : Objects.requireNonNull(folder.listFiles())) {
            if (f.isDirectory()) {
                search(f);
            }
            if (f.isFile()) {
                if (f.getName().matches(".*\\.lvl")) {
                    StringBuilder s = new StringBuilder(f.getName());
                    result.add(s.substring(0, f.getName().length()-4));
                }
                else if(f.getName().matches(".*\\.edit")){
                    StringBuilder s = new StringBuilder(f.getName());
                    result.add(s.substring(0, f.getName().length()-5));
                }
            }
        }
    }

}
