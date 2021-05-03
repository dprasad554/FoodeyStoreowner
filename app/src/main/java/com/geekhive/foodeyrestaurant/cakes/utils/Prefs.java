package com.geekhive.foodeyrestaurant.cakes.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

public class Prefs {
    static final String PREF_SPLASH_SCREEN_CAKES = "splash_screen";
	static final String PREF_USER_FIRST_NAME_CAKES = "user_first_name";
    static final String PREF_USER_LAST_NAME_CAKES = "user_last_name";
    static final String PREF_USER_NAME_CAKES = "user_name";
    static final String PREF_USER_PWD_CAKES = "user_pwd";
	static final String PREF_USER_EMAIL_CAKES = "user_email";
    static final String PREF_USER_MOBILE_CAKES = "user_mobile";
    static final String PREF_USER_PIC_URL_CAKES = "userPic_url";
    static final String PREF_USER_ID_CAKES = "user_id";
    static final String PREF_ORDER_ID_CAKES = "order_id";
    static final String PREF_ADDRESS_ID_CAKES = "address_id";
    static final String PREF_ADDRESS_CAKES = "address";
    static final String PREF_TOTAL_CAKES = "total";
    static final String PREF_ACC_EXISTS = "acc_exists";
    static final String PREF_GCM_TOKEN_CAKES = "gcm_token";
    static final String PREF_OPEN_CLOSE_CAKES = "open_close";
    static final String PREF_CAKE_LOGIN = "cakelogin";

    /* Android Info Generic Usecase*/

	static SharedPreferences getPrefs(Context ctx) {
		return PreferenceManager.getDefaultSharedPreferences(ctx);
	}

	public static void setUserFirstName(Context ctx, String s) {
		getPrefs(ctx).edit().putString(PREF_USER_FIRST_NAME_CAKES, s).commit();
	}

    public static void setUserLastName(Context ctx, String s) {
        getPrefs(ctx).edit().putString(PREF_USER_LAST_NAME_CAKES, s).commit();
    }

    public static void setUserName(Context ctx, String s) {
        getPrefs(ctx).edit().putString(PREF_USER_NAME_CAKES, s).commit();
    }


	public static String getUserFirstName(Context ctx) {
		return getPrefs(ctx).getString(PREF_USER_FIRST_NAME_CAKES, "");
	}

    public static String getUserLastName(Context ctx) {
        return getPrefs(ctx).getString(PREF_USER_LAST_NAME_CAKES, "");
    }

    public static String getUserName(Context ctx) {
        return getPrefs(ctx).getString(PREF_USER_NAME_CAKES, "");
    }

    public static void setUserEmail(Context ctx, String s) {
        getPrefs(ctx).edit().putString(PREF_USER_EMAIL_CAKES, s).commit();
    }


    public static String getUserEmail(Context ctx) {
        return getPrefs(ctx).getString(PREF_USER_EMAIL_CAKES, "");
    }

    public static void setOpenClose(Context ctx, String s) {
        getPrefs(ctx).edit().putString(PREF_OPEN_CLOSE_CAKES, s).commit();
    }


    public static String getOpenClose(Context ctx) {
        return getPrefs(ctx).getString(PREF_OPEN_CLOSE_CAKES, "");
    }

    public static void setMobileNumber(Context ctx, String s) {
        getPrefs(ctx).edit().putString(PREF_USER_MOBILE_CAKES, s).commit();
    }


    public static String getMobileNumber(Context ctx) {
        return getPrefs(ctx).getString(PREF_USER_MOBILE_CAKES, "");
    }


    public static void setUserProfileUrl(Context ctx, String s) {
        getPrefs(ctx).edit().putString(PREF_USER_PIC_URL_CAKES, s).commit();
    }

    public static String getUserProfileUrl(Context ctx) {
        return getPrefs(ctx).getString(PREF_USER_PIC_URL_CAKES, "");
    }

    public static void setUserPwd(Context ctx, String s) {
        getPrefs(ctx).edit().putString(PREF_USER_PWD_CAKES, s).commit();
    }

