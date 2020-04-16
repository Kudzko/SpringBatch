package by.kudko.classgenerator;

import java.io.*;

public class ClassTemplateReader {

    public static String readFileToString(File file) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader reader = new BufferedReader
                (new InputStreamReader(new FileInputStream(file), "UTF-8"));
        boolean flag = true;

        while (flag) {
            String currentLine = reader.readLine();
            if (currentLine == null) {
                break;
            }
            stringBuffer.append(currentLine);
            stringBuffer.append("\n");
        }
        reader.close();
        return stringBuffer.toString();
    }


}
