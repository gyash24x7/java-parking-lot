package dev.yashgupta.parking;

import java.util.LinkedList;
import java.util.List;

public class ParkingLot {
	private final List< Floor > floors;

	public ParkingLot( int floorCount, int premiumSpacesPerFloor, int nonPremiumSpacesPerFloor ) {
		this.floors = new LinkedList<>();
		for ( int i = 0; i < floorCount; i++ ) {
			this.floors.add( new Floor( premiumSpacesPerFloor, nonPremiumSpacesPerFloor, i + 1 ) );
		}
	}

	public List< Floor > getFloors() {
		return floors;
	}

	public void printDescription() {
		System.out.println( "------  Welcome to Awesome Parking Lot  ------" );
		System.out.println( "------         Available Spaces         ------" );
		System.out.println();
		for ( Floor floor : floors ) {
			System.out.println( "Floor " + ( floor.getFloorNumber() ) + ": " +
					( floor.getAvailablePremiumSpaces().size() ) + " Premium    " +
					( floor.getAvailableNonPremiumSpaces().size() ) + " Non-Premium" );
		}
	}
}
