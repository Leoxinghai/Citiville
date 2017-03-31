package Display.PopulationUI;

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

import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Display.MarketUI.*;
import Events.*;
//import flash.utils.*;

    public class PopulationDetailDialog extends GenericDialog
    {

        public  PopulationDetailDialog ()
        {
            super("");
            this.addEventListener(DataItemEvent.CLOSE_DIALOG, this.onClosePopulationDialog, false, 0, true);
            return;
        }//end  

         protected void  loadAssets ()
        {
            Global.delayedAssets.get(DelayedAssetLoader.POPULATION_ASSETS, makeAssets);
            return;
        }//end  

         protected Dictionary  createAssetDict ()
        {
            Dictionary _loc_1 =new Dictionary ();
            _loc_1.put("population_bg_happy",  m_comObject.population_bg_happy);
            _loc_1.put("population_bg_neutral",  m_comObject.population_bg_neutral);
            _loc_1.put("population_bg_sad",  m_comObject.population_bg_sad);
            _loc_1.put("population_horizontalRule",  m_comObject.population_horizontalRule);
            _loc_1.put("population_item",  m_comObject.population_item);
            return _loc_1;
        }//end  

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            return new PopulationDetailDialogView(param1, m_message, m_title, m_type, m_callback);
        }//end  

        protected void  onClosePopulationDialog (DataItemEvent event )
        {
            UI.displayCatalog(new CatalogParams("municipal").setItemName(event.item.name).setExclusiveCategory(true).setCloseMarket(true), false, true);
            this.close();
            return;
        }//end  

    }



