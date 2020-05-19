package com.arkumbra.chip8;

import org.apache.commons.codec.binary.Hex;

public class RadixUtils {

  public static String charToHex(char value) {
    return Hex.encodeHexString(new byte[]{
        (byte)(value >> 8),
        (byte)(value)
    });
  }

  public static void printHexAndBinary(char value) {
    String binary = Integer.toBinaryString(value);
    String hex = charToHex(value);

    System.out.println(hex + " : " + binary + " : " + (int)value);
  }

  public static String asHexAndBinary(char value) {
    String binary = Integer.toBinaryString(value);
    String asHex = Integer.toHexString(value);
    String hex = charToHex(value);

    return hex + " : " + asHex + " : " +binary + " : " + (int)value + " : " + value;
  }

}
