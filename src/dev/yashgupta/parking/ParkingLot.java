package dev.yashgupta.parking;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ParkingLot implements Parkable {
	private final List< Floor > floors;
	private final String name;
	private final int id;
	private static final Scanner sc = new Scanner( System.in );

	public ParkingLot( String name, int id, int floorCount, int premiumSpacesPerFloor, int nonPremiumSpacesPerFloor ) {
		this.floors = new LinkedList<>();
		this.name = name;
		this.id = id;
		for ( int i = 0; i < floorCount; i++ ) {
			this.floors.add( new Floor( premiumSpacesPerFloor, nonPremiumSpacesPerFloor, i + 1 ) );
		}
	}

	public int getId() {
		return id;
	}

	public void printAvailability() {
		System.out.println( "\n------         Available Spaces         ------\n" );
		for ( Floor floor : floors ) {
			System.out.println( "Floor " + ( floor.getFloorNumber() ) + ": " +
					( floor.getAvailablePremiumSpaces().size() ) + " Premium    " +
					( floor.getAvailableNonPremiumSpaces().size() ) + " Non-Premium" );
		}
	}

	public Floor selectFloor() {
		int floorNumber;

		while ( true ) {
			try {
				System.out.print( "\nEnter floor number: " );
				floorNumber = sc.nextInt();
				if ( floorNumber <= 0 || floorNumber > floors.size() ) {
					throw new InvalidInputException();
				}
				break;
			} catch ( InputMismatchException | InvalidInputException e ) {
				System.out.println( "Invalid Input!" );
				System.out.println( "Please try again!\n" );
				sc.nextLine();
			}
		}

		return floors.get( floorNumber - 1 );
	}

	@Override
	public boolean park() {
		Floor floor;
		int choice;
		boolean isParkSuccessful = false;

		while ( true ) {

			while ( true ) {
				printAvailability();
				System.out.println( "Choose from following: " );
				System.out.println( "1) Choose Floor to Park" );
				System.out.println( "2) Exit" );
				System.out.print( "Enter your choice: " );

				try {
					choice = sc.nextInt();
					if ( choice != 2 && choice != 1 ) {
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
				floor = selectFloor();
				isParkSuccessful = floor.park();

				if ( isParkSuccessful ) {
					break;
				}
			}

			if ( choice == 2 ) {
				break;
			}
		}

		return isParkSuccessful;
	}

	@Override
	public boolean unpark() {
		System.out.println( "Enter Ticket ID: " );
		String ticketId = sc.nextLine();
		// find ticket with ticketId and occupied;
		// calculate bill for the space based on current time and generated time diff
		return false;
	}

	@Override
	public void exit() {
		System.out.println( "Thanks for using our Parking lot!" );
		System.out.println( "See you soon!" );
	}

	@Override
	public String toString() {
		return "ParkingLot{name=" + ( name ) + ", id=" + ( id ) + "}";
	}
}
