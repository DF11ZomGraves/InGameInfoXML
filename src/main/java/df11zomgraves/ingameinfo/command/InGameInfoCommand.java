package df11zomgraves.ingameinfo.command;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import df11zomgraves.ingameinfo.InGameInfoCore;
import df11zomgraves.ingameinfo.command.arguments.AlignArgument;
import df11zomgraves.ingameinfo.command.arguments.FileArgument;
import df11zomgraves.ingameinfo.handler.ConfigurationHandler;
import df11zomgraves.ingameinfo.reference.Alignment;
import df11zomgraves.ingameinfo.reference.Names;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.fml.loading.FMLPaths;

public class InGameInfoCommand {

	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		final String strAlign = Names.Command.ALIENMENT;
		final String strGet = Names.Command.GET;
		final String strSet = Names.Command.SET;
		final String x = "X";
		final String y = "Y";
		final String seed = "Seed";
		final String filename = "filename";
		
		LiteralArgumentBuilder<CommandSourceStack> igiCommand = Commands.literal(Names.Command.NAME);
		// igi alignment get TOPLEFT
		igiCommand.then(Commands.literal(strAlign).then(Commands.literal(strGet).then(Commands.argument(strAlign, AlignArgument.GetAlignment())
				.executes(source -> GetAlignment(AlignArgument.GetString(source, strAlign))))));
		// igi alignment set TOPLEFT 2 2
		igiCommand.then(Commands.literal(strAlign).then(Commands.literal(strSet).then(Commands.argument(strAlign, AlignArgument.GetAlignment())
				.then(Commands.argument(x, IntegerArgumentType.integer()).then(Commands.argument(y, IntegerArgumentType.integer())
						.executes(source -> SetAlignment(AlignArgument.GetString(source, strAlign), IntegerArgumentType.getInteger(source, x),
								IntegerArgumentType.getInteger(source, y))))))));
		// igi setseed -2793514409027566328
		igiCommand.then(Commands.literal("setseed").then(Commands.argument(seed, LongArgumentType.longArg())
				.executes(source -> SetSeed(LongArgumentType.getLong(source, seed)))));
		// igi load ingameinfo.xml
		igiCommand.then(Commands.literal("load").then(Commands.argument(filename, FileArgument.files())
				.executes(source -> loadFile(FileArgument.GetString(source, filename)))));
		
		dispatcher.register(igiCommand);
	}

	public static int SetAlignment(String alignment, int x, int y) {
		String alignValue = String.format("%s %s", x, y);
		Alignment align = Alignment.parse(alignment);
		Minecraft mc = Minecraft.getInstance();
		if (align == null) {
			mc.gui.getChat().addMessage(new TranslatableComponent(Names.Command.Message.ALIGNMENT_SET_FAILURE, alignment));
			return -1;
		}
		align.setXY(alignValue);
		ConfigurationHandler.applyConfiguration(true);
		mc.gui.getChat().addMessage(new TranslatableComponent(Names.Command.Message.ALIGNMENT_SET_SUCCESS, alignment, align.x, align.y));
		return 1;
	}

	public static int GetAlignment(String alignment) {
		Alignment align = Alignment.parse(alignment);
		Minecraft mc = Minecraft.getInstance();
		if (align == null) {
			mc.gui.getChat().addMessage(new TranslatableComponent(Names.Command.Message.ALIGNMENT_GET_FAILURE, alignment));
			return -1;
		}
		mc.gui.getChat().addMessage(new TranslatableComponent(Names.Command.Message.ALIGNMENT_GET_SUCCESS, alignment, align.x, align.y));
		return 1;
	}
	
	public static int SetSeed(long seed) {
		Minecraft mc = Minecraft.getInstance();
		try {
			ConfigurationHandler.seed = seed;
			ConfigurationHandler.applyConfiguration(true);
			mc.gui.getChat().addMessage(new TranslatableComponent(Names.Command.Message.SED_SEET_SUCCESS, seed));
		} catch (final Exception e) {
			mc.gui.getChat().addMessage(new TranslatableComponent(Names.Command.Message.SED_SEET_FAILURE));
			return -1;
		}
		return 1;
	}
	
	public static int loadFile(String filename) {
		Minecraft mc = Minecraft.getInstance();
		mc.gui.getChat().addMessage(new TranslatableComponent(Names.Command.Message.LOAD));
    	InGameInfoCore core = InGameInfoCore.INSTANCE;
    	Path configPath = FMLPaths.CONFIGDIR.get();
		Path modConfigPath = Paths.get(configPath.toAbsolutePath().toString());
    	
    	core.setConfigDirectory(modConfigPath.toFile());
		core.setConfigFile(filename);
		ConfigurationHandler.configName = filename;
		ConfigurationHandler.applyConfiguration(true);
		boolean b = core.reloadConfig();
		if (!b) {
			mc.gui.getChat().addMessage(new TranslatableComponent(Names.Command.Message.FAILURE));
			return -1;
		}
		mc.gui.getChat().addMessage(new TranslatableComponent(Names.Command.Message.SUCCESS));
		return 1;
	};
}
