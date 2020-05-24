package com.arkumbra.startchip8.gdx.desktop;

import com.arkumbra.chip8.debug.CycleListener;
import com.arkumbra.chip8.debug.Debugger;
import com.arkumbra.chip8.machine.RegisterLabel;
import com.arkumbra.chip8.state.SaveStateHandler;
import com.arkumbra.startchip8.gdx.SaveStateFileManager;
import com.google.common.base.Splitter;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.DefaultCaret;
import org.apache.commons.codec.binary.Hex;

public class DebugPanel implements CycleListener {

  private static final Dimension SCREEN_SIZE = new Dimension(300, 500);

  private final Map<RegisterLabel, Label> dataDisplayLabelsPerRegister = new HashMap<>();
  private JTextArea taRam;
  private JButton btnPauseExecution;
  private JButton btnLoadState;

  private static final String PAUSE = "Pause Execution";
  private static final String RESUME = "Resume Execution";
  private boolean frozen = false;

  private final Debugger debugger;
  private final SaveStateHandler saveStateHandler;
  private final SaveStateFileManager saveStateFileManager;

  public DebugPanel(Debugger debugger, SaveStateHandler saveStateHandler, SaveStateFileManager saveStateFileManager) {
    this.debugger = debugger;
    this.saveStateHandler = saveStateHandler;
    this.saveStateFileManager = saveStateFileManager;

    JPanel basePanel = new JPanel();
    basePanel.setLayout(new BoxLayout(basePanel, BoxLayout.Y_AXIS));

    JPanel topPanel = new JPanel();
    topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
    topPanel.add(createRegisterPanel());
    topPanel.add(createRamPanel());
    basePanel.add(topPanel);

    JPanel bottomPanel = new JPanel();
    bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
    bottomPanel.add(createDebugControlPanel());
    basePanel.add(bottomPanel);


    JFrame frame = new JFrame("Debugger");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(basePanel);
    frame.setSize(SCREEN_SIZE);
    frame.setVisible(true);
    frame.pack();

    debugger.setCycleListener(this);
  }


  private JPanel createRegisterPanel() {
    JPanel dataRegisterPanel = new JPanel();
    dataRegisterPanel.setLayout(new BoxLayout(dataRegisterPanel, BoxLayout.Y_AXIS));
    dataRegisterPanel.setBorder(
        BorderFactory.createEmptyBorder(5, 5, 5, 5)
    );

    JPanel titlePanel = new JPanel();
    titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
    JLabel lblRegisterTitle = new JLabel("Registers");
    titlePanel.add(lblRegisterTitle);
    dataRegisterPanel.add(titlePanel);

    for (RegisterLabel register : RegisterLabel.values()) {
      JPanel singleRegisterPanel = new JPanel();
      singleRegisterPanel.setLayout(new BoxLayout(singleRegisterPanel, BoxLayout.X_AXIS));

      Label uiLabel = new Label(register.name());
      Label registerDataLabel = new Label("000");
      singleRegisterPanel.add(uiLabel);
      singleRegisterPanel.add(registerDataLabel);

      dataDisplayLabelsPerRegister.put(register, registerDataLabel);
      dataRegisterPanel.add(singleRegisterPanel);
    }

    return dataRegisterPanel;
  }

  private JPanel createRamPanel() {
    JPanel ramPanel = new JPanel();
    ramPanel.setLayout(new BoxLayout(ramPanel, BoxLayout.Y_AXIS));
    ramPanel.setPreferredSize(new Dimension(600, 500));
    ramPanel.setBorder(
        BorderFactory.createEmptyBorder(5, 5, 5, 5)
    );

    JPanel titlePanel = new JPanel();
    titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
    JLabel lblRamTitle = new JLabel("RAM");
    titlePanel.add(lblRamTitle);
    ramPanel.add(titlePanel);

    JTextArea textArea = new JTextArea();
    DefaultCaret caret = (DefaultCaret) textArea.getCaret();
    caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
    textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

    byte[] ram = debugger.getRam();
    String hex = Hex.encodeHexString(ram);

    Iterable<String> pieces = Splitter.fixedLength(4).split(hex);
    Iterator<String> iter = pieces.iterator();
    int i = 1;
    while (iter.hasNext()) {
      String val = iter.next();
      textArea.append(val);
      textArea.append(" ");
      if (i % 16 == 0) {
        textArea.append(System.lineSeparator());
      }
      i++;
    }

    JScrollPane scrollPane = new JScrollPane(textArea);
    scrollPane.setBounds(10,10,870,180);
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

    taRam = textArea;
    ramPanel.add(scrollPane);

    return ramPanel;
  }

