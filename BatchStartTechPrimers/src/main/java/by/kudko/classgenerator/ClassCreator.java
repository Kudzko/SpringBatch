package by.kudko.classgenerator;

import javax.tools.*;
import java.io.IOException;
import java.util.Collections;

public class ClassCreator {
    private static final String CLASS_NAME = "MyClass";

    public static void createClass(String code) throws Exception {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

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
            public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
                return compiledCode;
            }

            @Override
            public ClassLoader getClassLoader(Location location) {
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
