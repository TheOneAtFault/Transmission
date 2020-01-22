package com.ody.serial.Classes;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.ody.serial.Helpers.Response;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SerialPort {
    private static final String TAG = "SerialPort";
    private Response response = null;
    private FileDescriptor mFd;
    private FileInputStream mFileInputStream;
    private FileOutputStream mFileOutputStream;

    public SerialPort(File device, int baudrate, int flags, boolean flowCon, Context context)
            {
                Toast.makeText(context,"test",Toast.LENGTH_LONG).show();
        /*try{
            if ((!device.canRead()) || (!device.canWrite())) {
                Toast.makeText(context,"can read",Toast.LENGTH_LONG).show();
                *//*try {
                    Process su = Runtime.getRuntime().exec("/system/bin/su");
                    String cmd = "chmod 666 " + device.getAbsolutePath() + "\n" +
                            "exit\n";
                    su.getOutputStream().write(cmd.getBytes());
                    if ((su.waitFor() != 0) || (!device.canRead()) ||
                            (!device.canWrite())) {
                        //throw new SecurityException();
                        Toast.makeText(context,"oi",Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //throw new SecurityException();
                    Toast.makeText(context,"bro",Toast.LENGTH_LONG).show();
                }*//*
            }
            Toast.makeText(context,"cant",Toast.LENGTH_LONG).show();
            *//*this.mFd = open(device.getAbsolutePath(), baudrate, flags, flowCon);
            if (this.mFd == null) {
                Log.e("SerialPort", "native open returns null");
                //throw new IOException();
                Toast.makeText(context,"stop",Toast.LENGTH_LONG).show();
            }
            this.mFileInputStream = new FileInputStream(this.mFd);
            this.mFileOutputStream = new FileOutputStream(this.mFd);*//*
        }
        catch (Exception e){
            response = Response.getInstance().compose(false,e,"oh tits");
            Toast.makeText(context,response.getsErrorMessage(),Toast.LENGTH_LONG).show();
        }*/
    }

    public SerialPort(File device, int baudrate, int flags, boolean flowCon)
            throws SecurityException, IOException {
        if ((!device.canRead()) || (!device.canWrite())) {
            try {
                Process su = Runtime.getRuntime().exec("/system/bin/su");
                String cmd = "chmod 666 " + device.getAbsolutePath() + "\n" +
                        "exit\n";
                su.getOutputStream().write(cmd.getBytes());
                if ((su.waitFor() != 0) || (!device.canRead()) ||
                        (!device.canWrite())) {
                    throw new SecurityException();
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new SecurityException();
            }
        }
        this.mFd = open(device.getAbsolutePath(), baudrate, flags, flowCon);
        if (this.mFd == null) {
            Log.e("SerialPort", "native open returns null");
            throw new IOException();
        }
        this.mFileInputStream = new FileInputStream(this.mFd);
        this.mFileOutputStream = new FileOutputStream(this.mFd);
    }

    public InputStream getInputStream() {
        return this.mFileInputStream;
    }

    public OutputStream getOutputStream() {
        return this.mFileOutputStream;
    }

    static {
        System.loadLibrary("serial_port");
    }

    private static native FileDescriptor open(String paramString, int paramInt1, int paramInt2, boolean paramBoolean);

    public native void close();
}
