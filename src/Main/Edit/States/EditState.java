package Main.Edit.States;

import Main.Direction;
import Main.Game.States.CellState;
import Main.States;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

public class EditState implements Serializable {

    private static final long serialVersionUID = 6529685098267757890L;
    public Integer size;
    public CellState[][] cells;
    public int numberOfCircles = 0;

    public EditState(int N){
        size = N;
        cells = new CellState[size][size];
        fillArray();
    }
    public void fillArray(){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = new CellState(States.FREE, Direction.NONE, i, j);
            }
        }
    }
    public void editSaveGame(String fileName) throws IOException {
        ObjectOutputStream fs = new ObjectOutputStream(new FileOutputStream("./src/Load/" + fileName + ".edit"));
        fs.writeObject(this);
        fs.close();
    }
    public void countCircles(){
        for (CellState[] cell : cells) {
            for (CellState cellState : cell) {
                if(cellState.state != States.FREE){
                    numberOfCircles++;
                }
            }
        }
    }

}
