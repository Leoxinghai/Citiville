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
import Modules.guide.ui.*;
//import flash.net.*;
import org.aswing.event.*;

    public class QuestXPromoEpilogueDialog extends WelcomeDialog
    {
        private String m_callBackURL ;

        public  QuestXPromoEpilogueDialog (Object param1 )
        {
            this.m_callBackURL = param1.url;
            super(ZLoc.t("Quest", param1.message), ZLoc.t("Quest", param1.title), GenericDialogView.TYPE_SENDGIFTS, WelcomeDialog.POS_CENTER, null, true, param1.npcEpilogueUrl, "", null);
            return;
        }//end  

         protected void  onPanelClick (AWEvent event )
        {
            navigateToURL(new URLRequest(this.m_callBackURL), "_top");
            super.onPanelClick(event);
            return;
        }//end  

    }



