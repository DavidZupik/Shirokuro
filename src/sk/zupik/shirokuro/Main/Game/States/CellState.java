package sk.zupik.shirokuro.Main.Game.States;

import sk.zupik.shirokuro.Main.States;
import sk.zupik.shirokuro.Main.Direction;

import java.io.Serializable;

public class CellState implements Serializable {

    public States state;
    public Direction direction;
    int row;
    int col;
    public boolean clicked;

    /**
     * konstruktor triedy
     * @param state  stav v ktorom sa bunka nachadza
     * @param direction  smer ktorym bunka smeruje
     * @param row  riadok bunky
     * @param col  stlpec bunky
     */
    public CellState(States state, Direction direction, int row, int col){
        clicked = false;
        this.direction = direction;
        this.state = state;
        this.row = row;
        this.col = col;
    }

    /**
     * @return vrati riadok bunky
     */
    public int getRow() {
        return row;
    }

    /**
     * @return vrati stlpec bunky
     */
    public int getCol() {
        return col;
    }
}
