package dev.yashgupta.parking;

public class ParkingSpace {
	//	private final String spaceId;
	private boolean isOccupied;
	private boolean isReserved;
	private boolean isPremium;
	private final int spaceNumber;

	public ParkingSpace( int spaceNumber ) {
//		this.spaceId = spaceId;
		this.spaceNumber = spaceNumber;
		this.isOccupied = false;
		this.isReserved = false;
		this.isPremium = false;
	}

	public ParkingSpace( boolean isPremium, int spaceNumber ) {
		this( spaceNumber );
		this.isPremium = isPremium;
	}

	public boolean isPremium() {
		return isPremium;
	}

	public int getSpaceNumber() {
		return spaceNumber;
	}

	//	public String getSpaceId() {
//		return spaceId;
//	}

	public boolean isOccupied() {
		return isOccupied;
	}

	public boolean isReserved() {
		return isReserved;
	}

	public void occupy() {
		this.isOccupied = true;
	}

	public void vacate() {
		this.isOccupied = false;
	}
}
