package sk.zupik.shirokuro.Main.Edit.Pane;

import sk.zupik.shirokuro.Main.Shirokuro;
import javafx.scene.layout.GridPane;

public class EditPane extends GridPane {

    /**
     * konstruktor triedy
     */
    public EditPane(){
        setWidth(420);
        setHeight(420);
    }

    /**
     * vykreslenie editovacej plochy
     */
    public void paint(){
        getChildren().clear();
        for (int i = 0; i < Shirokuro.getEditStage().size; i++) {
            for (int j = 0; j < Shirokuro.getEditStage().size; j++) {
                EditCellPane cell = new EditCellPane(i, j);
                this.add(cell, j, i);
                cell.paint();
            }
        }
    }
}
