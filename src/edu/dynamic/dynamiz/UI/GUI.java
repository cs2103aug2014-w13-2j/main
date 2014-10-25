package edu.dynamic.dynamiz.UI;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

public class GUI extends Application {
	public static DisplayFormatter disp = new DisplayFormatter();
	
	@Override
	public void start(Stage primaryStage) {
		BorderPane bp = new  BorderPane();
		primaryStage.setTitle("Dynamiz");
/*		
		// Set output area
		TextArea ta = TextAreaBuilder.create().prefWidth(800).prefHeight(600).wrapText(true).build();
		ta.setEditable(false);
		
		bp.setTop(ta);
		
		ta.setText(disp.displayWelcomeMessage()+"\n");
		ta.appendText(disp.displayPrompt(1)+"\n");
		
		
		// Set input area
		TextField tf = new TextField();
		bp.setBottom(tf);
		
	*/	
		//hbox
	    HBox hbox = new HBox();
	    Scene scene = new Scene(new Group());
	    Label label1 = new Label("Search");
	    label1.setTextFill(Color.web("#0076a3"));

	    hbox.setSpacing(10);
	    hbox.getChildren().add((label1));
	    ((Group) scene.getRoot()).getChildren().add(hbox);

	    // html editor
	    primaryStage.setWidth(650650);
        primaryStage.setHeight(300);   
        final HTMLEditor htmlEditor = new HTMLEditor();
        htmlEditor.setPrefHeight(245);
        Scene htmlscene = new Scene(htmlEditor);   
	    
		// display Scene
	    primaryStage.setScene(htmlscene);
		//primaryStage.setScene(new Scene(bp));
		primaryStage.show();
		
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
