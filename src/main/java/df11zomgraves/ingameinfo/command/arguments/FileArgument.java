package df11zomgraves.ingameinfo.command.arguments;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import df11zomgraves.ingameinfo.InGameInfoCore;
import df11zomgraves.ingameinfo.reference.Names;
import net.minecraft.commands.SharedSuggestionProvider;

public class FileArgument implements ArgumentType<String> {
	private static final Collection<String> EXAMPLES = Arrays.asList("default", "ingameinfo.xml");
	private final InGameInfoCore core = InGameInfoCore.INSTANCE;

	public static FileArgument files() {
		return new FileArgument();
	}
	
	public static String GetString(final CommandContext<?> context, final String name) {
		return context.getArgument(name, String.class);
	}

	public String parse(StringReader reader) throws CommandSyntaxException {
		String filename = reader.getRemaining();
		reader.setCursor(reader.getTotalLength());
		return filename;
	}

	@Override
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext,
			SuggestionsBuilder suggestionsBuilder) {
		final File[] files = this.core.getConfigDirectory().listFiles((File dir, String name) -> {
			return name.toLowerCase().startsWith(Names.Files.NAME.toLowerCase()) && (name.endsWith(Names.Files.EXT_XML)
					|| name.endsWith(Names.Files.EXT_JSON) || name.endsWith(Names.Files.EXT_TXT));
		});

		final List<String> filenames = new ArrayList<String>();
		
		filenames.add("default");
		for (final File file : files)
			filenames.add(file.getName());
		String[] filenames1 = new String[filenames.size()];
		filenames1 = filenames.toArray(filenames1);
		
		return SharedSuggestionProvider.suggest(filenames1, suggestionsBuilder);
	}

	@Override
	public Collection<String> getExamples() {
		return EXAMPLES;
	}

}
