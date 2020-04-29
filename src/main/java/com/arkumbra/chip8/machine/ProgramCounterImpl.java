package com.arkumbra.chip8.machine;

import com.arkumbra.chip8.MemoryImpl;
import java.util.Stack;

public class ProgramCounterImpl implements ProgramCounter, Dumpable {

  private Stack<Counter> stack = new Stack<>();

  private boolean skipNextInstruction = false;
  private boolean setGotoPosition = false;

  public ProgramCounterImpl() {
    Counter counter = new Counter();
    stack.push(counter);
  }

  @Override
  public void increment() {
    if (setGotoPosition) {
      setGotoPosition = false;
      return;
    }
    Counter counter = stack.peek();

    counter.position += (counter.skipNextInstruction) ? 4 : 2;
//    counter.position += (this.skipNextInstruction) ? 4 : 2;
//    this.skipNextInstruction = false;
    counter.skipNextInstruction = false;
  }

  @Override
  public void skipNextInstruction() {
    stack.peek().skipNextInstruction = true;
//    this.skipNextInstruction = true;
  }

  @Override
  public int getPosition() {
    return stack.peek().position;
  }

  @Override
  public void goTo(int position) {
    stack.peek().position = position;
    this.setGotoPosition = true;
  }

  @Override
  public void push(int val) {
    Counter counter = new Counter();
    counter.position = val;
    stack.push(counter);
  }

  @Override
  public void pop() {
    stack.pop();
  }

  @Override
  public String dump() {
    return stack.toString();
  }

  class Counter {
    public int position = MemoryImpl.RESERVED;
    public boolean skipNextInstruction = false;

    @Override
    public String toString() {
      final StringBuilder sb = new StringBuilder("Counter{");
      sb.append("position=").append(position);
      sb.append(", skipNextInstruction=").append(skipNextInstruction);
      sb.append('}');
      return sb.toString();
    }
  }
}
