package com.arkumbra.chip8.machine;

import com.arkumbra.chip8.MemoryImpl;
import java.util.Stack;

public class ProgramCounterImpl implements ProgramCounter, Dumpable {

  private Stack<Counter> stack = new Stack<>();

  private Breakpoint breakpoint = new Breakpoint();
  private boolean skipNextInstruction = false;
  private boolean setGotoPosition = false;
  private boolean doSingleStep = false;

  public ProgramCounterImpl() {
    Counter counter = new Counter();
    stack.push(counter);
  }

  @Override
  public void increment() {
    if (breakpoint.isBreakpointActive()) {
      breakpoint.blockUntilBreakpointUnset();
    }

    if (setGotoPosition) {
      setGotoPosition = false;
      return;
    }

    Counter counter = stack.peek();
    counter.position += (this.skipNextInstruction) ? 4 : 2;
    this.skipNextInstruction = false;

    if (isDoSingleStep()) {
      breakpoint.freeze();
    }
  }

  @Override
  public void skipNextInstruction() {
    this.skipNextInstruction = true;
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
  public void toggleFreezeExecution() {
    breakpoint.toggleFreeze();
  }


  @Override
  public void step() {
    synchronized (this) {
      this.doSingleStep = true;
    }
  }

  private void disableSingleStep() {
    synchronized (this) {
      this.doSingleStep = false;
    }
  }

  private boolean isDoSingleStep() {
    synchronized (this) {
      return doSingleStep;
    }
  }



  @Override
  public String dump() {
    return stack.toString();
  }

  class Counter {
    private int position = MemoryImpl.RESERVED;

    @Override
    public String toString() {
      final StringBuilder sb = new StringBuilder("Counter{");
      sb.append("position=").append(position);
      sb.append(", skipNextInstruction=").append(skipNextInstruction);
      sb.append('}');
      return sb.toString();
    }
  }

  class Breakpoint {
    private boolean pauseExecution = false;

    void toggleFreeze() {
      synchronized (this) {
        this.pauseExecution = !pauseExecution;
      }
    }

    void freeze() {
      synchronized (this) {
        this.pauseExecution = true;
      }
    }

    void unfreeze() {
      synchronized (this) {
        this.pauseExecution = false;
      }
    }

    boolean isBreakpointActive() {
      return pauseExecution;
    }

    void blockUntilBreakpointUnset() {

      while (true) {
        synchronized (this) {
          if (! pauseExecution) {
            break;
          }
        }

        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
