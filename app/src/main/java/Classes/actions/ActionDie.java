package Classes.actions;

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
import com.greensock.*;

    public class ActionDie extends NPCAction
    {
        private static  double FADE_LENGTH =1;

        public  ActionDie (NPC param1 )
        {
            super(param1);
            return;
        }//end

         public void  enter ()
        {
            super.enter();
            this.tweenOut();
            return;
        }//end

         public void  reenter ()
        {
            super.reenter();
            this.tweenOut();
            return;
        }//end

        protected void  tweenOut ()
        {
            NPCAction me ;
            SeaVehicle npcSeaVehicle ;
            TourBus npcTourBus ;
            GeekSquadVan npcGeekSquadVan ;
            Cloud npcCloud ;
            me = new NPCAction();
void             TweenLite .to (m_npc ,FADE_LENGTH ,{0alpha , onComplete ()
            {
                Global.world.citySim.npcManager.removeNpc(m_npc);
                m_npc.getStateMachine().removeState(me);
                return;
            }//end
            });
            if (m_npc instanceof SeaVehicle)
            {
                npcSeaVehicle = SeaVehicle(m_npc);
                Ship.decrementShipCounter(npcSeaVehicle);
            }
            if (m_npc instanceof TourBus)
            {
                npcTourBus = TourBus(m_npc);
                Business.decrementBusCounter();
            }
            if (m_npc instanceof GeekSquadVan)
            {
                npcGeekSquadVan = GeekSquadVan(m_npc);
                Business.decrementGeekSquadVanCounter();
            }
            return;
        }//end

    }




