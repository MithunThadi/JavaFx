package test;

import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MonitorMethods {
	static HashMap<String, String> metaDataMap = new HashMap<String, String>();

	public static Scene access() {
	Text mnLabel = new Text(" Monitor Name");
	// Text field for mn

	TextField mnText = new TextField();
	// mnText.setText(value);
	// mnText.getText();
	mnText.getAccessibleText();
	// System.out.println("from");
	System.out.print(mnText.getAccessibleText());
	// Label for jn
	Text jnLabel = new Text("Job Name");
	// Text field for jn
	TextField jnText = new TextField();
	// jnText.getText();

	// Label for sdetails
	Text sdetailsLabel = new Text("Source Details:");
	// Label for sd
	Text sdLabel = new Text("Source Directory");
	// Text field for sd
	TextField sdText = new TextField();
	// sdText.getText();
	// Label for stp
	Text stpLabel = new Text("Source Trigger Pattern");
	// Text field for stp
	TextField stpText = new TextField();
	// stpText.getText();
	// Label for sfp
	Text sfpLabel = new Text("Source File Pattern");
	// Text field for sfp
	TextField sfpText = new TextField();
	// sfpText.getText();
	// Label for ddetails
	Text ddetailsLabel = new Text("Destination Details:");
	// Label for dd
	Text ddLabel = new Text("Destination Directory");
	// Text field for dd
	TextField ddText = new TextField();
	// ddText.getText();
	// Label for dfp
	Text dfpLabel = new Text("Destination File Pattern");
	// Text field for dfp
	final TextField dfpText = new TextField();
	dfpText.getAccessibleText();
	System.out.println(dfpText.getText());
	// Label for dtp
	Text dtpLabel = new Text("Destination Trigger Pattern");
	// Text field for dtp
	TextField dtpText = new TextField();
	dtpText.getText();

	// Label for polldetails
	Text polldetailsLabel = new Text("Poll Details:");
	// Label for pu
	Text pollunitsLabel = new Text("PollUnits");
	HBox box = new HBox(20);
	box.setPadding(new Insets(10, 10, 10, 10));
	// Label for pi
	Text pollintervalLabel = new Text("PollInterval");

	ObservableList<String> pu = FXCollections.observableArrayList("Minutes", "Seconds", "Days", "Weeks", "Years");

	final ObservableList<String> minutes = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8",
			"9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25",
			"26", "27", "28", "29", "30", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52",
			"53", "54", "55", "56", "57", "58", "59", "60");

	final ObservableList<String> seconds = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8",
			"9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25",
			"26", "27", "28", "29", "30", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52",
			"53", "54", "55", "56", "57", "58", "59", "60");

	final ObservableList<String> days = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8",
			"9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25",
			"26", "27", "28", "29", "30", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52",
			"53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69",
			"70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86",
			"87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99");

	final ObservableList<String> weeks = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8",
			"9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25",
			"26", "27", "28", "29", "30", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52",
			"53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69",
			"70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86",
			"87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99");

	final ObservableList<String> years = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8",
			"9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25",
			"26", "27", "28", "29", "30", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52",
			"53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69",
			"70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86",
			"87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99");

	ComboBox comboBox1 = new ComboBox(pu);
	final ComboBox comboBox2 = new ComboBox();
	comboBox1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
		@Override
		public void changed(ObservableValue ov, Object t, Object t1) {

			switch (t1.toString()) {
			case "Minutes":
				comboBox2.setItems(minutes);
				break;
			case "Seconds":
				comboBox2.setItems(seconds);
				break;
			case "Days":
				comboBox2.setItems(days);
				break;
			case "Weeks":
				comboBox2.setItems(weeks);
				break;
			case "Years":
				comboBox2.setItems(years);
				break;

			}
		}
	});
	box.getChildren().add(pollunitsLabel);
	box.getChildren().addAll(comboBox1);
	// System.out.println("display " + comboBox1.getAccessibleText());
	box.getChildren().add(pollintervalLabel);
	box.getChildren().addAll(comboBox2);

	// Label for mxml
	Text mxmlLabel = new Text(" Monitor XML FileName:");
	// Text field for mxml
	TextField mxmlText = new TextField();
	mxmlText.getText();

	// Creating Buttons
	Button button1 = new Button("Submit");
	Button button2 = new Button("Clear");
	//Button button3 = new Button("Finish");

	// Creating a Grid Pane
	// Adding a Label
	final Label label = new Label();
	GridPane.setConstraints(label, 1, 14);
	GridPane gridPane = new GridPane();
	gridPane.getChildren().add(label);

	// Setting size for the pane
	gridPane.setMinSize(500, 500);
	// Setting the padding
	gridPane.setPadding(new Insets(10, 10, 10, 10));
	// Setting the vertical and horizontal gaps between the columns
	gridPane.setVgap(5);
	gridPane.setHgap(5);
	// Setting the Grid allignment
	gridPane.setAlignment(Pos.CENTER);
	// Arranging all the nodes in the grid
	gridPane.add(mnLabel, 0, 0);
	gridPane.add(mnText, 1, 0);
	gridPane.add(jnLabel, 0, 1);
	gridPane.add(jnText, 1, 1);

	gridPane.add(sdetailsLabel, 0, 2);
	gridPane.add(sdLabel, 0, 3);
	gridPane.add(sdText, 1, 3);
	gridPane.add(stpLabel, 0, 4);
	gridPane.add(stpText, 1, 4);
	gridPane.add(sfpLabel, 0, 5);
	gridPane.add(sfpText, 1, 5);

	gridPane.add(ddetailsLabel, 0, 6);
	gridPane.add(ddLabel, 0, 7);
	gridPane.add(ddText, 1, 7);
	gridPane.add(dfpLabel, 0, 8);
	gridPane.add(dfpText, 1, 8);
	gridPane.add(dtpLabel, 0, 9);
	gridPane.add(dtpText, 1, 9);

	gridPane.add(polldetailsLabel, 0, 10);
	gridPane.add(pollunitsLabel, 0, 11);
	gridPane.add(box, 1, 11);

	gridPane.add(mxmlLabel, 0, 12);
	gridPane.add(mxmlText, 1, 12);

	gridPane.add(button1, 0, 13);
	gridPane.add(button2, 1, 13);
	//gridPane.add(button3, 2, 13);

	// Setting an action for the Submit button
	button1.setOnAction(new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent e) {
			if ((mnText.getText() != null && !mnText.getText().isEmpty())) {
				// label.setText(
				// mnText.getText() + " " + jnText.getText() + " " + sdText.getText() + " " +
				// stpText.getText()
				// + " " + sfpText.getText() + " " + ddText.getText() + " " + dfpText.getText()
				// + " "
				// + dtpText.getText() + " " + mxmlText.getText() + " " + "Sucessfull!");
				System.out.println(mxmlText.getText());
				metaDataMap.put("monitorName", mnText.getText().toString());

				metaDataMap.put("jobName", jnText.getText().toString());

				metaDataMap.put("sourceDirectory", sdText.getCharacters().toString());

				metaDataMap.put("sourceTriggerPattern", stpText.getText());

				metaDataMap.put("sourceFilePattern", sfpText.getText());
				metaDataMap.put("destinationDirectory", ddText.getText());
				metaDataMap.put("destinationFilePattern", dfpText.getText());
				metaDataMap.put("destinationTriggerPattern", dtpText.getText());
				metaDataMap.put("monitorFileName", mxmlText.getText());
				metaDataMap.put("pollUnits", comboBox1.getValue().toString());
				metaDataMap.put("pollInterval", comboBox2.getValue().toString());
				metaDataMap.put("monitorXml", mxmlText.getText());
				System.out.println(metaDataMap);
				label.setText("Successfullly");
				try {
					xmlCreator.access(metaDataMap);
				} catch (ParserConfigurationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (TransformerException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoSuchFieldException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SecurityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				label.setText("You have not entered all the details.");
			}
		}
	});

	// Setting an action for the Clear button
	button2.setOnAction(new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent e) {
			mnText.clear();
			jnText.clear();
			sdText.clear();
			stpText.clear();
			sfpText.clear();
			ddText.clear();
			dfpText.clear();
			dtpText.clear();
			mxmlText.clear();
			label.setText(null);
		}
	});

