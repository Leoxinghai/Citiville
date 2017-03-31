package Classes.bonus;

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
import Modules.goals.*;
import Modules.goals.mastery.*;

    public class MasteryBonus extends HarvestBonus
    {
        private String m_featureName ;

        public  MasteryBonus (XML param1 )
        {
            super(param1);
            this.m_featureName = param1.@featureName;
            return;
        }//end

         public void  init (MapResource param1 )
        {
            MasteryGoal _loc_3 =null ;
            Item _loc_4 =null ;
            int _loc_2 =0;
            if (param1 !=null)
            {
                _loc_3 =(MasteryGoal) Global.goalManager.getGoal(GoalManager.GOAL_MASTERY);
                _loc_4 = param1.harvestingDefinition;
                if (_loc_3 && Global.player.isEligibleForMastery(param1.harvestingDefinition) && _loc_4)
                {
                    _loc_2 = _loc_3.getBonusMultiplier(_loc_4.name);
                }
            }
            m_percentModifier = initialPercentModifier + _loc_2;
            return;
        }//end

         public double  maxPercentModifier ()
        {
            return m_percentModifier;
        }//end

    }



