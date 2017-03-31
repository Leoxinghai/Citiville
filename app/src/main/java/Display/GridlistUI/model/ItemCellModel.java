package Display.GridlistUI.model;

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

import Classes.*;
import Classes.util.*;
import Display.aswingui.*;
import Engine.Managers.*;
//import flash.display.*;
//import flash.utils.*;

    public class ItemCellModel extends GenericCellModel
    {
        protected Dictionary m_comObjs ;
        protected int m_assetsToLoad ;
        protected Loader m_contentLoader ;

        public  ItemCellModel ()
        {
            this.m_comObjs = new Dictionary();
            return;
        }//end

         public void  setCellValue (Object param1)
        {
            super.setCellValue(param1);
            this.loadAssets();
            this.loadContent();
            return;
        }//end

        protected void  loadAssets ()
        {
            this.m_assetsToLoad = 1;
            Global.delayedAssets.get(DelayedAssetLoader.MARKET_ASSETS, this.onAssetsLoaded);
            return;
        }//end

        protected void  loadContent ()
        {
            String _loc_2 =null ;
            _loc_1 = (Item)getCellValue()
            if (_loc_1)
            {
                _loc_2 = Global.gameSettings().getImageByName(_loc_1.name, "icon");
                if (!this.m_contentLoader && _loc_2)
                {
                    this.m_contentLoader = LoadingManager.load(_loc_2, this.onContentLoaded, LoadingManager.PRIORITY_HIGH);
                }
                else
                {
                    this.onContentLoaded();
                }
            }
            return;
        }//end

        final protected void  onAssetsLoaded (DisplayObject param1 ,String param2 )
        {
            if (param1 !=null)
            {
                this.m_comObjs.put(param2,  param1);
                this.m_assetsToLoad--;
                if (this.m_assetsToLoad == 0)
                {
                    this.finishLoad();
                }
            }
            return;
        }//end

        protected void  onContentLoaded (Object param1)
        {
            if (getCellComponent() instanceof IContentComponent)
            {
                if (this.m_contentLoader && this.m_contentLoader.content)
                {
                    (getCellComponent() as IContentComponent).setContent(this.m_contentLoader.content);
                }
            }
            return;
        }//end

        protected void  finishLoad ()
        {
            if (getCellComponent() instanceof IAssetComponent)
            {
                (getCellComponent() as IAssetComponent).makeAssets(this.m_comObjs);
            }
            this.initView();
            return;
        }//end

        protected void  initView ()
        {
            _loc_1 = (IGenericComponent)getCellComponent()
            if (_loc_1)
            {
                _loc_1.build();
                _loc_1.updateView(getCellValue());
                addListeners();
            }
            return;
        }//end

    }



