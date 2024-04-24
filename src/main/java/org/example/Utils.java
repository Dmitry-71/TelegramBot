package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class Utils {
    public static String getURL(String nasaURL) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ObjectMapper mapper = new ObjectMapper();

        HttpGet httpGet = new HttpGet(nasaURL);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            NASAAnswer answer = mapper.readValue(response.getEntity().getContent(), NASAAnswer.class);
            return answer.url;
        } catch (Exception e){
            System.out.println("Сервер NASA недоступен");
        }
        return "";
    }
}
