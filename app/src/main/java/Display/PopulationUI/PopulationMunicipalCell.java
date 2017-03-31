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
import Display.MarketUI.*;
import Display.aswingui.*;
import Events.*;
//import flash.display.*;
//import flash.events.*;
import org.aswing.*;
import org.aswing.geom.*;

    public class PopulationMunicipalCell extends DataItemCell
    {
        protected boolean m_iconLoaded =false ;
        protected boolean m_assetSwfLoaded =false ;
        protected Object m_comObject ;
        public static  int PADDING =8;

        public  PopulationMunicipalCell (LayoutManager param1)
        {
            super(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 0, SoftBoxLayout.CENTER));
            setPreferredSize(new IntDimension(Catalog.CARD_WIDTH + 2 * PADDING, Catalog.CARD_HEIGHT));
            setMaximumSize(new IntDimension(Catalog.CARD_WIDTH + 2 * PADDING, Catalog.CARD_HEIGHT));
            setMinimumSize(new IntDimension(Catalog.CARD_WIDTH + 2 * PADDING, Catalog.CARD_HEIGHT));
            this.addEventListener(MouseEvent.MOUSE_UP, this.onMouseUp, false, 0, true);
            this.addEventListener(MouseEvent.MOUSE_OVER, this.onMouseOver, false, 0, true);
            this.addEventListener(MouseEvent.MOUSE_OUT, this.onMouseOut, false, 0, true);
            return;
        }//end

         public void  setCellValue (Object param1)
        {
            super.setCellValue(param1);
            Global.delayedAssets.get(DelayedAssetLoader.POPULATION_ASSETS, this.onAssetSwfLoad);
            return;
        }//end

         protected void  onIconLoad (Event event )
        {
            if (m_loader && m_loader.content)
            {
                m_itemIcon = m_loader.content;
            }
            _loc_2 = scaleToFit(m_itemIcon);
            m_itemIcon.scaleY = scaleToFit(m_itemIcon);
            m_itemIcon.scaleX = _loc_2;
            this.m_iconLoaded = true;
            if (this.m_iconLoaded && this.m_assetSwfLoaded)
            {
                this.onAllAssetsLoaded();
            }
            return;
        }//end

        protected void  onAssetSwfLoad (DisplayObject param1 ,String param2 )
        {
            this.m_comObject = param1;
            this.m_assetSwfLoaded = true;
            if (this.m_iconLoaded && this.m_assetSwfLoaded)
            {
                this.onAllAssetsLoaded();
            }
            return;
        }//end

        protected void  onAllAssetsLoaded ()
        {
            this.initializeCell();
            setGridListCellStatus(m_gridList, false, m_index);
            return;
        }//end

         protected void  initializeCell ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(AsWingConstants.CENTER );
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(AsWingConstants.CENTER );
            _loc_2.setPreferredSize(new IntDimension(Catalog.CARD_WIDTH, Catalog.CARD_HEIGHT));
            _loc_2.setMaximumSize(new IntDimension(Catalog.CARD_WIDTH, Catalog.CARD_HEIGHT));
            _loc_2.setMinimumSize(new IntDimension(Catalog.CARD_WIDTH, Catalog.CARD_HEIGHT));
            _loc_3 = new this.m_comObject.population_item ()as DisplayObject ;
            MarginBackground _loc_4 =new MarginBackground(_loc_3 );
            _loc_2.setBackgroundDecorator(_loc_4);
            AssetPane _loc_5 =new AssetPane(m_itemIcon );
            ASwingHelper.prepare(_loc_5);
            _loc_6 = Catalog(.CARD_HEIGHT-m_itemIcon.height)/2;
            _loc_2.append(ASwingHelper.verticalStrut(_loc_6));
            _loc_2.append(_loc_5);
            ASwingHelper.prepare(_loc_2);
            _loc_1.append(ASwingHelper.horizontalStrut(PADDING));
            _loc_1.append(_loc_2);
            _loc_1.append(ASwingHelper.horizontalStrut(PADDING));
            ASwingHelper.prepare(_loc_1);
            this.append(_loc_1);
            ASwingHelper.prepare(this);
            return;
        }//end

        protected void  onMouseOver (MouseEvent event )
        {
            return;
        }//end

        protected void  onMouseOut (MouseEvent event )
        {
            return;
        }//end

        protected void  onMouseUp (MouseEvent event )
        {
            this.dispatchEvent(new DataItemEvent(DataItemEvent.CLOSE_DIALOG, m_item, null, true));
            return;
        }//end

    }



