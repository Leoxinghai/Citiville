package Display.FreeGiftMFS;

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

import Classes.util.*;
import Display.DialogUI.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;

    public class FreeGiftMFSDialog extends GenericDialog
    {
        protected String m_chosenGiftName ;
        protected Array m_recipients ;
        public static Dictionary assetDict ;

        public  FreeGiftMFSDialog (String param1 ,Array param2 )
        {
            this.m_chosenGiftName = param1;
            this.m_recipients = param2;
            super("", "FreeGiftMFS");
            return;
        }//end  

         protected Array  getAssetDependencies ()
        {
            return .get(DelayedAssetLoader.REQUESTS2_ASSETS);
        }//end  

         protected Dictionary  createAssetDict ()
        {
            assetDict = new Dictionary();
            assetDict.put("checkbox",  m_comObject.checkbox);
            assetDict.put("checkmark",  m_comObject.checkmark);
            assetDict.put("dialogBack",  m_comObject.dialogBack);
            assetDict.put("friendPanel",  m_comObject.friendPanel);
            assetDict.put("giftWindow",  m_comObject.giftWindow);
            assetDict.put("mainPanel",  m_comObject.mainPanel);
            assetDict.put("meterBack",  m_comObject.meterBack);
            assetDict.put("meterBar",  m_comObject.meterBar);
            assetDict.put("photoBack",  m_comObject.photoBack);
            assetDict.put("pigeon",  m_comObject.pigeon);
            assetDict.put("samGift",  m_comObject.samGift);
            assetDict.put("speechBubble",  m_comObject.speechBubble);
            return assetDict;
        }//end  

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            return new FreeGiftMFSDialogView(param1, this.m_chosenGiftName, this.m_recipients);
        }//end  

         public void  close ()
        {
            DisplayObject _loc_1 =this ;
            boolean _loc_2 =false ;
            this.mouseChildren = false;
            this.mouseEnabled = _loc_2;
            onHide();
            if (_loc_1.parent)
            {
                _loc_1.parent.removeChild(_loc_1);
            }
            dispatchEvent(new Event(Event.CLOSE));
            this.visible = false;
            return;
        }//end  

    }



