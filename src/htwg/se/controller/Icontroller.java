package htwg.se.controller;

import java.io.IOException;

import htwg.se.model.Field;
import htwg.util.IObservable;
import htwg.util.Point;

public interface Icontroller extends IObservable{
	public Field[][] getField();
	public String getStatusMessage();
	public void storeGameField() throws IOException, IOException;
	public void retrieveGameField();
	public void move(Point start, Point goal);
	public void reset();
	public boolean checkWin();
	public String getWinner();
	public void searchGameJson(String gameName);
	
}
