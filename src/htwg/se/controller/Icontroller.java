package htwg.se.controller;

import htwg.se.model.Field;
import htwg.util.IObservable;
import htwg.util.Point;

public interface Icontroller extends IObservable {
	public Field[][] getField();

	public String getStatusMessage();

	public void storeGame();

	public void retrieveGame();

	public void move(Point start, Point goal);

	public void reset();

	public boolean checkWin();

	public String getWinner();

}
