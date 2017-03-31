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
//import flash.display.*;
//import flash.utils.*;

    public class SocialBusinessGuestDialog extends HotelsGuestDialog
    {

        public  SocialBusinessGuestDialog (MechanicMapResource param1 ,Function param2 )
        {
            super(param1, param2);
            return;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            dialogView = new SocialBusinessGuestDialogView(param1, m_owner, m_upgradeCallback, m_message, m_title, m_type, m_callback, m_icon, m_iconPos, m_feedShareViralType, m_SkipCallback, m_customOk, m_relativeIcon);
            return dialogView;
        }//end

         protected Dictionary  createAssetDict ()
        {
            _loc_1 = super.createAssetDict();
            _loc_2 = this.getAsset("bonusImage");
            _loc_1.put("social_business_bonusImage", (DisplayObject) new _loc_2);
            return _loc_1;
        }//end

    }



