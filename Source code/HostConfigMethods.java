package test;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HostConfigMethods {

	public static Scene hostConfig() 

	{
		//creating label hn
		Text text1 = new Text("HostName");
		//creating label hid
		Text text2 = new Text("HostID");
		//creating label uid
		Text text3 = new Text("UserID");
		//creating label KP
		Text text4 = new Text("KafkaPort");
		//creating label CP
		Text text5 = new Text("CassandraPort");
		//Creating Text Filed for hn
		TextField textField1 = new TextField();
		textField1.setPromptText("HostName.");
		//Creating Text Filed for hid
		TextField textField2 = new TextField();
		textField2.setPromptText("HostIP.");
		//Creating Text Filed for uid
		TextField textField3 = new TextField();
		textField3.setPromptText("UserID.");
		//Creating Text Filed for kp
		TextField textField4 = new TextField();
		textField4.setPromptText("KafkaPort.");
		//Creating Text Filed for cp
		TextField textField5 = new TextField();
		textField5.setPromptText("CassandraPort.");
		//Creating Buttons
		Button button1 = new Button("Submit");
		Button button2 = new Button("Clear");
		//Creating a Grid Pane
		GridPane gridPane = new GridPane();
		//Setting size for the pane
		gridPane.setMinSize(500, 500);
		//Setting the padding
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		//Setting the vertical and horizontal gaps between the columns
		gridPane.setVgap(5);
		gridPane.setHgap(5);
		//Setting the Grid allignment
		gridPane.setAlignment(Pos.CENTER);
		//Arrianging all the nodes in the grid
		gridPane.add(text1, 0, 0);
		gridPane.add(textField1, 1, 0);
		gridPane.add(text2, 0, 1);
		gridPane.add(textField2, 1, 1);
		gridPane.add(text3, 0, 2);
		gridPane.add(textField3, 1, 2);
		gridPane.add(text4, 0, 3);
		gridPane.add(textField4, 1, 3);
		gridPane.add(text5, 0, 4);
		gridPane.add(textField5, 1, 4);
		gridPane.add(button1, 0, 5);
		gridPane.add(button2, 1, 5);
		//Styling nodes
		button1.setStyle("-fx-background-color: darkslateblue; -fx-text-fill:white;");
		button2.setStyle("-fx-background-color: darkslateblue; -fx-text-fill:white;");
		text1.setStyle("-fx-font: normal bold 20px 'serif' ");
		text2.setStyle("-fx-font: normal bold 20px 'serif' ");
		text3.setStyle("-fx-font: normal bold 20px 'serif' ");
		text4.setStyle("-fx-font: normal bold 20px 'serif' ");
		text5.setStyle("-fx-font: normal bold 20px 'serif' ");
		gridPane.setStyle("-fx-background-color: BEIGE;");
		//Creating a scene object
		Scene scene = new Scene(gridPane);
		//Setting title to the Stage
		//stage.setTitle("Test Example");
		//Adding scene to the satge
		//stage.setScene(scene);
		//Displaying the contents of the stage
		//stage.show();
		return(scene);
		}
		
}
