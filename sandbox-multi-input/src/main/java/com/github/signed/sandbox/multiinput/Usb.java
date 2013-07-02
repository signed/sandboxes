package com.github.signed.sandbox.multiinput;

import com.codeminders.hidapi.HIDDevice;
import com.codeminders.hidapi.HIDDeviceInfo;
import com.codeminders.hidapi.HIDManager;

import java.io.IOException;

public class Usb {

    public static void main(String[] args) throws IOException {

        HIDManager manager = null;
        try {
            ClassPathLibraryLoader.loadLinux64Bit();
            manager = HIDManager.getInstance();
            HIDDeviceInfo[] hidDeviceInfos = manager.listDevices();
            for (HIDDeviceInfo hidDeviceInfo : hidDeviceInfos) {

                HIDDevice device = manager.openByPath(hidDeviceInfo.getPath());

                //usage page =1 (generic desktop device) and usage = 6 (keyboard, and 7 is keypad), this is keyboard
                if (hidDeviceInfo.getUsage_page() == 1 && hidDeviceInfo.getUsage() == 6) {
                    System.out.println(hidDeviceInfo.getPath());
                    System.out.println(hidDeviceInfo.getUsage_page());
                    System.out.println(hidDeviceInfo.getManufacturer_string());
                } else {
                    int usage_page = hidDeviceInfo.getUsage_page();
                    System.out.println(hidDeviceInfo.getPath());
                    System.out.println("usage page: " + usage_page);
                    System.out.println("usage     : " + hidDeviceInfo.getUsage());
                }
            }
        } finally {
            if (null != manager) {
                manager.release();
            }
        }
    }
}
