package com.ody.serial.Classes.Drawer;

public abstract interface CashDrawerApiContext {
    public static final int Sucess_OK = 0;
    public static final int Sucess_Fail = 1;
    public static final int Sucess_Error = -1;

    public abstract int OpenCashDrawerA();

    public abstract int OpenCashDrawerB();

    public abstract boolean IsCashDrawerAOpen();

    public abstract boolean IsCashDrawerBOpen();

    public abstract String getSdkVersion();
}
