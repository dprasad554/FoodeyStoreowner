package com.geekhive.foodeyrestaurant.grocery.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

public class PrefsGrocery {
    static final String PREF_SPLASH_SCREEN_GROCERY = "splash_screen";
	static final String PREF_USER_FIRST_NAME_GROCERY = "user_first_name";
    static final String PREF_USER_LAST_NAME_GROCERY = "user_last_name";
    static final String PREF_USER_NAME_GROCERY = "user_name";
    static final String PREF_USER_PWD_GROCERY = "user_pwd";
	static final String PREF_USER_EMAIL_GROCERY = "user_email";
    static final String PREF_USER_MOBILE_GROCERY = "user_mobile";
    static final String PREF_USER_PIC_URL_GROCERY = "userPic_url";
    static final String PREF_USER_ID_GROCERY = "user_id";
    static final String PREF_ORDER_ID_GROCERY = "order_id";
    static final String PREF_ADDRESS_ID_GROCERY = "address_id";
    static final String PREF_ADDRESS_GROCERY = "address";
    static final String PREF_TOTAL_GROCERY = "total";
    static final String PREF_ACC_EXISTS = "acc_exists";
    static final String PREF_GCM_TOKEN_GROCERY = "gcm_token";
    static final String PREF_OPEN_CLOSE_GROCERY = "open_close";
    static final String PREF_STORE_LOGIN = "storelogin";

    /* Android Info Generic Usecase*/

	static SharedPreferences getPrefsGrocery(Context ctx) {
		return PreferenceManager.getDefaultSharedPreferences(ctx);
	}

	public static void setUserFirstNameGrocery(Context ctx, String s) {
		getPrefsGrocery(ctx).edit().putString(PREF_USER_FIRST_NAME_GROCERY, s).commit();
	}

    public static void setUserLastNameGrocery(Context ctx, String s) {
        getPrefsGrocery(ctx).edit().putString(PREF_USER_LAST_NAME_GROCERY, s).commit();
    }

    public static void setUserNameGrocery(Context ctx, String s) {
        getPrefsGrocery(ctx).edit().putString(PREF_USER_NAME_GROCERY, s).commit();
    }


	public static String getUserFirstNameGrocery(Context ctx) {
		return getPrefsGrocery(ctx).getString(PREF_USER_FIRST_NAME_GROCERY, "");
	}

    public static String getUserLastNameGrocery(Context ctx) {
        return getPrefsGrocery(ctx).getString(PREF_USER_LAST_NAME_GROCERY, "");
    }

    public static String getUserNameGrocery(Context ctx) {
        return getPrefsGrocery(ctx).getString(PREF_USER_NAME_GROCERY, "");
    }

    public static void setUserEmailGrocery(Context ctx, String s) {
        getPrefsGrocery(ctx).edit().putString(PREF_USER_EMAIL_GROCERY, s).commit();
    }


    public static String getUserEmailGrocery(Context ctx) {
        return getPrefsGrocery(ctx).getString(PREF_USER_EMAIL_GROCERY, "");
    }

    public static void setOpenCloseGrocery(Context ctx, String s) {
        getPrefsGrocery(ctx).edit().putString(PREF_OPEN_CLOSE_GROCERY, s).commit();
    }


    public static String getOpenCloseGrocery(Context ctx) {
        return getPrefsGrocery(ctx).getString(PREF_OPEN_CLOSE_GROCERY, "");
    }

    public static void setMobileNumberGrocery(Context ctx, String s) {
        getPrefsGrocery(ctx).edit().putString(PREF_USER_MOBILE_GROCERY, s).commit();
    }


    public static String getMobileNumberGrocery(Context ctx) {
        return getPrefsGrocery(ctx).getString(PREF_USER_MOBILE_GROCERY, "");
    }


    public static void setUserProfileUrlGrocery(Context ctx, String s) {
        getPrefsGrocery(ctx).edit().putString(PREF_USER_PIC_URL_GROCERY, s).commit();
    }

    public static String getUserProfileUrlGrocery(Context ctx) {
        return getPrefsGrocery(ctx).getString(PREF_USER_PIC_URL_GROCERY, "");
    }

    public static void setUserPwdGrocery(Context ctx, String s) {
        getPrefsGrocery(ctx).edit().putString(PREF_USER_PWD_GROCERY, s).commit();
    }

