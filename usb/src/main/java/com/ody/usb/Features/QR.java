package com.ody.usb.Features;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;

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

public class QR {

    private static QR mQr;
    private Context mContext;
    private USBPort port;
    private UsbDevice mDevice;
    private USBPortConnection portConnection;
    private boolean bHasPermission = false;
    private USB_Response response = USB_Response.getInstance();
    private UsbManager mUsbManager;
    private Bitmap generatedQR;
    private int QR_CODE_DIM = 300;

    public static QR getInstance() {
        return mQr = new QR();
    }

    public void connectService(Context context) {
        mContext = context;
    }
    public void disconnectService() {
        mContext = null;
    }

    public USB_Response plain(Context context, int vendorId, String data) {
        try {
            //Generate qr----------------
            generatedQR = generate(data);
            if (generatedQR == null) {
                return response = USB_Response.getInstance().compose(
                        false,
                        null,
                        response.getsErrorMessage()
                );
            }

            //set the application context passed from the call
            connectService(context);

            //register the required permission
            PendingIntent mPermissionIntent = PendingIntent.getBroadcast(context, 0,
                    new Intent("com.genericusb.mainactivity.USB_PERMISSION"), 0);

            //setup manager and port
            mUsbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
            port = new USBPort(mUsbManager);

            //get device
            HashMap<String, UsbDevice> usblist = mUsbManager.getDeviceList();
            Iterator<String> iterator = usblist.keySet().iterator();

            while (iterator.hasNext()) {
                mDevice = (UsbDevice) usblist.get(iterator.next());
                //validate against vendorid
                if (mDevice.getVendorId() == vendorId) {
                    try {
                        if (mUsbManager.hasPermission(mDevice)) {
                            bHasPermission = true;
                        } else {
                            bHasPermission = false;
                            mUsbManager.requestPermission(mDevice, mPermissionIntent);
                            bHasPermission = true;

                            response = USB_Response.getInstance().compose(
                                    false,
                                    null,
                                    "Device Permission requested."
                            );
                        }

                        if (bHasPermission) {
                            portConnection = port.connect_device(mDevice);
                            ESCPOSPrinter POSPrinter = new ESCPOSPrinter(portConnection);
                            //0 - left align, 1 - center align, 2 - right align
                            POSPrinter.printBitmap(1, generatedQR);

                            response = USB_Response.getInstance().compose(
                                    true,
                                    null,
                                    "Success"
                            );
                        }
                    } catch (Exception e) {
                        response = USB_Response.getInstance().compose(
                                false,
                                e,
                                "Exception caught in QR.plain()."
                        );
                    }
                }
            }
        } catch (Exception e) {
            response = USB_Response.getInstance().compose(
                    false,
                    e, "Exception in usb QR.plain"
            );
        }
        finally {
            disconnectService();
        }
        return response;
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
            response = USB_Response.getInstance().compose(
                    false,
                    e,
                    "Exception in usb QR generation"
            );
        }
        return null;
    }
}
