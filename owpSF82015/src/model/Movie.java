package model;

public class Movie {
	
	private long id;
	private String name;
	private String director;
	private String actors;
	private String genres;
	private int duration;
	private String distributor;
	private String country;
	private int year;
	private String description;
	private int deleted;
	
	public Movie() {
		
	}

	public Movie(long id, String name, String director, String actors, String genres, int duration, String distributor, String country,
			int year, String description, int deleted) {
		super();
		this.id = id;
		this.name = name;
		this.director = director;
		this.actors = actors;
		this.genres = genres;
		this.duration = duration;
		this.distributor = distributor;
		this.country = country;
		this.year = year;
		this.description = description;
		this.deleted = deleted;
	}
	
	public Movie(String name, String director, String actors, String genres, int duration, String distributor, String country,
			int year, String description, int deleted) {
		super();
		this.name = name;
		this.director = director;
		this.actors = actors;
		this.genres = genres;
		this.duration = duration;
		this.distributor = distributor;
		this.country = country;
		this.year = year;
		this.description = description;
		this.deleted = deleted;
	}

	public String getDistributor() {
		return distributor;
	}

	public void setDistributor(String distributor) {
		this.distributor = distributor;
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

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getActors() {
		return actors;
	}

	public void setActors(String actors) {
		this.actors = actors;
	}

	public String getGenres() {
		return genres;
	}

	public void setGenres(String genres) {
		this.genres = genres;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
	
	

}
