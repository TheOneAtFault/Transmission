package com.ody.usb.Features;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.ody.usb.Classes.QR.Contents;
import com.ody.usb.Classes.QR.QRCodeEncoder;
import com.ody.usb.Classes.Shared.ESCPOSPrinter;
import com.ody.usb.Classes.Shared.USBPort;
import com.ody.usb.Classes.Shared.USBPortConnection;
import com.ody.usb.Helpers.USB_Response;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Iterator;

public class PrintJob {
    private static PrintJob mSlip;
    private Context mContext;
    private UsbManager mUsbManager;
    private USBPort port;
    private UsbDevice mDevice;
    private USBPortConnection portConnection;
    private boolean bHasPermission = false;
    private USB_Response USBResponse = USB_Response.getInstance();

    private ESCPOSPrinter POSPrinter;

    private int imageWidth = 200;
    private int imageHeight = 200;


    private Bitmap generatedQR;
    private int QR_CODE_DIM = 300;

    private static final String ACTION_USB_PERMISSION = "com.genericusb.mainactivity.USB_PERMISSION";
    private String TAG = "Slip Printing USB";


    public static PrintJob getInstance() {
        return mSlip;
    }

    public void connectService(Context context) {
        mContext = context;
    }

    public void disconnectService() {
        try {
            portConnection.close();
        } catch (Exception e) {
            //expected
        }
        mContext = null;
    }

    public boolean main(Context context, int anId) {

        //set the application context passed from the call
        connectService(context);

        //register the required permission
        PendingIntent mPermissionIntent = PendingIntent.getBroadcast(mContext, 0,
                new Intent("com.genericusb.mainactivity.USB_PERMISSION"), 0);

        //setup manager and port
        mUsbManager = (UsbManager) mContext.getSystemService(Context.USB_SERVICE);
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
                        USBResponse = USB_Response.getInstance().compose(false, null,
                                "Device Permission requested.");
                    }

                    if (bHasPermission) {
                        portConnection = port.connect_device(mDevice);
                        POSPrinter = new ESCPOSPrinter(portConnection);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "main: error in init of main", e);
                    //USBResponse = USB_Response.getInstance().compose(false, e, "Exception caught in Image.plainImage().");
                }
            }
        }
        return bHasPermission;
    }

    public void print_image(String image, int padding) {
        if (POSPrinter != null) {
            try {
                Bitmap bitmap = BitmapFactory.decodeFile(image);
                bitmap = Bitmap.createScaledBitmap(bitmap, imageWidth, imageHeight, false);
                POSPrinter.printBitmap(1, bitmap, padding); //0 - left align, 1 - center align, 2 - right align
            } catch (Exception e) {
                Log.e(TAG, "image: error in image print", e);
            }
        }
    }

    public void print_text(String content) {
        if (POSPrinter != null) {
            try {
                POSPrinter.printNormal(content);
            } catch (Exception e) {
                Log.e(TAG, "text: error is text print", e);
            }
        }
    }

    public void print_qr(String qr, int padding) {
        if (POSPrinter != null) {
            generatedQR = generate(qr);
            if (generatedQR == null) {
                Log.d(TAG, "qr: generated qr empty");
            } else {
                try {
                    POSPrinter.printBitmap(1, generatedQR, padding);
                } catch (Exception e) {
                    Log.e(TAG, "qr: print failed", e);
                }
            }
        }
    }

    public void print_cut() {
        if (POSPrinter != null) {
            try {
                POSPrinter.cutPaper();
            } catch (Exception e) {
                Log.e(TAG, "print_cut: error", e);
            }
        }
    }

    public Bitmap generate(String data) {
        try {
            //Encode with a QR Code image
            QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(data,
                    null,
                    Contents.Type.TEXT,
                    BarcodeFormat.QR_CODE.toString(),
                    QR_CODE_DIM);
            Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();

            //Re-encode the bitmap, messy
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();
            Bitmap decodedByte = BitmapFactory.decodeByteArray(b, 0, b.length);

            return decodedByte;
        } catch (Exception e) {
            Log.e(TAG, "generate: error in qr generate", e);
        }
        return null;
    }


}
