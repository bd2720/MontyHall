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

  /**
   * Opens a door. This method is protected because only the HOST can open doors.
   */
  protected void open() {
    isOpen = true;
  }

  public boolean getPrize() throws MontyHallException {
    if (!isOpen) {
      throw new MontyHallException("This door is closed.");
    }
    return isPrize;
  }

  /**
   * Checks if a door has a prize, even if it is closed.
   * This method is protected because only the HOST can know what's behind closed doors.
   */
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
