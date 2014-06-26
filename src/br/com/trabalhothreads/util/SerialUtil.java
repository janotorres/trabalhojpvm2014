package br.com.trabalhothreads.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import br.com.trabalhothreads.objects.Texto;

public final class SerialUtil {

        private SerialUtil() {}

        /**
         * Read the object from Base64 string.
         * 
         * @throws ClassNotFoundException
         */
        public static Texto fromString(String s) throws IOException, ClassNotFoundException {
                byte[] data = Base64Coder.decode(s);
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
                Object o = ois.readObject();
                ois.close();
                return (Texto) o;
        }

        /** Write the object to a Base64 string. */
        public static String toString(Serializable o) throws IOException {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(o);
                oos.close();
                return new String(Base64Coder.encode(baos.toByteArray()));
        }
}