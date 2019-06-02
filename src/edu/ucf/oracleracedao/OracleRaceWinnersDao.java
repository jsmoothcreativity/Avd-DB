package edu.ucf.oracleracedao;

import java.util.Collection;

import edu.ucf.racedao.RaceWinnersDao;
import edu.ucf.racetransferobjects.RaceWinners;

public class OracleRaceWinnersDao implements RaceWinnersDao {

	public OracleRaceWinnersDao() {
		// initialization
	}
	@Override
	public boolean insertRaceWinners(RaceWinners raceWinner) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RaceWinners findRaceWinners(String raceName, int year) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateRaceWinners(RaceWinners raceWinner) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteRaceWinners(String raceName, int years) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<RaceWinners> selectAllRaceWinners() {
		// TODO Auto-generated method stub
		return null;
	}

}
