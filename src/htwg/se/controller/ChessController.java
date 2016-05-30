package htwg.se.controller;

import java.io.IOException;

import com.google.gson.JsonIOException;
import com.google.inject.Inject;

import htwg.se.model.Chesspiece;
import htwg.se.model.Field;
import htwg.se.model.GameField;
import htwg.se.persistence.IDataAccessObject;
import htwg.util.Observable;
import htwg.util.Point;


public class ChessController extends Observable implements Icontroller {
	private GameField gamefield;
	private boolean blackturn;
	
	@Inject
	private IDataAccessObject DAOdatabase;
	
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

	public void storeGame() {
		
		DAOdatabase.saveGame(gamefield.getGoverview());
	
	}
	
	@Override
	public void retrieveGame() {
		DAOdatabase.getAllGames();

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
