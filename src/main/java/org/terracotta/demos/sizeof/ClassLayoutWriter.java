package org.terracotta.demos.sizeof;

import sun.misc.Unsafe;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Alex Snaps
 */
public class ClassLayoutWriter {

  static final Unsafe UNSAFE;

  static {
    Unsafe unsafe;
    try {
      Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
      unsafeField.setAccessible(true);
      unsafe = (Unsafe)unsafeField.get(null);
    } catch (Throwable t) {
      unsafe = null;
    }
    UNSAFE = unsafe;
  }

  private final OutputStream stream;

  public ClassLayoutWriter() {
    this(System.out);
  }

  public ClassLayoutWriter(final OutputStream stream) {
    this.stream = stream;
  }



  public void writeInMemoryClassLayout(Object obj) throws IOException {
    writeInMemoryClassLayout(obj, false);
  }

  public void writeInMemoryClassLayout(Object obj, boolean showGap) throws IOException {
    stream.write(obj.getClass().getName().getBytes());
    stream.write("'s in memory class layout\n".getBytes());
    stream.write("    *** OBJECT HEADER (".getBytes());
    stream.write(Integer.toString(JvmInformation.objectHeaderSize()).getBytes());
    stream.write(")\n".getBytes());
    SortedSet<FieldLocation> fields = new TreeSet<FieldLocation>();
    int maxFieldSize = 0;
    int maxTypeSize  = 0;
    for (Class<?> klazz = obj.getClass(); klazz != null; klazz = klazz.getSuperclass()) {
      for (Field f : klazz.getDeclaredFields()) {
        if (!Modifier.isStatic(f.getModifiers())) {
          final FieldLocation fieldLocation = new FieldLocation(f, UNSAFE.objectFieldOffset(f));
          maxFieldSize = Math.max(maxFieldSize, fieldLocation.fieldNameLength());
          maxTypeSize = Math.max(maxTypeSize, fieldLocation.typeNameLength());
          fields.add(fieldLocation);
        }
      }
    }
    FieldLocation previous = null;
    for (FieldLocation field : fields) {
      stream.write(field.println(previous, maxFieldSize, maxTypeSize).getBytes());
      previous = showGap ? field : null;
    }
    stream.write('\n');
    stream.flush();
  }
}
