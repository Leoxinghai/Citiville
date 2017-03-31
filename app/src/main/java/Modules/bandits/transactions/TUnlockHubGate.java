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

import Classes.*;
import Engine.Transactions.*;

    public class TUnlockHubGate extends Transaction
    {
        private Municipal m_station ;
        private String m_groupId ;

        public  TUnlockHubGate (Municipal param1 )
        {
            this.m_station = param1;
            this.m_groupId = Global.gameSettings().getHubGroupIdForItemName(param1.getItemName());
            return;
        }//end

         public void  perform ()
        {
            signedCall("PreyService.unlockHubGate", this.m_groupId, this.m_station.getId());
            return;
        }//end

    }



