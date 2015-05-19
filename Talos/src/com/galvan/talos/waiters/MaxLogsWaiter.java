package com.galvan.talos.waiters;

import com.galvan.talos.interfaces.TalosLogDetector;
import com.galvan.talos.model.TalosLogRecord;

import java.util.ArrayList;
import java.util.Iterator;

import io.appium.java_client.AppiumDriver;

/**
 * Created by galvan on 4/11/15.
 */
public class MaxLogsWaiter extends TalosLogWaiter{

    private ArrayList<TalosLogDetector> mDetectors;

    public MaxLogsWaiter(AppiumDriver driver, ArrayList<TalosLogDetector> detectors) {
        super(driver);
        mDetectors = detectors;
    }

    @Override
    protected void onLogReceived(TalosLogRecord log) {
        if(log == null)
            return;

        synchronized(mDetectors){
            Iterator<TalosLogDetector> iter = mDetectors.iterator();

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

    @Override
    protected boolean shouldStopWaiting() {
        boolean result = false;

        synchronized (mDetectors) {
            result = mDetectors.size() == 0;
        }

        return result;
    }


    @Override
    protected boolean shouldFailAtEndOfTimeout() {
        return false;
    }

}
