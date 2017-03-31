package Classes.sim;

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
import Engine.Helpers.*;
import Modules.stats.experiments.*;
import Transactions.*;

    public class NotificationStateObserver implements IPlayerStateObserver, IGameWorldStateObserver, IGameWorldUpdateObserver, IPopulationStateObserver
    {
        private boolean m_monsterBanditsQuestStarted =false ;
        private int m_lastObservedEnergy =0;

        public  NotificationStateObserver (GameWorld param1 )
        {
            param1.addObserver(this);
            Global.player.addObserver(this);
            return;
        }//end

        public void  levelChanged (int param1 )
        {
            return;
        }//end

        public void  xpChanged (int param1 )
        {
            return;
        }//end

        public void  commodityChanged (int param1 ,String param2 )
        {
            if (param2 == "goods")
            {
                if (param1 <= 10)
                {
                    Global.ui.showTickerMessage(ZLoc.t("Main", "TickerLowGoods"));
                }
            }
            return;
        }//end

        public void  energyChanged (double param1 )
        {
            _loc_2 =Global.gameSettings().getInt("energyWarningAmount",10);
            if (param1 <= Global.gameSettings().getInt("energyWarningAmount", 10))
            {
                Global.ui.showTickerMessage(ZLoc.t("Main", "TickerLowEnergy"));
            }
            _loc_3 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_MONSTERBANDITS );
            if (!this.m_monsterBanditsQuestStarted && param1 < this.m_lastObservedEnergy && _loc_3 == ExperimentDefinitions.MONSTERBANDITS_ENABLED)
            {
                this.m_monsterBanditsQuestStarted = true;
                GameTransactionManager.addTransaction(new TStartManualQuest("qm_monsterbanditsminigame"), true);
            }
            if (!isNaN(param1))
            {
                this.m_lastObservedEnergy = param1;
            }
            return;
        }//end

        public void  goldChanged (int param1 )
        {
            return;
        }//end

        public void  cashChanged (int param1 )
        {
            return;
        }//end

        public void  initialize ()
        {
            return;
        }//end

        public void  cleanUp ()
        {
            return;
        }//end

        public void  onGameLoaded (Object param1 )
        {
            return;
        }//end

        public void  onResourceChange (MapResource param1 ,Vector3 param2 ,Vector3 param3 )
        {
            return;
        }//end

        public void  update (double param1 )
        {
            return;
        }//end

        public void  onPopulationInit (int param1 ,int param2 ,int param3 )
        {
            return;
        }//end

        public void  onPopulationChanged (int param1 ,int param2 ,int param3 ,int param4 )
        {
            return;
        }//end

        public void  onPotentialPopulationChanged (int param1 ,int param2 )
        {
            return;
        }//end

        public void  onPopulationCapChanged (int param1 )
        {
            return;
        }//end

    }


