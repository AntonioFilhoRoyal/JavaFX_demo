package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.service.DepartmentService;
import model.service.SellerService;

public class ViewController implements Initializable{

	@FXML
	private MenuItem menuItemSeller;
	
	@FXML
	private MenuItem menuItemDepartment;
	
	@FXML
	private MenuItem menuItemAbout;

	
	@FXML
	public void onMenuItemSeller() {
		loadView("/gui/Seller.fxml", (SellerController controller) -> {
			controller.setSellerService(new SellerService());
			controller.updateSeller();
		});
	}
	
	@FXML
	public void onMenuItemDeparment() {
		loadView("/gui/Department.fxml", (DepartmentController controller) -> {
			controller.setDepartmentService(new DepartmentService());
			controller.updateDeparment();
		});
	}
	
	@FXML
	public void onMenuItemAbout() {
		loadView("/gui/About.fxml", x -> {});
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	private synchronized <T> void loadView(String nameView, Consumer<T> initializingAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(nameView));
			VBox vbox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox) ((ScrollPane)mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(vbox.getChildren());
			
			T controller = loader.getController();
			initializingAction.accept(controller);
			
		} catch(IOException e) {
			Alerts.showAlert("IOException", "Error load view", e.getMessage(), AlertType.ERROR);
		}
	}
	
}