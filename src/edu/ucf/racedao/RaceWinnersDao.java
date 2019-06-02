package edu.ucf.racedao;

import java.util.Collection;

import edu.ucf.racetransferobjects.RaceWinners;

public interface RaceWinnersDao {
	public boolean insertRaceWinners(RaceWinners raceWinner);
	public RaceWinners findRaceWinners(String raceName, int year);
	public boolean updateRaceWinners(RaceWinners raceWinner);
	public boolean deleteRaceWinners(String raceName, int years);
	
	public Collection<RaceWinners> selectAllRaceWinners();
}
