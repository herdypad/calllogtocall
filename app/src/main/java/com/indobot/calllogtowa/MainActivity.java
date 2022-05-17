package com.indobot.calllogtowa;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.CallLog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;

import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALL_LOG},PackageManager.PERMISSION_GRANTED);

        textView = findViewById(R.id.textView);
        btn = findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                textView.setText("Loading");
                String stringOutput ="";
                textView.setText(getCallLog());
                Log.d("my log", stringOutput);
            }
        });


    }





    private String getCallLog() {
        StringBuffer sb = new StringBuffer();
        String stt = "";
        Cursor manageCursur = getContentResolver().query(CallLog.Calls.CONTENT_URI,null,null,null,null);
        int number =manageCursur.getColumnIndex(CallLog.Calls.NUMBER);
        int date =  manageCursur.getColumnIndex(CallLog.Calls.DATE);
        int type = manageCursur.getColumnIndex(CallLog.Calls.TYPE);

        while (manageCursur.moveToNext()){
            String phNumber = manageCursur.getString(number);
            String callType = manageCursur.getString(type);

            String phdate = manageCursur.getString(date);
            Date callDayTime = new Date(Long.valueOf(phdate));
            SimpleDateFormat formater = new SimpleDateFormat("dd-mm-yy HH:mm");
            String dateString = formater.format(callDayTime);

            sb.append("nomor  : "+phNumber +"\n"+callType +"\n"+dateString+"\n");

            stt = "nomor  : "+phNumber +"\n"+callType +"\n"+dateString+"\n";

        }
        manageCursur.close();
        return sb.toString();
    }
}