package prs.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import prs.main.Main;

import com.google.gson.Gson;
import com.sun.javafx.tk.Toolkit;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;

public class LoginController implements ILogin {
	private MainController mainController;
	private ILogin loginInterface;
	private boolean correctEmail = false;
	private boolean correctPassword = false;
	private String token;
	@FXML
	private TextField email;
	@FXML
	private PasswordField password;
	@FXML
	private Button signinButton;
	@FXML
	private Pane loginPane;

	@FXML
	void onChangeEmail() {
		if (email.getText().contains("@") && email.getText().length() > 5) {
			correctEmail = true;
		} else {
			correctEmail = false;
		}
		if (correctEmail && correctPassword) {
			signinButton.setDisable(false);
		} else {
			signinButton.setDisable(true);
		}
	}

	@FXML
	void onChangePassword() {
		/*
		 * if (password.getText().length() >= 8) { correctPassword = true; } else {
		 * correctPassword = false; } if (correctEmail && correctPassword) {
		 * signinButton.setDisable(false); } else { signinButton.setDisable(true); }
		 */
		signinButton.setDisable(false);
	}

	@FXML
	void signIn() {
		sendDataToServer(email.getText(), password.getText());

	}

	@FXML
	void restorePassword() {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/RestorePasswordLayout.fxml"));
		Pane pane = null;
		try {
			pane = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		loginInterface = loader.getController();
		loginInterface.setMainController(mainController);
		mainController.setMainScreen(pane);
	}

	@FXML
	void signUp() {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/SignUpLayout.fxml"));
		Pane pane = null;
		try {
			pane = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		loginInterface = loader.getController();
		loginInterface.setMainController(mainController);
		mainController.setMainScreen(pane);
	}

	@Override
	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

	public void sendDataToServer(String username, String password) {
		Request request = new Request();
		token = request.getToken(username, password);
		if (token == null) {
			System.out.println("heelo null");
		} else {
			showLandingLayout();
		}

	}

	private void showLandingLayout() {
		Stage stage=(Stage)loginPane.getScene().getWindow();
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		stage.setX(bounds.getMinX());
		stage.setY(bounds.getMinY());
		stage.setWidth(bounds.getWidth());
		stage.setHeight(bounds.getHeight());
		
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/LandingLayout.fxml"));
		Pane pane = null;
		try {
			pane = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		pane.setMaxWidth(bounds.getWidth()/1.5);
		loginInterface = loader.getController();
		loginInterface.setMainController(mainController);
		mainController.setMainScreen(pane);
		
		
		
		
	}
}
