

import java.math.BigInteger;
import java.util.BitSet;

public class DES_Encoder {
	int[] initialPermutationTable = { 58, 50, 42, 34, 26, 18, 10, 2, 60, 52, 44, 36, 28, 20, 12, 4, 62, 54, 46, 38, 30,
			22, 14, 6, 64, 56, 48, 40, 32, 24, 16, 8, 57, 49, 41, 33, 25, 17, 9, 1, 59, 51, 43, 35, 27, 19, 11, 3, 61,
			53, 45, 37, 29, 21, 13, 5, 63, 55, 47, 39, 31, 23, 15, 7 };
	int[] finalPermutationTable = { 40, 8, 48, 16, 56, 24, 64, 32, 39, 7, 47, 15, 55, 23, 63, 31, 38, 6, 46, 14, 54, 22,
			62, 30, 37, 5, 45, 13, 53, 21, 61, 29, 36, 4, 44, 12, 52, 20, 60, 28, 35, 3, 43, 11, 51, 19, 59, 27, 34, 2,
			42, 10, 50, 18, 58, 26, 33, 1, 41, 9, 49, 17, 57, 25 };
	int[] keyPermutationTable1 = { 57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18, 10, 2, 59, 51, 43, 35, 27, 19,
			11, 3, 60, 52, 44, 36, 63, 55, 47, 39, 31, 23, 15, 7, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53, 45, 37, 29, 21,
			13, 5, 28, 20, 12, 4 };
	int[] keyPermutationTable2 = { 14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10, 23, 19, 12, 4, 26, 8, 16, 7, 27, 20, 13,
			2, 41, 52, 31, 37, 47, 55, 30, 40, 51, 45, 33, 48, 44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29, 32 };
	int[] keyShiftTable = { 1, 2, 9, 16 };
	int[] fExpansionTable = { 32, 1, 2, 3, 4, 5, 4, 5, 6, 7, 8, 9, 8, 9, 10, 11, 12, 13, 12, 13, 14, 15, 16, 17, 16, 17,
			18, 19, 20, 21, 20, 21, 22, 23, 24, 25, 24, 25, 26, 27, 28, 29, 28, 29, 30, 31, 32, 1 };
	int[] fPermutationTable = { 16, 7, 20, 21, 29, 12, 28, 17, 1, 15, 23, 26, 5, 18, 31, 10, 2, 8, 24, 14, 32, 27, 3, 9,
			19, 13, 30, 6, 22, 11, 4, 25 };
	byte[][][] STable = {
			{ { 14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7 },
					{ 0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8 },
					{ 4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0 },
					{ 15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13 } },
			{ { 15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10 },
					{ 3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5 },
					{ 0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15 },
					{ 13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9 } },
			{ { 10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8 },
					{ 13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1 },
					{ 13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7 },
					{ 1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12 } },
			{ { 7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15 },
					{ 13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9 },
					{ 10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4 },
					{ 3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14 } },
			{ { 2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9 },
					{ 14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6 },
					{ 4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14 },
					{ 11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3 } },
			{ { 12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11 },
					{ 10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8 },
					{ 9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6 },
					{ 4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13 } },
			{ { 4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1 },
					{ 13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6 },
					{ 1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2 },
					{ 6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12 } },
			{ { 13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7 },
					{ 1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2 },
					{ 7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8 },
					{ 2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11 } } };

	public String run(String k, String w) { //converting to hex, dividing strings greater than 64bits and adding zeros
//		w = toHex(w);
//		while(w.charAt(0)=='0' && w.charAt(1)=='0') {
//			w = w.substring(2);
//		}
		
		String result = "";
		while (w.length() % 16 != 0) {
			w += "0";
		}
		int size = w.length() / 16;
		for (int i = 0; i < size; i++) {
			String sub = w.substring(i * 16, i * 16 + 16);
			sub = process(k, sub);
			result += sub;
		}
		return result;
	}

	private String process(String k, String w) {

		BitSet[] key = key(k);
		BitSet word = prepareBitSet(w);

		// FIRST PERMUTATION
		word = permutation(word, initialPermutationTable);

		// SPLIT TO 2
		BitSet L0 = new BitSet();
		BitSet R0 = new BitSet();
		for (int i = 0; i < 32; i++) {
			L0.set(i, word.get(i));
			R0.set(i, word.get(i + 32));
		}

		// ROUND 16
		word = round16(L0, R0, key);

		// FINAL PERMUTATION
		word = permutation(word, finalPermutationTable);

		// CONVERT TO HEX STRING
		word = reverse(word, 64);
		long hex = convertToLong(word);
		String result = Long.toHexString(hex);
		return result;
	}

