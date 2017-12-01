package prs.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class RestorePasswordController implements ILogin {
	private MainController mainController;

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
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


	public void sendDataToServer() {
		// TODO Auto-generated method stub
		
	}


	public void getDataFromServer() {
		// TODO Auto-generated method stub
		
	}
}
