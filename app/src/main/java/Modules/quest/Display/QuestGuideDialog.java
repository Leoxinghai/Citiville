package Modules.quest.Display;

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

import Modules.guide.ui.*;
    public class QuestGuideDialog extends WelcomeDialog
    {

        public  QuestGuideDialog (Object param1 ,boolean param2 =false )
        {
            String _loc_3 =null ;
            String _loc_4 =null ;
            String _loc_5 =null ;
            if (param1.isEpilogueData)
            {
                _loc_3 = param1.message;
                _loc_4 = param1.title;
                _loc_5 = param1.npcEpilogueUrl;
            }
            else
            {
                _loc_3 = param1.name + "_dialog_description";
                _loc_4 = param1.name + "_dialog_title";
                _loc_5 = param1.npcCompleteUrl;
            }
            _loc_6 =Global.questManager.getLocObjects ();
            super(ZLoc.t("Quest", _loc_3, _loc_6), ZLoc.t("Quest", _loc_4, _loc_6), 0, WelcomeDialog.POS_CENTER, null, true, _loc_5);
            return;
        }//end

    }



