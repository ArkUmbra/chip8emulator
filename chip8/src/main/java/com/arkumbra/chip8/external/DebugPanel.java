package com.arkumbra.chip8.external;

import com.arkumbra.chip8.machine.DataRegisters;
import com.arkumbra.chip8.machine.DelayTimer;
import com.arkumbra.chip8.machine.KeyLabel;
import com.arkumbra.chip8.machine.KeyPressListener;
import com.arkumbra.chip8.machine.ProgramCounter;
import com.arkumbra.chip8.machine.RegisterLabel;
import com.arkumbra.chip8.machine.ScreenMemory;
import com.arkumbra.chip8.machine.SoundTimer;
import com.arkumbra.chip8.machine.TickUpdateable;
import com.arkumbra.chip8.machine.Timer;
import com.arkumbra.chip8.machine.Timers;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DebugPanel extends JPanel implements TickUpdateable {

  private static final Dimension SCREEN_SIZE = new Dimension(100, 500);

  private final Map<RegisterLabel, Label> dataDisplayLabelsPerRegister = new HashMap<>();

  private final DataRegisters dataRegisters;
  private final ProgramCounter programCounter;

  public DebugPanel(DataRegisters dataRegisters, ProgramCounter pc, Timer soundTimer, Timer delayTimer) {

    this.dataRegisters = dataRegisters;
    this.programCounter = pc;

    GridLayout registersLayout = new GridLayout(9, 4);
    this.setLayout(registersLayout);

    initUI();

    JFrame frame = new JFrame("Debugger");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(this);
    frame.setSize(SCREEN_SIZE);
    frame.setVisible(true);
    frame.pack();
  }


  private void initUI() {

    for (RegisterLabel register : RegisterLabel.values()) {
      Label uiLabel = new Label(register.name());
      add(uiLabel);


      Label registerDataLabel = new Label("000");
      dataDisplayLabelsPerRegister.put(register, registerDataLabel);
      add(registerDataLabel);
    }

    Button freezeButton = new Button("Pause / Unpause");
    freezeButton.addActionListener(new BreakpointButtonListener(programCounter));
    add(freezeButton);
  }

  private void refreshUi() {
    refreshEachRegisterValue();
  }

  private void refreshEachRegisterValue() {
    for (RegisterLabel register : RegisterLabel.values()) {
      Label registerData = dataDisplayLabelsPerRegister.get(register);
      char data = dataRegisters.getRegister(register).get();

      String hex = Integer.toHexString(data);
      while (hex.length() < 3) {
        hex = " " + hex;
      }
      registerData.setText(hex);
    }
  }

  @Override
  public Dimension getPreferredSize() {
    return SCREEN_SIZE;
  }

  @Override
  public void tick() {
    refreshUi();
  }

  static class BreakpointButtonListener implements ActionListener {
    private final ProgramCounter programCounter;

    public BreakpointButtonListener(ProgramCounter programCounter) {
      this.programCounter = programCounter;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      System.out.println(e.getActionCommand());
      programCounter.toggleFreezeExecution();
    }
  }

}



