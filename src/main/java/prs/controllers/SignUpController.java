package prs.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import prs.models.AccountModel;
import prs.models.SignUpModel;
import javafx.scene.control.Alert.AlertType;

public class SignUpController implements ILogin {

	private MainController mainController;
	private boolean correctEmail = false;
	private boolean confirmedEmail = false;
	private boolean correctPassword = false;
	private boolean confirmedPassword = false;
	SignUpModel doctor;
	AccountModel account;
	Request request;
	@FXML
	private TextField email;
	@FXML
	private TextField confirmEmail;
	@FXML
	private PasswordField password;
	@FXML
	private PasswordField confirmPassword;
	@FXML
	private TextField name;
	@FXML
	private TextField surname;
	@FXML
	private TextField prefix;
	@FXML
	private TextField street;
	@FXML
	private TextField postCode;
	@FXML
	private TextField city;
	@FXML
	private TextField country;
	@FXML
	private TextField phoneNumber;

	@FXML
	void initialize() {
		onChangeEmail();
		onChangeConfirmEmail();
		onChangePassword();
		onChangeConfirmPassword();
		request=new Request();
	}

	@FXML
	void signUpButton() {
		initialize();
		if (correctEmail && confirmedEmail && correctPassword && confirmedPassword && name.getText() != null && surname.getText()!=null
		&& prefix.getText() != null && street.getText() != null && postCode.getText() != null && city.getText() != null) {
			doctor=new SignUpModel(name.getText(), surname.getText(), prefix.getText(), street.getText(), postCode.getText(), city.getText(),
			country.getText(), phoneNumber.getText());
			account=new AccountModel(email.getText(), password.getText());
			request.createDoctorAccount("/account/sign_up/doctor", account, doctor);
			mainController.showLoginLayout();
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("PRS-sign up error");
			alert.setHeaderText(null);
			alert.setContentText(
					"-Email must contanint @\n-Password must be 8 characters or more\n-You must fill every field");
			alert.showAndWait();
		}
	}

	@FXML
	void onChangeEmail() {
		if (email.getText().contains("@") && email.getText().length() > 5) {
			correctEmail = true;
		} else {
			correctEmail = false;
		}
	}

	@FXML
	void onChangeConfirmEmail() {
		if (confirmEmail.getText().equals(email.getText()) && correctEmail) {
			confirmedEmail = true;
		} else
			confirmedEmail = false;
	}

	@FXML
	void onChangePassword() {
		if (password.getText().length() >= 7)
			correctPassword = true;
	}

	@FXML
	void onChangeConfirmPassword() {
		if (confirmPassword.getText().equals(password.getText()) && correctPassword)
			confirmedPassword = true;
		else
			confirmedPassword = false;
	}

	@FXML
	void backToLogin() {
		mainController.showLoginLayout();
	}

	@Override
	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

	
	public void sendDataToServer() {
		// TODO Auto-generated method stub
		
	}

	public void getDataFromServer() {
		// TODO Auto-generated method stub
		
	}

}
