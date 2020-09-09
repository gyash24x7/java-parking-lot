package dev.yashgupta.parking;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Floor implements Parkable {
	private final int floorNumber;
	private final List< ParkingSpace > parkingSpaces;
	private static final Scanner sc = new Scanner( System.in );
	private final int id;
	private final int parkingLotId;

	public Floor( int floorNumber, int id, int parkingLotId ) {
		this.floorNumber = floorNumber;
		this.id = id;
		this.parkingLotId = parkingLotId;
		this.parkingSpaces = new LinkedList<>();
	}

	public int getFloorNumber() {
		return floorNumber;
	}

	public int getId() {
		return id;
	}

	public void printAvailability() {
		System.out.println( "\n------             Floor " + ( getFloorNumber() ) + "              ------\n" );
		System.out.println( "Available Spaces on Floor " + ( getFloorNumber() ) + ": " +
				( getAvailablePremiumSpaces().size() ) + " Premium\t" +
				( getAvailableNonPremiumSpaces().size() ) + " Non-Premium" );
	}

	public boolean isPremiumSpaceAvailable() {
		int availablePremiumSpacesCount = getAvailablePremiumSpaces().size();
		return availablePremiumSpacesCount > 0;
	}

	public List< ParkingSpace > getAvailablePremiumSpaces() {
		return getAllPremiumSpaces().stream().filter( ParkingSpace::isVacant ).collect( Collectors.toList() );
	}

	public List< ParkingSpace > getAvailableNonPremiumSpaces() {
		return getAllNonPremiumSpaces().stream().filter( ParkingSpace::isVacant ).collect( Collectors.toList() );
	}

	public List< ParkingSpace > getAllPremiumSpaces() {
		return this.parkingSpaces.stream().filter( ParkingSpace::isPremium ).collect( Collectors.toList() );
	}

	public List< ParkingSpace > getAllNonPremiumSpaces() {
		return this.parkingSpaces.stream().filter( ParkingSpace::isNotPremium ).collect( Collectors.toList() );
	}

	public boolean isEntrySuccessful() {

		int choice;
		while ( true ) {
			System.out.println( "Choose from following: " );
			System.out.println( "1) Park your vehicle on this floor ( Floor " + getFloorNumber() + " )" );
			System.out.println( "2) Exit Floor " + getFloorNumber() );
			System.out.print( "Enter your choice: " );

			try {
				choice = sc.nextInt();
				if ( choice != 1 && choice != 2 ) {
					throw new InvalidInputException();
				}
				break;
			} catch ( InvalidInputException | InputMismatchException e ) {
				System.out.println( "Invalid Input!" );
				System.out.println( "Please try again!\n" );
				sc.nextLine();
			}
		}

		if ( choice == 1 ) {
			ParkingSpace space;
			int premiumChoice = askForPremiumParking();

			if ( premiumChoice != 3 ) {
				space = selectSpace( premiumChoice == 1 );
				space.occupy( getFloorNumber() );
				return true;
			}
		}

		return false;
	}

	public int askForPremiumParking() {

		if ( isPremiumSpaceAvailable() ) {

			while ( true ) {
				System.out.println( "\nThere are premium spaces available on this floor." );
				System.out.println( "Do you want to use a premium space?" );
				System.out.print( "Enter 1 for yes and 2 for no. Ans: " );

				try {
					int input = sc.nextInt();
					if ( input != 1 && input != 2 ) {
						throw new InvalidInputException();
					}
					if ( input == 1 ) {
						return 1;
					} else {
						return 2;
					}
				} catch ( InputMismatchException | InvalidInputException e ) {
					System.out.println( "Invalid Input!" );
					System.out.println( "Please try again!\n" );
					sc.nextLine();
				}
			}

		} else {

			while ( true ) {
				System.out.println( "\nThere are no premium spaces available on this floor." );
				System.out.println( "Choose from following: " );
				System.out.println( "1) Go with a non-premium space on this floor" );
				System.out.println( "2) Look for a premium space on other floors" );
				System.out.print( "Enter your choice: " );

				try {
					int input = sc.nextInt();
					if ( input != 1 && input != 2 ) {
						throw new InvalidInputException();
					}
					if ( input == 1 ) {
						return 2;
					}
					break;
				} catch ( InputMismatchException | InvalidInputException e ) {
					System.out.println( "Invalid Input!" );
					System.out.println( "Please try again!\n" );
					sc.nextLine();
				}
			}
		}

		return 3;
	}

	public ParkingSpace selectSpace( boolean requirePremium ) {
		int spaceNumber;
		List< ParkingSpace > spaces = requirePremium ? getAvailablePremiumSpaces() : getAvailableNonPremiumSpaces();
		List< Integer > spaceNumbers = spaces.stream().map( ParkingSpace::getSpaceNumber ).collect(
				Collectors.toList() );

		while ( true ) {
			System.out.println( "\nAvailable Spaces: " );
			for ( ParkingSpace space : spaces ) {
				System.out.println( "Space " + space.getSpaceNumber() + ": \t" +
						( space.isOccupied() ? "Occupied" : "Unoccupied" ) );
			}

			System.out.print( "\nEnter Space Number: " );

			try {
				spaceNumber = sc.nextInt();

				if ( !spaceNumbers.contains( spaceNumber ) ) {
					throw new InvalidInputException();
				}

				break;

			} catch ( InputMismatchException e ) {

				System.out.println( "Invalid Input!" );
				System.out.println( "Please try again!\n" );
				sc.nextLine();

			} catch ( InvalidInputException e ) {

				System.out.println( "This Parking Space is not available." );
				System.out.println( "Please try again!\n" );
				sc.nextLine();

			}

		}

		int finalSpaceNumber = spaceNumber;
		return spaces.stream().filter(
				parkingSpace -> parkingSpace.getSpaceNumber() == finalSpaceNumber ).findFirst().orElse( null );
	}

	@Override
	public boolean park() {
		printAvailability();

		if ( parkingSpaces.stream().noneMatch( ParkingSpace::isVacant ) ) {
			System.out.println( "There are no Parking Spaces available on this floor!" );
			System.out.println( "Choose another floor!" );
			return false;
		}

		return isEntrySuccessful();
	}

	@Override
	public boolean unpark() {
		return false;
	}

	@Override
	public void exit() {
		System.out.println( "Exiting Floor " + getFloorNumber() + "!" );
	}
}

