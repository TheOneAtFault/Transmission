package com.ody.aidl.Classes.QR;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;

public class QRCodeEncoder {
    private static final int WHITE = -1;
    private static final int BLACK = -16777216;
    private int dimension = -2147483648;
    private String contents = null;
    private String displayContents = null;
    private String title = null;
    private BarcodeFormat format = null;
    private boolean encoded = false;

    public QRCodeEncoder(String data, Bundle bundle, String type, String format, int dimension) {
        this.dimension = dimension;
        this.encoded = encodeContents(data, bundle, type, format);
    }

    private boolean encodeContents(String data, Bundle bundle, String type, String formatString) {
        this.format = null;
        if (formatString != null) {
            try {
                this.format = BarcodeFormat.valueOf(formatString);
            } catch (IllegalArgumentException localIllegalArgumentException) {
            }
        }
        if ((this.format == null) || (this.format == BarcodeFormat.QR_CODE)) {
            this.format = BarcodeFormat.QR_CODE;
            encodeQRCodeContents(data, bundle, type);
        } else if ((data != null) && (data.length() > 0)) {
            this.contents = data;
            this.displayContents = data;
            this.title = "Text";
        }
        return (this.contents != null) && (this.contents.length() > 0);
    }

    private void encodeQRCodeContents(String data, Bundle bundle, String type) {
        if (type.equals("TEXT_TYPE")) {
            if ((data != null) && (data.length() > 0)) {
                this.contents = data;
                this.displayContents = data;
                this.title = "Text";
            }
        } else if (type.equals("EMAIL_TYPE")) {
            data = trim(data);
            if (data != null) {
                this.contents = ("mailto:" + data);
                this.displayContents = data;
                this.title = "E-Mail";
            }
        } else if (type.equals("PHONE_TYPE")) {
            data = trim(data);
            if (data != null) {
                this.contents = ("tel:" + data);
                this.displayContents = PhoneNumberUtils.formatNumber(data);
                this.title = "Phone";
            }
        } else if (type.equals("SMS_TYPE")) {
            data = trim(data);
            if (data != null) {
                this.contents = ("sms:" + data);
                this.displayContents = PhoneNumberUtils.formatNumber(data);
                this.title = "SMS";
            }
        } else if (type.equals("CONTACT_TYPE")) {
            if (bundle != null) {
                StringBuilder newContents = new StringBuilder(100);
                StringBuilder newDisplayContents = new StringBuilder(100);

                newContents.append("MECARD:");

                String name = trim(bundle.getString("name"));
                if (name != null) {
                    newContents.append("N:").append(escapeMECARD(name)).append(';');
                    newDisplayContents.append(name);
                }
                String address = trim(bundle.getString("postal"));
                if (address != null) {
                    newContents.append("ADR:").append(escapeMECARD(address)).append(';');
                    newDisplayContents.append('\n').append(address);
                }
                Collection<String> uniquePhones = new HashSet(Contents.PHONE_KEYS.length);
                String phone;
                for (int x = 0; x < Contents.PHONE_KEYS.length; x++) {
                    phone = trim(bundle.getString(Contents.PHONE_KEYS[x]));
                    if (phone != null) {
                        uniquePhones.add(phone);
                    }
                }
                for (String sPhone : uniquePhones) {
                    newContents.append("TEL:").append(escapeMECARD(sPhone)).append(';');
                    newDisplayContents.append('\n').append(PhoneNumberUtils.formatNumber(sPhone));
                }
                Collection<String> uniqueEmails = new HashSet(Contents.EMAIL_KEYS.length);
                String email;
                for (int x = 0; x < Contents.EMAIL_KEYS.length; x++) {
                    email = trim(bundle.getString(Contents.EMAIL_KEYS[x]));
                    if (email != null) {
                        uniqueEmails.add(email);
                    }
                }
                for (String sEmail : uniqueEmails) {
                    newContents.append("EMAIL:").append(escapeMECARD(sEmail)).append(';');
                    newDisplayContents.append('\n').append(sEmail);
                }
                String url = trim(bundle.getString("URL_KEY"));
                if (url != null) {
                    newContents.append("URL:").append(url).append(';');
                    newDisplayContents.append('\n').append(url);
                }
                String note = trim(bundle.getString("NOTE_KEY"));
                if (note != null) {
                    newContents.append("NOTE:").append(escapeMECARD(note)).append(';');
                    newDisplayContents.append('\n').append(note);
                }
                if (newDisplayContents.length() > 0) {
                    newContents.append(';');
                    this.contents = newContents.toString();
                    this.displayContents = newDisplayContents.toString();
                    this.title = "Contact";
                } else {
                    this.contents = null;
                    this.displayContents = null;
                }
            }
        } else if ((type.equals("LOCATION_TYPE")) &&
                (bundle != null)) {
            float latitude = bundle.getFloat("LAT", 3.4028235E+38F);
            float longitude = bundle.getFloat("LONG", 3.4028235E+38F);
            if ((latitude != 3.4028235E+38F) && (longitude != 3.4028235E+38F)) {
                this.contents = ("geo:" + latitude + ',' + longitude);
                this.displayContents = (latitude + "," + longitude);
                this.title = "Location";
            }
        }
    }

    public Bitmap encodeAsBitmap()
            throws WriterException {
        if (!this.encoded) {
            return null;
        }
        Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(this.contents);
        if (encoding != null) {
            hints = new EnumMap(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result = writer.encode(this.contents, this.format, this.dimension, this.dimension, hints);
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[(offset + x)] = (result.get(x, y) ? -16777216 : -1);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 'ÿ') {
                return "UTF-8";
            }
        }
        return null;
    }

    private static String trim(String s) {
        if (s == null) {
            return null;
        }
        String result = s.trim();
        return result.length() == 0 ? null : result;
    }

    private static String escapeMECARD(String input) {
        if ((input == null) || ((input.indexOf(':') < 0) && (input.indexOf(';') < 0))) {
            return input;
        }
        int length = input.length();
        StringBuilder result = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char c = input.charAt(i);
            if ((c == ':') || (c == ';')) {
                result.append('\\');
            }
            result.append(c);
        }
        return result.toString();
    }

}
