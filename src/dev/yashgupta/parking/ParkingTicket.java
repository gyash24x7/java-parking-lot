package dev.yashgupta.parking;

import java.sql.Timestamp;
import java.util.Date;

public class ParkingTicket {
	private int id;
	private String vehicleNumber;
	private Timestamp generatedTime;
	private boolean isBillPending;
	private int parkingLotId;
	private int floorId;
	private int spaceId;

	public ParkingTicket( String vehicleNumber, int floorId, int spaceId, int parkingLotId ) {
		this.id = 3;
		this.vehicleNumber = vehicleNumber;
		this.isBillPending = true;
		this.generatedTime = new Timestamp( new Date().getTime() );
		this.floorId = floorId;
		this.spaceId = spaceId;
		this.parkingLotId = parkingLotId;
	}

	public int getId() {
		return id;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public Timestamp getGeneratedTime() {
		return generatedTime;
	}

	public boolean isBillPending() {
		return isBillPending;
	}

	public boolean save() {
		return false;
	}
}
