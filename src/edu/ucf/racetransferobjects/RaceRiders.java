package edu.ucf.racetransferobjects;

public class RaceRiders implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	// member variables
	private String riderName;
	private String teamName;
	private String nationality;
	private int numOfWins;
		
	//constructor
	public RaceRiders(String riderName, String teamName, String nationality, int numOfWins) {
		this.riderName = riderName;
		this.teamName = teamName;
		this.nationality = nationality;
		this.numOfWins = numOfWins;
	}
	// getter and setter methods...
	public String getRiderName() {
		return riderName;
	}
	public void setRiderName(String riderName) {
		this.riderName = riderName;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public int getNumOfWins() {
		return numOfWins;
	}
	public void setNumOfWins(int numOfWins) {
		this.numOfWins = numOfWins;
	}
}
