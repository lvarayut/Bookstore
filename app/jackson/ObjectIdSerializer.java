package jackson;

import java.io.IOException;
 
import org.bson.types.ObjectId;
 
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
 
public class ObjectIdSerializer extends JsonSerializer<ObjectId> {
	@Override
	public void serialize(ObjectId value, JsonGenerator jsonGen,SerializerProvider provider) throws IOException,
			JsonProcessingException {
		jsonGen.writeString(value.toString());
	}
}
