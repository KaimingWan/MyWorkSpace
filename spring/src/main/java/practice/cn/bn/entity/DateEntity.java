package practice.cn.bn.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @description :
 * @date : 2016/11/9
 */
@Component
public class DateEntity {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Value("2016-10-22")
    private Date birthday;

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "DateEntity{" +
                "birthday=" + birthday +
                '}';
    }
}
