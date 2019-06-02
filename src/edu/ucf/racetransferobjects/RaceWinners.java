package edu.ucf.racetransferobjects;

public class RaceWinners implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	// member variables
	private String raceName;
	private int raceYear;
	private String riderName;
	private int distance;
	private double winningTime;
	// constructor
	public RaceWinners(String raceName, int raceYear, String riderName, int distance) {
		this.raceName = raceName;
		this.raceYear = raceYear;
		this.riderName = riderName;
		this.distance = distance;
		this.winningTime = 0.0;
	}
	// getter and setter methods...
	public String getRaceName() {
		return raceName;
	}
	public void setRaceName(String raceName) {
		this.raceName = raceName;
	}
	public int getRaceYear() {
		return raceYear;
	}
	public void setRaceYear(int raceYear) {
		this.raceYear = raceYear;
	}
	public String getRiderName() {
		return riderName;
	}
	public void setRiderName(String riderName) {
		this.riderName = riderName;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
}
