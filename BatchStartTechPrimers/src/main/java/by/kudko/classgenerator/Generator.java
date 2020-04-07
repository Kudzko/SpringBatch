package by.kudko.classgenerator;

import javax.tools.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.Collections;

public class Generator {
    private static final String CLASS_NAME = "MyClass";

    public static void main(String[] args) throws Exception {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        String code = "" +
                "public class MyClass {" +
                "    public static void main(String[] args) {" +
                "        System.out.println(\"Hello!\");" +
                "    }" +
                "}";

        SourceCode sourceCode = new SourceCode(CLASS_NAME, code);

        CompiledCode compiledCode = new CompiledCode(CLASS_NAME);

        ClassLoader classLoader = new ClassLoader(ClassLoader.getSystemClassLoader()) {
            @Override
            protected Class<?> findClass(String name) throws ClassNotFoundException {
                byte[] byteCode = compiledCode.getByteCode();
                return defineClass(name, byteCode, 0, byteCode.length);
            }
        };

        JavaFileManager fileManager = new ForwardingJavaFileManager<StandardJavaFileManager>(compiler.getStandardFileManager(null, null, null)) {
            @Override
            public JavaFileObject getJavaFileForOutput(JavaFileManager.Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
                return compiledCode;
            }

            @Override
            public ClassLoader getClassLoader(JavaFileManager.Location location) {
                return classLoader;
            }
        };

        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, null, null, Collections.singletonList(sourceCode));

        if (task.call()) {
            Class<?> clazz = classLoader.loadClass(CLASS_NAME);
            clazz.getDeclaredMethod("main", new Class[] { String[].class }).invoke(null, new Object[] { null });
        }
    }
}

/**
 * Класс для хранения исходного кода на языке Java.
 */
class SourceCode extends SimpleJavaFileObject {
    private String code;

    SourceCode(String className, String code) throws Exception {
        super(URI.create("string:///" + className.replace('.', '/') + JavaFileObject.Kind.SOURCE.extension), JavaFileObject.Kind.SOURCE);
        this.code = code;
    }

    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return code;
    }
}

/**
 * Класс для хранения скомпилированного байт-кода.
 */
class CompiledCode extends SimpleJavaFileObject {
    private ByteArrayOutputStream baos = new ByteArrayOutputStream();

    CompiledCode(String className) throws Exception {
        super(new URI(className), JavaFileObject.Kind.CLASS);
    }

    @Override
    public OutputStream openOutputStream() throws IOException {
        return baos;
    }

    byte[] getByteCode() {
        return baos.toByteArray();
    }
}