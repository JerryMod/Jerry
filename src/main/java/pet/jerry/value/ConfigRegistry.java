package pet.jerry.value;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import pet.jerry.feature.category.FeatureCategory;
import pet.jerry.value.serialiser.ColorDeserializer;
import pet.jerry.value.serialiser.ColorSerializer;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

public final class ConfigRegistry {
	private final DefaultSaveableContainer root = new DefaultSaveableContainer("Jerry", "jerry");
	private final File configDirectory;
	private final File configFile;
	private final ObjectMapper om = new ObjectMapper();

	public ConfigRegistry(File configDirectory) {
		this.configDirectory = new File(configDirectory, "jerry");
		this.configFile = new File(this.configDirectory, "jerry.config.json");
		om.registerModule(
				new SimpleModule()
						.addDeserializer(Color.class, new ColorDeserializer())
						.addSerializer(Color.class, new ColorSerializer())
		);
		root.add(GlobalSettingsContainer.get());
		root.add(Arrays.asList(FeatureCategory.values()));
	}

	public void register(SaveableContainer container) {
		root.add(container);
	}

	public void save() throws IOException {
		ObjectNode rootNode = om.createObjectNode();
		this.save(root, rootNode);

		if (!configDirectory.exists())
			configDirectory.mkdirs();

		if (!configFile.exists())
			configFile.createNewFile();

		om.writerWithDefaultPrettyPrinter().writeValue(configFile, rootNode);
	}

	private void save(SaveableContainer container, ObjectNode parentNode) {
		ObjectNode containerNode = parentNode.putObject(container.getID());
		for (Saveable<?> saveable : container.getValue()) {
			if (saveable instanceof SaveableContainer) {
				save((SaveableContainer) saveable, containerNode);
			} else {
				try {
					containerNode.putPOJO(saveable.getID(), saveable.getValue());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void load() throws IOException {
		if (!configFile.exists())
			save();

		JsonNode tree = om.readTree(configFile);
		if (tree.isObject()) {
			ObjectNode rootNode = (ObjectNode) tree;
			for (Iterator<Map.Entry<String, JsonNode>> it = rootNode.fields(); it.hasNext(); ) {
				Map.Entry<String, JsonNode> entry = it.next();
				String key = entry.getKey();

				if (key.equalsIgnoreCase(root.getID())) {
					this.loadSaveable(root, entry.getValue());
				}
			}
		} else {
			// ?????
		}
	}

	private void loadSaveable(Saveable<?> saveable, JsonNode node) throws IOException {
		if (saveable instanceof SaveableContainer && node.isObject()) {
			SaveableContainer container = (SaveableContainer) saveable;
			for (Iterator<Map.Entry<String, JsonNode>> it = node.fields(); it.hasNext(); ) {
				Map.Entry<String, JsonNode> entry = it.next();
				for (Saveable<?> child : container.getValue()) {
					if (child.getID().equalsIgnoreCase(entry.getKey())) {
						this.loadSaveable(child, entry.getValue());
					}
				}
			}
		} else if (saveable instanceof Value<?>) {
			Value<Object> value = ((Value<Object>) saveable);
			Class<?> clazz = value.getValue().getClass();
			while (clazz.isAnonymousClass()) {
				clazz = clazz.getSuperclass();
			}
			value.setValue(om.treeToValue(node, clazz));
		}
	}

	public SaveableContainer getRoot() {
		return root;
	}
}
