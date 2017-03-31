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
import Engine.Classes.*;
import Engine.Helpers.*;
import com.greensock.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
//import flash.geom.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.border.*;
import Classes.sim.*;

    public class HUDGoodsComponent extends HUDComponent
    {
        public DisplayObject m_goodsSlider =HUDThemeManager.getAsset(HUDThemeManager.GOODS_BAR );
        public JTextField m_goodsText ;
        protected JPanel m_goodsPanel ;
        private DisplayObject m_goodsAsset ;
        private JWindow m_moreEnergyWindow ;
        protected boolean m_bReminderDown =false ;
        private double fullRatio =0.1;
        private AutoAnimatedBitmap m_barberPole ;
        private int m_barberPoleTimeout ;
        protected CommodityPopup m_commodityPopup ;
        protected int m_commodityTweenedAmount ;

        public  HUDGoodsComponent (Function param1)
        {
            //this.m_goodsSlider = HUDThemeManager.getAsset(HUDThemeManager.GOODS_BAR);
            this.fullRatio = Global.gameSettings().getNumber("foodGoodsRatio", 0.1);
            this.m_goodsSlider.addEventListener(MouseEvent.MOUSE_OVER, this.onMouseOver, false, 0, true);
            this.m_goodsSlider.addEventListener(MouseEvent.MOUSE_OUT, this.onMouseOut, false, 0, true);
            this.m_goodsSlider.addEventListener(MouseEvent.CLICK, this.onClick, false, 0, true);
            return;
        }//end

         protected void  buildComponent ()
        {
            m_jPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT, 0);
            TextFormat _loc_1 =new TextFormat(EmbeddedArt.DEFAULT_FONT_NAME_BOLD ,16,16777215,true ,false ,false ,null ,null ,TextFormatAlign.CENTER );
            TextFormat _loc_2 =new TextFormat ();
            _loc_2.align = TextFormatAlign.CENTER;
            _loc_3 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            this.m_goodsPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            _loc_4 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_4.setBorder(new EmptyBorder(null, new Insets(5, 35, 0, 0)));
            this.m_goodsText = ASwingHelper.makeTextField("0", EmbeddedArt.DEFAULT_FONT_NAME_BOLD, 18, 16777215, 0, _loc_1);
            this.m_goodsText.filters = .get(new GlowFilter(0, 0.5, 3, 3, 10));
            this.m_goodsText.setBorder(new EmptyBorder(null, new Insets(5)));
            _loc_5 = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER );
            _loc_5.append(this.m_goodsText);
            _loc_5.setPreferredWidth(126);
            _loc_4.append(_loc_5);
            ASwingHelper.setSizedBackground(this.m_goodsPanel, this.m_goodsSlider);
            this.m_goodsPanel.append(_loc_4);
            _loc_3.append(this.m_goodsPanel);
            _loc_3.setBorder(new EmptyBorder(null, new Insets(3)));
            m_jPanel.append(_loc_3);
            return;
        }//end

        private void  barberPoleLoadHandler (DisplayObject param1 ,String param2 )
        {
            this.m_barberPole = new AutoAnimatedBitmap(((Bitmap)param1).bitmapData, 4, 132, 19, 24);
            this.m_barberPole.x = 37;
            this.m_barberPole.y = 12;
            this.m_barberPole.stop();
            this.m_barberPole.alpha = 0;
            addChildAt(this.m_barberPole, 0);
            return;
        }//end

        private void  playBarberPole (int param1 =1)
        {
            direction = param1;
            if (this.m_barberPole)
            {
                if (!this.m_barberPole.isPlaying)
                {
                    this.m_barberPole.direction = direction;
void                     TweenLite .to (this .m_barberPole ,0.3,{1alpha , onStart ()
            {
                m_barberPole.play();
                return;
            }//end
            });
                }
                clearTimeout(this.m_barberPoleTimeout);
                this.m_barberPoleTimeout = setTimeout(this.hideBarberPole, 1000);
            }
            return;
        }//end

        private void  hideBarberPole ()
        {
            clearTimeout(this.m_barberPoleTimeout);
void             TweenLite .to (this .m_barberPole ,0.3,{0alpha , onComplete ()
            {
                m_barberPole.stop();
                return;
            }//end
            });
            return;
        }//end

         protected void  attachToolTip ()
        {
            m_toolTip =new ToolTip (String  ()
            {
                return ZLoc.t("Main", "Goods_ToolTip", {amount:m_goodsText.getText(), capacity:Global.player.commodities.getCapacity(Commodities.GOODS_COMMODITY).toString()});
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
                this.m_commodityPopup.forceUpdateValue(this.m_commodityTweenedAmount);
                if (param1 !=null)
                {
                    this.m_commodityPopup.show();
                }
            }
            return;
        }//end

        protected Vector2  getPopupPosition (double param1 )
        {
            _loc_2 =(MovieClip) this.m_goodsSlider.get( "sliderMask");
            Vector2 _loc_3 =new Vector2 ();
            _loc_3.x = -this.m_commodityPopup.m_supplyTooltip.width;
            _loc_3.y = (1 - param1) * _loc_2.height - 27;
            if (localToGlobal(_loc_3).y < 0)
            {
                _loc_3.y = globalToLocal(new Point(0, 0)).y;
            }
            return _loc_3;
        }//end

         protected void  setCounter ()
        {
            m_counter =new HUDCapacityCounter (void  (double param1 ,double param2 )
            {
                m_commodityTweenedAmount = param2;
                _loc_3 =Global.player.commodities.getCapacity(Commodities.GOODS_COMMODITY );
                _loc_4 = _loc_3>0? (Math.min(param1, 1)) : (0);
                if ((_loc_3 > 0 ? (Math.min(param1, 1)) : (0)) <= 0.1 && _loc_4 > 0)
                {
                    _loc_4 = 0.1;
                }
                _loc_5 = 37;
                _loc_6 =(MovieClip) m_goodsSlider.get("sliderMask");
                _loc_7 = _loc_4(-1)*_loc_6.width +_loc_5 ;
                playBarberPole(_loc_7 > _loc_6.x ? (AnimatedBitmap.ANIMATE_FORWARD) : (AnimatedBitmap.ANIMATE_REVERSE));
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

    }



