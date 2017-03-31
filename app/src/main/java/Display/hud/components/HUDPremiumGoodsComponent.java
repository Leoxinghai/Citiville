package Display.hud.components;

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
import Classes.inventory.*;
import Display.*;
import Display.DialogUI.*;
import Display.PopulationUI.*;
import Display.aswingui.*;
import Display.hud.*;
import Engine.*;
import Engine.Helpers.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
//import flash.geom.*;
//import flash.text.*;
import org.aswing.*;
import org.aswing.border.*;
import Classes.sim.*;

    public class HUDPremiumGoodsComponent extends HUDComponent
    {
        public DisplayObject m_premiumGoodsSlider =HUDThemeManager.getAsset(HUDThemeManager.PREMIUM_GOODS_BAR );
        public JTextField m_goodsText ;
        public JTextField m_premiumText ;
        protected JPanel m_premiumPanel ;
        private DisplayObject m_premiumAsset ;
        private JWindow m_moreEnergyWindow ;
        protected boolean m_bReminderDown =false ;
        private double fullRatio =0.1;
        private AutoAnimatedBitmap m_barberPole1 ;
        private int m_barberPoleTimeout1 ;
        protected CommodityPopup m_commodityPopup ;
        protected int m_commodityTweenedAmount1 ;
        private AutoAnimatedBitmap m_barberPole2 ;
        private int m_barberPoleTimeout2 ;
        protected int m_commodityTweenedAmount2 ;
        private  int m_textSize =12;

        public  HUDPremiumGoodsComponent (Function param1)
        {
            //this.m_premiumGoodsSlider = HUDThemeManager.getAsset(HUDThemeManager.PREMIUM_GOODS_BAR);
            this.fullRatio = Global.gameSettings().getNumber("foodGoodsRatio", 0.1);
            this.m_premiumGoodsSlider.addEventListener(MouseEvent.MOUSE_OVER, this.onMouseOver, false, 0, true);
            this.m_premiumGoodsSlider.addEventListener(MouseEvent.MOUSE_OUT, this.onMouseOut, false, 0, true);
            this.m_premiumGoodsSlider.addEventListener(MouseEvent.CLICK, this.onClick, false, 0, true);
            return;
        }//end

         public boolean  doubleBarComponent ()
        {
            return true;
        }//end

         protected void  buildComponent ()
        {
            m_jPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT, 0);
            TextFormat _loc_1 =new TextFormat(EmbeddedArt.defaultFontNameBold ,this.m_textSize ,16777215,true ,false ,false ,null ,null ,TextFormatAlign.CENTER );
            TextFormat _loc_2 =new TextFormat ();
            _loc_2.align = TextFormatAlign.CENTER;
            _loc_3 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            this.m_premiumPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            _loc_4 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_4.setBorder(new EmptyBorder(null, new Insets(5, 35, 0, 0)));
            this.m_premiumText = ASwingHelper.makeTextField("0", EmbeddedArt.defaultFontNameBold, this.m_textSize, 16777215, 0, _loc_1);
            this.m_premiumText.filters = .get(new GlowFilter(0, 0.5, 3, 3, 10));
            this.m_premiumText.setBorder(new EmptyBorder(null, new Insets(4)));
            _loc_5 = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER );
            _loc_5.append(this.m_premiumText);
            _loc_5.setPreferredWidth(126);
            _loc_4.append(_loc_5);
            this.m_goodsText = ASwingHelper.makeTextField("0", EmbeddedArt.defaultFontNameBold, this.m_textSize, 16777215, 0, _loc_1);
            this.m_goodsText.filters = .get(new GlowFilter(0, 0.5, 3, 3, 10));
            _loc_6 = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER );
            _loc_6.append(this.m_goodsText);
            _loc_6.setPreferredWidth(126);
            _loc_4.append(_loc_6);
            ASwingHelper.setSizedBackground(this.m_premiumPanel, this.m_premiumGoodsSlider);
            this.m_premiumPanel.append(_loc_4);
            _loc_3.append(this.m_premiumPanel);
            _loc_3.setBorder(new EmptyBorder(null, new Insets(3)));
            m_jPanel.append(_loc_3);
            return;
        }//end

         protected void  attachToolTip ()
        {
            m_toolTip =new ToolTip (String  ()
            {
                return ZLoc.t("Main", "Premium_Goods_ToolTip", {amount1:m_premiumText.getText(), capacity1:Global.player.commodities.getCapacity(Commodities.PREMIUM_GOODS_COMMODITY).toString(), amount2:m_goodsText.getText(), capacity2:Global.player.commodities.getCapacity(Commodities.GOODS_COMMODITY).toString()});
            }//end
            );
            m_toolTip.attachToolTip(this);
            m_toolTip.hideCursor = true;
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

        protected void  onClick (MouseEvent event )
        {
            double _loc_2 =0;
            double _loc_3 =0;
            double _loc_4 =0;
            StorageDialog _loc_5 =null ;
            if (!Global.isVisiting())
            {
                _loc_2 = Global.player.commodities.getCapacity(Commodities.GOODS_COMMODITY);
                _loc_3 = Global.player.commodities.getCount(Commodities.GOODS_COMMODITY);
                _loc_4 = _loc_2 > 0 ? (Math.min(_loc_3 / _loc_2, 1)) : (0);
                if (_loc_3 / _loc_2 < this.fullRatio)
                {
                    ResourceExplanationDialog.show(ResourceExplanationDialog.TYPE_NOT_ENOUGH_GOODS, true);
                }
                else
                {
                    _loc_5 = new StorageDialog();
                    UI.displayPopup(_loc_5);
                }
            }
            return;
        }//end

        protected void  attachCommodityPopup (boolean param1 ,double param2 )
        {
            Vector2 _loc_3 =null ;
            if (!this.m_commodityPopup)
            {
                this.m_commodityPopup = new CommodityPopup(Commodities.GOODS_COMMODITY);
                this.addChild(this.m_commodityPopup);
                this.m_commodityPopup.hide();
            }
            if (this.m_commodityPopup)
            {
                _loc_3 = this.getPopupPosition(param2);
                this.m_commodityPopup.setAppearPos(_loc_3);
                this.m_commodityPopup.x = _loc_3.x;
                this.m_commodityPopup.y = _loc_3.y;
                this.m_commodityPopup.forceUpdateValue(this.m_commodityTweenedAmount1);
                if (param1 !=null)
                {
                    this.m_commodityPopup.show();
                }
            }
            return;
        }//end

        protected Vector2  getPopupPosition (double param1 )
        {
            _loc_2 =(MovieClip) this.m_premiumGoodsSlider.get( "sliderMask");
            Vector2 _loc_3 =new Vector2 ();
            _loc_3.x = -this.m_commodityPopup.m_supplyTooltip.width;
            _loc_3.y = (1 - param1) * _loc_2.height - 27;
            if (localToGlobal(_loc_3).y < 0)
            {
                _loc_3.y = globalToLocal(new Point(0, 0)).y;
            }
            return _loc_3;
        }//end

         protected void  setCounter1 ()
        {
            m_counter1 =new HUDCapacityCounter (void  (double param1 ,double param2 )
            {
                m_commodityTweenedAmount1 = param2;
                _loc_3 =Global.player.commodities.getCapacity(Commodities.GOODS_COMMODITY );
                _loc_4 = _loc_3>0? (Math.min(param1, 1)) : (0);
                if ((_loc_3 > 0 ? (Math.min(param1, 1)) : (0)) <= 0.1 && _loc_4 > 0)
                {
                    _loc_4 = 0.1;
                }
                _loc_5 =                 48;
                _loc_6 =(MovieClip) m_premiumGoodsSlider.get("sliderMask2");
                _loc_7 = _loc_4(-1)*_loc_6.width +_loc_5 ;
                _loc_6.x = _loc_7;
                TextFormat _loc_8 =new TextFormat ();
                _loc_8.align = TextFormatAlign.CENTER;
                m_goodsText.setText(Utilities.formatNumber(param2));
                m_goodsText.setTextFormat(_loc_8);
                return;
            }//end
            );
            return;
        }//end

         protected void  setCounter2 ()
        {
            m_counter2 =new HUDCapacityCounter (void  (double param1 ,double param2 )
            {
                m_commodityTweenedAmount2 = param2;
                _loc_3 =Global.player.commodities.getCapacity(Commodities.PREMIUM_GOODS_COMMODITY );
                _loc_4 = _loc_3>0? (Math.min(param1, 1)) : (0);
                if ((_loc_3 > 0 ? (Math.min(param1, 1)) : (0)) <= 0.1 && _loc_4 > 0)
                {
                    _loc_4 = 0.1;
                }
                _loc_5 =                 42;
                _loc_6 =(MovieClip) m_premiumGoodsSlider.get("sliderMask1");
                _loc_7 = _loc_4(-1)*_loc_6.width +_loc_5 ;
                _loc_6.x = _loc_7;
                TextFormat _loc_8 =new TextFormat ();
                _loc_8.align = TextFormatAlign.CENTER;
                m_premiumText.setText(Utilities.formatNumber(param2));
                m_premiumText.setTextFormat(_loc_8);
                return;
            }//end
            );
            return;
        }//end

    }



