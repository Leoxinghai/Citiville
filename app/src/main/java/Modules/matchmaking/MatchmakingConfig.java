package Modules.matchmaking;

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

import Classes.util.*;
    public class MatchmakingConfig
    {
        private int m_sendThrottle ;
        private int m_helpThrottle ;
        private int m_giftThrottle ;

        public  MatchmakingConfig (XML param1 )
        {
            this.m_sendThrottle = parseInt(param1.nonSNFriendSendThrottle);
            this.m_helpThrottle = parseInt(param1.nonSNFriendHelpThrottle);
            this.m_giftThrottle = parseInt(param1.nonSNFriendGiftThrottle);
            return;
        }//end

        public int  sendThrottle ()
        {
            return this.m_sendThrottle * DateUtil.SECONDS_PER_HOUR;
        }//end

        public int  helpThrottle ()
        {
            return this.m_helpThrottle * DateUtil.SECONDS_PER_HOUR;
        }//end

        public int  giftThrottle ()
        {
            return this.m_giftThrottle * DateUtil.SECONDS_PER_HOUR;
        }//end

    }



