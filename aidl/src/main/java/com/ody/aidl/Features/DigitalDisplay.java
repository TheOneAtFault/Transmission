package com.ody.aidl.Features;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.widget.Switch;

import com.ody.aidl.AIDLProvider;
import com.ody.aidl.Helpers.QRGenerator;
import com.ody.aidl.Helpers.Response;
import com.ody.aidl.Utils.AidlUtil;

import java.io.File;

public class DigitalDisplay {
    private static DigitalDisplay mDigtaDigitalDisplay = new DigitalDisplay();
    private Response response;

    public static DigitalDisplay getInstance() {
        return mDigtaDigitalDisplay;
    }

    public Response displayWake() {
        try {
            response = AidlUtil.getInstance().lcdWake();
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in DigitalDisplay.displayWake()");
        } finally {
            //AidlUtil.getInstance().disconnectPrinterService(AIDLProvider.getApplication());
        }
        return response;
    }

    public Response displaySleep() {
        try {
            response = AidlUtil.getInstance().lcdSleep();
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in DigitalDisplay.displaySleep()");
        } finally {
            // AidlUtil.getInstance().disconnectPrinterService(AIDLProvider.getApplication());
        }
        return response;
    }

    public Response displayImage(String filePath) {
        try {
            //chack file exists
            File file = new File(filePath);
            if (file.exists()) {
                try {
                    Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                    bitmap = Bitmap.createScaledBitmap(bitmap, 128, 40, false);
                    response = AidlUtil.getInstance().lcdBitmap(bitmap);
                } catch (Exception e) {
                    //ignore
                }
            } else {
                response = Response.getInstance().compose(false, null, "File does not exist");
            }

        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in DigitalDisplay.lcdImage");
        } finally {
            //AidlUtil.getInstance().disconnectPrinterService(AIDLProvider.getApplication());
        }
        return response;
    }

    public Response displayImage(Bitmap bitmap) {
        try {
            //chack file exists
            //File file = new File(filePath);
            if (bitmap != null) {
                //convert to itty bitty something
                //Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                response = AidlUtil.getInstance().lcdBitmap(bitmap);
            } else {
                response = Response.getInstance().compose(false, null, "File does not exist");
            }

        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in DigitalDisplay.lcdImage");
        } finally {
            //AidlUtil.getInstance().disconnectPrinterService(AIDLProvider.getApplication());
        }
        return response;
    }

    public Response displaySingle(String content) {
        try {
            response = AidlUtil.getInstance().lcdSingle(content);
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in lcdSingle - DigitalDisplay");
        } finally {
            //AidlUtil.getInstance().disconnectPrinterService(AIDLProvider.getApplication());
        }
        return response;
    }


    public Response displayDouble(String lineOne, String lineTwo) {
        try {
            response = AidlUtil.getInstance().lcdDouble(lineOne, lineTwo);
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in lcdDouble - DigitalDisplay");
        } finally {
            //AidlUtil.getInstance().disconnectPrinterService(AIDLProvider.getApplication());
        }
        return response;
    }

    public void displayQR(String data) {
        Bitmap qrCode = QRGenerator.getInstance().run(data);
        displayImage(qrCode);
    }


    public void displayBranding(String qrContent, String imagePath) {
        Bitmap qrCode = QRGenerator.getInstance().run(qrContent);

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        bitmap = Bitmap.createScaledBitmap(bitmap, 88, 40, false);

        Bitmap bmOverlay = Bitmap.createBitmap(128, 40, qrCode.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(qrCode, new Matrix(), null);
        canvas.drawBitmap(bitmap, 40, 0, null);

        displayImage(bmOverlay);
    }

    public void drawText(String item, String value) {
        Bitmap bmOverlay = Bitmap.createBitmap(128, 40, Bitmap.Config.ARGB_8888);

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(12);

        String LineOne = "";
        String LineTwo = "";
        String LineThree = "";
        item = item.trim();

        if (item.length() > 16) {

            String[] words = item.split(" ");
            int leng = 0;
            if (words.length >= 3) {
                leng = words[0].length() + words[1].length() + words[2].length();
                if (leng <= 15) {
                    LineOne = words[0] + " " + words[1] + " " + words[2].length();
                } else {
                    LineOne = words[0];
                }

                LineTwo = item.substring(LineOne.length() + 1, item.length());
                LineThree = value;
            } else if (words.length >= 2) {
                leng = words[0].length() + words[1].length();
                if (leng <= 15) {
                    LineOne = words[0] + " " + words[1];
                } else {
                    LineOne = words[0];
                }

                LineTwo = item.substring(LineOne.length() + 1, item.length());
                LineThree = value;
            } else {
                LineOne = words[0].substring(0, words[0].length() - 3) + "...";
                LineTwo = " ";
                LineThree = value;
            }

            Canvas canvas = new Canvas(bmOverlay);
            canvas.drawColor(Color.WHITE);
            canvas.drawText(LineOne, 0, 12, paint);
            canvas.drawText(LineTwo, 0, 24, paint);
            canvas.drawText(LineThree, 0, 36, paint);
            displayImage(bmOverlay);

        } else {
            Canvas canvas = new Canvas(bmOverlay);
            canvas.drawColor(Color.WHITE);
            canvas.drawText(item, 0, 12, paint);
            canvas.drawText("", 0, 24, paint);
            canvas.drawText(value, 0, 36, paint);
            displayImage(bmOverlay);
        }
    }
}
