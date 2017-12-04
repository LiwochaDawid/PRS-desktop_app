package prs.controllers;


import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class LandingPageController {
        //private MainLandingController mainController;
        @FXML
        private MenuButton accountButton;
        @FXML
        private ScrollPane scrollpane;
        
	@FXML
	void initialize() {
            Request request = new Request();
            request.Get();
	}
}
