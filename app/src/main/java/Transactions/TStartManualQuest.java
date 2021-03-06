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

import Modules.quest.Managers.*;
    public class TStartManualQuest extends TFarmTransaction
    {
        protected String m_questName ;

        public  TStartManualQuest (String param1 )
        {
            this.addQuestToHeader(param1);
            this.m_questName = param1;
            return;
        }//end

         public void  perform ()
        {
            if (GameQuestUtility.isQuestValid(this.m_questName))
            {
                signedCall("QuestService.requestManualQuest", this.m_questName);
            }
            return;
        }//end

         protected void  onComplete (Object param1 )
        {
            _loc_2 = param1&& param1.get("questStarted");
            Global.questManager.manualQuestStarted_FinalSetup(this.m_questName, _loc_2);
            return;
        }//end

    }



