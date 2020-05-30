package shirokuro.Main.Game.Pane;

import shirokuro.Main.Enums.Direction;
import shirokuro.Main.Game.States.CellState;
import shirokuro.Main.Game.States.GameState;
import shirokuro.Main.Shirokuro;
import shirokuro.Main.Stages.GameStage;
import shirokuro.Main.Stages.WinningStage;
import shirokuro.Main.Enums.States;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * trieda ktora vykresluje bunku na ploche
 */
public class CellPane extends Pane {

    int row;
    int col;
    double height;
    double width;

    /**
     * konstruktor triedy
     * @param row urcuje riadok bunky
     * @param col urcuje stlpec bunky
     */
    public CellPane(int row, int col){

        this.row = row;
        this.col = col;
        height = (425. / Shirokuro.getGameStage().state.size - 1);
        width = (425. / Shirokuro.getGameStage().state.size - 1);

        this.setOnMouseClicked(e-> {
            CellState cell = Shirokuro.getGameStage().state.getCell(row, col);
            checkCorrectness(cell);
        });

    }
    /**
     * kreslenie bunky
     */
    public void paint(){
        getChildren().clear();
        CellState cell = Shirokuro.getGameStage().state.getCell(row, col);

        Rectangle rect = new Rectangle();
        Circle c = new Circle();
        Region joins = new Region();
        StackPane sp = new StackPane();

        rect.setWidth(width);
        rect.setHeight(height);

        c.setCenterY(height / 2);
        c.setCenterX(width / 2);
        c.setRadius(height / 2);

        //clicked or not clicked cell
        if(cell.clicked){
            rect.getStyleClass().add("clicked-cell");
        }else {
            rect.getStyleClass().add("no-clicked-cell");
        }

        //directions of circles
        Direction cellDirection = cell.getDirection();
        if(cellDirection == Direction.UP){
            joins.setMaxSize(width/4, height/2);
            joins.getStyleClass().add("up-direction");
            sp.setAlignment(Pos.TOP_CENTER);
        }
        else if(cellDirection == Direction.DOWN){
            joins.setMaxSize(width/4, height/2);
            joins.getStyleClass().add("down-direction");
            sp.setAlignment(Pos.BOTTOM_CENTER);
        }
        else if(cellDirection == Direction.LEFT) {
            joins.setMaxSize(width/2, height/4);
            joins.getStyleClass().add("left-direction");
            sp.setAlignment(Pos.CENTER_LEFT);
        }
        else if(cellDirection == Direction.RIGHT) {
            joins.setMaxSize(width/2, height/4);
            joins.getStyleClass().add("right-direction");
            sp.setAlignment(Pos.CENTER_RIGHT);
        }
        else{
            joins.getStyleClass().add("none-direction");
        }

        //states of cell
        States cellState = cell.getState();
        if(cellState == States.WHITE){
            c.setStyle("-fx-fill: white");
        }
        else if(cellState == States.BLACK){
            c.setStyle("-fx-fill: black");
        }
        else{
            c.setStyle("-fx-fill: transparent");
            if(cellState == States.OCCUPY_HORIZ){
                joins.setMaxSize(width, height/4);
                joins.getStyleClass().add("horiz-verti-state");
            }
            else if(cellState == States.OCCUPY_VERTI){
                joins.setMaxSize(width/4, height);
                joins.getStyleClass().add("horiz-verti-state");
            }
        }

        sp.getChildren().addAll(rect, c, joins);
        getChildren().add(sp);
    }

    private void checkCorrectness(CellState cell){
        if (GameState.clickedCell == null) {
            firstClick(cell);
        } else {
            secondClick(cell);
        }
    }
    private void firstClick(CellState cell){
        States cellState = cell.getState();
        Direction cellDirection = cell.getDirection();
        if(cellState == States.OCCUPY_HORIZ){
            GameState.releaseCellH(cell, 0);
        }
        else if(cellState == States.OCCUPY_VERTI){
            GameState.releaseCellV(cell, 0);
        }
        else if(cellState == States.WHITE || cellState == States.BLACK){
            if(cellDirection != Direction.NONE){
                GameState.releaseCellBW(cell);
            }
            else{
                cell.clicked = true;
                GameState.clickedCell = cell;
            }
        }
        Shirokuro.getGameStage().gamePane.paint();
    }
    private void secondClick(CellState cell){
        States cellState = cell.getState();
        if(cellState == States.BLACK || cellState == States.WHITE) {
            if (cellState  != GameState.clickedCell.getState()) {
                if (GameState.checkRowCol(cell)) {
                    if (GameState.checkFreeCell(cell)) {
                        GameState.joinCells(cell);
                        GameState.addPair(cell);
                    }
                }

            }
        }
        if(Shirokuro.getGameStage().state.getPairs().size() == Shirokuro.getGameStage().state.getNumberOfCircles()){
            winGameStage();
        }else{
            GameState.clickedCell.clicked = false;
            Shirokuro.getGameStage().gamePane.paint();
            GameState.clickedCell = null;
        }
    }
    private void winGameStage(){
        Shirokuro.setWinningStage(new WinningStage(GameStage.getLvl()));
        Shirokuro.getWinningStage().show();
        Shirokuro.getGameStage().close();
        Shirokuro.setGameStage(null);
    }

}
