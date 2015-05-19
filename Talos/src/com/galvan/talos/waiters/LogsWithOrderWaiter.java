package com.galvan.talos.waiters;

import com.galvan.talos.interfaces.TalosLogDetector;
import com.galvan.talos.model.TalosLogRecord;

import java.util.ArrayList;
import java.util.Iterator;

import io.appium.java_client.AppiumDriver;

/**
 * Created by galvan on 4/11/15.
 */
public class LogsWithOrderWaiter extends TalosLogWaiter{

    //State
    private ArrayList<TalosLogDetector> mRequetedLogsDetectors;



    //Constructor
    public LogsWithOrderWaiter(AppiumDriver driver, ArrayList<TalosLogDetector> detectors){
        super(driver);
        if(detectors == null)
            mRequetedLogsDetectors = new ArrayList<TalosLogDetector>();
        else
            mRequetedLogsDetectors = detectors;
    }



    //LogWaiter interface
    @Override
    protected void onLogReceived(TalosLogRecord log) {
        if(log == null)
            return;

        synchronized(mRequetedLogsDetectors){
            Iterator<TalosLogDetector> iter = mRequetedLogsDetectors.iterator();

            boolean found = false;

            while(iter.hasNext() && !found){
                TalosLogDetector currentDetector = iter.next();
                if(currentDetector.detect(log)){
                    iter.remove();
                    addDetectorToResult(currentDetector);
                    found = true;
                }
            }

        }
    }


    //AbstractLogWaiter superclass
    @Override
    protected boolean shouldStopWaiting() {
        synchronized (mRequetedLogsDetectors) {
            //If the result is 'true' we continue with the test
            // --> don't forget to initialize the state for the next test
            boolean result = mRequetedLogsDetectors.size() == 0;
            return result;
        }
    }

    @Override
    protected boolean shouldFailAtEndOfTimeout() {
        return true;
    }

}
