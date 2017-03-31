package Classes;

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

import Display.aswingui.*;
import Engine.*;
import com.greensock.*;
//import flash.display.*;
//import flash.text.*;
import org.aswing.*;
import Display.IProgressBar;

    public class SimpleProgressBar extends JPanel implements IProgressBar
    {
        protected TimelineLite m_animTimeline ;
        protected double m_bgAlpha =0;
        protected double m_outlineAlpha =1;
        protected int m_titleColor ;
        protected int m_barColor ;
        protected int m_bgColor ;
        protected int m_outlineColor ;
        protected int m_barFlashColor =16777215;
        protected JLabel m_titleLabel ;
        protected String m_title ;
        protected JLabel m_ratioLabel ;
        protected String m_ratio ;
        protected double m_progress =0;
        protected JPanel m_barContainer ;
        protected JPanel m_barInnerContainer ;
        protected AssetPane m_barAssetPane ;
        protected Sprite m_barOutline ;
        protected Sprite m_barBg ;
        protected Sprite m_barFlash ;
        protected Sprite m_barHolder ;
        protected Sprite m_barMask ;
        protected Sprite m_bar ;
        protected double m_barWidth ;
        protected double m_barHeight ;
        protected double m_barCornerRounding ;
        protected double m_barX =4;
        protected double m_barY =0;
        protected double m_gap =3;
        protected double m_bottomOffset =3;

        public  SimpleProgressBar (int param1 ,int param2 ,double param3 ,double param4 ,double param5 =0,double param6 =0,int param7 =2)
        {
            super(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 0, SoftBoxLayout.LEFT));
            ASFont _loc_8 =new ASFont(EmbeddedArt.defaultFontNameBold ,14,false ,false ,false ,new ASFontAdvProperties(EmbeddedArt.defaultBoldFontEmbed ,AntiAliasType.ADVANCED ,GridFitType.PIXEL ));
            ASColor _loc_9 =new ASColor(param1 );
            this.m_bar = new Sprite();
            this.m_barBg = new Sprite();
            this.m_barMask = new Sprite();
            this.m_barFlash = new Sprite();
            this.m_barHolder = new Sprite();
            this.m_barOutline = new Sprite();
            this.m_barContainer = new JPanel(new FlowLayout(param7, 0, 0, false));
            this.m_barAssetPane = new AssetPane();
            this.m_titleLabel = new JLabel("", null, JLabel.LEFT);
            this.m_titleLabel.setFont(_loc_8);
            this.m_titleLabel.setForeground(_loc_9);
            this.m_ratioLabel = new JLabel("", null, JLabel.LEFT);
            this.m_ratioLabel.setFont(_loc_8);
            this.m_ratioLabel.setForeground(_loc_9);
            this.m_titleColor = param1;
            this.m_bgColor = param1;
            this.m_outlineColor = param1;
            this.m_barColor = param2;
            this.m_barCornerRounding = param5;
            this.m_barWidth = param3;
            this.m_barHeight = param4;
            this.m_gap = param6;
            this.m_barMask.graphics.beginFill(this.m_bgColor);
            this.m_barMask.graphics.drawRoundRect(this.m_barX, this.m_barY, this.m_barWidth, this.m_barHeight + 2, this.m_barCornerRounding, this.m_barCornerRounding);
            this.m_barMask.graphics.endFill();
            this.m_barFlash.alpha = 0;
            this.m_bar.mask = this.m_barMask;
            this.m_barBg.mask = this.m_barMask;
            this.m_barHolder.addChild(this.m_barMask);
            this.m_barHolder.addChild(this.m_barBg);
            this.m_barHolder.addChild(this.m_bar);
            this.m_barHolder.addChild(this.m_barFlash);
            this.m_barHolder.addChild(this.m_barOutline);
            this.m_barAssetPane.setAsset(this.m_barHolder);
            this.m_barAssetPane.setPreferredWidth(this.m_barHolder.width + 9);
            this.m_barAssetPane.setPreferredHeight((this.m_barHolder.height + 1));
            this.m_barContainer.append(this.m_barAssetPane);
            this.m_barContainer.append(this.m_ratioLabel);
            this.append(this.m_titleLabel);
            this.append(ASwingHelper.verticalStrut(this.m_gap));
            this.append(this.m_barContainer);
            this.setProgress(0);
            return;
        }//end

        public void  setTitle (String param1 )
        {
            if (param1 != this.m_title)
            {
                this.m_title = param1;
                this.m_titleLabel.setText(this.m_title);
                ASwingHelper.prepare(this);
            }
            return;
        }//end

        public void  setTitleColor (int param1 )
        {
            this.m_titleLabel.setForeground(new ASColor(param1));
            return;
        }//end

        public void  setOutlineColor (int param1 )
        {
            this.m_outlineColor = param1;
            this.setProgress(this.m_progress);
            return;
        }//end

        public void  setBgColor (int param1 )
        {
            this.m_bgColor = param1;
            this.setProgress(this.m_progress);
            return;
        }//end

        public void  setFilters (Array param1 )
        {
            this.m_titleLabel.setTextFilters(param1);
            return;
        }//end

        public void  setBgAlpha (double param1 )
        {
            this.m_bgAlpha = param1;
            this.setProgress(this.m_progress);
            return;
        }//end

        public void  setOutlineAlpa (double param1 )
        {
            this.m_outlineAlpha = param1;
            this.setProgress(this.m_progress);
            return;
        }//end

        public void  setProgressRatioText (String param1 )
        {
            if (this.m_ratio != param1)
            {
                this.m_ratio = param1;
                this.m_ratioLabel.setText(this.m_ratio);
                ASwingHelper.prepare(this);
            }
            return;
        }//end

        public void  setProgressRatio (double param1 ,double param2 )
        {
            double _loc_3 =0;
            if (param2 > 0)
            {
                _loc_3 = MathUtil.clamp(param1 / param2, 0, 1);
            }
            this.setProgress(_loc_3);
            _loc_4 = String(param1+"/"+param2);
            this.setProgressRatioText(_loc_4);
            return;
        }//end

        public void  setProgress (double param1 )
        {
            this.m_bar.graphics.clear();
            this.m_barOutline.graphics.clear();
            this.m_barBg.graphics.clear();
            this.m_barFlash.graphics.clear();
            this.m_progress = MathUtil.clamp(param1, 0, 1);
            this.m_barOutline.graphics.lineStyle(2, this.m_outlineColor, this.m_outlineAlpha);
            this.m_barOutline.graphics.drawRoundRect(this.m_barX, (this.m_barY + 1), this.m_barWidth, this.m_barHeight, this.m_barCornerRounding, this.m_barCornerRounding);
            this.m_barBg.graphics.beginFill(this.m_bgColor, this.m_bgAlpha);
            this.m_barBg.graphics.drawRoundRect(this.m_barX, (this.m_barY + 1), this.m_barWidth, this.m_barHeight, this.m_barCornerRounding, this.m_barCornerRounding);
            this.m_barBg.graphics.endFill();
            this.m_bar.graphics.beginFill(this.m_barColor);
            this.m_bar.graphics.drawRect(this.m_barX, (this.m_barY + 1), this.m_progress * this.m_barWidth, this.m_barHeight);
            this.m_bar.graphics.endFill();
            this.m_barFlash.graphics.beginFill(this.m_barFlashColor);
            this.m_barFlash.graphics.drawRect(this.m_barX, (this.m_barY + 1), this.m_progress * this.m_barWidth, this.m_barHeight);
            this.m_barFlash.graphics.endFill();
            return;
        }//end

        public void  setProgressBarFlash (double param1 =2,double param2 =0.5,int param3 =16777215)
        {
            this.m_barFlashColor = param3;
            this.setProgress(this.m_progress);
            Array _loc_4 =new Array();
            _loc_5 = param2/2;
            _loc_6 = int(param1/param2);
            int _loc_7 =0;
            while (_loc_7 < _loc_6)
            {

                _loc_4.push(new TweenLite(this.m_barFlash, _loc_5, {alpha:1}));
                _loc_4.push(new TweenLite(this.m_barFlash, _loc_5, {alpha:0}));
                _loc_7++;
            }
            if (this.m_animTimeline)
            {
                this.m_animTimeline.kill();
                this.m_barFlash.alpha = 0;
            }
            this.m_animTimeline = new TimelineLite();
            this.m_animTimeline.appendMultiple(_loc_4, 0, TweenAlign.SEQUENCE);
            return;
        }//end

    }



