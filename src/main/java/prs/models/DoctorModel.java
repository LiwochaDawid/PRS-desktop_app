package prs.models;

public class DoctorModel {
	private String name;
    private String surname;
    private String prefix;
    private String street;
    private String postcode;
    private String city;
    private String country;
    private String phoneNumber;
    public DoctorModel(String name, String surname, String prefix, String postcode,String street, String city, String country, String phoneNumber) {
		this.name = name;
		this.surname = surname;
		this.prefix = prefix;
		this.street = street;
		this.postcode = postcode;
		this.city = city;
		this.country = country;
		this.phoneNumber = phoneNumber;
	}
}
