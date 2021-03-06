package shirokuro.Main.Stages;

import shirokuro.Main.Enums.Direction;
import shirokuro.Main.Edit.Pane.EditCellPane;
import shirokuro.Main.Edit.Pane.EditPane;
import shirokuro.Main.Edit.States.EditState;
import shirokuro.Main.Game.States.CellState;
import shirokuro.Main.Shirokuro;
import shirokuro.Main.Enums.States;
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

/**
 * trieda ktora vyhodi okno s editovatelnou hraciou plochou
 */
public class EditStage extends Stage {

    SaveGame saveGame;
    EditBadSave badSave;
    HBox leftButtons;
    HBox rightButtons;
    BorderPane root;
    BorderPane topPane;
    BorderPane bottomPane;
    Button whiteCellButton;
    Button blackCellButton;
    Button removeCellButton;
    Button plusButton;
    Button minusButton;
    Button getBack = new Button("Back");
    Button save = new Button("Save");
    Button check = new Button("Check");

    /**
     * stav naeditovanej hry
     */
    public EditState editState;
    /**
     * stav na ktory sa zmeni kliknuta bunka
     */
    public static States state = States.FREE;
    /**
     * plocha na ktorej je vykreslena naeditovana hra
     */
    public EditPane editPane;
    /**
     * pocet buniek N*N
     */
    public int size = 10;
    /**
     * stav ci je naeditovana hra riesitelna
     */
    public boolean solvable = false;
    /**
     * kruh ktory ukazuje stav solvable<br>
     * cervena pre neriesitelnu hru<br>
     * zelena pre riesitelnu hru
     */
    public Circle solvableCircle;
    /**
     * pole s parmi
     */
    public HashMap<CellState, CellState> pairs;


    /**
     * konstruktor triedy
     */
    public EditStage(){
        editState = new EditState(size);
        editPane = new EditPane();
        editPane.setAlignment(Pos.CENTER);
    }
    /**
     * nastavenie komponentov ktore sa zobrazia v scene
     */
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

        getBack.getStyleClass().addAll("bottomButtonStyle", "bottomButtonStyle-edit");
        getBack.setOnAction(e-> getBackButtonAction());

        check.getStyleClass().addAll("bottomButtonStyle", "bottomButtonStyle-edit");
        check.setOnAction(e-> checkButtonAction());

        save.getStyleClass().addAll("bottomButtonStyle", "bottomButtonStyle-edit");
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

        scene.getStylesheets().addAll("shirokuro/Main/Styles_Images/ButtonStyles.css",
                "shirokuro/Main/Styles_Images/Background.css",
                "shirokuro/Main/Styles_Images/PaneBG.css",
                "shirokuro/Main/Styles_Images/TextStyles.css",
                "shirokuro/Main/Styles_Images/CellStyles.css");

        this.getIcons().add(new Image("shirokuro/Main/Styles_Images/Images/logo.png"));
        this.setTitle("Shirokuro - Level Edit");
        this.setResizable(false);
        this.setScene(scene);

