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

import Classes.*;
import Display.*;
import Display.MarketUI.*;
import Display.aswingui.*;
import com.greensock.*;
//import flash.display.*;
//import flash.filters.*;
import org.aswing.*;
import org.aswing.geom.*;

    public class CollectionItemCell extends DataItemCell
    {
        protected JPanel m_itemIconPane ;
        protected JPanel m_countPane ;
        protected JTextField m_countLabel ;
        protected IntDimension m_slotSize ;
        protected int m_slotOffset ;
        protected boolean m_initialized =false ;
        protected boolean m_fadeInOnInit =false ;
        protected Function m_completeCallback =null ;
        protected Function m_initializedCallback =null ;
        protected TweenLite m_fadeTween =null ;

        public  CollectionItemCell (LayoutManager param1 ,IntDimension param2 ,int param3 =2)
        {
            super(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 0, SoftBoxLayout.CENTER));
            if (param2)
            {
                this.m_slotSize = param2;
            }
            else
            {
                this.m_slotSize = new IntDimension(Catalog.CARD_WIDTH, Catalog.CARD_HEIGHT);
            }
            this.m_slotOffset = param3;
            setPreferredSize(this.m_slotSize);
            return;
        }//end

         protected double  scaleToFit (DisplayObject param1 )
        {
            double _loc_2 =1;
            double _loc_3 =1;
            _loc_4 = this.m_slotSize.width-this.m_slotOffset;
            _loc_5 = this.m_slotSize.height-this.m_slotOffset;
            if (param1.width > _loc_5)
            {
                _loc_2 = _loc_5 / param1.width;
            }
            if (param1.height > _loc_4)
            {
                _loc_3 = _loc_4 / param1.height;
            }
            return Math.min(_loc_2, _loc_3);
        }//end

         protected void  initializeCell ()
        {
            int _loc_1 =0;
            _loc_1 = Global.player.getNumCollectablesOwned(m_item.name);
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(AsWingConstants.CENTER ,-15);
            this.m_itemIconPane = ASwingHelper.makeSoftBoxJPanelVertical();
            m_itemIcon.alpha = this.getItemAlpha(_loc_1);
            AssetPane _loc_3 =new AssetPane(m_itemIcon );
            ASwingHelper.prepare(_loc_3);
            this.m_itemIconPane.append(_loc_3);
            ASwingHelper.prepare(this.m_itemIconPane);
            this.m_countPane = ASwingHelper.makeSoftBoxJPanel(AsWingConstants.LEFT);
            this.m_countLabel = ASwingHelper.makeTextField("x" + _loc_1 + " ", EmbeddedArt.defaultFontNameBold, 12, 16777215);
            this.m_countLabel.filters = .get(new GlowFilter(0));
            this.m_countPane.append(ASwingHelper.horizontalStrut(30));
            this.m_countPane.append(this.m_countLabel);
            ASwingHelper.prepare(this.m_countPane);
            _loc_2.append(this.m_itemIconPane);
            _loc_2.append(this.m_countPane);
            ASwingHelper.prepare(_loc_2);
            this.append(_loc_2);
            ASwingHelper.prepare(this);
            if (this.m_fadeInOnInit && this.m_completeCallback != null)
            {
                this.fadeInTween(this.m_completeCallback);
            }
            this.m_initialized = true;
            if (this.m_initializedCallback != null)
            {
                this.m_initializedCallback(true);
            }
            return;
        }//end

        protected double  getItemAlpha (int param1 )
        {
            if (param1 == 0 || this.m_fadeInOnInit)
            {
                return 0;
            }
            return 1;
        }//end

        public void  invalidateData ()
        {
            int _loc_1 =0;
            if (this.m_initialized)
            {
                _loc_1 = Global.player.getNumCollectablesOwned(m_item.name);
                this.m_countLabel.setText("x" + _loc_1 + " ");
                ASwingHelper.prepare(this);
            }
            return;
        }//end

        public void  playFadeIn (Function param1 ,Function param2 )
        {
            if (this.m_fadeTween != null)
            {
                param1();
                return;
            }
            if (this.m_initialized)
            {
                param2(true);
                this.m_initializedCallback = null;
                this.fadeInTween(param1);
            }
            else
            {
                param2(false);
                this.m_initializedCallback = param2;
                this.m_fadeInOnInit = true;
                this.m_completeCallback = param1;
            }
            return;
        }//end

        protected void  fadeInTween (Function param1 )
        {
            this.m_fadeTween = TweenLite.to(m_itemIcon, 1, {alpha:1, onComplete:param1});
            return;
        }//end

        public void  forceFadeComplete ()
        {
            if (this.m_fadeInOnInit && this.m_completeCallback && !this.m_initialized)
            {
                this.m_completeCallback();
            }
            return;
        }//end

    }



