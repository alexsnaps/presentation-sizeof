package org.terracotta.demos.sizeof;

import java.lang.reflect.Field;

/**
 * @author Alex Snaps
 */
class FieldLocation implements Comparable<FieldLocation> {
  private final Class<?> clazz;
  private final String field;
  private final long offSet;
  private final String type;
  private final PrimitiveType primitiveType;

  public FieldLocation(final Field f, final long l) {
    clazz = f.getDeclaringClass();
    field = f.getDeclaringClass().getSimpleName() + "." + f.getName();
    type = f.getType().getSimpleName();
    offSet = l;
    primitiveType = PrimitiveType.get(f.getType());
  }

  public int compareTo(final FieldLocation o) {
    return o.offSet > offSet ? -1 : (o.offSet == offSet) ? 0 : 1;
  }

  int fieldNameLength() {
    return field.length();
  }

  int sizeOf() {
    return primitiveType.sizeOf();
  }

  int typeNameLength() {
    return type.length();
  }

  String println(FieldLocation previousLocation, int maxFieldSize, final int maxTypeSize) {
    StringBuilder sb = new StringBuilder();
    if(previousLocation != null) {
      for(long i = previousLocation.offSet + previousLocation.sizeOf();  i < offSet; i++) {
        sb.append(String.format("   %" + maxFieldSize + "s %-" + maxTypeSize + "s    nothing at offset %3d †\n",
            "", "", i));
      }
    }
    final boolean newClass = previousLocation != null && previousLocation.clazz != clazz;
    sb.append(String.format(" %c %" + maxFieldSize + "s %-" + maxTypeSize + "s (%d) starts at offset %3d %c\n",
        newClass ? '¯' : ' ', field, type,
        primitiveType.sizeOf(), offSet, newClass ? '¯' : ' '));
    return sb.toString();
  }

  long getOffset() {
    return offSet;
  }
}
