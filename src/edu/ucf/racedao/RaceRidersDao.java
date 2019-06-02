package edu.ucf.racedao;

import java.util.Collection;

import edu.ucf.racetransferobjects.RaceRiders;

public interface RaceRidersDao {
	public boolean insertRaceRiders(RaceRiders raceRider);
	public RaceRiders findRaceRiders(String riderName);
	public boolean updateRaceRiders(RaceRiders raceRider);
	public boolean deleteRaceRiders(String riderName);
	
	public Collection<RaceRiders> selectAllRacerRiders();
}
