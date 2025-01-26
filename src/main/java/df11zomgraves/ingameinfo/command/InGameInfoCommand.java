package df11zomgraves.ingameinfo.command;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import df11zomgraves.ingameinfo.InGameInfoCore;
import df11zomgraves.ingameinfo.handler.ConfigurationHandler;
import df11zomgraves.ingameinfo.reference.Alignment;
import df11zomgraves.ingameinfo.reference.Names;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.fml.loading.FMLPaths;

public class InGameInfoCommand {

	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		final String strAlign = Names.Command.ALIENMENT;
		final String strGet = Names.Command.GET;
		final String strSet = Names.Command.SET;
		final String x = "X";
		final String y = "Y";
		final String seed = "Seed";
		final String strDefault = "default";
//		final String filename = "filename";
		
		LiteralArgumentBuilder<CommandSourceStack> igiCommand = Commands.literal(Names.Command.NAME);
		//TODO: So...why 1.20.1 do not work?
//		// igi alignment get TOPLEFT
//		igiCommand.then(Commands.literal(strAlign).then(Commands.literal(strGet).then(Commands.argument(strAlign, AlignArgument.GetAlignment())
//				.executes(source -> GetAlignment(AlignArgument.GetString(source, strAlign))))));
//		// igi alignment set TOPLEFT 2 2
//		igiCommand.then(Commands.literal(strAlign).then(Commands.literal(strSet).then(Commands.argument(strAlign, AlignArgument.GetAlignment())
//				.then(Commands.argument(x, IntegerArgumentType.integer()).then(Commands.argument(y, IntegerArgumentType.integer())
//						.executes(source -> SetAlignment(AlignArgument.GetString(source, strAlign), IntegerArgumentType.getInteger(source, x),
//								IntegerArgumentType.getInteger(source, y))))))));
//		// igi load ingameinfo.xml
//		igiCommand.then(Commands.literal("load").then(Commands.argument(filename, FileArgument.files())
//				.executes(source -> loadFile(FileArgument.GetString(source, filename)))));
		
