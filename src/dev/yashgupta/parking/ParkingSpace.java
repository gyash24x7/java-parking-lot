package dev.yashgupta.parking;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ParkingSpace {
	private boolean isOccupied;
	private final boolean isPremium;
	private final int spaceNumber;
	private final int id;
	private final int floorId;

	private static final Scanner sc = new Scanner( System.in );

	public ParkingSpace( ResultSet res ) throws SQLException {
		this.id = Integer.parseInt( res.getString( 2 ) );
		this.floorId = Integer.parseInt( res.getString( 3 ) );
		this.spaceNumber = Integer.parseInt( res.getString( 1 ) );
		this.isPremium = Boolean.parseBoolean( res.getString( 4 ) );
		this.isOccupied = Boolean.parseBoolean( res.getString( 5 ) );
	}

	public int getId() {
		return id;
	}

	public boolean isPremium() {
		return isPremium;
	}

	public boolean isNotPremium() {
		return !isPremium;
	}

	public int getSpaceNumber() {
		return spaceNumber;
	}

	public boolean isOccupied() {
		return isOccupied;
	}

	public boolean isVacant() {
		return !isOccupied;
	}

	public void occupy() {
		System.out.println( "Enter your vehicle number: " );
		String vehicleNumber = sc.nextLine();
//		ParkingTicket ticket = new ParkingTicket( vehicleNumber, floorNumber, spaceNumber );
//		printTicketDetails( ticket );
		this.isOccupied = true;
	}

	public void vacate() {
		this.isOccupied = false;
	}

	@Override
	public String toString() {
		return "ParkingSpace (number: " + ( getSpaceNumber() ) + ")";
	}

	private static void printTicketDetails( ParkingTicket ticket ) {
		System.out.println( "Ticket ID: " + ticket.getId() );
		System.out.println( "Remember TicketID for future reference!\n" );
	}
}
