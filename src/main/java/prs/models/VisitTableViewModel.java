package prs.models;

public class VisitTableViewModel {
	private String date;
	private String name;
	private String surname;
	private String purpose;
	
	public VisitTableViewModel(String date, String name, String surname, String purpose) {
		this.date=date;
		this.name=name;
		this.surname=surname;
		this.purpose=purpose;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
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
}
