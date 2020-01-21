package com.dvpie.utilities.networking;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;

public class UtilityNetworking {

    public static boolean isNetworkAvailable(@Nullable ConnectivityManager connManager) {
        assert connManager != null;
        NetworkInfo activeNetworkInfo = connManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
