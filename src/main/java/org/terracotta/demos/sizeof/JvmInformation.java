package org.terracotta.demos.sizeof;

/**
 * @author Alex Snaps
 */
class JvmInformation {

  static int addressSize() {
    return ClassLayoutWriter.UNSAFE.addressSize();
  }

  static int javaPointerSize() {
    return 4; // True for 32bit & 64bit w/ Compressed OOPS
  }

  static int objectHeaderSize() {
    return addressSize() + javaPointerSize();
  }
}
