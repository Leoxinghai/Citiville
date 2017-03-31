package Display.DialogUI;

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
//import flash.events.*;
//import flash.utils.*;
import org.aswing.*;

    public class CustomDialogView extends GenericDialogView
    {
        protected  int BUTTON_SPACING =15;
        protected Array m_buttonInfo ;

        public  CustomDialogView (Dictionary param1 ,String param2 ="",String param3 ="",String param4 ="",int param5 =0,Array param6 =null )
        {
            this.m_buttonInfo = param6;
            super(param1, param2, param3, TYPE_OK, null, param4, param5, "", null, "");
            return;
        }//end  

         protected JPanel  createButtonPanel ()
        {
            Object _loc_2 =null ;
            CustomButton _loc_3 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_1.append(ASwingHelper.horizontalStrut(this.BUTTON_SPACING));
            for(int i0 = 0; i0 < this.m_buttonInfo.size(); i0++) 
            {
            	_loc_2 = this.m_buttonInfo.get(i0);
                
                _loc_3 = new CustomButton(_loc_2.label, null, "GreenButtonUI");
                _loc_3.addEventListener(MouseEvent.CLICK, _loc_2.callback, false, 0, true);
                _loc_1.appendAll(_loc_3, ASwingHelper.horizontalStrut(this.BUTTON_SPACING));
            }
            return _loc_1;
        }//end  

    }




