package dev.yashgupta.parking;

import java.util.Scanner;

public class ParkingSpace {
	private boolean isOccupied;
	private boolean isPremium;
	private final int spaceNumber;
	private final int id;
	private final int floorId;

	private static final Scanner sc = new Scanner( System.in );

	public ParkingSpace( int spaceNumber, int spaceId, boolean isPremium, boolean isOccupied, int floorId ) {
		this.spaceNumber = spaceNumber;
		this.isOccupied = isOccupied;
		this.isPremium = isPremium;
		this.id = spaceId;
		this.floorId = floorId;
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

	public void occupy( int floorNumber ) {
		System.out.println( "Enter your vehicle number: " );
		String vehicleNumber = sc.nextLine();
		ParkingTicket ticket = new ParkingTicket( vehicleNumber, floorNumber, spaceNumber, 1 );
		printTicketDetails( ticket );
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
