package htwg.se.controller;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.inject.Inject;
import htwg.se.model.Chesspiece;
import htwg.se.model.Field;
import htwg.se.model.GameField;
import htwg.se.model.King;
import htwg.se.persistence.IDataAccessObject;
import htwg.util.Observable;
import htwg.util.Point;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;

@SuppressWarnings("ALL")
public class ChessController extends Observable implements Icontroller {
	private GameField gamefield;
	private boolean blackturn;
	private IDataAccessObject database;
	public King ki = new King(4, 4, 'b'); //gson test
	private String moveJson = "{";
    private String gameName = "Marco_Bene";
    private int id = 0;

    private JSONObject mainObj = new JSONObject();
    private JSONArray games = new JSONArray();
    private JSONObject gameProberties = new JSONObject();
    private JSONArray gameMovelist = new JSONArray();


	@Inject
	public ChessController(GameField gamefield) {
		this.gamefield = gamefield;
		blackturn = true;
        createJson();
	}
	

	public Field[][] getField() {
		return gamefield.getField();
	}

	public String getStatusMessage() {
		if (blackturn)
			return "black";
		return "white";
	}

	public void storeGameField() throws JsonIOException, IOException {
		System.out.println("funktioniert");
		Gson gson = new Gson();
		King kk = ki;
		kk.setmovedTrue();
		
		gson.toJson(kk, new FileWriter("C:\\file.json"));
		String jsonInString = gson.toJson(kk);
		System.out.println(jsonInString);
	}

	

	public void retrieveGameField() {
//		ObjectContainer dataBase = Db4o.openFile("Gamefield.db");
//		
//		Query query = dataBase.query();
//		ObjectSet<GameField> gamefields = query.execute();
//			gamefield.setField(gamefields.get(0));
	}

	public void move(Point start, Point goal) {
		if (checkTurn(start)) {
			if (gamefield.moveCheck(start, goal)) {
				gamefield.moveAfterCheck(start, goal);
                addJsonMove(start, goal);
				blackturn = (!blackturn);
			}
		}

		notifyObservers();

	}

    public void createJson() {
        mainObj.put("Gamelist", games);
        games.add(gameProberties);
        gameProberties.put("Gamename", gameName);
        gameProberties.put("Movelist",gameMovelist);
    }

    public void addJsonMove(Point start, Point goal) {
        JSONObject gameMove = new JSONObject();
        gameMove.put("From", start.toString());
        gameMove.put("To", goal.toString());
        gameMovelist.add(gameMove);


    }

    public void searchGameJson(String gameName) {

        for (int i = 0; i < games.size(); i++) {
            Object obj = games.get(i);
            JSONObject game = (JSONObject) obj;

            if(game.get("Gamename") == gameName) {
				JSONArray moveList = new JSONArray();
				obj = game.get("Movelist");
				moveList = (JSONArray) obj;
				for (int n = 0; n < moveList.size(); n++) {
					obj = moveList.get(n);
					game = (JSONObject) obj;
					System.out.println("From: " + game.get("From") + " To:" + game.get("To"));
				}

                System.out.println("Game gefunden");
                //getMoveListJson(games);
                break;
            }
        }

        System.out.println(mainObj);
    }

    public void getMoveListJson(JSONObject aktuellesGame) {


    }

    private boolean checkTurn(Point start) {
		Field field[][] = gamefield.getField();
		Chesspiece piece = field[start.getX()][start.getY()].getChessPiece();
		if (piece == null) {
			return false;
		}
		return colorCheck(piece);
	}

	public void reset() {
		gamefield = new GameField();
		blackturn = true;
		notifyObservers();
	}

	public boolean colorCheck(Chesspiece piece) {
		if (blackturn) {
			return piece.getcolor() == 'b';
		}
		return piece.getcolor() == 'w';
	}

	public boolean checkWin() {
		return gamefield.blackWon() || gamefield.whiteWon();
	}

	public String getWinner() {
		if (checkWin()) {
			if (gamefield.blackWon()) {
				return "black";
			} else if (gamefield.whiteWon()) {
				return "white";
			}
		}
		return "NONE";
	}


}

