package model;

import java.util.Date;

public class User {
	
	private long id;
	private String username;
	private String password;
	private Date date;
	private String role;
	private int deleted;
	
	public User() {
		
	}

	public User(long id, String username, String password, Date date, String role, int deleted) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.date = date;
		this.role = role;
		this.deleted = deleted;
	}
	
	public User(String username, String password, Date date, String role, int deleted) {
		super();
		this.username = username;
		this.password = password;
		this.date = date;
		this.role = role;
		this.deleted = deleted;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
	

}
