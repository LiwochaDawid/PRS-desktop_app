package prs.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;

public class LandingPageController implements ILogin{
        @FXML
        private MenuButton accountButton;
	@FXML
	void initialize() {
            Request request = new Request();
            request.Get();
	}

	@Override
	public void setMainController(MainController mainController) {
		// TODO Auto-generated method stub
		
	}

}
