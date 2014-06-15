//package jackson;
//
//import org.bson.types.ObjectId;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.module.SimpleModule;
//
//@SuppressWarnings("serial")
//public class CustomObjectMapper extends ObjectMapper {
//
//	public CustomObjectMapper() {
//        SimpleModule module = new SimpleModule("ObjectIdmodule");
//        module.addSerializer(ObjectId.class, new ObjectIdSerializer());
//        this.registerModule(module);
//    }
//
//}
