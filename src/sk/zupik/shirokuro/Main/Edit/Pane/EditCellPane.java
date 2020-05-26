package sk.zupik.shirokuro.Main.Edit.Pane;

import sk.zupik.shirokuro.Main.Direction;
import sk.zupik.shirokuro.Main.Game.States.CellState;
import sk.zupik.shirokuro.Main.Shirokuro;
import sk.zupik.shirokuro.Main.Stages.EditStage;
import sk.zupik.shirokuro.Main.States;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;


public class EditCellPane extends Pane {

    int row;
    int col;
    double height;
    double width;

    public EditCellPane(int row, int col){
        this.row = row;
        this.col = col;
        height = (420. / Shirokuro.getEditStage().editState.size);
        width = (420. / Shirokuro.getEditStage().editState.size);
        this.setOnMouseClicked(e->{
            Shirokuro.getEditStage().editState.cells[row][col].state = EditStage.state;
            Shirokuro.getEditStage().solvable = false;
            makeOccupyFree();
            Shirokuro.getEditStage().setCenterCircleFill();
            Shirokuro.getEditStage().editPane.paint();
        });
    }

    public void paint(){
        getChildren().clear();
        CellState cell = Shirokuro.getEditStage().editState.cells[row][col];

        Circle c = new Circle();
        Region rect = new Region();
        StackPane sp = new StackPane();

        rect.setPrefWidth(width);
        rect.setPrefHeight(height);
        rect.setStyle("-fx-background-color: transparent; -fx-border-style: solid; -fx-border-width: 1; -fx-border-color: black;");

        c.setCenterY(height / 2);
        c.setCenterX(width / 2);
        c.setRadius(height / 2 - 1);
        rect.getStyleClass().add("edit-cell");

        if(cell.state == States.WHITE){
            c.setStyle("-fx-fill: white");
        }
        else if(cell.state == States.BLACK){
            c.setStyle("-fx-fill: black");
        }
        else{
            c.setStyle("-fx-fill: transparent");
        }
        sp.getChildren().addAll(rect, c);
        getChildren().add(sp);

    }

    public static void makeOccupyFree(){
        for (CellState[] cell : Shirokuro.getEditStage().editState.cells) {
            for (CellState cellState : cell) {
                if(cellState.state == States.OCCUPY_HORIZ || cellState.state == States.OCCUPY_VERTI){
                    cellState.state = States.FREE;
                }
                if(cellState.state == States.BLACK || cellState.state == States.WHITE){
                    cellState.direction = Direction.NONE;
                }
            }
        }
    }

}
