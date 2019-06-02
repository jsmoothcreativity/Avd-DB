package edu.ucf.oracleracedao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.ucf.racedao.RaceRidersDao;
import edu.ucf.racetransferobjects.RaceRiders;

public class OracleRaceRidersDao implements RaceRidersDao {

	public OracleRaceRidersDao() {
		// initialization
	}
	@Override
	public boolean insertRaceRiders(RaceRiders raceRider) {

		boolean result = false;
		Connection connection = OracleDaoFactory.createConnection();
		String sqlStatement = new String("INSERT INTO raceriders VALUES (?, ?, ?, ?)");
		PreparedStatement prepSqlStatement = null;
		
		try
		{
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			prepSqlStatement.setString(1, raceRider.getRiderName());
			prepSqlStatement.setString(2, raceRider.getTeamName());
			prepSqlStatement.setString(3, raceRider.getNationality());
			prepSqlStatement.setInt(4, raceRider.getNumOfWins());
			int rowCount = prepSqlStatement.executeUpdate();
			if (rowCount != 1)
				result = false;
			else
				result = true;
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			result = false;
		}
		finally
		{
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}
		
		return result;
	}

	@Override
	public RaceRiders findRaceRiders(String riderName) {
		Connection connection = OracleDaoFactory.createConnection();
		String sqlStatement = new String("SELECT * FROM raceriders WHERE ridername = ?");
		PreparedStatement prepSqlStatement = null;
		ResultSet rsFindRaceRider = null;
		RaceRiders raceRidersTransferObject = null;
		
		try
		{
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			prepSqlStatement.setString(1, riderName);
			rsFindRaceRider = prepSqlStatement.executeQuery();
			while (rsFindRaceRider.next())
			{
				raceRidersTransferObject = new RaceRiders(
						rsFindRaceRider.getString(1),
						rsFindRaceRider.getString(2),
						rsFindRaceRider.getString(3),
						rsFindRaceRider.getInt(4));
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			DbUtil.close(rsFindRaceRider);
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}
		
		return raceRidersTransferObject;
	}

	@Override
	public boolean updateRaceRiders(RaceRiders raceRider) {
		
		boolean result = false;
		Connection connection = OracleDaoFactory.createConnection();
		String sqlStatement = new String("UPDATE raceriders SET teamname = ?, nationality = ?, num_pro_wins = ? WHERE ridername = ?");
		PreparedStatement prepSqlStatement = null;
		
		try
		{
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			prepSqlStatement.setString(1, raceRider.getTeamName());
			prepSqlStatement.setString(2, raceRider.getNationality());
			prepSqlStatement.setInt(3, raceRider.getNumOfWins());
			prepSqlStatement.setString(4, raceRider.getRiderName());
			int rowCount = prepSqlStatement.executeUpdate();
			if (rowCount != 1)
				result = false;
			else
				result = true;
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			result = false;
		}
		finally
		{
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}
		
		return result;
	}

	@Override
	public boolean deleteRaceRiders(String riderName) {
		boolean result = false;
		Connection connection = OracleDaoFactory.createConnection();
		String sqlStatement = new String("DELETE FROM raceriders WHERE ridername = ?");
		PreparedStatement prepSqlStatement = null;
		
		try
		{
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			prepSqlStatement.setString(1, riderName);
			int rowCount = prepSqlStatement.executeUpdate();
			
			if (rowCount != 1)
			{
				result = false;
			}
			else
			{
				result = true;
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			result = false;
		}
		finally
		{
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}
		
		return result;
	}

	@Override
	public Collection<RaceRiders> selectAllRacerRiders() {
		List<RaceRiders> raceRidersList = new ArrayList<RaceRiders>();
		Connection connection = OracleDaoFactory.createConnection();
		String sqlStatement = new String("SELECT * FROM raceriders ORDER BY ridername");
		PreparedStatement prepSqlStatement = null;
		ResultSet rsFindRaceRiders = null;
		RaceRiders raceRidersTransferObject = null;
		
		try 
		{
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			rsFindRaceRiders = prepSqlStatement.executeQuery();
			while (rsFindRaceRiders.next())
			{
				raceRidersTransferObject = new RaceRiders(
						rsFindRaceRiders.getString(1),
						rsFindRaceRiders.getString(2),
						rsFindRaceRiders.getString(3),
						rsFindRaceRiders.getInt(4));
				raceRidersList.add(raceRidersTransferObject);
			}
			
			return raceRidersList;
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			DbUtil.close(rsFindRaceRiders);
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}
		
		return null;
	}

}
