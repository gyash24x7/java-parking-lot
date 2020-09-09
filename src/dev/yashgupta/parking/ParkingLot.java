package dev.yashgupta.parking;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ParkingLot implements Parkable {
	private final List< Floor > floors;
	private final String name;
	private int floorCount;
	private int premiumSpacesPerFloor;
	private int nonPremiumSpacesPerFloor;
	private final int id;
	private static final Scanner sc = new Scanner( System.in );

	public ParkingLot( ParkingLot parkingLot ) throws SQLException {
		this.id = parkingLot.getId();
		this.name = parkingLot.getName();
		this.premiumSpacesPerFloor = parkingLot.getPremiumSpacesPerFloor();
		this.nonPremiumSpacesPerFloor = parkingLot.getNonPremiumSpacesPerFloor();
		this.floorCount = parkingLot.getFloorCount();

		Db db = new Db();
		this.floors = new LinkedList<>();

		for ( int i = 0; i < floorCount; i++ ) {
			Floor floor = db.createNewFloor( i + 1, this.id );
			if ( floor != null ) {
				this.floors.add( new Floor( floor, premiumSpacesPerFloor, nonPremiumSpacesPerFloor ) );
			}
		}
	}

	public ParkingLot( ResultSet res ) throws SQLException {
		this.id = Integer.parseInt( res.getString( 5 ) );
		this.name = res.getString( 1 );
		this.floorCount = Integer.parseInt( res.getString( 2 ) );
		this.premiumSpacesPerFloor = Integer.parseInt( res.getString( 3 ) );
		this.nonPremiumSpacesPerFloor = Integer.parseInt( res.getString( 4 ) );

		Db db = new Db();
		List< Floor > floors = db.getFloors( this.id );
		if ( floors != null ) {
			this.floors = floors;
		} else {
			this.floors = new LinkedList<>();
		}
	}

	public int getId() {
		return id;
	}

	public List< Floor > getFloors() {
		return floors;
	}

	public String getName() {
		return name;
	}

	public int getFloorCount() {
		return floorCount;
	}

	public int getPremiumSpacesPerFloor() {
		return premiumSpacesPerFloor;
	}

	public int getNonPremiumSpacesPerFloor() {
		return nonPremiumSpacesPerFloor;
	}

	public void printAvailability() {
		System.out.println( "\n------         Available Spaces         ------\n" );
		for ( Floor floor : floors ) {
			System.out.println( "Floor " + ( floor.getFloorNumber() ) + ": " +
					( floor.getAvailablePremiumSpaces().size() ) + " Premium    " +
					( floor.getAvailableNonPremiumSpaces().size() ) + " Non-Premium\n" );
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
