package dev.yashgupta.parking;

import java.sql.*;

public class Db {
	private final String url;
	private final String user;
	private final String password;
	private static Connection connection;

	public Db( String url, String user, String password ) throws SQLException {
		this.url = url;
		this.user = user;
		this.password = password;

		try {
			connection = DriverManager.getConnection( url, user, password );
			System.out.println( "Connected to DB Successfully!" );
		} catch ( SQLException e ) {
			System.out.println( "Unable to connect to DB! Please try again later!" );
			throw e;
		}

		setup();
	}

	public static void setup() throws SQLException {
		try ( Statement statement = connection.createStatement() ) {
			statement.execute(
					"CREATE TABLE IF NOT EXISTS parkinglots (name TEXT, floorCount INT, premiumSpacesPerFloor INT, nonPremiumSpacesPerFloor INT, id INT GENERATED ALWAYS AS IDENTITY, PRIMARY KEY(id))" );
			statement.execute(
					"CREATE TABLE IF NOT EXISTS floors (floorNumber INT, id INT GENERATED ALWAYS AS IDENTITY, parkingLotId INT, PRIMARY KEY(id), CONSTRAINT fk_parkinglot FOREIGN KEY(parkingLotId) REFERENCES parkinglots(id))" );
			statement.execute(
					"CREATE TABLE IF NOT EXISTS spaces (spaceNumber INT, id INT GENERATED ALWAYS AS IDENTITY, floorId INT, isPremium BOOLEAN NOT NULL DEFAULT FALSE, isOccupied BOOLEAN NOT NULL DEFAULT FALSE, PRIMARY KEY(id), CONSTRAINT fk_floor FOREIGN KEY(floorId) REFERENCES floors(id))" );
			statement.execute(
					"CREATE TABLE IF NOT EXISTS tickets (vehicleNumber VARCHAR , id INT GENERATED ALWAYS AS IDENTITY, spaceId INT, PRIMARY KEY(id), CONSTRAINT fk_space FOREIGN KEY(spaceId) REFERENCES spaces(id))" );
		} catch ( SQLException e ) {
			System.out.println( "Unable to Create Tables!" );
			throw e;
		}
	}

	public void createNewParkingLot() throws SQLException {
		try ( Statement statement = connection.createStatement() ) {
			statement.execute(
					"INSERT INTO parkinglots(name, floorCount, premiumSpacesPerFloor, nonPremiumSpacesPerFloor) VALUES('AWESOME PARKING', 3, 5 ,10)" );
		} catch ( SQLException e ) {
			System.out.println( "Unable to Create New Parking Lot!" );
			throw e;
		}
	}

	public ParkingLot findParkingLotWithId( int id ) throws SQLException {
		try ( Statement statement = connection.createStatement() ) {
			ResultSet res = statement.executeQuery( "SELECT * FROM parkinglots WHERE id=" + id );
			if ( res.next() ) {
				String name = res.getString( 1 );
				int floorCount = Integer.parseInt( res.getString( 2 ) );
				int premiumSpacesPerFloor = Integer.parseInt( res.getString( 3 ) );
				int nonPremiumSpacesPerFloor = Integer.parseInt( res.getString( 4 ) );
				return new ParkingLot( name, id, floorCount, premiumSpacesPerFloor, nonPremiumSpacesPerFloor );
			}
		} catch ( SQLException e ) {
			System.out.println( "Parking Lot Not Found!" );
			throw e;
		}

		return null;
	}

	public ParkingSpace findParkingSpaceWithId( int id ) throws SQLException {
		try ( Statement statement = connection.createStatement() ) {
			ResultSet res = statement.executeQuery( "SELECT * FROM spaces WHERE id=" + id );
			if ( res.next() ) {
				int spaceNumber = Integer.parseInt( res.getString( 1 ) );
				int floorId = Integer.parseInt( res.getString( 3 ) );
				boolean isPremium = Boolean.parseBoolean( res.getString( 4 ) );
				boolean isOccupied = Boolean.parseBoolean( res.getString( 5 ) );
				return new ParkingSpace( spaceNumber, id, isPremium, isOccupied, floorId );
			}
		} catch ( SQLException e ) {
			System.out.println( "Parking Lot Not Found!" );
			throw e;
		}

		return null;
	}

	public Floor findFloorWithId( int id ) throws SQLException {
		try ( Statement statement = connection.createStatement() ) {
			ResultSet res = statement.executeQuery( "SELECT * FROM floors WHERE id=" + id );
			if ( res.next() ) {
				String name = res.getString( 1 );
				int floorCount = Integer.parseInt( res.getString( 2 ) );
				int premiumSpacesPerFloor = Integer.parseInt( res.getString( 3 ) );
				int nonPremiumSpacesPerFloor = Integer.parseInt( res.getString( 4 ) );
				return new Floor( name, id, floorCount, premiumSpacesPerFloor, nonPremiumSpacesPerFloor );
			}
		} catch ( SQLException e ) {
			System.out.println( "Parking Lot Not Found!" );
			throw e;
		}

		return null;
	}

	public ParkingTicket findParkingTicketWithId( int id ) throws SQLException {
		try ( Statement statement = connection.createStatement() ) {
			ResultSet res = statement.executeQuery( "SELECT * FROM parkinglots WHERE id=" + id );
			if ( res.next() ) {
				String name = res.getString( 1 );
				int floorCount = Integer.parseInt( res.getString( 2 ) );
				int premiumSpacesPerFloor = Integer.parseInt( res.getString( 3 ) );
				int nonPremiumSpacesPerFloor = Integer.parseInt( res.getString( 4 ) );
				return new ParkingLot( name, id, floorCount, premiumSpacesPerFloor, nonPremiumSpacesPerFloor );
			}
		} catch ( SQLException e ) {
			System.out.println( "Parking Lot Not Found!" );
			throw e;
		}

		return null;
	}

}

