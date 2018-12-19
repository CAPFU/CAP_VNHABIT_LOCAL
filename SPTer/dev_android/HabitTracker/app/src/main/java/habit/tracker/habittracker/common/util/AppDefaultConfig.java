package habit.tracker.habittracker.common.util;

import android.content.Context;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Map;

import habit.tracker.habittracker.R;

public class AppDefaultConfig {

    private static AppDefaultConfig appDefaultConfig;

    private Map<String, String> sourceMap;

    private AppDefaultConfig(){}

    public static AppDefaultConfig getInstance(Context context) throws IOException, XmlPullParserException {
        if (appDefaultConfig != null) {
            return appDefaultConfig;
        }
        appDefaultConfig = new AppDefaultConfig();
        appDefaultConfig.readFromXml(context);
        return appDefaultConfig;
    }

    private void readFromXml(Context context) throws IOException, XmlPullParserException {
        sourceMap = XmlAppHelper.readFromAnXML(context, R.xml.app_default);
    }

    public int getUserLevel(int score) {
        if (score < Integer.parseInt(sourceMap.get(XmlAppHelper.LEVEL1))) {
            return 1;
        }
        if (score < Integer.parseInt(sourceMap.get(XmlAppHelper.LEVEL2))) {
            return 2;
        }
        if (score < Integer.parseInt(sourceMap.get(XmlAppHelper.LEVEL3))) {
            return 3;
        }
        if (score < Integer.parseInt(sourceMap.get(XmlAppHelper.LEVEL4))) {
            return 4;
        }
        if (score < Integer.parseInt(sourceMap.get(XmlAppHelper.LEVEL5))) {
            return 5;
        }
        if (score < Integer.parseInt(sourceMap.get(XmlAppHelper.LEVEL6))) {
            return 6;
        }
        if (score < Integer.parseInt(sourceMap.get(XmlAppHelper.LEVEL7))) {
            return 7;
        }
        if (score < Integer.parseInt(sourceMap.get(XmlAppHelper.LEVEL8))) {
            return 8;
        }
        if (score < Integer.parseInt(sourceMap.get(XmlAppHelper.LEVEL9))) {
            return 9;
        }
        if (score < Integer.parseInt(sourceMap.get(XmlAppHelper.LEVEL10))) {
            return 10;
        }
        return 11;
    }

    public int getIntValue(String key) {
        return Integer.parseInt(sourceMap.get(XmlAppHelper.LEVEL1));
    }
}
