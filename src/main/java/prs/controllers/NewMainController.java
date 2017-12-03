package prs.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class NewMainController implements ILogin{
	private ILogin mainInterface;
        private MainController mainController;
        
	public void setMainScreen(Pane pane) {
		MainBorderPane.setCenter(pane);
	}
	
        
        @Override
	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}
        
	@FXML
	private BorderPane MainBorderPane;
	@FXML
	private ImageView buttonClose;

        @FXML 
        private Button buttonToday;
          
        public void showLandingLayout(){
          FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/LandingLayout.fxml"));
		Pane pane = null;
		try {
			pane = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
                System.out.println("CHECKED");
		mainInterface = loader.getController();
		mainInterface.setMainController(mainController);
		mainController.setMainScreen(pane);
            
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
