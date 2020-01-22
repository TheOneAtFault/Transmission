package com.ody.transmission;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ody.serial.Features.Text;
import com.ody.serial.Helpers.Response;
import com.ody.serial.Services.Communication;
import com.ody.serial.Services.Print;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

public class SerialActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private ArrayList<String> list = new ArrayList<>();
    private String printerPort = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serial);

        //Get ports
        String response = Communication.requestPorts();
        if (response != null && response.length() > 0){
            response = response.substring(0, response.length() - 1);
            String[] ports = response.split("~");

            //get usb devices and fill spinner
            Spinner spinner = (Spinner) findViewById(R.id.spn_serial_devices);
            spinner.setOnItemSelectedListener(this);

            for (int i = 0; i < ports.length; i++) {
                list.add(ports[i]);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

        }
        final TextView log = (TextView) findViewById(R.id.tv_serial_log);
        log.setText(response);

        //USB_Print text
        Button text = (Button) findViewById(R.id.btn_serial_printText);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Toast.makeText(SerialActivity.this,"USB_Print init",Toast.LENGTH_SHORT).show();
                    Response response = Print.text("Serial Port text print", printerPort, 9600,SerialActivity.this);
                    if (!response.isSuccess()){
                        log.setText(response.getsErrorMessage());
                    }
                }
                catch (Exception e){
                    Writer writer = new StringWriter();
                    e.printStackTrace(new PrintWriter(writer));
                    String s = writer.toString();
                    log.setText(s);
                }

            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getSelectedItem().toString();
        printerPort = text;
        TextView info = (TextView) findViewById(R.id.tv_serial_log);
        info.setText(printerPort+"");
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
