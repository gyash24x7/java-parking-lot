package dev.yashgupta.parking;

public class ParkingSpace {
	private boolean isOccupied;
	private boolean isPremium;
	private final int spaceNumber;

	public ParkingSpace( int spaceNumber ) {
		this.spaceNumber = spaceNumber;
		this.isOccupied = false;
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

	public boolean isOccupied() {
		return isOccupied;
	}

	public void occupy() {
		this.isOccupied = true;
	}

	public void vacate() {
		this.isOccupied = false;
	}
}
