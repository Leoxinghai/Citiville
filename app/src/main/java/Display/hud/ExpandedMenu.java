package Display.hud;

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
import Display.aswingui.*;
import Engine.Helpers.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
import org.aswing.*;
import org.aswing.border.*;

    public class ExpandedMenu extends JWindow
    {
        protected  int TOOL_PIXEL_OFFSET =66;
        protected  GlowFilter glow_btn =new GlowFilter(16776960,1,2,2,10);
        protected  GlowFilter glow_lbl =new GlowFilter(10066176,1,2,2,10);
        protected DisplayObject m_bg ;
        protected JPanel m_content ;
        protected int m_contentCount ;

        public  ExpandedMenu (Object param1 ,boolean param2 =false )
        {


            super(param1, param2);
            this.makeContent();
            this.setContentPane(this.m_content);
            ASwingHelper.prepare(this);
            addEventListener(MouseEvent.MOUSE_OVER, this.onMaskSlotLadder);
            addEventListener(MouseEvent.MOUSE_MOVE, this.onMaskSlotLadder);
            addEventListener(MouseEvent.MOUSE_OUT, this.onMaskSlotLadder);
            return;
        }//end

        public void  onMaskSlotLadder (MouseEvent event )
        {
            event.stopImmediatePropagation();
            event.stopPropagation();
            return;
        }//end

        protected void  makeButton (Class param1 ,JButton param2 ,JLabel param3 =null )
        {
            double _loc_7 =0;
            DisplayObject _loc_4 =(DisplayObject)new param1;
            DisplayObject _loc_5 =(DisplayObject)new param1;
            (new (DisplayObject)param1).filters = .get(this.glow_btn);
            SimpleButton _loc_6 =new SimpleButton(_loc_4 ,_loc_5 ,_loc_4 ,_loc_4 );
            param2.wrapSimpleButton(_loc_6);
            if (param3)
            {
                _loc_7 = TextFieldUtil.getLocaleFontSize(12, 12, [{locale:"ja", size:9}]);
                param3.setFont(ASwingHelper.getBoldFont(_loc_7));
                param3.setForeground(ASwingHelper.getWhite());
                param3.setHeight(10);
                param3.setBorder(new EmptyBorder(null, null));
                this.m_content.appendAll(param2, param3);
            }
            else
            {
                this.m_content.appendAll(param2);
            }
            this.m_contentCount++;
            return;
        }//end

        protected void  attachButtonHandlers (JButton param1 ,Function param2 ,Function param3 =null ,Function param4 =null )
        {
            param1.addActionListener(param2, 0, true);
            if (param3 != null)
            {
                param1.addEventListener(MouseEvent.MOUSE_OVER, param3, false, 0, true);
            }
            if (param4 != null)
            {
                param1.addEventListener(MouseEvent.MOUSE_OUT, param4, false, 0, true);
            }
            return;
        }//end

        protected void  makeContent ()
        {
            return;
        }//end

        public Vector2  getDesiredPosition (Catalog param1)
        {
            if (param1 !=null)
            {
                return param1.getExpandedMainMenuPosition();
            }
            return new Vector2(635, 474 - this.TOOL_PIXEL_OFFSET * this.m_contentCount);
        }//end

    }


