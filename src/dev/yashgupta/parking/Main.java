package dev.yashgupta.parking;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

	static final Scanner sc = new Scanner( System.in );

	public static void main( String[] args ) {
		int floorCount, premiumSpacesPerFloor, nonPremiumSpacesPerFloor;

		while ( true ) {
			try {
				System.out.print( "Enter no. of floors: " );
				floorCount = sc.nextInt();
				break;
			} catch ( InputMismatchException e ) {
				System.out.println( "Invalid Input!" );
				System.out.println( "Please try again!\n" );
				sc.nextLine();
			}
		}

		while ( true ) {
			try {
				System.out.print( "Enter no. of premium parking spaces per floor: " );
				premiumSpacesPerFloor = sc.nextInt();
				break;
			} catch ( InputMismatchException e ) {
				System.out.println( "Invalid Input!" );
				System.out.println( "Please try again!\n" );
				sc.nextLine();
			}
		}

		while ( true ) {
			try {
				System.out.print( "Enter no. of non-premium parking spaces per floor: " );
				nonPremiumSpacesPerFloor = sc.nextInt();
				break;
			} catch ( InputMismatchException e ) {
				System.out.println( "Invalid Input!" );
				System.out.println( "Please try again!\n" );
				sc.nextLine();
			}
		}


		System.out.println( "\n" );
		ParkingLot parkingLot = new ParkingLot( floorCount, premiumSpacesPerFloor, nonPremiumSpacesPerFloor );
		parkingLot.printDescription();


		Floor floor = selectFloorFromFloors( parkingLot.getFloors() );
		floor.printDescription();

		boolean requirePremium = false;

		if ( floor.isPremiumSpaceAvailable() ) {
			System.out.println( "\nThere are premium spaces available on this floor." );
			System.out.println( "Do you want to use a premium space?" );

			while ( true ) {
				try {
					System.out.print( "Enter 1 for yes and else for no. Ans: " );
					requirePremium = sc.nextInt() == 1;
					break;
				} catch ( InputMismatchException e ) {
					System.out.println( "Invalid Input!" );
					System.out.println( "Please try again!\n" );
					sc.nextLine();
				}
			}

		}

		System.out.println( "You chose " + ( requirePremium ? "Premium" : "Non-Premium" ) + " Space." );

		ParkingSpace space = selectSpaceFromSpaces(
				requirePremium ? floor.getAllPremiumSpaces() : floor.getAllNonPremiumSpaces() );

		space.occupy();
		System.out.println( "You have occupied Parking Space " + space.getSpaceNumber() + " on Floor " +
				floor.getFloorNumber() + "." );

	}

	public static Floor selectFloorFromFloors( List< Floor > floors ) {
		int floorNumber;

		while ( true ) {
			try {
				System.out.print( "\nEnter floor number: " );
				floorNumber = sc.nextInt();
				if ( floorNumber > 0 && floorNumber <= floors.size() ) {
					break;
				} else {
					System.out.println( "Invalid floor number entered!" );
					System.out.println();
				}
			} catch ( InputMismatchException e ) {
				System.out.println( "Invalid Input!" );
				System.out.println( "Please try again!\n" );
				sc.nextLine();
			}
		}

		return floors.get( floorNumber - 1 );
	}

	public static ParkingSpace selectSpaceFromSpaces( List< ParkingSpace > spaces ) {
		int spaceNumber;

		System.out.println( "\nAll Spaces: " );
		for ( ParkingSpace space : spaces ) {
			System.out.println( "Space " + space.getSpaceNumber() + ": \t" +
					( space.isOccupied() ? "Occupied" : "Unoccupied" ) );
		}

		while ( true ) {
			try {
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
			} catch ( InputMismatchException e ) {
				System.out.println( "Invalid Input!" );
				System.out.println( "Please try again!\n" );
				sc.nextLine();
			}

		}

		return spaces.get( spaceNumber - 1 );
	}
}
