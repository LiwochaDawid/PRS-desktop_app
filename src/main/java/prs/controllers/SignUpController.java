package prs.controllers;

import javafx.fxml.FXML;

public class SignUpController implements ILogin {
	private MainController mainController;

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

	@FXML
	void signUp() {
		System.out.println("Account created");
		mainController.showLoginLayout();
	}

	@FXML
	void backToLogin() {
		mainController.showLoginLayout();
	}
}
