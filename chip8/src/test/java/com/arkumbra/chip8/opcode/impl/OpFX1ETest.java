package com.arkumbra.chip8.opcode.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.arkumbra.chip8.TestUtils;
import com.arkumbra.chip8.machine.DataRegister;
import com.arkumbra.chip8.machine.IndexRegister;
import com.arkumbra.chip8.machine.Machine;
import com.arkumbra.chip8.machine.RegisterLabel;
import com.arkumbra.chip8.machine.Timer;
import org.junit.Before;
import org.junit.Test;

public class OpFX1ETest {

  private OpFX1E sut;
  private Machine machineMock;

  @Before
  public void setup() {
    this.sut = new OpFX1E();
    this.machineMock = TestUtils.mockMachineAndParts();
  }

  /**
   * Adds VX to I. VF is set to 1 when there is a range overflow (I+VX>0xFFF), and to 0 when there isn't.
   */
  @Test
  public void testExecute_addDataRegisterXToIndexRegister_andWhenRolloverSetVFto1() {
    char inputOpCode = 0xFE1E;
    char currentERegisterValue = 0x01;
    boolean iRegisterOverflowsBecauseOfAdd = true;

    DataRegister dataRegisterE = mock(DataRegister.class);
    when(dataRegisterE.get())
        .thenReturn(currentERegisterValue);
    when(machineMock.getRegisters().getRegister(RegisterLabel.VE))
        .thenReturn(dataRegisterE);
    DataRegister dataRegisterFOverflow = mock(DataRegister.class);
    when(machineMock.getRegisters().getRegister(RegisterLabel.VF))
        .thenReturn(dataRegisterFOverflow);

    when(machineMock.getIndexRegister().add(anyInt()))
        .thenReturn(iRegisterOverflowsBecauseOfAdd);


    // Execute
    char opCodeData = sut.getBitMask().applyMask(inputOpCode);
    sut.execute(opCodeData, machineMock);

    verify(dataRegisterE, times(1)).get();
    verify(dataRegisterFOverflow, times(1))
        .set((char)1);
  }

  @Test
  public void testExecute_addDataRegisterXToIndexRegister_andWhenNoRolloverSetVFto0() {
    char inputOpCode = 0xFE1E;
    char currentERegisterValue = 0x01;
    boolean iRegisterOverflowsBecauseOfAdd = false;

    DataRegister dataRegisterE = mock(DataRegister.class);
    when(dataRegisterE.get())
        .thenReturn(currentERegisterValue);
    when(machineMock.getRegisters().getRegister(RegisterLabel.VE))
        .thenReturn(dataRegisterE);
    DataRegister dataRegisterFOverflow = mock(DataRegister.class);
    when(machineMock.getRegisters().getRegister(RegisterLabel.VF))
        .thenReturn(dataRegisterFOverflow);

    when(machineMock.getIndexRegister().add(anyInt()))
        .thenReturn(iRegisterOverflowsBecauseOfAdd);


    // Execute
    char opCodeData = sut.getBitMask().applyMask(inputOpCode);
    sut.execute(opCodeData, machineMock);

    verify(dataRegisterE, times(1)).get();
    verify(dataRegisterFOverflow, times(1))
        .set((char)0);
  }

}
