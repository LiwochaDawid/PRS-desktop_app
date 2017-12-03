package prs.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;

public class LandingPageController implements ILogin{
        private MainController mainController;
        @FXML
        private MenuButton accountButton;
	@FXML
	void initialize() {
            Request request = new Request();
            request.Get();
	}

	@Override
	public void setMainController(MainController mainController) {
		this.mainController = mainController;
		
	}

}
