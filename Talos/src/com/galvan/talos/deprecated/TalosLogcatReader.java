/*
package com.galvan.talos.deprecated;


import com.galvan.talos.client.TalosConfig;
import org.testng.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

*/
/**
 * Created by galvan on 4/11/15.
 *//*

abstract class TalosLogcatReader implements Runnable{

    private final int DELAY = 700;
    private final String ADB_LOGCAT_CMD = "./adb logcat ";

    private Thread t;
    private boolean exit = false;
    private Process process;

    private TalosConfig mConfigurations;


    //Constructor
    public TalosLogcatReader(TalosConfig configurations) {
        if(configurations == null)
            mConfigurations = new TalosConfig();
        else
            mConfigurations = configurations;

        if(!configurations.isValid()){
            Assert.fail("LogManagerConfig is not valid - Please verify you configurations");
        }

        t = new Thread(this);
        t.start();
    }


    */
/**
     * Disconnecting from logcat if a connection is opened.
     *//*

    public void stop(){
        try{
            System.out.println("LogHandler.stop()");
            exit = true;
            process.destroy();
        }catch (Exception e){
        }

    }


    public abstract void onLineAdd(String line);

    public void run(){
        while(!exit){
            try {
                clearPreviousLogs();
                connectToAdbLogcat();
                t.sleep(DELAY);
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void clearPreviousLogs() {
        System.out.println("Clear Previous Logs");

        try {
            String clearCmd = getCleanCmd(mConfigurations);
            Process process = Runtime.getRuntime().exec(clearCmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    */
/**
     * Returns String which represents shell command to clean the previous logs of LogCat
     * @param configurations - TalosConfigurations containing the required configurations.
     * @return String - adb clear command.
     *//*

    private String getCleanCmd(TalosConfig configurations) {
        StringBuilder result = new StringBuilder();

        result.append(configurations.getAdbPath()).append(ADB_LOGCAT_CMD).append("-c");

        return result.toString();
    }


    */
/**
     * Connects to the logcat via the adb. All the printed logs are streamed and parsed.
     * @throws IOException - if fails to open the connection.
     *//*

    private void connectToAdbLogcat() throws IOException{
        System.out.println("Connecting to logcat");

        String logcatConnectCmd = getLogcatConnectCmd(mConfigurations);

        process = Runtime.getRuntime().exec(logcatConnectCmd);
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));

        StringBuilder log = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            onLineAdd(line);
        }
    }


    */
/**
     * Returns String which represents shell command connect with adb logcat.
     * @param configurations - TalosConfigurations containing the required configurations.
     * @return String - adb clear command.
     *//*

    private String getLogcatConnectCmd(TalosConfig configurations) {
        StringBuilder result = new StringBuilder();

        result.append(configurations.getAdbPath()).append(ADB_LOGCAT_CMD).append("-T 1 ").append("-s ").append(configurations.getTagsToListen());

        return result.toString();
    }
}
*/
