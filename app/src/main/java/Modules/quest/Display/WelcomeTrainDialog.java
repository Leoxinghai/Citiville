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

import org.aswing.event.*;
    public class WelcomeTrainDialog extends QuestGuideDialog
    {
        protected String m_customPickUrl ;
        protected String m_questName ;

        public  WelcomeTrainDialog (Object param1 ,boolean param2 =false )
        {
            super(param1, param2);
            this.m_customPickUrl = param1.customPickUrl;
            this.m_questName = param1.name;
            return;
        }//end

         protected void  onPanelClick (AWEvent event )
        {
            super.onPanelClick(event);
            Global.world.citySim.trainManager.purchaseWelcomeTrain();
            return;
        }//end

    }



