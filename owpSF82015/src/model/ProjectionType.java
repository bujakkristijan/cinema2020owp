package model;

public class ProjectionType {
	
	private long id;
	private String name;
	
	public ProjectionType() {
		
	}

	public ProjectionType(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public ProjectionType(String name) {
		super();
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
