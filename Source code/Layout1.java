package test;

import javax.swing.JButton;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
public class Layout1 extends Application {
	 Button b1,b2,b3,b4,b5,b6,b7;
	    Scene scene;
	    Stage thestage;
@Override
public void start(Stage primaryStage) {
    
    thestage=primaryStage;
    //thestage.initStyle(StageStyle.TRANSPARENT);

	BorderPane border = new BorderPane();
	HBox hbox = addHBox();
	border.setTop(hbox);
	border.setLeft(addVBox());
    
	border.setCenter(addGridPane());
	border.setRight(addFlowPane());
//Creating a scene object
Scene scene = new Scene(border);
//Setting title to the Stage
thestage.setTitle("Test Example");
//Adding scene to the satge
thestage.setScene(scene);
thestage.setMaximized(true);
//Displaying the contents of the stage
thestage.show();
}



private Node addFlowPane() {
	return null;
}


private Node addGridPane() {
	
	return null;
}

public HBox addHBox() {
	
    HBox hbox = new HBox();
    hbox.setPadding(new Insets(15, 12, 15, 12));
    hbox.setSpacing(10);
    hbox.setStyle("-fx-background-color:BEIGE;");

    //creating label OFTE
    Text text1 = new Text("OPEN FILE TRANSFER EDITION");
    text1.setTextAlignment(TextAlignment.CENTER);
    text1.setStyle("-fx-font: normal bold 20px 'serif' ");
    hbox.getChildren().add(text1);

    return hbox;
}

public VBox addVBox() {
    VBox vbox = new VBox();
    vbox.setPadding(new Insets(10));
    vbox.setSpacing(8);

    Text title = new Text("Home");
    title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    vbox.getChildren().add(title);

    Stage ss = new Stage();
    ss.initStyle(StageStyle.TRANSPARENT);
    Button b1 = new Button(" Create Monitors");
    b1.setPrefSize(150, 20);
    
    b1.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
//        	ss.initStyle(StageStyle.TRANSPARENT);
        	ss.setScene(MonitorMethods.access()); 
        	ss.show();
        }
    });
    
    Button b2 = new Button("Monitor logs");
    b2.setPrefSize(150, 20);
    
    b2.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
        	ss.setScene(MonitorFetch.monitorFetch()); 
        	ss.show();
        }
    });

    Button b3 = new Button("OneTimeTransfer");
    b3.setPrefSize(150, 20);
    
    Button b4 = new Button("ScheduledTransfer");
    b4.setPrefSize(150, 20);

    Button b5 = new Button("TransferLogs");
    b5.setPrefSize(150, 20);
    
    b5.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
//        	ss.initStyle(StageStyle.TRANSPARENT);
        	ss.setScene(TransferFetch1.transferFetch1()); 
        	ss.show();
        }
    });
    Button b6 = new Button("HostConfig");
    b6.setPrefSize(150, 20);
    
    b6.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
        	
        	ss.setScene(HostConfigMethods.hostConfig());
        	ss.show();
        }
    });
    
    Button b7 = new Button("Finish");
    b7.setPrefSize(150, 20);
   
    b7.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
        	ss.close();
        }
    });
    
    
    
    //b1.setAction(e-> ButtonClicked(e));
    //b5.setOnAction(e-> ButtonClicked(e));

    
    vbox.getChildren().addAll(b1,b2,b3,b4,b5,b6,b7);

    return vbox;
}
public static void main(String args[]){
launch(args);

}

}

