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

import Classes.*;
import Display.aswingui.*;
//import flash.display.*;
//import flash.events.*;
import org.aswing.*;

    public class CheckBoxComponent extends JPanel
    {
        protected Function m_onChange ;
        protected AssetPane m_check ;
        protected JLabel m_label ;

        public  CheckBoxComponent (boolean param1 =true ,String param2 ,Function param3 =null ,DisplayObject param4 =null ,DisplayObject param5 =null )
        {
            this.m_onChange = param3;
            super(new FlowLayout(FlowLayout.CENTER, 0, 0, false));
            this.init(param1, param2, param4, param5);
            return;
        }//end

        public boolean  isChecked ()
        {
            return this.m_check.visible;
        }//end

        protected void  init (boolean param1 =true ,String param2 ,DisplayObject param3 =null ,DisplayObject param4 =null )
        {
            _loc_5 = param4;
            _loc_6 = param3;
            _loc_7 = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER);
            ASwingHelper.setSizedBackground(_loc_7, _loc_5, new Insets(0, 3, 2, 3));
            this.m_check = new AssetPane(_loc_6);
            this.m_check.visible = param1;
            _loc_7.append(this.m_check);
            this.append(_loc_7);
            if (param2)
            {
                this.m_label = ASwingHelper.makeLabel(param2, EmbeddedArt.defaultFontNameBold, 16, EmbeddedArt.brownTextColor);
                this.append(this.m_label);
            }
            this.addEventListener(MouseEvent.CLICK, this.toggleCheck, false, 0, true);
            return;
        }//end

        protected void  toggleCheck (MouseEvent event )
        {
            if (this.m_check.visible)
            {
                this.m_check.visible = false;
            }
            else
            {
                this.m_check.visible = true;
            }
            this.m_onChange(this.m_check.visible);
            return;
        }//end

        public void  setChecked (boolean param1 )
        {
            this.m_check.visible = param1;
            this.m_onChange(this.m_check.visible);
            return;
        }//end

        public void  setLabel (String param1 )
        {
            this.m_label.setText(param1);
            ASwingHelper.prepare(this);
            return;
        }//end

        public void  setLabelColor (int param1 =7356965)
        {
            this.m_label.setForeground(new ASColor(param1));
            ASwingHelper.prepare(this);
            return;
        }//end

    }



