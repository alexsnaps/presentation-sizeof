package fr.devoxx.sizeof;

import org.junit.Test;

/**
 * @author Alex Snaps
 */
public class MemoryCheck {

  static class A {
    byte a0,a1,a2,a3,a4,a5,a6,a7,a8,a9,
         b0,b1,b2,b3,b4,b5,b6,b7,b8,b9,
         c0,c1,c2,c3,c4,c5,c6,c7,c8,c9,
         d0,d1,d2,d3,d4,d5,d6
        ;
  }

  @Test
  public void measureHeapConsumption() {

    for(int i = 0; i < 5; i++) {
      gcWholeLot();
      long before = memoryUsed();
      long after = memoryUsed();
      Object someRef = new A();
      Object someOtherRef = null;

      before = memoryUsed();
      gcWholeLot();
      someOtherRef = new A();
      gcWholeLot();
      after = memoryUsed();
      System.out.println(after - before + " bytes used afaict!");
    }
  }

  private void gcWholeLot() {
    System.gc(); System.gc(); System.gc(); System.gc(); System.gc(); System.gc(); System.gc(); System.gc(); System.gc();
  }

  private long memoryUsed() {
    return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
  }
}
