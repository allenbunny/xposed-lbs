package com.github.xposed.lbs;

import android.app.AndroidAppHelper;
import android.app.PendingIntent;
import android.content.Context;
import android.location.LocationListener;
import android.os.Looper;
import android.widget.Toast;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class XposedLBSMonitor implements IXposedHookLoadPackage {

    private static String getStackTraceStr(RuntimeException exception) {
        StringBuffer buf = new StringBuffer(exception.toString());
        buf.append(":\n");

        for (StackTraceElement element : exception.getStackTrace()) {
            buf.append(element.toString());
            buf.append("\n");
        }

        return buf.toString();
    }

    @Override
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        // print pkg name
        //XposedBridge.log("xposed hooked pkg:" + lpparam.packageName);

        XposedHelpers.findAndHookMethod(
                "android.location.LocationManager",
                lpparam.classLoader,
                "getLastKnownLocation",
                String.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param)
                            throws Throwable {
                        String title = "Xposed hooked " + lpparam.packageName + " call getLastKnownLocation";
                        String trace = getStackTraceStr(new RuntimeException(title));
                        // print to log & show toast
                        XposedBridge.log(trace);
                        Toast.makeText((Context) AndroidAppHelper.currentApplication(), title, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param)
                            throws Throwable {
                    }
                }
        );

        Class ocationReqClz = lpparam.classLoader.loadClass("android.location.LocationRequest");
        XposedHelpers.findAndHookMethod(
                "android.location.LocationManager",
                lpparam.classLoader,
                "requestLocationUpdates",
                ocationReqClz,
                LocationListener.class,
                Looper.class,
                PendingIntent.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param)
                            throws Throwable {
                        String title = "Xposed hooked " + lpparam.packageName + " call requestLocationUpdates";
                        String trace = getStackTraceStr(new RuntimeException(title));
                        // print to log & show toast
                        XposedBridge.log(trace);
                        Toast.makeText((Context) AndroidAppHelper.currentApplication(), title, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param)
                            throws Throwable {
                    }
                }
        );

        XposedHelpers.findAndHookMethod(
                "android.telephony.TelephonyManager",
                lpparam.classLoader,
                "getCellLocation",
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param)
                            throws Throwable {
                        String title = "Xposed hooked " + lpparam.packageName + " call getCellLocation";
                        String trace = getStackTraceStr(new RuntimeException(title));
                        // print to log & show toast
                        XposedBridge.log(trace);
                        Toast.makeText((Context) AndroidAppHelper.currentApplication(), title, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param)
                            throws Throwable {
                    }
                }
        );
    }
}