//	button3.setOnAction(new EventHandler<ActionEvent>() {
//
//		@Override
//		public void handle(ActionEvent e) {
//		scene.close();
//		}
//		});
	// Styling nodes
	button1.setStyle("-fx-background-color: darkslateblue; -fx-text-fill:white;");
	button2.setStyle("-fx-background-color: darkslateblue; -fx-text-fill:white;");
	//button3.setStyle("-fx-background-color: darkslateblue; -fx-text-fill:white;");
	mnLabel.setStyle("-fx-font: normal bold 15px 'serif' ");
	jnLabel.setStyle("-fx-font: normal bold 15px 'serif' ");
	sdetailsLabel.setStyle("-fx-font: normal bold 18px 'serif' ");
	sdLabel.setStyle("-fx-font: normal bold 15px 'serif' ");
	stpLabel.setStyle("-fx-font: normal bold 15px 'serif' ");
	sfpLabel.setStyle("-fx-font: normal bold 15px 'serif' ");
	ddetailsLabel.setStyle("-fx-font: normal bold 18px 'serif' ");
	ddLabel.setStyle("-fx-font: normal bold 15px 'serif' ");
	dfpLabel.setStyle("-fx-font: normal bold 15px 'serif' ");
	dtpLabel.setStyle("-fx-font: normal bold 15px 'serif' ");
	polldetailsLabel.setStyle("-fx-font: normal bold 18px 'serif' ");
	pollunitsLabel.setStyle("-fx-font: normal bold 15px 'serif' ");
	pollintervalLabel.setStyle("-fx-font: normal bold 15px 'serif' ");
	mxmlLabel.setStyle("-fx-font: normal bold 15px 'serif' ");
	// Setting the back ground color
	gridPane.setStyle("-fx-background-color: BEIGE;");
	// Creating a scene object
	Scene scene = new Scene(gridPane);
	// Setting title to the Stage
//	stage.setTitle("Monitors");
//	// Adding scene to the stage
//	stage.setScene(scene);
//	// Displaying the contents of the stage
//	stage.show();
	return scene;
	}
	
}
