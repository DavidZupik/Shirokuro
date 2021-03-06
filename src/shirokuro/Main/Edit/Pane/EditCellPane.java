package shirokuro.Main.Edit.Pane;

import shirokuro.Main.Enums.Direction;
import shirokuro.Main.Game.States.CellState;
import shirokuro.Main.Shirokuro;
import shirokuro.Main.Stages.EditStage;
import shirokuro.Main.Enums.States;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

/**
 * trieda na vykreslenie bunky v editovaciom rezime
 */
public class EditCellPane extends Pane {

    private final int row;
    private final int col;
    private final double height;
    private final double width;
    /**
     * konstruktor triedy
     * @param row riadok bunky
     * @param col stlpec bunky
     */
    public EditCellPane(int row, int col){
        this.row = row;
        this.col = col;
        height = (420. / Shirokuro.getEditStage().editState.size);
        width = (420. / Shirokuro.getEditStage().editState.size);
        this.setOnMouseClicked(e->{
            Shirokuro.getEditStage().editState.cells[row][col].setState(EditStage.state);
            Shirokuro.getEditStage().solvable = false;
            makeOccupyFree();
            Shirokuro.getEditStage().setCenterCircleFill();
            Shirokuro.getEditStage().editPane.paint();
        });
    }
    /**
     * vykreslenie bunky
     */
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

        if(cell.getState() == States.WHITE){
            c.setStyle("-fx-fill: white");
        }
        else if(cell.getState() == States.BLACK){
            c.setStyle("-fx-fill: black");
        }
        else{
            c.setStyle("-fx-fill: transparent");
        }
        sp.getChildren().addAll(rect, c);
        getChildren().add(sp);

    }
    /**
     * vsetky bunky ktore su v inom stave ako white alebo black vrati do stavu free
     * bunkam ktore maju stav white alebo black hodi smer none
     */
    public static void makeOccupyFree(){
        for (CellState[] cell : Shirokuro.getEditStage().editState.cells) {
            for (CellState cellState : cell) {
                if(cellState.getState() == States.OCCUPY_HORIZ || cellState.getState() == States.OCCUPY_VERTI){
                    cellState.setState(States.FREE);
                }
                if(cellState.getState() == States.BLACK || cellState.getState() == States.WHITE){
                    cellState.setDirection(Direction.NONE);
                }
            }
        }
    }

}
