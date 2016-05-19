package htwg.se.controller;

import com.google.inject.Inject;

import htwg.se.model.*;
import htwg.se.presistence.IDataAccessObject;
import htwg.util.*;

public class ChessController extends Observable implements Icontroller {
	private GameField gamefield;
	private boolean blackturn;
	private IDataAccessObject database;

	@Inject
	public ChessController(GameField gamefield) {
		this.gamefield = gamefield;
		blackturn = true;
	}

	public Field[][] getField() {
		return gamefield.getField();
	}

	public String getStatusMessage() {
		if (blackturn)
			return "black";
		return "white";
	}

	public void storeGameField() {
			
		try {
			database.create(gamefield);
		} finally {
			database.update(gamefield);
		}
	
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
				blackturn = (!blackturn);
			}
		}

		notifyObservers();

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

	private boolean colorCheck(Chesspiece piece) {
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
