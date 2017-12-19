package basic;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 给指定目录下的所有JAVA文件增加所需要的import，在WriterKeyForJava运行后使用
 * Created by wanshao
 * Date: 2017/11/30
 * Time: 上午11:33
 **/
public class ReplaceVMCode {
    //private static final String PREFIX = "";
    private static String srcPath = "/Users/wanshao/projects/jingwei3/web";
    //private static String srcPath = "/Users/wanshao/tmp/jingwei_cn/test";

    private static String targetPath = "/Users/wanshao/tmp/jingwei_cn/imported";

    private static Pattern spacePattern = Pattern.compile("JingweiEnvironment.getLangEnv\\(\\)\\) '");


    public static void main(String[] args) throws IOException {
        File srcDir = new File(srcPath);
        // addImport(srcDir);
        removeDuplcatdSpaces(srcDir);
    }


    private static void removeDuplcatdSpaces(File srcDir) throws IOException {
        if(srcDir.isDirectory()){
            File[] subFiles=srcDir.listFiles();
            for (File subFile : subFiles) {
                removeDuplcatdSpaces(subFile);
            }
        }

        //读取到java文件
        if (srcDir.getName().endsWith(".vm")) {
            //srcPath是读取的JAVA源代码的前缀路径，替换成目标目录
            File targetFile = new File(srcDir.getAbsolutePath().replace(srcPath, targetPath));
            File targetParent = targetFile.getParentFile();
            //没有相关目录和文件则创建
            if (!targetFile.exists()) {
                targetParent.mkdirs();
                targetFile.createNewFile();
            }
            List<String> stringList = FileUtils.readLines(srcDir);
            int count=0;
            for (String s : stringList) {
                Matcher matcher = spacePattern.matcher(s);
                if (matcher.find()) {
                    s=StringUtils.deleteWhitespace(s);
                }

                FileUtils.write(targetFile,s+"\n","utf-8",true);
                count++;
            }
        }
    }


}
