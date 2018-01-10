package prs.models;

import com.google.gson.*;
import org.apache.http.ParseException;
import prs.controllers.Request;
import prs.util.file.Open;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

public class addVisitModel {
    private PatientModel patient;
    private PurposeModel purpose;
    private DoctorModel doctor;
    private Timestamp date;

    public addVisitModel(String name, String surname, String purpose, String date){
        GsonBuilder gSonBuilder = new GsonBuilder();
        String token = null;
        String TIME_FORMAT = "HH:mm:ss";
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
        findAndSetPatient(name, surname, token);
        findAndSetPurpose(purpose,token);
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date parsedDate = dateFormat.parse(date);
            timestamp = new java.sql.Timestamp(parsedDate.getTime());
        } catch(Exception e) {
        }
        setDate(timestamp);
    }

    private final void findAndSetPatient(String name, String surname, String token){
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

    private final void findAndSetPurpose(String name, String token){
        GsonBuilder gSonBuilder = new GsonBuilder();
        gSonBuilder.registerTypeAdapter(Time.class, new addVisitModel.TimeDeserializer());
        Gson gson = gSonBuilder.create();
        String TIME_FORMAT = "HH:mm:ss";
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

    private final void findAndSetDoctor(String token){
        GsonBuilder gSonBuilder = new GsonBuilder();
        Gson gson = gSonBuilder.create();
        Request request = new Request();
        String response = request.Get("/doctor/this?",token);
        JsonElement json = new JsonParser().parse(response);
        setDoctor(gson.fromJson(json, DoctorModel.class));
    }

    public final PatientModel getPatient()
    {
        return patient;
    }

    public final Timestamp getDate()
    {
        return date;
    }

    public final PurposeModel getPurpose()
    {
        return purpose;
    }

    public final void setPatient(PatientModel patient)
    {
        this.patient=patient;
    }

    public final void setDate(Timestamp date)
    {
        this.date=date;
    }

    public final void setPurpose(PurposeModel purpose)
    {
        this.purpose=purpose;
    }

    public void setDoctor(DoctorModel doctor) {
        this.doctor = doctor;
    }


    private class TimeDeserializer implements JsonDeserializer<Time> {
        @Override
        public Time deserialize(JsonElement jsonElement, Type typeOF, JsonDeserializationContext context)
                throws JsonParseException {
            String TIME_FORMAT = "HH:mm:ss";
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


