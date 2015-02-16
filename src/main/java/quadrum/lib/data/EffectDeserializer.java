package quadrum.lib.data;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class EffectDeserializer implements JsonDeserializer<Effect> {

	@Override
	public Effect deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		Effect effect = new Effect();
		for (Map.Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet()) {
			if (entry.getKey().equalsIgnoreCase("id"))
				effect.id = entry.getValue().getAsInt();
			else if (entry.getKey().equalsIgnoreCase("probability"))
				effect.probability = entry.getValue().getAsFloat();
			else if (entry.getKey().equalsIgnoreCase("duration"))
				effect.duration = entry.getValue().getAsInt();
			else if (entry.getKey().equalsIgnoreCase("amplifier"))
				effect.amplifier = entry.getValue().getAsInt();
		}
		return effect;
	}
}
