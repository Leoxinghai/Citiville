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
//import flash.events.*;

    public class QuestInviteFriendsGuideDialog extends InviteFriendsGuideDialog
    {

        public  QuestInviteFriendsGuideDialog (Object param1 )
        {
            Global.player.setAllowQuests(false);
            super(ZLoc.t("Quest", param1.message), ZLoc.t("Quest", param1.title), 0, null, true, param1.npcEpilogueUrl, "", null);
            return;
        }//end  

        public void  onSkipClicked (Event event )
        {
            return;
        }//end  

         public void  close ()
        {
            Global.player.setAllowQuests(true);
            Global.questManager.refreshActiveIconQuests();
            super.close();
            return;
        }//end  

    }



