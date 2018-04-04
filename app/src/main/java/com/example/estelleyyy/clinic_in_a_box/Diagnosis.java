package com.example.estelleyyy.clinic_in_a_box;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Diagnosis extends AppCompatActivity {
    public final String ACTION_USB_PERMISSION = "com.example.estelleyyy.clinic_in_a_box.USB_PERMISSION";
    Button sendButton;
    TextView textView;
    EditText editText;
    UsbManager usbManager;
    UsbDevice device;
    UsbSerialDevice serialPort;
    UsbDeviceConnection connection;
    TextView testdisplay;
    List<String> armpit;

    UsbSerialInterface.UsbReadCallback mCallback = new UsbSerialInterface.UsbReadCallback() { //Defining a Callback which triggers whenever data is read.
        @Override
        public void onReceivedData(byte[] arg0) {
            String data = null;
            try {
                data = new String(arg0, "UTF-8");
                data.concat("/n");
                tvAppend(testdisplay, data+"--");
               if(armpit.size()<41) {
                   data.trim();
                   if(data.contains(".")&&!data.contains("found")&&data!="") {
                       armpit.add(data);
                   }
                }
                else
                {
                    tvAppend(testdisplay,"more");
                   /* double temp = CalculateFinalResult(armpit);
                    //((GlobalVariables) this.getApplication()).setTemp(temp);;
                    tvAppend(testdisplay,Double.toString(temp));
                    serialPort.close();
                    AlertDialog.Builder completion = new AlertDialog.Builder(Diagnosis.this);
                    completion.setMessage("You have been completed armpit temperature test. Click OK to view report.")
                            // .setCancelable(false)
                            .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                    Intent startNewActivity = new Intent(Diagnosis.this, TabReportActivity.class);
                                    startActivity(startNewActivity);
                                }
                            });
                    AlertDialog alert = completion.create();
                    alert.setTitle("Status");
                    alert.show();
                    alert.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(Color.parseColor("#ffffff"));
                    alert.getButton(AlertDialog.BUTTON_NEUTRAL).setBackgroundColor(Color.parseColor("#bf0913"));
                    int height = getResources().getDimensionPixelSize(R.dimen.dialog_height);
                    int width  = getResources().getDimensionPixelSize(R.dimen.dialog_width);
                    alert.getButton(AlertDialog.BUTTON_NEUTRAL).setWidth(width);
                    alert.getButton(AlertDialog.BUTTON_NEUTRAL).setHeight(height);
*/

                }
                System.out.println(Arrays.toString(armpit.toArray()));

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

            } //else if (intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_DETACHED)) {
                //onClickStop(stopButton);

            //}
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);
        testdisplay = (TextView) findViewById(R.id.textView3);
        usbManager = (UsbManager) getSystemService(this.USB_SERVICE);
        sendButton = (Button) findViewById(R.id.button4);
        armpit = new ArrayList<>();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_USB_PERMISSION);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        registerReceiver(broadcastReceiver, filter);
        onClickStart();

    }


    public void onClickStart() {
        HashMap<String, UsbDevice> usbDevices = usbManager.getDeviceList();
        if (!usbDevices.isEmpty()) {
            boolean keep = true;
            for (Map.Entry<String, UsbDevice> entry : usbDevices.entrySet()) {
                device = entry.getValue();
                //int deviceVID = device.getVendorId();
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
           // onClickSend("a");
            //   try {
            //     Thread.sleep(1000);
            //} catch (InterruptedException e) {
            //   e.printStackTrace();
            //}
           // onClickSend("a");
        }
    }


    public void goToNext(View v){
        Intent startNewActivity = new Intent(this, TabReportActivity.class);
        startActivity(startNewActivity);
    }

    public void onClickSend(View view) {
            serialPort.write("a".getBytes());
            serialPort.write("a".getBytes());

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

    double CalculateFinalResult(List<String> raw_result)
    {
        List<String> last_ten = raw_result;
        double temp = 0.0;
        int count = 0;
        for (int i=0; i < last_ten.size(); i++)
        {
            double element = Double.parseDouble(last_ten.get(i).split(":")[1]);
            if(i > 10)
            {
                temp = temp + element;
                count++;
            }
        }
        temp = temp / count;
        return temp;
    }
}


