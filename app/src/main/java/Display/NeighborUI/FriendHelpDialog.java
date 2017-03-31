package Display.NeighborUI;

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
//import flash.events.*;
//import flash.utils.*;

    public class FriendHelpDialog extends GenericDialog
    {
        private Player m_friend ;
        private String m_acceptText ;

        public  FriendHelpDialog (Player param1 ,String param2 ,String param3 ,String param4 ="IdleGoHelpOutNow_button",Function param5 =null ,boolean param6 =true )
        {
            this.m_friend = param1;
            super(param2, param3, 0, param5, param3);
            m_message = param2;
            m_title = param3;
            this.m_acceptText = param4;
            return;
        }//end  

         protected void  loadAssets ()
        {
            Global.delayedAssets.get("assets/dialogs/NeighborVisitAssets.swf", makeAssets);
            return;
        }//end  

         protected void  onAssetsLoaded (Event event =null )
        {
            Dictionary _loc_2 =new Dictionary ();
            _loc_2.put("closeBtnDown",  m_comObject.closeBtnDown);
            _loc_2.put("closeBtnOver",  m_comObject.closeBtnOver);
            _loc_2.put("closeBtnUp",  m_comObject.closeBtnUp);
            _loc_2.put("dialogBG",  m_comObject.dialogBG);
            _loc_2.put("friend",  this.m_friend);
            if (this.m_acceptText == null)
            {
                this.m_acceptText = "IdleGoHelpOutNow_button";
            }
            m_jpanel = new FriendHelpDialogView(_loc_2, m_message, m_title, m_type, this.m_acceptText, m_callback);
            finalizeAndShow();
            return;
        }//end  

         protected Dictionary  createAssetDict ()
        {
            Dictionary _loc_1 =new Dictionary ();
            _loc_1.put("closeBtnDown",  m_comObject.closeBtnDown);
            _loc_1.put("closeBtnOver",  m_comObject.closeBtnOver);
            _loc_1.put("closeBtnUp",  m_comObject.closeBtnUp);
            _loc_1.put("dialogBG",  m_comObject.dialogBG);
            _loc_1.put("friend",  this.m_friend);
            return _loc_1;
        }//end  

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            FriendHelpDialogView _loc_2 =new FriendHelpDialogView(param1 ,m_message ,m_title ,m_type ,this.m_acceptText ,m_callback );
            return _loc_2;
        }//end  

    }



