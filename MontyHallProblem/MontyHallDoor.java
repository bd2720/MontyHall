package MontyHall.MontyHallProblem;

public final class MontyHallDoor {
  private boolean isOpen = false;
  private boolean isPrize = false;

  public MontyHallDoor() {
  }

  public MontyHallDoor(boolean prize) {
    isPrize = prize;
  }

  public boolean isOpen() {
    return isOpen;
  }

  protected void open() {
    isOpen = true;
  }

  public boolean getPrize() throws MontyHallException {
    if (!isOpen) {
      throw new MontyHallException("This door is closed.");
    }
    return isPrize;
  }

  protected boolean hasPrize() {
    return isPrize;
  }

  @Override
  public String toString() {
    if (!isOpen) {
      return "D";      
    }
    return hasPrize() ? "$" : "x";
  }

  /** 
   * If closedStr is provided, show it when the door is closed
   */
  public String toString(String closedStr) {
    return isOpen ? toString() : closedStr; 
  }
}
