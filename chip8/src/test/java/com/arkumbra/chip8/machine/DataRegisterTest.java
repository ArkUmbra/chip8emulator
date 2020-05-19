package com.arkumbra.chip8.machine;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class DataRegisterTest {

  private DataRegister sut;

  @Before
  public void setUp() {
    this.sut = new DataRegisterImpl();
  }

  @Test
  public void testSimpleSetAndGet() {
    char val = 255;

    sut.set(val);

    assertEquals(val, sut.get());
  }

  @Test
  public void testOverflowOWhenRunsOver255() {
    char one = (char)1;

    char val = 254;

    sut.set(val);
    assertEquals(val, sut.get());

    sut.add(one);
    assertEquals((char)255, sut.get());

    sut.add(one);
    assertEquals((char)0, sut.get());
  }

}
