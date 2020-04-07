package by.kudko.classgenerator;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

 /**
 * Класс для хранения скомпилированного байт-кода.
 */
public class CompiledCode extends SimpleJavaFileObject {
     private ByteArrayOutputStream baos = new ByteArrayOutputStream();

     CompiledCode(String className) throws Exception {
         super(new URI(className), Kind.CLASS);
     }

     @Override
     public OutputStream openOutputStream() throws IOException {
         return baos;
     }

     byte[] getByteCode() {
         return baos.toByteArray();
     }
 }