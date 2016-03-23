package htwg.se.controller;

/**
 * Created by benedict on 23.03.16.
 */
public interface IDataAccessObject {
    public void create();
    public Object read();
    public void update();
    public void delete();

}
