package com.alberovalley.novedadesumbria.service.task;

/**
 * Created by frank on 10/02/17.
 */

import android.content.Context;

import com.alberovalley.novedadesumbria.R;
import com.alberovalley.novedadesumbria.comm.UmbriaData;
import com.alberovalley.novedadesumbria.comm.UmbriaLoginData;
import com.alberovalley.novedadesumbria.comm.UmbriaParser;
import com.alberovalley.novedadesumbria.utils.AlberoLog;
import com.alberovalley.novedadesumbria.utils.AppConstants;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper for different tasks (like log-in to the website and check for news)
 * to be carried out by Services
 *
 * @author frank
 */
public class TaskManager {
    // ////////////////////////////////////////////////////////////
    // Constants
    // ////////////////////////////////////////////////////////////
    private final static String URL_INICIAL = "http://www.comunidadumbria.com/front";

    // ////////////////////////////////////////////////////////////
    // Methods
    // ////////////////////////////////////////////////////////////
    public static UmbriaData getNovedades(UmbriaLoginData ld, Context ctx) {
        UmbriaData umbriadata = new UmbriaData();

        String html = "";
        HttpClient httpClient = new DefaultHttpClient();
        StringBuilder builder = new StringBuilder();
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair(UmbriaLoginData.userNameTAG, ld.getUserName()));
        nameValuePairs.add(new BasicNameValuePair(UmbriaLoginData.passwordTAG, ld.getPassword()));

        HttpPost request = new HttpPost(AppConstants.URL_NOVEDADES);

        try {
            request.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = httpClient.execute(request);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();

            AlberoLog.v("TaskManager.getNovedades código respuesta: " + statusCode);
            if (statusCode == 200) {
                /*
                 * Si todo fue ok, montamos la String con los datos en formato JSON
                 */
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }

                html = builder.toString();
                AlberoLog.d("TaskManager.getNovedades respuesta recibida: " + html);
                // TODO parseo

                umbriadata.setPlayerMessages(UmbriaParser.findPlayerMessages(html));

                umbriadata.setStorytellerMessages(UmbriaParser.findStorytellerMessages(html));

                umbriadata.setVipMessages(UmbriaParser.findVIPMessages(html));

                umbriadata.setPrivateMessages(UmbriaParser.findPrivateMessages(html));
            } else {
                umbriadata.flagError(
                        ctx.getResources().getString(R.string.error_wrong_response_title),
                        ctx.getResources().getString(R.string.error_wrong_response_body)
                );
                AlberoLog.i("TaskManager.getNovedades Respuesta incorrecta: " + statusCode);
            }
        } catch (UnsupportedEncodingException e) {
            umbriadata.flagError(
                    ctx.getResources().getString(R.string.error_encoding_title),
                    ctx.getResources().getString(R.string.error_encoding_body)
            );
            AlberoLog.e("TaskManager.getNovedades Problema de codificación " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalStateException e) {
            umbriadata.flagError(
                    ctx.getResources().getString(R.string.error_ilegal_state_title),
                    ctx.getResources().getString(R.string.error_ilegal_state_body)
            );
            AlberoLog.e("TaskManager.getNovedades IllegalStateException Problema de Estado Ilegal " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            umbriadata.flagError(
                    ctx.getResources().getString(R.string.error_ioexception_title),
                    ctx.getResources().getString(R.string.error_ioexception_body)
            );
            AlberoLog.e("TaskManager.getNovedades IOException " + e.getMessage());
            e.printStackTrace();
        }
        return umbriadata;

    }

    public static boolean login(UmbriaLoginData ld) throws IllegalStateException, IOException {
        boolean ok = false;

        HttpClient httpClient = new DefaultHttpClient();
        System.setProperty("http.maxRedirects", "3");

        StringBuilder builder = new StringBuilder();
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair(UmbriaLoginData.userNameTAG, ld.getUserName()));
        nameValuePairs.add(new BasicNameValuePair(UmbriaLoginData.passwordTAG, ld.getPassword()));

        HttpPost request = new HttpPost(AppConstants.URL_NOVEDADES);
        AlberoLog.v("TaskManager.login llamando a: " + URL_INICIAL);

        request.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));

        HttpResponse response = httpClient.execute(request);
        StatusLine statusLine = response.getStatusLine();
        int statusCode = statusLine.getStatusCode();
        AlberoLog.v("TaskManager.login código respuesta: " + statusCode);
        if (statusCode == 200) {

            HttpEntity entity = response.getEntity();
            InputStream content = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            String html = builder.toString();
            AlberoLog.v("TaskManager.login html: " + html);
            if (html.lastIndexOf("Has perdido tu clave") < 0) {
                AlberoLog.v("TaskManager.login OK : ");
                ok = true;
            } else {
                AlberoLog.w("TaskManager.login Falló el login : ");
            }
        }

        return ok;
    }

}