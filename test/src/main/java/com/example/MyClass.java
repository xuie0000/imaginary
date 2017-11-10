package com.example;


import java.io.UnsupportedEncodingException;

public class MyClass {
    private static final String TAG = "MyClass";

    public static void main(String[] args) {
        System.out.println("hello.");
//        String dateString = "2017-08-11T14:05:53.749Z";
        // 4e0d 7528 53bb 731c
        byte[] bTest = {0x4e, 0x0d, 0x75, 0x28, 0x53, (byte) 0xbb, 0x73, 0x1c};
//        byte[] bSong = {0x00, 0x4A, 0x00, 0x6F, 0x00, 0x6E, 0x00, 0x79, 0x00, 0x20, 0x00, 0x4A, 0x00, 0x20, 0x00, 0x2D, 0x00, 0x20, 0x4E, 0x0D, 0x75, 0x28, 0x53, (byte) 0xBB, 0x73, 0x1C};
        // 00 03 00 02 00 4A 00 6F 00 6E 00 79 00 20 00 4A 00 20 00 2D 00 20 4E 0D 75 28 53 BB 73 1C 00 2E 00
        // FC 00 00 1A 81 17 0D 00 02 54 68 4F 20 96 C4 00 2D 82 B1 99 99 00 2E 00 61 00 70 F9 FE
        byte[] bSong = {0x54, 0x68, 0x4F, 0x20, (byte) 0x96, (byte) 0xC4, 0x00, 0x2D, (byte) 0x82, (byte) 0xB1, (byte) 0x99, (byte) 0x99, 0x00, 0x2E, 0x00, 0x61, 0x00, 0x70};
        // FC 00 00 28 81 17 0D 00 03 00 4A 00 6F 00 6E 00 79 00 20 00 4A 00 20 00 2D 00 20 4E 0D 75 28 53 BB
        byte[] bSong2 = {0x00, 0x4A, 0x00, 0x6F, 0x00, 0x6E, 0x00, 0x79, 0x00, 0x20, 0x00, 0x4A, 0x00, 0x20, 0x00, 0x2D, 0x00, 0x20, 0x4E, 0x0D, 0x75, 0x28, 0x53, (byte) 0xBB};
        try {
            System.out.println(new String(bTest, "UNICODE"));
            System.out.println(new String(bSong, "UNICODE"));
            System.out.println(new String(bSong2, "UNICODE"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public static String toString(byte[] var0) {
        if (var0 == null) {
            return "null";
        } else {
            int var1 = var0.length - 1;
            if (var1 == -1) {
                return "[]";
            } else {
                StringBuilder var2 = new StringBuilder();
                var2.append('[');
                int var3 = 0;

                while (true) {
                    var2.append(var0[var3] & 0xFF);
                    if (var3 == var1) {
                        return var2.append(']').toString();
                    }

                    var2.append(", ");
                    ++var3;
                }
            }
        }
    }
}
