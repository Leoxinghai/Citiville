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

//import flash.utils.*;
    public class PermitBuyShareDialog extends GenericDialog
    {
        protected int m_PermitsRequired ;
        protected int m_PermitOverrideCost ;
        protected String m_Message ;
        protected String m_Title ;
        protected String m_DialogIcon ;

        public  PermitBuyShareDialog (int param1 =0,int param2 =0,Function param3 =null ,Function param4 =null ,String param5 ="PermitDialog_message",String param6 ="PermitDialog",String param7 ="assets/hud/buildables/permits.png",int param8 =12)
        {
            this.m_PermitsRequired = param1;
            this.m_PermitOverrideCost = param2;
            this.m_Message = param5;
            this.m_Title = param6;
            this.m_DialogIcon = param7;
            m_type = param8;
            super(ZLoc.t("Dialogs", this.m_Message, {permitNumber:this.m_PermitsRequired.toString()}), this.m_Title, param8, param4, this.m_Title, this.m_DialogIcon, true, 0, "", param3);
            return;
        }//end  

         public boolean  isLockable ()
        {
            return true;
        }//end  

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            PermitBuyShareDialogView _loc_2 =new PermitBuyShareDialogView(param1 ,m_message ,m_dialogTitle ,m_type ,m_callback ,m_icon ,0,this.m_PermitsRequired ,this.m_PermitOverrideCost );
            return _loc_2;
        }//end  

    }


