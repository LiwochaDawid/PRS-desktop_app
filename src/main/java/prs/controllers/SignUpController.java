package prs.controllers;

import javafx.fxml.FXML;

public class SignUpController {
	private MainController mainController;
	public void setMainController(MainController mainController) {
		this.mainController=mainController;
	}
	
	@FXML
	 void signUp() {
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!UFO PORNO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		mainController.showLoginLayout();
	}
	
	@FXML
	void backToLogin() {
		mainController.showLoginLayout();
	}
}
