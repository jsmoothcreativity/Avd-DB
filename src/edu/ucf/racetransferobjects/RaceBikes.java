package edu.ucf.racetransferobjects;

public class RaceBikes implements java.io.Serializable 
{
	private static final long serialVersionUID = 1L;
	// member variables
	private String bikeName;
	private String countryOfOrigin;
	private int cost;
	
	// constructor
	public RaceBikes(String bikeName, String countryOfOrigin, int cost) {
		this.bikeName = bikeName;
		this.countryOfOrigin = countryOfOrigin;
		this.cost = cost;
	}
	// getter and setter methods...
	public String getBikeName() {
		return bikeName;
	}
	public void setBikeName(String bikeName) {
		this.bikeName = bikeName;
	}
	public String getCountryOfOrigin() {
		return countryOfOrigin;
	}
	public void setCountryOfOrigin(String countryOfOrigin) {
		this.countryOfOrigin = countryOfOrigin;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
}
