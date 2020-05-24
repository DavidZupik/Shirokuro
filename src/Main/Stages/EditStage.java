package Main.Stages;

import Main.Direction;
import Main.Edit.Pane.EditPane;
import Main.Edit.States.EditState;
import Main.Game.States.CellState;
import Main.Shirokuro;
import Main.States;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class EditStage extends Stage {

    public EditState editState;
    public static States state = States.FREE;
    public EditPane editPane;
    SaveGame saveGame;

    BorderPane root;
    BorderPane topPane;
    HBox leftButtons;
    HBox rightButtons;
    BorderPane bottomPane;

    Button whiteCellButton;
    Button blackCellButton;
    Button removeCellButton;
    Button plusButton;
    Button minusButton;
    Button getBack = new Button("Back");
    Button save = new Button("Save");
    Button check = new Button("Check");

    public int size = 10;
    public boolean solvable = false;
    public Circle solvableCircle;
    public HashMap<CellState, CellState> pairs = new LinkedHashMap<>();


    public EditStage(){
        editState = new EditState(size);
        editPane = new EditPane();
        editPane.setAlignment(Pos.CENTER);
    }

    public void setUpEditStage(){
        solvableCircle = solvableCircleComponents();

        Circle wCircle = whiteButtonComponents();
        whiteCellButton = new Button("", wCircle);
        whiteCellButton.getStyleClass().add("edit-buttons");
        whiteCellButton.setOnAction(e-> whiteCellButtonAction());


        Circle bCircle = blackButtonComponents();
        blackCellButton = new Button("", bCircle);
        blackCellButton.getStyleClass().add("edit-buttons");
        blackCellButton.setOnAction(e-> blackCellButtonAction());


        removeCellButton = new Button("");
        removeCellButton.getStyleClass().add("edit-buttons");
        removeCellButton.setOnAction(e-> removeCellButtonAction());


        StackPane sp = plusButtonComponents();
        plusButton = new Button("", sp);
        plusButton.getStyleClass().add("edit-buttons");
        plusButton.setOnAction(e-> plusButtonAction());


        Rectangle minusRect = minusButtonComponents();
        minusButton = new Button("", minusRect);
        minusButton.getStyleClass().add("edit-buttons");
        minusButton.setOnAction(e-> minusButtonAction());


        leftButtons = new HBox();
        leftButtons.setPrefHeight(25);
        leftButtons.setSpacing(10);
        leftButtons.getChildren().addAll(whiteCellButton, blackCellButton, removeCellButton);

        rightButtons = new HBox();
        rightButtons.setPrefHeight(25);
        rightButtons.setSpacing(10);
        rightButtons.getChildren().addAll(plusButton, minusButton);

        topPane = new BorderPane();
        topPane.setStyle("-fx-background-color: linear-gradient(to right, #2b5876, #4e4376);");
        topPane.setRight(rightButtons);
        topPane.setCenter(solvableCircle);
        topPane.setLeft(leftButtons);

        getBack.getStyleClass().add("bottomButtonStyle");
        getBack.setOnAction(e-> getBackButtonAction());

        check.getStyleClass().add("bottomButtonStyle");
        check.setOnAction(e-> checkButtonAction());

        save.getStyleClass().add("bottomButtonStyle");
        save.setOnAction(e-> saveButtonAction());

        bottomPane = new BorderPane();
        bottomPane.setPrefHeight(50);
        bottomPane.setLeft(getBack);
        bottomPane.setCenter(check);
        bottomPane.setRight(save);

        root = new BorderPane();
        root.getStyleClass().add("edit-root");
        root.setTop(topPane);
        root.setCenter(editPane);
        root.setBottom(bottomPane);

        Scene scene = new Scene(root , 700, 500);

        scene.getStylesheets().addAll("Main/Styles_Images/ButtonStyles.css",
                "Main/Styles_Images/Background.css",
                "Main/Styles_Images/PaneBG.css",
                "Main/Styles_Images/TextStyles.css",
                "Main/Styles_Images/CellStyles.css");

        this.getIcons().add(new Image("./Main/Styles_Images/Images/logo.png"));
        this.setTitle("Shirokuro - Level Edit");
        this.setResizable(false);
        this.setScene(scene);

        editPane.paint();

    }

    public void setClickedStyle(Button button){

        whiteCellButton.getStyleClass().clear();
        blackCellButton.getStyleClass().clear();
        removeCellButton.getStyleClass().clear();

        if (button == whiteCellButton) {
            whiteCellButton.getStyleClass().add("edit-buttons-selected");
            blackCellButton.getStyleClass().add("edit-buttons");
            removeCellButton.getStyleClass().add("edit-buttons");
        }
        else if (button == blackCellButton) {
            blackCellButton.getStyleClass().add("edit-buttons-selected");
            whiteCellButton.getStyleClass().add("edit-buttons");
            removeCellButton.getStyleClass().add("edit-buttons");
        }
        else if (button == removeCellButton) {
            removeCellButton.getStyleClass().add("edit-buttons-selected");
            whiteCellButton.getStyleClass().add("edit-buttons");
            blackCellButton.getStyleClass().add("edit-buttons");
        }
    }

    public void setCenterCircleFill(){
        if(solvable){
            solvableCircle.setFill(Color.GREEN);
        }
        else{
            solvableCircle.setFill(Color.RED);
        }
    }

    public boolean numberOfCirclesWB(){
        int b = 0;
        int w = 0;
        for (CellState[] cell : editState.cells) {
            for (CellState cellState : cell) {
                if(cellState.state == States.BLACK){
                    b++;
                }
                else if(cellState.state == States.WHITE){
                    w++;
                }
            }
        }
        return b == w && b != 0;
    }

    Circle solvableCircleComponents(){
        Circle circle = new Circle();
        circle.setRadius(12.5);
        circle.setFill(Color.RED);
        return circle;
    }

    void whiteCellButtonAction(){
        setClickedStyle(whiteCellButton);
        state = States.WHITE;
    }
    Circle whiteButtonComponents(){
        Circle circle = new Circle();
        circle.setRadius(9);
        circle.setStyle("-fx-fill: white");
        return circle;
    }

    void blackCellButtonAction(){
        setClickedStyle(blackCellButton);
        state = States.BLACK;
    }
    Circle blackButtonComponents(){
        Circle circle = new Circle();
        circle.setRadius(9);
        circle.setStyle("-fx-fill: black");
        return circle;
    }

    void removeCellButtonAction(){
        setClickedStyle(removeCellButton);
        state = States.FREE;
    }

    void plusButtonAction(){
        if(size + 1 <= 20) {
            editState = new EditState(++size);
            editPane.paint();
        }
    }
    StackPane plusButtonComponents(){
        StackPane stackPane = new StackPane();
        Rectangle plusRect1 = new Rectangle();
        plusRect1.setX(12.5);
        plusRect1.setWidth(20);
        plusRect1.setY(25./4);
        plusRect1.setHeight(25./5);
        plusRect1.setFill(Color.BLACK);

        Rectangle plusRect2 = new Rectangle();
        plusRect2.setX(25./4);
        plusRect2.setWidth(25./5);
        plusRect2.setY(0);
        plusRect2.setHeight(20);
        plusRect2.setFill(Color.BLACK);

        stackPane.getChildren().addAll(plusRect1, plusRect2);
        return stackPane;
    }

    void minusButtonAction(){
        if(size - 1 >= 10) {
            editState = new EditState(--size);
            editPane.paint();
        }
    }
    Rectangle minusButtonComponents(){
        Rectangle rectangle = new Rectangle();
        rectangle.setX(12.5);
        rectangle.setWidth(20);
        rectangle.setY(25./4);
        rectangle.setHeight(25./5);
        rectangle.setFill(Color.BLACK);
        return rectangle;
    }

    void getBackButtonAction(){
        this.close();
        Shirokuro.getMainMenu().show();

    }
    void saveButtonAction(){
        if(solvable) {
            editState.countCircles();
            saveGame = new SaveGame(this, true);
            saveGame.show();
        }
        else{
            //todo show stage - you need to check if its solvable
        }
    }
    void checkButtonAction(){
        if(numberOfCirclesWB()) {
            if(isSolvable()) {
                solvable = !solvable;
                setCenterCircleFill();
            }
        }
    }

    boolean isSolvable(){
        return false;
    }

}