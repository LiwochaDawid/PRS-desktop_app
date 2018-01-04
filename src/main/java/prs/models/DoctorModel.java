package prs.models;

public class DoctorModel {
	private String doctorId;
	private String name;
    private String surname;
    private String prefix;
    private String street;
    private String postcode;
    private String city;
    private String country;
    private String phoneNumber;
    public DoctorModel(String doctorId, String name, String surname, String prefix, String postcode,String street, String city, String country, String phoneNumber) {
    	this.doctorId=doctorId;
		this.name = name;
		this.surname = surname;
		this.prefix = prefix;
		this.street = street;
		this.postcode = postcode;
		this.city = city;
		this.country = country;
		this.phoneNumber = phoneNumber;
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
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
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
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}
}
