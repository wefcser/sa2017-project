package scis.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import scis.rest.JacksonCustomPageDeserializer;
import scis.rest.JacksonCustomPageSerializer;

/**
 * Created by wangyifei on 2017/6/16.
 */
@JsonSerialize(using = JacksonCustomPageSerializer.class)
@JsonDeserialize(using = JacksonCustomPageDeserializer.class)
public class Page {
    private Integer no;

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }
}
