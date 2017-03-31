package Classes.Managers;

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
import Classes.effects.*;
import Events.*;

//import flash.events.*;

    public class StagePickManager extends EventDispatcher
    {
        protected Vector<StagePickEvent> m_stateModifiers;

        public  StagePickManager (IEventDispatcher param1)
        {
            this.m_stateModifiers = new Vector<StagePickEvent>();
            super(param1);
            return;
        }//end

        public void  hideAllPicks ()
        {
            StagePickEvent _loc_1 =new StagePickEvent(StagePickEvent.HIDE );
            dispatchEvent(_loc_1);
            this.m_stateModifiers.splice(0, this.m_stateModifiers.length());
            this.m_stateModifiers.push(_loc_1);
            return;
        }//end

        public void  showAllPicks ()
        {
            StagePickEvent _loc_1 =new StagePickEvent(StagePickEvent.SHOW );
            dispatchEvent(_loc_1);
            this.m_stateModifiers.splice(0, this.m_stateModifiers.length());
            return;
        }//end

        public void  hidePicksByType (String param1 )
        {
            StagePickEvent _loc_2 =new StagePickEvent(StagePickEvent.HIDE ,param1 );
            dispatchEvent(_loc_2);
            this.m_stateModifiers.push(_loc_2);
            return;
        }//end

        public void  showPicksByType (String param1 )
        {
            StagePickEvent _loc_2 =new StagePickEvent(StagePickEvent.SHOW ,param1 );
            dispatchEvent(_loc_2);
            this.m_stateModifiers.push(_loc_2);
            return;
        }//end

        public boolean  isPickTypeVisible (String param1 )
        {
            StagePickEvent _loc_3 =null ;
            boolean _loc_2 =true ;
            for(int i0 = 0; i0 < this.m_stateModifiers.size(); i0++)
            {
            		_loc_3 = this.m_stateModifiers.get(i0);

                if (_loc_3.pickName == param1 || _loc_3.allPicks)
                {
                    if (_loc_3.type == StagePickEvent.HIDE)
                    {
                        _loc_2 = false;
                        continue;
                    }
                    _loc_2 = true;
                }
            }
            return _loc_2;
        }//end

        public void  attachStagePick (MapResource param1 ,String param2 ,boolean param3 =true )
        {
            _loc_4 = (StagePickEffect)MapResourceEffectFactory.createEffect(param1,EffectType.STAGE_PICK)
            if (param1.stagePickEffect && param1.stagePickEffect.visible)
            {
                _loc_4.pickOffset = param1.getToolTipFloatOffset() + 5;
            }
            _loc_4.drawBackground = param3;
            _loc_4.setPickType(param2);
            _loc_4.queuedFloat();
            param1.addAnimatedEffectFromInstance(_loc_4);
            return;
        }//end

        public void  detachStagePick (MapResource param1 )
        {
            _loc_2 = param1.getAnimatedEffectByClass(StagePickEffect )as StagePickEffect ;
            if (_loc_2)
            {
                _loc_2.stopFloat();
                param1.removeAnimatedEffectByClass(StagePickEffect);
                param1.refreshArrow();
            }
            return;
        }//end

        public void  attachStagePicksBatch (String param1 ,Function param2 ,Array param3 =null ,boolean param4 =true )
        {
            MapResource _loc_5 =null ;
            for(int i0 = 0; i0 < Global.world.getObjectsByPredicate(param2).size(); i0++)
            {
            		_loc_5 = Global.world.getObjectsByPredicate(param2).get(i0);

                if (param3 && param3.indexOf(_loc_5.getId()) != -1)
                {
                    continue;
                }
                this.attachStagePick(_loc_5, param1, param4);
            }
            return;
        }//end

        public void  detachStagePicksBatch (Function param1)
        {
            MapResource _loc_2 =null ;
            for(int i0 = 0; i0 < Global.world.getObjectsByPredicate(param1).size(); i0++)
            {
            		_loc_2 = Global.world.getObjectsByPredicate(param1).get(i0);

                this.detachStagePick(_loc_2);
            }
            return;
        }//end

    }


