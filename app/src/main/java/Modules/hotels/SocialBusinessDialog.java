package Modules.hotels;

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
import Display.DialogUI.*;
import Engine.Managers.*;
//import flash.events.*;
//import flash.utils.*;

    public class SocialBusinessDialog extends HotelsDialog
    {

        public  SocialBusinessDialog (MechanicMapResource param1 ,Item param2 ,int param3 =0,String param4 ="",Function param5 =null )
        {
            super(param1, param2, param3, param4, param5);
            return;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            SocialBusinessDialogView dialogView ;
            assetDict = param1;
            assetDict.put("spawner",  m_owner);
            dialogView = new SocialBusinessDialogView(assetDict, m_tab, m_message, m_dialogTitle, m_type, m_callback, m_icon, m_iconPos, m_feedShareViralType, m_SkipCallback, m_customOk, m_relativeIcon);
            LoadingManager .load (Global .getAssetURL (INFOGRAPHIC_URL ),void  (Event event )
            {
                dialogView.setInfographic(event.target.content);
                return;
            }//end
            );
            return dialogView;
        }//end

    }



