package com.ody.usb.Classes.Shared;

public class ESCPOS {
    public static final byte ESC = 27;
    public static final byte FS = 28;
    public static final byte GS = 29;
    public static final byte DLE = 16;
    public static final byte EOT = 4;
    public static final byte ENQ = 5;
    public static final byte SP = 32;
    public static final byte HT = 9;
    public static final byte LF = 10;
    public static final byte CR = 13;
    public static final byte FF = 12;
    public static final byte CAN = 24;
    public static final int LK_STS_PRINTEROFF = 128;
    public static final int LK_STS_MSR_READ = 64;
    public static final int LK_STS_PAPER_EMPTY = 32;
    public static final int LK_STS_COVER_OPEN = 16;
    public static final int LK_STS_BATTERY_LOW = 8;
    public static final int LK_STS_NORMAL = 0;
    public static final int TRACK_1 = 49;
    public static final int TRACK_2 = 50;
    public static final int TRACK_1_2 = 51;
    public static final int TRACK_3 = 52;
    public static final int TRACK_2_3 = 54;

    public byte[] ESC_EXCLAMATION(int n)
    {
        return EPCommand((byte)27, '!', n);
    }

    public byte[] GS_B(int n)
    {
        return EPCommand((byte)29, 'B', n);
    }

    public byte[] ESC_a(int n)
    {
        return EPCommand((byte)27, 'a', n);
    }

    public byte[] ESC_cut()
    {
        return EPCommand((byte)29, 'V', 65 , 3);
    }

    public byte[] ESC_AT()
    {
        return EPCommand((byte)27, '@', 0);
    }

    public byte[] ESC_M(int n)
    {
        return EPCommand((byte)27, 'M', n);
    }

    public byte[] GS_EXCLAMATION(int n)
    {
        return EPCommand((byte)29, '!', n);
    }

    public byte[] ESC_HYPHEN(int n)
    {
        return EPCommand((byte)27, '-', n);
    }

    public byte[] ESC_E(int n)
    {
        return EPCommand((byte)27, 'E', n);
    }

    public byte[] GS_SLASH(int m)
    {
        return EPCommand((byte)29, '/', m);
    }

    public byte[] ESC_d(int n)
    {
        return EPCommand((byte)27, 'd', n);
    }

    public byte[] ESC_J(int n)
    {
        return EPCommand((byte)27, 'J', n);
    }

    public byte[] GS_v(int m, int xL, int xH, int yL, int yH, byte[] buf)
    {
        int length = buf.length;
        byte[] command = new byte[8 + length];
        command[0] = 29;
        command[1] = 118;
        command[2] = 48;
        command[3] = ((byte)m);
        command[4] = ((byte)xL);
        command[5] = ((byte)xH);
        command[6] = ((byte)yL);
        command[7] = ((byte)yH);
        System.arraycopy(buf, 0, command, 8, length);
        return command;
    }

    private byte[] EPCommand(byte group, char select)
    {
        byte[] result = new byte[2];
        result[0] = group;
        result[1] = ((byte)select);
        return result;
    }

    private byte[] EPCommand(byte group, char select, int n)
    {
        byte[] result = new byte[3];
        result[0] = group;
        result[1] = ((byte)select);
        result[2] = ((byte)n);
        return result;
    }

    private byte[] EPCommand(byte group, char select, int n, int m)
    {
        byte[] result = new byte[4];
        result[0] = group;
        result[1] = ((byte)select);
        result[2] = ((byte)n);
        result[3] = ((byte)m);
        return result;
    }
}
