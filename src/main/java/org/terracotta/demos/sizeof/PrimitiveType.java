package org.terracotta.demos.sizeof;

/**
 * @author Alex Snaps
 */
public enum PrimitiveType {

  LONG(long.class, 8),
  DOUBLE(double.class, 8),
  INT(int.class, 4),
  FLOAT(float.class, 4),
  SHORT(short.class, 2),
  BYTE(byte.class, 1),
  BOOLEAN(boolean.class, 1),
  OOP(null, JvmInformation.javaPointerSize());

  private final Class clazz;
  private final int size;

  PrimitiveType(final Class clazz, final int size) {
    this.clazz = clazz;
    this.size = size;
  }

  public int sizeOf() {
    return size;
  }

  public static PrimitiveType get(final Class<?> type) {
    for (PrimitiveType primitiveType : values()) {
      if(primitiveType.clazz == type)
        return primitiveType;
    }
    return OOP;
  }
}
