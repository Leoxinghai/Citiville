package Display.MarketUI;

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

    public class TooltipType
    {
        public static  String ONE_ITEM ="oneItem";
        public static  String ONE_ITEM_W_LINE ="oneItemwLine";
        public static  String ONE_LINE ="oneLine";
        public static  String TITLE ="title";
        public static  String INDENTED_DESCRIPTOR ="indentedDescriptor";
        public static  String TWO_ITEMS ="twoItems";
        public static  String DESCRIPTION ="description";
        public static  String REQUIREMENT ="requirement";
        public static  String SALE ="sale";
        public static  String CHECKMARK_W_LINE ="checkmarkwLine";
        public static  String EMPTY_LOT ="emptylot";
        public static  String STARTER_PACK ="starter_pack";
        public static  String MYSTERY_CRATE ="mystery_crate";
        public static  String BUSINESS ="business";
        public static  String RESIDENCE ="residence";
        public static  String MUNICIPAL ="municipal";
        public static  String MASTERY ="plot";
        public static  String PROGRESS_BAR ="assetProgressBar";
        public static  String SIMPLE_PROGRESS_BAR ="progressBar";
        public static  String PROGRESS_COUNT ="progressCount";
        public static  String STARS ="stars";
        public static  String RECEIVABLE_XGAME_GIFT ="receivableXgameGift";
        public static  String PERMIT_PACK ="permit_pack";
        public static  String STARS_BG ="starsBG";
        public static  String SHORT_BUSINESS ="shortBusiness";
        public static  String SHORT_CROP ="shortBusiness";
        public static  String BRANDED_BUSINESS ="brandedBusiness";
        public static  String GENERIC_BUNDLE ="genericBundle";
        public static  String THEMED_BUNDLE ="themed_bundle";
        public static  String SPECIAL_OFFER ="specialOffer";

        public  TooltipType ()
        {
            return;
        }//end

    }



