package shirokuro.Main.Game.States;

import shirokuro.Main.Enums.States;
import shirokuro.Main.Enums.Direction;

import java.io.Serializable;

/**
 * trieda ktora uchovava stav bunky
 */
public class CellState implements Serializable {

    States state;
    Direction direction;
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

    public States getState() {
        return state;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction){
        this.direction = direction;
    }

    public void setState(States state) {
        this.state = state;
    }
}
