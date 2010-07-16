package edu.sumdu.dl.common;

public final class Base64 {

    public Base64() {
    }

    public static synchronized byte[] decode(byte abyte0[]) {
        if (abyte0 == null) {
            return null;
        }
        byte abyte1[] = removeWhiteSpace(abyte0);
        if (abyte1.length % 4 != 0) {
            return null;
        }
        int i = abyte1.length / 4;
        if (i == 0) {
            return new byte[0];
        }
        byte abyte2[] = null;
        byte byte0 = 0;
        byte byte1 = 0;
        boolean flag = false;
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        byte byte7 = 0;
        byte byte8 = 0;
        byte byte9 = 0;
        byte byte10 = 0;
        int j = 0;
        int k = 0;
        int l = 0;
        abyte2 = new byte[i * 3];
        for (; j < i - 1; j++) {
            if (!isData(byte7 = abyte1[l++]) || !isData(byte8 = abyte1[l++]) || !isData(byte9 = abyte1[l++]) || !isData(byte10 = abyte1[l++])) {
                return null;
            }
            byte0 = base64Alphabet[byte7];
            byte1 = base64Alphabet[byte8];
            byte byte2 = base64Alphabet[byte9];
            byte byte5 = base64Alphabet[byte10];
            abyte2[k++] = (byte) (byte0 << 2 | byte1 >> 4);
            abyte2[k++] = (byte) ((byte1 & 0xf) << 4 | byte2 >> 2 & 0xf);
            abyte2[k++] = (byte) (byte2 << 6 | byte5);
        }

        if (!isData(byte7 = abyte1[l++]) || !isData(byte8 = abyte1[l++])) {
            return null;
        }
        byte0 = base64Alphabet[byte7];
        byte1 = base64Alphabet[byte8];
        byte9 = abyte1[l++];
        byte10 = abyte1[l++];
        if (!isData(byte9) || !isData(byte10)) {
            if (isPad(byte9) && isPad(byte10)) {
                if ((byte1 & 0xf) != 0) {
                    return null;
                } else {
                    byte abyte3[] = new byte[j * 3 + 1];
                    System.arraycopy(abyte2, 0, abyte3, 0, j * 3);
                    abyte3[k] = (byte) (byte0 << 2 | byte1 >> 4);
                    return abyte3;
                }
            }
            if (!isPad(byte9) && isPad(byte10)) {
                byte byte3 = base64Alphabet[byte9];
                if ((byte3 & 3) != 0) {
                    return null;
                } else {
                    byte abyte4[] = new byte[j * 3 + 2];
                    System.arraycopy(abyte2, 0, abyte4, 0, j * 3);
                    abyte4[k++] = (byte) (byte0 << 2 | byte1 >> 4);
                    abyte4[k] = (byte) ((byte1 & 0xf) << 4 | byte3 >> 2 & 0xf);
                    return abyte4;
                }
            } else {
                return null;
            }
        } else {
            byte byte4 = base64Alphabet[byte9];
            byte byte6 = base64Alphabet[byte10];
            abyte2[k++] = (byte) (byte0 << 2 | byte1 >> 4);
            abyte2[k++] = (byte) ((byte1 & 0xf) << 4 | byte4 >> 2 & 0xf);
            abyte2[k++] = (byte) (byte4 << 6 | byte6);
            return abyte2;
        }
    }

    public static synchronized byte[] encode(byte abyte0[]) {
        if (abyte0 == null) {
            return null;
        }
        int i = abyte0.length * 8;
        int j = i % 24;
        int k = i / 24;
        byte abyte1[] = null;
        if (j != 0) {
            abyte1 = new byte[(k + 1) * 4];
        } else {
            abyte1 = new byte[k * 4];
        }
        boolean flag = false;
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        boolean flag4 = false;
        int l = 0;
        int i1 = 0;
        int j1 = 0;
        for (j1 = 0; j1 < k; j1++) {
            i1 = j1 * 3;
            byte byte5 = abyte0[i1];
            byte byte8 = abyte0[i1 + 1];
            byte byte10 = abyte0[i1 + 2];
            byte byte3 = (byte) (byte8 & 0xf);
            byte byte0 = (byte) (byte5 & 3);
            l = j1 * 4;
            byte byte11 = (byte5 & 0xffffff80) != 0 ? (byte) (byte5 >> 2 ^ 0xc0) : (byte) (byte5 >> 2);
            byte byte14 = (byte8 & 0xffffff80) != 0 ? (byte) (byte8 >> 4 ^ 0xf0) : (byte) (byte8 >> 4);
            byte byte16 = (byte10 & 0xffffff80) != 0 ? (byte) (byte10 >> 6 ^ 0xfc) : (byte) (byte10 >> 6);
            abyte1[l] = lookUpBase64Alphabet[byte11];
            abyte1[l + 1] = lookUpBase64Alphabet[byte14 | byte0 << 4];
            abyte1[l + 2] = lookUpBase64Alphabet[byte3 << 2 | byte16];
            abyte1[l + 3] = lookUpBase64Alphabet[byte10 & 0x3f];
        }

        i1 = j1 * 3;
        l = j1 * 4;
        if (j == 8) {
            byte byte6 = abyte0[i1];
            byte byte1 = (byte) (byte6 & 3);
            byte byte12 = (byte6 & 0xffffff80) != 0 ? (byte) (byte6 >> 2 ^ 0xc0) : (byte) (byte6 >> 2);
            abyte1[l] = lookUpBase64Alphabet[byte12];
            abyte1[l + 1] = lookUpBase64Alphabet[byte1 << 4];
            abyte1[l + 2] = 61;
            abyte1[l + 3] = 61;
        } else if (j == 16) {
            byte byte7 = abyte0[i1];
            byte byte9 = abyte0[i1 + 1];
            byte byte4 = (byte) (byte9 & 0xf);
            byte byte2 = (byte) (byte7 & 3);
            byte byte13 = (byte7 & 0xffffff80) != 0 ? (byte) (byte7 >> 2 ^ 0xc0) : (byte) (byte7 >> 2);
            byte byte15 = (byte9 & 0xffffff80) != 0 ? (byte) (byte9 >> 4 ^ 0xf0) : (byte) (byte9 >> 4);
            abyte1[l] = lookUpBase64Alphabet[byte13];
            abyte1[l + 1] = lookUpBase64Alphabet[byte15 | byte2 << 4];
            abyte1[l + 2] = lookUpBase64Alphabet[byte4 << 2];
            abyte1[l + 3] = 61;
        }
        return abyte1;
    }

