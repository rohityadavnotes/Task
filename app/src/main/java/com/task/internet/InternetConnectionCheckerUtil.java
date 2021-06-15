package com.task.internet;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.widget.Toast;
import androidx.annotation.NonNull;

import com.task.utils.LogcatUtil;

public class InternetConnectionCheckerUtil {

    /**
     * Method to open wireless settings
     *
     * @param context - Context
     */
    public static void openWirelessSettings(Context context) {
        if (Build.VERSION.SDK_INT > 10) {
            context.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } else {
            context.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    /***********************************************************************************************
     ************************************** OLD WAY USE OF CONNECTION CHECK ************************
     **********************************************************************************************/

    /**
     * Method to checking for all possible internet providers
     *
     * @param context - Context
     * @param connectionType
     */
    public static boolean isConnectedBefore22(Context context, int connectionType) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            if (connectivityManager != null)
            {
                Network[] arrayNetwork = connectivityManager.getAllNetworks();
                /*
                 * Android 10
                 * public class NetworkInfo :
                 *                              Added in API level 21
                 *                              This class was deprecated in API level 29.
                 */
                NetworkInfo networkInfo;

                if (arrayNetwork != null)
                    for (int i = 0; i < arrayNetwork.length; i++)
                    {
                        networkInfo = connectivityManager.getNetworkInfo(arrayNetwork[i]);
                        if(networkInfo != null)
                        {
                            if (networkInfo.getState() == NetworkInfo.State.CONNECTED)
                            {
                                LogcatUtil.debuggingMessage("Network", " "+i+" NETWORK NAME Using getAllNetworks() : " + networkInfo.getTypeName());

                                if (networkInfo.getType() == connectionType)
                                {
                                    return true;
                                }
                            }
                        }
                    }
            }
        }
        else
        {
            if (connectivityManager != null)
            {
                /*
                 * Android 6.0 Marshmallow
                 *
                 * public NetworkInfo[] getAllNetworkInfo() :
                 *                                           Added in API level 1
                 *                                           Deprecated in API level 23
                 * This method does not support multiple connected networks of the same type.
                 *
                 * Use
                 *     public Network[] getAllNetworks() :
                 *                                        Added in API level 21
                 *     and
                 *
                 *     public NetworkInfo getNetworkInfo (Network network) :
                 *                                                          Added in API level 21
                 */
                NetworkInfo[] arrayNetworkInfo = connectivityManager.getAllNetworkInfo();
                if (arrayNetworkInfo != null)
                    for (int i = 0; i < arrayNetworkInfo.length; i++)
                    {
                        if (arrayNetworkInfo[i].getState() == NetworkInfo.State.CONNECTED)
                        {
                            LogcatUtil.debuggingMessage("Network", " "+i+" NETWORK NAME Using getAllNetworkInfo() : " + arrayNetworkInfo[i].getTypeName());
                            if (arrayNetworkInfo[i].getType() == connectionType)
                            {
                                return true;
                            }
                        }
                    }
            }
        }
        return false;
    }

    public static boolean isMobileConnectBefore22(@NonNull Context context) {
        return isConnectedBefore22(context, ConnectivityManager.TYPE_MOBILE);
    }

    public static boolean isWifiConnectBefore22(@NonNull Context context) {
        return isConnectedBefore22(context, ConnectivityManager.TYPE_WIFI);
    }

