package com.arkumbra.chip8.machine;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ProgramCounterTest {

  private ProgramCounter sut;

  @Before
  public void setUp() {
    this.sut = new ProgramCounterImpl();
  }

  @Test
  public void test() {
    sut.goTo(2);
    assertEquals(2, sut.getPosition());
    sut.increment();
    assertEquals(4, sut.getPosition());

    sut.push(20);
    assertEquals(20, sut.getPosition());
    sut.increment();
    assertEquals(22, sut.getPosition());

    sut.pop();
    assertEquals(4, sut.getPosition());
  }

}
