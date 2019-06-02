package edu.ucf.oracleracedao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.ucf.racedao.RaceTeamsDao;
import edu.ucf.racetransferobjects.RaceTeams;

public class OracleRaceTeamsDao implements RaceTeamsDao {

	public OracleRaceTeamsDao() {
		// initialization
	}
	@Override
	public boolean insertRaceTeams(RaceTeams raceTeam) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RaceTeams findRaceTeams(String teamName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateRaceTeams(RaceTeams raceTeam) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteRaceTeams(String teamName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<RaceTeams> selectAllRaceTeams() {
		
		List<RaceTeams> raceTeamsList = new ArrayList<RaceTeams>();
		Connection connection = OracleDaoFactory.createConnection();
		String sqlStatement = new String("SELECT * FROM raceteams ORDER BY teamname");
		PreparedStatement prepSqlStatement = null;
		ResultSet rsFindRaceTeams = null;
		RaceTeams raceTeamsTransferObject = null;
		
		try
		{
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			rsFindRaceTeams = prepSqlStatement.executeQuery();
			while (rsFindRaceTeams.next())
			{
				raceTeamsTransferObject = new RaceTeams(
						rsFindRaceTeams.getString(1),
						rsFindRaceTeams.getString(2),
						rsFindRaceTeams.getString(3),
						rsFindRaceTeams.getInt(4),
						rsFindRaceTeams.getString(5));
				raceTeamsList.add(raceTeamsTransferObject);
			}
			return raceTeamsList;
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			DbUtil.close(rsFindRaceTeams);
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}
		
		return null;
	}

}
