package htwg.se.chess;

import htwg.se.controller.Icontroller;
import htwg.se.persistence.IDataAccessObject;

import com.google.inject.AbstractModule;

public class ChessModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(Icontroller.class).to(htwg.se.controller.ChessController.class);
		bind(IDataAccessObject.class).to(htwg.se.persistence.CouchDB.DAOCouchDB.class);
		//bind(IDataAccessObject.class).to(htwg.se.persistence.hibernate.DAOHibernate.class);
	}

}
