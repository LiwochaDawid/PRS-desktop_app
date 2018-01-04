package prs.controllers;


import java.time.YearMonth;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import prs.util.calendar.FullCalendarView;

public class LandingPageController {
        //private MainLandingController mainController;
        @FXML
        private MenuButton accountButton;
        @FXML
        private BorderPane borderPane;
        @FXML
        private Label todayVisits;
        @FXML
        private Label tommorowVisits;
        //private FullCalendarView calendar;
        @FXML
        private VBox calendar;
	@FXML
	void initialize() {
			calendar.getChildren().add(new FullCalendarView(YearMonth.now()).getView());	
            Request request = new Request();
	}
}
