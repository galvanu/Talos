package com.galvan.talos.interfaces;

import com.galvan.talos.model.TalosLogRecord;

/**
 * Created by galvan on 4/11/15.
 */
public interface TalosLogDetector {

    public boolean detect(TalosLogRecord logRecord);

}
