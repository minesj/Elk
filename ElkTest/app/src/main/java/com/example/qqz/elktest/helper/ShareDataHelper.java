package com.example.qqz.elktest.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.List;

public class ShareDataHelper {
    private final static String SHAREPREFENCENAME="COM.TRAVELTAO";

    public final static String SHARE_COMMONUSUALLIST="COMMON_CURRENCY_LIST";
    public final static String SHARE_LANGUAGE="SHARE_LANGUAGE_KEY";
    public final static String SHARE_LOCATION_ENABLE="SHARE_LOCATION_ENABLE_KEY";
    public final static String SHARE_CURRENCYSYMBOL_ENABLE="SHARE_CURRENCYSYMBOL_ENABLE_KEY";
    public final static String SHARE_CUSTOMER_GUIDE="SHARE_CUSTOMER_GUIDE_KEY";
    public final static String SHARE_SEARCHZH_COMMONUSUALLIST = "SEARCH_ZH_COMMON_CURRENCY_LIST";
    public final static String SHARE_SEARCHEN_COMMONUSUALLIST = "SEARCH_EN_COMMON_CURRENCY_LIST";

    public final static String KEY_WIDGET_BASE_CURR_UNIT = "KEY_WIDGET_BASE_CURR_UNIT";
    public final static String KEY_DEFAULT_VALUE ="KEY_DEFAULT_VALUE";
    public final static String KEY_DEFAULT_FIAT_VALUE ="KEY_DEFAULT_FIAT_VALUE";
    public final static String KEY_DEFAULT_CRYPTO_VALUE ="KEY_DEFAULT_CRYPTO_VALUE";

    public final static String SHARE_THEME_TYPE="SHARE_THEME_TYPE_KEY";

    public final static String SHARE_TAIWAN_FLAG="SHARE_TAIWAN_FLAG_KEY";

    public final static String SHARE_LAST_LOCATION="SHARE_LAST_LOCATION_KEY";
    public final static String SHARE_LAST_INPUT_CURRENCY="SHARE_LAST_INPUT_CURRENCY_KEY";

    public final static String FIRST_INSTALL_APP_CURRENCY="FIRST_INSTALL_APP_CURRENCY_SKK";

    public final static String UPDATE_RATE_TIME="UPDATE_RATE_TIME";
    public final static String BOC_RATES="BOC_RATES";
    public final static String BOT_RATES="BOT_RATES";

    public final static String APP_CONFIG="APP_CONFIG";
    public final static String APP_CACHE_POPId="APP_CACHE_POPId";

    public final static String RATE_RESOURCE_LIST="RATE_RESOURCE_LIST";
    public final static String RATE_RESOURCE="RATE_RESOURCE";
    public final static String RECENT_USED_RESOURCE="RECENT_USED_RESOURCE";

    public static final String GUIDE_STEP = "GUIDE_STEP";

    public static Boolean setStringValue(Context context, String key, String value){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHAREPREFENCENAME, 0);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static String getStringValueWithDefault(Context context,String key, String value) {
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHAREPREFENCENAME, 0);
        return sharedPreferences.getString(key, value);
    }


    public static String getStringValue(Context context,String key) {
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHAREPREFENCENAME, 0);
        return sharedPreferences.getString(key, "");
    }


    public static Boolean setIntValue(Context context,String key, int value){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHAREPREFENCENAME, 0);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    public static int getIntValueWithDefault(Context context,String key, int value) {
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHAREPREFENCENAME, 0);
        return sharedPreferences.getInt(key, value);
    }

    public static int getIntValue(Context context,String key) {
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHAREPREFENCENAME, 0);
        return sharedPreferences.getInt(key, 3);
    }

    public static Boolean setBooleanValue(Context context,String key, boolean value){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHAREPREFENCENAME, 0);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public static boolean getBooleanValue(Context context,String key) {
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHAREPREFENCENAME, 0);
        return sharedPreferences.getBoolean(key, true);
    }

    public static Boolean setLongValue(Context context,String key, Long value){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHAREPREFENCENAME, 0);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    public static Long getLongValue(Context context,String key) {
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHAREPREFENCENAME, 0);
        return sharedPreferences.getLong(key, 0);
    }

    public static <T> boolean saveCacheData(Context context,String key,List<T> nList) {

        Gson gson=new Gson();

        ShareDataHelper.setStringValue(context,key, gson.toJson(nList));

        return true;

    }

    public static boolean isFirtUpgrateApp(Context context){
        boolean isFirst = ShareDataHelper.getBooleanValue(context,FIRST_INSTALL_APP_CURRENCY);
        if (isFirst){
            ShareDataHelper.setBooleanValue(context,FIRST_INSTALL_APP_CURRENCY,false);
            return true;
        }
        return false;
    }

    public static int getGuideStep(Context context) {
        return ShareDataHelper.getIntValueWithDefault(context, GUIDE_STEP, 0);
    }

    public static void setGuideStep(Context context, int step) {
        ShareDataHelper.setIntValue(context, GUIDE_STEP, step);
    }
}

