package df11zomgraves.ingameinfo.command.arguments;

import df11zomgraves.ingameinfo.reference.Names;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InGameInfoArgumentTypes {
	public static final DeferredRegister<ArgumentTypeInfo<?, ?>> COMMAND_ARGUMENT_TYPES =
			DeferredRegister.create(ForgeRegistries.COMMAND_ARGUMENT_TYPES, Names.MODID);
	
	public static final RegistryObject<ArgumentTypeInfo<AlignArgument, ?>> ALIGNMENT =
			COMMAND_ARGUMENT_TYPES.register("alignment", () -> SingletonArgumentInfo.contextFree(AlignArgument::GetAlignment));
	public static final RegistryObject<ArgumentTypeInfo<FileArgument, ?>> FILE =
			COMMAND_ARGUMENT_TYPES.register("filename", () -> SingletonArgumentInfo.contextFree(FileArgument::files));
	
	public static void registerArgumentTypes() {
		ArgumentTypeInfos.registerByClass(AlignArgument.class, ALIGNMENT.get());
		ArgumentTypeInfos.registerByClass(FileArgument.class, FILE.get());
	}
}
