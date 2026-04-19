package MontyHall.MontyHallProblem;

/**
 * 0-indexed model of the Monty Hall Problem with any number of doors.
 */
public class MontyHallProblem {
  // problem setup
  private int numDoors;
  private MontyHallDoor[] doors;
  private int numPhases;

  // ongoing state
  private int chosenDoorIndex;
  private int currPhase;
  
  public MontyHallProblem(int n, int p) {
    numDoors = n;
    doors = new MontyHallDoor[numDoors];
    numPhases = p;
    reset();
  }

  public MontyHallProblem(int n) {
    numDoors = n;
    doors = new MontyHallDoor[numDoors];
    numPhases = n - 1;
    reset();
  }

  public MontyHallProblem() {
    numDoors = 3;
    doors = new MontyHallDoor[numDoors];
    numPhases = 2;
    reset();
  }

  public void reset() {
      // randomly choose the prize door
      int prizeDoorIndex = (int)(Math.random() * numDoors);
      // initialize doors
      for (int i = 0; i < numDoors; i++) {
        boolean hasPrize = i == prizeDoorIndex;
        doors[i] = new MontyHallDoor(hasPrize);
      }
      currPhase = 0;
  }

  public int getNumDoors() {
    return numDoors;
  }

  public MontyHallDoor[] getDoors() {
    return doors;
  }
  
  public int getNumPhases() {
    return numPhases;
  }

  public String getDoorsString() {
    String retStr = doors[0].toString(Integer.toString(0));
    for(int i = 1; i < numDoors; i++) {
      retStr += " " + doors[i].toString(Integer.toString(i));
    }
    return retStr;
  }

  public MontyHallDoor getDoor(int index) throws MontyHallException {
    if (index < 0 || index > numDoors) {
      throw new MontyHallException("Index " + index + " out of range.");
    }
    return doors[index];
  }

  public void chooseDoor(int index) throws MontyHallException {
    // make sure the door index is in range
    if (index < 0 || index >= numDoors) {
      throw new MontyHallException("The chosen door " + index + " is out of range.");
    }
    // store the choice
    chosenDoorIndex = index;

    if (currPhase < numPhases - 1) {
      // Non-Final Phases: host must open a closed, non-selected door with no prize behind it
      int candidateDoorIndex = getRandomDoorSatisfying((d, i) -> canHostOpenDoor(i));
      if (candidateDoorIndex < 0) {
        throw new MontyHallException("The host cannot open any more doors.");
      }
      doors[candidateDoorIndex].open();
    } else {
      // Final Phase: host opens all doors
      for (MontyHallDoor d : doors) {
        d.open();
      }
    }
    // advance to next phase
    currPhase++;
  }

  public boolean wonPrize() {
    return (currPhase == numPhases) && doors[chosenDoorIndex].hasPrize(); 
  }

  private boolean canHostOpenDoor(int index) {
    MontyHallDoor d = doors[index];
    return index != chosenDoorIndex && !d.isOpen() && !d.hasPrize();
  }

  /**
   * Returns a random door that satisfies the provided predicate.
   */
  public int getRandomDoorSatisfying(MontyHallPredicate p) {
    int candidateDoors = 0;
    for (int i = 0; i < numDoors; i++) {
      MontyHallDoor d = doors[i];
      if (!p.test(d, i)) continue;
      candidateDoors++;
    }
    if (candidateDoors == 0) {
      return -1; // no door found
    }
    // select the door
    int hostDoorCount = (int)(Math.random() * candidateDoors);
    for (int i = 0; i < numDoors; i++) {
      MontyHallDoor d = doors[i];
      if (!p.test(d, i)) continue;
      hostDoorCount--;
      if (hostDoorCount >= 0) continue;
      return i;
    }
    return -1;
  }

  /**
   * Returns the index of the first door that satisfies the provided predicate.
   */
  public int getFirstDoorSatisfying(MontyHallPredicate p) {
    for (int i = 0; i < numDoors; i++) {
      MontyHallDoor d = doors[i];
      if (!p.test(d, i)) continue;
      // select the door
      return i; 
    }
    return -1; // no door found
  }
}
