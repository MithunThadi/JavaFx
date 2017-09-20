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
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class TransferFetch1 {

   public static Scene transferFetch1(){

      //Query
     String query3 = "SELECT transfer_id,monitor_name,job_name,source_file,target_file,transfer_Status FROM monitor_transfers;" ;
		
		
      //Creating Cluster object
      Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").withPort(9042).build();
   
      //Creating Session object
      Session session = cluster.connect("ofte");
      
      ResultSet result = session.execute(query3);
       ObservableList<Map> data =FXCollections.observableArrayList();
       String Column3MapKey = "transfer_id";
       String Column2MapKey = "job_name";
       String Column1MapKey = "monitor_name";
       String Column4MapKey = "source_file";
       String Column5MapKey = "target_file";
       String Column6MapKey = "transfer_Status";
       List<Row> row = result.all();
        for (int i = 0; i < row.size(); i++) {
        	Map<String, String> dataRow = new HashMap<>();
            
        	 dataRow.put(Column1MapKey, row.get(i).getString("monitor_name"));
             dataRow.put(Column2MapKey, row.get(i).getString("job_name"));
             dataRow.put(Column3MapKey, row.get(i).getString("transfer_id"));
             dataRow.put(Column4MapKey, row.get(i).getString("source_file"));
             dataRow.put(Column5MapKey, row.get(i).getString("target_file"));
             dataRow.put(Column6MapKey, row.get(i).getString("transfer_Status"));
             data.add(dataRow);
       //data = FXCollections.observableArrayList(new resultSet(result));
       System.out.println(result);
      }
        Scene scene = new Scene(new Group());
        final Label label = new Label("Transfer logs");
        label.setFont(new Font("TimesNewRoman", 20));
 
        TableColumn mnCol = new TableColumn("Monitor_Name");
        mnCol.setMinWidth(30);
        mnCol.setCellValueFactory(
      		  new MapValueFactory(Column1MapKey));
        TableColumn jnCol = new TableColumn("Job_Name");
        jnCol.setMinWidth(30);
        jnCol.setCellValueFactory(
      		  new MapValueFactory(Column2MapKey));
        TableColumn tidCol = new TableColumn("Transfer_Id");
        tidCol.setMinWidth(100);
        tidCol.setCellValueFactory(
      		  new MapValueFactory(Column3MapKey));
        
        TableColumn sfCol = new TableColumn("Source_File");
        sfCol.setMinWidth(40);
        sfCol.setCellValueFactory(
      		  new MapValueFactory(Column4MapKey));
        TableColumn tfCol = new TableColumn("Target_File");
        tfCol.setMinWidth(40);
        tfCol.setCellValueFactory(
      		  new MapValueFactory(Column5MapKey));
        TableColumn tsCol = new TableColumn("Transfer_Status");
        tsCol.setMinWidth(30);
        tsCol.setCellValueFactory(
      		  new MapValueFactory(Column6MapKey));
        
        
         TableView table = new TableView();
         table.setEditable(false);
         table.getColumns().setAll(mnCol, jnCol, tidCol,sfCol, tfCol,tsCol );
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
     jnCol.setCellFactory(cellFactoryForMap);
     tidCol.setCellFactory(cellFactoryForMap);
     sfCol.setCellFactory(cellFactoryForMap);
     tfCol.setCellFactory(cellFactoryForMap);
     tsCol.setCellFactory(cellFactoryForMap);
    
         table.setItems(data);
         ResultSet resultSuccess = session.execute("select count(transfer_status) from monitor_transfers where transfer_status = 'success' ALLOW FILTERING;");
         ResultSet resultFailure = session.execute("select count(transfer_status) from monitor_transfers where transfer_status = 'failure' ALLOW FILTERING;");
         ResultSet resulttotal = session.execute("select count(transfer_status) from monitor_transfers  ALLOW FILTERING;");
         ResultSet resultPartial = session.execute("select count(transfer_status) from monitor_transfers where transfer_status = 'partialSuccess'  ALLOW FILTERING;");
         float total =  resulttotal.one().getLong(0);
         float success =  resultSuccess.one().getLong(0);
         float failure =   resultFailure.one().getLong(0);
         float  partial = resultPartial.one().getLong(0);
         System.out.println(success / total);
          ObservableList<PieChart.Data> pieChartData =
                 FXCollections.observableArrayList(
                 new PieChart.Data("Failure", ( failure /total) *100),
                 new PieChart.Data("PartialSuccess", (partial / total)*100 ), new PieChart.Data("Success", (success / total) *100));
         final PieChart chart = new PieChart(pieChartData);
         chart.setTitle("LOG DETAILS");
         final ObservableList<Node> children = ((Group) scene.getRoot()).getChildren();

         children.add(chart);

         final Label caption = new Label("");
         caption.setTextFill(Color.BLACK);
         caption.setStyle("-fx-font: 20 arial;");
         children.add(caption);

         for (final PieChart.Data data1 : chart.getData()) {
             data1.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                 new EventHandler<MouseEvent>() {
                     @Override public void handle(MouseEvent e) {
                         caption.setTranslateX(e.getSceneX());
                         caption.setTranslateY(e.getSceneY());
                         caption.setText(String.valueOf(data1.getPieValue()) + "%");
                         caption.setVisible(true);
                      }
                 });
         }

          final VBox vbox = new VBox();
          vbox.setSpacing(5);
          vbox.setPadding(new Insets(10, 0, 0, 10));
          vbox.getChildren().addAll(label, table,chart);
   
          ((Group) scene.getRoot()).getChildren().addAll(vbox);
          
          //stage.setScene(new Scene(new BorderPane(table), 600, 400));
          
      return scene;
   }
}