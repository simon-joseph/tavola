package game;

/**
 * @author polchawa
 * 
 */
public class RandomIntsGenerator {
    private long[] mt = new long[624];
    private int index = 0;

    public RandomIntsGenerator(int seed) {
	mt[0] = seed;
	for (int i = 1; i <= 623; ++i) {
	    mt[i] = 1812433253L * (mt[i - 1] ^ mt[i - 1] >> 30) + i
		    & (1L << 32) - 1;
	}
    }

    public int nextInt() {
	if (index == 0) {
	    for (int i = 0; i <= 623; ++i) {
		long y = (mt[i] & 1L << 31)
			+ (mt[(i + 1) % 624] & (1 << 31) - 1);
		mt[i] = mt[(i + 397) % 624] ^ y >> 1;
		if (y % 2 == 1) {
		    mt[i] = mt[i] ^ 2567483615L;
		}
	    }
	}
	long y = mt[index];
	y ^= y >> 11;
	y ^= y << 7 & 2636928640L;
	y ^= y << 15 & 4022730752L;
	y ^= y >> 18;
	index = (index + 1) % 624;
	return (int) (y & (1L << 32) - 1);
    }

    public int nextInt(int maxValue) {
	int value = nextInt();
	if (value < 0) {
	    value = -value;
	    if (value < 0) {
		return 0;
	    }
	}
	return value % (maxValue + 1);
    }
}
