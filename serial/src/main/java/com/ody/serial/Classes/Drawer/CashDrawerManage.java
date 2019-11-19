package com.ody.serial.Classes.Drawer;

import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CashDrawerManage implements CashDrawerApiContext {
    private void writeToFile(String FILENAME, String data)
    {
        try
        {
            FileOutputStream output = new FileOutputStream(FILENAME);
            byte[] b = data.getBytes();
            output.write(b);
            output.close();
        }
        catch (IOException e)
        {
            Log.d("CashDrawer", "File write failed: " + e.toString());
        }
    }

    private int readFromFile(String FILENAME)
    {
        int ret = -1;
        try
        {
            FileInputStream inputStream = new FileInputStream(FILENAME);
            int data = inputStream.read();
            inputStream.close();
            ret = data;
        }
        catch (FileNotFoundException e)
        {
            Log.d("CashDrawer", "File not found: " + e.toString());
        }
        catch (IOException e)
        {
            Log.d("CashDrawer", "Can not read file: " + e.toString());
        }
        return ret;
    }

    public int OpenCashDrawerA()
    {
        try
        {
            writeToFile("/sys/class/gpio/gpio142/direction", "in");
            writeToFile("/sys/class/gpio/gpio35/direction", "out");
            writeToFile("/sys/class/gpio/gpio35/value", "1");
            writeToFile("/sys/class/gpio/gpio35/value", "0");
            writeToFile("/sys/class/gpio/gpio35/value", "1");
            return 0;
        }
        catch (Exception e)
        {
            Log.d("CashDrawer", "OpenCashDrawerA " + e.toString());
        }
        return -1;
    }

    public int OpenCashDrawerB()
    {
        try
        {
            writeToFile("/sys/class/gpio/gpio137/drection", "in");
            writeToFile("/sys/class/gpio/gpio34/direction", "out");
            writeToFile("/sys/class/gpio/gpio34/value", "1");
            writeToFile("/sys/class/gpio/gpio34/value", "0");
            writeToFile("/sys/class/gpio/gpio34/value", "1");
            return 0;
        }
        catch (Exception e)
        {
            Log.d("CashDrawer", "OpenCashDrawerB " + e.toString());
        }
        return -1;
    }

    public boolean IsCashDrawerAOpen()
    {
        try
        {
            int data = readFromFile("/sys/class/gpio/gpio142/value");

            boolean answer = false;
            if (data == 48) {}
            return true;
        }
        catch (Exception e)
        {
            Log.d("CashDrawer", "IsCashDrawerAOpen " + e.toString());
        }
        return false;
    }

    public boolean IsCashDrawerBOpen()
    {
        try
        {
            int data = readFromFile("/sys/class/gpio/gpio137/value");

            boolean answer = false;
            if (data == 48) {}
            return true;
        }
        catch (Exception e)
        {
            Log.d("CashDrawer", "IsCashDrawerAOpen " + e.toString());
        }
        return false;
    }

    public String getSdkVersion()
    {
        return "Pat100 CDR SDK version 1.1.0";
    }
}
