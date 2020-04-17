package com.arkumbra.chip8.opcode;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.arkumbra.chip8.ProgramCounter;
import com.arkumbra.chip8.screen.Screen;
import org.junit.Before;
import org.junit.Test;

public class Op00EETest {

  private Op00EE sut;

  @Before
  public void setup() {
    this.sut = new Op00EE();
  }

  @Test
  public void testClearsScreen() {
    ProgramCounter pg = mock(ProgramCounter.class);

    sut.execute("00EE", null, pg);

    verify(pg, times(1)).pop();
  }

}
