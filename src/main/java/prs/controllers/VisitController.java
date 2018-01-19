package prs.controllers;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Optional;
import java.util.*;

import org.apache.http.ParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import prs.models.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import prs.models.DoctorModel;
import prs.models.PatientModel;
import prs.models.PatientTableViewModel;
import prs.models.PurposeModel;
import prs.models.VisitModel;
import prs.models.VisitModel2;
import prs.models.VisitModelTable;
import prs.models.addPurposeModel;
import prs.util.file.Open;
import prs.util.parse.MyDateTypeAdapter;
import prs.util.parse.MySqlDateTypeAdapter;
import sun.java2d.pipe.SpanShapeRenderer;


import static prs.util.calendar.FullCalendarView.getDate;

public class VisitController {
	private String token;
	private int patientCount;
	private int purposeCount;
	@FXML
	private ComboBox<String> time;
	private AnchorPane anchorPane;
	@FXML
	public TableView<VisitModelTable> visitTable;
	@FXML
	public TableColumn<VisitModelTable, Integer> id;
	@FXML
	public TableColumn<VisitModelTable, Date> date;
	@FXML
	public TableColumn<VisitModelTable, String> name;
	@FXML
	public TableColumn<VisitModelTable, String> surname;
	@FXML
	public TableColumn<VisitModel, String> purpose;
	@FXML
	public TableColumn<VisitModelTable, ImageView> image;
	@FXML 
	private TextField comment;
	@FXML
	private ComboBox<String> purposeList;
	private ObservableList<VisitModelTable> masterData = FXCollections.observableArrayList();
	private ObservableList<PatientTableViewModel> patientData = FXCollections.observableArrayList();
	private ObservableList<PurposeModel> purposeData = FXCollections.observableArrayList();
	Request request = new Request();
	private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	private static final String SQLDATE_FORMAT = "yyyy-MM-dd";
	private static final String TIME_FORMAT = "HH:mm:ss";
	@FXML
	private ComboBox<String> patientList;
	@FXML
	private DatePicker dateVisit;
	private String visitDate=null;
	private ConfigurationModel configuration;
	List<VisitModel> visitList = new ArrayList<>();

	private class DateDeserializer implements JsonDeserializer<Date> {
		@Override
		public Date deserialize(JsonElement jsonElement, Type typeOF, JsonDeserializationContext context)
				throws JsonParseException {
			try {
				return new SimpleDateFormat(DATE_FORMAT, Locale.US).parse(jsonElement.getAsString());
			} catch (java.text.ParseException e) {
			}

			throw new JsonParseException(
					"Unparseable date: \"" + jsonElement.getAsString() + "\". Supported formats: " + DATE_FORMAT);
		}
	}

	private class SqlDateDeserializer implements JsonDeserializer<java.sql.Date> {
		@Override
		public java.sql.Date deserialize(JsonElement jsonElement, Type typeOF, JsonDeserializationContext context)
				throws JsonParseException {
			try {
				Date parsed=new SimpleDateFormat(SQLDATE_FORMAT, Locale.US).parse(jsonElement.getAsString());
				return new java.sql.Date(parsed.getTime());
			} catch (java.text.ParseException e) {
			}

			throw new JsonParseException(
					"Unparseable date: \"" + jsonElement.getAsString() + "\". Supported formats: " + DATE_FORMAT);
		}
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

