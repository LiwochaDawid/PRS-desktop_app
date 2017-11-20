package prs.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class RestorePasswordController {
	private MainController mainController;
	public void setMainController(MainController mainController) {
		this.mainController=mainController;
	}
	
	@FXML
	void restorePassword() {
		mainController.showLoginLayout();
		System.out.println("Email with new pass sended");
		
	}
	@FXML
	void backToLogin() {
		mainController.showLoginLayout();
	}
}
