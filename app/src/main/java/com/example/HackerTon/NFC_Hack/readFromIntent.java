package com.example.HackerTon.NFC_Hack;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Parcelable;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;


public class readFromIntent extends AppCompatActivity {

    public static final String ERROR_DETECTED = "No NFC tag detected!";
    public static final String WRITE_SUCCESS = "Text written to the NFC tag successfully!";
    public static final String WRITE_ERROR = "Error during writing, is the NFC tag close enough to your device?";
    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    IntentFilter writeTagFilters[];
    boolean writeMode;
    Tag myTag;
    Context context;
    TextView tvNFCContent;
    NdefMessage ndefmsg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context mContext = getApplicationContext();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        AlertDialog.Builder aDialog = new AlertDialog.Builder(readFromIntent.this);
        View layout = inflater.inflate(R.layout.writetag,(ViewGroup) findViewById(R.id.writepopup));
        aDialog.setView(layout); //dialog.xml 파일을 뷰로 셋팅
        setContentView(R.layout.activity_readid);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        aDialog.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        //팝업창 생성
        AlertDialog ad = aDialog.create();
        ad.show();//보여줌!

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
 //read 시작
        tvNFCContent = (TextView) findViewById(R.id.textname);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            // Stop here, we definitely need NFC
            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
            finish();
        }
        //@Override
        protected void onResume() {
            super.onResume();
            Parcelable[] messages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES); //태그정보를 저장하기위한 배열;
            if (messages == null) return;
            for (int i = 0; i < messages.length; i++)
                setReadTagData((NdefMessage)messages[0]);
        }

        public void setReadTagDataa(NdefMessage ndefmsg) {
            if(ndefmsg == null ) {    //미태그시 반환;
                return ;

            }

            String msgs = "";
            msgs += ndefmsg.toString() + "\n";
            NdefRecord [] records = ndefmsg.getRecords() ;
            for(NdefRecord rec : records) {
                byte [] payload = rec.getPayload() ;
                String textEncoding = "UTF-8" ;
                if(payload.length > 0)
                    textEncoding = ( payload[0] & 0200 ) == 0 ? "UTF-8" : "UTF-16";   //nfc정보를 해당 형식으로 인코딩한다
                Short tnf = rec.getTnf();
                String type = String.valueOf(rec.getType());
                String payloadStr = new String(rec.getPayload(), Charset.forName(textEncoding));
            }
        }
       // readFromIntent(getIntent());
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter tagDetected = new IntentFilter(nfcAdapter.ACTION_TAG_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        writeTagFilters = new IntentFilter[] { tagDetected };
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
 //       readFromIntent(intent);
        if(nfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())){
            myTag = intent.getParcelableExtra(nfcAdapter.EXTRA_TAG);
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        WriteModeOff();
    }
    public static String byteArrayToHexString(byte[] b) {
        int len = b.length;
        String data = new String();

        for (int i = 0; i < len; i++){
            data += Integer.toHexString((b[i] >> 4) & 0xf);
            data += Integer.toHexString(b[i] & 0xf);
        }
        return data;
    }


    출처: http://biig.tistory.com/78 [덩치의 안드로이드 스터디]


    /******************************************************************************
     **********************************Enable Write********************************
     ******************************************************************************/

    private void WriteModeOn(){
        writeMode = true;
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, writeTagFilters, null);
    }
    /******************************************************************************
     **********************************Disable Write*******************************
     ******************************************************************************/

    private void WriteModeOff(){
        writeMode = false;
        nfcAdapter.disableForegroundDispatch(this);
    }

}
