package dev.yashgupta.parking;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class ParkingTicket {
	private int id;
	private String vehicleNumber;
	private Timestamp generatedTime;
	private boolean isBillPending;
	private int spaceId;

	public ParkingTicket( String vehicleNumber, int id, int spaceId ) {
		this.id = id;
		this.vehicleNumber = vehicleNumber;
		this.isBillPending = true;
		this.generatedTime = new Timestamp( new Date().getTime() );
		this.spaceId = spaceId;
	}

	public ParkingTicket( ResultSet res ) throws SQLException {
		this.vehicleNumber = res.getString( 1 );
		this.spaceId = Integer.parseInt( res.getString( 3 ) );
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
