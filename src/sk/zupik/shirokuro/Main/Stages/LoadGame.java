package sk.zupik.shirokuro.Main.Stages;

import sk.zupik.shirokuro.Main.Shirokuro;

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

    /**
     * cesta k ulozenym suborom
     */
    final File folder = new File(Shirokuro.directory + "./Load");
    /**
     * nazvy vsetkych ulozenych hier ktore su v subore Load
     */
    static ArrayList<String> result;

    /**
     * konstruktor triedy
     * @param parentStage stage z ktoreho bola tato trieda zavolana
     */
    LoadGame(Stage parentStage){
        Button back = new Button("Back");
        VBox textBox = new VBox();
        VBox root;
        BorderPane bottomPane;


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
        scene.getStylesheets().addAll("sk/zupik/shirokuro/Main/Styles_Images/ButtonStyles.css",
                "sk/zupik/shirokuro/Main/Styles_Images/Background.css",
                "sk/zupik/shirokuro/Main/Styles_Images/PaneBG.css",
                "sk/zupik/shirokuro/Main/Styles_Images/TextStyles.css",
                "sk/zupik/shirokuro/Main/Styles_Images/ShapesStyles.css");

        this.initOwner(parentStage);
        this.initModality(Modality.WINDOW_MODAL);
        this.setTitle("Shirokuro - Saved files");
        this.getIcons().add(new Image("sk/zupik/shirokuro/Main/Styles_Images/Images/logo.png"));
        this.setResizable(false);
        this.setScene(scene);

    }

    /**
     * metoda hlada vsetky zlozky z koncovkou lvl a edit v subore<br>
     * a vklada ich do pola
     * @param folder subor v ktorom hlada
     */
    public static void search(final File folder) {
        folder.mkdirs();
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
