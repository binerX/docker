package com.bin.yang.rest.api;

/**
 * @ClassName: com.bin.yang.rest.api.BitMapController
 * @Author: bin.yang
 * @Date: 2020/12/16 15:33
 * @Description: TODO
 */
public class BitMapController {

    /**
     * 创建bitmap数组
     */
    public byte[] create(int n){
        byte[] bits = new byte[getIndex(n) + 1];

        for(int i = 0; i < n; i++){

            if(i % 3 == 0){continue;}
            add(bits, i);
        }

        int index = 1;
        for(byte bit : bits){
            System.out.println("-------" + index++ + "-------");
            showByte(bit);

        }

        return bits;
    }

    /**
     * 标记指定数字（num）在bitmap中的值，标记其已经出现过<br/>
     * 将1左移position后，那个位置自然就是1，然后和以前的数据做|，这样，那个位置就替换成1了
     * @param bits
     * @param num
     */
    public void add(byte[] bits, int num){
        bits[getIndex(num)] |= 1 << getPosition(num);
    }

    /**
     * 判断指定数字num是否存在<br/>
     * 将1左移position后，那个位置自然就是1，然后和以前的数据做&，判断是否为0即可
     * @param bits
     * @param num
     * @return
     */
    public boolean contains(byte[] bits, int num){
        return (bits[getIndex(num)] & 1 << getPosition(num)) != 0;
    }

    /**
     * num/8得到byte[]的index
     * @param num
     * @return
     */
    public int getIndex(int num){
        return num >> 3;
    }

    /**
     * num%8得到在byte[index]的位置
     * @param num
     * @return
     */
    public int getPosition(int num){
        return num & 0x07;
    }

    /**
     * 重置某一数字对应在bitmap中的值<br/>
     * 对1进行左移，然后取反，最后与byte[index]作与操作。
     * @param bits
     * @param num
     */
    public void clear(byte[] bits, int num){
        bits[getIndex(num)] &= ~(1 << getPosition(num));
    }

    /**
     * 打印byte类型的变量<br/>
     * 将byte转换为一个长度为8的byte数组，数组每个值代表bit
     */

    public void showByte(byte b){
        byte[] array = new byte[8];
        for(int i = 7; i >= 0; i--){
            array[i] = (byte)(b & 1);
            b = (byte)(b >> 1);
        }

        for (byte b1 : array) {
            System.out.print(b1);
            System.out.print(" ");
        }

        System.out.println();
    }

/*    public static void main(String[] args) {
        int n = 100;
        new BitMapController().create(n);
    }


    public static void main(String[] args) {

        String s = Integer.toBinaryString(1024);
        System.out.println(s);
        System.out.println(s.length());


        int[] arr = {151,209,193,4,36,113,57,63,109,182,216,232,221,112,76,20,225,22,96,25,48,212,24,248,197,41,15,143,66,185,189,58,46,59,222,165,239,249,67,34,88,79,2,72,48,230,158,84,143,74,206,153,106,186,113,149,185,80,4,165,236,32,41,60,63,88,196,33,162,75,95,123,44,135,165,78,159,52,200,228,237,4,8,239,176,100,248,229,207,159,81,216,75,228,78,113,128,40,46,95};

        byte[] bitMap = createBitMap(arr);

        boolean existen = containsBit(9,bitMap);

        System.out.println(existen);

//        showBitMap(bitMap);
        System.out.println();
        System.out.println("--------------end-------");
        System.out.println();

//        int num = 2000000000;
//        int b = 2147483647 / 8;
//        int kb = b / 1024;
//        int mb = kb / 1024;
//        int g = mb / 1024;
//
//        long l = 2000000000;
//        long iB = l * 16;
//        long ib = iB / 8;
//        long ik = ib / 1024;
//        long im = ik / 1024;
//        long ig = im / 1024;
//
//        System.out.println("总数据 20亿");
//        System.out.println();
//        System.out.println("int[] ===================");
//        System.out.println(iB + "B");
//        System.out.println(ib + "b");
//        System.out.println(ik + "kb");
//        System.out.println(im + "m");
//        System.out.println(ig + "g");
//        System.out.println("BitMap ==================");
//        System.out.println(num + "B");
//        System.out.println(b + "b");
//        System.out.println(kb + "kb");
//        System.out.println(mb + "mb");
//        System.out.println(g + "g");

    }

    private static void showBitMap(byte[] bitMap) {

        int index = 0;
        for (int i = 0; i < bitMap.length; i++) {
            System.out.println("------"+ ++index +"------");
            showByte(bitMap[i]);
        }
    }

    private static void showByte(byte b) {
        byte[] bs = new byte[8];
        for (int i = 7; i >= 0; i--) {
            bs[i] = (byte) (b & 1);
            b =(byte) (b >> 1);
        }

        for (int i = 0; i < bs.length; i++) {
            System.out.print(bs[i]);
            System.out.print(" ");

        }
        System.out.println();
    }

    private static boolean containsBit(int num, byte[] bitMap) {
        int b = bitMap[getIndex(num)];
        int position = 1 << getPosition(num);
        int i = b & position;
        return i != 0;
//        return (bitMap[getIndex(num)] & 1 << getPosition(num) ) != 0;
    }

    private static byte[] createBitMap(int[] arr) {
        int max = 0;
        for (int i : arr) {
            if(i > max){
                max = i;
            }
        }
        byte[] bits = new byte[(max >> 3) +1];
        for (int i = 0; i < arr.length; i++) {
            addBits(bits,arr[i]);
        }
        return bits;
    }

    private static void addBits(byte[] bits, int num) {
        int index = getIndex(num);
        int position = getPosition(num);
        int i = 1 << position;
        bits[index] |= i;

        // bits[getIndex(num)] = bits[getIndex(num)] | 1 << getPosition(num);
        // bits[getIndex(num)] |= 1 << getPosition(num);
    }

    private static int getPosition(int num) {
        return num & 0x07;
    }

    private static int getIndex(int num) {
        return num >> 3;
    }*/


}
