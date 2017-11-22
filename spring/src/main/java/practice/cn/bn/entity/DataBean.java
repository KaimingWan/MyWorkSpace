package practice.cn.bn.entity;

import org.springframework.stereotype.Component;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-09-19 PM05:38
 */
@Component
public class DataBean {
    private String data;
    private String source;

    public DataBean() {
    }

    public DataBean(String data, String source) {
        this.data = data;
        this.source = source;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "DataBean{" +
                "data='" + data + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
