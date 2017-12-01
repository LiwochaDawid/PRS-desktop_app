package prs.models;


public class PatientModel {
	private int patientID;
    private String name;
    private String surname;
    private String street;
    private String postcode;
    private String city;
    private String country;
    private String phoneNumber;
    public PatientModel(PatientModel patient) {
		this.patientID = patient.getPatientID();
		this.name = patient.getName();
		this.surname = patient.getSurname();
		this.street = patient.getStreet();
		this.postcode = patient.getPostcode();
		this.city = patient.getCity();
		this.country = patient.getCountry();
		this.phoneNumber = patient.getPhoneNumber();
	}
	public final int getPatientID() {
		return patientID;
	}
	public final void setPatientID(int patientID) {
		this.patientID = patientID;
	}
	public final String getName() {
		return name;
	}
	public final void setName(String name) {
		this.name = name;
	}
	public final String getSurname() {
		return surname;
	}
	public final void setSurname(String surname) {
		this.surname = surname;
	}
	public final String getStreet() {
		return street;
	}
	public final void setStreet(String street) {
		this.street = street;
	}
	public final String getPostcode() {
		return postcode;
	}
	public final void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public final String getCity() {
		return city;
	}
	public final void setCity(String city) {
		this.city = city;
	}
	public final String getCountry() {
		return country;
	}
	public final void setCountry(String country) {
		this.country = country;
	}
	public final String getPhoneNumber() {
		return phoneNumber;
	}
	public final void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
