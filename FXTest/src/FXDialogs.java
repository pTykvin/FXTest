import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FXDialogs extends Application {

	@Override
	public void start(Stage primaryStage) {

		Button b = new Button("Show transparent dialog full screen");

		b.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	showDialog(primaryStage, true);
		    }
		});
		
		Button b1 = new Button("Show transparent dialog");

		b1.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	showDialog(primaryStage, false);
		    }
		});
		
		primaryStage.initStyle(StageStyle.UNDECORATED);
		
		b1.setTranslateY(50);
		
		StackPane root = new StackPane(b, b1);
		root.setPrefSize(600, 600);
		root.setStyle("-fx-background-color: linear-gradient(from 25% 25% to 100% 100%, #4398A1, #661a33)");
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	protected void showDialog(Stage primaryStage, boolean fullScreen) {
		Stage dialogStage = new Stage();
		dialogStage.initOwner(primaryStage);
		dialogStage.initStyle(StageStyle.UNDECORATED);
		dialogStage.initStyle(StageStyle.TRANSPARENT);	
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.setX(primaryStage.getX());
		dialogStage.setY(primaryStage.getY());
		dialogStage.setWidth(primaryStage.getWidth());
		dialogStage.setHeight(primaryStage.getHeight());
		dialogStage.setFullScreen(fullScreen);
		
		dialogStage.setFullScreenExitKeyCombination(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));
		dialogStage.setFullScreenExitHint("Нажмите Ctrl + E для выхода");

		
		Button b = new Button("EXIT");		
		b.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	dialogStage.close();
		    }
		});
		
		b.setPrefSize(100, 100);
		
		StackPane root = new StackPane(b);
		root.setPrefSize(100, 100);
		root.setStyle("-fx-background-color: rgba(0,0,0,0)");
		Scene scene = new Scene(root);
		scene.setFill(Color.web("rgba(0,0,0,0.5)"));
		dialogStage.setScene(scene);
		dialogStage.show();
		
//		
//		Alert alert = new Alert(AlertType.ERROR);
//		alert.setTitle("Exception Dialog");
//		alert.setHeaderText("Look, an Exception Dialog");
//		alert.setContentText("Could not find file blabla.txt!");
//		alert.initOwner(primaryStage);
//		alert.initStyle(StageStyle.UNDECORATED);
//		alert.initStyle(StageStyle.TRANSPARENT);				
//		DialogPane pane = alert.getDialogPane();		
//		pane.setStyle("-fx-background-color: rgba(0,0,0,0)");
//		Scene scene = pane.getScene();
//				
//		scene.setFill(Color.web("rgba(128,128,128,0.5)"));
//		
//		alert.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
