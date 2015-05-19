package com.galvan.talos.client;

/**
 * Created by galvan on 4/26/15.
 */
//TODO: Check if the idevicesyslog is installed

class IOSLogsReader extends TalosLogsReader {

    public IOSLogsReader(TalosConfig configurations, LogHandler logHandler) {
        super(configurations, logHandler);
    }

    @Override
    protected String getCleanCommand(TalosConfig mConfigurations) {
        return null;
    }

    @Override
    protected String getConnectCommand(TalosConfig configurations) {
        return "idevicesyslog";
    }
}
