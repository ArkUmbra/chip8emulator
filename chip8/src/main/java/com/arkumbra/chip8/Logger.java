package com.arkumbra.chip8;


public class Logger {
  private final Class clazz;

  public Logger(Class clazz) {
    this.clazz = clazz;
  }

  public void opcodePerCycle(String msg) {
    print(msg);
  }

  public void debug(String msg) {
      print(msg);
  }

  private void print(String msg) {
//    System.out.println(clazz.getName() + " - " + msg);
  }

}
