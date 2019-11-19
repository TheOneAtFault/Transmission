package com.ody.wifi.Classes;

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

    public byte[] DLE_EOT(int n)
    {
        return EPCommand((byte)16, '\004', n);
    }

    public byte[] DLE_ENQ(int n)
    {
        return EPCommand((byte)16, '\005', n);
    }

    public byte[] ESC_FF()
    {
        return EPCommand((byte)27, '\f');
    }

    public byte[] ESC_SP(int n)
    {
        return EPCommand((byte)27, ' ', n);
    }

    public byte[] ESC_EXCLAMATION(int n)
    {
        return EPCommand((byte)27, '!', n);
    }

    public byte[] ESC_DOLLAR(int nL, int nH)
    {
        return EPCommand((byte)27, '$', nL, nH);
    }

    public byte[] ESC_PERCENT(int n)
    {
        return EPCommand((byte)27, '%', n);
    }

    public byte[] ESC_AMPERSAND(int y, int c1, int c2, byte[][] buf)
    {
        byte[] temp = twoDToOneD(buf);
        int length = temp.length;
        byte[] command = new byte[length + 5];
        command[0] = 27;
        command[1] = 38;
        command[2] = ((byte)y);
        command[3] = ((byte)c1);
        command[4] = ((byte)c2);
        System.arraycopy(temp, 0, command, 4, length);
        return command;
    }

    public byte[] ESC_ASTERISK(int m, int nL, int nH, byte[] buf)
    {
        int length = buf.length;
        byte[] command = new byte[length + 5];
        command[0] = 27;
        command[1] = 42;
        command[2] = ((byte)m);
        command[3] = ((byte)nL);
        command[4] = ((byte)nH);
        System.arraycopy(buf, 0, command, 5, length);
        return command;
    }

    public byte[] ESC_HYPHEN(int n)
    {
        return EPCommand((byte)27, '-', n);
    }

    public byte[] ESC_2()
    {
        return EPCommand((byte)27, '2');
    }

    public byte[] ESC_3(int n)
    {
        return EPCommand((byte)27, '3', n);
    }

    public byte[] ESC_QUESTION(int n)
    {
        return EPCommand((byte)27, '?', n);
    }

    public byte[] ESC_AT()
    {
        return EPCommand((byte)27, '@');
    }

    public byte[] ESC_D(byte[] buf)
    {
        int length = buf.length;
        byte[] command = new byte[length + 3];
        command[0] = 27;
        command[1] = 68;
        System.arraycopy(buf, 0, command, 2, length);
        command[(length + 2)] = 0;
        return command;
    }

    public byte[] ESC_E(int n)
    {
        return EPCommand((byte)27, 'E', n);
    }

    public byte[] ESC_G(int n)
    {
        return EPCommand((byte)27, 'G', n);
    }

    public byte[] ESC_J(int n)
    {
        return EPCommand((byte)27, 'J', n);
    }

    public byte[] ESC_L()
    {
        return EPCommand((byte)27, 'L');
    }

    public byte[] ESC_M(int n)
    {
        return EPCommand((byte)27, 'M', n);
    }

    public byte[] ESC_R(int n)
    {
        return EPCommand((byte)27, 'R', n);
    }

    public byte[] ESC_S()
    {
        return EPCommand((byte)27, 'S');
    }

    public byte[] ESC_T(int n)
    {
        return EPCommand((byte)27, 'T', n);
    }

    public byte[] ESC_V(int n)
    {
        return EPCommand((byte)27, 'V', n);
    }

    public byte[] ESC_W(int xL, int xH, int yL, int yH, int dxL, int dxH, int dyL, int dyH)
    {
        byte[] command = new byte[10];
        command[0] = 27;
        command[1] = 87;
        command[2] = ((byte)xL);
        command[3] = ((byte)xH);
        command[4] = ((byte)yL);
        command[5] = ((byte)yH);
        command[6] = ((byte)dxL);
        command[7] = ((byte)dxH);
        command[8] = ((byte)dyL);
        command[9] = ((byte)dyH);
        return command;
    }

    public byte[] ESC_BACKSLASH(int nL, int nH)
    {
        return EPCommand((byte)27, '\\', nL, nH);
    }

    public byte[] ESC_a(int n)
    {
        return EPCommand((byte)27, 'a', n);
    }

    public byte[] ESC_d(int n)
    {
        return EPCommand((byte)27, 'd', n);
    }

    public byte[] ESC_p(int m, int t1, int t2)
    {
        byte[] command = new byte[5];
        command[0] = 27;
        command[1] = 112;
        command[2] = ((byte)m);
        command[3] = ((byte)t1);
        command[4] = ((byte)t2);
        return command;
    }

    public byte[] ESC_t(int n)
    {
        return EPCommand((byte)27, 't', n);
    }

    public byte[] ESC_LEFTBRACE(int n)
    {
        return EPCommand((byte)27, '{', n);
    }

    public byte[] FS_p(int n, int m)
    {
        return EPCommand((byte)28, 'p', n, m);
    }

    public byte[] FS_q(int n, byte[][] buf)
    {
        byte[] temp = twoDToOneD(buf);
        int length = temp.length;
        byte[] command = new byte[3 + length];
        command[0] = 28;
        command[1] = 113;
        command[2] = ((byte)n);
        System.arraycopy(temp, 0, command, 3, length);
        return command;
    }

    public byte[] GS_EXCLAMATION(int n)
    {
        return EPCommand((byte)29, '!', n);
    }

    public byte[] GS_DOLLAR(int nL, int nH)
    {
        return EPCommand((byte)29, '$', nL, nH);
    }

    public byte[] GS_ASTERISK(int x, int y, byte[] buf)
    {
        int length = buf.length;
        byte[] command = new byte[length + 4];
        command[0] = 29;
        command[1] = 42;
        command[2] = ((byte)x);
        command[3] = ((byte)y);
        System.arraycopy(buf, 0, command, 4, length);
        return command;
    }

    public byte[] GS_SLASH(int m)
    {
        return EPCommand((byte)29, '/', m);
    }

    public byte[] GS_B(int n)
    {
        return EPCommand((byte)29, 'B', n);
    }

    public byte[] GS_H(int n)
    {
        return EPCommand((byte)29, 'H', n);
    }

    public byte[] GS_I(int n)
    {
        return EPCommand((byte)29, 'I', n);
    }

    public byte[] GS_L(int nL, int nH)
    {
        return EPCommand((byte)29, 'L', nL, nH);
    }

    public byte[] GS_W(int nL, int nH)
    {
        return EPCommand((byte)29, 'W', nL, nH);
    }

    public byte[] GS_BACKSLASH(int nL, int nH)
    {
        return EPCommand((byte)29, '\\', nL, nH);
    }

    public byte[] GS_a(int n)
    {
        return EPCommand((byte)29, 'a', n);
    }

    public byte[] GS_f(int n)
    {
        return EPCommand((byte)29, 'f', n);
    }

    public byte[] GS_h(int n)
    {
        return EPCommand((byte)29, 'h', n);
    }

    public byte[] GS_k(int m, int n, byte[] data)
    {
        byte[] command = new byte[4 + n];
        command[0] = 29;
        command[1] = 107;
        command[2] = ((byte)m);
        command[3] = ((byte)n);
        System.arraycopy(data, 0, command, 4, n);
        return command;
    }

    public byte[] GS_k(int m, int n, String data)
    {
        return GS_k(m, n, data.getBytes());
    }

    public byte[] GS_r(int n)
    {
        return EPCommand((byte)29, 'r', n);
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

    public byte[] GS_w(int n)
    {
        return EPCommand((byte)29, 'w', n);
    }

    public byte[] GS_S_C(int n)
    {
        byte[] command = new byte[9];
        command[0] = 29;
        command[1] = 83;
        command[2] = 67;
        command[3] = 1;
        command[4] = ((byte)n);
        command[5] = 1;
        command[6] = 33;
        command[7] = 8;
        command[8] = 1;

        return command;
    }

    private byte[] dataRequest = { 29, 83, 77, 1, 49 };

    public byte[] msrdataRequest()
    {
        return this.dataRequest;
    }

    public byte[] FS_M(int n)
    {
        return EPCommand((byte)28, 'M', n);
    }

    public byte[] twoDToOneD(byte[][] two)
    {
        int index = 0;
        int length1 = two.length;
        int length2 = two[0].length;
        byte[] result = new byte[length1 * length2];
        for (int i = 0; i < length1; i++) {
            for (int j = 0; j < length2; j++)
            {
                result[index] = two[i][j];
                index++;
            }
        }
        return result;
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
