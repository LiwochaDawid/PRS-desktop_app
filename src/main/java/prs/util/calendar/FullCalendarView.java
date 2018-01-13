package prs.util.calendar;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import prs.controllers.MainLandingController;
import prs.controllers.Request;
import prs.controllers.VisitController;
import prs.util.file.Open;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import static jdk.nashorn.internal.objects.Global.println;


public class FullCalendarView {

    private ArrayList<AnchorPaneNode> allCalendarDays = new ArrayList<>(35);
    private VBox view;
    private Label calendarTitle;
    private YearMonth currentYearMonth;
    private GridPane calendar;
    private static String date;
    
    /**
     * Create a calendar view
     * @param yearMonth year month to create the calendar of
     */
    public FullCalendarView(YearMonth yearMonth) {
        currentYearMonth = yearMonth;
        // Create the calendar grid pane
        calendar = new GridPane();
        calendar.setAlignment(Pos.CENTER);
        calendar.setMinSize(1200, 800);
        calendar.setGridLinesVisible(true);
        date = null;
        // Create rows and columns with anchor panes for the calendar
        
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                AnchorPaneNode ap = new AnchorPaneNode();
                
             
                ap.setPrefSize(200,200);
                calendar.add(ap,j,i);
                allCalendarDays.add(ap);
            }
        }
        // Days of the week labels
        Text[] dayNames = new Text[]{  new Text("Monday"), new Text("Tuesday"),
                                        new Text("Wednesday"), new Text("Thursday"), new Text("Friday"),
                                        new Text("Saturday"),new Text("Sunday")};
        GridPane dayLabels = new GridPane();
        dayLabels.setAlignment(Pos.CENTER);
        dayLabels.setPrefWidth(600);
        Integer col = 0;
        for (Text txt : dayNames) {
            AnchorPane ap = new AnchorPane();
            ap.setPrefSize(200, 10);
            AnchorPane.setBottomAnchor(txt, 5.0);
            ap.getChildren().add(txt);
            dayLabels.add(ap, col++, 0);
        }
        // Create calendarTitle and buttons to change current month
        
        calendarTitle = new Label();
        calendarTitle.setMinSize(100, 16);
        calendarTitle.setAlignment(Pos.CENTER);
        Button previousMonth = new Button("<<");
        previousMonth.setOnAction(e -> previousMonth());
        Button nextMonth = new Button(">>");
        nextMonth.setOnAction(e -> nextMonth());
        for (AnchorPaneNode allCalendarDay : allCalendarDays)
            allCalendarDay.setOnMouseClicked(e -> {
                        AnchorPaneNode source = (AnchorPaneNode) e.getSource();
                        Label x = (Label) source.getChildren().get(0);
                        String day[] = x.getText().split(" ");
                        String date[] = calendarTitle.getText().split(" ");
                        openVisits(day[0], date[0], date[1]);
                        System.out.println("constructedDATE = "+day[0] + " " + date[0] + " " + date[1]);
                    }
            );
        HBox titleBar = new HBox(previousMonth, calendarTitle, nextMonth);
        titleBar.setAlignment(Pos.BASELINE_CENTER);
        // Populate calendar with the appropriate day numbers
        populateCalendar(yearMonth);
        // Create the calendar view
        view = new VBox(titleBar, dayLabels, calendar);
    }


    /**
     * Set the days of the calendar to correspond to the appropriate date
     * @param yearMonth year and month of month to render
     */
    public void populateCalendar(YearMonth yearMonth) {
        // Get the date we want to start with on the calendar
        LocalDate calendarDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 1);

		String date;
		if (Integer.toString(calendarDate.getMonthValue()).length() == 1) date = "010" + Integer.toString(calendarDate.getMonthValue()) + Integer.toString(calendarDate.getYear());
		else date = "01" + Integer.toString(calendarDate.getMonthValue()) + Integer.toString(calendarDate.getYear());
		
        // Dial back the day until it is SUNDAY (unless the month starts on a sunday)
        while (!calendarDate.getDayOfWeek().toString().equals("MONDAY") ) {
            calendarDate = calendarDate.minusDays(1);
        }
        
        String token;
        
		try {
			token = Open.openFile();
			Request request = new Request();
			
			String response = request.Get("/visit/numberOfVisitsMonth=" + date + "?", token);
			JsonElement json = new JsonParser().parse(response);
			JsonArray numberOfVisits = json.getAsJsonArray();
			
			System.out.println(numberOfVisits);
        
	        // Populate the calendar with day numbers
	        boolean isBeforeCurrentMonth = true;
	        boolean isAfterCurrentMonth = false;
	        for (AnchorPaneNode ap : allCalendarDays) {
	            if (ap.getChildren().size() != 0) {
	                ap.getChildren().remove(0);
	            }
	            Label x=new Label();
	            x.setText(String.valueOf(calendarDate.getDayOfMonth()));
	            x.setMaxWidth(Double.MAX_VALUE);
	            x.setMaxHeight(Double.MAX_VALUE);
	            x.setAlignment(Pos.CENTER);
	            x.setFont(Font.font ("Verdana",FontWeight.BOLD, 20));
	            ap.setMinSize(0,0);
                ap.setPrefSize(300,300);
                ap.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
	            ap.setBorder(null);
	            if (isBeforeCurrentMonth) {
	            	if (calendarDate.getDayOfMonth() > 1) {
	        			ap.setStyle("-fx-background-color: rgba(0, 0, 0, .15);");
	            		x.setDisable(true);
	            	}
	            	else {
	            		if(numberOfVisits.get(calendarDate.getDayOfMonth()-1).getAsInt()<=2 && !isAfterCurrentMonth)
	            			ap.setStyle("-fx-background-color: rgba(0, 255, 80, .3);");
	            		else if(numberOfVisits.get(calendarDate.getDayOfMonth()-1).getAsInt()>2 && numberOfVisits.get(calendarDate.getDayOfMonth()-1).getAsInt()<=5 && !isAfterCurrentMonth)
	            			ap.setStyle("-fx-background-color: rgba(255, 150, 0, .3);");
	            		else if(numberOfVisits.get(calendarDate.getDayOfMonth()-1).getAsInt()>5  && !isAfterCurrentMonth)
	            			ap.setStyle("-fx-background-color: rgba(255, 50, 0, .3);");
		            	x.setText(x.getText() + " (" + numberOfVisits.get(calendarDate.getDayOfMonth()-1) + ")"); 	
	            		isBeforeCurrentMonth = false;
	            	}
	            }
	            else {
	            	if (calendarDate.getDayOfMonth() == 1) {
	            		isAfterCurrentMonth = true;
	            	}
	            	
	            	if (isAfterCurrentMonth) {
	        			ap.setStyle("-fx-background-color: rgba(0, 0, 0, .15);");
	            		x.setDisable(true);
	            	}
	            	else if (!isAfterCurrentMonth) {
	            		if(numberOfVisits.get(calendarDate.getDayOfMonth()-1).getAsInt()<=2) ap.setStyle("-fx-background-color: rgba(0, 255, 80, .3);");
	            		else if(numberOfVisits.get(calendarDate.getDayOfMonth()-1).getAsInt()>2 && numberOfVisits.get(calendarDate.getDayOfMonth()-1).getAsInt()<=5) ap.setStyle("-fx-background-color: rgba(255, 150, 0, .3);");
	            		else if(numberOfVisits.get(calendarDate.getDayOfMonth()-1).getAsInt()>5) ap.setStyle("-fx-background-color: rgba(255, 50, 0, .3);");
	            		x.setText(x.getText() + " (" + numberOfVisits.get(calendarDate.getDayOfMonth()-1) + ")");
	    	            Calendar cal = Calendar.getInstance();
	    	            int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
	    	            if (calendarDate.getDayOfMonth() == dayOfMonth && calendarDate.getMonthValue() == cal.get(Calendar.MONTH)+1 && calendarDate.getYear() == cal.get(Calendar.YEAR)) {
	    		            ap.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THICK)));
	    	            }
	            	}
	            }
	            
	            ap.setDate(calendarDate);
	            AnchorPane.setTopAnchor(x, 5.0);
	            AnchorPane.setBottomAnchor(x, 5.0);
	            AnchorPane.setRightAnchor(x, 5.0);
	            AnchorPane.setLeftAnchor(x, 5.0);
	            ap.getChildren().add(x);
	            calendarDate = calendarDate.plusDays(1);
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // Change the title of the calendar
        calendarTitle.setText(yearMonth.getMonth().toString() + " " + String.valueOf(yearMonth.getYear()));
    }

    /**
     * Move the month back by one. Repopulate the calendar with the correct dates.
     */
    private void previousMonth() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        populateCalendar(currentYearMonth);
    }

    /**
     * Move the month forward by one. Repopulate the calendar with the correct dates.
     */
    private void nextMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        populateCalendar(currentYearMonth);
    }

    public VBox getView() {
        return view;
    }

    public ArrayList<AnchorPaneNode> getAllCalendarDays() {
        return allCalendarDays;
    }

    public void setAllCalendarDays(ArrayList<AnchorPaneNode> allCalendarDays) {
        this.allCalendarDays = allCalendarDays;
    }


    public void openVisits(String day, String month, String year) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/VisitLayout.fxml"));
        AnchorPane pane = null;
        month=String.valueOf(Month.valueOf(month.toUpperCase()).getValue());
        if (Integer.parseInt(day)<10)
            day="0"+day;
        if (Integer.parseInt(month)<10)
            month="0"+month;
        date=day+month+year;
        System.out.println("Date = "+date);
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        view.getChildren().clear();
        pane.setMinSize(Region.USE_COMPUTED_SIZE,Region.USE_COMPUTED_SIZE);
        pane.setPrefSize(1000,1000);
        pane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        view.getChildren().add(0,pane);
        view.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        view.setPrefSize(1000,1000);
    }

    public static String getDate() {
        return date;
    }

    public static void nullifyDate(){
        date = null;
    }
}
