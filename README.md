# chip8emulator
A Java-based interpreter for the CHIP-8 system

![Screenshot of the game Blinky](screenshots/blinky.png?raw=true)


# What is CHIP-8? 
From Wikipedia - "CHIP-8 is an interpreted programming language, developed by Joseph Weisbecker. It was initially used on the COSMAC VIP and Telmac 1800 8-bit microcomputers in the mid-1970s. CHIP-8 programs are run on a CHIP-8 virtual machine. It was made to allow video games to be more easily programmed for these computers. "
https://en.wikipedia.org/wiki/CHIP-8

# Particulars of this implementation
This interpreter is built in a way that will (hopefully) make it much easier to read and understand the particulars of an operation. The interpreter is being built heavily on interfaces, with many small classes separating concerns. This is done to make it easier to test (it's my first emulator project) and to substitute out different implementations for key presses / graphics, etc - but this also means it's _very_ far away from a minimal/compact implementation which may not induce wincing in some readers.

# Build State
Work in progress

# References
The Wikipedia page has a good overview of the operations and system details. It also contains useful information regarding edge cases, and some undocumented features.
https://en.wikipedia.org/wiki/CHIP-8

Thomas P. Greene (aka Cowgod) also has an excellent technical reference manual for CHIP-8 here (which is thankfully still available, many decades after being written up!)
http://devernay.free.fr/hacks/chip8/C8TECH10.HTM#Dxyn
