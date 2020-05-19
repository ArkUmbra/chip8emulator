package com.arkumbra.chip8.machine;

public interface Font {

    byte[] getFontDataAndSetFixedAddressLocation(int addressLocation, FontLabel fontLabel);

    int getAddress(FontLabel fontLabel);

}
