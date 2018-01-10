package prs.models;

import java.sql.Time;

public class PurposeModel2 {
	private int purposeID;
	private String name;
	private String description;
	private Time duration;
	private int price;
public PurposeModel2(int purposeID, String name, String description, Time duration, int price) {
	this.purposeID=purposeID;
	this.name=name;
	this.description=description;
	this.duration=duration;
	this.price=price;
}
public int getPurposeID() {
	return purposeID;
}
public void setPurposeID(int purposeID) {
	this.purposeID = purposeID;
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
public Time getDuration() {
	return duration;
}
public void setDuration(Time duration) {
	this.duration = duration;
}
public int getPrice() {
	return price;
}
public void setPrice(int price) {
	this.price = price;
}

}

