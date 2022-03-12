package model;

import java.util.Date;

public class Ticket {
	
	private long id;
	private long projectionId;
	private long seatNumber;
	private Date date;
	private long userId;
	private int deleted;
	
	public Ticket() {
		
	}

	public Ticket(long id, long projectionId, long seatNumber, Date date, long userId, int deleted) {
		super();
		this.id = id;
		this.projectionId = projectionId;
		this.seatNumber = seatNumber;
		this.date = date;
		this.userId = userId;
		this.deleted = deleted;
	}
	
	public Ticket(long projectionId, long seatNumber, Date date, long userId, int deleted) {
		super();
		this.projectionId = projectionId;
		this.seatNumber = seatNumber;
		this.date = date;
		this.userId = userId;
		this.deleted = deleted;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getProjectionId() {
		return projectionId;
	}

	public void setProjectionId(long projectionId) {
		this.projectionId = projectionId;
	}

	public long getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(long seatNumber) {
		this.seatNumber = seatNumber;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
	
	

}
