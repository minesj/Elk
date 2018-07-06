package com.example.qqz.elktest;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;

import java.util.Locale;

public class LanguageHelper {
    public static final String TAG = "LanguageHelper";

    public static String getSystemLanguage(Context context){
        String localLanguage = "en";
        if (context.getResources().getConfiguration().locale != null) {
            localLanguage = context.getResources().getConfiguration().locale.getLanguage();

            if(localLanguage.equals("zh")){
                String countryCode=context.getResources().getConfiguration().locale.getCountry();
                if(countryCode==null || countryCode.equals("TW")){
                    countryCode="HK";
                }
                localLanguage+="-"+ countryCode;
            }

        }

        return localLanguage;
    }

    public static String getCurrentAppLanguage(Context context){
        String localLanguage = "en";
        String language_Pre = ShareDataHelper.getStringValue(context,
                ShareDataHelper.SHARE_LANGUAGE);

        if (language_Pre.length() > 0){
            localLanguage=language_Pre;
        }

        return localLanguage;
    }

    public static boolean isZHLanguage(Context context){
        String localLanguage = getSystemLanguage(context);
        if (localLanguage.contains("zh")){
            return true;
        }

        return false;
    }

    public static boolean isJALanguage(Context context){
        String localLanguage = getSystemLanguage(context);
        if (localLanguage.contains("ja")){
            return true;
        }

        return false;
    }

    public static String getSystemCountryCode(Context context){
        String countryCode="";

        if (Locale.getDefault()!=null) {
            countryCode = Locale.getDefault().getCountry();
        }
        if (countryCode.length()==0){
            countryCode="CN";
        }

        return countryCode;
    }

    public static void initLanguage(Context context) {

        String language_Pre = ShareDataHelper.getStringValue(context,
                ShareDataHelper.SHARE_LANGUAGE);
        String localLanguage = getSystemLanguage(context);

        String language="";

        if (language_Pre.length() > 0) {
            language = language_Pre;
        } else {
            language = localLanguage;
        }

        Log.d(TAG, String.format("langInPref: %s, lang: %s", language_Pre, language));
        setLanguage(language, context);
    }

    public static void setLanguage(String lang, Context context) {
        Configuration config = new Configuration();
        Locale locale;
        if (lang.equals("zh-HK")) {
            locale = Locale.TAIWAN;
        } else if (lang.equals("zh-CN")) {
            locale = Locale.CHINA;
        } else {
            locale = new Locale(lang);
        }
        Locale.setDefault(locale);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale);
        } else {
            config.locale = locale;
        }
        context.getResources().updateConfiguration(
                config, context.getResources().getDisplayMetrics());
    }

    public static Locale localFromString(String language) {
        if (language.equals("zh-HK")) {
            return Locale.TAIWAN;
        } else if (language.equals("zh-CN")) {
            return Locale.CHINA;
        } else {
            return new Locale(language);
        }
    }

    public static Locale getSystemLocale(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        return locale != null ? locale : localFromString("en");
    }

    // https://stackoverflow.com/questions/40398528/android-webview-language-changes-abruptly-on-android-n
    public static void fixWebViewLanguage(Context context) {
        Locale locale = getSystemLocale(context);
        Locale.setDefault(locale);
        final Resources resources = context.getResources();
        final Configuration config = resources.getConfiguration();
        config.locale = locale;
        context.getResources().updateConfiguration(config, resources.getDisplayMetrics());
    }
}