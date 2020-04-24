package com.arkumbra.chip8.machine;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class IndexRegisterTest {

  private IndexRegister sut;

  @Before
  public void setUp() {
    this.sut = new IndexRegisterImpl();
  }

  @Test
  public void testSimpleSetAndGet() {
    char val = 255;

    sut.set(val);

    assertEquals(val, sut.get());
  }

  @Test
  public void testOverflowOWhenRunsOver0xFFF() {
    char val = 0xFFF;
    System.out.println((int)val);

    sut.set(val);
    assertEquals(val, sut.get());

    sut.add(1);
    assertEquals(0, sut.get());
  }

}
