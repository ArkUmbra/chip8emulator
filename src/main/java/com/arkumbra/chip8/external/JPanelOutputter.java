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
  private static final int KEYPRESS_AREA_STARTX = GAME_WIDTH + 20;
  private static final int KEYPRESS_AREA_STARTY = 10;
  private static final int KEYPRESS_AREA_START_COLUMN_WIDTH = 30;
  private static final int KEYPRESS_AREA_START_COLUMN_HEIGHT = 30;
  private static final int KEYPRESS_AREA_START_BOX_SIZE = 20;

  private static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH + KEY_PRESS_PANEL_WIDTH, GAME_HEIGHT);



  private ScreenMemory screenMemory;
  private KeyPressListener keyPressListener;

  public JPanelOutputter() {
    JFrame frame = new JFrame("Chip 8 Emulator");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(this);
    frame.setSize(SCREEN_SIZE);
    frame.setVisible(true);
    frame.addKeyListener(this);
    frame.pack();
  }

  
  @Override
  public void paintComponent(Graphics g) {
    g.setColor(Color.black);
    g.fillRect(0, 0, SCREEN_SIZE.width, SCREEN_SIZE.height);

    drawGameArea(g);
    drawPressedKeysArea(g);
  }

  private void drawGameArea(Graphics g) {
    boolean[][] pixels = screenMemory.getPixels();

    g.setColor(Color.green);
    for (int y = 0; y < pixels[0].length; y++) {
      for (int x = 0; x < pixels.length; x++) {
        if (pixels[x][y]) {
          g.fillRect(x*PIXELSIZE, y*PIXELSIZE, PIXELSIZE, PIXELSIZE);
        }
      }
    }
  }

  private void drawPressedKeysArea(Graphics g) {
    int i = 0;
    int col = 0, row = 0;
    
    for (KeyLabel keyLabel : KeyLabel.values()) {
      boolean isPressed = keyPressListener.isPressed(keyLabel);

      int xPos = KEYPRESS_AREA_STARTX + (col * KEYPRESS_AREA_START_COLUMN_WIDTH);
      int yPos = KEYPRESS_AREA_STARTY + (row * KEYPRESS_AREA_START_COLUMN_HEIGHT);

      g.setColor(Color.blue);
      g.drawRect(xPos, yPos, KEYPRESS_AREA_START_BOX_SIZE, KEYPRESS_AREA_START_BOX_SIZE);
      g.setColor((isPressed) ? Color.green : Color.black);
      g.fillRect(xPos + 1, yPos + 1, KEYPRESS_AREA_START_BOX_SIZE - 2, KEYPRESS_AREA_START_BOX_SIZE
          - 2);

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
  public void init(ScreenMemory screenMemory, KeyPressListener keyPressListener) {
    this.screenMemory = screenMemory;
    this.keyPressListener = keyPressListener;

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


