package com.arkumbra.chip8.opcode;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.arkumbra.chip8.screen.Screen;
import org.junit.Before;
import org.junit.Test;

public class Op00E0Test {

  private Op00E0 sut;

  @Before
  public void setup() {
    this.sut = new Op00E0();
  }

  @Test
  public void testClearsScreen() {
    Screen screen = mock(Screen.class);

    sut.execute("00E0", screen, null);

    verify(screen, times(1)).clearScreen();
  }

}
