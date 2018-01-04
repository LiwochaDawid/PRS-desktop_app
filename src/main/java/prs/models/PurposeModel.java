package prs.models;

import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.Table;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="purposes")
public class PurposeModel {
    @Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="purpose_id")
	private int purposeID;
    @Column(name = "name")
	private String name;
	@Column(name = "description")
	private String description;
    @Column(name="duration")
	private Time duration;
    @Column(name="price")
    private int price;
	@OneToOne
	@JoinColumn(name = "doctor_id")
	private DoctorModel doctor;

    public final int getPurposeID()
    {
        return purposeID;
    }

    public final void setPurposeID(int purposeID)
    {
    	this.purposeID = purposeID;
    }

    public final String getName()
    {
        return name;
    }

    public final void setName(String name)
    {
    	this.name = name;
    }

    public final String getDescription()
    {
        return description;
    }

    public final void setDescription(String description)
    {
    	this.description = description;
    }

    public final Time getDuration()
    {
        return duration;
    }

    public final void setDuration(Time duration)
    {
    	this.duration = duration;
    }

    public final int getPrice()
    {
        return price;
    }

    public final void setPrice(int price)
    {
    	this.price = price;
    }

    public final DoctorModel getDoctor()
    {
        return doctor;
    }

    public final void setDoctor(DoctorModel doctor)
    {
    	this.doctor = doctor;
    }
}


