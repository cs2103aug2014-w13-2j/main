package edu.dynamic.dynamiz.UI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUI extends Application {

	@Override
	public void start(Stage primaryStage) {
		BorderPane p = new  BorderPane();
		
		primaryStage.setTitle("Dynamiz");
		
		primaryStage.setScene(new Scene(p));
		primaryStage.show();
		
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
