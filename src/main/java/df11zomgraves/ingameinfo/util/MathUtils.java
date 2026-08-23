package df11zomgraves.ingameinfo.util;

public class MathUtils {
    public static long mean(long[] values)
    {
        long sum = 0L;
        for (long v : values)
            sum += v;
        return sum / values.length;
    }
}
