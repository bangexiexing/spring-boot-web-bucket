package bucket.component.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Date;

/**
 * @Author: qyl
 * @Description:
 * @Date: Created in 0:27 2018/6/4
 */
public class DateSerializer extends JsonSerializer<Date> {

    @Override
    public Class<Date> handledType() {
        return Date.class;
    }

    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeNumber(value.getTime() / 1000);
    }
}
