package prs.controllers;

import java.awt.Window;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import prs.main.Main;

public class MainLandingController {
	@FXML
	private BorderPane mainBorderPane;
	@FXML
	private Image minimalize;
	@FXML
	private ImageView buttonClose;

	@FXML
	void initialize() {
		showLandingLayout();
	}

	public void showLandingLayout() {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/LandingLayout.fxml"));
		ScrollPane pane = null;
		try {
			pane = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		mainBorderPane.setMargin(pane, new Insets(15, 0, 0, 15));
		mainBorderPane.setCenter(pane);
	}

	@FXML
	public void settings() {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/SettingsLayout.fxml"));
		AnchorPane pane = null;
		try {
			pane = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		mainBorderPane.setMargin(pane, new Insets(15, 0, 0, 15));
		mainBorderPane.setCenter(pane);
	}

	@FXML
	public void logout() {
		Stage stage = (Stage) mainBorderPane.getScene().getWindow();

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
		stage.setX(bounds.getWidth() / 2 - (borderPane.getPrefWidth() / 2));
		stage.setY(bounds.getHeight() / 2 - (borderPane.getPrefWidth() / 2) - 100);
		stage.setHeight(borderPane.getPrefHeight());
		stage.setWidth(borderPane.getPrefWidth());
		Scene scene = new Scene(borderPane);
		stage.setScene(scene);
		new MainController();

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

}