		// igi alignment get TOPLEFT
		igiCommand.then(Commands.literal(strAlign).then(Commands.literal(strGet).then(Commands.literal(Names.Command.TOP_LEFT)
				.executes(source -> GetAlignment(Names.Command.TOP_LEFT)))));
		igiCommand.then(Commands.literal(strAlign).then(Commands.literal(strGet).then(Commands.literal(Names.Command.TOP_CENTER)
				.executes(source -> GetAlignment(Names.Command.TOP_CENTER)))));
		igiCommand.then(Commands.literal(strAlign).then(Commands.literal(strGet).then(Commands.literal(Names.Command.TOP_RIGHT)
				.executes(source -> GetAlignment(Names.Command.TOP_RIGHT)))));
		igiCommand.then(Commands.literal(strAlign).then(Commands.literal(strGet).then(Commands.literal(Names.Command.MIDDLE_LEFT)
				.executes(source -> GetAlignment(Names.Command.MIDDLE_LEFT)))));
		igiCommand.then(Commands.literal(strAlign).then(Commands.literal(strGet).then(Commands.literal(Names.Command.MIDDLE_CENTER)
				.executes(source -> GetAlignment(Names.Command.MIDDLE_CENTER)))));
		igiCommand.then(Commands.literal(strAlign).then(Commands.literal(strGet).then(Commands.literal(Names.Command.MIDDLE_RIGHT)
				.executes(source -> GetAlignment(Names.Command.MIDDLE_RIGHT)))));
		igiCommand.then(Commands.literal(strAlign).then(Commands.literal(strGet).then(Commands.literal(Names.Command.BOTTOM_LEFT)
				.executes(source -> GetAlignment(Names.Command.BOTTOM_LEFT)))));
		igiCommand.then(Commands.literal(strAlign).then(Commands.literal(strGet).then(Commands.literal(Names.Command.BOTTOM_CENTER)
				.executes(source -> GetAlignment(Names.Command.BOTTOM_CENTER)))));
		igiCommand.then(Commands.literal(strAlign).then(Commands.literal(strGet).then(Commands.literal(Names.Command.BOTTOM_RIGHT)
				.executes(source -> GetAlignment(Names.Command.BOTTOM_RIGHT)))));
		// igi alignment set TOPLEFT 2 2
		igiCommand.then(Commands.literal(strAlign).then(Commands.literal(strSet).then(Commands.literal(Names.Command.TOP_LEFT)
				.then(Commands.argument(x, IntegerArgumentType.integer()).then(Commands.argument(y, IntegerArgumentType.integer())
						.executes(source -> SetAlignment(Names.Command.TOP_LEFT, IntegerArgumentType.getInteger(source, x),
								IntegerArgumentType.getInteger(source, y))))))));
		igiCommand.then(Commands.literal(strAlign).then(Commands.literal(strSet).then(Commands.literal(Names.Command.TOP_CENTER)
				.then(Commands.argument(x, IntegerArgumentType.integer()).then(Commands.argument(y, IntegerArgumentType.integer())
						.executes(source -> SetAlignment(Names.Command.TOP_CENTER, IntegerArgumentType.getInteger(source, x),
								IntegerArgumentType.getInteger(source, y))))))));
		igiCommand.then(Commands.literal(strAlign).then(Commands.literal(strSet).then(Commands.literal(Names.Command.TOP_RIGHT)
				.then(Commands.argument(x, IntegerArgumentType.integer()).then(Commands.argument(y, IntegerArgumentType.integer())
						.executes(source -> SetAlignment(Names.Command.TOP_RIGHT, IntegerArgumentType.getInteger(source, x),
								IntegerArgumentType.getInteger(source, y))))))));
		igiCommand.then(Commands.literal(strAlign).then(Commands.literal(strSet).then(Commands.literal(Names.Command.MIDDLE_LEFT)
				.then(Commands.argument(x, IntegerArgumentType.integer()).then(Commands.argument(y, IntegerArgumentType.integer())
						.executes(source -> SetAlignment(Names.Command.MIDDLE_LEFT, IntegerArgumentType.getInteger(source, x),
								IntegerArgumentType.getInteger(source, y))))))));
		igiCommand.then(Commands.literal(strAlign).then(Commands.literal(strSet).then(Commands.literal(Names.Command.MIDDLE_CENTER)
				.then(Commands.argument(x, IntegerArgumentType.integer()).then(Commands.argument(y, IntegerArgumentType.integer())
						.executes(source -> SetAlignment(Names.Command.MIDDLE_CENTER, IntegerArgumentType.getInteger(source, x),
								IntegerArgumentType.getInteger(source, y))))))));
		igiCommand.then(Commands.literal(strAlign).then(Commands.literal(strSet).then(Commands.literal(Names.Command.MIDDLE_RIGHT)
				.then(Commands.argument(x, IntegerArgumentType.integer()).then(Commands.argument(y, IntegerArgumentType.integer())
						.executes(source -> SetAlignment(Names.Command.MIDDLE_RIGHT, IntegerArgumentType.getInteger(source, x),
								IntegerArgumentType.getInteger(source, y))))))));
		igiCommand.then(Commands.literal(strAlign).then(Commands.literal(strSet).then(Commands.literal(Names.Command.BOTTOM_LEFT)
				.then(Commands.argument(x, IntegerArgumentType.integer()).then(Commands.argument(y, IntegerArgumentType.integer())
						.executes(source -> SetAlignment(Names.Command.BOTTOM_LEFT, IntegerArgumentType.getInteger(source, x),
								IntegerArgumentType.getInteger(source, y))))))));
		igiCommand.then(Commands.literal(strAlign).then(Commands.literal(strSet).then(Commands.literal(Names.Command.BOTTOM_CENTER)
				.then(Commands.argument(x, IntegerArgumentType.integer()).then(Commands.argument(y, IntegerArgumentType.integer())
						.executes(source -> SetAlignment(Names.Command.BOTTOM_CENTER, IntegerArgumentType.getInteger(source, x),
								IntegerArgumentType.getInteger(source, y))))))));
		igiCommand.then(Commands.literal(strAlign).then(Commands.literal(strSet).then(Commands.literal(Names.Command.BOTTOM_RIGHT)
				.then(Commands.argument(x, IntegerArgumentType.integer()).then(Commands.argument(y, IntegerArgumentType.integer())
						.executes(source -> SetAlignment(Names.Command.BOTTOM_RIGHT, IntegerArgumentType.getInteger(source, x),
								IntegerArgumentType.getInteger(source, y))))))));
		// igi setseed -2793514409027566328
		igiCommand.then(Commands.literal("setseed").then(Commands.argument(seed, LongArgumentType.longArg())
				.executes(source -> SetSeed(LongArgumentType.getLong(source, seed)))));
//		// igi load ingameinfo.xml
		igiCommand.then(Commands.literal("load").then(Commands.literal(strDefault)
				.executes(source -> loadFile(strDefault))));
		final File[] files = InGameInfoCore.INSTANCE.getConfigDirectory().listFiles((File dir, String name) -> {
			return name.toLowerCase().startsWith(Names.Files.NAME.toLowerCase()) && (name.endsWith(Names.Files.EXT_XML)
					|| name.endsWith(Names.Files.EXT_JSON) || name.endsWith(Names.Files.EXT_TXT));
		});
		for (final File file : files) {
			final String strFileName = file.getName();
			// why?
			igiCommand.then(Commands.literal("load").then(Commands.literal(strFileName).executes(source -> loadFile(strFileName))));
		}
		dispatcher.register(igiCommand);
	}

	public static int SetAlignment(String alignment, int x, int y) {
		String alignValue = String.format("%s %s", x, y);
		Alignment align = Alignment.parse(alignment);
		Minecraft mc = Minecraft.getInstance();
		if (align == null) {
			mc.gui.getChat().addMessage(Component.translatable(Names.Command.Message.ALIGNMENT_SET_FAILURE, alignment));
			return -1;
		}
		align.setXY(alignValue);
		ConfigurationHandler.applyConfiguration();
		mc.gui.getChat().addMessage(Component.translatable(Names.Command.Message.ALIGNMENT_SET_SUCCESS, alignment, align.x, align.y));
		return 1;
	}

	public static int GetAlignment(String alignment) {
		Alignment align = Alignment.parse(alignment);
		Minecraft mc = Minecraft.getInstance();
		if (align == null) {
			mc.gui.getChat().addMessage(Component.translatable(Names.Command.Message.ALIGNMENT_GET_FAILURE, alignment));
			return -1;
		}
		mc.gui.getChat().addMessage(Component.translatable(Names.Command.Message.ALIGNMENT_GET_SUCCESS, alignment, align.x, align.y));
		return 1;
	}
	
	public static int SetSeed(long seed) {
		Minecraft mc = Minecraft.getInstance();
		try {
			ConfigurationHandler.seed = seed;
			ConfigurationHandler.applyConfiguration();
			mc.gui.getChat().addMessage(Component.translatable(Names.Command.Message.SED_SEET_SUCCESS, seed));
		} catch (final Exception e) {
			mc.gui.getChat().addMessage(Component.translatable(Names.Command.Message.SED_SEET_FAILURE));
			return -1;
		}
		return 1;
	}
	
	public static int loadFile(String filename) {
		Minecraft mc = Minecraft.getInstance();
		mc.gui.getChat().addMessage(Component.translatable(Names.Command.Message.LOAD, filename));
    	InGameInfoCore core = InGameInfoCore.INSTANCE;
    	Path configPath = FMLPaths.CONFIGDIR.get();
		Path modConfigPath = Paths.get(configPath.toAbsolutePath().toString());
    	
    	core.setConfigDirectory(modConfigPath.toFile());
		core.setConfigFile(filename);
		ConfigurationHandler.configName = filename;
		ConfigurationHandler.applyConfiguration();
		boolean b = core.reloadConfig();
		if (!b) {
			mc.gui.getChat().addMessage(Component.translatable(Names.Command.Message.FAILURE));
			return -1;
		}
		mc.gui.getChat().addMessage(Component.translatable(Names.Command.Message.SUCCESS));
		return 1;
	};
}
