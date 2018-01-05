package prs.controllers;


import java.io.IOException;
import java.time.YearMonth;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import prs.util.calendar.AnchorPaneNode;
import prs.util.calendar.FullCalendarView;

public class LandingPageController {
        @FXML
        private VBox calendar;
 
	@FXML
	void initialize() {
			calendar.getChildren().add(new FullCalendarView(YearMonth.now()).getView());
            Request request = new Request();      
	}
}
