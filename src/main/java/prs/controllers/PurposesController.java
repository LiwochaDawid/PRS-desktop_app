package prs.controllers;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import org.apache.http.ParseException;

import prs.models.PatientTableViewModel;
import prs.models.PurposeModel;
import prs.models.addPurposeModel;
import prs.util.file.Open;

public class PurposesController {
	private String token;
	@FXML
	private TableColumn<PurposeModel, Integer> ID;
	@FXML
	private TableColumn<PurposeModel, String> name;
	@FXML
	private TableColumn<PurposeModel, String> description;
	@FXML
	private TableColumn<PurposeModel, Time> duration;
	@FXML
	private TableColumn<PurposeModel, Integer> price;
	@FXML
	private TableView<PurposeModel> purposeTable;
	@FXML
	private TextField nameField;
	@FXML
	private TextField descriptionField;
	@FXML
	private TextField durationField;
	@FXML
	private TextField priceField;
	private ObservableList<PurposeModel> masterData = FXCollections.observableArrayList();
	private ObservableList<PurposeModel> filteredData = FXCollections.observableArrayList();
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

	public PurposesController() {
		GsonBuilder gSonBuilder = new GsonBuilder();
		gSonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
		gSonBuilder.registerTypeAdapter(Time.class, new TimeDeserializer());
		Gson gson = gSonBuilder.create();
		try {
			token = Open.openFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Request request = new Request();
		String response = request.Get("/purpose/doctor?", token);
		JsonElement json = new JsonParser().parse(response);
		JsonArray array = json.getAsJsonArray();
		Iterator iterator = array.iterator();
		while (iterator.hasNext()) {
			JsonElement json2 = (JsonElement) iterator.next();
			// System.out.println(gson.fromJson(json2, PurposeModel.class).getName());
			PurposeModel purpose = gson.fromJson(json2, PurposeModel.class);
			masterData.add(purpose);

		};
	}
	@FXML
	void initialize() {
		// size
		purposeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		ID.setMaxWidth(1f * Integer.MAX_VALUE * 5); // 10% width
		name.setMaxWidth(1f * Integer.MAX_VALUE * 15); // 10% width
		description.setMaxWidth(1f * Integer.MAX_VALUE * 60); // 10% width
		duration.setMaxWidth(1f * Integer.MAX_VALUE * 10); // 70% width
		price.setMaxWidth(1f * Integer.MAX_VALUE * 10); // 70% width
		// ***************************************************************

		ID.setCellValueFactory(new PropertyValueFactory<>("purposeID"));
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		description.setCellValueFactory(new PropertyValueFactory<>("description"));
		duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
		price.setCellValueFactory(new PropertyValueFactory<>("price"));

		purposeTable.setItems(masterData);

	}

	@FXML
	void addPurpose() {
		addPurposeModel addPurpose = new addPurposeModel(nameField.getText(), descriptionField.getText(),
				durationField.getText(), Integer.parseInt(priceField.getText()));
		Request request = new Request();
		request.Post("/purpose/add?", token, addPurpose);
		new PurposesController();
		initialize();
	}
}
