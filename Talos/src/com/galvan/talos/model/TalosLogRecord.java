package com.galvan.talos.model;

/**
 * Created by galvan on 4/11/15.
 */
public class TalosLogRecord {

    private String mLogLevel;
    private String mLogTag;
    private String mLogMessage;

    //Not in used
    private TalosLogRecord(){
        mLogLevel = "";
        mLogTag = "";
        mLogMessage = "";
    }


    public TalosLogRecord(String log){
        try{
            parseLogRecord(log);
        }
        catch(Exception e){
            mLogLevel = "E";
            mLogTag = "TalosLogRecord";
            mLogMessage = "Error occurred during log record parsing process";
        }
    }


    private void parseLogRecord(String log) {
        mLogLevel = "" + log.charAt(0);

        int tagEndIndex = getTagEndIndex(log);
        mLogTag = log.substring(2, tagEndIndex);
        mLogTag = mLogTag.trim();

        int messageStartIndex = getMessageStartIndex(tagEndIndex,log);
        mLogMessage = log.substring(messageStartIndex);
        mLogMessage = mLogMessage.trim();
    }


    private int getMessageStartIndex(int startIndex, String log) {
        return log.indexOf( "):" ) + 2;
    }


    private int getTagEndIndex(String log) {

        for(int index=0; index < log.length(); index++)
            if(log.charAt(index) == '(')
                return index;

        return -1;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Level:").append(mLogLevel).append(", ");
        builder.append("Tag:").append(mLogTag).append(", ");
        builder.append("Message:").append(mLogMessage);

        return builder.toString();
    }

    public String getLogLevel(){ return mLogLevel; }

    public String getLogTag(){ return mLogTag; }

    public String getLogMessage(){ return mLogMessage; }



    //TODO - Change the LogRecord to fit both iOS and Android
    //Setters

    public void setLogMessage(String mLogMessage) {
        this.mLogMessage = mLogMessage;
    }

    public void setLogLevel(String mLogLevel) {
        this.mLogLevel = mLogLevel;
    }

    public void setLogTag(String mLogTag) {
        this.mLogTag = mLogTag;
    }
}
