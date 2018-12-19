package habit.tracker.habittracker.common.util;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class XmlAppHelper {
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

    public static Map<String, String> readFromAnXML(Context context, int resource) throws IOException, XmlPullParserException {
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
}
