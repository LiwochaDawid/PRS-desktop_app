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
    private String token;
    private String TIME_FORMAT = "HH:mm:ss";

	public VisitModel(String name, String surname, String purpose, String date){
        GsonBuilder gSonBuilder = new GsonBuilder();
        Gson gson = gSonBuilder.create();
	    Integer patientID = null;
	    Request request = new Request();
	    Timestamp timestamp = null;
	    try {
            token = Open.openFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        findAndSetPatient(name, surname);
        findAndSetPurpose(purpose);
        findAndSetDoctor();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date parsedDate = dateFormat.parse(date);
            timestamp = new java.sql.Timestamp(parsedDate.getTime());
        } catch(Exception e) {
        }
        setDate(timestamp);
        setComment("Lalala");
        System.out.println(getVisitID());
    }

    private final void findAndSetPatient(String name, String surname){
        GsonBuilder gSonBuilder = new GsonBuilder();
        Gson gson = gSonBuilder.create();
        Request request = new Request();
        String response = request.Get("/patient/all?",token);
        JsonElement json = new JsonParser().parse(response);
        System.out.println(response);
        JsonArray array = json.getAsJsonArray();
        Iterator iterator = array.iterator();
        while (iterator.hasNext()) {
            JsonElement json2 = (JsonElement) iterator.next();
            PatientModel tempPatient = gson.fromJson(json2, PatientModel.class);
            if (name.equals(tempPatient.getName()) && surname.equals(tempPatient.getSurname()))
                setPatient(tempPatient);
        }
    }

    private final void findAndSetPurpose(String name){
        GsonBuilder gSonBuilder = new GsonBuilder();
        gSonBuilder.registerTypeAdapter(Time.class, new VisitModel.TimeDeserializer());
        Gson gson = gSonBuilder.create();
        Request request = new Request();
        String response = request.Get("/purpose/doctor?",token);
        JsonElement json = new JsonParser().parse(response);
        System.out.println(response);
        JsonArray array = json.getAsJsonArray();
        Iterator iterator = array.iterator();
        while (iterator.hasNext()) {
            JsonElement json2 = (JsonElement) iterator.next();
            PurposeModel tempPurpose = gson.fromJson(json2, PurposeModel.class);
            if (name.equals(tempPurpose.getName()))
                setPurpose(tempPurpose);
        }
    }

    private final void findAndSetDoctor(){
        GsonBuilder gSonBuilder = new GsonBuilder();
        Gson gson = gSonBuilder.create();
        Request request = new Request();
        String response = request.Get("/doctor/this?",token);
        JsonElement json = new JsonParser().parse(response);
        setDoctor(gson.fromJson(json, DoctorModel.class));
    }

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


    private class TimeDeserializer implements JsonDeserializer<Time> {
        @Override
        public Time deserialize(JsonElement jsonElement, Type typeOF, JsonDeserializationContext context)
                throws JsonParseException {
            try {

                String s = jsonElement.getAsString();
                SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT, Locale.US);
                long ms = 0;
                try {
                    sdf.parse(s);
                    ms = sdf.parse(s).getTime();
                } catch (java.text.ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                Time t = new Time(ms);
                return t;
            } catch (ParseException e) {
            }
            throw new JsonParseException(
                    "Unparseable time: \"" + jsonElement.getAsString() + "\". Supported formats: " + TIME_FORMAT);
        }
    }
}

