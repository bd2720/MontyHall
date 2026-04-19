package MontyHall.Tester;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;
import MontyHall.MontyHallProblem.*;

/**
 * Simulates many Monty Hall Problems in a row.
 * Customize the number of doors, number of games, and the strategy.
 */
public class MontyHallSimulator {
  /*** PARAMETERS ***/
  public static final int NUM_DOORS = 50;
  public static final int NUM_GAMES = 10000;
  public static final MontyHallStrategy strategy = MontyHallStrategy.SMART_SWITCH;

  public static void main(String[] args) {
    System.out.println("Simulating " + NUM_GAMES + " Monty Hall Problems...");
    MontyHallProblem mhp = new MontyHallProblem(NUM_DOORS);
    int wins = 0;
    try {
      for (int game = 0; game < NUM_GAMES; game++) {
        // initial phase (no strategy)
        int currChoice = (int)(Math.random() * mhp.getNumDoors());
        mhp.chooseDoor(currChoice);
        // need a reference for prevChoice, since it's captured by the lambda and dynamic
        // https://stackoverflow.com/questions/34865383/variable-used-in-lambda-expression-should-be-final-or-effectively-final
        final AtomicInteger prevChoiceRef = new AtomicInteger(currChoice);
        // tracking for SMART_SWITCH strategy
        final HashSet<Integer> prevChoiceSet = new HashSet<Integer>();
        final ArrayList<Integer> prevChoices = new ArrayList<Integer>(NUM_DOORS);
        prevChoiceSet.add(currChoice);
        prevChoices.add(currChoice);
        // subsequent phases (strategy)
        for (int p = 1; p < mhp.getNumPhases(); p++) {
          // choose door after the host opens one
          switch(strategy) {
            case SMART_SWITCH:
              // first, try to switch to another closed door never picked before
              currChoice = mhp.getRandomDoorSatisfying((d, i) -> !prevChoiceSet.contains(i) && !d.isOpen());
              if (currChoice < 0) {
                // if not found, switch to the most recently picked door
                for (int i = prevChoices.size() - 1; i >= 0; i--) {
                  int choice = prevChoices.get(i);
                  var door = mhp.getDoor(choice);
                  if (choice != prevChoiceRef.get() && !door.isOpen()) {
                    currChoice = choice;
                    break;
                  }
                }
              }
              break;
            case SWITCH:
              // can switch to any other closed door
              currChoice = mhp.getRandomDoorSatisfying((d, i) -> i != prevChoiceRef.get() && !d.isOpen());
              break;
            case RANDOM:
              // can switch to any closed door, including current
              currChoice = mhp.getRandomDoorSatisfying((d, i) -> !d.isOpen());
              break;
            case STAY:
            default:
              // do nothing, since currChoice is already set to prevChoice
              break;
          }
          mhp.chooseDoor(currChoice);
          prevChoiceRef.set(currChoice);
          prevChoiceSet.add(currChoice);
          prevChoices.add(currChoice);
        }
        // game is over, check win
        wins += mhp.wonPrize() ? 1 : 0;
        // reset the game
        mhp.reset();
      }
    } catch (MontyHallException mhe) {
      System.out.println("Error during MHP Simulation: " + mhe.getMessage());
    }
    double winFraction = wins / (double) NUM_GAMES;
    System.out.println("Won " + wins + " / " + NUM_GAMES + " games (" + winFraction + ")");
  }
}
