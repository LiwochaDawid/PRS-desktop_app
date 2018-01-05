package prs.models;

import java.sql.Timestamp;
import java.util.Date;

import javafx.scene.image.ImageView;

public class VisitModelTable {
	private Date date;
	private String name;
	private String surname;
	private String purpose;
	private ImageView image;
	
	public VisitModelTable(Date date, String name, String surname, String purpose,ImageView image) {
		this.date=date;
		this.name=name;
		this.surname=surname;
		this.purpose=purpose;
		this.image=image;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public ImageView getImage() {
		return image;
	}

	public void setImage(ImageView image) {
		this.image = image;
	}
}
