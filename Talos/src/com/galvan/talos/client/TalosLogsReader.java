package com.galvan.talos.client;

import org.testng.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by galvan on 4/24/15.
 */
abstract class TalosLogsReader implements Runnable{

    private final int DELAY = 1000;

    private boolean mShouldRun = true;
    private Thread mLogsReaderThread;
    private TalosConfig mConfigurations;
    private LogHandler mLogHandler;

    private Process mReadProcess;


    public TalosLogsReader(TalosConfig configurations, LogHandler logHandler){
        if(configurations == null)
            mConfigurations = new TalosConfig();
        else
            mConfigurations = configurations;

        if(!configurations.isValid()){
            Assert.fail("LogManagerConfig is not valid - Please verify you configurations");
        }

        mLogHandler = logHandler;

        mLogsReaderThread = new Thread(this);
        mLogsReaderThread.start();
    }


    @Override
    public void run() {
        while(mShouldRun){
            try {
                clearPreviousLogs();
                connectToLogs();
                mLogsReaderThread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void clearPreviousLogs() {
        System.out.println("Clear Previous Logs");

        try {
            String clearCmd = getCleanCommand(mConfigurations);

            //if the Logger supports clear options
            if(clearCmd != null){
                Process process = Runtime.getRuntime().exec(clearCmd);
                process.waitFor();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void connectToLogs() throws IOException {
        System.out.println("Connecting to logcat");

        String logcatConnectCmd = getConnectCommand(mConfigurations);

        mReadProcess = Runtime.getRuntime().exec(logcatConnectCmd);
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(mReadProcess.getInputStream()));

        StringBuilder log = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            if(mLogHandler != null)
                mLogHandler.onLogAdded(line);
            //onLineAdd(line);
        }
    }


    /**
     * Disconnecting from log reader if a connection is opened.
     */
    public void stop(){
        try{
            mShouldRun = false;

            if(mReadProcess != null)
                mReadProcess.destroy();
        }catch (Exception e){
        }

    }

    //Abstract Methods
    //protected abstract void onLineAdd(String line);

    protected abstract String getCleanCommand(TalosConfig mConfigurations);

    protected abstract String getConnectCommand(TalosConfig configurations);



    interface LogHandler {
        void onLogAdded(String line);
    }
}
