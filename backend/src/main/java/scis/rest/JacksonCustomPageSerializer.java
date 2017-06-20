package scis.rest;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import scis.model.Page;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;

/**
 * Created by wangyifei on 2017/6/16.
 */
public class JacksonCustomPageSerializer extends StdSerializer<Page> {
    public JacksonCustomPageSerializer() {
        this(null);
    }

    protected JacksonCustomPageSerializer(Class<Page> t) {
        super(t);
    }

    @Override
    public void serialize(Page page, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject(); // page
        if (page.getNo() == null) {
            jgen.writeNullField("no");
        } else {
            jgen.writeNumberField("no", page.getNo());
        }
        jgen.writeEndObject(); // page
    }

}
