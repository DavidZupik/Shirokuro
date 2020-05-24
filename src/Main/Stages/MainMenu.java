package Main.Stages;

import Main.Shirokuro;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainMenu extends Stage {

    //center pane
    VBox mRoot;
    Text titleText = new Text();
    Button play = new Button("Play");
    Button edit = new Button("Edit");
    Button help = new Button("Help");
    Button exit = new Button("Exit");

    //top pane - music settings
    HBox topPane = new HBox();
    Button audio;
    Button plus;
    Button minus;
    boolean sound = true;
    ImageView audioIMG;
    ImageView noAudioIMG;
    ImageView plusIMG;
    ImageView minusIMG;

    //botompane autor name
    HBox bottomPane = new HBox();
    Text name = new Text("©Dávid Župík");


    public MainMenu(){

        loadImages();
        //top pane
        //set buttons
        audio = new Button("", audioIMG);
        plus = new Button("", plusIMG);
        minus = new Button("", minusIMG);
        //audio button
        audio.getStyleClass().add("sound-buttons");
        audio.setOnAction(e -> audioButtonAction());
        //minus button
        minus.getStyleClass().add("sound-buttons");
        minus.setOnAction( e -> Shirokuro.getSounds().volumeDOWN());
        //plus button
        plus.getStyleClass().add("sound-buttons");
        plus.setOnAction( e -> Shirokuro.getSounds().volumeUP());
        //add buttons to top pane
        topPane.getChildren().addAll(minus, audio, plus);
        topPane.setAlignment(Pos.TOP_RIGHT);

        //bottom pane
        bottomPane.getChildren().add(name);
        bottomPane.setMaxSize(20, 10);

        //rcenter pane - root
        titleText.setText("Shirokuro");
        titleText.getStyleClass().add("textTitle");
        titleText.requestFocus();

        play.setAlignment(Pos.TOP_CENTER);
        play.getStyleClass().add("buttonStyle");
        play.setOnAction(e-> playButtonAction() );

        edit.setAlignment(Pos.CENTER);
        edit.getStyleClass().add("buttonStyle");
        edit.setOnAction(e-> editButtonAction() );

        help.setAlignment(Pos.CENTER);
        help.getStyleClass().add("buttonStyle");
        help.setOnAction(e-> helpButtonAction() );

        exit.setAlignment(Pos.BOTTOM_CENTER);
        exit.getStyleClass().add("buttonStyle");
        exit.setOnAction(e -> Platform.exit());

        mRoot = new VBox(topPane, titleText, play, edit, help, exit, bottomPane);
        mRoot.getStyleClass().add("bgM");
        mRoot.setAlignment(Pos.CENTER);
        mRoot.setSpacing(25);

        Scene scene = new Scene(mRoot, 700, 500);
        scene.getStylesheets().addAll("Main/Styles_Images/ButtonStyles.css",
                                            "Main/Styles_Images/Background.css",
                                            "Main/Styles_Images/PaneBG.css",
                                            "Main/Styles_Images/TextStyles.css",
                                            "Main/Styles_Images/ShapesStyles.css");

        this.getIcons().add(new Image("./Main/Styles_Images/Images/logo.png"));
        this.setTitle("Shirokuro - MainMenu");
        this.setResizable(false);
        this.setScene(scene);

    }

    void playButtonAction(){
        Shirokuro.setLvlSelection(new LevelSelection());
        Shirokuro.getLvlSelection().show();
        this.close();
    }
    void editButtonAction(){
        Shirokuro.setEditStage(new EditStage());
        Shirokuro.getEditStage().setUpEditStage();
        Shirokuro.getEditStage().show();
        this.close();
    }
    void helpButtonAction(){
        Shirokuro.setHelp(new Help());
        Shirokuro.getHelp().show();
        this.close();
    }
    void audioButtonAction(){
        if(sound){
            Shirokuro.getSounds().pause();
            audio.setGraphic(noAudioIMG);
            sound = false;
        }
        else{
            Shirokuro.getSounds().play();
            audio.setGraphic(audioIMG);
            sound = true;
        }
    }
    void loadImages(){
        audioIMG = new ImageView(new Image("./Main/Styles_Images/Images/audio.png"));
        noAudioIMG = new ImageView(new Image("./Main/Styles_Images/Images/noaudio.png"));
        minusIMG = new ImageView(new Image("./Main/Styles_Images/Images/minus.png"));
        plusIMG = new ImageView(new Image("./Main/Styles_Images/Images/plus.png"));
    }


}
