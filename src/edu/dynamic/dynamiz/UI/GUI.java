package edu.dynamic.dynamiz.UI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextAreaBuilder;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUI extends Application {
	public static DisplayFormatter disp = new DisplayFormatter();
	
	@Override
	public void start(Stage primaryStage) {
		BorderPane bp = new  BorderPane();
		primaryStage.setTitle("Dynamiz");
		
		// Set output area
		TextArea ta = TextAreaBuilder.create().prefWidth(800).prefHeight(600).wrapText(true).build();
		ta.setEditable(false);
		
		bp.setTop(ta);
		
		ta.setText(disp.displayWelcomeMessage()+"\n");
		ta.appendText(disp.displayPrompt(1)+"\n");
		
		// Set input area
		TextField tf = new TextField();
		bp.setBottom(tf);
		
		primaryStage.setScene(new Scene(bp));
		primaryStage.show();
		
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
