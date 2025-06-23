
import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GuiServer extends Application{

	
	TextField TFport;
	Button serverChoice;
	HashMap<String, Scene> sceneMap;
	HBox buttonBox;
	VBox mainBox;
	Scene startScene;
	BorderPane startPane;
	Server serverConnection;
	ListView<String> listItems;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Main Page");
		
		this.serverChoice = new Button("Connect");
		
		this.serverChoice.setOnAction(e->{ 
			
			primaryStage.setScene(sceneMap.get("server"));
			primaryStage.setTitle("This is the Server");
			serverConnection = new Server(Integer.parseInt(TFport.getText()), data -> {
				Platform.runLater(()->{
					listItems.getItems().add(data.toString());
				});

			});
											
		});
		
		Text Tname = new Text("Input The Port Number");
		Tname.setFill(Color.RED);
		Tname.setFont(new Font(30));
		
		this.TFport = new TextField();
		this.TFport.setMaxSize(180, 30);
		this.TFport.setMinSize(180, 30);
		
		
		
		this.serverChoice.setMaxSize(80, 30);
		this.serverChoice.setMinSize(80, 30);
		this.serverChoice.setStyle("-fx-background-color: #FFFFFF;" + 
								   "-fx-text-fill: #000000;" + 
								   "-fx-border-color: #FFFFFF;");
		
		this.mainBox = new VBox(20,Tname,TFport,serverChoice);
		this.mainBox.setAlignment(Pos.CENTER);
		
		startPane = new BorderPane();
		startPane.setPadding(new Insets(70));
		startPane.setCenter(mainBox);
		startPane.setStyle("-fx-background-color: #000000;");
		
		startScene = new Scene(startPane, 500,400);
		
		listItems = new ListView<String>();
		
		sceneMap = new HashMap<String, Scene>();
		
		sceneMap.put("server",  createServerGui());
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
		
		 
		
		primaryStage.setScene(startScene);
		primaryStage.show();
		
	}
	
	public Scene createServerGui() {
		
		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(70));
		pane.setStyle("-fx-background-color: #000000");
		
		pane.setCenter(listItems);
	
		return new Scene(pane, 500, 400);
		
		
	}
	
}
