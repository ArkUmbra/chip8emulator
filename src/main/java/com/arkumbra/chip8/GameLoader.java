package com.arkumbra.chip8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import org.apache.commons.codec.binary.Hex;

public class GameLoader {

  public Memory loadGameIntoMemory(String filePath) throws IOException {
    byte[] bytes = Files.readAllBytes(Path.of(filePath));

    char[] opcodes = new char[bytes.length / 2];
    int cCounter = 0;


    for (int i = 0; i < bytes.length; i += 2) {
      byte byteLeft = bytes[i];
      byte byteRight = bytes[i + 1];
//      char opCode = (char) ((byteLeft << 8) | byteRight);
      // We need to add the bitmask on the right, to make Java not do weird stuff with widening
      char opCode = (char) ((byteLeft << 8) | byteRight & 0xFF);

      opcodes[cCounter] = opCode;
      cCounter++;
    }

//    short[] opcodes = new short[bytes.length / 2];
//    int cCounter = 0;
//
//
//    for (int i = 0; i < bytes.length; i += 2) {
//      byte byteLeft = bytes[i];
//      byte byteRight = bytes[i + 1];
//      short opCode = (short) ((byteLeft << 8) | byteRight);
//
//      char[] encoded = Hex.encodeHex(new byte[]{byteLeft, byteRight});
//      System.out.println(encoded.length);
//
//      System.out.println(Hex.encodeHexString(new byte[]{byteLeft, byteRight}, false) + " : " + Integer.toHexString(opCode) + " : " + Integer.toHexString(byteLeft + byteRight));
//
//      opcodes[cCounter] = opCode;
//      cCounter++;
//    }

//    System.out.println(Hex.encodeHexString(bytes));

//    if (opcodes[0] == 0x00E0) {
//    Integer firstTwo = bytes[0] << 8 | bytes[1];
//    System.out.println(firstTwo);
//    System.out.println(Integer.toHexString(firstTwo));
//
//    TreeSet<String> unique = new TreeSet<>((a, b) -> a.compareTo(b) * -1);
//
//    for (Character c : opcodes) {
//      unique.add(Integer.toHexString(c));
//    }
//    System.out.println(unique);


//    if ((bytes[0] << 8 | bytes[1]) == 0x00E0) {
//      System.out.println("HIT");
//    }
//    System.out.println(java.util.Arrays.toString(Hex.encodeHexString(bytes).split("(?<=\\G....)")));

    Memory memory = new MemoryImpl();
    memory.load(opcodes);
//    memory.load(Hex.encodeHex(bytes));
    return memory;
  }

}