    public static synchronized int getDecodedDataLength(byte abyte0[]) {
        if (abyte0 == null) {
            return -1;
        }
        if (abyte0.length == 0) {
            return 0;
        }
        byte abyte1[] = null;
        if ((abyte1 = decode(abyte0)) == null) {
            return -1;
        } else {
            return abyte1.length;
        }
    }

    public static synchronized boolean isArrayByteBase64(byte abyte0[]) {
        return getDecodedDataLength(abyte0) >= 0;
    }

    public static boolean isBase64(byte byte0) {
        return isWhiteSpace(byte0) || isPad(byte0) || isData(byte0);
    }

    public static boolean isBase64(String s) {
        if (s == null) {
            return false;
        } else {
            return isArrayByteBase64(s.getBytes());
        }
    }

    protected static boolean isData(byte byte0) {
        return base64Alphabet[byte0] != -1;
    }

    protected static boolean isPad(byte byte0) {
        return byte0 == 61;
    }

    protected static boolean isWhiteSpace(byte byte0) {
        return byte0 == 32 || byte0 == 13 || byte0 == 10 || byte0 == 9;
    }

    public static synchronized byte[] removeWhiteSpace(byte abyte0[]) {
        if (abyte0 == null) {
            return null;
        }
        int i = 0;
        int j = abyte0.length;
        for (int k = 0; k < j; k++) {
            if (!isWhiteSpace(abyte0[k])) {
                i++;
            }
        }

        if (i == j) {
            return abyte0;
        }
        byte abyte1[] = new byte[i];
        int i1 = 0;
        for (int l = 0; l < j; l++) {
            if (!isWhiteSpace(abyte0[l])) {
                abyte1[i1++] = abyte0[l];
            }
        }

        return abyte1;
    }
    private static final int BASELENGTH = 255;
    private static final int LOOKUPLENGTH = 64;
    private static final int TWENTYFOURBITGROUP = 24;
    private static final int EIGHTBIT = 8;
    private static final int SIXTEENBIT = 16;
    private static final int SIXBIT = 6;
    private static final int FOURBYTE = 4;
    private static final int SIGN = -128;
    private static final byte PAD = 61;
    private static final boolean fDebug = false;
    private static byte base64Alphabet[];
    private static byte lookUpBase64Alphabet[];

    static {
        base64Alphabet = new byte[255];
        lookUpBase64Alphabet = new byte[64];
        for (int i = 0; i < 255; i++) {
            base64Alphabet[i] = -1;
        }

        for (int j = 90; j >= 65; j--) {
            base64Alphabet[j] = (byte) (j - 65);
        }

        for (int k = 122; k >= 97; k--) {
            base64Alphabet[k] = (byte) ((k - 97) + 26);
        }

        for (int l = 57; l >= 48; l--) {
            base64Alphabet[l] = (byte) ((l - 48) + 52);
        }

        base64Alphabet[43] = 62;
        base64Alphabet[47] = 63;
        for (int i1 = 0; i1 <= 25; i1++) {
            lookUpBase64Alphabet[i1] = (byte) (65 + i1);
        }

        int j1 = 26;
        for (int k1 = 0; j1 <= 51; k1++) {
            lookUpBase64Alphabet[j1] = (byte) (97 + k1);
            j1++;
        }

        int l1 = 52;
        for (int i2 = 0; l1 <= 61; i2++) {
            lookUpBase64Alphabet[l1] = (byte) (48 + i2);
            l1++;
        }

        lookUpBase64Alphabet[62] = 43;
        lookUpBase64Alphabet[63] = 47;
    }
}
