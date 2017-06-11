package scis.rest;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import scis.model.SC;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wangyifei on 2017/6/11.
 */
public class JacksonCustomSCDeserializer extends StdDeserializer<SC> {
    public JacksonCustomSCDeserializer() {
        this(null);
    }

    public JacksonCustomSCDeserializer(Class<SC> t) {
        super(t);
    }

    @Override
    public SC deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
        SC sc = new SC();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = parser.getCodec().readTree(parser);
        int scId = node.get("id").asInt();
        String no = node.get("no").asText(null);
        String name = node.get("name").asText(null);
        String depart = node.get("depart").asText(null);
        String course = node.get("course").asText(null);

        if (!(scId == 0)) {
            sc.setId(scId);
        }
        sc.setNo(no);
        sc.setName(name);
        sc.setDepart(depart);
        sc.setCourse(course);
        return sc;
    }
}
