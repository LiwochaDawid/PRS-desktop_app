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
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import prs.models.DoctorModel;
import prs.models.PatientTableViewModel;
import prs.util.file.Open;

public class SettingsController {
	private String token;
	@FXML
	private AnchorPane anchorPane;
	@FXML
	private TextField name;
	@FXML
	private TextField surname;
	@FXML
	private TextField prefix;
	@FXML
	private TextField street;
	@FXML
	private TextField postcode;
	@FXML
	private TextField city;
	@FXML
	private TextField phoneNumber;
	@FXML
	private Button saveBtn;
	@FXML
	private Button changeDataBtn;
	DoctorModel doctor;
	@FXML
	public void changeData() {
		name.setDisable(false);
		surname.setDisable(false);
		prefix.setDisable(false);
		street.setDisable(false);
		postcode.setDisable(false);
		city.setDisable(false);
		phoneNumber.setDisable(false);
		changeDataBtn.setVisible(false);
		saveBtn.setVisible(true);
		name.setStyle("-fx-border-color:grey");
		surname.setStyle("-fx-border-color:grey");
		prefix.setStyle("-fx-border-color:grey");
		street.setStyle("-fx-border-color:grey");
		postcode.setStyle("-fx-border-color:grey");
		city.setStyle("-fx-border-color:grey");
		phoneNumber.setStyle("-fx-border-color:grey");
	};
	@FXML
	public void save() {
		name.setDisable(true);
		surname.setDisable(true);
		prefix.setDisable(true);
		street.setDisable(true);
		postcode.setDisable(true);
		city.setDisable(true);
		phoneNumber.setDisable(true);
		changeDataBtn.setVisible(true);
		saveBtn.setVisible(false);
		doctor.setName(name.getText());
		
		Request request = new Request();
		request.Post("/doctor/update?", token, doctor);
		new SettingsController();
		initialize();
		
	}
	public SettingsController() {
		try {
			token=Open.openFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Request request = new Request();
		String response=request.Get("/doctor/this?", token);
		JsonElement json = new JsonParser().parse(response);    
		Gson gson = new Gson();
		doctor = gson.fromJson(json, DoctorModel.class);	
    }
	public void setToken(String token) {
		this.token=token;
	}
	@FXML
	public void loadPurposes() {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/PurposeLayout.fxml"));
		AnchorPane pane = null;
		try {
			pane = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		BorderPane bp=(BorderPane) anchorPane.getParent();
		bp.setCenter(pane);
		
		
	}
    @FXML
    private void initialize() {
    	name.setText(doctor.getName());
    	surname.setText(doctor.getSurname());
    	prefix.setText(doctor.getPrefix());
    	street.setText(doctor.getStreet());
    	postcode.setText(doctor.getPostcode());
    	city.setText(doctor.getCity());
    	phoneNumber.setText(doctor.getPhoneNumber());
    }
    	
}
