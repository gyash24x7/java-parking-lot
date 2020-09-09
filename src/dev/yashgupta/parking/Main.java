package dev.yashgupta.parking;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Properties;
import java.util.Scanner;

public class Main {

	static final Scanner sc = new Scanner( System.in );
	static final Properties props = new Properties();

	public static void main( String[] args ) throws SQLException {
		readFromProperties();

		String dbUrl = ( String ) props.get( "dbUrl" );
		String dbUser = ( String ) props.get( "dbUser" );
		String dbPassword = ( String ) props.get( "dbPassword" );
		boolean createMode = Boolean.parseBoolean( ( String ) props.get( "createMode" ) );

		Db db = new Db( dbUrl, dbUser, dbPassword );
		ParkingLot parkingLot;

		if ( createMode ) {
			int floorCount = Integer.parseInt( ( String ) props.get( "floorCount" ) );
			int premiumSpacesPerFloor = Integer.parseInt( ( String ) props.get( "premiumSpacesPerFloor" ) );
			int nonPremiumSpacesPerFloor = Integer.parseInt( ( String ) props.get( "nonPremiumSpacesPerFloor" ) );
			String name = ( String ) props.get( "name" );

			parkingLot = db.createNewParkingLot( name, floorCount, premiumSpacesPerFloor, nonPremiumSpacesPerFloor );

			if ( parkingLot != null ) {
				parkingLot = new ParkingLot( parkingLot );
				System.out.println( "New Parking Lot created!" );
				System.out.println( "Name: " + parkingLot.getName() );
			}

		} else {
			int parkingLotId = Integer.parseInt( ( String ) props.get( "parkingLotId" ) );

			parkingLot = db.getParkingLot( parkingLotId );
			System.out.println( "\n------  Welcome to Awesome Parking Lot  ------" );

			while ( true ) {
				int choice;
				while ( true ) {
					System.out.println( "\nChoose from following : " );
					System.out.println( "1) Park your vehicle" );
					System.out.println( "2) Move your parked vehicle" );
					System.out.println( "3) Exit the Parking Lot" );
					System.out.print( "Enter your choice: " );

					try {
						choice = sc.nextInt();
						if ( choice != 1 && choice != 2 && choice != 3 ) {
							throw new InvalidInputException();
						}
						break;
					} catch ( InputMismatchException | InvalidInputException e ) {
						System.out.println( "Invalid Input!" );
						System.out.println( "Please try again!\n" );
						sc.nextLine();
					}
				}

				if ( choice == 1 ) {
					boolean isParkSuccessful = parkingLot.park();

					if ( isParkSuccessful ) {
						System.out.println( "Park Successful!" );
					}
				}

				if ( choice == 3 ) {
					parkingLot.exit();
					break;
				}
			}
		}
	}

	public static void readFromProperties() {
		File configFile = new File( "config.properties" );
		try ( FileReader reader = new FileReader( configFile ) ) {
			props.load( reader );
		} catch ( FileNotFoundException e ) {
			System.out.println( "Config File not found! Exiting. Bye!" );
		} catch ( IOException e ) {
			System.out.println( "Exception occurred! " + e.getMessage() );
		}
	}
}
