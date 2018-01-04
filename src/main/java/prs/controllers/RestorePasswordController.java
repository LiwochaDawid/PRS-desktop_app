package prs.controllers;

import javafx.fxml.FXML;

public class RestorePasswordController implements ILogin {
	private MainController mainController;

	@Override
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
