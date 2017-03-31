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
import Classes.util.*;
import Display.DialogUI.*;
//import flash.events.*;
//import flash.utils.*;

    public class MutualFriendInviteDialog extends GenericDialog
    {
        private Player m_friend ;
        private Array m_portraits ;

        public  MutualFriendInviteDialog (Player param1 ,Array param2 )
        {
            this.m_friend = param1;
            this.m_portraits = param2;
            super("");
            return;
        }//end  

         protected void  loadAssets ()
        {
            Global.delayedAssets.get(DelayedAssetLoader.GENERIC_DIALOG_ASSETS, makeAssets);
            return;
        }//end  

         protected void  onAssetsLoaded (Event event =null )
        {
            Dictionary _loc_2 =new Dictionary ();
            _loc_2.put("dialogBG",  m_comObject.dialog_bg);
            _loc_2.put("friend",  this.m_friend);
            _loc_2.put("hrule",  m_comObject.neighborHRule);
            _loc_2.put("sam",  m_comObject.neighborSam);
            _loc_2.put("portraits",  this.m_portraits);
            m_jpanel = new MutualFriendInviteDialogView(_loc_2);
            finalizeAndShow();
            return;
        }//end  

    }