    public static String getUserPwd(Context ctx) {
        return getPrefs(ctx).getString(PREF_USER_PWD_CAKES, "");
    }
/* Global server user id */
    public static void setUserId(Context ctx, String s) {
        getPrefs(ctx).edit().putString(PREF_USER_ID_CAKES, s).commit();
    }

    public static String getUserId(Context ctx) {
        return getPrefs(ctx).getString(PREF_USER_ID_CAKES, "");
    }

    public static boolean hasUserIdGenerated(Context ctx){
        return !TextUtils.isEmpty(getUserId(ctx));
    }
    /* Android Info Generic Use case */


    public static void setGCMToken(Context ctx, String s) {
        getPrefs(ctx).edit().putString(PREF_GCM_TOKEN_CAKES, s).commit();
    }

    public static String getGCMToken(Context ctx) {
        return getPrefs(ctx).getString(PREF_GCM_TOKEN_CAKES, null);
    }

    public static void setAccExists(Context ctx, String username, boolean y) {
        if(username == null) return;
        getPrefs(ctx).edit().putBoolean(username, y).commit();
    }

    public static boolean getAccExists(Context ctx, String username) {
        if(username == null) return false;
        return getPrefs(ctx).getBoolean(username, false);
    }

    public static void setSplashScreenPref(Context ctx, String s) {
        getPrefs(ctx).edit().putString(PREF_SPLASH_SCREEN_CAKES, s).commit();
    }


    public static String getSplashScreenPref(Context ctx) {
        return getPrefs(ctx).getString(PREF_SPLASH_SCREEN_CAKES, "");
    }

    /*public static boolean getSplashScreenPref(Context ctx) {
        return getPrefs(ctx).getString(PREF_SPLASH_SCREEN);
    }

    public static void (Context ctx, String s) {
        getPrefs(ctx).edit().putString(PREF_SPLASH_SCREEN, s).commit();
    }*/

    public static boolean getUserNotificationPreference(final Context context) {
        return getPrefs(context).getBoolean("notification_alerts", true);
    }

    public static void saveDataWithKeyAndValue(Context context, String key, String value){
        SharedPreferences.Editor editor = context.getSharedPreferences("aina",context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getDataFromKey(Context context, String key){
        SharedPreferences prefs = context.getSharedPreferences("aina",context.MODE_PRIVATE);
        String restoredText = prefs.getString(key, null);
        return restoredText;

    }

    public static void setAddressId(Context ctx, String s) {
        getPrefs(ctx).edit().putString(PREF_ADDRESS_ID_CAKES, s).commit();
    }


    public static String getAddressId(Context ctx) {
        return getPrefs(ctx).getString(PREF_ADDRESS_ID_CAKES, "");
    }

    public static void setAddress(Context ctx, String s) {
        getPrefs(ctx).edit().putString(PREF_ADDRESS_CAKES, s).commit();
    }


    public static String getAddress(Context ctx) {
        return getPrefs(ctx).getString(PREF_ADDRESS_CAKES, "");
    }

    public static void setTotal(Context ctx, String s) {
        getPrefs(ctx).edit().putString(PREF_TOTAL_CAKES, s).commit();
    }


    public static String getTotal(Context ctx) {
        return getPrefs(ctx).getString(PREF_TOTAL_CAKES, "");
    }

    public static void setOrderId(Context ctx, String s) {
        getPrefs(ctx).edit().putString(PREF_ORDER_ID_CAKES, s).commit();
    }


    public static String getOrderId(Context ctx) {
        return getPrefs(ctx).getString(PREF_ORDER_ID_CAKES, "");
    }

    public static void setCakeLogin(Context ctx, String s) {
        getPrefs(ctx).edit().putString(PREF_CAKE_LOGIN, s).commit();
    }


    public static String getCakeLogin(Context ctx) {
        return getPrefs(ctx).getString(PREF_CAKE_LOGIN, "");
    }
}