package com.gaia.app.smartwarehouse.service;

import android.os.AsyncTask;

import com.gaia.app.smartwarehouse.classes.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import static com.gaia.app.smartwarehouse.service.CommonUtilities.ITEM_URL;

/**
 * Created by anant on 21/06/16.
 */

public class ItemGetTask extends AsyncTask <String, Void, String> {

    private String email,JSON_STRING, iname, cname, unit, weight, quant;
    private String TAG_ITEM="items";
    private String TAG_NAME="iname";
    private String TAG_CAT="cname";
    private String TAG_UNIT="unit";
    private String TAG_WEIGHT="weight";
    private String TAG_QUANT="quant";
    private JSONObject jsonObject,JO;
    private JSONArray jsonArray;
    private ArrayList<Item> itemArrayList= new ArrayList<>();
    private ArrayList<String> categories;

    public interface PlottingItems {
        void setItems(String cname ,ArrayList<Item> items);

    }

     PlottingItems plot = null;

    public ItemGetTask(PlottingItems plot){
        this.plot = plot;
    }


    @Override
    protected String doInBackground(String... strings) {
        try {
            email = strings[0];


            URL url = new URL(ITEM_URL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") ;
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();


            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "ISO-8859-1"));
            StringBuilder stringBuilder = new StringBuilder();

            while ((JSON_STRING = bufferedReader.readLine()) != null) {
                stringBuilder.append(JSON_STRING + "\n");
            }

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return stringBuilder.toString().trim();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {

            jsonObject = new JSONObject(s);
            jsonArray = jsonObject.getJSONArray(email);



            for (int i=0; i<jsonArray.length(); i++){


                JO = jsonArray.getJSONObject(i);
                cname=JO.getString(TAG_CAT);
                //categories.add(cname);
                JSONArray items=JO.getJSONArray(TAG_ITEM);
                itemArrayList.clear();

                for (int j=0; j<items.length(); j++) {
                    JO=items.getJSONObject(j);
                    iname = JO.getString(TAG_NAME);
                    weight = JO.getString(TAG_WEIGHT);
                    unit = JO.getString(TAG_UNIT);
                    quant = JO.getString(TAG_QUANT);
                    Item item = new Item(iname, cname, unit, weight, quant);
                    itemArrayList.add(item);

                }
                plot.setItems(cname,itemArrayList);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }




    }
}
