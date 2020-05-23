package com.arkumbra.chip8.machine;

import java.nio.ByteBuffer;
import java.util.Stack;

public class ProgramCounterImpl implements ProgramCounter, SerializableData, Dumpable {
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
    this.setGotoPosition = true;
  }

  @Override
  public void pop() {
    stack.pop();
    this.setGotoPosition = false;
  }

  @Override
  public void toggleFreezeExecution() {
    breakpoint.toggleFreeze();
  }

  @Override
  public void freeze() {
    breakpoint.freeze();
  }

  @Override
  public void unfreeze() {
    breakpoint.unfreeze();
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

  @Override
  public byte[] serialize() {
    byte skipNext = (byte) (skipNextInstruction ? 1 : 0);
    byte goToSet = (byte) (setGotoPosition ? 1 : 0);
    byte stackSize = (byte) stack.size();

    ByteBuffer byteBuffer = ByteBuffer.allocate(1 + 1 + 1 + (stackSize * Integer.BYTES));

    byteBuffer.put(skipNext);
    byteBuffer.put(goToSet);
    byteBuffer.put(stackSize);

    // flip the stack around so that they can be deserialised easier / as-is order
    Stack<Counter> reversed = new Stack<>();
    for (Counter counter : stack) {
      reversed.push(counter);
    }

    for (Counter counter : reversed) {
      byteBuffer.putInt(counter.position);
    }

    return byteBuffer.array();
  }

  @Override
  public void deserialize(byte[] data) {
    ByteBuffer byteBuffer = ByteBuffer.wrap(data);

    this.skipNextInstruction = byteBuffer.get() == 1;
    this.setGotoPosition = byteBuffer.get() == 1;

    Stack<Counter> newStack = new Stack<>();
    byte stackSize = byteBuffer.get();
    for (int i = 0; i < stackSize; i++) {
      newStack.push(new Counter(byteBuffer.getInt()));
    }

    this.stack = newStack;
  }

  class Counter {
    private int position;

    public Counter() {
      this.position  = RamImpl.RESERVED;
    }

    public Counter(int position) {
      this.position = position;
    }

    @Override
    public String toString() {
      final StringBuilder sb = new StringBuilder("Counter{");
      sb.append("position=").append(position);
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
