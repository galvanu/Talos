package com.galvan.talos.waiters;

import com.galvan.talos.interfaces.TalosLogDetector;
import com.galvan.talos.model.TalosLogRecord;

import java.util.ArrayList;
import java.util.Iterator;

import io.appium.java_client.AppiumDriver;

/**
 * Created by galvan on 4/11/15.
 */
public class OneOfLogsWaiter extends TalosLogWaiter {

    private ArrayList<TalosLogDetector> mDetectors;
    private boolean mShouldStopWait;

    public OneOfLogsWaiter(AppiumDriver driver, ArrayList<TalosLogDetector> dectors) {
        super(driver);
        mDetectors = dectors;
        mShouldStopWait = false;
    }

    @Override
    protected void onLogReceived(TalosLogRecord log) {
        if(log == null)
            return;

        synchronized (mDetectors) {
            Iterator<TalosLogDetector> iter = mDetectors.iterator();

            boolean found = false;

            while(iter.hasNext() && !found){
                TalosLogDetector currentDetector = iter.next();
                if(currentDetector.detect(log)){
                    iter.remove();
                    addDetectorToResult(currentDetector);
                    mShouldStopWait = true;
                    found = true;
                }
            }
        }
    }

    @Override
    protected boolean shouldStopWaiting() {
        return mShouldStopWait;
    }

    @Override
    protected boolean shouldFailAtEndOfTimeout() {
        return true;
    }
}
