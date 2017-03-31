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

    public interface ICatalogUI
    {

//        public  ICatalogUI ();

        void  init (Catalog param1 ,CatalogParams param2 );

        void  updateElements ();

        void  updateElementsByItemNames (Array param1 );

        void  switchType (String param1 );

        void  switchSubType (String param1 );

        void  switchItems (Array param1 ,String param2 );

        void  goToItem (String param1 );

        void  shutdown ();

        String  type ();

        String  subType ();

        Array  items ();

        MarketScrollingList  shelf ();

        boolean  isInitialized ();

        void  overrideTitle (String param1 );

        void  exclusive (boolean param1 );

        boolean  exclusive ();

        void  onTweenIn ();

        void  onTweenOut ();

        void  refreshShelf ();

        void  updatePriceMultiplier (double param1 );

        void  setIgnoreExcludeExperiments (boolean param1 );

    }



