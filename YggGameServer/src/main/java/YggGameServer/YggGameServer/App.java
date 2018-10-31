package YggGameServer.YggGameServer;

import java.awt.List;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		String[] BoxContent = { "1X€100", "2X€20", "5X€5", "1XExtra Life", "3XGame Over Sign" };
		String[] AdditionalReward = { "€5", "€10", "€20", "Second Chance" };
		
	
		// System.out.println(getRewardFromBoxOpened(BoxContent));
		System.out.println(expectationRewardOfRoundsOfGamePlayed(BoxContent, AdditionalReward, 2));

	}

	// Question 1 from assignment simulation of rounds of Game
	static double expectationRewardOfRoundsOfGamePlayed(String[] BoxContent, String[] AdditionalReward, int nRounds) {
		if (BoxContent == null)
			throw new NullPointerException("Box reward values is empty");
		if (AdditionalReward.length == 0)
			throw new IllegalArgumentException("Bonus reward value is empty.");

		boolean isGameOver = false;
		String[] openedBox = BoxContent;
		String[] bonuReward = AdditionalReward;

		ArrayList<String> openBoxreward = new ArrayList<String>();
		ArrayList<String> AdditionalrewardGained = new ArrayList<String>();
		double expectation =0;
		
		// treat the game play here
		for (int i = 0; i < nRounds; i++) {
			int r1 = (int) (Math.random() * 5);
			String rewardFromOpenBox= openedBox[r1];
			//String rewardFromOpenBox = getRewardFromBoxOpened(BoxContent);

			switch (rewardFromOpenBox) {
			case "1X€100":
				openBoxreward.add(rewardFromOpenBox);
				break;
			case "2X€20":
				openBoxreward.add(rewardFromOpenBox);
				break;
			case "5X€5":
				openBoxreward.add(rewardFromOpenBox);
				break;
			case "1XExtra Life":
				openedBox = Arrays.copyOf(openedBox, 4); // Nullify the game over sign from the opened box until the
															// game rounds finish
				break;
			case "3XGame Over Sign": // first condition game can be over
				isGameOver = true;
				break;
			// Don't add to open box reward again, But stop the looping of Game playing

			default:
			}

		}
		if (isGameOver) {
			
			int r = (int) (Math.random() * 4);
			String BonusReward = bonuReward[r];
			//String BonusReward = getRewardFromBoxOpened(bonuReward);
			switch (BonusReward) {
			case "€5":
				AdditionalrewardGained.add(BonusReward);
				expectation = expectationRewardOfRoundsOfGamePlayed(BoxContent, AdditionalReward, nRounds);
				break;
			case "€10":
				AdditionalrewardGained.add(BonusReward);
				expectation = expectationRewardOfRoundsOfGamePlayed(BoxContent, AdditionalReward, nRounds);
				break;
			case "€20":
				AdditionalrewardGained.add(BonusReward);
				expectation = getExpectedValue(openBoxreward, AdditionalrewardGained);
				break;
			case "Second Chance":
				bonuReward = Arrays.copyOf(bonuReward, 3); // Nullify the Second Chance from the bonusReward
															// container and start the game all over again.
				expectation = expectationRewardOfRoundsOfGamePlayed(BoxContent, AdditionalReward, nRounds); 
																											
				break;

			default:
				//
			}
			// returning expectation as sum
		}
		return expectation;

	}

	// Question 2 from assignment
	static double getExpectedValue(ArrayList<String> rewardFromOpenedBox, ArrayList<String> AdditionalReward) {
		ArrayList<Integer> finalList = new ArrayList<Integer>();
		double expectedReward = 0;
		// treat the values coming from openBox reward
		for (String st : rewardFromOpenedBox) {
			String[] ar = st.split("X");
			finalList.add(Integer.parseInt(ar[0]) * Integer.parseInt(ar[1].substring(1))); // 2 *€20 change to 2*20 =40
		}
		// treat the values coming from additional reward too
		for (String str : AdditionalReward) {
			finalList.add(Integer.parseInt(str.substring(1))); // €20 change to 20
		}

		// calculate the expected overall reward here
		if (finalList.size() > 0) {
			double probability = (1.0 / (finalList.size()));
			for (Integer fl : finalList) {

				expectedReward += fl * probability;

			}

		}

		return expectedReward;
	}

	
}
