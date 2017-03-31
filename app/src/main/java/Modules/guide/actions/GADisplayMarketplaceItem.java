package Modules.guide.actions;

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

import Display.*;
import Display.MarketUI.*;

    public class GADisplayMarketplaceItem extends GADisplayArrow
    {
        protected String m_itemName ;
        protected String m_tabName ;
        protected String m_subtabName ;
        public static  int MARKETPLACE_REL_INIT_X =106;
        public static  int MARKETPLACE_REL_INIT_Y =75;

        public  GADisplayMarketplaceItem ()
        {
            return;
        }//end

         public boolean  createFromXml (XML param1 )
        {
            _loc_2 = super.createFromXml(param1);
            this.m_itemName = m_xml.@itemName;
            this.m_tabName = m_xml.@tabName;
            this.m_subtabName = m_xml.@subtabName;
            if (!this.m_itemName || !this.m_tabName)
            {
                return false;
            }
            return _loc_2;
        }//end

         public void  enter ()
        {
            Catalog _loc_1 =null ;
            int _loc_2 =0;
            int _loc_3 =0;
            int _loc_4 =0;
            int _loc_5 =0;
            int _loc_6 =0;
            MarketScrollingList _loc_7 =null ;
            MarketScrollingList _loc_8 =null ;
            m_overrideArrowBoolean = true;
            if (this.m_itemName && this.m_tabName)
            {
                _loc_1 = UI.displayCatalog(new CatalogParams(this.m_tabName).setItemName(this.m_itemName).setSubType(this.m_subtabName));
                if (_loc_1.asset instanceof LargeCatalogUI && _loc_1.asset.shelf)
                {
                    _loc_7 = _loc_1.asset.shelf;
                    if (_loc_7.curIndex > 0 || this.m_subtabName != null)
                    {
                        _loc_2 = (_loc_1.asset as LargeCatalogUI).getIndexOfItem(this.m_itemName);
                        _loc_3 = (_loc_2 - _loc_7.curIndex) % 3;
                        _loc_5 = Math.floor((_loc_2 - _loc_7.curIndex) / 3);
                        _loc_4 = _loc_3 > 0 ? (_loc_3 * 218) : (0);
                        _loc_6 = _loc_5 > 0 ? (_loc_5 * 178) : (0);
                        m_offsetX = GADisplayMarketplaceItem.MARKETPLACE_REL_INIT_X + _loc_4;
                        m_offsetY = GADisplayMarketplaceItem.MARKETPLACE_REL_INIT_Y + _loc_6;
                    }
                }
                else if (_loc_1.asset instanceof ItemCatalogUI && _loc_1.asset.shelf)
                {
                    _loc_8 = _loc_1.asset.shelf;
                    if (_loc_8.curIndex >= 0)
                    {
                        _loc_2 = (_loc_1.asset as ItemCatalogUI).getIndexOfItem(this.m_itemName);
                        _loc_3 = _loc_2 - _loc_8.curIndex;
                        _loc_4 = _loc_3 > 0 ? (_loc_3 * _loc_8.getItemWidth()) : (0);
                        m_offsetX = GADisplayMarketplaceItem.MARKETPLACE_REL_INIT_X + _loc_4;
                        m_offsetY = GADisplayMarketplaceItem.MARKETPLACE_REL_INIT_Y;
                    }
                }
                else
                {
                    return;
                }
            }
            else
            {
                return;
            }
            super.enter();
            return;
        }//end

    }



