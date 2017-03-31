package Modules.mechanics.MarketUI.view;

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
import Display.GridlistUI.view.*;
import Display.aswingui.*;
import Mechanics.GameMechanicInterfaces.*;
import Modules.mechanics.ui.*;
//import flash.display.*;
//import flash.utils.*;
import org.aswing.*;

    public class SlottedContainerCellView extends ItemCellView
    {
        protected IMechanicUser m_mechanicUser ;
        protected SlottedContainerConfig m_config ;
        protected int m_slotId ;
        protected int m_tabId ;
        protected JPanel m_arrowAlignPanel ;
        protected JPanel m_arrowPanel ;
        protected Insets m_arrowPanelInsets ;
        protected boolean m_showArrow ;

        public  SlottedContainerCellView (IMechanicUser param1 ,SlottedContainerConfig param2 ,int param3 ,int param4 ,boolean param5 =true )
        {
            this.m_mechanicUser = param1;
            this.m_config = param2;
            this.m_slotId = param3;
            this.m_tabId = param4;
            this.m_showArrow = param5;
            if (this.m_showArrow)
            {
                this.m_arrowAlignPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
                this.m_arrowPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                this.m_arrowPanelInsets = new Insets(0, 5, 20, 0);
            }
            return;
        }//end

        public IMechanicUser  generator ()
        {
            return this.m_mechanicUser;
        }//end

        protected DisplayObject  arrowAsset ()
        {
            return m_assetDict.get("sequence_arrow");
        }//end

         public void  makeAssets (Dictionary param1 )
        {
            _loc_2 = param1.get(DelayedAssetLoader.SLOTTED_CONTAINER_ASSETS) ;
            this.m_assetDict.put("sequence_arrow", (DisplayObject) new _loc_2.get("mall_arrow"));
            return;
        }//end

         public void  updateView (Object param1)
        {
            ASwingHelper.prepare(this);
            return;
        }//end

        public int  slotId ()
        {
            return this.m_slotId;
        }//end

        public int  tabId ()
        {
            return this.m_tabId;
        }//end

         protected void  initLayout ()
        {
            super.initLayout();
            if (this.m_arrowAlignPanel)
            {
                if (this.m_arrowPanel)
                {
                    this.updateArrowPanel();
                    this.m_arrowAlignPanel.append(this.m_arrowPanel);
                }
                this.append(this.m_arrowAlignPanel);
            }
            ASwingHelper.setEasyBorder(m_panel, 0, 13, 0);
            return;
        }//end

        protected void  updateArrowPanel ()
        {
            if (this.arrowAsset)
            {
                ASwingHelper.setSizedBackground(this.m_arrowPanel, this.arrowAsset, this.m_arrowPanelInsets);
                ASwingHelper.prepare(this.m_arrowAlignPanel);
            }
            return;
        }//end

    }



