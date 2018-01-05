package prs.controllers;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Optional;

import org.apache.http.ParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import prs.models.VisitModel;
import prs.models.VisitModelTable;
import prs.util.file.Open;
import prs.util.parse.MyDateTypeAdapter;

public class VisitController {
	private String token;
	@FXML
	private TableView<VisitModelTable> visitTable;
	@FXML
	private TableColumn<VisitModelTable, Date> date;
	@FXML
	private TableColumn<VisitModelTable, String> name;
	@FXML
	private TableColumn<VisitModelTable, String> surname;
	@FXML
	private TableColumn<VisitModel, String> purpose;
	@FXML
	private TableColumn<VisitModelTable, ImageView> image;
	@FXML
	private TextField filterField;
	private ObservableList<VisitModelTable> masterData = FXCollections.observableArrayList();
	Request request = new Request();
	private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	private static final String TIME_FORMAT = "HH:mm:ss";

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
		String month="0"+Integer.toString(calendar.get(Calendar.MONTH)+1);
		String year=Integer.toString(calendar.get(Calendar.YEAR));
		String date=day+month+year;
		System.out.println(day+month+year);
		String response = request.Get("/visit/thisDoctorDate="+date+"?", token);
		JsonElement json = new JsonParser().parse(response);
		JsonArray array = json.getAsJsonArray();
		Iterator iterator = array.iterator();
		Image image = new Image("/images/delete.png");
		
		while (iterator.hasNext()) {
			JsonElement json2 = (JsonElement) iterator.next();
			VisitModel visit = gson.fromJson(json2, VisitModel.class);
			VisitModelTable visit2 = new VisitModelTable(visit.getDate(), visit.getPatient().getName(),
					visit.getPatient().getSurname(), visit.getPurpose().getName(), new ImageView(image));
			masterData.add(visit2);
		}
	}

	@FXML
	void initialize() {
		// size
		visitTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		date.setMaxWidth(1f * Integer.MAX_VALUE * 30); // 10% width
		name.setMaxWidth(1f * Integer.MAX_VALUE * 20); // 10% width
		surname.setMaxWidth(1f * Integer.MAX_VALUE * 20); // 10% width
		purpose.setMaxWidth(1f * Integer.MAX_VALUE * 28); // 70% width
		image.setMaxWidth(1f * Integer.MAX_VALUE * 2);
		// ***************************************************************
		date.setCellValueFactory(new PropertyValueFactory<>("date"));
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
		purpose.setCellValueFactory(new PropertyValueFactory<>("purpose"));
		image.setCellValueFactory(new PropertyValueFactory<>("image"));
		visitTable.setItems(masterData);
		
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
			// DELETE
		} else if (result.get() == buttonTypeTwo) {
			alert.close();
			
			//System.out.println(visitTable.getItems().get(i).get);
		}
	}
}
