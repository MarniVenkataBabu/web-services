package com.cglia.springboot.webservices.dao;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Repository;


@Repository
public class BackendServiceClient {
	
    private final CloseableHttpClient httpClient;
    
    private String url = "http://192.168.60.55:7000";

    public BackendServiceClient() {
        this.httpClient = HttpClients.createDefault();
    }

    public String getData() throws IOException {
        HttpGet request = new HttpGet(url+"/getall");
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity);
            }
        }
        return null;
    }
    
    public String getSingleData(int id) throws IOException {
        HttpGet request = new HttpGet(url+"/getone/" + id);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity);
            }
        }
        return null;
    }
    
    public void insertData(String data) throws IOException {
        HttpPost request = new HttpPost(url+"/save");
        request.setEntity(new StringEntity(data));
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            // Process the response as needed
        	   HttpEntity entity = response.getEntity();
        }
    }

    public void updateData(int id, String updatedData) throws IOException {
        HttpPut request = new HttpPut(url+"/update/" + id);
        request.setEntity(new StringEntity(updatedData));
        try (CloseableHttpResponse response = httpClient.execute(request)) {
        	  // Process the response as needed
        	   HttpEntity entity = response.getEntity();
        }
    }

    public void deleteData(int id) throws IOException {
        HttpDelete request = new HttpDelete(url+"/delete/" + id);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
        	  // Process the response as needed
        	   HttpEntity entity = response.getEntity();
        }
    }

    public void close() throws IOException {
        httpClient.close();
    }
}
