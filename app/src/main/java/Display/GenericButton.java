package Display;

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
import com.xiyu.util.Event;

import root.Global;

//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
//import flash.text.*;

    public class GenericButton
    {
        private double m_overStateScale =1.14;
        private double m_currentScaling =1;
        private String m_buttonValue ="";
        private MovieClip m_target ;
        private String m_text ;
        private boolean m_disabled =false ;
        private Function m_callback =null ;
        public MovieClip m_overlay ;
        private TextFormat m_tfOver ;
        private TextFormat m_tfNormal ;
        private String m_state ="up";
        private boolean m_enableHitState =false ;
        private int m_hitFrameNumber =3;
        private int m_curFrame =1;
        private boolean m_enterFrameOn =false ;
        private Matrix normalTransform ;
        private Matrix scaledTransform ;
        private Matrix targetTransform ;
        public static String UPSTATE ="up";
        public static String OVERSTATE ="over";
        public static String DOWNSTATE ="down";
        public static String HITSTATE ="hit";

        public  GenericButton (MovieClip param1 ,Function param2 ,String param3 ,String param4)
        {
            MovieClip _loc_7 =null ;
            this.m_overlay = new MovieClip();
            this.m_tfOver = new TextFormat();
            this.m_tfOver.size = 15;
            this.m_tfNormal = new TextFormat();
            this.m_tfNormal.size = 14;
            this.m_target = param1;
            _loc_5 = this.m_target.getBounds(this.m_target.parent);
            this.m_overlay.graphics.beginFill(255, 0);
            this.m_overlay.graphics.drawRect(0, 0, _loc_5.width, _loc_5.height);
            this.m_overlay.graphics.endFill();
            _loc_6 = (MovieClip)this.m_target.parent
            if (param4 != "")
            {
                _loc_7 =(MovieClip) _loc_6.getChildByName(param4);
                if (_loc_7 != null)
                {
                    _loc_6.removeChild(_loc_7);
                }
            }
            else
            {
                param4 = "gb_Overlay";
            }
            this.m_target.parent.addChild(this.m_overlay);
            this.m_overlay.x = _loc_5.x;
            this.m_overlay.y = _loc_5.y;
            this.m_overlay.buttonMode = true;
            this.m_overlay.name = param4;
            this.m_overlay.buttonPointer = this;
            param1.buttonMode = true;
            param1.useHandCursor = true;
            param1.gotoAndStop(1);
            this.m_overlay.addEventListener(MouseEvent.MOUSE_DOWN, this.onDown);
            this.m_overlay.addEventListener(MouseEvent.MOUSE_OUT, this.onOut);
            this.m_overlay.addEventListener(MouseEvent.MOUSE_OVER, this.onOver);
            if (param2 != null)
            {
                this.m_callback = param2;
                this.m_overlay.addEventListener(MouseEvent.CLICK, this.onClick);
            }
            param1.addFrameScript(0, this.frameScriptTextUpdate);
            param1.addFrameScript(1, this.frameScriptTextUpdate);
            param1.addFrameScript(2, this.frameScriptTextUpdate);
            param1.addFrameScript(3, this.frameScriptTextUpdate);
            this.m_text = "";
            if (this.m_target.generic_tf && this.m_target.generic_tf.text)
            {
                this.m_text = this.m_target.generic_tf.text;
                this.normalTransform = this.m_target.generic_tf.transform.matrix.clone();
                this.calcScaledTransform();
                this.targetTransform = this.normalTransform;
            }
            this.buttonValue = param3;
            return;
        }//end

        private void  calcScaledTransform ()
        {
            _loc_1 = this.m_target.generic_tf.x+this.m_target.generic_tf.width/2;
            _loc_2 = this.m_target.generic_tf.y+this.m_target.generic_tf.height/2;
            this.scaledTransform = this.normalTransform.clone();
            this.scaledTransform.translate(-_loc_1, -_loc_2);
            this.scaledTransform.scale(this.m_overStateScale, this.m_overStateScale);
            this.scaledTransform.translate(_loc_1, _loc_2);
            return;
        }//end

        private void  onOver (MouseEvent event )
        {
            if (!this.disabled)
            {
                this.m_target.addFrameScript(1, this.frameScriptTextUpdate);
                this.m_enterFrameOn = false;
                if (!this.m_enableHitState || this.m_enableHitState && this.m_state != HITSTATE)
                {
                    this.targetTransform = this.scaledTransform;
                    this.m_currentScaling = this.m_overStateScale;
                    this.m_state = OVERSTATE;
                    this.m_target.gotoAndStop(this.m_target.totalFrames);
                    this.m_target.gotoAndStop(2);
                    Global.stage.invalidate();
                    this.m_target.addEventListener(Event.RENDER, this.updateText);
                }
            }
            return;
        }//end

        private void  onDown (MouseEvent event )
        {
            this.m_target.addFrameScript(2, this.frameScriptTextUpdate);
            this.m_enterFrameOn = false;
            this.targetTransform = this.normalTransform;
            if (!this.m_enableHitState || this.m_enableHitState && this.m_state != HITSTATE)
            {
                this.m_state = DOWNSTATE;
                Global.stage.invalidate();
                this.m_target.addEventListener(Event.RENDER, this.updateText);
                this.m_target.gotoAndStop(this.m_target.totalFrames);
                this.m_target.gotoAndStop(3);
            }
            return;
        }//end

        public void  onOut (MouseEvent event )
        {
            this.targetTransform = this.normalTransform;
            this.m_target.addFrameScript(0, this.frameScriptTextUpdate);
            this.m_enterFrameOn = false;
            if (!this.m_enableHitState || this.m_enableHitState && this.m_state != HITSTATE)
            {
                this.m_state = UPSTATE;
                Global.stage.invalidate();
                this.m_target.addEventListener(Event.RENDER, this.updateText);
                this.m_target.gotoAndStop(this.m_target.totalFrames);
                this.m_target.gotoAndStop(1);
            }
            return;
        }//end

        private void  onClick (MouseEvent event )
        {
            if (!this.disabled)
            {
                this.m_target.addFrameScript((this.m_hitFrameNumber - 1), this.frameScriptTextUpdate);
                this.m_enterFrameOn = false;
                this.targetTransform = this.normalTransform;
                this.m_state = HITSTATE;
                Global.stage.invalidate();
                this.m_target.addEventListener(Event.RENDER, this.updateText);
                this.m_target.gotoAndStop(this.m_target.totalFrames);
                this.m_target.gotoAndStop(this.m_hitFrameNumber);
                if (this.m_callback != null)
                {
                    this.m_callback(event);
                }
            }
            return;
        }//end

        public void  name (String param1 )
        {
            this.m_overlay.name = param1;
            return;
        }//end

        public String  name ()
        {
            return this.m_overlay.name;
        }//end

        public void  text (String param1 )
        {
            this.m_curFrame = this.m_target.currentFrame;
            this.m_target.gotoAndStop(this.m_target.totalFrames);
            this.m_target.gotoAndStop(this.m_curFrame);
            if (this.m_target.generic_tf)
            {
                this.m_target.generic_tf.text = param1;
                this.m_text = param1;
            }
            else
            {
                this.m_target.addFrameScript(this.m_curFrame, this.frameScriptTextUpdate);
            }
            return;
        }//end

        public void  tfOver (TextFormat param1 )
        {
            this.m_tfOver = param1;
            return;
        }//end

        public void  tfNormal (TextFormat param1 )
        {
            this.m_tfNormal = param1;
            return;
        }//end

        public void  state (String param1 )
        {
            switch(param1)
            {
                case UPSTATE:
                {
                    this.m_target.gotoAndStop(1);
                    this.m_state = UPSTATE;
                    break;
                }
                case OVERSTATE:
                {
                    this.m_target.gotoAndStop(2);
                    this.m_state = OVERSTATE;
                    break;
                }
                case DOWNSTATE:
                {
                    this.m_target.gotoAndStop(3);
                    this.m_state = DOWNSTATE;
                    break;
                }
                case HITSTATE:
                {
                    this.m_target.gotoAndStop(this.m_hitFrameNumber);
                    this.m_state = HITSTATE;
                    break;
                }
                default:
                {
                    break;
                }
            }
            return;
        }//end

        public String  state ()
        {
            return this.m_state;
        }//end

        public void  enableHitState (boolean param1 )
        {
            this.m_enableHitState = param1;
            return;
        }//end

        public void  setHitFrameNumber (int param1 )
        {
            if (param1 > 0 && param1 <= this.m_target.totalFrames)
            {
                this.m_hitFrameNumber = param1;
            }
            return;
        }//end

        public void  turnOff ()
        {
            this.m_target.buttonMode = false;
            this.m_target.useHandCursor = false;
            this.m_target.visible = false;
            this.m_overlay.buttonMode = false;
            this.m_overlay.visible = false;
            this.m_overlay.removeEventListener(MouseEvent.MOUSE_DOWN, this.onDown);
            this.m_overlay.removeEventListener(MouseEvent.MOUSE_OUT, this.onOut);
            this.m_overlay.removeEventListener(MouseEvent.MOUSE_OVER, this.onOver);
            if (this.m_callback != null)
            {
                this.m_overlay.removeEventListener(MouseEvent.CLICK, this.onClick);
            }
            return;
        }//end

        public void  turnOn ()
        {
            this.turnOff();
            this.m_target.visible = true;
            this.m_target.buttonMode = true;
            this.m_target.useHandCursor = true;
            this.m_overlay.buttonMode = true;
            this.m_overlay.visible = true;
            this.m_overlay.addEventListener(MouseEvent.MOUSE_DOWN, this.onDown);
            this.m_overlay.addEventListener(MouseEvent.MOUSE_OUT, this.onOut);
            this.m_overlay.addEventListener(MouseEvent.MOUSE_OVER, this.onOver);
            if (this.m_callback != null)
            {
                this.m_overlay.addEventListener(MouseEvent.CLICK, this.onClick);
            }
            return;
        }//end

        public void  toggleDisable (boolean param1 )
        {
            this.disabled = !param1;
            return;
        }//end

        private void  frameScriptTextUpdate ()
        {
            if (this.m_target.generic_tf && this.m_text != "")
            {
                this.m_target.generic_tf.setTextFormat(this.m_tfOver);
                this.m_target.generic_tf.text = this.m_text;
                this.m_enterFrameOn = false;
                this.m_target.generic_tf.transform.matrix = this.targetTransform;
            }
            else if (!this.m_enterFrameOn)
            {
                this.m_target.addEventListener(Event.ENTER_FRAME, this.updateText);
                this.m_enterFrameOn = true;
            }
            return;
        }//end

        private void  updateText (Event event )
        {
            if (this.m_target.generic_tf && this.m_text != "")
            {
                this.m_target.generic_tf.setTextFormat(this.m_tfOver);
                this.m_target.generic_tf.text = this.m_text;
                this.calcScaledTransform();
                this.m_target.removeEventListener(Event.ENTER_FRAME, this.updateText);
            }
            return;
        }//end

        public MovieClip  getButton ()
        {
            return this.m_target;
        }//end

        public void  disabled (boolean param1 )
        {
            this.m_disabled = param1;
            if (param1 !=null)
            {
                this.m_overlay.buttonMode = false;
                this.m_overlay.visible = false;
            }
            else
            {
                this.m_overlay.buttonMode = true;
                this.m_overlay.visible = true;
            }
            return;
        }//end

        public boolean  disabled ()
        {
            return this.m_disabled;
        }//end

        public void  buttonValue (String param1 )
        {
            this.m_overlay.buttonValue = param1;
            this.m_buttonValue = param1;
            return;
        }//end

        public String  buttonValue ()
        {
            return this.m_buttonValue;
        }//end

    }



