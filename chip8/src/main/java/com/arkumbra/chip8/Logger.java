package com.arkumbra.chip8;

import com.arkumbra.chip8.machine.IndexRegisterImpl;
import com.arkumbra.chip8.machine.ScreenImpl;
import com.arkumbra.chip8.opcode.impl.Op2NNN;
import java.util.Set;

public class Logger {
  private static final Set<Class> disabled = Set.of(
      IndexRegisterImpl.class,
      ScreenImpl.class,
      Op2NNN.class
  );

  private final Class clazz;

  public Logger(Class clazz) {
    this.clazz = clazz;
  }

  public void opcodePerCycle(String msg) {
    print(msg);
  }

  public void debug(String msg) {
    if (! disabled.contains(clazz)) {
//      print(msg);
    }
  }

  private void print(String msg) {
    System.out.println(clazz.getName() + " - " + msg);
  }

}
