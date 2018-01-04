package prs.models;

import java.sql.Time;

public class addPurposeModel {
	private String name;
	private String description;
	private String duration;
	private int price;
	
	public addPurposeModel(String name, String description, String duration, int price) {
		this.name=name;
		this.description=description;
		this.duration=duration;
		this.price=price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
}
