package prs.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
import java.util.logging.Level;
import java.util.logging.Logger;

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
	void keySignIn(KeyEvent key) {
		if(key.getCode()==KeyCode.ENTER) {
			signIn();
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
		stage.close();
		Stage newStage =new Stage();
		newStage.initStyle(StageStyle.DECORATED);
                FXMLLoader stageLoader = new FXMLLoader(this.getClass().getResource("/fxml/MainLandingLayout.fxml"));
                BorderPane rootLayout=new BorderPane();
            try {
                rootLayout = (BorderPane) stageLoader.load();
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }

                Scene scene = new Scene(rootLayout);
                newStage.setScene(scene);    
                Screen screen = Screen.getPrimary();
        		Rectangle2D bounds = screen.getVisualBounds();
        		newStage.setX(bounds.getMinX());
        		newStage.setY(bounds.getMinY());
        		newStage.setWidth(bounds.getWidth());
        		newStage.setHeight(bounds.getHeight());
        		newStage.setTitle("Patient registration system");
        		newStage.getIcons().add(new Image("/images/logo.png"));
                newStage.show();
	}
}
