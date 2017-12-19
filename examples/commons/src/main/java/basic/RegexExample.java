package basic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 匹配中文
 * Created by wanshao
 * Date: 2017/11/29
 * Time: 下午4:12
 **/
public class RegexExample {
    //匹配中文，包括引号
    private static Pattern pattern = Pattern.compile("\"([^\"]*[\u4e00-\u9fa5][^\"]*)\"");
    //该正则匹配单引号及引号里面的所有内容，针对STATUS==2
    private static Pattern pattern2 = Pattern.compile("\'([^']*[\u4e00-\u9fa5][^']*)\'");
    //针对STATUS==3
    private static Pattern pattern3 = Pattern.compile("\\[([^\"]*[\u4e00-\u9fa5][^\"]*)\\]");
    //匹配中文，不包括引号
    private static Pattern partternPure = Pattern.compile("([\u4e00-\u9fa5]+[^\\s]*[\u4e00-\u9fa5]+)");
    private static Pattern testPattern = Pattern.compile("JingweiEnvironment.getLangEnv\\(\\)\\) \'");
    //匹配中文
    public static void main(String[] args) {
        String s= "JW_vm.table8995',$JingweiEnvironment.getLangEnv()) \'";
        Matcher matcher = testPattern.matcher(s);
        if (matcher.find()) {
            String str = matcher.group(0);
            System.out.println(str);
            System.out.println(StringUtils.deleteWhitespace(str));
        }

        //Matcher matcherPure = partternPure.matcher(s);
        //System.out.println("partternPure pattern");
        //if (matcherPure.find()) {
        //    System.out.println(matcherPure.group(0));
        //}

    }



}
