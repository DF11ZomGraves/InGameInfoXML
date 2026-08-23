package df11zomgraves.ingameinfo.util;

import java.util.Random;

import net.minecraft.core.BlockPos;

public class ChunkHelper {
    private static final Random RANDOM = new Random();

    public static boolean isSlimeChunk(final long seed, final BlockPos pos) {
        final int x = pos.getX() >> 4;
        final int z = pos.getZ() >> 4;
        RANDOM.setSeed(seed + (long) (x * x * 4987142) + (long) (x * 5947611) + (long) (z * z) * 4392871L + (long) (z * 389711) ^ 987234911L);
        return RANDOM.nextInt(10) == 0;
    }
}
