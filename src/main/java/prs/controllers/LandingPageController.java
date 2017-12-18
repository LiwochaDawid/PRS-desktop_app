package prs.controllers;


import java.time.YearMonth;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
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
