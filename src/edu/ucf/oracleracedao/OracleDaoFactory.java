package edu.ucf.oracleracedao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import edu.ucf.dao.DAOFactory;
import edu.ucf.racedao.*;
import edu.ucf.oracleracedao.*;

public class OracleDaoFactory extends DAOFactory {

	public static final String DATABASE_URL = "jdbc:oracle:thin:@localhost:1521:xe";
	public static final String USER = "SYSTEM";
	public static final String PASSWORD = "mr_smooth504";
	private static Connection connection;
	
	public static Connection createConnection() {
		try
		{
			connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		return connection;
	}
	
	@Override
	public RaceBikesDao getRaceBikesDAO() {
		return new OracleRaceBikesDao();
	}

	@Override
	public RaceRidersDao getRaceRidersDAO() {
		return new OracleRaceRidersDao();
	}

	@Override
	public RaceTeamsDao getRaceTeamsDAO() {
		return new OracleRaceTeamsDao();
	}

	@Override
	public RaceWinnersDao getRaceWinnersDAO() {
		return new OracleRaceWinnersDao();
	}

	
}
