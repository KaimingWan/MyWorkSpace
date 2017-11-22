package jdk8;

/**
 * 存放8位（pos范围是0-255）的bit map实现。
 */
public class BitArray {

    private byte bitX8[] = null;

    public BitArray(int size) {
        bitX8 = new byte[size / 8 + (size % 8 == 0 ? 0 : 1)];
    }

    public boolean getBit(int pos) {
        return (bitX8[pos / 8] & (1 << (pos % 8))) != 0;
    }

    public void setBit(int pos, boolean b) {
        byte b8 = bitX8[pos / 8];
        byte posBit = (byte) (1 << (pos % 8));
        if (b) {
            b8 |= posBit;
        } else {
            b8 &= (255 - posBit);
        }
        bitX8[pos / 8] = b8;
    }

    public static void main(String[] args) {
        BitArray bitArray = new BitArray(10);
        bitArray.setBit(5,true);
        System.out.println(bitArray.getBit(5));

    }

}