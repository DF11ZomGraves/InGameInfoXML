package df11zomgraves.ingameinfo.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class MBlockPos extends BlockPos {
	public int x;
	public int y;
	public int z;

	public MBlockPos() {
		this(0, 0, 0);
	}

	public MBlockPos(final Entity source) {
		this(source.getBlockX(), source.getBlockY(), source.getBlockZ());
	}

	public MBlockPos(final Vec3i source) {
		this(source.getX(), source.getY(), source.getZ());
	}

	public MBlockPos(final int x, final int y, final int z) {
		super(0, 0, 0);
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public MBlockPos set(final Entity source) {
		return set(source.getBlockX(), source.getBlockY(), source.getBlockZ());
	}

	public MBlockPos set(final Vec3i source) {
		return set(source.getX(), source.getY(), source.getZ());
	}

	public MBlockPos set(final double x, final double y, final double z) {
		return set(Mth.floor(x), Mth.floor(y), Mth.floor(z));
	}

	public MBlockPos set(final int x, final int y, final int z) {
	    this.x = x;
	    this.y = y;
	    this.z = z;
	    return this;
	}

	@Override
	public int getX() {
		return this.x;
	}

	@Override
	public int getY() {
		return this.y;
	}

	@Override
	public int getZ() {
		return this.z;
	}
}
