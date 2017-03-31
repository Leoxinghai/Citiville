package Classes.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.graphics.*;

import com.xiyu.util.Array;
import com.xiyu.util.Dictionary;

    public class DateFormatter
    {
        public static  String CENTRAL_EUROPEAN ="CET";
        public static  String EASTERN_EUROPEAN ="EET";
        public static  String BAGHDAD ="BT";
        public static  String CHINA_COST ="CCT";
        public static  String JAPAN_STANDARD ="JST";
        public static  String GUAM_STANDARD ="GT";
        public static  String NEW_ZEALAND ="NZST";
        public static  String WEST_AFRICA ="WAT";
        public static  String AZORES ="AT";
        public static  String ATLANTIC_STANDARD ="AST";
        public static  String EASTERN_DAYLIGHT ="EDT";
        public static  String CENTRAL_DAYLIGHT ="CDT";
        public static  String MOUNTAIN_DAYLIGHT ="MDT";
        public static  String PACIFIC_DAYLIGHT ="PDT";
        public static  String EASTERN_STANDARD ="EST";
        public static  String CENTRAL_STANDARD ="CST";
        public static  String MOUNTAIN_STANDARD ="MST";
        public static  String PACIFIC_STANDARD ="PST";
        public static  String YUKON_STANDARD ="YST";
        public static  String ALASKA_HAWAII ="HST";
        public static  String NOME_TIME ="NT";
        public static  String DATE_LINE_WEST ="IDLW";
        private static  String CET ="+0100";
        private static  String EET ="+0200";
        private static  String BT ="+0300";
        private static  String CCT ="+0800";
        private static  String JST ="+0900";
        private static  String GT ="+1000";
        private static  String NZST ="+1200";
        private static  String WAT ="-0100";
        private static  String AT ="-0200";
        private static  String AST ="-0400";
        private static  String EDT ="-0400";
        private static  String CDT ="-0500";
        private static  String MDT ="-0600";
        private static  String PDT ="-0700";
        private static  String EST ="-0500";
        private static  String CST ="-0600";
        private static  String MST ="-0700";
        private static  String PST ="-0800";
        private static  String YST ="-0900";
        private static  String HST ="-1000";
        private static  String NT ="-1100";
        private static  String IDLW ="-1200";

        public  DateFormatter ()
        {
            return;
        }//end

        public static Date  parseDateString (String param1 )
        {
            if (!param1 || param1 == "")
            {
                return null;
            }
            String _loc_2 =new String(convertTimeZone(param1 ));
            _loc_3 = Date.parse(_loc_2);
            Date _loc_4 =new Date(_loc_3 );
            return new Date(_loc_3);
        }//end

        public static double  parseTimeString (String param1 )
        {
            if (!param1 || param1 == "")
            {
                return NaN;
            }
            String _loc_2 =new String(convertTimeZone(param1 ));
            _loc_3 = Date.parse(_loc_2);
            return _loc_3;
        }//end

        public static String  convertTimeZone (String param1 )
        {
            if (!param1 || param1 == "")
            {
                return param1;
            }
            if (param1.search(/GMT""GMT/ig) >= 0)
            {
                return param1;
            }
            if (param1.search(/UTC""UTC/ig) >= 0)
            {
                return param1;
            }
            param1 = param1.replace(CENTRAL_EUROPEAN, "GMT" + CET);
            param1 = param1.replace(EASTERN_EUROPEAN, "GMT" + EET);
            param1 = param1.replace(BAGHDAD, "GMT" + BT);
            param1 = param1.replace(CHINA_COST, "GMT" + CCT);
            param1 = param1.replace(JAPAN_STANDARD, "GMT" + JST);
            param1 = param1.replace(GUAM_STANDARD, "GMT" + GT);
            param1 = param1.replace(NEW_ZEALAND, "GMT" + NZST);
            param1 = param1.replace(WEST_AFRICA, "GMT" + WAT);
            param1 = param1.replace(AZORES, "GMT" + AT);
            param1 = param1.replace(ATLANTIC_STANDARD, "GMT" + AST);
            param1 = param1.replace(EASTERN_DAYLIGHT, "GMT" + EDT);
            param1 = param1.replace(CENTRAL_DAYLIGHT, "GMT" + CDT);
            param1 = param1.replace(MOUNTAIN_DAYLIGHT, "GMT" + MDT);
            param1 = param1.replace(PACIFIC_DAYLIGHT, "GMT" + PDT);
            param1 = param1.replace(EASTERN_STANDARD, "GMT" + EST);
            param1 = param1.replace(CENTRAL_STANDARD, "GMT" + CST);
            param1 = param1.replace(MOUNTAIN_STANDARD, "GMT" + MST);
            param1 = param1.replace(PACIFIC_STANDARD, "GMT" + PST);
            param1 = param1.replace(YUKON_STANDARD, "GMT" + YST);
            param1 = param1.replace(ALASKA_HAWAII, "GMT" + HST);
            param1 = param1.replace(NOME_TIME, "GMT" + NT);
            param1 = param1.replace(DATE_LINE_WEST, "GMT" + IDLW);
            return param1;
        }//end

    }



