package com.arkumbra.chip8.machine;

public interface Screen {

    void clearScreen();

    /**
     * Draws 8 bits of pixels to the screen.
     *
     * From http://devernay.free.fr/hacks/chip8/C8TECH10.HTM#Dxyn
     * "Dxyn - DRW Vx, Vy, nibble
     * Display n-byte sprite starting at memory location I at (Vx, Vy), set VF = collision.
     *
     * The interpreter reads n bytes from memory, starting at the address stored in I. These bytes
     * are then displayed as sprites on screen at coordinates (Vx, Vy). Sprites are XORed onto
     * the existing screen. If this causes any pixels to be erased, VF is set to 1, otherwise it is
     * set to 0. If the sprite is positioned so part of it is outside the coordinates of the display
     * , it wraps around to the opposite side of the screen. See instruction 8xy3 for more
     * information on XOR, and section 2.4, Display, for more information on the Chip-8
     * screen and sprites."
     *
     * @param bitFlagsToDraw byte indicating which of 8 pixels should be set
     * @param fromX x position to start drawing the 8 bits from (i.e increase x by 1 for each bit)
     * @param y row on which to draw
     * @return boolean indicating if any of the pixels are flipped from set to unset as a result
     *  of this draw
     */
    boolean draw(byte bitFlagsToDraw, int fromX, int y);

//    void blockReads();
//    void unblockReads();
}
