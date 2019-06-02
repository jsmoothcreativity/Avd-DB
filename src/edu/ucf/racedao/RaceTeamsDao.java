package edu.ucf.racedao;

import java.util.Collection;

import edu.ucf.racetransferobjects.RaceTeams;

public interface RaceTeamsDao {
	public boolean insertRaceTeams(RaceTeams raceTeam);
	public RaceTeams findRaceTeams(String teamName);
	public boolean updateRaceTeams(RaceTeams raceTeam);
	public boolean deleteRaceTeams(String teamName);
	
	public Collection<RaceTeams> selectAllRaceTeams();
}
