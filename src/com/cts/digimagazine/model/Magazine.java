package com.cts.digimagazine.model;

public class Magazine {
	
	private int magazineId;
	private String title;
	private String genre;
	private String publicationFrequency;
	private String publisher;
	
	public Magazine(int magazineId, String title, String genre, String publicationFrequency, String publisher) {
		super();
		this.magazineId = magazineId;
		this.title = title;
		this.genre = genre;
		this.publicationFrequency = publicationFrequency;
		this.publisher = publisher;
	}
	
	public Magazine(String title,String genre,String publicationFrequency,String publisher) {
		this.title = title;
		this.genre = genre;
		this.publicationFrequency = publicationFrequency;
		this.publisher = publisher;
	}
	
	//Getters and Setters
	
	public int getMagazineId() {
		return magazineId;
	}
	
	public void setMagazineId(int magazineId) {
		this.magazineId = magazineId;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getGenre() {
		return genre;
	}
	

	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public String getPublicationFrequency() {
		return publicationFrequency;
	}
	
	public void setPublicationFrequency(String publicationFrequency) {
		this.publicationFrequency = publicationFrequency;
	}
	
	public String getPublisher() {
		return publisher;
	}
	
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	@Override
	public String toString() {
		return "Magazine [ID=" + magazineId + ",Title="+ title+", Genre="+genre+", Frequency="+publicationFrequency+", Publisher="+ publisher +"]";
	}
}
