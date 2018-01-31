package com.example.bongjae.nfctest;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Public_institution extends Activity {

    TextView textview;
    Document doc = null;
    LinearLayout layout = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_institution);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        textview = (TextView) findViewById(R.id.textView1);

        GetXMLTask task = new GetXMLTask();
        //String api_key = "";
        String url = "http://apis.data.go.kr/1320000/LosfundInfoInqireService/getLosfundInfoAccToClAreaPd?serviceKey=sNPGscNguU3hts7hokCosm6jB819ahxyAZJ8I9VUTRs94PlOg8M9JehtQceZToKaRwpDhKrjOhRt1fqFvbxBpA%3D%3D&N_FD_LCT_CD=LCA000&numOfRows=100";
        task.execute(url);
    }

    private class GetXMLTask extends AsyncTask<String, Void, Document> {

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
            NodeList nodeList = doc.getElementsByTagName("item");
            //row태그를 가지는 노드를 찾음, 계층적인 노드 구조를 반환

            for(int i = 0; i< nodeList.getLength(); i++) {

                //데이터를 추출
                s += "No." + i + "\n";
                Node node = nodeList.item(i); //row 노드

                Element fstElmnt = (Element) node;
                NodeList nameList  = fstElmnt.getElementsByTagName("depPlace");
                Element nameID = (Element) nameList.item(0);
                nameList = nameID.getChildNodes();
                s += "보관장소 = "+ ((Node) nameList.item(0)).getNodeValue() +"\n";

                NodeList getNameList = fstElmnt.getElementsByTagName("fdFilePathImg");

                if(getNameList.item(0).getChildNodes().item(0).getNodeValue().toString().equals("https://www.lost112.go.kr/lostnfs/images/sub/img02_no_img.gif")) {
                    s += "습득물 사진 이미지 = 이미지가 준비중입니다.\n";
                } else {
                    s += "습득물 사진 이미지 = "+  getNameList.item(0).getChildNodes().item(0).getNodeValue() +"\n";
                }
                NodeList getDateList = fstElmnt.getElementsByTagName("fdPrdtNm");
                s += "물품명 = "+  getDateList.item(0).getChildNodes().item(0).getNodeValue() +"\n";

                NodeList takePlaceList = fstElmnt.getElementsByTagName("fdSbjt");
                s += "게시제목 = "+  takePlaceList.item(0).getChildNodes().item(0).getNodeValue() +"\n";

                NodeList cateList = fstElmnt.getElementsByTagName("fdYmd");
                s += "습득일자 = "+  cateList.item(0).getChildNodes().item(0).getNodeValue() +"\n";

                NodeList get_positionList = fstElmnt.getElementsByTagName("prdtClNm");
                s += "물품분류명 = "+  get_positionList.item(0).getChildNodes().item(0).getNodeValue() +"\n\n-----------------------------------------------------\n\n";
            }
            textview.setText(s);
            super.onPostExecute(doc);
        }
    }
}