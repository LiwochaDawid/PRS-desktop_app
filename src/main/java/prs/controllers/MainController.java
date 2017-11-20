package prs.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class MainController {
	public void setMainScreen(Pane pane) {
		MainBorderPane.setCenter(pane);
	}
	public void showLoginLayout() {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/LoginLayout.fxml"));
		Pane pane = null;
		try {
			pane = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		LoginController loginController = loader.getController();
		loginController.setMainController(this);
		MainBorderPane.setCenter(pane);
	}

	@FXML
	private BorderPane MainBorderPane;
	@FXML
	private ImageView buttonClose;

	@FXML
	public void initialize() {
		showLoginLayout();
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
