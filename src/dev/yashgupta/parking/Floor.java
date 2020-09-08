package dev.yashgupta.parking;

import java.util.LinkedList;
import java.util.List;

public class Floor {
	private final int floorNumber;
	private boolean isReserved;
	private final List< ParkingSpace > parkingSpaces;

	public Floor( int premiumSpaceCount, int nonPremiumSpaceCount, int floorNumber ) {
		this.floorNumber = floorNumber;
		this.isReserved = false;
		this.parkingSpaces = new LinkedList<>();

		for ( int i = 0; i < premiumSpaceCount; i++ ) {
			this.parkingSpaces.add( new ParkingSpace( true, i + 1 ) );
		}

		for ( int i = 0; i < nonPremiumSpaceCount; i++ ) {
			this.parkingSpaces.add( new ParkingSpace( i + 1 ) );
		}
	}

	public int getFloorNumber() {
		return floorNumber;
	}

	public boolean isReserved() {
		return isReserved;
	}

	public List< ParkingSpace > getParkingSpaces() {
		return new LinkedList<>( parkingSpaces );
	}

	public void reserve() {
		this.isReserved = true;
	}

	public void unReserve() {
		this.isReserved = false;
	}

	public void printDescription() {
		List< ParkingSpace > availablePremiumSpaces = getAvailablePremiumSpaces();
		List< ParkingSpace > availableNonPremiumSpaces = getAvailableNonPremiumSpaces();

		System.out.println( "Available Spaces on Floor " + ( getFloorNumber() ) + ": " +
				( availablePremiumSpaces.size() ) + " Premium\t" +
				( availableNonPremiumSpaces.size() ) + " Non-Premium" );
	}

	public boolean isPremiumSpaceAvailable() {
		int availablePremiumSpacesCount = getAvailablePremiumSpaces().size();
		return availablePremiumSpacesCount > 0;
	}

	public List< ParkingSpace > getAvailablePremiumSpaces() {
		List< ParkingSpace > availableNonPremiumSpaces = getAvailableNonPremiumSpaces();
		List< ParkingSpace > availablePremiumSpaces = new LinkedList<>( getParkingSpaces() );
		availablePremiumSpaces.removeIf( ParkingSpace::isOccupied );
		availablePremiumSpaces.removeAll( availableNonPremiumSpaces );

		return availablePremiumSpaces;
	}

	public List< ParkingSpace > getAvailableNonPremiumSpaces() {
		List< ParkingSpace > availableNonPremiumSpaces = new LinkedList<>( getParkingSpaces() );
		availableNonPremiumSpaces.removeIf( ParkingSpace::isOccupied );
		availableNonPremiumSpaces.removeIf( ParkingSpace::isPremium );

		return availableNonPremiumSpaces;
	}

	public List< ParkingSpace > getAllPremiumSpaces() {
		List< ParkingSpace > availableNonPremiumSpaces = getAllNonPremiumSpaces();
		List< ParkingSpace > availablePremiumSpaces = new LinkedList<>( this.parkingSpaces );
		availablePremiumSpaces.removeAll( availableNonPremiumSpaces );

		return availablePremiumSpaces;
	}

	public List< ParkingSpace > getAllNonPremiumSpaces() {
		List< ParkingSpace > availableNonPremiumSpaces = new LinkedList<>( this.parkingSpaces );
		availableNonPremiumSpaces.removeIf( ParkingSpace::isPremium );

		return availableNonPremiumSpaces;
	}
}

