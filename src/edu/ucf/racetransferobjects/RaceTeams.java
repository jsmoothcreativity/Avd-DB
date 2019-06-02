package edu.ucf.racetransferobjects;

public class RaceTeams implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	// member variables
	private String teamName;
	private String bikeName;
	private String registeredNation;
	private int numOfRiders;
	private String manager;
	// constructor
	public RaceTeams(String teamName, String bikeName, String registeredNation, int numOfRiders, String manager) {
		this.teamName = teamName;
		this.bikeName = bikeName;
		this.registeredNation = registeredNation;
		this.numOfRiders = numOfRiders;
		this.manager = manager;
	}
	//getter and setter methods...
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getBikeName() {
		return bikeName;
	}
	public void setBikeName(String bikeName) {
		this.bikeName = bikeName;
	}
	public String getRegisteredNation() {
		return registeredNation;
	}
	public void setRegisteredNation(String registeredNation) {
		this.registeredNation = registeredNation;
	}
	public int getNumOfRiders() {
		return numOfRiders;
	}
	public void setNumOfRiders(int numOfRiders) {
		this.numOfRiders = numOfRiders;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
}
