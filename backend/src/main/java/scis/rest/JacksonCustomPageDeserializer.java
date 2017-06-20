package scis.rest;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import scis.model.Page;

import java.io.IOException;

/**
 * Created by wangyifei on 2017/6/16.
 */
public class JacksonCustomPageDeserializer extends StdDeserializer<Page> {
    public JacksonCustomPageDeserializer() {
        this(null);
    }

    public JacksonCustomPageDeserializer(Class<Page> t) {
        super(t);
    }

    @Override
    public Page deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
        Page page= new Page();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = parser.getCodec().readTree(parser);
        int pageNo = node.get("no").asInt();
        if (!(pageNo == 0)) {
            page.setNo(pageNo);
        }
        return page;
    }
}
