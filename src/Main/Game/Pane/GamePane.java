package Main.Game.Pane;

import Main.Shirokuro;
import javafx.scene.layout.GridPane;

public class GamePane extends GridPane {

    public GamePane() {
        setWidth(425);
        setHeight(425);
    }

    public void paint() {
        this.getChildren().clear();
        for (int riadok = 0; riadok < Shirokuro.getGameStage().state.size; riadok++) {
            for (int stlpec = 0; stlpec < Shirokuro.getGameStage().state.size; stlpec++) {
                CellPane cell = new CellPane(riadok, stlpec);
                this.add(cell, stlpec, riadok);
                cell.paint();
            }
        }
    }

}
