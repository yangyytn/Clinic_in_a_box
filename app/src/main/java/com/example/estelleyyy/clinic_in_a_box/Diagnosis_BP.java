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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Diagnosis_BP extends AppCompatActivity {
    public final String ACTION_USB_PERMISSION = "com.example.estelleyyy.clinic_in_a_box.USB_PERMISSION";
    Button sendButton;
    UsbManager usbManager;
    UsbDevice device;
    UsbSerialDevice serialPort;
    UsbDeviceConnection connection;
    TextView testdisplay;
    String sys;
    List<String> dia;
    String regex;
    int count=1;

    UsbSerialInterface.UsbReadCallback mCallback = new UsbSerialInterface.UsbReadCallback() { //Defining a Callback which triggers whenever data is read.
        @Override
        public void onReceivedData(byte[] arg0) {
            String data = null;
            try {
                data = new String(arg0, "UTF-8");
                data.concat("/n");

                tvAppend(testdisplay, "[" + data+ "]");
                if(!sys.contains("Pulse")) {
                    tvAppend(testdisplay,"{" + data + "}");
                    sys = sys + data;
                }
                else
                {
                    tvAppend(testdisplay,"more");
                    serialPort.close();
                    if(count == 1) {
                        CalculateFinalResult(sys);
                    }
                    else{serialPort.close();}

                    serialPort.close();
                    count =0;
                }

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis__bp);
        testdisplay = (TextView) findViewById(R.id.textView17);
        sendButton = (Button) findViewById(R.id.bpstart);
        usbManager = (UsbManager) getSystemService(this.USB_SERVICE);
        IntentFilter filter = new IntentFilter();
        sys = " ";
        //count = 1;
        filter.addAction(ACTION_USB_PERMISSION);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        registerReceiver(broadcastReceiver, filter);
        onClickStart();
    }


    public void onClickStart() {
        tvAppend(testdisplay,"2");

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


    public void goTobo(View v){

        // for testing only! go to questionnaire page
        Intent startNewActivity = new Intent(this, Diagnosis_BO.class);
        //Intent startNewActivity = new Intent(this, Diagnosis_BO.class);
        startActivity(startNewActivity);
    }

    public void onClickSend(View v) {
        serialPort.write("b".getBytes());
        //tvAppend(textView, "\nData Sent : " + string + "\n");
        try{

            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        serialPort.write("b".getBytes());
        tvchange(sendButton,"Waiting");

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

    void CalculateFinalResult(String raw_result_1)
    {
        String word1 = "Systolic";
        String word2 = "Diastolic";
        String word3 = "Pulse";
        double tempsys = 0.0;
        double tempdia = 0.0;
        //int count = 0;

        tempsys = Double.parseDouble(raw_result_1.substring((raw_result_1.indexOf(word1)+8),(raw_result_1.indexOf(word2))));

        tempdia = Double.parseDouble(raw_result_1.substring((raw_result_1.indexOf(word2)+9),raw_result_1.indexOf(word3)));
       tvAppend(testdisplay,Double.toString(tempsys));
        tvAppend(testdisplay,Double.toString(tempdia));
        double[] result = new double[2];
        result[0] = tempsys;
        result[1] = tempdia;
        ((GlobalVariables) this.getApplication()).setBloodPressure(result);

        tvchange(sendButton,"Complete");
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Intent startNewActivity = new Intent(Diagnosis_BP.this, Diagnosis_BO.class);
        startActivity(startNewActivity);

        count++;

        return;
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
