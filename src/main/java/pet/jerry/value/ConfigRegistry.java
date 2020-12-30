package pet.jerry.value;

import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.*;

public final class ConfigRegistry {
	private final Set<SaveableContainer> root = new HashSet<>();
	private final File configDirectory;
	private final File configFile;
	private final ObjectMapper om = new ObjectMapper();

	public ConfigRegistry(File configDirectory) {
		this.configDirectory = new File(configDirectory, "jerry");
		if(!configDirectory.exists())
			configDirectory.mkdirs();
		this.configFile = new File(this.configDirectory, "jerry.config.json");
	}

	public void register(SaveableContainer container) {
		root.add(container);
	}

	public void save() throws IOException {
		ObjectNode rootNode = om.createObjectNode();
		for (SaveableContainer container : root) {
			save(container, rootNode);
		}

		om.writerWithDefaultPrettyPrinter().writeValue(configFile, rootNode);
	}

	private void save(SaveableContainer container, ObjectNode parentNode) {
		ObjectNode containerNode = parentNode.putObject(container.getID());
		for (Saveable<?> saveable : container.getValue()) {
			if(saveable instanceof SaveableContainer) {
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
		if(!configFile.exists())
			save();

		JsonNode tree = om.readTree(configFile);
		if(tree.isObject()) {
			ObjectNode rootNode = (ObjectNode) tree;
			for (Iterator<Map.Entry<String, JsonNode>> it = rootNode.fields(); it.hasNext(); ) {
				Map.Entry<String, JsonNode> entry = it.next();
				String key = entry.getKey();
				JsonNode node = entry.getValue();

				if(!node.isObject())
					continue;

				SaveableContainer thisContainer = null;
				for (SaveableContainer container : root) {
					if(container.getID().equalsIgnoreCase(key)) {
						thisContainer = container;
						break;
					}
				}
				if(null != thisContainer) {
					loadContainer(thisContainer, (ObjectNode) node);
				}
			}
		} else {
			// ?????
		}
	}

	private void loadContainer(SaveableContainer container, ObjectNode node) throws IOException {
		for (Iterator<Map.Entry<String, JsonNode>> it = node.fields(); it.hasNext(); ) {
			Map.Entry<String, JsonNode> entry = it.next();

			for (Saveable<?> saveable : container.getValue()) {
				if(saveable.getID().equalsIgnoreCase(entry.getKey())) {
					if(saveable instanceof SaveableContainer) {
						if(!entry.getValue().isObject())
							continue;
						loadContainer((SaveableContainer) saveable, (ObjectNode) entry.getValue());
					} else {
						if(saveable instanceof Value<?>) {
							Value<Object> value = ((Value<Object>) saveable);
							value.setValue(om.treeToValue(entry.getValue(), value.getValue().getClass()));
						}
					}
				}
			}
		}
	}
}
