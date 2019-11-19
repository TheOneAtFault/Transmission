package com.ody.usb.Classes.Shared;

import com.ody.usb.Classes.RequestQueue;
import com.ody.usb.Classes.Shared.ESCPOS;

import java.io.UnsupportedEncodingException;

public class OLEPOSCommand {
    private ESCPOS escpos;
    private RequestQueue rQueue;
    private String charSet;

    //constructor
    protected OLEPOSCommand(String charset, RequestQueue queue)
    {
        this.charSet = charset;
        this.escpos = new ESCPOS();
        this.rQueue = queue;
    }

    private int lengthThreeCMD(String input)
    {
        String command = input.substring(0, 3);
        if (command.equals("\033|N"))
        {
            this.rQueue.addRequest(this.escpos.ESC_EXCLAMATION(0));

            this.rQueue.addRequest(this.escpos.GS_B(0));

            this.rQueue.addRequest(this.escpos.ESC_a(0));

            this.rQueue.addRequest(this.escpos.ESC_M(0));
        }
        else
        {
            return -1;
        }
        return 3;
    }

    private int lengthFourCMD(String input)
    {
        String command = input.substring(0, 4);
        if (command.equals("\033|1C")) {
            this.rQueue.addRequest(this.escpos.GS_EXCLAMATION(0));
        } else if (command.equals("\033|2C")) {
            this.rQueue.addRequest(this.escpos.GS_EXCLAMATION(16));
        } else if (command.equals("\033|3C")) {
            this.rQueue.addRequest(this.escpos.GS_EXCLAMATION(1));
        } else if (command.equals("\033|4C")) {
            this.rQueue.addRequest(this.escpos.GS_EXCLAMATION(17));
        } else if (command.equals("\033|rA")) {
            this.rQueue.addRequest(this.escpos.ESC_a(2));
        } else if (command.equals("\033|cA")) {
            this.rQueue.addRequest(this.escpos.ESC_a(1));
        } else if (command.equals("\033|lA")) {
            this.rQueue.addRequest(this.escpos.ESC_a(0));
        } else if (command.equals("\033|uC")) {
            this.rQueue.addRequest(this.escpos.ESC_HYPHEN(1));
        } else if (command.equals("\033|bC")) {
            this.rQueue.addRequest(this.escpos.ESC_E(1));
        } else if (command.charAt(3) == 'B') {
            this.rQueue.addRequest(this.escpos.GS_SLASH(0));
        } else {
            return -1;
        }
        return 4;
    }

