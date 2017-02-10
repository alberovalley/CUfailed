package com.alberovalley.novedadesumbria.comm;

/**
 * Created by frank on 10/02/17.
 */

import android.os.Parcel;
import android.os.Parcelable;

/**
 * POJO used to transfer the data received from the website
 *
 * @author frank
 */
public class UmbriaData implements Parcelable {
    public static final Parcelable.Creator<UmbriaData> CREATOR = new Parcelable.Creator<UmbriaData>() {
        public UmbriaData createFromParcel(Parcel in) {
            return new UmbriaData(in);
        }

        public UmbriaData[] newArray(int size) {
            return new UmbriaData[size];
        }
    };
    // ////////////////////////////////////////////////////////////
    // Attributes
    // ////////////////////////////////////////////////////////////
    protected String numericalId = "";
    protected int privateMessages = 0;
    protected int storytellerMessages = 0;
    protected int playerMessages = 0;
    protected int vipMessages = 0;
    protected boolean errorFlag = false;
    protected String errorMessageTitle = "";
    protected String errorMessageBody = "";
    protected boolean notifyPrivateMessages = false;
    protected boolean notifyStorytellerMessages = false;
    protected boolean notifyPlayerMessages = false;
    protected boolean notifyVipMessages = false;

    // ////////////////////////////////////////////////////////////
    // Constructors
    // ////////////////////////////////////////////////////////////
    public UmbriaData(Parcel in) {
        readFromParcel(in);
    }

    public UmbriaData() {
    }

    // ////////////////////////////////////////////////////////////
    // Methods
    // ////////////////////////////////////////////////////////////
    public void flagError(String errorMsgTitle, String errorMsgBody) {
        this.errorFlag = true;
        this.errorMessageTitle = errorMsgTitle;
        this.errorMessageBody = errorMsgBody;
    }

    // ////////////////////////////////////////////////////////////
    // Getters & Setters
    // ////////////////////////////////////////////////////////////
    public int getStorytellerMessages() {
        return storytellerMessages;
    }

    public void setStorytellerMessages(int storytellerMessages) {
        this.storytellerMessages = storytellerMessages;
    }

    public int getPlayerMessages() {
        return playerMessages;
    }

    public void setPlayerMessages(int playerMessages) {
        this.playerMessages = playerMessages;
    }

    public int getVipMessages() {
        return vipMessages;
    }

    public void setVipMessages(int vipMessages) {
        this.vipMessages = vipMessages;
    }

    public boolean isThereError() {
        return this.errorFlag;
    }

    public String getNumericalId() {
        return numericalId;
    }

    public void setNumericalId(String numericalId) {
        this.numericalId = numericalId;
    }

    public int getPrivateMessages() {
        return privateMessages;
    }

    public void setPrivateMessages(int privateMessages) {
        this.privateMessages = privateMessages;
    }

    public String getErrorMessageTitle() {
        return errorMessageTitle;
    }

    public void setErrorMessageTitle(String errorMessageTitle) {
        this.errorMessageTitle = errorMessageTitle;
    }

    public String getErrorMessageBody() {
        return errorMessageBody;
    }

    public void setErrorMessageBody(String errorMessageBody) {
        this.errorMessageBody = errorMessageBody;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean isNotifyPrivateMessages() {
        return notifyPrivateMessages;
    }

    public void setNotifyPrivateMessages(boolean notifyPrivateMessages) {
        this.notifyPrivateMessages = notifyPrivateMessages;
    }

    public boolean isNotifyStorytellerMessages() {
        return notifyStorytellerMessages;
    }

    public void setNotifyStorytellerMessages(boolean notifyStorytellerMessages) {
        this.notifyStorytellerMessages = notifyStorytellerMessages;
    }

    public boolean isNotifyPlayerMessages() {
        return notifyPlayerMessages;
    }

    public void setNotifyPlayerMessages(boolean notifyPlayerMessages) {
        this.notifyPlayerMessages = notifyPlayerMessages;
    }

    public boolean isNotifyVipMessages() {
        return notifyVipMessages;
    }

    public void setNotifyVipMessages(boolean notifyVipMessages) {
        this.notifyVipMessages = notifyVipMessages;
    }

    // ////////////////////////////////////////////////////////////
    // Parcelable implementation
    // ////////////////////////////////////////////////////////////
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(numericalId);
        dest.writeInt(privateMessages);
        dest.writeInt(storytellerMessages);
        dest.writeInt(playerMessages);
        dest.writeInt(vipMessages);
        int intErrorFlag = errorFlag ? 1 : 0;
        dest.writeInt(intErrorFlag);
        dest.writeString(errorMessageTitle);

        int iNotifyPrivateMessages = notifyPrivateMessages ? 1 : 0;
        dest.writeInt(iNotifyPrivateMessages);
        int iNotifyStorytellerMessages = notifyStorytellerMessages ? 1 : 0;
        dest.writeInt(iNotifyStorytellerMessages);
        int iNotifyPlayerMessages = notifyPlayerMessages ? 1 : 0;
        dest.writeInt(iNotifyPlayerMessages);
        int iNotifyVipMessages = notifyVipMessages ? 1 : 0;
        dest.writeInt(iNotifyVipMessages);

    }

    private void readFromParcel(Parcel in) {
        numericalId = in.readString();
        privateMessages = in.readInt();
        storytellerMessages = in.readInt();
        playerMessages = in.readInt();
        vipMessages = in.readInt();
        int intErrorFlag = in.readInt();
        errorFlag = intErrorFlag == 1;
        errorMessageTitle = in.readString();

        int iNotifyPrivateMessages = in.readInt();
        notifyPrivateMessages = iNotifyPrivateMessages == 1;
        int iNotifyStorytellerMessages = in.readInt();
        notifyStorytellerMessages = iNotifyStorytellerMessages == 1;
        int iNotifyPlayerMessages = in.readInt();
        notifyPlayerMessages = iNotifyPlayerMessages == 1;
        int iNotifyVipMessages = in.readInt();
        notifyVipMessages = iNotifyVipMessages == 1;

    }

}