	public VisitController() {
		GsonBuilder gSonBuilder = new GsonBuilder();
		gSonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
		gSonBuilder.registerTypeAdapter(Time.class, new TimeDeserializer());
		gSonBuilder.registerTypeAdapter(Date.class, new MyDateTypeAdapter()).create();
		Gson gson = gSonBuilder.create();
		try {
			token = Open.openFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Request request = new Request();
		Calendar calendar = Calendar.getInstance();
		String day="";
		if(calendar.get(Calendar.DAY_OF_MONTH)<10)
			day+="0"+calendar.get(Calendar.DAY_OF_MONTH);
		else
			day=Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
		
		String month="";
		if(calendar.get(Calendar.MONTH)+1<10)
			month="0"+Integer.toString(calendar.get(Calendar.MONTH)+1);
		else
			month=Integer.toString(calendar.get(Calendar.MONTH)+1);
		
		String year=Integer.toString(calendar.get(Calendar.YEAR));
		String date = day + month + year;
		System.out.println(day+month+year);
		if (getDate()!=null)
			date=getDate();
		
		
		String response=request.Get("/patient/all?", token);
		JsonElement json = new JsonParser().parse(response);    
		JsonArray array= json.getAsJsonArray();    
		Iterator iterator = array.iterator();   
		while(iterator.hasNext()){
		    JsonElement json2 = (JsonElement)iterator.next();
		    PatientTableViewModel patient = gson.fromJson(json2, PatientTableViewModel.class);
		    patientData.add(patient);
		    patientCount++;
		}
		Request request2 = new Request();
		String response2 = request2.Get("/visit/thisDoctorDate="+date+"?", token);
		JsonElement json2 = new JsonParser().parse(response2);
		JsonArray array2 = json2.getAsJsonArray();
		Iterator iterator2 = array2.iterator();
		Image image = new Image("/images/delete.png");
		
		while (iterator2.hasNext()) {
			JsonElement json3 = (JsonElement) iterator2.next();
			VisitModel visit = gson.fromJson(json3, VisitModel.class);
			VisitModelTable visit2 = new VisitModelTable(visit.getVisitID(), visit.getDate(), visit.getPatient().getName(),
					visit.getPatient().getSurname(), visit.getPurpose().getName(), new ImageView(image));
			masterData.add(visit2);
		}

		Request request3 = new Request();
		String response3 = request3.Get("/purpose/doctor?", token);
		JsonElement json3 = new JsonParser().parse(response3);
		JsonArray array3 = json3.getAsJsonArray();
		Iterator iterator3 = array3.iterator();
		while (iterator3.hasNext()) {
			JsonElement json4 = (JsonElement) iterator3.next();
			PurposeModel purpose = gson.fromJson(json4, PurposeModel.class);
			purposeData.add(purpose);
			System.out.println(purpose.getDescription());
			purposeCount++;
		}


	}
	public VisitController(String date) {
		this.visitDate=date;
		GsonBuilder gSonBuilder = new GsonBuilder();
		gSonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
		gSonBuilder.registerTypeAdapter(Time.class, new TimeDeserializer());
		gSonBuilder.registerTypeAdapter(Date.class, new MyDateTypeAdapter()).create();
		Gson gson = gSonBuilder.create();
		try {
			token = Open.openFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Request request = new Request();
		String response=request.Get("/patient/all?", token);
		JsonElement json = new JsonParser().parse(response);    
		JsonArray array= json.getAsJsonArray();    
		Iterator iterator = array.iterator();   
		while(iterator.hasNext()){
		    JsonElement json2 = (JsonElement)iterator.next();
		    PatientTableViewModel patient = gson.fromJson(json2, PatientTableViewModel.class);
		    patientData.add(patient);
		}
		Request request2 = new Request();
		String response2 = request2.Get("/visit/thisDoctorDate="+date+"?", token);
		JsonElement json2 = new JsonParser().parse(response2);
		JsonArray array2 = json2.getAsJsonArray();
		Iterator iterator2 = array2.iterator();
		Image image = new Image("/images/delete.png");
		System.out.println(date);
		
		while (iterator2.hasNext()) {
			JsonElement json3 = (JsonElement) iterator2.next();
			VisitModel visit = gson.fromJson(json3, VisitModel.class);
            System.out.println(visit.getDate());
			VisitModelTable visit2 = new VisitModelTable(visit.getVisitID(), visit.getDate(), visit.getPatient().getName(),
					visit.getPatient().getSurname(), visit.getPurpose().getName(), new ImageView(image));

			masterData.add(visit2);
		}
	}

	@FXML
	void initialize() {
		Callback<DatePicker, DateCell> dayCellFactory = dp -> new DateCell()
        {
            @Override
            public void updateItem(LocalDate item, boolean empty)
            {
                super.updateItem(item, empty);

                if(item.isBefore(LocalDate.now()))
                {
                    setStyle("-fx-background-color: #ffc0cb;");
                    Platform.runLater(() -> setDisable(true));                }
            }
        };
        dateVisit.setDayCellFactory(dayCellFactory);


		patientList.getItems().clear();
		purposeList.getItems().clear();
		visitTable.getItems().clear();
		for (int i=0; i<patientCount; i++) {
		patientList.getItems().add(patientData.get(i).getPatientID()+", "+patientData.get(i).getName()+" "+patientData.get(i).getSurname());
		}
		patientList.setValue(patientList.getItems().get(0));
		for (int i=0; i<purposeCount; i++) {
		purposeList.getItems().add(purposeData.get(i).getPurposeID()+", "+purposeData.get(i).getName());
		}
		purposeList.setValue(purposeList.getItems().get(0));
		
		visitTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		id.setMaxWidth(1f*Integer.MAX_VALUE * 5);
		date.setMaxWidth(1f * Integer.MAX_VALUE * 25); // 10% width
		name.setMaxWidth(1f * Integer.MAX_VALUE * 20); // 10% width
		surname.setMaxWidth(1f * Integer.MAX_VALUE * 20); // 10% width
		purpose.setMaxWidth(1f * Integer.MAX_VALUE * 28); // 70% width
		image.setMaxWidth(1f * Integer.MAX_VALUE * 2);
		// ***************************************************************
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		date.setCellValueFactory(new PropertyValueFactory<>("date"));
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
		purpose.setCellValueFactory(new PropertyValueFactory<>("purpose"));
		image.setCellValueFactory(new PropertyValueFactory<>("image"));
		
		visitTable.setItems(masterData);
		setWorkTime();
//		populateTimeBox();
	}
	@FXML
	public void deleteVisit() {
		int i = visitTable.getSelectionModel().getSelectedCells().get(0).getRow();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete visit");
		alert.setHeaderText("Confirm delete");
		alert.setContentText("Do you want to delete visit for " + visitTable.getItems().get(i).getName() + " "
				+ visitTable.getItems().get(i).getSurname());
		ButtonType buttonTypeOne = new ButtonType("YES");
		ButtonType buttonTypeTwo = new ButtonType("NO");
		alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeOne) {
			int id=visitTable.getSelectionModel().getSelectedItem().getId();
			Request delete=new Request();
			delete.deleteVisit("/visit/deleteAsDoctorVisitID=", token, id);

			visitTable.getItems().remove(i);
			visitTable.refresh();
		} else if (result.get() == buttonTypeTwo) {
			alert.close();
		}
	}
	
	@FXML
	public void addVisit() {
		String pt=patientList.getValue();
		System.out.println(pt);
		String id[]=pt.split(",");
		String puValue=purposeList.getValue();
		String idPurp[]=puValue.split(",");
		Request request = new Request();
		String response=request.Get("/doctor/this?", token);
		JsonElement json = new JsonParser().parse(response);    
		GsonBuilder gSonBuilder = new GsonBuilder();
		gSonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
		gSonBuilder.registerTypeAdapter(Time.class, new TimeDeserializer());
		gSonBuilder.registerTypeAdapter(Date.class, new MyDateTypeAdapter()).create();
		Gson gson = gSonBuilder.create();
		DoctorModel doctor = gson.fromJson(json, DoctorModel.class);	
		if(!id[0].isEmpty()) {
			Timestamp timestamp = Timestamp.valueOf(dateVisit.getValue().toString()+" "+ time.getValue()+":00");
			int idPatient=Integer.parseInt(id[0]);
			
			String entry="{ \"date\":"+timestamp.getTime()+", \"comment\": \""+comment.getText()+"\", "
					+ "\"patient\": { \"patientID\":"+idPatient+"}, \"purpose\": { \"purposeID\": "+idPurp[0]+"}  }";
			System.out.println(entry);
			Request requesT = new Request();
			Image image = new Image("/images/delete.png");
			requesT.addVisit("/visit/addAsDoctor?", token, entry);
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
			Request requestx = new Request();
			String responsex = requestx.Get("/visit/thisDoctorDate="+sdf.format(Date.from(dateVisit.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()))+"?", token);
			JsonElement jsonx = new JsonParser().parse(responsex);
			JsonArray array = jsonx.getAsJsonArray();
			Iterator iterator2 = array.iterator();
			visitTable.getItems().clear();
			while (iterator2.hasNext()) {
				JsonElement json3 = (JsonElement) iterator2.next();
				VisitModel visit = gson.fromJson(json3, VisitModel.class);
				System.out.println("Visits: " + visit.getVisitID());
				visitTable.getItems().add(visitTable.getItems().size(),new VisitModelTable(visit.getVisitID(),visit.getDate(),visit.getPatient().getName(),visit.getPatient().getSurname(), visit.getPurpose().getName(),new ImageView(image)));
			}
		}
	}

	public void updateView() {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/VisitLayout.fxml"));
		AnchorPane pane = null;

		try {
			pane = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		pane.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
		pane.setPrefSize(1000, 800);
		//pane.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

		VisitController visit=new VisitController();
		loader.setController(visit);

		VBox bp=(VBox)anchorPane.getParent();
		bp.setPrefSize(1000,800);
		bp.getChildren().clear();
		bp.getChildren().add(pane);

	}

	private int hourParse(Time time){
		Long hour = time.getTime()/1000/60/60;
		return hour.intValue()+1;
	}

	private int minParse(Time time){
		Long min = time.getTime()/1000/60;
		while (min > 59.99999999999999)
			min-=60;
		return min.intValue();
	}

	public void populateTimeBox() throws java.text.ParseException {
		GsonBuilder gSonBuilder = new GsonBuilder();
		gSonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
		gSonBuilder.registerTypeAdapter(Time.class, new TimeDeserializer());
		gSonBuilder.registerTypeAdapter(Date.class, new MyDateTypeAdapter()).create();
		Gson gson = gSonBuilder.create();
		Integer hour;
		Integer min;
		Integer interval = 30;
		ObservableList<String> oTimes = FXCollections.observableArrayList();
		String stringTime = null;
		Time startTime = new Time(0);
		Time endTime = new Time(0);
		Time t = new Time(0);
		Calendar calendar = Calendar.getInstance();
		Request request = new Request();
		String idPurp[]=purposeList.getValue().split(",");
		Request request2 = new Request();

		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		String response = request.Get("/visit/thisDoctorDate="+sdf.format(Date.from(dateVisit.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()))+"?", token);
		JsonElement json = new JsonParser().parse(response);
		JsonArray array = json.getAsJsonArray();
		Iterator iterator2 = array.iterator();
		visitList.clear();
		while (iterator2.hasNext()) {
			JsonElement json3 = (JsonElement) iterator2.next();
			VisitModel visit = gson.fromJson(json3, VisitModel.class);
			System.out.println("Visits: " + visit.getVisitID());
			visitList.add(visit);
		}
		for (int i = 0; i< purposeData.size(); i++)
		{

			if (purposeList.getValue().contains(purposeData.get(i).getName())) {
				t = purposeData.get(i).getDuration();
				System.out.println("TEST VALUE: "+purposeData.get(i).getDuration() + " By: "+purposeData.get(i).getName());
			}
		}
		if (dateVisit.getValue()!=null){
			switch (dateVisit.getValue().getDayOfWeek()) {
				case MONDAY: {
					startTime = configuration.getMoWorkStart();
					endTime = configuration.getMoWorkEnd();
					break;
				}
				case WEDNESDAY: {
					startTime = configuration.getWeWorkStart();
					endTime = configuration.getWeWorkEnd();
					break;
				}
				case TUESDAY: {
					startTime = configuration.getTuWorkStart();
					endTime = configuration.getTuWorkEnd();
					break;
				}
				case THURSDAY: {
					startTime = configuration.getThWorkStart();
					endTime = configuration.getThWorkEnd();
					break;
				}
				case FRIDAY: {
					startTime = configuration.getFrWorkStart();
					endTime = configuration.getFrWorkEnd();
					break;
				}
				case SATURDAY: {
					startTime = configuration.getSaWorkStart();
					endTime = configuration.getSaWorkEnd();
					break;
				}
				case SUNDAY: {
					startTime = configuration.getSuWorkStart();
					endTime = configuration.getSuWorkEnd();
					break;
				}
				default: {
					startTime.setTime(0);
					startTime.setTime(0);
				}
			}

			System.out.println("Time starts: "+hourParse(startTime)+":"+minParse(startTime)+" Time ends: "+hourParse(endTime)+":"+minParse(endTime));
			for (hour = hourParse(startTime); hour <= hourParse(endTime); hour++) {
				Calendar endDuration = Calendar.getInstance();
				for (min = minParse(startTime); min < 60; min += interval) {
					System.out.println("Hour added: "+hour+":"+min);

							if (hour < 10 && min < 10)
								stringTime = "0" + hour.toString() + ":0" + min.toString();
							else if (min < 10)
								stringTime = hour.toString() + ":0" + min.toString();
							else if (hour < 10)
								stringTime = "0" + hour.toString() + ":" + min.toString();
							else
								stringTime = hour.toString() + ":" + min.toString();
					long addedDuration = timeFormat.parse(stringTime).getTime()+t.getTime()+3600000;
					if ((hour == hourParse(endTime) && min >= minParse(endTime))|| addedDuration > endTime.getTime())
						break;
							if (isItVisitTime(stringTime,timeFormat.format(t.getTime())))
							 	oTimes.add(stringTime);
							System.out.println(isItVisitTime(stringTime, timeFormat.format(t.getTime())));

				}
			}
		}
		time.setItems(oTimes);
	}

	private Boolean isItVisitTime(String stringTime, String newVisitDuration) throws java.text.ParseException {
			boolean returnValue = true;
		for (int i = 0; i < visitList.size(); i++) {
			System.out.println(visitList.get(i).getPatient().getName());
			System.out.println(visitList.get(i).getPurpose().getDescription());
			PurposeModel purpose = visitList.get(i).getPurpose();
			System.out.println("Time considered: "+stringTime);
			SimpleDateFormat stringParser = new SimpleDateFormat("HH:mm");
			SimpleDateFormat durationParser = new SimpleDateFormat("HH:mm:ss");
			Calendar checkedTime = Calendar.getInstance();
			Calendar visitTime = Calendar.getInstance();
			Calendar durationTime = Calendar.getInstance();
			Calendar checkedTimeDuration = Calendar.getInstance();
			checkedTimeDuration.setTime(stringParser.parse(newVisitDuration));
			visitTime.setTime(stringParser.parse(stringParser.format(visitList.get(i).getDate())));
			checkedTime.setTime(stringParser.parse(stringTime));
			long t= visitTime.getTimeInMillis();
			durationTime.setTime(durationParser.parse(purpose.getDuration().toString()));
			long t2 = durationTime.getTimeInMillis()+3600000;
			long t3 = checkedTime.getTimeInMillis();
			long t4 = checkedTimeDuration.getTimeInMillis()+3600000;
			Date afterAddingDuration=new Date(t + t2);
			Date checkedAfterAddingDuration = new Date (t3 + t4);
			System.out.println("Visit start: "+visitTime.getTime()+" Visit end: "+afterAddingDuration);
			for (long time = t3; time < t3+t4; time+=300000){
				Date temp = new Date(time);
				if (!((stringParser.parse(stringTime).before(visitTime.getTime()) || stringParser.parse(stringTime).after(afterAddingDuration))))
					{
						returnValue = false;
					}
					else if (!(temp.before(visitTime.getTime())||temp.after(afterAddingDuration)))
						returnValue = false;
			}
		}
		return returnValue;
	}

	private void setWorkTime(){
		GsonBuilder gSonBuilder = new GsonBuilder();
		gSonBuilder.registerTypeAdapter(Date.class, new SqlDateDeserializer());
		gSonBuilder.registerTypeAdapter(Time.class, new TimeDeserializer());
		gSonBuilder.registerTypeAdapter(Date.class, new MySqlDateTypeAdapter()).create();
		Gson gson = gSonBuilder.create();
		Request request = new Request();
		String response = request.Get("/configuration/doctor?", token);
		JsonElement json = new JsonParser().parse(response);
		configuration = gson.fromJson(json, ConfigurationModel.class);

	}

}
