package com.galvan.talos.waiters;

import com.galvan.talos.interfaces.LogWaiter;
import com.galvan.talos.interfaces.TalosLogDetector;
import com.galvan.talos.model.TalosLogRecord;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

import io.appium.java_client.AppiumDriver;

/**
 * Created by galvan on 4/11/15.
 */
public abstract class TalosLogWaiter implements LogWaiter {

    private final int WAITER_TRESHOLD = 2;

    private AtomicBoolean mIsEndOfTimeout;
    private Timer mWaitTimer;
    private AppiumDriver mDriver;
    private ArrayList<TalosLogDetector> mWaitResultDetector;

    public TalosLogWaiter(AppiumDriver driver){
        mDriver = driver;
        mIsEndOfTimeout = new AtomicBoolean(false);
        mWaitResultDetector = new ArrayList<TalosLogDetector>();
    }


    @Override
    public final void handleLog(TalosLogRecord log) {
        onLogReceived(log);
    }

    @Override
    public ArrayList<TalosLogDetector> waitForLogs(int timeoutInSeconds) {
        startWaitTimeout(timeoutInSeconds);

        new WebDriverWait(mDriver, timeoutInSeconds + WAITER_TRESHOLD)
                //TODO: Change this log message to be more detailed with the missing logs
                .withMessage("Logs not found")
                .until(new ExpectedCondition<Boolean>() {
                    @Override
                    public Boolean apply(WebDriver d) {

                        if(shouldStopWaiting()){
                            stopWaitTimeout();
                            return true;
                        }
                        else if(mIsEndOfTimeout.get()){

                            //TODO - here we need to send a more detailed log about why this waiter fails the test
                            if(shouldFailAtEndOfTimeout()){
                                Assert.fail("The waiter didn't find all the required values");
                            }

                            return true;
                        }
                        else
                            return false;
                    };
                });

        return mWaitResultDetector;
    }


    private void startWaitTimeout(int timeoutInSeconds){
        mWaitTimer = new Timer();
        mWaitTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                mIsEndOfTimeout.set(true);
            }

        }, timeoutInSeconds * 1000);
    }


    private void stopWaitTimeout(){
        try{
            if(mWaitTimer == null){
                mWaitTimer.cancel();
                mWaitTimer = null;
            }
        }catch(Exception e){

        }
    }


    protected void addDetectorToResult(TalosLogDetector detectorToAdd){
        synchronized (mWaitResultDetector) {
            if(!mWaitResultDetector.contains(detectorToAdd)){
                mWaitResultDetector.add(detectorToAdd);
            }
        }
    }


    protected abstract void onLogReceived(TalosLogRecord log);

    protected abstract boolean shouldStopWaiting();

    protected abstract boolean shouldFailAtEndOfTimeout();
}
