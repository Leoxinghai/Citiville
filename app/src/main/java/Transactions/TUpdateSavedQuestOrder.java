package Transactions;

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

import Engine.Transactions.*;
    public class TUpdateSavedQuestOrder extends Transaction
    {
        private Array m_savedQuestNames ;
        private Array m_questManagerQuests ;
        private Array m_hiddenQuests ;

        public  TUpdateSavedQuestOrder (Array param1 ,Array param2 ,Array param3 )
        {
            this.m_savedQuestNames = param1;
            this.m_questManagerQuests = param2;
            this.m_hiddenQuests = param3;
            return;
        }//end  

         public void  perform ()
        {
            signedCall("UserService.updateSavedQuestOrder", this.m_savedQuestNames, this.m_questManagerQuests, this.m_hiddenQuests);
            return;
        }//end  

    }



