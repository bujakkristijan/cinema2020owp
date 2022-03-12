package model;

public class Seat {
	
	private long id;
	private long number;
	private long hallId;
	
	public Seat() {
		
	}

	public Seat(long id, long number, long hallId) {
		super();
		this.id = id;
		this.number = number;
		this.hallId = hallId;
	}
	
	public Seat(long number, long hallId) {
		super();
		this.number = number;
		this.hallId = hallId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public long getHallId() {
		return hallId;
	}

	public void setHallId(long hallId) {
		this.hallId = hallId;
	}
	
	

}
