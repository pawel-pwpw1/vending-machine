package vendingmachine.domain;

public class Coin {

  private final int weight;

  private final int size;

  public Coin(int weight, int size) {
    this.weight = weight;
    this.size = size;
  }

  public int getWeight() {
    return weight;
  }

  public int getSize() {
    return size;
  }
}
