package habit.tracker.habittracker.common.util;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import habit.tracker.habittracker.R;

public class AppDefaultConfig {

    private static final String USER_TAG = "user";
    public static final String USAGE = "usage";
    public static final String CURRENT_USAGE_CHAIN = "current_usage_chain";
    public static final String BEST_USAGE_CHAIN = "best_usage_chain";
    public static final String USER_SCORE = "user_score";

    public static final String LEVEL1 = "level1";
    public static final String LEVEL2 = "level2";
    public static final String LEVEL3 = "level3";
    public static final String LEVEL4 = "level4";
    public static final String LEVEL5 = "level5";
    public static final String LEVEL6 = "level6";
    public static final String LEVEL7 = "level7";
    public static final String LEVEL8 = "level8";
    public static final String LEVEL9 = "level9";
    public static final String LEVEL10 = "level10";

    public static final String DAILY = "daily";
    public static final String WEEKLY = "weekly";
    public static final String MONTHLY = "monthly";
    public static final String YEARLY = "yearly";

    private static AppDefaultConfig appDefaultConfig;

    private static Map<String, String> sourceMap;

    private AppDefaultConfig(){}

    public static AppDefaultConfig getInstance(Context context) throws IOException, XmlPullParserException {
        if (appDefaultConfig != null) {
            return appDefaultConfig;
        }
        appDefaultConfig = new AppDefaultConfig();
        sourceMap = readFromAnXML(context, R.xml.app_default);
        return appDefaultConfig;
    }

    private static Map<String, String> readFromAnXML(Context context, int resource) throws IOException, XmlPullParserException {
        Map<String, String> map = new HashMap<>();
        Resources res = context.getResources();
        XmlResourceParser xpp = res.getXml(resource);
        xpp.next();
        int eventType = xpp.getEventType();
        String key = "";
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                key = xpp.getName();
            } else if (eventType == XmlPullParser.TEXT) {
                map.put(key, xpp.getText());
            }
            eventType = xpp.next();
        }
        return map;
    }

    public int getUserLevel(int score) {
        if (score < Integer.parseInt(sourceMap.get(LEVEL1))) {
            return 1;
        }
        if (score < Integer.parseInt(sourceMap.get(LEVEL2))) {
            return 2;
        }
        if (score < Integer.parseInt(sourceMap.get(LEVEL3))) {
            return 3;
        }
        if (score < Integer.parseInt(sourceMap.get(LEVEL4))) {
            return 4;
        }
        if (score < Integer.parseInt(sourceMap.get(LEVEL5))) {
            return 5;
        }
        if (score < Integer.parseInt(sourceMap.get(LEVEL6))) {
            return 6;
        }
        if (score < Integer.parseInt(sourceMap.get(LEVEL7))) {
            return 7;
        }
        if (score < Integer.parseInt(sourceMap.get(LEVEL8))) {
            return 8;
        }
        if (score < Integer.parseInt(sourceMap.get(LEVEL9))) {
            return 9;
        }
        if (score < Integer.parseInt(sourceMap.get(LEVEL10))) {
            return 10;
        }
        return 11;
    }

    public int getIntValue(String key) {
        return Integer.parseInt(sourceMap.get(key));
    }

    public String getStringValue(String key) {
        return sourceMap.get(key);
    }
}