	private BitSet[] key(String k) {

		BitSet[] result = new BitSet[16];
		BitSet keyBits = prepareBitSet(k);

		// FIRST PERMUTATION
		keyBits = permutation(keyBits, keyPermutationTable1);

		// SPLIT TO 2
		BitSet Ci = new BitSet();
		BitSet Di = new BitSet();
		for (int i = 0; i < 28; i++) {
			Ci.set(i, keyBits.get(i));
			Di.set(i, keyBits.get(i + 28));
		}

		// TRANSFORM
		for (int i = 0; i < 16; i++) {
			Ci = shift(Ci);
			Di = shift(Di);
			boolean check = true;
			for (int element : keyShiftTable) {
				if (element == i + 1) {
					check = false;
				}
			}
			if (check) {
				Ci = shift(Ci);
				Di = shift(Di);
			}
			keyBits = mergeBitSets(Ci, Di, 28);
			keyBits = permutation(keyBits, keyPermutationTable2);

			result[i] = keyBits;
		}
		return result;
	}

	private BitSet prepareBitSet(String str) {
		String str1 = str.substring(0, 8);
		String str2 = str.substring(8, 16);
		BitSet s1 = BitSet.valueOf(new long[] { Long.valueOf(str1, 16) });
		BitSet s2 = BitSet.valueOf(new long[] { Long.valueOf(str2, 16) });
		s1 = reverse(s1, 32);
		s2 = reverse(s2, 32);
		BitSet result = new BitSet();
		for (int i = 0; i < 32; i++) {
			result.set(i, s1.get(i));
			result.set(i + 32, s2.get(i));
		}
		return result;
	}

	private BitSet permutation(BitSet number, int[] permutationTable) { // to jest dobrze na 100%
		BitSet result = new BitSet();
		for (int i = 0; i < permutationTable.length; i++) {
			boolean a = number.get(permutationTable[i] - 1);
			result.set(i, a);
		}
		return result;
	}

	private BitSet round16(BitSet L0, BitSet R0, BitSet[] key) {
		BitSet result = new BitSet();
		BitSet Li = L0;
		BitSet Ri = R0;
		for (int i = 0; i < 16; i++) {
			BitSet tmp = Ri;
			Li.xor(fFunction(Ri, key[i]));
			Ri = Li;
			Li = tmp;
		}
		result = mergeBitSets(Ri, Li, 32);
		return result;
	}

	private BitSet fFunction(BitSet bits, BitSet key) {
		BitSet result = new BitSet();
		bits = permutation(bits, fExpansionTable);
		bits.xor(key);

		// split to 8
		BitSet[] bi = new BitSet[8];
		int index = 0;
		for (int i = 0; i < 8; i++) {
			bi[i] = new BitSet();
			for (int j = 0; j < 6; j++) {
				bi[i].set(j, bits.get(index));
				index++;
			}
		}

		// Sblock
		for (int i = 0; i < 8; i++) {
			int row = 0;
			int col = 0;
			if (bi[i].get(0))
				row += 2;
			if (bi[i].get(5))
				row += 1;
			if (bi[i].get(1))
				col += 8;
			if (bi[i].get(2))
				col += 4;
			if (bi[i].get(3))
				col += 2;
			if (bi[i].get(4))
				col += 1;
			byte value = STable[i][row][col];
			BitSet Si = BitSet.valueOf(new byte[] { value });
			Si = reverse(Si, 4);
			// Merge
			for (int j = 0; j < 4; j++) {
				result.set(i * 4 + j, Si.get(j));
			}
		}
		result = permutation(result, fPermutationTable);
		return result;
	}

	private BitSet reverse(BitSet bits, int size) {

		int R = size - 1;
		for (int L = 0; L < R; L++) {
			boolean leftBit = bits.get(L);
			bits.set(L, bits.get(R));
			bits.set(R, leftBit);
			R--;
		}
		return bits;
	}

	private BitSet shift(BitSet arg) {
		boolean check = false;
		if (arg.get(0) == true)
			check = true;
		arg = arg.get(1, arg.length());
		if (check)
			arg.set(27, true);
		return arg;
	}

	private BitSet mergeBitSets(BitSet b1, BitSet b2, int size) {
		BitSet result = new BitSet();
		for (int i = 0; i < size; i++) {
			result.set(i, b1.get(i));
			result.set(i + size, b2.get(i));
		}
		return result;
	}

	private long convertToLong(BitSet bits) {
		long value = 0L;
		for (int i = 0; i < bits.length(); ++i) {
			value += bits.get(i) ? (1L << i) : 0L;
		}
		return value;
	}

	private String toHex(String arg) {
		return String.format("%040x", new BigInteger(1, arg.getBytes(/* YOUR_CHARSET? */)));
	}

}
