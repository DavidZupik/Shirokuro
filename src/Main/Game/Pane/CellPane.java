package Main.Game.Pane;

import Main.Direction;
import Main.Game.States.CellState;
import Main.Game.States.GameState;
import Main.Shirokuro;
import Main.Stages.GameStage;
import Main.Stages.WinningStage;
import Main.States;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class CellPane extends Pane {

    int row;
    int col;
    double height;
    double width;

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
        if(cell.direction == Direction.UP){
            joins.setMaxSize(width/4, height/2);
            joins.getStyleClass().add("up-direction");
            sp.setAlignment(Pos.TOP_CENTER);
        }
        else if(cell.direction == Direction.DOWN){
            joins.setMaxSize(width/4, height/2);
            joins.getStyleClass().add("down-direction");
            sp.setAlignment(Pos.BOTTOM_CENTER);
        }
        else if(cell.direction == Direction.LEFT) {
            joins.setMaxSize(width/2, height/4);
            joins.getStyleClass().add("left-direction");
            sp.setAlignment(Pos.CENTER_LEFT);
        }
        else if(cell.direction == Direction.RIGHT) {
            joins.setMaxSize(width/2, height/4);
            joins.getStyleClass().add("right-direction");
            sp.setAlignment(Pos.CENTER_RIGHT);
        }
        else{
            joins.getStyleClass().add("none-direction");
        }

        //states of cell
        if(cell.state == States.WHITE){
            c.setStyle("-fx-fill: white");
        }
        else if(cell.state == States.BLACK){
            c.setStyle("-fx-fill: black");
        }
        else{
            c.setStyle("-fx-fill: transparent");
            if(cell.state == States.OCCUPY_HORIZ){
                joins.setMaxSize(width, height/4);
                joins.getStyleClass().add("horiz-verti-state");
            }
            else if(cell.state == States.OCCUPY_VERTI){
                joins.setMaxSize(width/4, height);
                joins.getStyleClass().add("horiz-verti-state");
            }
        }

        sp.getChildren().addAll(rect, c, joins);
        getChildren().add(sp);
    }
    public void checkCorrectness(CellState cell){
        if (GameState.clickedCell == null) {
            firstClick(cell);
        } else {
            secondClick(cell);
        }
    }
    public void firstClick(CellState cell){
        if(cell.state == States.OCCUPY_HORIZ){
            GameState.releaseCellH(cell, 0);
        }
        else if(cell.state == States.OCCUPY_VERTI){
            GameState.releaseCellV(cell, 0);
        }
        else if(cell.state == States.WHITE || cell.state == States.BLACK){
            if(cell.direction != Direction.NONE){
                GameState.releaseCellBW(cell);
            }
            else{
                cell.clicked = true;
                GameState.clickedCell = cell;
            }
        }
        Shirokuro.getGameStage().gamePane.paint();
    }
    public void secondClick(CellState cell){
        if(cell.state == States.BLACK || cell.state == States.WHITE) {
            if (cell.state != GameState.clickedCell.state) {
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
    public void winGameStage(){
        Shirokuro.getGameStage().close();
        Shirokuro.setWinningStage(new WinningStage(GameStage.getLvl()));
        Shirokuro.getWinningStage().show();
        Shirokuro.setGameStage(null);
    }

}
