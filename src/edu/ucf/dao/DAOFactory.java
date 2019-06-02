package edu.ucf.dao;

import edu.ucf.oracleracedao.OracleDaoFactory;
import edu.ucf.racedao.*;

public abstract class DAOFactory {

	// List of DAO types supported by the factory
	public static final int MYSQL = 1; // not implemented
	public static final int ORACLE = 2;
	public static final int SQL_SERVER = 3; // not implemented
	
	public abstract RaceBikesDao getRaceBikesDAO();
	public abstract RaceRidersDao getRaceRidersDAO();
	public abstract RaceTeamsDao getRaceTeamsDAO();
	public abstract RaceWinnersDao getRaceWinnersDAO();
	
	public static DAOFactory getDAOFactory(int whichFactory) {
		switch (whichFactory) {
		case MYSQL:
			// return new MySqlDaoFactory(); // not implemented
		case ORACLE:
			return new OracleDaoFactory();
		case SQL_SERVER:
			// return new MySqlServerDaoFactory(); // not implemented
		default:
			return null;
		}
	}
}
