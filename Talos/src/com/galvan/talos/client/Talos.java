package com.galvan.talos.client;

import com.galvan.talos.interfaces.TalosLogDetector;
import com.galvan.talos.model.TalosLogRecord;
import com.galvan.talos.waiters.LogsWithOrderWaiter;
import com.galvan.talos.waiters.LogsWithoutOrderWaiter;
import com.galvan.talos.waiters.MaxLogsWaiter;
import com.galvan.talos.waiters.OneOfLogsWaiter;
import com.galvan.talos.waiters.TalosLogWaiter;

import java.util.ArrayList;

import io.appium.java_client.AppiumDriver;

/**
 * Created by galvan on 4/11/15.
 */
//TODO - Add the support of end test
public class Talos  implements TalosLogsReader.LogHandler{

    private TalosLogsReader mLogReader;
    private AppiumDriver mDriver;							//The AppiumDriver that in used by the publisher

    private TalosLogWaiter mWaiter;

    //Empty Constructor - not in use
    @SuppressWarnings("unused")
    private Talos(){}


    //Constructor
    public Talos(AppiumDriver driver, TalosConfig configurations){
        initilizeState();

        mDriver = driver;

        //Use the LogsReader according to the platform
        if(configurations.getPlatform() == TalosConfig.ANDROID){
            mLogReader = new AndroidLogsReader(configurations,this);
        }

        else if(configurations.getPlatform() == TalosConfig.IOS){
            mLogReader = new IOSLogsReader(configurations, this);
        }

    }


    /**
     * Initialize the state if this LogManager - initialize all data structures.
     * Should be called in the constructor and after every test.
     */
    private void initilizeState(){
        mWaiter = null;
    }



    public void interestedInLogs(TalosLogDetector... detectors){
        mWaiter = new LogsWithoutOrderWaiter(mDriver, parseLogDetectors(detectors));
    }

    public void interestedInLogsWithOrder(TalosLogDetector... orderedDetectors){
        mWaiter = new LogsWithOrderWaiter(mDriver, parseLogDetectors(orderedDetectors));
    }

    public void interestedInOneOf(TalosLogDetector ... detectors ){
        mWaiter = new OneOfLogsWaiter(mDriver, parseLogDetectors(detectors));
    }

    public void interestInMaxLogs(TalosLogDetector ... detectors){
        mWaiter = new MaxLogsWaiter(mDriver, parseLogDetectors(detectors));
    }


    /**
     * Creates an ArrayList of LogDetector from the given detectors.
     * @param detectors - A set of LogDetectors.
     * @return ArrayList<LogDetector> - contains from the given detectors.
     */
    private ArrayList<TalosLogDetector> parseLogDetectors(TalosLogDetector... detectors){
        ArrayList<TalosLogDetector> result = new ArrayList<TalosLogDetector>();

        if(detectors != null){
            for(TalosLogDetector d : detectors){
                if(d != null)
                    result.add(d);
            }
        }

        return result;
    }

    public ArrayList<TalosLogDetector> waitForLogs(int timeout){
        return mWaiter.waitForLogs(timeout);
    }


    public void endTest() {
        mLogReader.stop();
    }



    @Override
    public void onLogAdded(String line) {
        System.out.println(line);
        TalosLogRecord logRecord = new TalosLogRecord("");
        logRecord.setLogMessage(line);


        if(mWaiter != null)
            mWaiter.handleLog(logRecord);

        //TODO - Remove the hard coded filter and add it to the configurations
        //if(mWaiter != null && line.contains("UltraApp"))
        //    mWaiter.handleLog(logRecord);
    }
}
