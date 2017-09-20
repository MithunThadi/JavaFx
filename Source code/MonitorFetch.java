package test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class MonitorFetch {

   public static Scene monitorFetch(){

      //Query
     String query3 = "SELECT monitor_name,monitor_status,thread_status FROM monitor;" ;
		
      //Creating Cluster object
      Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
   
      //Creating Session object
      Session session = cluster.connect("ofte");
      
      ResultSet result = session.execute(query3);
       ObservableList<Map> data =FXCollections.observableArrayList();
       String Column1MapKey = "monitor_name";
       String Column2MapKey = "thread_status";
       String Column3MapKey = "monitor_status";
       List<Row> row = result.all();
        for (int i = 0; i < row.size(); i++) {
        	Map<String, String> dataRow = new HashMap<>();
            
    	  dataRow.put(Column1MapKey, row.get(i).getString("monitor_name"));
          dataRow.put(Column2MapKey, row.get(i).getString("thread_status"));
          dataRow.put(Column3MapKey, row.get(i).getString("monitor_status"));
          data.add(dataRow);
       //data = FXCollections.observableArrayList(new resultSet(result));
       System.out.println(result);
      }
    	  Scene scene = new Scene(new Group());
          final Label label = new Label("Monitor logs");
          label.setFont(new Font("TimesNewRoman", 20));
   
          TableColumn mnCol = new TableColumn("Monitor_Name");
          mnCol.setMinWidth(100);
          mnCol.setCellValueFactory(
        		  new MapValueFactory(Column1MapKey));
          TableColumn tsCol = new TableColumn("Thread_Status");
          tsCol.setMinWidth(100);
          tsCol.setCellValueFactory(new MapValueFactory(Column2MapKey));
          TableColumn msCol = new TableColumn("Monitor_Status");
          msCol.setMinWidth(100);
          msCol.setCellValueFactory(new MapValueFactory(Column3MapKey));
           TableView table = new TableView();
           table.setEditable(true);
           table.getColumns().setAll(mnCol, tsCol, msCol);
           //table.getRowFactory().call(result);s
           Callback<TableColumn<Map, String>, TableCell<Map, String>>
           cellFactoryForMap = new Callback<TableColumn<Map, String>,
               TableCell<Map, String>>() {
                   @Override
                   public TableCell call(TableColumn p) {
                       return new TextFieldTableCell(new StringConverter() {
                           @Override
                           public String toString(Object t) {
                               return t.toString();
                           }
                           @Override
                           public Object fromString(String string) {
                               return string;
                           }                                    
                       });
                   }
       };
       mnCol.setCellFactory(cellFactoryForMap);
       tsCol.setCellFactory(cellFactoryForMap);
       msCol.setCellFactory(cellFactoryForMap);
           table.setItems(data);
           
          final VBox vbox = new VBox();
          vbox.setSpacing(5);
          vbox.setPadding(new Insets(10, 0, 0, 10));
          vbox.getChildren().addAll(label, table);
   
          ((Group) scene.getRoot()).getChildren().addAll(vbox);
          
          //stage.setScene(new Scene(new BorderPane(table), 600, 400));
          
      return scene;
   }
}