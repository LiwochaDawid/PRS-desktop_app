package prs.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Shape;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import prs.util.file.Delete;



public class MainLandingController {
	private String token;
	private String email;
	private String password;
	@FXML
	private BorderPane mainBorderPane;
	@FXML
	private Image minimalize;
	@FXML
	private ImageView buttonClose;
	@FXML
	private Shape circle;
	@FXML
	private Button schedule;
	@FXML
	private Button visit;
	@FXML
	private Button settings;
	@FXML
	private Button patients;
	@FXML
	private Button logout;
	@FXML
	void initialize() {
		
		//start icon import
		//icon schedule
		final Image ICON_CALENDAR = new Image(getClass().getResourceAsStream("/images/calendar_ico2.png"));
        final ImageView scheduleIcon = new ImageView(ICON_CALENDAR);
		schedule.setGraphic(scheduleIcon);
		//icon visit
		final Image ICON_LIST = new Image(getClass().getResourceAsStream("/images/list_ico2.png"));
        final ImageView visitIcon = new ImageView(ICON_LIST);
		visit.setGraphic(visitIcon);
		//patient
		final Image ICON_PATIENT = new Image(getClass().getResourceAsStream("/images/patient_ico2.png"));
        final ImageView patientIcon = new ImageView(ICON_PATIENT);
		patients.setGraphic(patientIcon);
		//settings
		final Image ICON_SETTING = new Image(getClass().getResourceAsStream("/images/settings_ico2.png"));
        final ImageView settingIcon = new ImageView(ICON_SETTING);
		settings.setGraphic(settingIcon);
		//logout
		final Image ICON_LOGOUT = new Image(getClass().getResourceAsStream("/images/logout_ico2.png"));
        final ImageView logoutIcon = new ImageView(ICON_LOGOUT);
		logout.setGraphic(logoutIcon);
		mainBorderPane.setId("#mainBorderPane");
		//end
		initImage();
		showLandingLayout();
	}
	private void initImage() {
		Image im = new Image("/images/lekarz.jpg",false);
        circle.setFill(new ImagePattern(im));
        //cir2.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));
	}
	@FXML
	public void showVisits() {
		visit.setStyle("-fx-background-color: grey;");
		schedule.setStyle("-fx-background-color: transparent;"+".button:hover {-fx-background-color: grey;}");
		patients.setStyle("-fx-background-color: transparent;"+".button:hover {-fx-background-color: grey;}");
		settings.setStyle("-fx-background-color: transparent;"+".button:hover {-fx-background-color: grey;}");
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/VisitLayout.fxml"));
		AnchorPane pane = null;
		try {
			pane = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		mainBorderPane.setCenter(pane);
	}
	@FXML
	public void showLandingLayout() {
		visit.setStyle("-fx-background-color: transparent;"+".button:hover {-fx-background-color: grey;}");
		schedule.setStyle("-fx-background-color: grey;");
		patients.setStyle("-fx-background-color: transparent;"+".button:hover {-fx-background-color: grey;}");
		settings.setStyle("-fx-background-color: transparent;"+".button:hover {-fx-background-color: grey;}");
		
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/LandingLayout.fxml"));
		BorderPane pane = null;
		try {
			pane = (BorderPane)loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		mainBorderPane.setCenter(pane);

	}

	@FXML
	public void showPatients() {
		visit.setStyle("-fx-background-color: transparent;"+".button:hover {-fx-background-color: grey;}");
		schedule.setStyle("-fx-background-color: transparent;"+".button:hover {-fx-background-color: grey;}");
		patients.setStyle("-fx-background-color: grey;");
		settings.setStyle("-fx-background-color: transparent;"+".button:hover {-fx-background-color: grey;}");
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/PatientsLayout.fxml"));
		AnchorPane pane = null;
		try {
			pane = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		PatientsController pc=loader.getController();
		pc.setToken(token);
		//System.out.println(token);
		mainBorderPane.setCenter(pane);
	}
	@FXML
	public void showSettings() {
		visit.setStyle("-fx-background-color: transparent;"+".button:hover {-fx-background-color: grey;}");
		schedule.setStyle("-fx-background-color: transparent;"+".button:hover {-fx-background-color: grey;}");
		patients.setStyle("-fx-background-color: transparent;"+".button:hover {-fx-background-color: grey;}");
		settings.setStyle("-fx-background-color: grey;");
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/SettingsLayout.fxml"));
		AnchorPane pane = null;
		try {
			pane = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		mainBorderPane.setCenter(pane);
	}
	public void setView() {
		showVisits();
	}
	@FXML
	public void initLogout() {
		Stage stage = (Stage) mainBorderPane.getScene().getWindow();
		stage.close();
		Stage newStage = new Stage();
		newStage.initStyle(StageStyle.UNDECORATED);
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/MainLayout.fxml"));
		BorderPane borderPane = null;
		try {
			borderPane = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		newStage.setX(bounds.getWidth() / 2 - (borderPane.getPrefWidth() / 2));
		newStage.setY(bounds.getHeight() / 2 - (borderPane.getPrefWidth() / 2) - 100);
		newStage.setHeight(borderPane.getPrefHeight());
		newStage.setWidth(borderPane.getPrefWidth());
		Scene scene = new Scene(borderPane);
		newStage.setScene(scene);
		Delete.deleteFile("token.txt");
		new MainController();
		newStage.show();
	}

	@FXML
	public void minimalize(MouseEvent event) {
		Stage stage = (Stage) mainBorderPane.getScene().getWindow();
		stage.setIconified(true);
	}

	@FXML
	public void btnCloseHoverOn(MouseEvent event) {
		buttonClose.setImage(new Image("/images/closeBtnOn.png"));
	}

	@FXML
	public void btnCloseHoverOff(MouseEvent event) {
		buttonClose.setImage(new Image("/images/closeBtnOff.png"));
	}

	@FXML
	public void btnClose(MouseEvent event) {
		System.exit(0);
	}

	public BorderPane getMainBorderPane() {
		return mainBorderPane;
	}
}