    /**
     * Method to checking any network connected or not
     *
     * now call the method , for safe use try catch
     *
     * try {
     *      if (isInternetOn()) { connected actions  }
     *      else {  not connected actions  }
     *      }
     *      catch (Exception e)
     *      {
     *          Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
     *       }
     *
     * @param context - Context
     */
    public final boolean isInternetOn(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        assert connectivityManager != null;
        if (connectivityManager.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTING ) {
            Toast.makeText(context, connectivityManager.getActiveNetworkInfo().getTypeName(), Toast.LENGTH_LONG).show();
            return true;
        }
        else if (connectivityManager.getActiveNetworkInfo().getState() == NetworkInfo.State.DISCONNECTED ||
                connectivityManager.getActiveNetworkInfo().getState() == NetworkInfo.State.DISCONNECTED  ) {
            Toast.makeText(context, " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }

    /***********************************************************************************************
     ************************************** NEW WAY USE OF CONNECTION CHECK ************************
     **********************************************************************************************/

    /**
     * Method to checking is mobile or wifi is connected or not
     *
     * @param context - Context
     * @return true or false
     */
    public static boolean isConnectedAfter23(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (connectivityManager != null)
            {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                if (capabilities != null)
                {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
                    {
                        return true;
                    }
                    else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
                    {
                        return true;
                    }
                }
            }
        }
        else
        {
            if (connectivityManager != null)
            {
                NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                if (activeNetwork != null)
                {
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                    {
                        return true;
                    }
                    else if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Method to checking network type
     *
     * @param context - Context
     * @return Returns connection type. 0: none; 1: mobile data; 2: wifi
     */
    public static int getConnectedType(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        {
            if (connectivityManager != null)
            {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                if (capabilities != null)
                {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
                    {
                        return 1;
                    }
                    else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
                    {
                        return 2;
                    }
                }
            }
        }
        else
        {
            if (connectivityManager != null)
            {
                NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                if (activeNetwork != null)
                {
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                    {
                        return 1;
                    }
                    else if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                    {
                        return 2;
                    }
                }
            }
        }
        return 0;
    }

    public static boolean isMobileConnectedAfter23(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (connectivityManager != null)
            {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                if (capabilities != null)
                {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
                    {
                        return true;
                    }
                }
            }
        }
        else
        {
            if (connectivityManager != null)
            {
                NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                if (activeNetwork != null)
                {
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isWifiConnectedAfter23(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (connectivityManager != null)
            {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        return true;
                    }
                }
            }
        }
        else
        {
            if (connectivityManager != null)
            {
                NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                if (activeNetwork != null)
                {
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /***********************************************************************************************
     ************************************** GET WHICH NETWORK USED *********************************
     **********************************************************************************************/

    public enum NetworkType {
        NETWORK_WIFI,
        NETWORK_4G,
        NETWORK_3G,
        NETWORK_2G,
        NETWORK_UNKNOWN,
        NETWORK_NO
    }

    private static final int NETWORK_TYPE_GSM      = 16;
    private static final int NETWORK_TYPE_TD_SCDMA = 17;
    private static final int NETWORK_TYPE_IWLAN    = 18;

    public static NetworkType getNetworkType(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo info  = connectivityManager.getActiveNetworkInfo();

        NetworkType netType = NetworkType.NETWORK_NO;

        if (info != null && info.isAvailable())
        {

            if (info.getType() == ConnectivityManager.TYPE_WIFI)
            {
                netType = NetworkType.NETWORK_WIFI;
            }
            else if (info.getType() == ConnectivityManager.TYPE_MOBILE)
            {
                switch (info.getSubtype())
                {
                    case NETWORK_TYPE_GSM:
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        netType = NetworkType.NETWORK_2G;
                        break;

                    case NETWORK_TYPE_TD_SCDMA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        netType = NetworkType.NETWORK_3G;
                        break;

                    case NETWORK_TYPE_IWLAN:
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        netType = NetworkType.NETWORK_4G;
                        break;

                    default:
                        String subtypeName = info.getSubtypeName();
                        if (subtypeName.equalsIgnoreCase("TD-SCDMA")
                                || subtypeName.equalsIgnoreCase("WCDMA")
                                || subtypeName.equalsIgnoreCase("CDMA2000")) {
                            netType = NetworkType.NETWORK_3G;
                        }
                        else
                        {
                            netType = NetworkType.NETWORK_UNKNOWN;
                        }
                        break;
                }
            }
            else
            {
                netType = NetworkType.NETWORK_UNKNOWN;
            }
        }
        return netType;
    }

    private InternetConnectionCheckerUtil() {
        throw new UnsupportedOperationException(
                "Should not create instance of Util class. Please use as static..");
    }
}
