package com.alberovalley.novedadesumbria.comm;

/**
 * Created by frank on 10/02/17.
 */

import android.content.Context;

import com.alberovalley.novedadesumbria.R;
import com.alberovalley.novedadesumbria.utils.AlberoLog;
import com.alberovalley.novedadesumbria.utils.AlberoStrings;


/**
 * Collection of methods that transform the data received into information
 *
 * @author frank
 */
public class UmbriaMessenger {
    // ////////////////////////////////////////////////////////////
    // Methods
    // ////////////////////////////////////////////////////////////

    /**
     * receives UmbriaData and decides if there's news or not
     *
     * @param data
     * @return boolean
     * @throws UmbriaConnectionException
     */
    public static boolean isThereAnythingNew(UmbriaData data) throws UmbriaConnectionException {
        boolean somethingNew = false;
        AlberoLog.d("UmbriaMessenger.isThereAnythingNew: ");
        if (!data.isThereError()) {
            int player = 0, storyteller = 0, vip = 0, pMessages = 0;
            // only take into account news the user wants to be notified about
            if (data.notifyPlayerMessages)
                player = data.getPlayerMessages();
            if (data.notifyStorytellerMessages)
                storyteller = data.getStorytellerMessages();
            if (data.notifyVipMessages)
                vip = data.getVipMessages();
            if (data.notifyPrivateMessages)
                pMessages = data.getPrivateMessages();
            somethingNew = (player + storyteller + vip + pMessages) != 0;
        } else {
            // there was some error, throw UmbriaConnectionException
            throw new UmbriaConnectionException(data.getErrorMessageTitle() + " \n " + data.getErrorMessageBody());
        }

        return somethingNew;
    }

    public static String makeNotificationText(UmbriaData data, Context ctx) {
        String message = "";
        AlberoLog.d("UmbriaMessenger.makeNotificationText: ");
        if (!data.isThereError()) {
            message = "";
            try {
                if (isThereAnythingNew(data)) {
                    message = ctx.getResources().getString(R.string.new_messages);
                    String temp = message;
                    if (data.isNotifyPlayerMessages() && data.getPlayerMessages() > 0)
                        message += " " + ctx.getResources().getString(R.string.new_messages_player);
                    if (data.notifyStorytellerMessages && data.getStorytellerMessages() > 0) {
                        message = AlberoStrings
                                .appendComma(
                                        temp,
                                        message,
                                        ctx.getResources().getString(R.string.new_messages_storyteller));
                    }
                    if (data.notifyVipMessages && data.getVipMessages() > 0) {
                        message = AlberoStrings
                                .appendComma(
                                        temp,
                                        message,
                                        ctx.getResources().getString(R.string.new_messages_vip));

                    }
                    if (data.notifyPrivateMessages && data.getPrivateMessages() > 0) {
                        message = AlberoStrings
                                .appendComma(
                                        temp,
                                        message,
                                        ctx.getResources().getString(R.string.new_messages_private));
                    }
                } else {
                    message = ctx.getResources().getString(R.string.widget_text_empty);
                }
            } catch (UmbriaConnectionException e) {
                message = e.getMessage();
            }

        } else {
            message = data.getErrorMessageTitle();
            AlberoLog.e("UmbriaMessenger.makeNotificationText Error en comunicación: " + data.getErrorMessageTitle() + " "
                    + data.getErrorMessageBody());
        }
        AlberoLog.d("UmbriaMessenger.makeNotificationText mensaje: " + message);
        return message;
    }
}