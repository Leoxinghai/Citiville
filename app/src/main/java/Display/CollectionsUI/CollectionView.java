package Display.CollectionsUI;

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
import Display.InventoryUI.*;
import Display.aswingui.*;
//import flash.display.*;
import org.aswing.*;

    public class CollectionView extends InventoryView
    {

        public  CollectionView (String param1)
        {
            super(param1);
            return;
        }//end

         protected void  loadAssets ()
        {
            m_ui = new CollectionCatalogUI();
            m_content = m_ui;
            Global.delayedAssets.get(DelayedAssetLoader.INVENTORY_ASSETS, makeAssets);
            Sprite _loc_1 =new Sprite ();
            this.addChild(_loc_1);
            JWindow _loc_2 =new JWindow(_loc_1 );
            _loc_2.setContentPane(m_ui);
            ASwingHelper.prepare(_loc_2);
            _loc_2.show();
            return;
        }//end

         public void  show ()
        {
            showHelper();
            return;
        }//end

    }


