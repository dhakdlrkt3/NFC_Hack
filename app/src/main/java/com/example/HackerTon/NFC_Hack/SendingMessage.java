package com.example.HackerTon.NFC_Hack;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class SendingMessage extends AppCompatActivity {

    NfcAdapter nfcAdapter;

    EditText phoneno, smsmessage;
    Button callbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        phoneno = (EditText) findViewById(R.id.editCellphonemessage);
        smsmessage = (EditText) findViewById(R.id.editMessage);
        callbtn = (Button) findViewById(R.id.messagebutton);

        callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Context mContext = getApplicationContext();
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

                //R.layout.dialog는 xml 파일명이고  R.id.popup은 보여줄 레이아웃 아이디
                View layout = inflater.inflate(R.layout.writetag,(ViewGroup) findViewById(R.id.writepopup));
                AlertDialog.Builder aDialog = new AlertDialog.Builder(SendingMessage.this);

                //aDialog.setTitle("태그를 터치하세요"); //타이틀바 제목
                aDialog.setView(layout); //dialog.xml 파일을 뷰로 셋팅

                ///그냥 닫기버튼을 위한 부분
                aDialog.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                //팝업창 생성
                AlertDialog ad = aDialog.create();
                ad.show();//보여줌!

            }
        });

    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onResume() {
        super.onPostResume();

        enableForegroundDispatchSystem();
    }

    @Override
    protected void onPause() {
        super.onPause();
        disableForegroundDispatchSystem();

    }

    //Enable foreground dispatcher
    private void enableForegroundDispatchSystem() {


        Intent intent = new Intent(this, SendingMessage.class).addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        IntentFilter[] intentFilters = new IntentFilter[]{};
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilters, null);


    }

    //disable foreground dispatcher
    private void disableForegroundDispatchSystem() {
        nfcAdapter.disableForegroundDispatch(this);

    }

    @Override
    public void onNewIntent(Intent intent) {
        Log.i("Foreground dispatch", "Discovered tag with intent: " + intent);
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        String externalType = "nfclab.com:smsService";
        String smsNumber = phoneno.getText().toString();
        String smsBody = smsmessage.getText().toString();
        String urlAddress = "sms:"+smsNumber+"?body="+smsBody;
        NdefRecord smsUriRecord = NdefRecord.createUri(urlAddress);
        //NdefRecord extRecord = new NdefRecord(NdefRecord.TNF_EXTERNAL_TYPE, externalType.getBytes(), new byte[0], urlAddress.getBytes());
        NdefMessage newMessage = new NdefMessage(new NdefRecord[] { smsUriRecord});
        writeNdefMessageToTag(newMessage, tag);
    }

    boolean writeNdefMessageToTag(NdefMessage message, Tag detectedTag) {
        int size = message.toByteArray().length;
        try {
            Ndef ndef = Ndef.get(detectedTag);
            if (ndef != null) {
                ndef.connect();
                if (!ndef.isWritable()) {
                    Toast.makeText(this, "Tag is read-only.", Toast.LENGTH_SHORT).show();
                    return false;
                }
                if (ndef.getMaxSize() < size) {
                    Toast.makeText(this, "The data cannot written to tag, Tag capacity is " + ndef.getMaxSize() + " bytes, message is "
                            + size + " bytes.", Toast.LENGTH_SHORT).show();
                    return false;
                }
                ndef.writeNdefMessage(message);
                ndef.close();
                Toast.makeText(this, "저장 완료",
                        Toast.LENGTH_SHORT).show();
                SendingMessage.this.finish();
                return true;
            } else {
                NdefFormatable ndefFormat = NdefFormatable.get(detectedTag);
                if (ndefFormat != null) {
                    try {
                        ndefFormat.connect();
                        ndefFormat.format(message);
                        ndefFormat.close();
                        Toast.makeText(this, "저장 완료",
                                Toast.LENGTH_SHORT).show();
                        return true;
                    } catch (IOException e) {
                        Toast.makeText(this, "저장 실패",
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }
                } else {
                    Toast.makeText(this, "NDEF is not supported",
                            Toast.LENGTH_SHORT).show();
                    return false;
                }

            }
        } catch (Exception e) {
            Toast.makeText(this, "Write opreation is failed",
                    Toast.LENGTH_SHORT).show();
        }
        return false;
    }

}