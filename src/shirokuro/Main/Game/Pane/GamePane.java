package shirokuro.Main.Game.Pane;

import shirokuro.Main.Shirokuro;
import javafx.scene.layout.GridPane;

/**
 * trieda na vykreslenie hracej plochy
 */
public class GamePane extends GridPane {

    /**
     * konstruktor triedy
     */
    public GamePane() {
        setWidth(425);
        setHeight(425);
    }
    /**
     * vykreslenie hracej plochy
     */
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
