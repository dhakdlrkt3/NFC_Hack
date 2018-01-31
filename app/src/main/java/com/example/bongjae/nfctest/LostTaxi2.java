package com.example.bongjae.nfctest;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LostTaxi2 extends Activity {

    TextView textview;
    Document doc = null;
    LinearLayout layout = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lost_taxi);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        textview = (TextView) findViewById(R.id.textView1);

        GetXMLTask task = new GetXMLTask();
        String api_key = "6154614f5869797336387955776172";
        String url = "http://openapi.seoul.go.kr:8088/" + api_key + "/xml/ListLostArticleService/1/200/t2/";
        task.execute(url);
    }

    private class GetXMLTask extends AsyncTask<String, Void, Document>{

        @Override
        protected Document doInBackground(String... urls) {
            URL url;
            try {
                url = new URL(urls[0]);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder(); //XML문서 빌더 객체를 생성
                doc = db.parse(new InputSource(url.openStream())); //XML문서를 파싱한다.
                doc.getDocumentElement().normalize();

            } catch (Exception e) {
                Toast.makeText(getBaseContext(), "Parsing Error", Toast.LENGTH_SHORT).show();
            }
            return doc;
        }

        @Override
        protected void onPostExecute(Document doc) {

            String s = "";
            //row태그가 있는 노드를 찾아서 리스트 형태로 만들어서 반환
            NodeList nodeList = doc.getElementsByTagName("row");
            //row태그를 가지는 노드를 찾음, 계층적인 노드 구조를 반환

            for(int i = 0; i< nodeList.getLength(); i++){

                //데이터를 추출
                s += "No." + i + "\n";
                Node node = nodeList.item(i); //row 노드

                Element fstElmnt = (Element) node;
                NodeList nameList  = fstElmnt.getElementsByTagName("ID");
                Element nameID = (Element) nameList.item(0);
                nameList = nameID.getChildNodes();
                s += "분실물 ID = "+ ((Node) nameList.item(0)).getNodeValue() +"\n";
                // <ID>분실물 ID</ID>

                NodeList getNameList = fstElmnt.getElementsByTagName("GET_NAME");
                // <GET_NAME>습득물품명</GET_NAME>
                s += "습득물품명 = "+  getNameList.item(0).getChildNodes().item(0).getNodeValue() +"\n";

                NodeList getDateList = fstElmnt.getElementsByTagName("GET_DATE");
                // <GET_DATE>습득일자</GET_DATE>
                s += "습득일자 = "+  getDateList.item(0).getChildNodes().item(0).getNodeValue() +"\n";

                NodeList takePlaceList = fstElmnt.getElementsByTagName("TAKE_PLACE");
                // <TAKE_PLACE>수령가능장소</TAKE_PLACE>
                s += "수령가능장소 = "+  takePlaceList.item(0).getChildNodes().item(0).getNodeValue() +"\n";

                NodeList contactList = fstElmnt.getElementsByTagName("CONTACT");
                // <CONTACT>수령가능장소연락처</CONTACT>
                s += "수령가능장소연락처 = "+  contactList.item(0).getChildNodes().item(0).getNodeValue() +"\n";

                NodeList cateList = fstElmnt.getElementsByTagName("CATE");
                // <CATE>습득물분류</CATE>
                s += "습득물분류 = "+  cateList.item(0).getChildNodes().item(0).getNodeValue() +"\n";

                NodeList get_positionList = fstElmnt.getElementsByTagName("GET_POSITION");
                // <GET_POSITION>습득위치_회사명</GET_POSITION>
                s += "습득위치_회사명 = "+  get_positionList.item(0).getChildNodes().item(0).getNodeValue() +"\n";

                NodeList statusList = fstElmnt.getElementsByTagName("STATUS");
                // <STATUS>분실물상태</STATUS>
                s += "분실물상태 = "+  statusList.item(0).getChildNodes().item(0).getNodeValue() +"\n\n-----------------------------------------------------\n\n";
            }
            textview.setText(s);
            super.onPostExecute(doc);
        }
    }
}
