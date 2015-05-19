package com.galvan.talos.client;

/**
 * Created by galvan on 4/11/15.
 */
public class TalosConfig {

    //Describe the available platforms
    public static final int ANDROID = 1;
    public static final int IOS = 2;

    //State
    private String mAdbPath = "";
    private String mTagsToListen = "";
    private int mPlatform = -1;


    //Constructor
    public TalosConfig(){
    }

    //Setters
    public TalosConfig setAdbPath(String absolutePath){
        mAdbPath = absolutePath;
        return this;
    }

    public TalosConfig setPlatform(int platform){
        mPlatform = platform;
        return this;
    }

    public TalosConfig addTagToListen(String tag/*, String level*/){
        if(tag == null/* || level == null*/)
            return this;

        String tagLevel = "*";

        //TODO - Handle the log level of the Tag - getting error when using it
        /*if(VERBOSE.equals(level) || DEBUG.equals(level) || INFO.equals(level) || WARNING.equals(level) || ERROR.equals(level))
            tagLevel = level;*/

        mTagsToListen += tag + ":" + tagLevel + " ";

        return this;
    }





    //Getters

    /**
     * Returns the path to ADB file (under the sdk folder).
     * @return String - represents the path to the adb file.
     */
    String getAdbPath() { return mAdbPath; }

    /**
     * Rreturns String represents the tags and the level of each tag to listen.
     * @return String - TAGS and Level of each tag.
     */
    String getTagsToListen() { return mTagsToListen; }

    /**
     * Return the platform which is set in the config file.
     * @return int - represents the platform (ANDROID = 1 , IOS = 2).
     */
    public int getPlatform() { return mPlatform; }

    /**
     * Validates the configuration object.
     * @return - boolean - return true if the configurations are valid, false otherwise.
     */
    boolean isValid(){
        return (mAdbPath != null && !mAdbPath.equals("")) &&
                (mTagsToListen != null && !mTagsToListen.equals("")) &&
                (mPlatform == ANDROID || mPlatform == IOS);
    }




    /*public static final String VERBOSE = "V";
    public static final String DEBUG = "D";
    public static final String INFO = "I";
    public static final String WARNING = "W";
    public static final String ERROR = "E";*/
}
