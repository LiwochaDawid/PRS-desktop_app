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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import prs.models.PatientTableViewModel;
import prs.models.VisitTableViewModel;

public class VisitController {

	@FXML
	private TableView<VisitTableViewModel> visitTable;
	@FXML
	private TableColumn<VisitTableViewModel, String> date;
	@FXML
	private TableColumn<VisitTableViewModel, String> name;
	@FXML
	private TableColumn<VisitTableViewModel, String> surname;
	@FXML
	private TableColumn<VisitTableViewModel, String> purpose;
	@FXML
	private TextField filterField;
	
	
	@FXML
	void initialize() {
		visitTable.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
		date.setMaxWidth( 1f * Integer.MAX_VALUE * 15 ); // 10% width
		name.setMaxWidth( 1f * Integer.MAX_VALUE * 15 ); // 10% width
		surname.setMaxWidth( 1f * Integer.MAX_VALUE * 15 ); // 10% width
		purpose.setMaxWidth( 1f * Integer.MAX_VALUE * 55 ); // 70% width
		
		
		date.setCellFactory(TextFieldTableCell.forTableColumn());
    	date.setOnEditCommit(
    	    new EventHandler<CellEditEvent<VisitTableViewModel, String>>() {
    	        @Override
    	        public void handle(CellEditEvent<VisitTableViewModel, String> t) {
    	            ((VisitTableViewModel) t.getTableView().getItems().get(
    	                t.getTablePosition().getRow())
    	                ).setDate(t.getNewValue());
    	        }
    	    }
    	);
		
		name.setCellFactory(TextFieldTableCell.forTableColumn());
    	name.setOnEditCommit(
    	    new EventHandler<CellEditEvent<VisitTableViewModel, String>>() {
    	        @Override
    	        public void handle(CellEditEvent<VisitTableViewModel, String> t) {
    	            ((VisitTableViewModel) t.getTableView().getItems().get(
    	                t.getTablePosition().getRow())
    	                ).setName(t.getNewValue());
    	        }
    	    }
    	);
    	
    	surname.setCellFactory(TextFieldTableCell.forTableColumn());
    	surname.setOnEditCommit(
    	    new EventHandler<CellEditEvent<VisitTableViewModel, String>>() {
    	        @Override
    	        public void handle(CellEditEvent<VisitTableViewModel, String> t) {
    	            ((VisitTableViewModel) t.getTableView().getItems().get(
    	                t.getTablePosition().getRow())
    	                ).setSurname(t.getNewValue());
    	        }
    	    }
    	);
    	
    	purpose.setCellFactory(TextFieldTableCell.forTableColumn());
    	purpose.setOnEditCommit(
    	    new EventHandler<CellEditEvent<VisitTableViewModel, String>>() {
    	        @Override
    	        public void handle(CellEditEvent<VisitTableViewModel, String> t) {
    	            ((VisitTableViewModel) t.getTableView().getItems().get(
    	                t.getTablePosition().getRow())
    	                ).setPurpose(t.getNewValue());
    	        }
    	    }
    	);
    	date.setCellValueFactory(
                new PropertyValueFactory<VisitTableViewModel, String>("Date"));
        name.setCellValueFactory(
                new PropertyValueFactory<VisitTableViewModel, String>("Name"));
        surname.setCellValueFactory(
                new PropertyValueFactory<VisitTableViewModel, String>("Surname"));
        purpose.setCellValueFactory(
                new PropertyValueFactory<VisitTableViewModel, String>("Purpose"));
        // Add filtered data to the table
        visitTable.setItems(filteredData);

        // Listen for text changes in the filter text field
        filterField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {

                updateFilteredData();
            }
        });

	}
	
	
	private ObservableList<VisitTableViewModel> masterData = FXCollections.observableArrayList();
	private ObservableList<VisitTableViewModel> filteredData = FXCollections.observableArrayList();
	
	public VisitController() {
		
		
        // Add some sample data to the master data
        masterData.add(new VisitTableViewModel("20.01.2018", "Jan", "Kowalski", "Tooth"));
        masterData.add(new VisitTableViewModel("20.01.2018", "Janek", "Kowal", "Tooth"));
        masterData.add(new VisitTableViewModel("21.01.2018", "Jan", "Tuwim", "Tooth"));
        masterData.add(new VisitTableViewModel("23.01.2018", "Tom", "Jerry", "Tooth"));
        masterData.add(new VisitTableViewModel("25.01.2018", "Ola", "Kot", "Tooth"));
        masterData.add(new VisitTableViewModel("27.01.2018", "Sebastian", "Okoñ", "Tooth"));
        masterData.add(new VisitTableViewModel("29.01.2018", "Kamil", "Wabik", "Tooth"));
        masterData.add(new VisitTableViewModel("02.02.2018", "Robert", "Kubica", "Tooth"));
        masterData.add(new VisitTableViewModel("14.02.2018", "Darek", "Trudny", "Tooth"));
        masterData.add(new VisitTableViewModel("16.02.2018", "Bartek", "Chudy", "Tooth"));
     
        
        // Initially add all data to filtered data
        filteredData.addAll(masterData);

        // Listen for changes in master data.
        // Whenever the master data changes we must also update the filtered data.
        masterData.addListener(new ListChangeListener<VisitTableViewModel>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends VisitTableViewModel> change) {
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

        for (VisitTableViewModel v : masterData) {
            if (matchesFilter(v)) {
                filteredData.add(v);
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
    private boolean matchesFilter(VisitTableViewModel visit) {
        String filterString = filterField.getText();
        if (filterString == null || filterString.isEmpty()) {
            // No filter --> Add all.
            return true;
        }

        String lowerCaseFilterString = filterString.toLowerCase();

        if (visit.getName().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        } else if (visit.getSurname().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        }

        return false; // Does not match
    }

    private void reapplyTableSortOrder() {
        ArrayList<TableColumn<VisitTableViewModel, ?>> sortOrder = new ArrayList<>(visitTable.getSortOrder());
        visitTable.getSortOrder().clear();
        visitTable.getSortOrder().addAll(sortOrder);
    }

}
