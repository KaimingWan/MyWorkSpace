package util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.mozilla.universalchardet.UniversalDetector;

/**
 *
 *  gbk的编码似乎不能准确探测，只能探测为GB18030
 *
 *  sun.jnu.encoding affects the creation of file name (this possibly gets set by LANG on unix before starting java)影响类名的读取和Main方法参数的读取。
    file.encoding affects the content of a file
 * Created by wanshao
 * Date: 2017/11/29
 * Time: 上午10:46
 **/
public class DetectFileEncodingExample {
    public static void main(String[] args) throws IOException {
        String fileName = "java_template_with_keys.csv";
        URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
        File file = new File(url.getFile());
        FileInputStream fis = new FileInputStream(file);
        byte[] buf = new byte[4096];
        // (1)
        UniversalDetector detector = new UniversalDetector(null);

        // (2)
        int nread;
        while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
            detector.handleData(buf, 0, nread);
        }
        // (3)
        detector.dataEnd();

        // (4)
        String encoding = detector.getDetectedCharset();
        if (encoding != null) {
            System.out.println("Detected encoding = " + encoding);
        } else {
            System.out.println("No encoding detected.");
        }

        // (5)
        detector.reset();
    }
}
