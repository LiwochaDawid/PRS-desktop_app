package prs.controllers;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import org.apache.http.ParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
	private TextField filterField;
	private ObservableList<VisitModelTable> masterData = FXCollections.observableArrayList();
	Request request = new Request();
	  private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	    private static final String TIME_FORMAT = "HH:mm:ss";

	    private class DateDeserializer implements JsonDeserializer<Date> {

	        @Override
	        public Date deserialize(JsonElement jsonElement, Type typeOF,
	                                JsonDeserializationContext context) throws JsonParseException {
	                try {
	                    return new SimpleDateFormat(DATE_FORMAT, Locale.US).parse(jsonElement.getAsString());
	                } catch (java.text.ParseException e) {
	                }

	            throw new JsonParseException("Unparseable date: \"" + jsonElement.getAsString()
	                    + "\". Supported formats: " + DATE_FORMAT);
	        }
	    }

	    private class TimeDeserializer implements JsonDeserializer<Time> {
	    	@Override
	        public Time deserialize(JsonElement jsonElement, Type typeOF, JsonDeserializationContext context) throws JsonParseException {
	                try {

	                    String s = jsonElement.getAsString();
	                    SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT, Locale.US);
	                    long ms = 0;
	                    try {
							sdf.parse(s);
							ms= sdf.parse(s).getTime();
						} catch (java.text.ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}  
	                    Time t = new Time(ms);
	                    return t;
	                } catch (ParseException e) {
	                }
	            throw new JsonParseException("Unparseable time: \"" + jsonElement.getAsString()
	                    + "\". Supported formats: " + TIME_FORMAT);
	        }

	    }

	public VisitController() {
		
		GsonBuilder gSonBuilder=  new GsonBuilder();
	    gSonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
	    gSonBuilder.registerTypeAdapter(Time.class, new TimeDeserializer());
	    gSonBuilder.registerTypeAdapter(Date.class,new MyDateTypeAdapter()).create();
	    Gson gson = gSonBuilder.create();
		
		try {
			token = Open.openFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Request request = new Request();
		String response = request.Get("/visit/thisDoctor?", token);
		JsonElement json = new JsonParser().parse(response);
		JsonArray array = json.getAsJsonArray();
		Iterator iterator = array.iterator();
		while (iterator.hasNext()) {
			JsonElement json2 = (JsonElement) iterator.next();
			// System.out.println(gson.fromJson(json2, PurposeModel.class).getName());
			VisitModel visit = gson.fromJson(json2, VisitModel.class);
			VisitModelTable visit2= new VisitModelTable(visit.getDate(), visit.getPatient().getName(),visit.getPatient().getSurname(),
					visit.getPurpose().getName());
			/*
			 * if(purpose.getName()!=null) System.out.println(purpose.getDuration()); else
			 * System.out.println("null");
			 */
			
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
		purpose.setMaxWidth(1f * Integer.MAX_VALUE * 30); // 70% width
		// ***************************************************************

		date.setCellValueFactory(new PropertyValueFactory<>("date"));
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
		purpose.setCellValueFactory(new PropertyValueFactory<>("purpose"));

		visitTable.setItems(masterData);
	}

	// old conception
	/*
	 * date.setCellFactory(TextFieldTableCell.forTableColumn());
	 * date.setOnEditCommit( new EventHandler<CellEditEvent<VisitModel, String>>() {
	 * 
	 * @Override public void handle(CellEditEvent<VisitModel, String> t) {
	 * ((VisitModel) t.getTableView().getItems().get( t.getTablePosition().getRow())
	 * ).setDate(t.getNewValue()); try { token=Open.openFile(); } catch (IOException
	 * e) { // TODO Auto-generated catch block e.printStackTrace(); }
	 * request.Post("/patient/update?", token,
	 * (VisitTableViewModel)t.getTableView().getItems().get(t.getTablePosition().
	 * getRow())); } } );
	 * 
	 * name.setCellFactory(TextFieldTableCell.forTableColumn());
	 * name.setOnEditCommit( new EventHandler<CellEditEvent<VisitTableViewModel,
	 * String>>() {
	 * 
	 * @Override public void handle(CellEditEvent<VisitTableViewModel, String> t) {
	 * ((VisitTableViewModel) t.getTableView().getItems().get(
	 * t.getTablePosition().getRow()) ).setName(t.getNewValue()); try {
	 * token=Open.openFile(); } catch (IOException e) { // TODO Auto-generated catch
	 * block e.printStackTrace(); } request.Post("/patient/update?", token,
	 * (VisitTableViewModel)t.getTableView().getItems().get(t.getTablePosition().
	 * getRow())); } } );
	 * 
	 * surname.setCellFactory(TextFieldTableCell.forTableColumn());
	 * surname.setOnEditCommit( new EventHandler<CellEditEvent<VisitTableViewModel,
	 * String>>() {
	 * 
	 * @Override public void handle(CellEditEvent<VisitTableViewModel, String> t) {
	 * ((VisitTableViewModel) t.getTableView().getItems().get(
	 * t.getTablePosition().getRow()) ).setSurname(t.getNewValue()); try {
	 * token=Open.openFile(); } catch (IOException e) { // TODO Auto-generated catch
	 * block e.printStackTrace(); } request.Post("/patient/update?", token,
	 * (VisitTableViewModel)t.getTableView().getItems().get(t.getTablePosition().
	 * getRow())); } } );
	 * 
	 * purpose.setCellFactory(TextFieldTableCell.forTableColumn());
	 * purpose.setOnEditCommit( new EventHandler<CellEditEvent<VisitTableViewModel,
	 * String>>() {
	 * 
	 * @Override public void handle(CellEditEvent<VisitTableViewModel, String> t) {
	 * ((VisitTableViewModel) t.getTableView().getItems().get(
	 * t.getTablePosition().getRow()) ).setPurpose(t.getNewValue());
	 * 
	 * } } ); date.setCellValueFactory( new
	 * PropertyValueFactory<VisitTableViewModel, String>("Date"));
	 * name.setCellValueFactory( new PropertyValueFactory<VisitTableViewModel,
	 * String>("Name")); surname.setCellValueFactory( new
	 * PropertyValueFactory<VisitTableViewModel, String>("Surname"));
	 * purpose.setCellValueFactory( new PropertyValueFactory<VisitTableViewModel,
	 * String>("Purpose")); // Add filtered data to the table
	 * visitTable.setItems(filteredData);
	 * 
	 * // Listen for text changes in the filter text field
	 * filterField.textProperty().addListener(new ChangeListener<String>() {
	 * 
	 * @Override public void changed(ObservableValue<? extends String> observable,
	 * String oldValue, String newValue) {
	 * 
	 * updateFilteredData(); } });
	 * 
	 * }
	 * 
	 * 
	 * private ObservableList<VisitTableViewModel> masterData =
	 * FXCollections.observableArrayList(); private
	 * ObservableList<VisitTableViewModel> filteredData =
	 * FXCollections.observableArrayList();
	 * 
	 * public VisitController() { try { token=Open.openFile(); } catch (IOException
	 * e) { // TODO Auto-generated catch block e.printStackTrace(); } // Add some
	 * sample data to the master data masterData.add(new
	 * VisitTableViewModel("20.01.2018", "Jan", "Kowalski", "Tooth"));
	 * masterData.add(new VisitTableViewModel("20.01.2018", "Janek", "Kowal",
	 * "Tooth")); masterData.add(new VisitTableViewModel("21.01.2018", "Jan",
	 * "Tuwim", "Tooth")); masterData.add(new VisitTableViewModel("23.01.2018",
	 * "Tom", "Jerry", "Tooth")); masterData.add(new
	 * VisitTableViewModel("25.01.2018", "Ola", "Kot", "Tooth")); masterData.add(new
	 * VisitTableViewModel("27.01.2018", "Sebastian", "Okoñ", "Tooth"));
	 * masterData.add(new VisitTableViewModel("29.01.2018", "Kamil", "Wabik",
	 * "Tooth")); masterData.add(new VisitTableViewModel("02.02.2018", "Robert",
	 * "Kubica", "Tooth")); masterData.add(new VisitTableViewModel("14.02.2018",
	 * "Darek", "Trudny", "Tooth")); masterData.add(new
	 * VisitTableViewModel("16.02.2018", "Bartek", "Chudy", "Tooth"));
	 * 
	 * 
	 * // Initially add all data to filtered data filteredData.addAll(masterData);
	 * 
	 * // Listen for changes in master data. // Whenever the master data changes we
	 * must also update the filtered data. masterData.addListener(new
	 * ListChangeListener<VisitTableViewModel>() {
	 * 
	 * @Override public void onChanged(ListChangeListener.Change<? extends
	 * VisitTableViewModel> change) { updateFilteredData(); } });
	 */
	/*
	 * 
	 * }
	 * 
	 * 
	 * /** Updates the filteredData to contain all data from the masterData that
	 * matches the current filter.
	 */
	/*
	 * private void updateFilteredData() { filteredData.clear();
	 * 
	 * for (VisitTableViewModel v : masterData) { if (matchesFilter(v)) {
	 * filteredData.add(v); } } // Must re-sort table after items changed
	 * reapplyTableSortOrder(); }
	 * 
	 * /** Returns true if the person matches the current filter. Lower/Upper case
	 * is ignored.
	 * 
	 * @param person
	 * 
	 * @return
	 */
	/*
	 * private boolean matchesFilter(VisitModel visit) { String filterString =
	 * filterField.getText(); if (filterString == null || filterString.isEmpty()) {
	 * // No filter --> Add all. return true; }
	 * 
	 * String lowerCaseFilterString = filterString.toLowerCase();
	 * 
	 * if (visit.getName().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
	 * return true; } else if
	 * (visit.getSurname().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
	 * return true; }
	 * 
	 * return false; // Does not match }
	 * 
	 * private void reapplyTableSortOrder() {
	 * ArrayList<TableColumn<VisitTableViewModel, ?>> sortOrder = new
	 * ArrayList<>(visitTable.getSortOrder()); visitTable.getSortOrder().clear();
	 * visitTable.getSortOrder().addAll(sortOrder); }
	 */
	// end old conception ***************************************************

}
