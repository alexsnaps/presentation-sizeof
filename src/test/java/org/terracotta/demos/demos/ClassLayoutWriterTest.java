package org.terracotta.demos.demos;

import org.junit.Test;
import org.terracotta.demos.sizeof.ClassLayoutWriter;

import java.io.IOException;

/**
 * @author Alex Snaps
 */
public class ClassLayoutWriterTest {

  static class Foo {
    private int a;
    private long b;
    private byte e;
    private double f;
    private float g;
    private Object o;
    private int c;
    private long d;
  }

  static class Bar extends Foo {
    private long a;
    private boolean b, c, d, e, f, g, h;
    private short i;
  }

  @Test
  public void showClassLayout() throws IOException {
      new ClassLayoutWriter().writeInMemoryClassLayout(new Bar(), true);
  }
}
