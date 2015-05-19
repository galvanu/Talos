package com.galvan.talos.interfaces;

import com.galvan.talos.model.TalosLogRecord;

import java.util.ArrayList;

/**
 * Created by galvan on 4/11/15.
 */
public interface LogWaiter {

    public void handleLog (TalosLogRecord log);

    public ArrayList<TalosLogDetector> waitForLogs(int timeoutInSeconds);

}
