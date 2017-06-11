package scis.rest;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import scis.model.SC;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;

/**
 * Created by wangyifei on 2017/6/11.
 */
public class JacksonCustomSCSerializer extends StdSerializer<SC> {
    public JacksonCustomSCSerializer() {
        this(null);
    }

    protected JacksonCustomSCSerializer(Class<SC> t) {
        super(t);
    }

    @Override
    public void serialize(SC sc, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        Format formatter = new SimpleDateFormat("yyyy/MM/dd");
        jgen.writeStartObject(); // sc
        if (sc.getId() == null) {
            jgen.writeNullField("id");
        } else {
            jgen.writeNumberField("id", sc.getId());
        }
        jgen.writeStringField("no", sc.getNo());
        jgen.writeStringField("name", sc.getName());
        jgen.writeStringField("depart", sc.getDepart());
        jgen.writeStringField("course", sc.getCourse());

        jgen.writeEndObject(); // sc
    }

}
