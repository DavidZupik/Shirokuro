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

    /**
     * uchovava informaciu ci je bunka zakliknuta alebo nie
     */
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
    /**
     * @return stav bunky
     */
    public States getState() {
        return state;
    }
    /**
     * @return smer bunky
     */
    public Direction getDirection() {
        return direction;
    }
    /**
     * metoda nastavy smer bunky
     * @param direction smer na ktory sa ma nastavit bunka
     */
    public void setDirection(Direction direction){
        this.direction = direction;
    }
    /**
     * metoda nastavy stav bunky
     * @param state stav na ktory sa ma bunka nastavit
     */
    public void setState(States state) {
        this.state = state;
    }

}
