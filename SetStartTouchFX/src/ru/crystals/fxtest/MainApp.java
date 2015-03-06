package ru.crystals.fxtest;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/FXTest.fxml"));
		BorderPane rootLayout = (BorderPane) loader.load();
		Scene scene = new Scene(rootLayout);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Тест JavaFX");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
