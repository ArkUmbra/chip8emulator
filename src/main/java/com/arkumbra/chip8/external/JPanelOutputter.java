package com.arkumbra.chip8.external;

import com.arkumbra.chip8.machine.KeyLabel;
import com.arkumbra.chip8.machine.KeyPressListener;
import com.arkumbra.chip8.machine.ScreenMemory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class JPanelOutputter extends JPanel implements ScreenOutputter, KeyListener {

  private static final int PIXELSIZE = 10;
  private static final int GAME_WIDTH = 64 * PIXELSIZE;
  private static final int GAME_HEIGHT = 32 * PIXELSIZE;

  private static final int KEY_PRESS_PANEL_WIDTH = 80;

  private static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH + KEY_PRESS_PANEL_WIDTH, GAME_HEIGHT);



  private ScreenMemory screenMemory;
  private KeyPressListener keyPressListener;

  public JPanelOutputter(KeyPressListener keyPressListener) {
    this.keyPressListener = keyPressListener;

    JFrame frame = new JFrame("Chip 8 Emulator");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(this);
    frame.setSize(SCREEN_SIZE);
    frame.setVisible(true);
    frame.addKeyListener(this);
    frame.pack();
  }


  int counter;
  @Override
  public void paintComponent(Graphics g) {
    g.setColor(Color.black);
    g.fillRect(0, 0, SCREEN_SIZE.width, SCREEN_SIZE.height);

    g.setColor(Color.green);

    String counterNumber = "" + counter;
    g.drawString(counterNumber, 30, 30);
    counter++;


    boolean[][] pixels = screenMemory.getPixels();

    for (int y = 0; y < pixels[0].length; y++) {
      for (int x = 0; x < pixels.length; x++) {
        if (pixels[x][y]) {
          g.setColor(Color.green);
          g.fillRect(x*PIXELSIZE, y*PIXELSIZE, PIXELSIZE, PIXELSIZE);
//          g.setColor(Color.blue);
//          g.drawString("" + x, x*PIXELSIZE, y*PIXELSIZE);
        }
      }
    }

    drawPressedKeys(g);
  }

  private void drawPressedKeys(Graphics g) {
    int i = 0;
    int col = 0;
    int row = 0;
    int startX = GAME_WIDTH + 20;
    int startY = 10;
    int columnWidth = 30;
    int columnHeight = 30;
    int boxSize = 20;

    for (KeyLabel keyLabel : KeyLabel.values()) {
      boolean isPressed = keyPressListener.isPressed(keyLabel);

      int xPos = startX + (col * columnWidth);
      int yPos = startY + (row * columnHeight);

      g.setColor(Color.blue);
      g.drawRect(xPos, yPos, boxSize, boxSize);
      g.setColor((isPressed) ? Color.green : Color.black);
      g.fillRect(xPos + 1, yPos + 1, boxSize - 2, boxSize - 2);

      g.setColor((isPressed) ? Color.black : Color.blue);
      g.drawString(keyLabel.getKeyName(), xPos + 8, yPos + 15);

      i++;
      row++;
      if (i == 7) {
        row = 0;
        col = 1;
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
    KeyLabel keyLabel = InputToKeyLabelMapper.fromKeyboardButton(keyChar);

    if (keyLabel != null) {
      keyPressListener.keyPressed(keyLabel);
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    char keyChar = e.getKeyChar();
    KeyLabel keyLabel = InputToKeyLabelMapper.fromKeyboardButton(keyChar);

    if (keyLabel != null) {
      keyPressListener.keyReleased(keyLabel);
    }
  }

  @Override
  public void init(ScreenMemory screenMemory) {
    this.screenMemory = screenMemory;

    new Thread(new DrawRunner(this)).start();
  }

  @Override
  public void drawFrame() {
    repaint();
  }

  @Override
  public Dimension getPreferredSize() {
    return SCREEN_SIZE;
  }
}


