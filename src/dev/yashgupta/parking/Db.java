package dev.yashgupta.parking;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class Db {
	private static Connection connection;

	public Db( String url, String user, String password ) throws SQLException {
		try {
			connection = DriverManager.getConnection( url, user, password );
			System.out.println( "Connected to DB Successfully!" );
		} catch ( SQLException e ) {
			System.out.println( "Unable to connect to DB! Please try again later!" );
			throw e;
		}

		setup();
	}

	public Db() {
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
		}
	}

	public ParkingLot createNewParkingLot( String name, int floorCount, int premiumSpacesPerFloor, int nonPremiumSpacesPerFloor ) throws SQLException {
		try ( Statement statement = connection.createStatement() ) {
			ResultSet res = statement.executeQuery(
					"INSERT INTO parkinglots(name, floorCount, premiumSpacesPerFloor, nonPremiumSpacesPerFloor) VALUES('" + ( name ) + "', " + ( floorCount ) + ", " + ( premiumSpacesPerFloor ) + ", " + ( nonPremiumSpacesPerFloor ) + ") RETURNING id" );
			if ( res.next() ) {
				int id = Integer.parseInt( res.getString( 1 ) );
				return getParkingLot( id );
			}
		}

		return null;
	}

	public Floor createNewFloor( int floorNumber, int parkingLotId ) throws SQLException {
		try ( Statement statement = connection.createStatement() ) {
			ResultSet res = statement.executeQuery(
					"INSERT INTO floors(floorNumber, parkingLotId) VALUES(" + ( floorNumber ) + ", " + parkingLotId + ") RETURNING id" );
			if ( res.next() ) {
				int id = Integer.parseInt( res.getString( 1 ) );
				return getFloor( id );
			}
		}

		return null;
	}

	public ParkingSpace createNewParkingSpace( int spaceNumber, int floorId, boolean isPremium ) throws SQLException {
		try ( Statement statement = connection.createStatement() ) {
			ResultSet res = statement.executeQuery(
					"INSERT INTO spaces(spaceNumber, floorId, isPremium, isOccupied) VALUES(" + ( spaceNumber ) + ", " + ( floorId ) + ", " + ( isPremium ) + ", " + ( false ) + ") RETURNING id" );
			if ( res.next() ) {
				int id = Integer.parseInt( res.getString( 1 ) );
				return getParkingSpace( id );
			}
		}

		return null;
	}

	public ParkingTicket createNewParkingTicket( int vehicleNumber, int spaceId ) throws SQLException {
		try ( Statement statement = connection.createStatement() ) {
			ResultSet res = statement.executeQuery(
					"INSERT INTO spaces(vehicleNumber, spaceId) VALUES(" + ( vehicleNumber ) + ", " + spaceId + ")" );
			if ( res.next() ) {
				return new ParkingTicket( res );
			}
		}

		return null;
	}

	public ParkingLot getParkingLot( int id ) throws SQLException {
		try ( Statement statement = connection.createStatement() ) {
			ResultSet res = statement.executeQuery( "SELECT * FROM parkinglots WHERE id=" + id );
			if ( res.next() ) {
				return new ParkingLot( res );
			}
		}

		return null;
	}

	public List< Floor > getFloors( int parkingLotId ) throws SQLException {
		List< Floor > floors = new LinkedList<>();
		try ( Statement statement = connection.createStatement() ) {
			ResultSet res = statement.executeQuery( "SELECT * FROM floors WHERE parkinglotid=" + parkingLotId );
			while ( res.next() ) {
				floors.add( new Floor( res ) );
			}
		}

		return floors;
	}

	public Floor getFloor( int id ) throws SQLException {
		try ( Statement statement = connection.createStatement() ) {
			ResultSet res = statement.executeQuery( "SELECT * FROM floors WHERE id=" + id );
			if ( res.next() ) {
				return new Floor( res );
			}
		}

		return null;
	}

	public List< ParkingSpace > getParkingSpaces( int floorId ) throws SQLException {
		List< ParkingSpace > spaces = new LinkedList<>();
		try ( Statement statement = connection.createStatement() ) {
			ResultSet res = statement.executeQuery( "SELECT * FROM spaces WHERE floorId=" + floorId );
			while ( res.next() ) {
				spaces.add( new ParkingSpace( res ) );
			}
		}

		return spaces;
	}

	public ParkingSpace getParkingSpace( int id ) throws SQLException {
		try ( Statement statement = connection.createStatement() ) {
			ResultSet res = statement.executeQuery( "SELECT * FROM spaces WHERE id=" + id );
			if ( res.next() ) {
				return new ParkingSpace( res );
			}
		}

		return null;
	}


	public ParkingTicket getParkingTicket( int id ) throws SQLException {
		try ( Statement statement = connection.createStatement() ) {
			ResultSet res = statement.executeQuery( "SELECT * FROM tickets WHERE id=" + id );
			if ( res.next() ) {
				return new ParkingTicket( res );
			}
		}

		return null;
	}

}

