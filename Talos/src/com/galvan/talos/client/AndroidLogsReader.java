package com.galvan.talos.client;

/**
 * Created by galvan on 4/24/15.
 */
class AndroidLogsReader extends TalosLogsReader {



    private final String ADB_LOGCAT_CMD = "./adb logcat ";


    public AndroidLogsReader(TalosConfig configurations, LogHandler logHandler) {
        super(configurations, logHandler);
    }


    @Override
    protected String getCleanCommand(TalosConfig mConfigurations) {
        StringBuilder result = new StringBuilder();

        result.append(mConfigurations.getAdbPath()).append(ADB_LOGCAT_CMD).append("-c");

        return result.toString();
    }

    @Override
    protected String getConnectCommand(TalosConfig configurations) {
        StringBuilder result = new StringBuilder();

        //TODO - Check how to perform the -T option
        result.append(configurations.getAdbPath()).append(ADB_LOGCAT_CMD)./*append("-T 1 ").*/append("-s ").append(configurations.getTagsToListen());

        return result.toString();
    }
}
