package by.kudko.classgenerator;

import javax.tools.SimpleJavaFileObject;
import java.io.IOException;
import java.net.URI;

 /**
 * Класс для хранения исходного кода на языке Java.
 */
public class SourceCode extends SimpleJavaFileObject {
    private String code;

    public SourceCode(String className, String code) throws Exception {
        super(URI.create("string:///" + className.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
        this.code = code;
    }

    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return code;
    }
}