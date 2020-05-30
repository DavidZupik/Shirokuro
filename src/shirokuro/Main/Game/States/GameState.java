package shirokuro.Main.Game.States;

import shirokuro.Main.Enums.Direction;
import shirokuro.Main.Edit.States.EditState;
import shirokuro.Main.Shirokuro;
import shirokuro.Main.Enums.States;
import shirokuro.Main.Util.FileResolver;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;

/**
 * trieda ktora uchovava stav hry
 */
public class GameState implements Serializable {

    private static final long serialVersionUID = 6529685098267757690L;
    public Integer size;
    public CellState[][] cells;
    private static final String FILE_PATH = "Levels/";
    public Integer time;
    public int numberOfCircles;
    public static CellState clickedCell = null;
    public HashMap<CellState, CellState> pairs;


    /**
     * konstruktor triedy
     * @param levelNumber cislo levelu ktory sa nacita
     */
    public GameState(int levelNumber){
        time = 0;
        numberOfCircles = 0;
        size = null;
        pairs = new HashMap<>();
        loadLevelCFG(String.valueOf(levelNumber));
    }
    /**
     * konstruktor triedy
     * @param state v tejto premenej je ulozeny naeditovany level
     */
    public GameState(EditState state){
        this.size = state.size;
        this.cells = Arrays.copyOf(state.cells, state.cells.length);
        this.time = 0;
        this.numberOfCircles = state.numberOfCircles;
        pairs = new HashMap<>();
    }
    /**
     * nacita konfiguraciu hry zo suboru
     * @param levelName urcuje z ktoreho suboru ma byt hra nahrana
     */
    public void loadLevelCFG(String levelName){
        String line;
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    FileResolver.getInputStream(FILE_PATH + levelName + ".txt")));
            int i = 0;
            while((line = br.readLine()) != null){
                if(size == null) {
                    size = Integer.valueOf(line);
                    cells = new CellState[size][size];
                }
                else{
                    String[] arr;
                    arr = line.split(" ");
                    for (int j = 0; j < size; j++) {
                        if(arr[j].length() == 1) {
                            cells[i][j] = new CellState(getState(arr[j]), Direction.NONE, i, j);
                        }
                        else{
                            cells[i][j] = new CellState(getState(arr[j].charAt(0) + ""), getDirection(arr[j].charAt(1) + ""), i, j);
                        }
                    }
                    i++;
                }
            }
            br.close();
        }catch (Exception e){
            System.err.println("Something went wrong with loading " + e.getLocalizedMessage());
        }
    }
    /**
     * ulozenie rozohranej hry
     * @param fileName urcuje ako sa ma subor volat
     * @throws IOException zle nacitanie triedy ktora ma byt ulozena do subora<br>
     *                     nenajdenie suboru do ktoreho ma byt trieda ulozena
     */
    public void saveGame(String fileName) throws IOException {
        if(!new File(Shirokuro.directory + "./Load").exists()) {
            new File(Shirokuro.directory + "./Load").mkdirs();
        }
        ObjectOutputStream fs = new ObjectOutputStream(new FileOutputStream("./Load/" + fileName + ".lvl"));
        fs.writeObject(this);
        fs.close();
    }
    /**
     * nacitanie ulozenej hry
     * @param fileName fileName urcuje ktory subor ma byt nahrany
     * @return vrati objekt v ktorom je hra ulozena
     * @throws IOException zle nacitanie triedy ktora ma byt ulozena do subora<br>
     *                     nenajdenie suboru do ktoreho ma byt trieda ulozena
     * @throws ClassNotFoundException nenajdenie suboru s triedou
     */
    public static GameState loadGame(String fileName) throws IOException, ClassNotFoundException {
        ObjectInputStream is = new ObjectInputStream(new FileInputStream("./Load/" + fileName + ".lvl"));
        GameState gameState = (GameState)is.readObject();
        is.close();
        return gameState;
    }
    /**
     * nacitanie ulozenej hry
     * @param fileName fileName urcuje ktory subor ma byt nahrany
     * @return vrati objekt v ktorom je hra ulozena
     * @throws IOException zle nacitanie triedy ktora ma byt ulozena do subora<br>
     *                     nenajdenie suboru do ktoreho ma byt trieda ulozena
     * @throws ClassNotFoundException nenajdenie suboru s triedou
     */
    public static EditState loadEditGame(String fileName) throws IOException, ClassNotFoundException{
        ObjectInputStream is = new ObjectInputStream(new FileInputStream("./Load/" + fileName + ".edit"));
        EditState editState = (EditState)is.readObject();
        is.close();
        return editState;
    }
    /**
     * metoda prerobi string na stav v ktorom je bunka
     * @param s string pomocou ktoreho sa urcuje stav
     * @return stav daneho bunka
     */
    public States getState(String s){
        switch (s){
            case "b":
                numberOfCircles++;
                return States.BLACK;
            case "w":
                numberOfCircles++;
                return States.WHITE;
            case "-": return States.FREE;
            case "|": return States.OCCUPY_VERTI;
            case "_": return States.OCCUPY_HORIZ;
            default : return null;
        }
    }
    /**
     * metoda prerobi st ring na smer v ktorom je bunka
     * @param s string pomocou ktoreho sa urcuje smer
     * @return vrati smer ktorym smeruje bunka
     */
    public Direction getDirection(String s){
        switch (s){
            case "u":
                numberOfCircles++;
                return Direction.UP;
            case "d":
                numberOfCircles++;
                return Direction.DOWN;
            case "l":
                numberOfCircles++;
                return Direction.LEFT;
            case "r":
                numberOfCircles++;
                return Direction.RIGHT;
            default : return null;
        }
    }
    /**
     * @param row riadok bunky ktoru chceme
     * @param col stlpec bunky ktoru chceme
     * @return vrati danu bunku z pola
     */
    public CellState getCell(int row, int col){
        return cells[row][col];
    }
    /**
     * @param cell druha kliknuta bunka
     * @return true ak prva kliknuta bunka ma rovnaky stlpec alebo riadok ako druha kliknuta bunka
     */
    public static boolean checkRowCol(CellState cell){
        return cell.col == clickedCell.col || cell.row == clickedCell.row;
    }
    /**
     * @param cell druha kliknuta bunka
     * @return true ak je volna cesta od prvej kliknutej bunky k druhej kliknutej bunke
     */
    public static boolean checkFreeCell(CellState cell){
        if(clickedCell.col == cell.col){
            if(clickedCell.row < cell.row){
                for (int i = clickedCell.row+1; i < cell.row; i++) {
                    if(Shirokuro.getGameStage().state.getCell(i, clickedCell.col).state != States.FREE){
                        return false;
                    }

                }
            }
            else{
                for (int i = cell.row+1; i < clickedCell.row; i++) {
                    if(Shirokuro.getGameStage().state.getCell(i, clickedCell.col).state != States.FREE){
                        return false;
                    }
                }
            }
        }
        else{
            if(clickedCell.col < cell.col){
                for (int i = clickedCell.col+1; i < cell.col; i++) {
                    if(Shirokuro.getGameStage().state.getCell(clickedCell.row, i).state != States.FREE){
                        return false;
                    }
                }
            }
            else{
                for (int i = cell.col+1; i < clickedCell.col; i++) {
                    if(Shirokuro.getGameStage().state.getCell(cell.row, i).state != States.FREE){
                        return false;
                    }
                }
            }
        }
        return true;
    }
    /**
     * metoda ktora spoji prvu kliknutu a druhu klinkutu bunku
     * @param cell druha kliknuta bunka
     */
    public static void joinCells(CellState cell){
        if(clickedCell.col == cell.col){
            if(clickedCell.row < cell.row){
                for (int i = clickedCell.row+1; i < cell.row; i++) {
                    Shirokuro.getGameStage().state.getCell(i, clickedCell.col).state = States.OCCUPY_VERTI;
                }
                clickedCell.direction = Direction.DOWN;
                cell.direction = Direction.UP;
            }
            else{
                for (int i = cell.row+1; i < clickedCell.row; i++) {
                    Shirokuro.getGameStage().state.getCell(i, clickedCell.col).state = States.OCCUPY_VERTI;
                }
                clickedCell.direction = Direction.UP;
                cell.direction = Direction.DOWN;
            }
        }
        else{
            if(clickedCell.col < cell.col){
                for (int i = clickedCell.col+1; i < cell.col; i++) {
                    Shirokuro.getGameStage().state.getCell(clickedCell.row, i).state = States.OCCUPY_HORIZ;
                }
                clickedCell.direction = Direction.RIGHT;
                cell.direction = Direction.LEFT;
            }
            else{
                for (int i = cell.col+1; i < clickedCell.col; i++) {
                    Shirokuro.getGameStage().state.getCell(cell.row, i).state = States.OCCUPY_HORIZ;
                }
                clickedCell.direction = Direction.LEFT;
                cell.direction = Direction.RIGHT;
            }
        }
    }
    /**
     * metoda prida par do pola
     * @param cell druha kliknuta bunka
     */
    public static void addPair(CellState cell){
        removePair(cell);
        Shirokuro.getGameStage().state.getPairs().put(clickedCell, cell);
        Shirokuro.getGameStage().state.getPairs().put(cell, clickedCell);
    }
    /**
     * odstani par z pola
     * @param cell druha kliknuta bunka
     */
    public static void removePair(CellState cell){
        if(Shirokuro.getGameStage().state.getPairs().containsKey(cell)){
            Shirokuro.getGameStage().state.getPairs().remove(Shirokuro.getGameStage().state.getPairs().get(cell));
            Shirokuro.getGameStage().state.getPairs().remove(cell);
        }
    }
    /**
     * metoda odstrani spojenie danje bunky horizontalne
     * @param cell bunka ktorej ma byt odstanene spojenie
     * @param colOff smer ktorym sa ma odstanovat spojenie
     */
    public static void releaseCellH(CellState cell, int colOff){
        if(colOff == 1){
            CellState cell2 = Shirokuro.getGameStage().state.getCell(cell.row, cell.col+1);
            if(cell2.state == States.WHITE || cell2.state == States.BLACK){
                if(cell2.direction == Direction.LEFT){
                    cell2.direction = Direction.NONE;
                    removePair(cell2);
                }
            }
            else{
                cell.state = States.FREE;
                releaseCellH(cell2, 1);
            }
        }
        else if(colOff == -1){
            CellState cell2 = Shirokuro.getGameStage().state.getCell(cell.row, cell.col-1);
            if(cell2.state == States.WHITE || cell2.state == States.BLACK){
                if(cell2.direction == Direction.RIGHT){
                    cell2.direction = Direction.NONE;
                    removePair(cell2);
                }
            }
            else{
                cell.state = States.FREE;
                releaseCellH(cell2, -1);
            }
        }
        else if(colOff == 0){
            releaseCellH(cell, 1);
            releaseCellH(cell, -1);
        }
        cell.state = States.FREE;
    }
    /**
     * metoda odstrani spojenie danje bunky vertikalne
     * @param cell bunka ktorej ma byt odstanene spojenie
     * @param rowOff smer ktorym sa ma odstanovat spojenie
     */
    public static void releaseCellV(CellState cell, int rowOff){
        if(rowOff == 1){
            CellState cell2 = Shirokuro.getGameStage().state.getCell(cell.row+1,cell.col);
            if(cell2.state == States.WHITE || cell2.state == States.BLACK){
                if(cell2.direction == Direction.UP){
                    cell2.direction = Direction.NONE;
                    removePair(cell2);
                }
            }
            else{
                cell.state = States.FREE;
                releaseCellV(cell2, 1);
            }
        }
        else if(rowOff == -1){
            CellState cell2 = Shirokuro.getGameStage().state.getCell(cell.row-1,cell.col);
            if(cell2.state == States.WHITE || cell2.state == States.BLACK){
                if(cell2.direction == Direction.DOWN){
                    cell2.direction = Direction.NONE;
                    removePair(cell2);
                }
            }
            else{
                cell.state = States.FREE;
                releaseCellV(cell2, -1);
            }
        }
        else if(rowOff == 0){
            releaseCellV(cell, 1);
            releaseCellV(cell, -1);
        }
        cell.state = States.FREE;
    }
    /**
     * metoda odstrani smer ktorym smeruju bunky ktore boli spojene
     * @param cell bunka ktorej ma byt odstanene spojenie
     */
    public static void releaseCellBW(CellState cell){
        int row = cell.row;
        int col = cell.col;
        if(cell.direction == Direction.UP){
            while(true){
                if(Shirokuro.getGameStage().state.getCell(row-1, col).state != States.BLACK && Shirokuro.getGameStage().state.getCell(row-1,col).state != States.WHITE){
                    Shirokuro.getGameStage().state.getCell(row-1,col).state = States.FREE;
                }
                else{
                    if(Shirokuro.getGameStage().state.getCell(row-1,col).direction == Direction.DOWN) {
                        Shirokuro.getGameStage().state.getCell(row - 1,col).direction = Direction.NONE;
                    }
                    break;
                }
                row--;
            }
        }
        else if(cell.direction == Direction.DOWN){
            while(true){
                if(Shirokuro.getGameStage().state.getCell(row+1,col).state != States.BLACK && Shirokuro.getGameStage().state.getCell(row+1,col).state != States.WHITE){
                    Shirokuro.getGameStage().state.getCell(row+1,col).state = States.FREE;
                }
                else{
                    if(Shirokuro.getGameStage().state.getCell(row+1,col).direction == Direction.UP) {
                        Shirokuro.getGameStage().state.getCell(row + 1,col).direction = Direction.NONE;
                    }
                    break;
                }
                row++;
            }
        }
        else if(cell.direction == Direction.LEFT){
            while(true){
                if(Shirokuro.getGameStage().state.getCell(row,col-1).state != States.BLACK && Shirokuro.getGameStage().state.getCell(row,col-1).state != States.WHITE){
                    Shirokuro.getGameStage().state.getCell(row,col-1).state = States.FREE;
                }
                else{
                    if(Shirokuro.getGameStage().state.getCell(row,col-1).direction == Direction.RIGHT) {
                        Shirokuro.getGameStage().state.getCell(row,col - 1).direction = Direction.NONE;
                    }
                    break;
                }
                col--;
            }
        }
        else{
            while(true){
                if(Shirokuro.getGameStage().state.getCell(row,col+1).state != States.BLACK && Shirokuro.getGameStage().state.getCell(row,col+1).state != States.WHITE){
                    Shirokuro.getGameStage().state.getCell(row,col+1).state = States.FREE;
                }
                else{
                    if(Shirokuro.getGameStage().state.getCell(row, col+1).direction == Direction.LEFT) {
                        Shirokuro.getGameStage().state.getCell(row,col + 1).direction = Direction.NONE;
                    }
                    break;
                }
                col++;
            }
        }
        removePair(cell);
        cell.direction = Direction.NONE;
    }
    /**
     * @return pocet buniek v ktorych je kruh
     */
    public int getNumberOfCircles() {
        return numberOfCircles;
    }
    /**
     * @return pole s parmi
     */
    public HashMap<CellState, CellState> getPairs() {
        return pairs;
    }
}
