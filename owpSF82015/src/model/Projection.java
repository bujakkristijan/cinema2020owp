package model;

import java.util.Date;

public class Projection {
	
	private long id;
	private long movieId;
	private long projectionTypeId;
	private long hallId;
	private Date date;
	private double price;
	private long userId;
	private int deleted;
	
	public Projection() {
		
	}

	public Projection(long id, long movieId, long projectionTypeId, long hallId, Date date, double price, long userId,
			int deleted) {
		super();
		this.id = id;
		this.movieId = movieId;
		this.projectionTypeId = projectionTypeId;
		this.hallId = hallId;
		this.date = date;
		this.price = price;
		this.userId = userId;
		this.deleted = deleted;
	}
	
	public Projection(long movieId, long projectionTypeId, long hallId, Date date, double price, long userId,
			int deleted) {
		super();
		this.movieId = movieId;
		this.projectionTypeId = projectionTypeId;
		this.hallId = hallId;
		this.date = date;
		this.price = price;
		this.userId = userId;
		this.deleted = deleted;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getMovieId() {
		return movieId;
	}

	public void setMovieId(long movieId) {
		this.movieId = movieId;
	}

	public long getProjectionTypeId() {
		return projectionTypeId;
	}

	public void setProjectionTypeId(long projectionTypeId) {
		this.projectionTypeId = projectionTypeId;
	}

	public long getHallId() {
		return hallId;
	}

	public void setHallId(long hallId) {
		this.hallId = hallId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
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
