package Modules.bandits.transactions;

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
import Modules.bandits.*;

    public class TProcessHubWorkers extends Transaction
    {
        protected String m_groupId ="";

        public  TProcessHubWorkers (String param1 )
        {
            this.m_groupId = param1;
            return;
        }//end  

         public void  perform ()
        {
            signedCall("PreyService.processHubWorkers", this.m_groupId);
            return;
        }//end  

         protected void  onComplete (Object param1 )
        {
            Global.world.citySim.preyManager.getWorkerManagerByGroup(this.m_groupId).preserveAndLoadObject(param1.frontendData);
            if (!Global.world.citySim.preyManager.areHubsLocked(this.m_groupId))
            {
                PreyManager.spawnExistingHunters(false, this.m_groupId);
            }
            return;
        }//end  

    }



