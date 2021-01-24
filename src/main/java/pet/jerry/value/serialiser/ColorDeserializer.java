package pet.jerry.value.serialiser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.awt.*;
import java.io.IOException;

public class ColorDeserializer extends StdDeserializer<Color> {
	public ColorDeserializer() {
		super(Color.class);
	}

	@Override
	public Color deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		JsonNode node = p.getCodec().readTree(p);
		return new Color(node.get("r").intValue(), node.get("g").intValue(), node.get("b").intValue(), node.get("a").intValue());
	}
}
