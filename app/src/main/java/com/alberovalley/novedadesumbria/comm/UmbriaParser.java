package com.alberovalley.novedadesumbria.comm;

/**
 * Created by frank on 10/02/17.
 */

import android.util.Log;

import com.alberovalley.novedadesumbria.utils.AlberoLog;

public class UmbriaParser {
    // ////////////////////////////////////////////////////////////
    // Constants
    // ////////////////////////////////////////////////////////////
    private static final String STORYTELLER_TAG = "<h3>Nuevos mensajes en partidas como director</h3>";
    private static final String PLAYER_TAG = "<h3>Nuevos mensajes en las partidas como jugador</h3>";
    private static final String VIP_TAG = "<h3>Nuevos mensajes en partidas VIP</h3>";
    private static final String PRIVATE_MESSAGES_TAG = "title=\"Mensajes privados\"><span>";

    private static final String NO_MSG_TAG = "<p>No hay novedades</p>";

    // ////////////////////////////////////////////////////////////
    // Methods
    // ////////////////////////////////////////////////////////////
    public static int findStorytellerMessages(String html) {
        AlberoLog.d("NovedadesParser.findStorytellerMessages ");
        return findMessageByTag(html, STORYTELLER_TAG);

    }

    public static int findPlayerMessages(String html) {
        AlberoLog.d("NovedadesParser.findPlayerMessages");
        return findMessageByTag(html, PLAYER_TAG);

    }

    public static int findVIPMessages(String html) {
        AlberoLog.d("NovedadesParser.findVIPMessages");
        return findMessageByTag(html, VIP_TAG);

    }

    /*
     * public static String findNumericalId(String html, String username) {
     * String numericalId = "";
     * String substring = html.substring(html.lastIndexOf(username));
     * return numericalId;
     * }
     */

    public static int findPrivateMessages(String html) {
        AlberoLog.d("NovedadesParser.findPrivateMessages ");
        int msg = 0;
        Log.w("novUmbria", "findPrivateMessages html" + html);
        final String tag = PRIVATE_MESSAGES_TAG;
        int indexOfTag = html.lastIndexOf(tag) + tag.length();
        if (indexOfTag > 0) {
            String substring = html.substring(
                    indexOfTag, indexOfTag + 1
            );
            AlberoLog.v("findPrivateMessages substring  " + substring);
            msg = Integer.parseInt(substring);
        } else {
            AlberoLog.v("findPrivateMessages indexOfTag <=0 ");
        }
        return msg;
    }

    public static int findMessageByTag(String html, String tag) {
        AlberoLog.d("findMessageByTag tag: " + tag);
        int msg = 0;
        int indexOfTag = html.lastIndexOf(tag);
        String substring = html.substring(indexOfTag + tag.length(), indexOfTag + (3 * tag.length()));
        AlberoLog.d("findMessageByTag substring: " + substring);
        indexOfTag = substring.lastIndexOf(NO_MSG_TAG);
        AlberoLog.d("findMessageByTag indexOfTag: " + indexOfTag);
        switch (indexOfTag) {
            case -1:
                // sí hay mensaje
                AlberoLog.d("findMessageByTag hay mensaje: ");
                msg = 1;
                break;
            default:
                // no hay mensaje
                msg = 0;
                AlberoLog.d("findMessageByTag no hay mensaje: ");

        }

        return msg;

    }

}