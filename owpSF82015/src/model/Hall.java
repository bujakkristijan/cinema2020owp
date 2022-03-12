package model;

import java.util.ArrayList;

public class Hall {
	
	private long id;
	private String name;
	private ArrayList<ProjectionType> projectionTypeList;
	
	public Hall() {
		
	}

	public Hall(long id, String name, ArrayList<ProjectionType> projectionTypeList) {
		super();
		this.id = id;
		this.name = name;
		this.projectionTypeList = projectionTypeList;
	}
	
	public Hall(String name, ArrayList<ProjectionType> projectionTypeList) {
		super();
		this.name = name;
		this.projectionTypeList = projectionTypeList;
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

	public ArrayList<ProjectionType> getProjectionTypeList() {
		return projectionTypeList;
	}

	public void setProjectionTypeList(ArrayList<ProjectionType> projectionTypeList) {
		this.projectionTypeList = projectionTypeList;
	}
	
}
