package edu.ucf.oracleracedao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.ucf.racedao.RaceBikesDao;
import edu.ucf.racetransferobjects.RaceBikes;

public class OracleRaceBikesDao implements RaceBikesDao {

	public OracleRaceBikesDao() {
		// initialization
	}
	@Override
	public boolean insertRaceBike(RaceBikes raceBike) {
		
		boolean result = false;
		Connection connection = OracleDaoFactory.createConnection();
		String sqlStatement = new String("INSERT INTO racebikes VALUES (?, ?, ?)");
		PreparedStatement prepSqlStatement = null;
		
		try
		{
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			prepSqlStatement.setString(1, raceBike.getBikeName());
			prepSqlStatement.setString(2, raceBike.getCountryOfOrigin());
			prepSqlStatement.setInt(3, raceBike.getCost());
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
	public RaceBikes findRaceBikes(String bikeName) {
		Connection connection = OracleDaoFactory.createConnection();
		String sqlStatement =  new String("SELECT * FROM racebikes WHERE bikename = ?");
		PreparedStatement prepSqlStatement = null;
		ResultSet rsFindRaceBike = null;
		RaceBikes raceBikeTransferObject = null;
		
		try
		{
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			prepSqlStatement.setString(1, bikeName);
			rsFindRaceBike = prepSqlStatement.executeQuery();
			while (rsFindRaceBike.next())
			{
				raceBikeTransferObject = new RaceBikes(
						rsFindRaceBike.getString(1),
						rsFindRaceBike.getString(2),
						rsFindRaceBike.getInt(3));
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			DbUtil.close(rsFindRaceBike);
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}
		
		return raceBikeTransferObject;
	}

	@Override
	public boolean updateRaceBikes(RaceBikes raceBike) {
		
		boolean result = false;
		Connection connection = OracleDaoFactory.createConnection();
		String sqlStatement = new String("UPDATE racebikes SET country_of_origin = ?, cost = ? WHERE bikename = ?");
		PreparedStatement prepSqlStatement = null;
		
		try 
		{
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			prepSqlStatement.setString(1, raceBike.getCountryOfOrigin());
			prepSqlStatement.setInt(2, raceBike.getCost());
			prepSqlStatement.setString(3, raceBike.getBikeName());
			int rowCount = prepSqlStatement.executeUpdate();
			if (rowCount != 1) {
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
		finally {
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}
		
		return result;
	}

	@Override
	public boolean deleteRaceBikes(String bikeName) {
		
		boolean result = false;
		Connection connection = OracleDaoFactory.createConnection();
		String sqlStatement = new String("DELETE FROM racebikes WHERE bikename = ?");
		PreparedStatement prepSqlStatement = null;
		
		try
		{
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			prepSqlStatement.setString(1, bikeName);
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
	public Collection<RaceBikes> selectAllRaceBikes() {
		List<RaceBikes> raceBikesList = new ArrayList<RaceBikes>();
		Connection connection = OracleDaoFactory.createConnection();
		String sqlStatement = new String("SELECT * FROM racebikes ORDER BY bikename");
		PreparedStatement prepSqlStatement = null;
		ResultSet rsFindRaceBikes = null;
		RaceBikes raceBikesTransferObject = null;
		
		try
		{
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			rsFindRaceBikes = prepSqlStatement.executeQuery();
			while (rsFindRaceBikes.next())
			{
				raceBikesTransferObject = new RaceBikes(
						rsFindRaceBikes.getString(1),
						rsFindRaceBikes.getString(2),
						rsFindRaceBikes.getInt(3));
				raceBikesList.add(raceBikesTransferObject);
			}
			return raceBikesList;
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			DbUtil.close(rsFindRaceBikes);
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}
		return null;
	}
}
