package prs.models;


import com.google.gson.*;
import org.apache.http.ParseException;
import prs.controllers.Request;
import prs.controllers.VisitController;
import prs.util.file.Open;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.Table;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Table(name="visits")
public class VisitModel {
    @Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="visit_id", unique = true, nullable = false)
	private int visitID;
	@OneToOne
	@JoinColumn(name = "patient_id")
	private PatientModel patient;
	@OneToOne
	@JoinColumn(name = "doctor_id")
	private DoctorModel doctor;
    @Column(name="date")
	private Timestamp date;
    @Column(name="comment")
    private String comment;
	@OneToOne
	@JoinColumn(name = "purpose_id")
	private PurposeModel purpose;

    public final int getVisitID()
    {
        return visitID;
    }
    
    public final PatientModel getPatient()
    {
        return patient;
    }
    
    public final DoctorModel getDoctor()
    {
        return doctor;
    }
    
    public final Timestamp getDate()
    {
        return date;
    }
    
    public final String getComment()
    {
        return comment;
    }
    
    public final PurposeModel getPurpose()
    {
        return purpose;
    }
    
    public final void setVisitID(int visitID)
    {
        this.visitID=visitID;
    }
    
    public final void setPatient(PatientModel patient)
    {
        this.patient=patient;
    }
    
    public final void setDoctor(DoctorModel doctor)
    {
        this.doctor=doctor;
    }
    
    public final void setDate(Timestamp date)
    {
        this.date=date;
    }
    
    public final void setComment(String comment)
    {
        this.comment=comment;
    }
    
    public final void setPurpose(PurposeModel purpose)
    {
        this.purpose=purpose;
    }
}

