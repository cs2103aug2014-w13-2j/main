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
		BorderPane bp = new  BorderPane();
	
		Text t = new Text("Welcome to Dynamiz!");
		t.setFont(Font.font("Arial", 60));
		t.setEffect(new DropShadow(2,3,3,Color.BLUE));
		
		
		bp.setCenter(t);
			
		primaryStage.setTitle("Dynamiz");
		
		primaryStage.setScene(new Scene(bp));
		primaryStage.show();
		
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
