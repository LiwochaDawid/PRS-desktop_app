package prs.util.calendar;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import prs.controllers.LandingPageController;
import prs.controllers.MainLandingController;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Create an anchor pane that can store additional data.
 */
public class AnchorPaneNode extends AnchorPane {

    // Date associated with this pane
    private LocalDate date;
    private BorderPane bp;
    /**
     * Create a anchor pane node. Date is not assigned in the constructor.
     * @param children children of the anchor pane
     */
    public AnchorPaneNode(Node... children) {
        super(children);
        // Add action handler for mouse clicked
        this.setOnMouseClicked(e -> {
        	String day;
        	if(date.getDayOfMonth()<10)
        		day="0"+Integer.toString(date.getDayOfMonth());
        	else
        		day=Integer.toString(date.getDayOfMonth());
        	String month;
        	if(date.getMonthValue()<10)
        		month="0"+Integer.toString(date.getMonthValue());
        	else
        		month=Integer.toString(date.getMonthValue());
        	String year=Integer.toString(date.getYear());
        	
        	String newDate=day+month+year;
        	
        });
    }
    public void setBorderPane(BorderPane bp) {
		this.bp=bp;
	}
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
