package prs.controllers;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import prs.models.PatientTableViewModel;
import prs.util.file.Open;

public class PatientsController {
	private String token;
	@FXML
	private TextField filterField;
	@FXML
	private TableView<PatientTableViewModel> patientsTable;
	@FXML
	private TableColumn<PatientTableViewModel, String> name;
	@FXML
	private TableColumn<PatientTableViewModel, String> surname;
	@FXML
	private TableColumn<PatientTableViewModel, String> street;
	@FXML
	private TableColumn<PatientTableViewModel, String> postcode;
	@FXML
	private TableColumn<PatientTableViewModel, String> city;
	@FXML
	private TableColumn<PatientTableViewModel, String> phoneNumber;
	Request request = new Request();
	private ObservableList<PatientTableViewModel> masterData = FXCollections.observableArrayList();
	private ObservableList<PatientTableViewModel> filteredData = FXCollections.observableArrayList();
	public PatientsController() {
		try {
			token=Open.openFile();
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
		    Gson gson = new Gson();
		    PatientTableViewModel patient = gson.fromJson(json2, PatientTableViewModel.class);
		    //System.out.println(patient.get);
		    masterData.add(patient);
		}
		
        // Initially add all data to filtered data
        filteredData.addAll(masterData);

        // Listen for changes in master data.
        // Whenever the master data changes we must also update the filtered data.
        masterData.addListener(new ListChangeListener<PatientTableViewModel>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends PatientTableViewModel> change) {
                updateFilteredData();
            }
        });
    }
	public void setToken(String token) {
		this.token=token;
	}

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	System.out.println(token);
    	// Initialize the person table
    	
    	name.setCellFactory(TextFieldTableCell.forTableColumn());
    	name.setOnEditCommit(
    	    new EventHandler<CellEditEvent<PatientTableViewModel, String>>() {
    	        @Override
    	        public void handle(CellEditEvent<PatientTableViewModel, String> t) {
    	            ((PatientTableViewModel) t.getTableView().getItems().get(
    	                t.getTablePosition().getRow())
    	                ).setName(t.getNewValue());
    	            try {
    	    			token=Open.openFile();
    	    		} catch (IOException e) {
    	    			// TODO Auto-generated catch block
    	    			e.printStackTrace();
    	    		}
    	            request.Post("/patient/update?", token, (PatientTableViewModel)t.getTableView().getItems().get(t.getTablePosition().getRow()));
    	        }
    	    }
    	);
    	surname.setCellFactory(TextFieldTableCell.forTableColumn());
    	surname.setOnEditCommit(
    	    new EventHandler<CellEditEvent<PatientTableViewModel, String>>() {
    	        @Override
    	        public void handle(CellEditEvent<PatientTableViewModel, String> t) {
    	            ((PatientTableViewModel) t.getTableView().getItems().get(
    	                t.getTablePosition().getRow())
    	                ).setSurname(t.getNewValue());
    	            try {
    	    			token=Open.openFile();
    	    		} catch (IOException e) {
    	    			// TODO Auto-generated catch block
    	    			e.printStackTrace();
    	    		}
    	            request.Post("/patient/update?", token, (PatientTableViewModel)t.getTableView().getItems().get(t.getTablePosition().getRow()));
    	        }
    	    }
    	);
    	street.setCellFactory(TextFieldTableCell.forTableColumn());
    	street.setOnEditCommit(
    	    new EventHandler<CellEditEvent<PatientTableViewModel, String>>() {
    	        @Override
    	        public void handle(CellEditEvent<PatientTableViewModel, String> t) {
    	            ((PatientTableViewModel) t.getTableView().getItems().get(
    	                t.getTablePosition().getRow())
    	                ).setStreet(t.getNewValue());
    	            try {
    	    			token=Open.openFile();
    	    		} catch (IOException e) {
    	    			// TODO Auto-generated catch block
    	    			e.printStackTrace();
    	    		}
    	            request.Post("/patient/update?", token, (PatientTableViewModel)t.getTableView().getItems().get(t.getTablePosition().getRow()));
    	        }
    	    }
    	);
    	postcode.setCellFactory(TextFieldTableCell.forTableColumn());
    	postcode.setOnEditCommit(
    	    new EventHandler<CellEditEvent<PatientTableViewModel, String>>() {
    	        @Override
    	        public void handle(CellEditEvent<PatientTableViewModel, String> t) {
    	            ((PatientTableViewModel) t.getTableView().getItems().get(
    	                t.getTablePosition().getRow())
    	                ).setPostcode(t.getNewValue());
    	            try {
    	    			token=Open.openFile();
    	    		} catch (IOException e) {
    	    			// TODO Auto-generated catch block
    	    			e.printStackTrace();
    	    		}
    	            request.Post("/patient/update?", token, (PatientTableViewModel)t.getTableView().getItems().get(t.getTablePosition().getRow()));
    	        }
    	    }
    	);
    	city.setCellFactory(TextFieldTableCell.forTableColumn());
    	city.setOnEditCommit(
    	    new EventHandler<CellEditEvent<PatientTableViewModel, String>>() {
    	        @Override
    	        public void handle(CellEditEvent<PatientTableViewModel, String> t) {
    	            ((PatientTableViewModel) t.getTableView().getItems().get(
    	                t.getTablePosition().getRow())
    	                ).setCity(t.getNewValue());
    	            try {
    	    			token=Open.openFile();
    	    		} catch (IOException e) {
    	    			// TODO Auto-generated catch block
    	    			e.printStackTrace();
    	    		}
    	            request.Post("/patient/update?", token, (PatientTableViewModel)t.getTableView().getItems().get(t.getTablePosition().getRow()));
    	        }
    	    }
    	);
    	phoneNumber.setCellFactory(TextFieldTableCell.forTableColumn());
    	phoneNumber.setOnEditCommit(
    	    new EventHandler<CellEditEvent<PatientTableViewModel, String>>() {
    	        @Override
    	        public void handle(CellEditEvent<PatientTableViewModel, String> t) {
    	            ((PatientTableViewModel) t.getTableView().getItems().get(
    	                t.getTablePosition().getRow())
    	                ).setPhoneNumber(t.getNewValue());
    	            try {
    	    			token=Open.openFile();
    	    		} catch (IOException e) {
    	    			// TODO Auto-generated catch block
    	    			e.printStackTrace();
    	    		}
    	            request.Post("/patient/update?", token, (PatientTableViewModel)t.getTableView().getItems().get(t.getTablePosition().getRow()));
    	        }
    	    }
    	);
        name.setCellValueFactory(
                new PropertyValueFactory<PatientTableViewModel, String>("Name"));
        surname.setCellValueFactory(
                new PropertyValueFactory<PatientTableViewModel, String>("Surname"));
        street.setCellValueFactory(
                new PropertyValueFactory<PatientTableViewModel, String>("Street"));
        postcode.setCellValueFactory(
                new PropertyValueFactory<PatientTableViewModel, String>("Postcode"));
        city.setCellValueFactory(
                new PropertyValueFactory<PatientTableViewModel, String>("City"));
        phoneNumber.setCellValueFactory(
                new PropertyValueFactory<PatientTableViewModel, String>("PhoneNumber"));
        // Add filtered data to the table
        patientsTable.setItems(filteredData);

        // Listen for text changes in the filter text field
        filterField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {

                updateFilteredData();
            }
        });
    }

    /**
     * Updates the filteredData to contain all data from the masterData that
     * matches the current filter.
     */
    private void updateFilteredData() {
        filteredData.clear();

        for (PatientTableViewModel p : masterData) {
            if (matchesFilter(p)) {
                filteredData.add(p);
            }
        }

        // Must re-sort table after items changed
        reapplyTableSortOrder();
    }

    /**
     * Returns true if the person matches the current filter. Lower/Upper case
     * is ignored.
     * 
     * @param person
     * @return
     */
    private boolean matchesFilter(PatientTableViewModel patient) {
        String filterString = filterField.getText();
        if (filterString == null || filterString.isEmpty()) {
            // No filter --> Add all.
            return true;
        }

        String lowerCaseFilterString = filterString.toLowerCase();

        if (patient.getName().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        } else if (patient.getSurname().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        }

        return false; // Does not match
    }

    private void reapplyTableSortOrder() {
        ArrayList<TableColumn<PatientTableViewModel, ?>> sortOrder = new ArrayList<>(patientsTable.getSortOrder());
        patientsTable.getSortOrder().clear();
        patientsTable.getSortOrder().addAll(sortOrder);
    }
}
