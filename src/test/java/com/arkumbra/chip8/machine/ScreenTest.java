package com.arkumbra.chip8.machine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ScreenTest {

  @Test
  public void testDraw() {
    ScreenImpl screen = new ScreenImpl();

    boolean flipped = screen.draw((byte) 0b11111111, 2, 6);
    System.out.println(screen.dump());
    assertFalse(flipped);

    flipped = screen.draw((byte) 0b11111101, 2, 6);
    System.out.println(screen.dump());
    assertTrue(flipped);

    System.out.println(screen.dump());
  }

}