  private JPanel createDebugControlPanel() {
    JPanel debugControlPanel = new JPanel();
    debugControlPanel.setLayout(new BoxLayout(debugControlPanel, BoxLayout.X_AXIS));

    btnPauseExecution = new JButton("Pause Execution");
    btnPauseExecution.addActionListener(new ToogleFreezeButtonListener());
    debugControlPanel.add(btnPauseExecution);

    JButton btnRefreshRam = new JButton("Refresh RAM");
    btnRefreshRam.addActionListener(new RefreshRamButtonListener());
    debugControlPanel.add(btnRefreshRam);

    JButton btnSingleStep = new JButton("Step Forward");
    btnSingleStep.addActionListener(new SingleStepButtonListener());
    debugControlPanel.add(btnSingleStep);

    JButton btnSingleStepBack = new JButton("Step Backward");
    btnSingleStepBack.addActionListener(new SingleStepBackButtonListener());
    debugControlPanel.add(btnSingleStepBack);

    JButton btnSaveState = new JButton("Save State");
    btnSaveState.addActionListener(new SaveStateButtonListener());
    debugControlPanel.add(btnSaveState);

    btnLoadState = new JButton("Load State (Buggy)");
    btnLoadState.addActionListener(new LoadStateButtonListener());
    debugControlPanel.add(btnLoadState);
    btnLoadState.setEnabled(false);


    return debugControlPanel;
  }

  private void refreshRamPanelContents() {
    StringBuilder sb = new StringBuilder();

    byte[] ram = debugger.getRam();
    String hex = Hex.encodeHexString(ram);

    Iterable<String> pieces = Splitter.fixedLength(4).split(hex);

    Iterator<String> iter = pieces.iterator();
    int i = 1;
    while (iter.hasNext()) {
      String val = iter.next();
      sb.append(val);
      sb.append(" ");
      if (i % 16 == 0) {
        sb.append(System.lineSeparator());
      }

      i++;
    }

    taRam.setText(sb.toString());
  }

  private void refreshEachRegisterValue() {
    for (RegisterLabel register : RegisterLabel.values()) {
      Label registerData = dataDisplayLabelsPerRegister.get(register);
      char data = debugger.getRegisterValue(register);

      String hex = Integer.toHexString(data);
      while (hex.length() < 3) {
        hex = " " + hex;
      }
      registerData.setText(hex);
    }
  }


  @Override
  public void onSingleCycleCompleted() {
    refreshEachRegisterValue();
  }



  private void updateToggleFreezeButtonText() {
    frozen = !frozen;
    btnPauseExecution.setText(frozen ? RESUME : PAUSE);
  }

  private void setToggleFreezeButtonText() {
    btnPauseExecution.setText(frozen ? RESUME : PAUSE);
  }

  class ToogleFreezeButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      debugger.toggleFreezeExecution();
      updateToggleFreezeButtonText();
    }
  }

  class RefreshRamButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      refreshRamPanelContents();
    }
  }

  class SingleStepButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      debugger.stepForward();

      frozen = true;
      setToggleFreezeButtonText();
    }
  }

  class SingleStepBackButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      debugger.stepBackward();

      frozen = true;
      setToggleFreezeButtonText();
      refreshEachRegisterValue();
    }
  }

  class SaveStateButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      byte[] state = saveStateHandler.createSaveState();
      saveStateFileManager.saveFile(state);
      btnLoadState.setEnabled(true);
    }
  }

  class LoadStateButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      byte[] state = saveStateFileManager.load();
      saveStateHandler.loadFromSaveStateAndRun(state);
    }
  }

}



