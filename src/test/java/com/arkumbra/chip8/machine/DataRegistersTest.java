package com.arkumbra.chip8.machine;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class DataRegistersTest {

  private DataRegisters sut;

  @Before
  public void setUp() {
    this.sut = new DataRegistersImpl();
  }

  @Test
  public void testRegistersAreInitialiasedToZero() {
    char zero = (char)0;

    for (RegisterLabel registerLabel : RegisterLabel.values()) {

      DataRegister dataRegister = sut.getRegister(registerLabel);
      assertEquals(zero, dataRegister.get());
    }
  }

}
