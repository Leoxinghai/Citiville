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

    public class CatalogParams
    {
        public String type ;
        public String itemName =null ;
        public boolean exclusiveCategory =false ;
        public String overrideTitle =null ;
        public boolean closeMarket =false ;
        public String subType =null ;
        public Array customItems =null ;
        public boolean ignoreExcludeExperiments ;

        public  CatalogParams (String param1 ,String param2 )
        {
            this.type = param1;
            this.subType = param2;
            return;
        }//end

        public CatalogParams  setIgnoreExcludeExperiments (boolean param1 )
        {
            this.ignoreExcludeExperiments = param1;
            return this;
        }//end

        public CatalogParams  setType (String param1 )
        {
            this.type = param1;
            return this;
        }//end

        public CatalogParams  setItemName (String param1 )
        {
            this.itemName = param1;
            return this;
        }//end

        public CatalogParams  setExclusiveCategory (boolean param1 )
        {
            this.exclusiveCategory = param1;
            return this;
        }//end

        public CatalogParams  setOverrideTitle (String param1 )
        {
            this.overrideTitle = param1;
            return this;
        }//end

        public CatalogParams  setCloseMarket (boolean param1 )
        {
            this.closeMarket = param1;
            return this;
        }//end

        public CatalogParams  setSubType (String param1 )
        {
            this.subType = param1;
            return this;
        }//end

        public CatalogParams  setCustomItems (Array param1 )
        {
            this.customItems = param1;
            return this;
        }//end

    }



