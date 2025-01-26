package df11zomgraves.ingameinfo.tag;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class TagMouseOver extends Tag {
	@Override
	public String getCategory() {
		return "mouseover";
	}

	public static class Name extends TagMouseOver {
		@Override
		public String getValue() {
			final HitResult hitResult = minecraft.hitResult;
			if (hitResult != null) {
				final Type hitType = hitResult.getType();
				if (hitType == HitResult.Type.ENTITY) {
					final EntityHitResult targetEntity = (EntityHitResult) hitResult;
					return targetEntity.getEntity().getName().getString();
				} else if (hitType == HitResult.Type.BLOCK) {
					final BlockHitResult targetBlock = (BlockHitResult) hitResult;
					final BlockPos pos = targetBlock.getBlockPos();
					final BlockState blockState = world.getBlockState(pos);
					final Block block = blockState.getBlock();
					return block.getName().getString();
				}
			}
			return "";
		}
	}

	public static class UniqueName extends TagMouseOver {
		@Override
		public String getValue() {
			final HitResult hitResult = minecraft.hitResult;
			if (hitResult != null) {
				final Type hitType = hitResult.getType();
				ResourceLocation res;
				if (hitType == HitResult.Type.ENTITY) {
					final EntityHitResult targetEntity = (EntityHitResult) hitResult;
					res = ForgeRegistries.ENTITY_TYPES.getKey(targetEntity.getEntity().getType());
					return res.toString();
				} else if (hitType == HitResult.Type.BLOCK) {
					final BlockHitResult targetBlock = (BlockHitResult) hitResult;
					final BlockPos pos = targetBlock.getBlockPos();
					final BlockState blockState = world.getBlockState(pos);
					res = ForgeRegistries.BLOCKS.getKey(blockState.getBlock());
					return res.toString();
				}
			}
			return "";
		}
	}

	public static class Id extends TagMouseOver {
		@Override
		public String getValue() {
			final HitResult hitResult = minecraft.hitResult;
			if (hitResult != null) {
				final Type hitType = hitResult.getType();
				if (hitType == HitResult.Type.ENTITY) {
					final EntityHitResult targetEntity = (EntityHitResult) hitResult;
					return String.valueOf(targetEntity.getEntity().getId());
				}
			}
			return "0";
		}
	}

//	public static class Metadata extends TagMouseOver {
//		@Override
//		public String getValue() {
//			final RayTraceResult objectMouseOver = minecraft.objectMouseOver;
//			if (objectMouseOver != null) {
//				if (objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK) {
//					final IBlockState blockState = world.getBlockState(objectMouseOver.getBlockPos());
//					return String.valueOf(blockState.getBlock().getMetaFromState(blockState));
//				}
//			}
//			return "0";
//		}
//	}

	public static class IsBlock extends TagMouseOver {
		@Override
		public String getValue() {
			final HitResult hitResult = minecraft.hitResult;
			final Type hitType = hitResult.getType();
			return String.valueOf(hitType == HitResult.Type.BLOCK);
		}
	}

	public static class PowerWeak extends TagMouseOver {
		@Override
		public String getValue() {
			final HitResult hitResult = minecraft.hitResult;
			if (hitResult != null) {
				final Type hitType = hitResult.getType();
				if (hitType == HitResult.Type.BLOCK) {
					int power = -1;
					for (final Direction side : Direction.values()) {
						final BlockHitResult targetBlock = (BlockHitResult) hitResult;
						final BlockPos pos = targetBlock.getBlockPos();
						power = Math.max(power, world.getDirectSignal(pos, side));
						if (power >= 15)
							break;
					}
					return String.valueOf(power);
				}
			}
			return "-1";
		}
	}

	public static class PowerStrong extends TagMouseOver {
		@Override
		public String getValue() {
			final HitResult hitResult = minecraft.hitResult;
			if (hitResult != null) {
				final Type hitType = hitResult.getType();
				if (hitType == HitResult.Type.BLOCK) {
					int power = -1;
					final BlockHitResult targetBlock = (BlockHitResult) hitResult;
					final BlockPos pos = targetBlock.getBlockPos();
					for (final Direction side : Direction.values()) {
						power = Math.max(power, world.getDirectSignal(pos, side));
						if (power >= 15)
							break;
					}
					return String.valueOf(power);
				}
			}
			return "-1";
		}
	}

//	public static class PowerInput extends TagMouseOver {
//		@Override
//		public String getValue() {
//			final RayTraceResult objectMouseOver = minecraft.objectMouseOver;
//			if (objectMouseOver != null) {
//				if (objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK) {
//					return String.valueOf(world.isBlockIndirectlyGettingPowered(objectMouseOver.getBlockPos()));
//				}
//			}
//			return "-1";
//		}
//	}
	
	public static class LookingAtXYZ extends TagMouseOver {
		@Override
		public String getValue() {
			final HitResult hitResult = minecraft.hitResult;
			if (hitResult != null) {
				final Type hitType = hitResult.getType();
				if (hitType == HitResult.Type.BLOCK) {
					final BlockHitResult targetBlock = (BlockHitResult) hitResult;
					final BlockPos pos = targetBlock.getBlockPos();
					return String.format("%s, %s, %s", pos.getX(), pos.getY(), pos.getZ());
				}
			}
			return "";
		}
	}


	public static void register() {
		TagRegistry.INSTANCE.register(new Name().setName("mouseovername"));
		TagRegistry.INSTANCE.register(new UniqueName().setName("mouseoveruniquename"));
		TagRegistry.INSTANCE.register(new PowerWeak().setName("mouseoverpowerweak"));
		TagRegistry.INSTANCE.register(new PowerStrong().setName("mouseoverpowerstrong"));
		TagRegistry.INSTANCE.register(new IsBlock().setName("mouseoverisblock"));
		TagRegistry.INSTANCE.register(new LookingAtXYZ().setName("lookingblockxyz"));
		TagRegistry.INSTANCE.register(new Id().setName("mouseoverid"));
//		TagRegistry.INSTANCE.register(new Metadata().setName("mouseovermetadata"));
//		TagRegistry.INSTANCE.register(new PowerInput().setName("mouseoverpowerinput"));
	}
}
