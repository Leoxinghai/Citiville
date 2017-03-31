package Mechanics.ClientDisplayMechanics;

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
import Engine.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
//import flash.events.*;
//import flash.geom.*;
//import flash.utils.*;
import root.GlobalEngine;
import validation.*;

    public class EffectRenderer implements IMapResourceEffectMechanic, IValidationMechanic
    {
        protected  int VALIDATION_PERIOD =8000;
        protected  int VALIDATION_STAGGER_MAX =2000;
        protected int m_validationStagger ;
        protected int m_lastValidationCheck ;
        protected boolean m_lastValidationState ;
        protected MechanicMapResource m_owner ;
        protected String m_type ;
        protected Dictionary m_effectTypes ;
        protected GenericValidationScript m_validation ;
        protected int m_currentDirection ;
        protected MapResourceEffect m_effect ;

        public  EffectRenderer ()
        {
            this.m_effectTypes = new Dictionary();
            this.m_validationStagger = MathUtil.random(0, this.VALIDATION_STAGGER_MAX);
            return;
        }//end  

        public void  updateDisplayObject (double param1 )
        {
            int _loc_2 = GlobalEngine.getTimer();
            if (_loc_2 - this.m_lastValidationCheck >= this.VALIDATION_PERIOD + this.m_validationStagger)
            {
                this.m_lastValidationCheck = _loc_2;
                this.m_lastValidationState = this.isValid();
            }
            if (this.m_lastValidationState)
            {
                if (this.m_effect)
                {
                    this.updateEffect(param1);
                }
                else
                {
                    this.initEffect();
                }
            }
            else if (this.m_effect)
            {
                this.killEffect();
            }
            return;
        }//end  

        public void  detachDisplayObject ()
        {
            if (this.m_effect)
            {
                this.killEffect();
            }
            return;
        }//end  

        public boolean  isPixelInside (Point param1 )
        {
            return false;
        }//end  

        public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            XML _loc_3 =null ;
            int _loc_4 =0;
            this.m_owner =(MechanicMapResource) param1;
            this.m_type = param2.params.get("type");
            this.m_effectTypes.put("all",  param2.params.get("effectType"));
            for(int i0 = 0; i0 < param2.rawXMLConfig.effectType.size(); i0++) 
            {
            	_loc_3 = param2.rawXMLConfig.effectType.get(i0);
                
                if (_loc_3.attribute("name").length() > 0 && _loc_3.attribute("direction").length() > 0)
                {
                    _loc_4 = Constants.get("DIRECTION_" + String(_loc_3.@direction));
                    this.m_effectTypes.get(_loc_4) = String(_loc_3.@name);
                }
            }
            this.m_validation = this.m_owner.getItem().getValidation(param2.params.get("validate"));
            return;
        }//end  

        public boolean  isVisitEnabled ()
        {
            return false;
        }//end  

        public boolean  isValid ()
        {
            return this.m_validation ? (this.m_validation.validate(this.m_owner)) : (true);
        }//end  

        public void  initEffect ()
        {
            this.m_effect = MapResourceEffectFactory.createEffect(this.m_owner, this.getEffectType());
            this.m_owner.addEventListener(MapResource.REPLACE_CONTENT, this.onReplaceContent);
            return;
        }//end  

        public void  updateEffect (double param1 )
        {
            return;
        }//end  

        public void  killEffect ()
        {
            this.m_owner.removeEventListener(MapResource.REPLACE_CONTENT, this.onReplaceContent);
            this.m_effect.cleanUp();
            this.m_effect = null;
            return;
        }//end  

        final protected EffectType  getEffectType ()
        {
            if (this.hasOwnProperty("effectOverride"))
            {
                return this.get("effectOverride") as EffectType;
            }
            return this.getEffectTypeForDirection();
        }//end  

        protected void  onReplaceContent (Event event =null )
        {
            if (!this.m_effect || this.m_currentDirection != this.m_owner.getDirection())
            {
                this.m_currentDirection = this.m_owner.getDirection();
                this.initEffect();
            }
            if (this.m_effect)
            {
                this.m_effect.reattach();
            }
            return;
        }//end  

        private EffectType  getEffectTypeForDirection ()
        {
            if (this.m_effectTypes.get("all"))
            {
                return EffectType.get(String(this.m_effectTypes.get("all")).toUpperCase());
            }
            return EffectType.get(String(this.m_effectTypes.get(this.m_currentDirection)).toUpperCase());
        }//end  

    }