    public static String getUserPwdGrocery(Context ctx) {
        return getPrefsGrocery(ctx).getString(PREF_USER_PWD_GROCERY, "");
    }
/* Global server user id */
    public static void setUserIdGrocery(Context ctx, String s) {
        getPrefsGrocery(ctx).edit().putString(PREF_USER_ID_GROCERY, s).commit();
    }

    public static String getUserIdGrocery(Context ctx) {
        return getPrefsGrocery(ctx).getString(PREF_USER_ID_GROCERY, "");
    }

    public static boolean hasUserIdGenerated(Context ctx){
        return !TextUtils.isEmpty(getUserIdGrocery(ctx));
    }
    /* Android Info Generic Use case */


    public static void setGCMTokenGrocery(Context ctx, String s) {
        getPrefsGrocery(ctx).edit().putString(PREF_GCM_TOKEN_GROCERY, s).commit();
    }

    public static String getGCMTokenGrocery(Context ctx) {
        return getPrefsGrocery(ctx).getString(PREF_GCM_TOKEN_GROCERY, null);
    }

    public static void setAccExistsGrocery(Context ctx, String username, boolean y) {
        if(username == null) return;
        getPrefsGrocery(ctx).edit().putBoolean(username, y).commit();
    }

    public static boolean getAccExistsGrocery(Context ctx, String username) {
        if(username == null) return false;
        return getPrefsGrocery(ctx).getBoolean(username, false);
    }

    public static void setSplashScreenPrefGrocery(Context ctx, String s) {
        getPrefsGrocery(ctx).edit().putString(PREF_SPLASH_SCREEN_GROCERY, s).commit();
    }


    public static String getSplashScreenPref(Context ctx) {
        return getPrefsGrocery(ctx).getString(PREF_SPLASH_SCREEN_GROCERY, "");
    }

    /*public static boolean getSplashScreenPref(Context ctx) {
        return getPrefsGrocery(ctx).getString(PREF_SPLASH_SCREEN);
    }

    public static void (Context ctx, String s) {
        getPrefsGrocery(ctx).edit().putString(PREF_SPLASH_SCREEN, s).commit();
    }*/

    public static boolean getUserNotificationPreferenceGrocery(final Context context) {
        return getPrefsGrocery(context).getBoolean("notification_alerts", true);
    }

    public static void saveDataWithKeyAndValueGrocery(Context context, String key, String value){
        SharedPreferences.Editor editor = context.getSharedPreferences("aina",context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getDataFromKeyGrocery(Context context, String key){
        SharedPreferences prefs = context.getSharedPreferences("aina",context.MODE_PRIVATE);
        String restoredText = prefs.getString(key, null);
        return restoredText;

    }

    public static void setAddressIdGrocery(Context ctx, String s) {
        getPrefsGrocery(ctx).edit().putString(PREF_ADDRESS_ID_GROCERY, s).commit();
    }


    public static String getAddressIdGrocery(Context ctx) {
        return getPrefsGrocery(ctx).getString(PREF_ADDRESS_ID_GROCERY, "");
    }

    public static void setAddressGrocery(Context ctx, String s) {
        getPrefsGrocery(ctx).edit().putString(PREF_ADDRESS_GROCERY, s).commit();
    }


    public static String getAddressGrocery(Context ctx) {
        return getPrefsGrocery(ctx).getString(PREF_ADDRESS_GROCERY, "");
    }

    public static void setTotalGrocery(Context ctx, String s) {
        getPrefsGrocery(ctx).edit().putString(PREF_TOTAL_GROCERY, s).commit();
    }


    public static String getTotalGrocery(Context ctx) {
        return getPrefsGrocery(ctx).getString(PREF_TOTAL_GROCERY, "");
    }

    public static void setOrderIdGrocery(Context ctx, String s) {
        getPrefsGrocery(ctx).edit().putString(PREF_ORDER_ID_GROCERY, s).commit();
    }


    public static String getOrderIdGrocery(Context ctx) {
        return getPrefsGrocery(ctx).getString(PREF_ORDER_ID_GROCERY, "");
    }

    public static void setStoreLogin(Context ctx, String s) {
        getPrefsGrocery(ctx).edit().putString(PREF_STORE_LOGIN, s).commit();
    }


    public static String getStoreLogin(Context ctx) {
        return getPrefsGrocery(ctx).getString(PREF_STORE_LOGIN, "");
    }

}