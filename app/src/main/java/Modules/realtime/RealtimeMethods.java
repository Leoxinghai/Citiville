package Modules.realtime;

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
import Classes.util.*;
import Modules.stats.experiments.*;
import Transactions.*;

    public class RealtimeMethods
    {

        public  RealtimeMethods ()
        {
            return;
        }//end

        public static Friend  getFriendByZid (String param1 )
        {
            Friend _loc_2 =null ;
            for(int i0 = 0; i0 < Global.friendbar.size(); i0++) 
            {
            		_loc_2 = Global.friendbar.get(i0);

                if (_loc_2.uid == param1)
                {
                    return _loc_2;
                }
            }
            return null;
        }//end

        public static void  showAction (String param1 ,String param2 ,String param3 =null )
        {
            return;
        }//end

        public static void  showNeighborVisit (String param1 )
        {
            Player _loc_2 =null ;
            String _loc_3 =null ;
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_RT_NEIGHBOR_VISITS) == ExperimentDefinitions.RT_NEIGHBOR_VISITS)
            {
                _loc_2 = Global.player.findFriendById(param1);
                if (_loc_2)
                {
                    _loc_3 = ZLoc.t("Neighbors", "RtNeighborVisit", {user:ZLoc.tn(_loc_2.firstName, _loc_2.gender)});
                    GameTransactionManager.addTransaction(new TGetOrders(_loc_3));
                }
            }
            return;
        }//end

    }



