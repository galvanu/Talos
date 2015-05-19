package com.galvan.talos.detectors;

import com.galvan.talos.interfaces.TalosLogDetector;
import com.galvan.talos.model.TalosLogRecord;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by galvan on 4/11/15.
 */
public class LogWithStringsDetector implements TalosLogDetector{
    private ArrayList<String> mShouldContain;

    private LogWithStringsDetector(){}


    public LogWithStringsDetector(String... strings){
        mShouldContain = new ArrayList<String>();

        //Add the string to the SrrayList
        if(strings != null)
            for(String s: strings){
                if(s != null && s.length() != 0)
                    mShouldContain.add(s);
            }
    }

    @Override
    public boolean detect(TalosLogRecord logRecord) {
        ArrayList<String> copyOfShouldContain = new ArrayList<String>();
        for(String s : mShouldContain)
            copyOfShouldContain.add(s);

        Iterator<String> iter = copyOfShouldContain.iterator();

        while(iter.hasNext()){
            String shouldContain = iter.next();
            if(logRecord.getLogMessage().contains(shouldContain)){
                System.out.println("********************** Found log that contains - " + shouldContain);
                iter.remove();
            }
        }

        return copyOfShouldContain.size() == 0;
    }
}
