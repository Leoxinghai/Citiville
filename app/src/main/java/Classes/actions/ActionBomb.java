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
import Classes.effects.*;
//import flash.geom.*;
import Engine.*;
import Classes.doobers.*;
import Classes.util.*;

import com.xinghai.Debug;

    public class ActionBomb extends NPCAction
    {
        private static  double FADE_LENGTH =1;

        public  ActionBomb (NPC param1 )
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

	        Sounds.play("sound_kdbomb");
	        _loc_22 = newBombEffect(IsoMath.tilePosToPixelPos(m_npc.positionX,m_npc.positionY),EffectType.BOMBSMOKE.type,true,false,false);

                int ii =0;

                int e_length =Global.weiboManager.m_enermies.length ;
                Array tempE =new Array();

                for(;ii<e_length;ii++) {
                	WeiboPeep enermy =(WeiboPeep)Global.weiboManager.m_enermies.get(ii);
                	Debug.debug7("ActionBomb.kill " + enermy.positionX + ";" + m_npc.positionX + ";" + enermy.positionY + ";" + m_npc.positionY);
                	if(enermy != null) {
                		if( enermy.positionX - m_npc.positionX < 2 && enermy.positionX - m_npc.positionX > -2 &&  enermy.positionY - m_npc.positionY < 2 && enermy.positionY - m_npc.positionY > -2) {
                		    Debug.debug7("ActionBomb.killed ");
				    _loc_11 = ActionBomb.generateDoobers(10,0,20,Doober.DOOBER_COIN);
				    Global.world.dooberManager.createBatchDoobers(_loc_11, null, m_npc.positionX, m_npc.positionY);
                		    enermy.detach();
                		}  else {
                		    tempE.push(enermy);
                		}

                	}
                }
                Global.weiboManager.m_enermies = tempE;

                if(tempE.length == 0) {
                            Global.weiboManager.nextLevel();
                }


                return;
            }//end
            });

            return;
        }//end


        public static Array  generateDoobers (int param1 ,int param2 ,int param3 ,String param4 )
        {
            int _loc_6 =0;
            int _loc_7 =0;
            int _loc_11 =0;
            Array _loc_5 =new Array ();
            _loc_8 =Global.gameSettings().getInt("trainAmountPerDoober",3);
            _loc_9 =Global.gameSettings().getInt("trainMaxDoobers",6);
            int _loc_10 =0;
            if (param1 > 0)
            {
                _loc_6 = Math.floor(Math.min(param1 / _loc_8, _loc_9));
                _loc_7 = Math.floor(param1 / _loc_6);
                _loc_11 = param1 % _loc_6;
                _loc_10 = 0;
                while (_loc_10 < _loc_6)
                {

                    _loc_5.push(new Array(Global.gameSettings().getDooberFromType(Doober.DOOBER_COIN, _loc_7), _loc_7));
                    _loc_10++;
                }
                if (_loc_11 > 0)
                {
                    _loc_5.push(new Array(Global.gameSettings().getDooberFromType(Doober.DOOBER_COIN, _loc_11), _loc_11));
                }
            }
            if (param2 > 0)
            {
                _loc_6 = Math.floor(Math.min(param2 / _loc_8, _loc_9));
                _loc_7 = Math.floor(param2 / _loc_6);
                _loc_11 = param2 % _loc_6;
                _loc_10 = 0;
                while (_loc_10 < _loc_6)
                {

                    _loc_5.push(new Array(Global.gameSettings().getDooberFromType(Doober.DOOBER_XP, _loc_7), _loc_7));
                    _loc_10++;
                }
                if (_loc_11 > 0)
                {
                    _loc_5.push(new Array(Global.gameSettings().getDooberFromType(Doober.DOOBER_XP, _loc_11), _loc_11));
                }
            }
            if (param3 > 0)
            {
                _loc_6 = Math.floor(Math.min(param3 / _loc_8, _loc_9));
                _loc_7 = Math.floor(param3 / _loc_6);
                _loc_11 = param3 % _loc_6;
                _loc_10 = 0;
                while (_loc_10 < _loc_6)
                {

                    _loc_5.push(new Array(Global.gameSettings().getDooberFromType(param4, _loc_7), _loc_7));
                    _loc_10++;
                }
                if (_loc_11 > 0)
                {
                    _loc_5.push(new Array(Global.gameSettings().getDooberFromType(param4, _loc_11), _loc_11));
                }
            }
            return _loc_5;
        }//end


    }



