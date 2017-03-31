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

import Engine.Managers.*;
import root.GlobalEngine;
import root.ZLoc;

public class FriendRewardDialog extends GenericPopup
    {

        public  FriendRewardDialog ()
        {
            String _loc_1 = (String)(GlobalEngine.getFlashVar("frMsg"));
            double _loc_2 =(double)(GlobalEngine.getFlashVar("frHost"));
            String _loc_3 = (String)(GlobalEngine.getFlashVar("frParams"));
            String [] _loc_4 = _loc_3.split(":");
            String _loc_5 = ZLoc.t(_loc_1,null,_loc_4);
            StatsManager.milestone("achieve_md", 0);
            super(_loc_5, TYPE_OK,null,"assets/dialogs/TI_GenericPopUp.swf");
            return;
        }//end

    }



