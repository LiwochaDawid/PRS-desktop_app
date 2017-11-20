package prs.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class LoginController {
	private MainController mainController;
	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

	@FXML
	void signIn() {
		System.out.println("Hello world");
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
		RestorePasswordController restorePasswordController = loader.getController();
		restorePasswordController.setMainController(mainController);
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
		SignUpController signUpController = loader.getController();
		signUpController.setMainController(mainController);
		mainController.setMainScreen(pane);
	}

}
