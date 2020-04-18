package com.arkumbra.chip8;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    short opCodeLeft = 0xA2;  // binary 10100010
    short opCodeRight = 0xF0; // binary 11110000
    int expectedOpCode = 0xA2F0;

    System.out.println("Left   " + Integer.toBinaryString(opCodeLeft));
    System.out.println("Right          " + Integer.toBinaryString(opCodeRight));

    short[] memory = new short[] {
        opCodeLeft,
        opCodeRight
    };

    systemUnderTest.load(memory);

    ProgramCounter pgMock = mock(ProgramCounter.class);
    when(pgMock.getPosition()).thenReturn(0);

    int resultOpCode = systemUnderTest.readRawOpCode(pgMock);
    System.out.println("Result " + Integer.toBinaryString(resultOpCode));

    assertEquals(expectedOpCode, resultOpCode);
  }

}