    private int lengthFiveCMD(String input)
    {
        String command = input.substring(0, 5);
        if (command.equals("\033|!bC"))
        {
            this.rQueue.addRequest(this.escpos.ESC_E(0));
        }
        else if (command.equals("\033|1uC"))
        {
            this.rQueue.addRequest(this.escpos.ESC_HYPHEN(1));
        }
        else if (command.equals("\033|2uC"))
        {
            this.rQueue.addRequest(this.escpos.ESC_HYPHEN(2));
        }
        else if ((command.equals("\033|!uC")) || (command.equals("\033|0uC")))
        {
            this.rQueue.addRequest(this.escpos.ESC_HYPHEN(0));
        }
        else if (command.charAt(4) == 'B')
        {
            this.rQueue.addRequest(this.escpos.GS_SLASH(0));
        }
        else if (command.equals("\033|rvC"))
        {
            this.rQueue.addRequest(this.escpos.GS_B(1));
        }
        else if (command.substring(3, 5).equals("hC"))
        {
            int temp = Integer.parseInt(command.substring(2, 3));
            switch (temp)
            {
                case 1:
                    temp = 0;
                    break;
                case 2:
                    temp = 16;
                    break;
                case 3:
                    temp = 32;
                    break;
                case 4:
                    temp = 48;
                    break;
                case 5:
                    temp = 64;
                    break;
                case 6:
                    temp = 80;
                    break;
                case 7:
                    temp = 96;
                    break;
                case 8:
                    temp = 112;
                    break;
                default:
                    temp = 0;
            }
            this.rQueue.addRequest(this.escpos.GS_EXCLAMATION(temp));
        }
        else if (command.substring(3, 5).equals("vC"))
        {
            int temp = Integer.parseInt(command.substring(2, 3));
            if ((temp >= 1) && (temp <= 8)) {
                temp--;
            } else {
                temp = 0;
            }
            this.rQueue.addRequest(this.escpos.GS_EXCLAMATION(temp));
        }
        else if (command.substring(3, 5).equals("fT"))
        {
            int temp = Integer.parseInt(command.substring(2, 3));
            temp--;
            if (temp != 1) {
                temp = 0;
            }
            this.rQueue.addRequest(this.escpos.ESC_M(temp));
        }
        else if (command.substring(3, 5).equals("fP"))
        {
            this.rQueue.addRequest(this.escpos.ESC_d(1));
        }
        else if (command.substring(3, 5).equals("lF"))
        {
            int temp = Integer.parseInt(command.substring(2, 3));
            this.rQueue.addRequest(this.escpos.ESC_d(temp));
        }
        else if (command.substring(3, 5).equals("uF"))
        {
            int temp = Integer.parseInt(command.substring(2, 3));
            this.rQueue.addRequest(this.escpos.ESC_J(temp));
        }
        else
        {
            return -1;
        }
        return 5;
    }

    private int lengthSixCMD(String input)
    {
        String command = input.substring(0, 6);
        if (command.substring(4, 6).equals("fP"))
        {
            this.rQueue.addRequest(this.escpos.ESC_d(1));
        }
        else if (command.substring(4, 6).equals("lF"))
        {
            int temp = Integer.parseInt(command.substring(2, 4));
            this.rQueue.addRequest(this.escpos.ESC_d(temp));
        }
        else if (command.substring(4, 6).equals("uF"))
        {
            int temp = Integer.parseInt(command.substring(2, 4));
            this.rQueue.addRequest(this.escpos.ESC_J(temp));
        }
        else if (command.equals("\033|!rvC"))
        {
            this.rQueue.addRequest(this.escpos.GS_B(0));
        }
        else
        {
            return -1;
        }
        return 6;
    }

    private void convertCommand(String input)
            throws UnsupportedEncodingException
    {
        int length = input.length();
        if (length >= 3)
        {
            int retval = lengthThreeCMD(input);
            if ((retval < 0) && (length >= 4)) {
                retval = lengthFourCMD(input);
            }
            if ((retval < 0) && (length >= 5)) {
                retval = lengthFiveCMD(input);
            }
            if ((retval < 0) && (length >= 6)) {
                retval = lengthSixCMD(input);
            }
            if (retval < 0)
            {
                this.rQueue.addRequest(input.getBytes(this.charSet));
            }
            else if (length > retval)
            {
                this.rQueue.addRequest(input.substring(retval, length).getBytes(this.charSet));

                this.rQueue.addRequest(this.escpos.ESC_EXCLAMATION(0));
                this.rQueue.addRequest(this.escpos.GS_B(0));
                this.rQueue.addRequest(this.escpos.GS_EXCLAMATION(0));
            }
        }
        else
        {
            this.rQueue.addRequest(input.getBytes(this.charSet));
        }
    }

    protected void parseJposCMD(String input)
            throws UnsupportedEncodingException
    {
        String div = "\033|";
        int length = input.length();
        int end = 0;
        int start = 0;
        start = input.indexOf(div, end);
        if (start < 0) {
            this.rQueue.addRequest(input.getBytes(this.charSet));
        } else {
            for (;;)
            {
                end = input.indexOf(div, start + 1);
                if (end < 0)
                {
                    convertCommand(input.substring(start, length));
                    break;
                }
                convertCommand(input.substring(start, end));
                start = end;
            }
        }
    }
}
