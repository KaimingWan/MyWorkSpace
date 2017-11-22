package practice.cn.bn.entity;

import java.util.Date;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-10-08 AM11:21
 */
public class TestBean {
    private String data;
    private String source;
    private Date date;

    public void setDate(Date date) {
        this.date = date;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "TestBean{" +
                "data='" + data + '\'' +
                ", source='" + source + '\'' +
                ", date=" + date +
                '}';
    }
}
