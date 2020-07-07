package com.ody.usb.Features;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;

import com.ody.usb.Classes.Shared.ESCPOSPrinter;
import com.ody.usb.Classes.Shared.USBPort;
import com.ody.usb.Classes.Shared.USBPortConnection;
import com.ody.usb.Helpers.USB_Response;

import java.util.HashMap;
import java.util.Iterator;

public class Cutter {
    private static Cutter mCutter;
    private Context mContext;
    private UsbManager mUsbManager;
    private USBPort port;
    private boolean bHasPermission;
    private USBPortConnection portConnection;
    private UsbDevice mDevice;
    private USB_Response USBResponse = USB_Response.getInstance();

    private Cutter() {
    }

    public void connectService(Context context) {
        mContext = context;
    }

    private void disconnectService() {
        mContext = null;
    }

    public static Cutter getInstance() {
        return mCutter = new Cutter();
    }

    public void cut(Context context, int anId) {
        //set the application context passed from the call
        connectService(context);

        //register the required permission
        PendingIntent mPermissionIntent = PendingIntent.getBroadcast(
                context,
                0,
                new Intent("com.genericusb.mainactivity.USB_PERMISSION"),
                0
        );

        //setup manager and port
        mUsbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        port = new USBPort(mUsbManager);

        //get device
        HashMap<String, UsbDevice> usblist = mUsbManager.getDeviceList();
        Iterator<String> iterator = usblist.keySet().iterator();

        while (iterator.hasNext()) {
            mDevice = (UsbDevice) usblist.get(iterator.next());
            //validate against anId
            if (mDevice.getProductId() == anId) {
                try {
                    if (mUsbManager.hasPermission(mDevice)) {
                        bHasPermission = true;
                    } else {
                        bHasPermission = false;
                        mUsbManager.requestPermission(mDevice, mPermissionIntent);
                        bHasPermission = true;
                        USBResponse = USB_Response.getInstance().compose(
                                false,
                                null,
                                "Device Permission requested."
                        );
                    }

                    if (bHasPermission) {
                        portConnection = port.connect_device(mDevice);
                        ESCPOSPrinter POSPrinter = new ESCPOSPrinter(portConnection);
                        POSPrinter.cutPaper();
                        USBResponse = USB_Response.getInstance().compose(
                                true,
                                null,
                                "Success"
                        );
                    }
                } catch (Exception e) {
                    USBResponse = USB_Response.getInstance().compose(
                            false,
                            e,
                            "Exception caught in Image.plainImage()."
                    );
                }
            }
        }
    }
}
