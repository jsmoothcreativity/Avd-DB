package edu.ucf.racedao;

import java.util.Collection;

import edu.ucf.racetransferobjects.RaceBikes;

public interface RaceBikesDao {
	public boolean insertRaceBike(RaceBikes raceBike);  //Create
	public RaceBikes findRaceBikes(String bikeName);    //Retrieve
	public boolean updateRaceBikes(RaceBikes raceBike); //Update
	public boolean deleteRaceBikes(String bikeName);    //Delete
	
	public Collection<RaceBikes> selectAllRaceBikes();
}
