package prs.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class LoginController implements ILogin {
	private MainController mainController;
	ILogin loginInterface;

	@FXML
	void signIn() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("PRS");
		alert.setHeaderText(null);
		alert.setContentText("W tym momencie pojawia siê aplikacja dla lekarza");
		alert.showAndWait();
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
		// InternalLayoutsInterface signUpInter
		// SignUpController signUpController = loader.getController();
		// signUpController.setMainController(mainController);
		loginInterface = loader.getController();
		loginInterface.setMainController(mainController);
		mainController.setMainScreen(pane);
	}

	@Override
	public void setMainController(MainController mainController) {
		// TODO Auto-generated method stub
		this.mainController = mainController;
	}

}
