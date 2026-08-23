package df11zomgraves.ingameinfo.command.arguments;

import df11zomgraves.ingameinfo.reference.Names;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class InGameInfoArgumentTypes {
	public static final DeferredRegister<ArgumentTypeInfo<?, ?>> COMMAND_ARGUMENT_TYPES =
			DeferredRegister.create(BuiltInRegistries.COMMAND_ARGUMENT_TYPE, Names.MODID);
	
	public static final DeferredHolder<ArgumentTypeInfo<?, ?>, ArgumentTypeInfo<AlignArgument, ?>> ALIGNMENT =
			COMMAND_ARGUMENT_TYPES.register("alignment", () -> SingletonArgumentInfo.contextFree(AlignArgument::GetAlignment));
	public static final DeferredHolder<ArgumentTypeInfo<?, ?>, ArgumentTypeInfo<FileArgument, ?>> FILE =
			COMMAND_ARGUMENT_TYPES.register("filename", () -> SingletonArgumentInfo.contextFree(FileArgument::files));
	
	public static void registerArgumentTypes() {
		ArgumentTypeInfos.registerByClass(AlignArgument.class, ALIGNMENT.get());
		ArgumentTypeInfos.registerByClass(FileArgument.class, FILE.get());
	}
}
