import java.io.Serializable;
public class BaccaratInfo implements Serializable{
	

	int PlayerBetOpion;
	double PlayerBetAmount;
	double playerTotalAmount;
	int[] Cards;
	String[] suite;
	boolean isWin;
	String outcome;
	boolean Pquit = false;
	boolean reStart;
}
