package com.example.estelleyyy.clinic_in_a_box;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.felhr.usbserial.UsbSerialDevice;
import com.felhr.usbserial.UsbSerialInterface;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Diagnosis_BO extends AppCompatActivity {
    public final String ACTION_USB_PERMISSION = "com.example.estelleyyy.clinic_in_a_box.USB_PERMISSION";
    Button startButton, sendButton, clearButton, stopButton;
    TextView textView;
    EditText editText;
    UsbManager usbManager;
    UsbDevice device;
    UsbSerialDevice serialPort;
    UsbDeviceConnection connection;

    TextView testdisplay;
    String bo;
    String regex;

    UsbSerialInterface.UsbReadCallback mCallback = new UsbSerialInterface.UsbReadCallback() { //Defining a Callback which triggers whenever data is read.
        @Override
        public void onReceivedData(byte[] arg0) {
            String data = null;
            try {
                //tvAppend(testdisplay, Arrays.toString(arg0));
                data = new String(arg0, "UTF-8");
                data.concat("/n");
                if(data.matches(".*\\W.*")||data==null)
                {}
                else{
                //tvAppend(testdisplay, data);
                bo = data;
                    tvAppend(testdisplay,"{"+bo+"}");
                if(bo.trim().matches("[0-9]+"))
                {
                    tvAppend(testdisplay,"["+bo+"]");
                    CalculateFinalResult(bo);
                }
                else
                {
                    //tvAppend(testdisplay,"{"+data+"}");
                }
                }

/*
                if(bo.length()>5) {
                    bo = data;
                    tvAppend(testdisplay,"more");
                    serialPort.close();

                    CalculateFinalResult(bo);
                }
                else
                {

                   // tvchange(sendButton,"again");
                   tvAppend(testdisplay,"{" + data + "}");
                    data = data.trim();
                    if(data!=null) {
                        bo = " ";
                        bo = bo+data;
                        bo = bo.trim();
                        tvAppend(testdisplay, "add");
                    }
                }*/


            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
    };

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { //Broadcast Receiver to automatically start and stop the Serial connection.
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ACTION_USB_PERMISSION)) {
                boolean granted = intent.getExtras().getBoolean(UsbManager.EXTRA_PERMISSION_GRANTED);
                if (granted) {
                    connection = usbManager.openDevice(device);
                    serialPort = UsbSerialDevice.createUsbSerialDevice(device, connection);
                    if (serialPort != null) {
                        if (serialPort.open()) { //Set Serial Connection Parameters.
                            //setUiEnabled(true);
                            serialPort.setBaudRate(115200);
                            serialPort.setDataBits(UsbSerialInterface.DATA_BITS_8);
                            serialPort.setStopBits(UsbSerialInterface.STOP_BITS_1);
                            serialPort.setParity(UsbSerialInterface.PARITY_NONE);
                            serialPort.setFlowControl(UsbSerialInterface.FLOW_CONTROL_OFF);
                            serialPort.read(mCallback);
                            tvAppend(testdisplay,"Serial Connection Opened!\n");
                            //
                            // onClickSend("c"); //need edit
                        } else {
                            Log.d("SERIAL", "PORT NOT OPEN");
                        }
                    } else {
                        Log.d("SERIAL", "PORT IS NULL");
                    }
                } else {
                    Log.d("SERIAL", "PERM NOT GRANTED");
                }
            } else if (intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_ATTACHED)) {
              //  onClickStart();
                //onClickSend("temp");
            } //else if (intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_DETACHED)) {
            //onClickStop(stopButton);

            //}
        }

        ;
    };

    public void onClickStart() {
        tvAppend(testdisplay,"2");
        //testdisplay.setText("123");

        HashMap<String, UsbDevice> usbDevices = usbManager.getDeviceList();
        //tvAppend(testdisplay,"3");
        if (!usbDevices.isEmpty()) {
            boolean keep = true;
            for (Map.Entry<String, UsbDevice> entry : usbDevices.entrySet()) {
                device = entry.getValue();
                int deviceVID = device.getVendorId();

                //testdisplay.setText(deviceVID);
                //if (deviceVID == 0x2341)//Arduino Vendor ID
                //{
                PendingIntent pi = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
                usbManager.requestPermission(device, pi);
                tvAppend(testdisplay,"3");
                keep = false;
                //onClickSend("temp");
                //} else {
                //connection = null;
                //device = null;
                //}

                if (!keep)
                    break;
            }
        }




    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis__bo);
        testdisplay = (TextView) findViewById(R.id.textView16);
        sendButton = (Button) findViewById(R.id.spo2start);
        usbManager = (UsbManager) getSystemService(this.USB_SERVICE);
        IntentFilter filter = new IntentFilter();
        bo = "s";
        filter.addAction(ACTION_USB_PERMISSION);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        registerReceiver(broadcastReceiver, filter);
        onClickStart();
    }

    public void goToNext(View v){
        Intent startNewActivity = new Intent(this, Diagnosis.class);
        startActivity(startNewActivity);
    }

    public void onClickSend(View v) {
        serialPort.write("c".getBytes());
        //tvAppend(textView, "\nData Sent : " + string + "\n");
        tvchange(sendButton,"Waiting");
    }

    void CalculateFinalResult(String raw_result_1)
    {

        tvAppend(testdisplay,raw_result_1);
        double tempspo2 = Double.parseDouble(raw_result_1.trim());

        //((GlobalVariables) this.getApplication()).setBloodPressure(tempspo2);
        tvchange(sendButton,"Complete");
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Intent startNewActivity = new Intent(Diagnosis_BO.this, SignUp.class);
        startActivity(startNewActivity);

        return;
    }

    private void tvAppend(TextView tv, CharSequence text) {
        final TextView ftv = tv;
        final CharSequence ftext = text;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ftv.append(ftext);
            }
        });
    }

    private void tvchange(Button tv, CharSequence text) {
        final Button ftv = tv;
        final CharSequence ftext = text;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ftv.setText(ftext);
            }
        });
    }

    private void tvreset(TextView tv, CharSequence text) {
        final TextView ftv = tv;
        final CharSequence ftext = text;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ftv.setText(ftext);
            }
        });
    }
}
