package Main.Game.States;

import Main.States;
import Main.Direction;

import java.io.Serializable;

public class CellState implements Serializable {

    public States state;
    public Direction direction;
    int row;
    int col;
    public boolean clicked;

    public CellState(States state, Direction direction, int row, int col){
        clicked = false;
        this.direction = direction;
        this.state = state;
        this.row = row;
        this.col = col;
    }

    @Override
    public String toString() {
        return "[" + row + ", " +col + "] = " + state + "";
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
