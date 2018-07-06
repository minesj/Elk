package com.example.qqz.elktest;

import android.content.Context;

import android.text.TextUtils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class Currency implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String TYPE_FIAT_CURRENCY = "currency";
    public static final String TYPE_CRYPTO_CURRENCY = "crypto_currency";

    public static final String SECTION_FAV = "★";
    public static final String SECTION_LOCAL = "local";

    private static final int FIAT_CUR_DECIMAL_COUNT = 4;
    private static final int CRYPTO_CUR_DECIMAL_COUNT = 8;

    private String symbol;
    private String sign;
    private String type;
    private HashMap<String, String> name = new HashMap<>();
    private HashMap<String, String> country = new HashMap<>();
    private HashMap<String, String> section = new HashMap<>();
    private String customSection;
    private String countryCode;
    private String countryCodes;
    private String moreInfo;
    private String searchData;

    private double rate;
    private String result;
    private String inputResult;

    public static Currency copyFrom(Currency src) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        Currency currency = null;
        try {
            out = new ObjectOutputStream(os);
            out.writeObject(src);
            out.flush();
            in = new ObjectInputStream(new ByteArrayInputStream(os.toByteArray()));
            currency = (Currency)in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                    out = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                    in = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return currency;
    }

    public String getSymbol() {
        return symbol;
    }

  /*public String getSign() {
        boolean isShowSign = ShareDataHelper.getBooleanValue(BaseApplication.getApplication(), ShareDataHelper.SHARE_CURRENCYSYMBOL_ENABLE);
        return isShowSign ? sign : "";
    }*/

    public String getType() {
        return type;
    }

    public boolean isFiatCurrency() {
        return (!TextUtils.isEmpty(type) && type.equals(TYPE_FIAT_CURRENCY));
    }

    public boolean isCryptoCurrency() {
        return (!TextUtils.isEmpty(type) && type.equals(TYPE_CRYPTO_CURRENCY));
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName(String language) {
        String name = this.name.get(language);
        return !TextUtils.isEmpty(name) ? name : this.name.get("en");
    }

    /*public String getName(Context context) {
        String language = LanguageHelper.getSystemLanguage(context);
        return getName(language);
    }*/

    public String getSection(Context context) {
        if (!TextUtils.isEmpty(customSection)) {
            return customSection;
        }
        String section;
        String language = LanguageHelper.getSystemLanguage(context);
        if (language.contains("zh")) {
            section = name.get("pinyin");
        } else if (language.equals("in")) {
            // Indonesia
            section = name.get("id");
        } else if (language.equals("ja") || language.equals("ko")) {
            section = this.section.get(language);
        } else {
            section = name.get(language);
        }
        return section;
    }

    public void setSection(String section) {
        customSection = section;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public boolean containsCountryCode(String code) {
        if (code.equals(countryCode)) {
            return true;
        } else if (!TextUtils.isEmpty(countryCodes)) {
            return countryCodes.contains(code);
        }
        return false;
    }

    public String getSearchData() {
        return searchData;
    }

    public void buildSearchData() {
        ArrayList<String> stringList = new ArrayList<>();
        stringList.add(symbol);
        stringList.add(sign);
        stringList.addAll(name.values());
        stringList.addAll(country.values());
        stringList.addAll(section.values());
        stringList.add(countryCode);
        stringList.add(countryCodes);
        stringList.add(moreInfo);
        searchData = TextUtils.join(" ", stringList).toLowerCase();
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getRate() {
        return (rate <= 0) ? 1 : rate;
    }

    public String getResult() {
        if (result != null) {
            return result.replace(",", "");
        }
        return null;
    }

    public void setResult(String result) {
        this.result = result;
    }

   /* public String getInputResult(Context context, int decimal) {
        decimal = isFiatCurrency() ?
                CurrencyValuesUtil.getChooseCurrencyDecimal(BaseApplication.getApplication(), ShareDataHelper.KEY_DEFAULT_FIAT_VALUE) :
                CurrencyValuesUtil.getChooseCurrencyDecimal(BaseApplication.getApplication(), ShareDataHelper.KEY_DEFAULT_CRYPTO_VALUE);
        if (TextUtils.isEmpty(inputResult)) {
            return ShareDataHelper.getStringValueWithDefault(
                    context, ShareDataHelper.KEY_DEFAULT_VALUE, "100");
        } else if (inputResult.length() == 0 || inputResult.equals("0")) {
            return "0";
        } else if (CalculatorUtil.hasOperator(inputResult)) {
            Double cal = CalculatorUtil.calc(inputResult).doubleValue();
            return StringUtil.priceToString(cal * rate, decimal);
        }
        int index = inputResult.indexOf(".");
        if (index > 0) {
            double tempResult = Double.parseDouble(inputResult);
            if (tempResult < 1000) {
                return inputResult;
            }
            String decimals = inputResult.substring(index + 1, inputResult.length());
            if (decimals.length() == 0) {
                return StringUtil.priceToString(Double.parseDouble(inputResult), 0) + ".";
            }
            return StringUtil.priceToString(Double.parseDouble(inputResult), decimals.length());
        }
        return StringUtil.priceToString(Double.parseDouble(inputResult), 0);
    }*/

    public void setInputResult(String inputResult) {
        this.inputResult = inputResult;
    }

    /*public void calculateResult(int decimal) {
        if (inputResult == null || inputResult.isEmpty()) {
            String defaultValue = ShareDataHelper.getStringValueWithDefault(
                    BaseApplication.getApplication().getApplicationContext(),
                    ShareDataHelper.KEY_DEFAULT_VALUE, "100");
            if (defaultValue.equals("1")) {
                result = StringUtil.priceToString(1 * rate, decimal);
            } else if (defaultValue.equals("10")) {
                result = StringUtil.priceToString(10 * rate, decimal);
            } else {
                result = StringUtil.priceToString(100 * rate, decimal);
            }
        } else if (inputResult.length() > 0) {
            if (CalculatorUtil.hasOperator(inputResult)) {
                Double cal = CalculatorUtil.calc(inputResult).doubleValue();
                result = StringUtil.priceToString(cal * rate, decimal);
            } else {
                try {
                    result = StringUtil.priceToString(
                            Double.parseDouble(inputResult) * rate, decimal);
                } catch (Exception e) {
                    result = "0.00";
                }
            }
        } else {
            result = "0.00";
        }
    }

   public String getShowResult(int decimal) {
        calculateResult(isFiatCurrency() ?
                CurrencyValuesUtil.getChooseCurrencyDecimal(BaseApplication.getApplication(), ShareDataHelper.KEY_DEFAULT_FIAT_VALUE) :
                CurrencyValuesUtil.getChooseCurrencyDecimal(BaseApplication.getApplication(), ShareDataHelper.KEY_DEFAULT_CRYPTO_VALUE));
        return result;
    }*/

    public String getInputCalculator() {
        return inputResult;
    }

    public String getDisplayInputCalculator() {
        if (!TextUtils.isEmpty(inputResult)) {
            return inputResult.replace("+", " + ").replace("-", " - ").replace("*", " x ")
                    .replace("/", " ÷ ");
        }
        return inputResult;
    }

    public String getResName() {
        String resourceName;
        if (symbol.equals("TRY")) {
            resourceName = "trytry";
        } else {
            resourceName = symbol.toLowerCase(Locale.ENGLISH);
        }
        return resourceName;
    }

    public int getFlagResId(Context context) {
        String resourceName = getResName();
        return context.getResources()
                .getIdentifier(resourceName, "drawable", context.getPackageName());
    }
    /*public Drawable getFlagDrawable(Context context) {
        String resourceName = getResName();
        return AppHelper.fetchDrawableByResId(context, resourceName);
    }*/

    public static final class Deserializer implements JsonDeserializer<Currency> {
        @Override
        public Currency deserialize(
                JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            Currency currency = new Currency();
            JsonObject obj = json.getAsJsonObject();
            currency.symbol = obj.get("symbol").getAsString();
            currency.sign = obj.get("sign").getAsString();
            currency.type = obj.get("type").getAsString();

            Type t = new TypeToken<HashMap<String, String>>() {}.getType();
            currency.name = context.deserialize(obj.get("currency_name"), t);
            currency.country = context.deserialize(obj.get("country"), t);
            currency.section = context.deserialize(obj.get("section"), t);

            currency.countryCode = obj.get("country_code").getAsString();
            currency.countryCodes = obj.get("country_codes").getAsString();
            currency.moreInfo = obj.get("moreinfo").getAsString();

            return currency;
        }
    }

}
