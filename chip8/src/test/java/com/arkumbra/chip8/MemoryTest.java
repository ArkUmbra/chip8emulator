package com.arkumbra.chip8;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.arkumbra.chip8.machine.ProgramCounter;
import com.arkumbra.chip8.machine.ProgramCounterImpl;
import org.junit.Before;
import org.junit.Test;

public class MemoryTest {

  private Memory systemUnderTest;

  @Before
  public void setUp() {
    this.systemUnderTest = new MemoryImpl();
  }

  @Test
  public void test() {
    byte opCodeLeft = (byte)0xA2;       // binary 10100010
    byte opCodeRight = (byte)0xF0;      // binary         11110000
    char expectedOpCode = (char)0xA2F0; // binary 1010001011110000

    System.out.println("Left   " + Integer.toBinaryString(opCodeLeft));
    System.out.println("Right          " + Integer.toBinaryString(opCodeRight));

    systemUnderTest.load(new byte[]{opCodeLeft, opCodeRight});

    ProgramCounter pgMock = new ProgramCounterImpl();

    char resultOpCode = systemUnderTest.readRawOpCode(pgMock);

    System.out.println("Result " + Integer.toBinaryString(resultOpCode));

    assertEquals(expectedOpCode, resultOpCode);
  }

}
