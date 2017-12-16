package prs.controllers;


import java.util.ArrayList;

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

public class PatientsController {
	@FXML
	private TextField filterField;
	@FXML
	private TableView<PatientTableViewModel> patientsTable;
	@FXML
	private TableColumn<PatientTableViewModel, String> name;
	@FXML
	private TableColumn<PatientTableViewModel, String> surname;
	@FXML
	private TableColumn<PatientTableViewModel, String> email;
	@FXML
	private TableColumn<PatientTableViewModel, String> phoneNumber;
	
	private ObservableList<PatientTableViewModel> masterData = FXCollections.observableArrayList();
	private ObservableList<PatientTableViewModel> filteredData = FXCollections.observableArrayList();
	
	public PatientsController() {
		
		
        // Add some sample data to the master data
        masterData.add(new PatientTableViewModel("Hans", "Muster", "hans.m@gmail.com", "731242154"));
        masterData.add(new PatientTableViewModel("Ola", "Ferr", "olka.fair@gmail.com", "654521154"));
        masterData.add(new PatientTableViewModel("Jan", "Kowalski", "j.kowalski@wp.pl", "500100100"));
        masterData.add(new PatientTableViewModel("Adam", "Kalaf", "adam.k31@gmail.com", "664554551"));
        masterData.add(new PatientTableViewModel("Sebastian1", "Parny1", "seba1.par@onet.pl", "700145542"));
        masterData.add(new PatientTableViewModel("Hans1", "Muster1", "hans1.m@gmail.com", "731242154"));
        masterData.add(new PatientTableViewModel("Ola1", "Ferr1", "olka1.fair@gmail.com", "654521154"));
        masterData.add(new PatientTableViewModel("Jan1", "Kowalski1", "j1.kowalski@wp.pl", "500100100"));
        masterData.add(new PatientTableViewModel("Adam1", "Kalaf1", "adam1.k31@gmail.com", "664554551"));
        masterData.add(new PatientTableViewModel("Sebastian1", "Parny1", "seba1.par@onet.pl", "700145542"));
        masterData.add(new PatientTableViewModel("Ola2", "Ferr2", "olka2.fair@gmail.com", "654521154"));
        masterData.add(new PatientTableViewModel("Jan2", "Kowalski2", "j2.kowalski@wp.pl", "500100100"));
        masterData.add(new PatientTableViewModel("Adam2", "Kalaf2", "adam2.k31@gmail.com", "664554551"));
        masterData.add(new PatientTableViewModel("Sebastian2", "Parny2", "seba2.par@onet.pl", "700145542"));        
        masterData.add(new PatientTableViewModel("Hans", "Muster", "hans.m@gmail.com", "731242154"));
        masterData.add(new PatientTableViewModel("Ola", "Ferr", "olka.fair@gmail.com", "654521154"));
        masterData.add(new PatientTableViewModel("Jan", "Kowalski", "j.kowalski@wp.pl", "500100100"));
        masterData.add(new PatientTableViewModel("Adam", "Kalaf", "adam.k31@gmail.com", "664554551"));
        masterData.add(new PatientTableViewModel("Sebastian1", "Parny1", "seba1.par@onet.pl", "700145542"));
        masterData.add(new PatientTableViewModel("Hans1", "Muster1", "hans1.m@gmail.com", "731242154"));
        masterData.add(new PatientTableViewModel("Ola1", "Ferr1", "olka1.fair@gmail.com", "654521154"));
        masterData.add(new PatientTableViewModel("Jan1", "Kowalski1", "j1.kowalski@wp.pl", "500100100"));
        masterData.add(new PatientTableViewModel("Adam1", "Kalaf1", "adam1.k31@gmail.com", "664554551"));
        masterData.add(new PatientTableViewModel("Sebastian1", "Parny1", "seba1.par@onet.pl", "700145542"));
        masterData.add(new PatientTableViewModel("Ola2", "Ferr2", "olka2.fair@gmail.com", "654521154"));
        masterData.add(new PatientTableViewModel("Jan2", "Kowalski2", "j2.kowalski@wp.pl", "500100100"));
        masterData.add(new PatientTableViewModel("Adam2", "Kalaf2", "adam2.k31@gmail.com", "664554551"));
        masterData.add(new PatientTableViewModel("Sebastian2", "Parny2", "seba2.par@onet.pl", "700145542"));        
        masterData.add(new PatientTableViewModel("Hans", "Muster", "hans.m@gmail.com", "731242154"));
        masterData.add(new PatientTableViewModel("Ola", "Ferr", "olka.fair@gmail.com", "654521154"));
        masterData.add(new PatientTableViewModel("Jan", "Kowalski", "j.kowalski@wp.pl", "500100100"));
        masterData.add(new PatientTableViewModel("Adam", "Kalaf", "adam.k31@gmail.com", "664554551"));
        masterData.add(new PatientTableViewModel("Sebastian1", "Parny1", "seba1.par@onet.pl", "700145542"));
        masterData.add(new PatientTableViewModel("Hans1", "Muster1", "hans1.m@gmail.com", "731242154"));
        masterData.add(new PatientTableViewModel("Ola1", "Ferr1", "olka1.fair@gmail.com", "654521154"));
        masterData.add(new PatientTableViewModel("Jan1", "Kowalski1", "j1.kowalski@wp.pl", "500100100"));
        masterData.add(new PatientTableViewModel("Adam1", "Kalaf1", "adam1.k31@gmail.com", "664554551"));
        masterData.add(new PatientTableViewModel("Sebastian1", "Parny1", "seba1.par@onet.pl", "700145542"));
        masterData.add(new PatientTableViewModel("Ola2", "Ferr2", "olka2.fair@gmail.com", "654521154"));
        masterData.add(new PatientTableViewModel("Jan2", "Kowalski2", "j2.kowalski@wp.pl", "500100100"));
        masterData.add(new PatientTableViewModel("Adam2", "Kalaf2", "adam2.k31@gmail.com", "664554551"));
        masterData.add(new PatientTableViewModel("Sebastian2", "Parny2", "seba2.par@onet.pl", "700145542"));        
        masterData.add(new PatientTableViewModel("Hans", "Muster", "hans.m@gmail.com", "731242154"));
        masterData.add(new PatientTableViewModel("Ola", "Ferr", "olka.fair@gmail.com", "654521154"));
        masterData.add(new PatientTableViewModel("Jan", "Kowalski", "j.kowalski@wp.pl", "500100100"));
        masterData.add(new PatientTableViewModel("Adam", "Kalaf", "adam.k31@gmail.com", "664554551"));
        masterData.add(new PatientTableViewModel("Sebastian1", "Parny1", "seba1.par@onet.pl", "700145542"));
        masterData.add(new PatientTableViewModel("Hans1", "Muster1", "hans1.m@gmail.com", "731242154"));
        masterData.add(new PatientTableViewModel("Ola1", "Ferr1", "olka1.fair@gmail.com", "654521154"));
        masterData.add(new PatientTableViewModel("Jan1", "Kowalski1", "j1.kowalski@wp.pl", "500100100"));
        masterData.add(new PatientTableViewModel("Adam1", "Kalaf1", "adam1.k31@gmail.com", "664554551"));
        masterData.add(new PatientTableViewModel("Sebastian1", "Parny1", "seba1.par@onet.pl", "700145542"));
        masterData.add(new PatientTableViewModel("Ola2", "Ferr2", "olka2.fair@gmail.com", "654521154"));
        masterData.add(new PatientTableViewModel("Jan2", "Kowalski2", "j2.kowalski@wp.pl", "500100100"));
        masterData.add(new PatientTableViewModel("Adam2", "Kalaf2", "adam2.k31@gmail.com", "664554551"));
        masterData.add(new PatientTableViewModel("Sebastian2", "Parny2", "seba2.par@onet.pl", "700145542"));        
        masterData.add(new PatientTableViewModel("Hans", "Muster", "hans.m@gmail.com", "731242154"));
        masterData.add(new PatientTableViewModel("Ola", "Ferr", "olka.fair@gmail.com", "654521154"));
        masterData.add(new PatientTableViewModel("Jan", "Kowalski", "j.kowalski@wp.pl", "500100100"));
        masterData.add(new PatientTableViewModel("Adam", "Kalaf", "adam.k31@gmail.com", "664554551"));
        masterData.add(new PatientTableViewModel("Sebastian1", "Parny1", "seba1.par@onet.pl", "700145542"));
        masterData.add(new PatientTableViewModel("Hans1", "Muster1", "hans1.m@gmail.com", "731242154"));
        masterData.add(new PatientTableViewModel("Ola1", "Ferr1", "olka1.fair@gmail.com", "654521154"));
        masterData.add(new PatientTableViewModel("Jan1", "Kowalski1", "j1.kowalski@wp.pl", "500100100"));
        masterData.add(new PatientTableViewModel("Adam1", "Kalaf1", "adam1.k31@gmail.com", "664554551"));
        masterData.add(new PatientTableViewModel("Sebastian1", "Parny1", "seba1.par@onet.pl", "700145542"));
        masterData.add(new PatientTableViewModel("Ola2", "Ferr2", "olka2.fair@gmail.com", "654521154"));
        masterData.add(new PatientTableViewModel("Jan2", "Kowalski2", "j2.kowalski@wp.pl", "500100100"));
        masterData.add(new PatientTableViewModel("Adam2", "Kalaf2", "adam2.k31@gmail.com", "664554551"));
        masterData.add(new PatientTableViewModel("Sebastian2", "Parny2", "seba2.par@onet.pl", "700145542"));        
        
        
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

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	// Initialize the person table
    	
    	name.setCellFactory(TextFieldTableCell.forTableColumn());
    	name.setOnEditCommit(
    	    new EventHandler<CellEditEvent<PatientTableViewModel, String>>() {
    	        @Override
    	        public void handle(CellEditEvent<PatientTableViewModel, String> t) {
    	            ((PatientTableViewModel) t.getTableView().getItems().get(
    	                t.getTablePosition().getRow())
    	                ).setName(t.getNewValue());
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
    	        }
    	    }
    	);
    	email.setCellFactory(TextFieldTableCell.forTableColumn());
    	email.setOnEditCommit(
    	    new EventHandler<CellEditEvent<PatientTableViewModel, String>>() {
    	        @Override
    	        public void handle(CellEditEvent<PatientTableViewModel, String> t) {
    	            ((PatientTableViewModel) t.getTableView().getItems().get(
    	                t.getTablePosition().getRow())
    	                ).setEmail(t.getNewValue());
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
    	        }
    	    }
    	);
        name.setCellValueFactory(
                new PropertyValueFactory<PatientTableViewModel, String>("Name"));
        surname.setCellValueFactory(
                new PropertyValueFactory<PatientTableViewModel, String>("Surname"));
        email.setCellValueFactory(
                new PropertyValueFactory<PatientTableViewModel, String>("Email"));
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
