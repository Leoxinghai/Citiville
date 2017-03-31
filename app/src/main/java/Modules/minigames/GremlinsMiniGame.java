package Modules.minigames;

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
import Classes.actions.*;
import Modules.bandits.*;
import com.zynga.skelly.util.*;

    public class GremlinsMiniGame extends MiniGame
    {
        public Array gremArray ;
        public static  String GREMLINS_MINI_GAME ="GremlinsMiniGame";
        public static  int TIME_LIMIT =60000;
        public static  String NPC_NAME ="NPC_monster_ghost";
        public static  int NUM_GREMLINS =15;

        public  GremlinsMiniGame ()
        {
            return;
        }//end

         protected String  name ()
        {
            return GREMLINS_MINI_GAME;
        }//end

         protected int  timeLimit ()
        {
            return TIME_LIMIT;
        }//end

         public void  init ()
        {
            super.init();
            Global.hud.updateMiniGame();
            this.gremArray = new Array();
            int _loc_1 =0;
            while (_loc_1 < NUM_GREMLINS)
            {

                this.spawnGremlin();
                _loc_1++;
            }
            return;
        }//end

         public void  update (double param1 )
        {
            super.update(param1);
            return;
        }//end

        public void  updateScore ()
        {
            _loc_2 = m_score+1;
            m_score = _loc_2;
            Global.hud.updateMiniGame();
            if (m_score >= NUM_GREMLINS)
            {
                complete();
            }
            return;
        }//end

        public void  spawnGremlin ()
        {
            NPC bandit ;
            road = Global.world.citySim.roadManager.findRandomRoad();
            bandit = Global.world.citySim.npcManager.createWalkerByNameAtPosition(NPC_NAME, road.getHotspot(), true);
            bandit.actionSelection = new BanditActionSelection(bandit);
            this.gremArray.push(bandit);
            bandit .playActionCallback =Curry .curry (void  (NPC param1 )
            {
                npc = param1;
                if (m_isActive && npc.alpha == 1)
                {
                    updateScore();
                    npc.alpha = 0.99;
                }
                npc.getStateMachine().removeAllStates();
                bandit .getStateMachine ().addActions (new ActionTween (npc ,ActionTween .TO ,0.5,{0alpha }),new ActionFn (npc ,Curry .curry (void  (NPC param1 )
                {
                    Global.world.citySim.npcManager.removeWalker(param1);
                    return;
                }//end
                , npc)));
                return;
            }//end
            , bandit);
            return;
        }//end

         public void  onEnd ()
        {
            NPC npc ;
            int _loc_2 =0;
            _loc_3 = this.gremArray ;
            for(int i0 = 0; i0 < _loc_3.size(); i0++) 
            {
            		npc = _loc_3.get(i0);


                npc.getStateMachine().removeAllStates();
                npc .getStateMachine ().addActions (new ActionTween (npc ,ActionTween .TO ,0.5,{0alpha }),new ActionFn (npc ,Curry .curry (void  (NPC param1 )
            {
                Global.world.citySim.npcManager.removeWalker(param1);
                return;
            }//end
            , npc)));
            }
            super.onEnd();
            Global.hud.updateMiniGame();
            return;
        }//end

        public static boolean  canStartMiniGame ()
        {
            return Global.world.citySim.roadManager.hasWalkableRoad();
        }//end

    }



