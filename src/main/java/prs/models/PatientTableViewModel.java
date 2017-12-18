package prs.models;

public class PatientTableViewModel {
	private String name;
	private String surname;
	private String street;
	private String postcode;
	private String city;
	private String phoneNumber;
	
	public PatientTableViewModel(String name, String surname, String street, String postcode, String city,  String phoneNumber) {
		this.name=name;
		this.surname=surname;
		this.street=street;
		this.postcode=postcode;
		this.city=city;
		this.phoneNumber=phoneNumber;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
}
