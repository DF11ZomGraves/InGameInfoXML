package df11zomgraves.ingameinfo.tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import df11zomgraves.ingameinfo.InGameInfoXML;

public class TagRegistry {
	public static final TagRegistry INSTANCE = new TagRegistry();

	private Map<String, Tag> stringTagMap = new HashMap<String, Tag>();

	private void register(final String name, final Tag tag) {
		if (this.stringTagMap.containsKey(name)) {
			InGameInfoXML.logger.error("Duplicate tag key '" + name + "'!");
			return;
		}

		if (name == null) {
			InGameInfoXML.logger.error("Tag name cannot be null!");
			return;
		}

		this.stringTagMap.put(name.toLowerCase(Locale.ENGLISH), tag);
	}

	public void register(final Tag tag) {
		register(tag.getName(), tag);

		for (final String name : tag.getAliases()) {
			register(name, tag);
		}
	}

	public String getValue(final String name) {
		final Tag tag = this.stringTagMap.get(name.toLowerCase(Locale.ENGLISH));
		return tag != null ? tag.getValue() : null;
	}

	public List<Tag> getRegisteredTags() {
		final List<Tag> tags = new ArrayList<Tag>();
		for (final Map.Entry<String, Tag> entry : this.stringTagMap.entrySet()) {
			tags.add(entry.getValue());
		}
		return tags;
	}

	public void init() {
		TagFormatting.register();
		TagMisc.register();
		TagMouseOver.register();
		TagNearbyPlayer.register();
		TagPlayerEquipment.register();
		TagPlayerGeneral.register();
		TagPlayerPosition.register();
		TagPlayerEffect.register();
		TagRiding.register();
		TagTime.register();
		TagWorld.register();

		InGameInfoXML.logger.info("Registered " + this.stringTagMap.size() + " tags.");
	}
}
