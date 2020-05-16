package com.arkumbra.chip8.external;

public class DrawRunner implements Runnable {
  private static final int FPS = 60;
  private static final int MS_PER_FRAME = 1000 / FPS;

  private final JPanelDrawer jPanelDrawer;

  public DrawRunner(JPanelDrawer jPanelDrawer) {
    this.jPanelDrawer = jPanelDrawer;
  }

  @Override
  public void run() {

    while (true) {
      long beforeDrawMs = System.currentTimeMillis();
      jPanelDrawer.drawFrame();
      long afterDrawMs = System.currentTimeMillis();

      long sleepTime = MS_PER_FRAME - (afterDrawMs - beforeDrawMs);
      if (sleepTime < 10) {
        sleepTime = 10;
      }
      try {

        Thread.sleep(sleepTime);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
