package com.arkumbra.chip8.external;

import com.arkumbra.chip8.machine.KeyLabel;
import com.arkumbra.chip8.machine.KeyPressListener;
import com.arkumbra.chip8.machine.ScreenMemory;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class JPanelOutputter extends JPanel implements ScreenOutputter, KeyListener {

  private static final int PIXELSIZE = 10;
  private static final int WIDTH = 64 * PIXELSIZE;
  private static final int HEIGHT = 32 * PIXELSIZE;

  private ScreenMemory screenMemory;
  private KeyPressListener keyPressListener;
  private static final Boolean LOCK = Boolean.FALSE;

  public JPanelOutputter(KeyPressListener keyPressListener) {
    this.keyPressListener = keyPressListener;

    JFrame frame = new JFrame("Oval Sample");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(this);
    frame.setSize(WIDTH, HEIGHT);
    frame.setVisible(true);
    frame.addKeyListener(this);
  }

  @Override
  public void drawFrame(ScreenMemory screenMemory) {
//    synchronized (LOCK) {
      this.screenMemory = screenMemory;
//    }

    repaint();
  }

  int counter;
  @Override
  public void paintComponent(Graphics g) {
    g.setColor(Color.black);
    g.fillRect(0, 0, WIDTH, HEIGHT);

    g.setColor(Color.green);
//    g.drawOval(0, 0, width, height);

    String counterNumber = "" + counter;
    g.drawString(counterNumber, 30, 30);
    counter++;


    boolean[][] pixels = screenMemory.getPixels();

    for (int y = 0; y < pixels[0].length; y++) {
      for (int x = 0; x < pixels.length; x++) {
        if (pixels[x][y]) {
          g.fillRect(x*PIXELSIZE, y*PIXELSIZE, PIXELSIZE, PIXELSIZE);
        }
      }
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {
    // Don't need?
  }

  @Override
  public void keyPressed(KeyEvent e) {
    char keyChar = e.getKeyChar();
    KeyLabel keyLabel = KeyLabel.toKey(keyChar);
    keyPressListener.keyPressed(keyLabel);
  }

  @Override
  public void keyReleased(KeyEvent e) {
    char keyChar = e.getKeyChar();
    KeyLabel keyLabel = KeyLabel.toKey(keyChar);
    keyPressListener.keyReleased(keyLabel);
  }
}
