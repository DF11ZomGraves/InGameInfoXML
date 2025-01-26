package df11zomgraves.ingameinfo.command.arguments;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import df11zomgraves.ingameinfo.reference.Names;
import net.minecraft.commands.SharedSuggestionProvider;

public class AlignArgument implements ArgumentType<String> {
	private static final Collection<String> EXAMPLES = Arrays.asList("TOPLEFT", "MIDDLECENTER", "BOTTOMRIGHT");

	public static AlignArgument GetAlignment() {
		return new AlignArgument();
	}
	
	public static String GetString(final CommandContext<?> context, final String name) {
		return context.getArgument(name, String.class);
	}
	
	@Override
	public String parse(StringReader reader) throws CommandSyntaxException {
		String alignment = reader.readString();
		return alignment;
	}

	@Override
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext,
			SuggestionsBuilder suggestionsBuilder) {
		String[] align = {
				Names.Command.TOP_LEFT,
				Names.Command.TOP_CENTER,
				Names.Command.TOP_RIGHT,
				Names.Command.MIDDLE_LEFT,
				Names.Command.MIDDLE_CENTER,
				Names.Command.MIDDLE_RIGHT,
				Names.Command.BOTTOM_LEFT,
				Names.Command.BOTTOM_CENTER,
				Names.Command.BOTTOM_RIGHT
		};
		return SharedSuggestionProvider.suggest(align, suggestionsBuilder);
	}
	
	@Override
	public Collection<String> getExamples() {
		return EXAMPLES;
	}

}