        editPane.paint();

    }
    /**
     * metoda nastavi tlacitko na clicked
     * @param button tlacikto ktore bolo stlacenie
     */
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
    /**
     * zmenenie stredneho kruhu
     * cervena = naeditovany level sa neda ulozit (neriesitelne/neskotrolovane)
     * zelena = naeditovany level sa moze ulozit
     */
    public void setCenterCircleFill(){
        if(solvable){
            solvableCircle.setFill(Color.GREEN);
        }
        else{
            solvableCircle.setFill(Color.RED);
        }
    }
    /**
     * @return vrati true ak pocet bielych kruhov == pocet ciernych kruhov != 0
     */
    public boolean numberOfCirclesWB(){
        int b = 0;
        int w = 0;
        for (CellState[] cell : editState.cells) {
            for (CellState cellState : cell) {
                if(cellState.getState() == States.BLACK){
                    b++;
                }
                else if(cellState.getState() == States.WHITE){
                    w++;
                }
            }
        }
        return b == w && b != 0;
    }
    private Circle solvableCircleComponents(){
        Circle circle = new Circle();
        circle.setRadius(12.5);
        circle.setFill(Color.RED);
        return circle;
    }
    private void whiteCellButtonAction(){
        setClickedStyle(whiteCellButton);
        state = States.WHITE;
    }
    private Circle whiteButtonComponents(){
        Circle circle = new Circle();
        circle.setRadius(9);
        circle.setStyle("-fx-fill: white");
        return circle;
    }
    private void blackCellButtonAction(){
        setClickedStyle(blackCellButton);
        state = States.BLACK;
    }
    private Circle blackButtonComponents(){
        Circle circle = new Circle();
        circle.setRadius(9);
        circle.setStyle("-fx-fill: black");
        return circle;
    }
    private void removeCellButtonAction(){
        setClickedStyle(removeCellButton);
        state = States.FREE;
    }
    private void plusButtonAction(){
        if(size + 1 <= 20) {
            editState = new EditState(++size);
            editPane.paint();
        }
    }
    private StackPane plusButtonComponents(){
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
    private void minusButtonAction(){
        if(size - 1 >= 10) {
            editState = new EditState(--size);
            editPane.paint();
        }
    }
    private Rectangle minusButtonComponents(){
        Rectangle rectangle = new Rectangle();
        rectangle.setX(12.5);
        rectangle.setWidth(20);
        rectangle.setY(25./4);
        rectangle.setHeight(25./5);
        rectangle.setFill(Color.BLACK);
        return rectangle;
    }
    private void getBackButtonAction(){
        Shirokuro.getMainMenu().show();
        this.close();
    }
    private void saveButtonAction(){
        if(solvable) {
            EditCellPane.makeOccupyFree();
            saveGame = new SaveGame(this, true);
            saveGame.show();
        }
        else{
            badSave = new EditBadSave(this);
            badSave.show();
        }
    }
    private void checkButtonAction(){
        if(numberOfCirclesWB()) {
            editState.countCircles();
            pairs = new LinkedHashMap<>();
            if(isSolvable()) {
                solvable = !solvable;
                setCenterCircleFill();
            }
        }
    }
    private boolean isSolvable(){
        solvableBacktrack(findNextWhite(0, 0));
        return pairs.size() == editState.numberOfCircles;
    }
    private CellState findNextWhite(int row, int col){
        for (int j = col; j < editState.cells.length; j++) {
            if(!pairs.containsKey(editState.cells[row][j])) {
                if (editState.cells[row][j].getState() == States.WHITE) {
                    return editState.cells[row][j];
                }
            }
        }

        for (int i = row+1; i < editState.cells.length; i++) {
            for (int j = 0; j < editState.cells.length; j++) {
                if(editState.cells[i][j].getState() == States.WHITE){
                    return editState.cells[i][j];
                }
            }
        }
        return null;
    }
    private void solvableBacktrack(CellState cell){

        //going top
        if (cell != null && cell.getRow()-1 >= 0){
            for (int row = cell.getRow()-1; row >= 0; row--) {
                if (editState.cells[row][cell.getCol()].getState() == States.BLACK
                        && editState.cells[row][cell.getCol()].getDirection() == Direction.NONE) {
                    CellState blackCell = editState.cells[row][cell.getCol()];
                    addPair(cell, blackCell);
                    solvableBacktrack(findNextWhite(cell.getRow(), cell.getCol()));
                }
                if(editState.cells[row][cell.getCol()].getState() == States.FREE) {
                    continue;
                }
                break;
            }
        }

        //going down
        if (cell != null && cell.getRow()+1 < editState.size){
            for (int row = cell.getRow()+1; row < editState.size; row++) {
                if(editState.cells[row][cell.getCol()].getState() == States.FREE) {
                    continue;
                }
                if (editState.cells[row][cell.getCol()].getState() == States.BLACK
                        && editState.cells[row][cell.getCol()].getDirection() == Direction.NONE) {
                    CellState blackCell = editState.cells[row][cell.getCol()];
                    addPair(cell, blackCell);
                    solvableBacktrack(findNextWhite(cell.getRow(), cell.getCol()));
                }
                break;
            }
        }

        //going left
        if (cell != null && cell.getCol()-1 >= 0){
            for (int col = cell.getCol() - 1; col >= 0 ; col--) {
                if (editState.cells[cell.getRow()][col].getState() == States.FREE) {
                    continue;
                }
                if (editState.cells[cell.getRow()][col].getState() == States.BLACK
                        && editState.cells[cell.getRow()][col].getDirection() == Direction.NONE) {
                    CellState blackCell = editState.cells[cell.getRow()][col];
                    addPair(cell, blackCell);
                    solvableBacktrack(findNextWhite(cell.getRow(), cell.getCol()));
                }
                break;
            }
        }

        //going right
        if (cell != null && cell.getCol()+1 < editState.size){
            for (int col = cell.getCol() + 1; col < editState.size ; col++) {
                if (editState.cells[cell.getRow()][col].getState() == States.FREE) {
                    continue;
                }
                if (editState.cells[cell.getRow()][col].getState() == States.BLACK
                        && editState.cells[cell.getRow()][col].getDirection() == Direction.NONE) {
                    CellState blackCell = editState.cells[cell.getRow()][col];
                    addPair(cell, blackCell);
                    solvableBacktrack(findNextWhite(cell.getRow(), cell.getCol()));
                }
                break;
            }
        }

        if(pairs.size() == editState.numberOfCircles){
            return;
        }
        deleteLastPair();
    }
    private void addPair(CellState cell1, CellState cell2){
        makePipeBetween(cell1, cell2);
        pairs.put(cell1, cell2);
        pairs.put(cell2, cell1);
    }
    private void makePipeBetween(CellState cell1, CellState cell2){
        if(cell1.getCol() == cell2.getCol()){
            if(cell1.getRow() < cell2.getRow()){
                for (int i = cell1.getRow()+1; i < cell2.getRow(); i++) {
                    editState.cells[i][cell1.getCol()].setState(States.OCCUPY_VERTI);
                }
                cell1.setDirection(Direction.DOWN);
                cell2.setDirection(Direction.UP);
            }
            else{
                for (int i = cell2.getRow()+1; i < cell1.getRow(); i++) {
                    editState.cells[i][cell1.getCol()].setState(States.OCCUPY_VERTI);
                }
                cell1.setDirection(Direction.UP);
                cell2.setDirection(Direction.DOWN);
            }
        }
        else{
            if(cell1.getCol() < cell2.getCol()){
                for (int i = cell1.getCol()+1; i < cell2.getCol(); i++) {
                    editState.cells[cell1.getRow()][i].setState(States.OCCUPY_HORIZ);
                }
                cell1.setDirection(Direction.RIGHT);
                cell2.setDirection(Direction.LEFT);
            }
            else{
                for (int i = cell2.getCol()+1; i < cell1.getCol(); i++) {
                    editState.cells[cell1.getRow()][i].setState(States.OCCUPY_HORIZ);
                }
                cell1.setDirection(Direction.LEFT);
                cell2.setDirection(Direction.RIGHT);
            }
        }
    }
    private void deleteLastPair(){
        if(pairs.size() > 0){
            List<CellState> list = new ArrayList<>(pairs.keySet());
            CellState cell1 = list.remove(list.size() - 1);
            CellState cell2 = list.remove(list.size() - 1);
            deletePipeBetween(cell1, cell2);
            pairs.remove(cell1);
            pairs.remove(cell2);
        }
    }
    private void deletePipeBetween(CellState cell1, CellState cell2){
        if(cell1.getCol() == cell2.getCol()){
            if(cell1.getRow() < cell2.getRow()){
                for (int i = cell1.getRow()+1; i < cell2.getRow(); i++) {
                    editState.cells[i][cell1.getCol()].setState(States.FREE);
                }
            }
            else{
                for (int i = cell2.getRow()+1; i < cell1.getRow(); i++) {
                    editState.cells[i][cell1.getCol()].setState(States.FREE);
                }
            }
        }
        else{
            if(cell1.getCol() < cell2.getCol()){
                for (int i = cell1.getCol()+1; i < cell2.getCol(); i++) {
                    editState.cells[cell1.getRow()][i].setState(States.FREE);
                }
            }
            else{
                for (int i = cell2.getCol()+1; i < cell1.getCol(); i++) {
                    editState.cells[cell1.getRow()][i].setState(States.FREE);
                }
            }
        }
        cell1.setDirection(Direction.NONE);
        cell2.setDirection(Direction.NONE);
    }
}
