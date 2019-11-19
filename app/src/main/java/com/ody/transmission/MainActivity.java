package com.ody.transmission;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ody.usb.Helpers.Response;
import com.ody.usb.Helpers.ReturnDevices;
import com.ody.usb.Services.Print;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class MainActivity extends AppCompatActivity {
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = getApplicationContext();
    }

    public void run(View view){
        try {
            Response Res =  Print.textPlain(context,0,"Ugh");
            TextView log = findViewById(R.id.tv_log);
            log.setText(Res.getsCustomMessage());
        }catch (Exception e){
            TextView log = findViewById(R.id.tv_log);
            Writer writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer));
            String s = writer.toString();
            log.setText(s);
        }
    }

    public void getDevices(View view){
        try{
            String list = ReturnDevices.getInstance().findDevices(context);
            TextView log = findViewById(R.id.tv_log);
            log.setText(list);
        }catch (Exception e){
            TextView log = findViewById(R.id.tv_log);
            Writer writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer));
            String s = writer.toString();
            log.setText(s);
        }
    }
}
