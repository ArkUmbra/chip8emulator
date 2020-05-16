package com.arkumbra.chip8;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class JavaByteWideningNarrowingTest {

  @Test
  public void test() {
    char c1 = 0x00E0;
    char c2 = 0x00E0;

    assertEquals(c1, c2);

    byte left = 0x00;
    char right = 0xE0;
    char opCode = (char) ((left << 8) | right & 0xFF);

    assertEquals(opCode, c1);
  }
}
