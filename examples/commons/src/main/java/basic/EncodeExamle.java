package basic;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import util.HexUtil;

/**
 * Created by wanshao
 * Date: 2017/11/28
 * Time: 下午5:30
 **/
public class EncodeExamle {

    /**
     * GBK每个汉字占用2字节，UTF-8每个汉字占用3字节
     * @param args
     */
    public static void main(String[] args) throws UnsupportedEncodingException {
        String s = "测试文字";

        // 原始字节序列
        // byte[] bytes = char2byte(s.toCharArray());
        // print(bytes);

        // ISO-8859-1
        byte[] iso88591 = s.getBytes("ISO-8859-1");
        System.out.println("iso8859-1编码结果: " + HexUtil.toHex(iso88591));
        System.out.println("iso8859-1解码结果: " + new String(iso88591, "ISO-8859-1"));

        // GB2312
        byte[] gb2312 = s.getBytes("GB2312");
        System.out.println("\ngb2312编码结果: " + HexUtil.toHex(gb2312));
        System.out.println("gb2312解码结果: " + new String(gb2312, "GB2312"));

        // GBK
        byte[] gbk = s.getBytes("GBK");
        System.out.println("\ngbk编码结果: " + HexUtil.toHex(gbk));
        System.out.println("gbk解码结果: " + new String(gbk, "GBK"));

        // GB18030
        byte[] gb18030 = s.getBytes("GB18030");
        System.out.println("\ngb18030编码结果: " + HexUtil.toHex(gb18030));
        System.out.println("gb18030解码结果: " + new String(gb18030, "GB18030"));

        // UTF-16
        byte[] utf16 = s.getBytes("UTF-16");
        System.out.println("\nutf-16编码结果: " + HexUtil.toHex(utf16));
        System.out.println("utf-16解码结果: " + new String(utf16, "UTF-16"));

        // UTF-8
        byte[] utf8 = s.getBytes("UTF-8");
        System.out.println("\nutf-8编码结果: " + HexUtil.toHex(utf8));
        System.out.println("utf-8解码结果: " + new String(utf8, "UTF-8"));

    }
}
