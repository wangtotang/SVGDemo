package com.tang.svgdemo;

/**
 * @author tanghongtu
 * @date 2021/4/21
 */
public class EventData {

    private String StationName;
    private String DevName;
    private int DeviceStatus;

    public String getStationName() {
        return StationName;
    }

    public void setStationName(String stationName) {
        StationName = stationName;
    }

    public String getDevName() {
        return DevName;
    }

    public void setDevName(String devName) {
        DevName = devName;
    }

    public int getDeviceStatus() {
        return DeviceStatus;
    }

    public void setDeviceStatus(int deviceStatus) {
        DeviceStatus = deviceStatus;
    }
}
