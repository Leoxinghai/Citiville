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

import Display.DialogUI.*;
    public class QuestPictureDialog extends GenericPictureDialog
    {

        public  QuestPictureDialog (Object param1 )
        {
            Global.player.setAllowQuests(false);
            super(ZLoc.t("Quest", param1.message), param1.title, 0, null, ZLoc.t("Quest", param1.title), "", true, 0, param1.button, param1.imageUrl);
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



