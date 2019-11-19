package com.ody.serial.Features;

import com.ody.serial.Classes.SerialPortFinder;

public class Ports {
    private static Ports mPorts;

    public static Ports getInstance(){
        return mPorts;
    } 
    public String get(){
        SerialPortFinder mSerialPortFinder;
        mSerialPortFinder = new SerialPortFinder();
        String returnString = "";

        String[] primitiveReturnStringArray = mSerialPortFinder.getAllDevicesPath();

        for(String device : primitiveReturnStringArray)
        {
            returnString += device + "~";
        }

        return returnString;
    }
}
