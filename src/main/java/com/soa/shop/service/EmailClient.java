package com.soa.shop.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmailClient {

    private static String APIKey = "ev-0048d99b139310391bc01cd8382e97fa";
    private static String APIURL = "https://api.email-validator.net/api/verify";

    public static boolean verify(String email) {
        HttpClient client = new DefaultHttpClient();

        try {
            HttpPost request = new HttpPost(APIURL);
            List<NameValuePair> Input = new ArrayList<>();
            Input.add(new BasicNameValuePair("EmailAddress", email));
            Input.add(new BasicNameValuePair("APIKey", APIKey));
            request.setEntity(new UrlEncodedFormEntity(Input));
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            String output = EntityUtils.toString(entity, "UTF-8");
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(output);
            JSONObject jsonObject = (JSONObject) obj;
            Long result = (Long) jsonObject.get("status");
            if (result == 200 || result == 207 || result == 215) {
                return true;
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        } finally {
            client.getConnectionManager().shutdown();
        }

        return false;
    }
}
