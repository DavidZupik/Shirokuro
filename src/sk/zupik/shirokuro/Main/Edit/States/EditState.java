package sk.zupik.shirokuro.Main.Edit.States;

import sk.zupik.shirokuro.Main.Direction;
import sk.zupik.shirokuro.Main.Game.States.CellState;
import sk.zupik.shirokuro.Main.States;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class EditState implements Serializable {

    private static final long serialVersionUID = 6529685098267757890L;
    public Integer size;
    public CellState[][] cells;
    public int numberOfCircles = 0;

    /**
     * konstruktor triedy
     * @param N velkost hracej plochy
     */
    public EditState(int N){
        size = N;
        cells = new CellState[size][size];
        fillArray();
    }

    /**
     * vyplnenie pola bunkami ktore maju stav free a smer none
     */
    public void fillArray(){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = new CellState(States.FREE, Direction.NONE, i, j);
            }
        }
    }

    /**
     * ulozenie naeditovanej hry
     * @param fileName nazov pod ktorym bude hra ulozena
     * @throws IOException zle nacitanie triedy ktora ma byt ulozena
     */
    public void editSaveGame(String fileName) throws IOException {
        ObjectOutputStream fs = new ObjectOutputStream(new FileOutputStream("./Load/" + fileName + ".edit"));
        fs.writeObject(this);
        fs.close();
    }

    /**
     * spocita pocet kruhov
     */
    public void countCircles(){
        numberOfCircles = 0;
        for (CellState[] cell : cells) {
            for (CellState cellState : cell) {
                if(cellState.getState() != States.FREE){
                    numberOfCircles++;
                }
            }
        }
    }

}
