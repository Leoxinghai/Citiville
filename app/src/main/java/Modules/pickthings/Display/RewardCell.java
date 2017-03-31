package Modules.pickthings.Display;

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
import Display.MarketUI.*;
import Display.aswingui.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
import org.aswing.*;
import org.aswing.geom.*;
import org.aswing.zynga.*;
import com.xinghai.Debug;

    public class RewardCell extends JPanel
    {
        protected JWindow m_toolTipWindow ;
        private Bitmap m_asset ;
        private JLabel m_label ;
        private JLabel m_title ;
        private Bitmap m_counter ;
        private double m_count =0;
        private Bitmap m_unknown =null ;
        private AssetPane holder ;
        private Bitmap m_bg ;
        private JPanel layerPanel ;
        private String m_item ;
        private boolean active =false ;

        public  RewardCell (String param1 ,String param2 ,Bitmap param3 ,Bitmap param4 ,Bitmap param5 ,Bitmap param6 )
        {
            double _loc_8 =0;
            double _loc_9 =0;
            JPanel _loc_10 =null ;
            JPanel _loc_11 =null ;
            JPanel _loc_12 =null ;
            this.m_item = param1;
            Debug.debug6("RewardCell"+param1+";"+param2);

            super(new SoftBoxLayout(SoftBoxLayout.Y_AXIS));
            if (param2)
            {
                _loc_8 = ASwingHelper.shrinkFontSizeToFit(param4.width, param2, EmbeddedArt.titleFont, 11, null, null, null, 8);
                this.m_title = ASwingHelper.makeLabel(param2, EmbeddedArt.titleFont, _loc_8, 3840965, AsWingConstants.CENTER);
                this.append(this.m_title);
            }
            this.layerPanel = new JPanel(new LayeredLayout());
            this.layerPanel.setPreferredSize(new IntDimension(param4.width, param4.height));
            JPanel _loc_7 =new JPanel(new CenterLayout ());
            this.m_asset = param3;
            if (param6)
            {
                this.holder = new AssetPane(param6);
                this.m_unknown = param6;
            }
            else
            {
                this.holder = new AssetPane(param3);
                this.active = true;
            }
            if (param4)
            {
                ASwingHelper.setBackground(_loc_7, param4);
                this.m_bg = param4;
                _loc_9 = Math.max(param4.width / Math.max(param3.width, param4.width), param4.height / Math.max(param4.height, param3.height));
                _loc_13 = _loc_9;
                param3.scaleY = _loc_9;
                param3.scaleX = _loc_13;
                param3.smoothing = true;
            }
            _loc_7.append(this.holder);
            this.layerPanel.append(_loc_7);
            this.m_label = ASwingHelper.makeLabel("00", EmbeddedArt.defaultFontNameBold, 11, 16777215);
            if (param5)
            {
                _loc_10 = new JPanel(new SoftBoxLayout(SoftBoxLayout.Y_AXIS));
                _loc_11 = new JPanel(new SoftBoxLayout(SoftBoxLayout.X_AXIS));
                _loc_10.append(ASwingHelper.verticalStrut(70));
                _loc_10.append(_loc_11);
                _loc_11.append(ASwingHelper.horizontalStrut(52));
                _loc_12 = new JPanel(new SoftBoxLayout(SoftBoxLayout.X_AXIS));
                _loc_11.append(_loc_12);
                _loc_12.append(this.m_label);
                ASwingHelper.setBackground(_loc_12, param5);
                _loc_12.setPreferredSize(new IntDimension(param5.width, param5.height));
                this.m_label.setPreferredSize(new IntDimension(param5.width, param5.height));
                this.layerPanel.append(_loc_10);
            }
            this.append(this.layerPanel);
            this.layerPanel.addEventListener(MouseEvent.ROLL_OVER, this.onMouseOver, false, 0, true);
            this.layerPanel.addEventListener(MouseEvent.ROLL_OUT, this.onMouseOut, false, 0, true);
            ASwingHelper.prepare(this);
            return;
        }//end

        public double  count ()
        {
            return this.m_count;
        }//end

        public void  updateCount (double param1 )
        {
            if (param1 != this.m_count)
            {
                this.m_count = param1;
                if (this.count < 10)
                {
                    this.m_label.setText("0" + this.m_count);
                }
                else
                {
                    this.m_label.setText((String)this.m_count);
                }
                if (this.m_count == 0 && this.m_unknown)
                {
                    this.active = false;
                    this.holder.setAsset(this.m_unknown);
                }
                else
                {
                    this.active = true;
                    this.holder.setAsset(this.m_asset);
                }
                ASwingHelper.prepare(this.m_label);
                ASwingHelper.prepare(this.holder);
            }
            return;
        }//end

        public void  onMouseOver (MouseEvent event )
        {

            Debug.debug6("RewardCell.onMouseOver");

            if (this.m_count > 0)
            {
                this.showToolTip();
            }
            return;
        }//end

        public void  onMouseOut (MouseEvent event )
        {
            this.hideToolTip();
            return;
        }//end

        private void  showToolTip ()
        {
            _loc_1 = this.parent.parent.parent.parent ;
            if (!this.m_toolTipWindow)
            {
                this.m_toolTipWindow = new JWindow(Global.stage);
            }
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            ItemCatalogToolTip _loc_3 =new ItemCatalogToolTip ();
            _loc_3.changeInfo(Global.gameSettings().getItemByName(this.m_item));
            _loc_2.appendAll(_loc_3);
            _loc_4 = this.layerPanel.parent.localToGlobal(new Point(this.layerPanel.getX (),this.layerPanel.getY ()));
            this.layerPanel.parent.localToGlobal(new Point(this.layerPanel.getX(), this.layerPanel.getY())).x = this.layerPanel.parent.localToGlobal(new Point(this.layerPanel.getX(), this.layerPanel.getY())).x - this.layerPanel.width / 2;
            ASwingHelper.prepare(_loc_3);
            this.m_toolTipWindow.setContentPane(_loc_2);
            this.m_toolTipWindow.setX(_loc_4.x);
            this.m_toolTipWindow.setY(_loc_4.y - _loc_3.height + 10);
            ASwingHelper.prepare(this.m_toolTipWindow);
            this.m_toolTipWindow.mouseChildren = false;
            this.m_toolTipWindow.mouseEnabled = false;
            this.m_toolTipWindow.show();
            Sounds.play("UI_mouseover");
            return;
        }//end

        public void  changeAsset (Bitmap param1 )
        {
            double _loc_2 =0;
            this.m_asset = param1;
            if (this.m_bg)
            {
                _loc_2 = Math.max(this.m_bg.width / Math.max(param1.width, this.m_bg.width), this.m_bg.height / Math.max(this.m_bg.height, param1.height));
                _loc_3 = _loc_2;
                param1.scaleY = _loc_2;
                param1.scaleX = _loc_3;
                param1.smoothing = true;
            }
            this.holder.setAsset(param1);
            return;
        }//end

        private void  hideToolTip ()
        {
            if (this.m_toolTipWindow)
            {
                this.m_toolTipWindow.hide();
            }
            return;
        }//end

    }



