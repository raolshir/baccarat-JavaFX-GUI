
import java.util.HashMap;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class GuiClient extends Application{

	
	TextField TFport,TFipAddress, TFbet;
	Button clientChoice,Bsubmit,Bplay,Bquit, BonPlayer, BonBanker, BonDraw;
	HashMap<String, Scene> sceneMap;
	GridPane grid;
	VBox clientBox, Box;
	Scene startScene, gameScene;
	BorderPane startPane;
	StackPane stackpane;
	Line line;
	Rectangle rectBox;
	Label BaccaratGame,Lplayer, Lbanker, Lbet, LbetOn, Lwinning;
	Label Lp1, Lp2, Lp3, Lps1, Lps2, Lps3;
	Label Lb1, Lb2, Lb3, Lbs1, Lbs2, Lbs3;
	Text Twinning;
	Client clientConnection;
	
	ListView<String> listItems;
	
	int clientOption;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Main Page");
		

		
		
		this.clientChoice = new Button("Connect");
		this.clientChoice.setMaxSize(80, 30);
		this.clientChoice.setMinSize(80, 30);
		this.clientChoice.setStyle("-fx-background-color: #FFFFFF;" + 
								   "-fx-text-fill: #000000;" + 
								   "-fx-border-color: #FFFFFF;");
		
		this.clientChoice.setOnAction(e-> {
			primaryStage.setScene(sceneMap.get("client"));
			primaryStage.setTitle("Baccarat Game");
			clientConnection = new Client(TFipAddress.getText(), Integer.parseInt(TFport.getText()),data->{
									
				BaccaratInfo temp = (BaccaratInfo) data;
				Platform.runLater(()->{
						clientConnection.setMessage();
						creatingCard(temp.Cards, temp.suite);
						int sec = 7;
						if(temp.Cards[2] == 0 && temp.Cards[5] == 0 ) {
							sec = 5;
						} else if((temp.Cards[2] == 0 && temp.Cards[5] != 0) || (temp.Cards[2] != 0 && temp.Cards[5] == 0) ) {
							sec = 6;
						}
						PauseTransition pause1 = new PauseTransition(Duration.seconds(sec));
						pause1.play();
						pause1.setOnFinished(time ->{ 
							listItems.getItems().add(clientConnection.message);
						
						});
						PauseTransition pause2 = new PauseTransition(Duration.seconds(sec));
						pause2.play();
						pause2.setOnFinished(time ->{ 
							if(temp.isWin) {
								double winTotal = Double.parseDouble(Twinning.getText()) + temp.playerTotalAmount;
								
								Twinning.setText(String.valueOf(winTotal));
							} else {
								double winTotal = Double.parseDouble(Twinning.getText()) - Double.parseDouble(TFbet.getText());
								
								Twinning.setText(String.valueOf(winTotal));
							}
						
						});
						
						
						
				
				});
			});
	
			clientConnection.start();
		});
		

		Text Tname = new Text("Input The Port Number");
		Tname.setFill(Color.RED);
		Tname.setFont(new Font(30));
		
		this.TFport = new TextField();
		this.TFport.setMaxSize(180, 30);
		this.TFport.setMinSize(180, 30);
		
		Text TipName = new Text("Input The IP Address");
		TipName.setFill(Color.RED);
		TipName.setFont(new Font(30));
		
		this.TFipAddress = new TextField();
		this.TFipAddress.setMaxSize(180, 30);
		this.TFipAddress.setMinSize(180, 30);
		
		clientBox = new VBox(10,Tname,TFport,TipName,TFipAddress,clientChoice);
		clientBox.setAlignment(Pos.CENTER);

		startPane = new BorderPane();
		startPane.setPadding(new Insets(50));
		startPane.setCenter(clientBox);
		startPane.setStyle("-fx-background-color: #000000;");
		
		startScene = new Scene(startPane, 500,400);
		
		
		
		sceneMap = new HashMap<String, Scene>();
		
		sceneMap.put("client",  createClientGui());
		
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
	
	public void enableButton() {
		BonBanker.setDisable(false);
		BonDraw.setDisable(false);
		BonPlayer.setDisable(false);
		Bsubmit.setDisable(false);
		
	}
	
	public Scene createClientGui() {
		
		listItems = new ListView<String>();

		listItems.setMaxSize(300, 230);
		listItems.setMinSize(300, 230);
		listItems.setTranslateX(219);
		listItems.setTranslateY(157);
		
		rectBox = new Rectangle();
		rectBox.setWidth(740);
		rectBox.setHeight(87);
		rectBox.setStyle("-fx-fill:  DODGERBLUE");
		rectBox.setStroke(Color.BLACK);
		rectBox.setTranslateX(0);
		rectBox.setTranslateY(-229);
		// giving the game a title
		BaccaratGame = new Label("Baccarat Game");
		BaccaratGame.setFont(new Font(40));
		Stop[] stops = new Stop[] {new Stop(0 , Color.rgb(81,68,68)), new Stop(1 , Color.RED)};
		BaccaratGame.setTextFill(new LinearGradient(0,0,1,0,true, CycleMethod.NO_CYCLE, stops));
		BaccaratGame.setUnderline(true);;
		BaccaratGame.setTranslateX(0);
		BaccaratGame.setTranslateY(-230); 
		// labeling where the player card goes
		Lplayer = new Label("Player");
		Lplayer.setFont(new Font(34));
		Lplayer.setTextFill(Color.RED);
		Lplayer.setUnderline(true);;
		Lplayer.setTranslateX(-157);
		Lplayer.setTranslateY(-148);
		// Labeling where the banker cards goes
		Lbanker = new Label("Banker");
		Lbanker.setFont(new Font(34));
		Lbanker.setTextFill(Color.RED);
		Lbanker.setUnderline(true);
		Lbanker.setTranslateX(157);
		Lbanker.setTranslateY(-148);
		// creating the line that separates the player from the banker
		line = new Line();
		line.setStartX(101f);
		line.setStartY(-219.5f);
		line.setEndX(101f);
		line.setEndY(-75.7f);
		line.setTranslateX(0);
		line.setTranslateY(-87);
		line.setStroke(Color.BLACK);
		line.setStrokeWidth(3);
		
		Lbet = new Label("Bet Amount:");
		Lbet.setFont(new Font(18));
		Lbet.setTextFill(Color.RED);
		Lbet.setTranslateX(-298);
		Lbet.setTranslateY(60);
		
		LbetOn = new Label("Bet On:");
		LbetOn.setFont(new Font(18));
		LbetOn.setTextFill(Color.RED);
		LbetOn.setTranslateX(-295);
		LbetOn.setTranslateY(120);
		
		Lwinning = new Label("Total Amount:");
		Lwinning.setFont(new Font(18));
		Lwinning.setTextFill(Color.RED);
		Lwinning.setTranslateX(-307);
		Lwinning.setTranslateY(180);
		// Starting the game
		Bsubmit = new Button("Submit");
		Bsubmit.setFont(new Font(13));
		Bsubmit.setTextFill(Color.WHITE);
		Bsubmit.setStyle("-fx-background-color: #000000;");
		Bsubmit.setTranslateX(-77);
		Bsubmit.setTranslateY(62);
		Bsubmit.setOnAction(c->{
			if(TFbet.getText().length() != 0 && clientOption !=0) {
				Bsubmit.setDisable(true);
				int x = Integer.parseInt(TFbet.getText());
				clientConnection.BetAmount(x);
				clientConnection.startgame();
			} else {
				listItems.getItems().add("Did not press the Button or input the bet");
			}
			

			
		});
		
		
		// Quieting the game 
		Bquit = new Button("Quit");
		Bquit.setFont(new Font(13));
		Bquit.setTextFill(Color.rgb(255,169,169));
		Bquit.setStyle("-fx-background-color: #000000;" + "-fx-font-weight: bold");
		Bquit.setTranslateX(-243);
		Bquit.setTranslateY(13);
		Bquit.setOnAction(quit -> {
			Platform.exit();
			clientConnection.QuitGame(true);
			
			System.exit(0);
		});
		// playing the game
		Bplay = new Button("Play");
		Bplay.setFont(new Font(13));
		Bplay.setTextFill(Color.rgb(255,169,169));
		Bplay.setStyle("-fx-background-color: #000000;" + "-fx-font-weight: bold");
		Bplay.setTranslateX(-318);
		Bplay.setTranslateY(13);
		Bplay.setOnAction(play -> {
			TFbet.clear();
			Bplay.setText("Play Again");
			resetCard();
			clientOption = 0;
			enableButton();
		});
		// inputting the bet amount
		TFbet = new TextField();
		TFbet.setMaxWidth(100);
		TFbet.setTranslateX(-180);
		TFbet.setTranslateY(61);
		
		//betting options
		BonPlayer = new Button("Player");
		BonPlayer.setTranslateX(-220);
		BonPlayer.setTranslateY(122);
		BonPlayer.setOnAction(e->{
			clientOption = 1;
			BonBanker.setDisable(true);
			BonDraw.setDisable(true);
			BonPlayer.setDisable(true);
			
			clientConnection.BetOption(1);
		});
		
		BonBanker = new Button("Banker");
		BonBanker.setTranslateX(-160);
		BonBanker.setTranslateY(122);
		BonBanker.setOnAction(e->{
			clientOption = 2;
			BonPlayer.setDisable(true);
			BonDraw.setDisable(true);
			BonBanker.setDisable(true);
			clientConnection.BetOption(clientOption);
		});
		
		BonDraw = new Button("Draw");
		BonDraw.setTranslateX(-101);
		BonDraw.setTranslateY(122);
		BonDraw.setOnAction(e->{
			clientOption = 3;
			BonPlayer.setDisable(true);
			BonBanker.setDisable(true);
			BonDraw.setDisable(true);
			clientConnection.BetOption(clientOption);
		});
		
		//displaying the winning and lost amount
		Twinning = new Text("0");
		Twinning.setFont(new Font(18));
		Twinning.setTranslateX(-220);
		Twinning.setTranslateY(180);
		//banker cards
		Lb1 = new Label();
		Lb1.setTranslateX(78);
		Lb1.setTranslateY(-94);
		
		Lb2 = new Label();
		Lb2.setTranslateX(157);
		Lb2.setTranslateY(-94);
		
		Lb3 = new Label();
		Lb3.setTranslateX(235);
		Lb3.setTranslateY(-94);
		
		Lbs1 = new Label();
		Lbs1.setTranslateX(78);
		Lbs1.setTranslateY(-39);
		
		Lbs2 = new Label();
		Lbs2.setTranslateX(157);
		Lbs2.setTranslateY(-39);
		
		Lbs3 = new Label();
		Lbs3.setTranslateX(235);
		Lbs3.setTranslateY(-39);
		//player cards
		Lp1 = new Label();
		Lp1.setTranslateX(-235);
		Lp1.setTranslateY(-94);
		
		Lp2 = new Label();
		Lp2.setTranslateX(-157);
		Lp2.setTranslateY(-94);
		
		Lp3 = new Label();
		Lp3.setTranslateX(-78);
		Lp3.setTranslateY(-94);
		
		Lps1 = new Label();
		Lps1.setTranslateX(-235);
		Lps1.setTranslateY(-39);
		
		Lps2 = new Label();
		Lps2.setTranslateX(-157);
		Lps2.setTranslateY(-39);
		
		Lps3 = new Label();
		Lps3.setTranslateX(-78);
		Lps3.setTranslateY(-39);
		
		stackpane = new StackPane(rectBox,listItems,BaccaratGame , Lplayer, Lbanker, line,
								Lbet, LbetOn, Lwinning, Bsubmit, Bquit, Bplay, TFbet, BonPlayer, BonBanker, BonDraw,
								Twinning, Lp1, Lp2, Lp3, Lps1, Lps2, Lps3, Lb1, Lb2, Lb3, Lbs1, Lbs2, Lbs3);
		stackpane.setStyle("-fx-background-color:  lightblue");
		return new Scene(stackpane, 738, 545);
		
	}

	public void resetCard() {
		Lp1.setText("");
		Lps1.setText("");
		Lp2.setText("");
		Lps2.setText("");
		Lp3.setText("");
		Lps3.setText("");

		Lp1.setTextFill(Color.BLACK);
		Lps1.setTextFill(Color.BLACK);
		Lp2.setTextFill(Color.BLACK);
		Lps2.setTextFill(Color.BLACK);
		Lp3.setTextFill(Color.BLACK);
		Lps3.setTextFill(Color.BLACK);
		
		Lb1.setText("");
		Lbs1.setText("");
		Lb2.setText("");
		Lbs2.setText("");
		Lb3.setText("");
		Lbs3.setText("");
		
		Lb1.setTextFill(Color.BLACK);
		Lbs1.setTextFill(Color.BLACK);
		Lb2.setTextFill(Color.BLACK);
		Lbs2.setTextFill(Color.BLACK);
		Lb3.setTextFill(Color.BLACK);
		Lbs3.setTextFill(Color.BLACK);
	}
	public void creatingCard(int[] card, String[] suite) {
		Lp1.setFont(new Font(18));
		Lp2.setFont(new Font(18));
		Lp3.setFont(new Font(18));
		Lps1.setFont(new Font(18));
		Lps2.setFont(new Font(18));
		Lps3.setFont(new Font(18));

		
		Lb1.setFont(new Font(18));
		Lb2.setFont(new Font(18));
		Lb3.setFont(new Font(18));
		Lbs1.setFont(new Font(18));
		Lbs2.setFont(new Font(18));
		Lbs3.setFont(new Font(18));
		
		String[] tempSuite = suite;
		changeColor(tempSuite);
		
		for(int i = 0; i< tempSuite.length; i++) {
			if(tempSuite[i].equals("hearts") ) {
				tempSuite[i] = "♥";
			} else if(tempSuite[i].equals("spades")) {
				tempSuite[i] = "♠";
			} else if(tempSuite[i].equals("clubs")) {
				tempSuite[i] = "♣";
			} else if(tempSuite[i].equals( "diamonds")) {
				tempSuite[i] = "♦";
			}
		}
		
		PauseTransition pause1 = new PauseTransition(Duration.seconds(1)); 
		PauseTransition pause2 = new PauseTransition(Duration.seconds(2)); 
		PauseTransition pause3 = new PauseTransition(Duration.seconds(3)); 
		PauseTransition pause4 = new PauseTransition(Duration.seconds(4)); 
		PauseTransition pause5 = new PauseTransition(Duration.seconds(5));
		 
		pause1.play();
		pause1.setOnFinished(e ->{ 
			Lp1.setText(changeNumb(card[0]));
			Lps1.setText(tempSuite[0]);
		});
		pause2.play();
		pause2.setOnFinished(e ->{ 
			Lp2.setText(changeNumb(card[1]));
			Lps2.setText(tempSuite[1]);
		});
		
		pause3.play();
		pause3.setOnFinished(e ->{ 
			Lb1.setText(changeNumb(card[3]));
			Lbs1.setText(tempSuite[3]);
		});
		pause4.play();
		pause4.setOnFinished(e ->{ 
			Lb2.setText(changeNumb(card[4]));
			Lbs2.setText(tempSuite[4]);
		});
		int sec = 6;
		if(card[2] != 0) {
			pause5.play();
			pause5.setOnFinished(e ->{ 
				
				Lp3.setText(changeNumb(card[2]));
				Lps3.setText(tempSuite[2]);
			
			});
		} else {
			sec = 5;
		}
		
		PauseTransition pause6 = new PauseTransition(Duration.seconds(sec));
		if(card[5] != 0) {
			pause6.play();
			pause6.setOnFinished(e ->{ 
				
				Lb3.setText(changeNumb(card[5]));
				Lbs3.setText(tempSuite[5]);
				
			});
		}
		
	}
	public String changeNumb(int card) {
		
		if(card == 1) {
			return "A";
		} else if(card > 1 && card < 11 ) {
			return Integer.toString(card);
		} else if (card == 11) {
			return "J";
		} else if(card == 12) {
			return "Q";
		}else if(card == 13) {
			return "K";
		}
		return null;
	}
	public void changeColor(String[] card) {
		if(card[0].equals("hearts") || card[0].equals("diamonds")) {
			Lps1.setTextFill(Color.RED);
			Lp1.setTextFill(Color.RED);
		}
		if(card[1].equals("hearts") || card[1].equals("diamonds")) {
			Lps2.setTextFill(Color.RED);
			Lp2.setTextFill(Color.RED);
		}
		if(card[2].equals("hearts") || card[2].equals("diamonds")) {
			Lps3.setTextFill(Color.RED);
			Lp3.setTextFill(Color.RED);
		}
		if(card[3].equals("hearts") || card[3].equals("diamonds")) {
			Lbs1.setTextFill(Color.RED);
			Lb1.setTextFill(Color.RED);
		}
		if(card[4].equals("hearts") || card[4].equals("diamonds")) {
			Lbs2.setTextFill(Color.RED);
			Lb2.setTextFill(Color.RED);
		}
		if(card[5].equals("hearts") || card[5].equals("diamonds")) {
			Lbs3.setTextFill(Color.RED);
			Lb3.setTextFill(Color.RED);
		}
		
	}
}
