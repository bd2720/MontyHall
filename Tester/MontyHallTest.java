package MontyHall.Tester;

import java.util.Scanner;

import MontyHall.MontyHallProblem.*;

public class MontyHallTest {
  static final int NUM_DOORS = 4;
  
  public static void main(String[] args) {
    MontyHallProblem mhp = new MontyHallProblem(NUM_DOORS);
    Scanner sc = new Scanner(System.in);

    try {
      for (int p = 0; p < mhp.getNumPhases(); p++) {
        System.out.println(mhp.getDoorsString());
        int initialChoice = sc.nextInt();
        mhp.chooseDoor(initialChoice);
      }
      // Game is over
      System.out.println(mhp.getDoorsString());
      System.out.println("You " + (mhp.wonPrize() ? "Win" : "Lose") + "!");
    } catch(MontyHallException mhe) {
      System.out.println("Error during MHP Test: " + mhe.getMessage());
    }
    sc.close();
  }
}
