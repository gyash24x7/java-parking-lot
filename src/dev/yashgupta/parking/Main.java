package dev.yashgupta.parking;

import java.util.List;
import java.util.Scanner;

public class Main {

	static final Scanner sc = new Scanner( System.in );

	public static void main( String[] args ) {
		System.out.print( "Enter no. of floors: " );
		int floorCount = sc.nextInt();
		System.out.print( "Enter no. of premium parking spaces per floor: " );
		int premiumSpacesPerFloor = sc.nextInt();
		System.out.print( "Enter no. of non-premium parking spaces per floor: " );
		int nonPremiumSpacesPerFloor = sc.nextInt();

		ParkingLot parkingLot = new ParkingLot( floorCount, premiumSpacesPerFloor, nonPremiumSpacesPerFloor );
		parkingLot.printDescription();

		int floorNumber = 0;
		boolean requirePremium = false;

		while ( true ) {
			System.out.print( "\nEnter floor number: " );
			floorNumber = sc.nextInt();
			if ( floorNumber > 0 && floorNumber <= parkingLot.getFloors().size() ) {
				break;
			} else {
				System.out.println( "Invalid floor number entered!" );
				System.out.println();
			}
		}

		Floor floor = parkingLot.getFloors().get( floorNumber - 1 );
		floor.printDescription();

		if ( floor.isPremiumSpaceAvailable() ) {
			System.out.println( "\nThere are premium spaces available on this floor." );
			System.out.println( "Do you want to use a premium space?)" );
			System.out.print( "Enter 1 for yes and else for no. Ans: " );
			requirePremium = sc.nextInt() == 1;
		}

		System.out.println( "You chose " + ( requirePremium ? "Premium" : "Non-Premium" ) + "Space.\n" );

		ParkingSpace space = selectSpaceFromSpaces(
				requirePremium ? floor.getAllPremiumSpaces() : floor.getAllNonPremiumSpaces() );

		space.occupy();
		System.out.println(
				"Parking Space " + space.getSpaceNumber() + " on Floor " + floor.getFloorNumber() + " is Occupied." );

	}

	public static ParkingSpace selectSpaceFromSpaces( List< ParkingSpace > spaces ) {
		int spaceNumber = 0;

		while ( true ) {
			System.out.println( "\nAll Spaces: " );
			for ( ParkingSpace space : spaces ) {
				System.out.println( "Space " + space.getSpaceNumber() + ": \t" +
						( space.isOccupied() ? "Occupied" : "Unoccupied" ) );
			}
			System.out.print( "\nEnter Space Number: " );
			spaceNumber = sc.nextInt();

			if ( spaceNumber > 0 && spaceNumber <= spaces.size() ) {
				if ( spaces.get( spaceNumber - 1 ).isOccupied() ) {
					System.out.println( "This space is already Occupied! Choose another." );
				} else {
					break;
				}
			} else {
				System.out.println( "Invalid Parking Space Number entered!" );
			}
		}

		return spaces.get( spaceNumber - 1 );
	}
}
