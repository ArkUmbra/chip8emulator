package com.arkumbra.chip8.util;

/*
 * Copyright 2010 Firat Salgur
 * Improved by Abdallah Abdelazim
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Convert between decimal & BCD (binary-coded decimal).
 */
public class BcdConverter {

  /**
   * Encodes a positive integer number into an unsigned packed BCD.
   *
   * @param num a positive integer number to encode (maximum value of 2<sup>63</sup>-1).
   * @return BCD representation of the passed number argument.
   * @throws IllegalArgumentException if the passed num argument is negative.
   */
  public static byte[] decimalToBcd(long num) {
    if (num < 0) throw new IllegalArgumentException(
        "The method decimalToBcd doesn't support negative numbers." +
            " Invalid argument: " + num);

    int digits = 0;

    long temp = num;
    while (temp != 0) {
      digits++;
      temp /= 10;
    }

    int byteLen = digits % 2 == 0 ? digits / 2 : (digits + 1) / 2;

    byte[] bcd = new byte[byteLen];

    for (int i = 0; i < digits; i++) {
      byte tmp = (byte) (num % 10);

      if (i % 2 == 0) {
        bcd[i / 2] = tmp;
      } else {
        bcd[i / 2] |= (byte) (tmp << 4);
      }

      num /= 10;
    }

    for (int i = 0; i < byteLen / 2; i++) {
      byte tmp = bcd[i];
      bcd[i] = bcd[byteLen - i - 1];
      bcd[byteLen - i - 1] = tmp;
    }

    return bcd;
  }

  /**
   * Decodes an unsigned packed BCD to its integer number.
   *
   * @param bcd the BCD to decode.
   * @return the decoded integer number.
   */
  public static long bcdToDecimal(byte[] bcd) {
    return Long.parseLong(BcdConverter.bcdToString(bcd));
  }

  /**
   * Decodes an unsigned packed BCD to its integer number wrapped in a {@code String}.
   *
   * @param bcd the BCD to decode.
   * @return the decoded integer number wrapped inside a {@code String}.
   */
  public static String bcdToString(byte[] bcd) {
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < bcd.length; i++) {
      sb.append(bcdToString(bcd[i]));
    }

    return sb.toString();
  }

  /**
   * Decodes an unsigned packed BCD byte to its integer number wrapped in a {@code String}.
   *
   * @param bcd the BCD byte to decode.
   * @return the decoded integer number wrapped inside a {@code String}.
   */
  public static String bcdToString(byte bcd) {
    StringBuilder sb = new StringBuilder();

    byte high = (byte) (bcd & 0xf0);
    high >>>= (byte) 4;
    high = (byte) (high & 0x0f);
    byte low = (byte) (bcd & 0x0f);

    sb.append(high);
    sb.append(low);

    return sb.toString();
  }


  public static void main(String[] args) {
    System.out.println("Testing decimalToBcd:");
    System.out.println(String.format("         %10s %20s %18s", "Value", "Expected", "Result"));
    BcdConverter.testForValue(1L, "00000001");
    BcdConverter.testForValue(11L, "00010001");
    BcdConverter.testForValue(111L, "0000000100010001");
    BcdConverter.testForValue(1111L, "0001000100010001");
    BcdConverter.testForValue(11111L, "000000010001000100010001");
    BcdConverter.testForValue(42, "01000010");
    BcdConverter.testForValue(112233L, "000100010010001000110011");
    BcdConverter.testForValue(12345L, "000000010010001101000101");

    System.out.println("\nTesting two way conversion using decimalToBcd and back using bcdToDecimal:");
    System.out.println(String.format("         %10s %20s %18s", "Value", "Expected", "Result"));
    BcdConverter.testForValue(1L);
    BcdConverter.testForValue(11L);
    BcdConverter.testForValue(111L);
    BcdConverter.testForValue(1111L);
    BcdConverter.testForValue(11111L);
    BcdConverter.testForValue(12983283L);
    BcdConverter.testForValue(9832098349L);
  }

  /* Testing Utilities */

  private static void testForValue(long val, String expected) {
    String binaryString = BcdConverter.byteArrayToBinaryString(BcdConverter.decimalToBcd(val));
    System.out.print(String.format("Testing: %10d -> %30s %4s\n", val, binaryString
        , binaryString.equals(expected) ? "[OK]" : "[FAIL]"));
  }

  private static void testForValue(long val) {
    long newVal = BcdConverter.bcdToDecimal(BcdConverter.decimalToBcd(val));

    System.out.print(String.format("Testing: %10d -> %30d %4s\n", val, newVal
        , newVal == val ? "[OK]" : "[FAIL]"));
  }

  private static String byteArrayToBinaryString(byte[] bytes) {
    StringBuilder sb = new StringBuilder();
    for (byte b : bytes) {
      String byteInBinary = String.format("%8s", Integer.toBinaryString(b)).replace(' ', '0');
      sb.append(byteInBinary);
    }
    return sb.toString();
  }
}
