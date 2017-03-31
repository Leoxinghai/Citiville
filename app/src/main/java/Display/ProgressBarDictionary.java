package Display;

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
//import flash.display.*;
//import flash.utils.*;
import org.aswing.*;

    public class ProgressBarDictionary extends JPanel implements IProgressBar
    {
        protected JPanel m_progressBarPane ;
        protected JPanel m_progressBarBGPanel ;
        protected double m_width ;
        protected double m_minWidth ;
        protected Dictionary m_assetDict ;

        public  ProgressBarDictionary (Dictionary param1 ,double param2 ,double param3 )
        {
            this.m_assetDict = param1;
            this.m_width = param2;
            this.m_minWidth = param3;
            super(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 0, SoftBoxLayout.LEFT));
            _loc_4 = this.createProgressPanel ();
            this.append(_loc_4);
            this.setPreferredWidth(_loc_4.getPreferredWidth());
            this.setPreferredHeight(_loc_4.getPreferredHeight());
            ASwingHelper.prepare(this);
            return;
        }//end

        protected JPanel  createProgressPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_2 =(DisplayObject) new this.m_assetDict.get( "upgradeBarBG");
            this.m_progressBarBGPanel = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT);
            ASwingHelper.setBackground(this.m_progressBarBGPanel, _loc_2);
            this.m_progressBarBGPanel.setPreferredWidth(this.m_width);
            this.m_progressBarBGPanel.setPreferredHeight(_loc_2.height);
            _loc_3 =(DisplayObject) new this.m_assetDict.get( "upgradeBar");
            this.m_progressBarPane = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT);
            ASwingHelper.setBackground(this.m_progressBarPane, _loc_3);
            this.m_progressBarPane.setPreferredHeight(_loc_3.height);
            ASwingHelper.setEasyBorder(this.m_progressBarPane, 0, 0, 0, 0);
            this.m_progressBarBGPanel.append(this.m_progressBarPane);
            this.setProgress(0);
            _loc_1.append(ASwingHelper.verticalStrut(5));
            _loc_1.append(this.m_progressBarBGPanel);
            _loc_1.append(ASwingHelper.verticalStrut(5));
            _loc_1.setPreferredWidth(this.m_width);
            return _loc_1;
        }//end

        public void  setProgressRatio (double param1 ,double param2 )
        {
            double _loc_3 =0;
            if (param2 > 0)
            {
                _loc_3 = MathUtil.clamp(param1 / param2, 0, 1);
            }
            this.setProgress(_loc_3);
            return;
        }//end

        public void  setProgress (double param1 )
        {
            _loc_2 = param1==0? (0) : (this.m_minWidth + Math.ceil(param1 * (this.m_progressBarBGPanel.getPreferredWidth() - this.m_minWidth)));
            this.m_progressBarPane.setPreferredWidth(_loc_2);
            this.m_progressBarPane.setMinimumWidth(_loc_2);
            this.m_progressBarPane.setMaximumWidth(_loc_2);
            ASwingHelper.prepare(this.m_progressBarPane);
            ASwingHelper.prepare(this);
            return;
        }//end

        public void  setBgAlpha (double param1 )
        {
            this.m_progressBarBGPanel.setAlpha(param1);
            ASwingHelper.prepare(this);
            return;
        }//end

        public void  setBgColor (int param1 )
        {
            return;
        }//end

    }


