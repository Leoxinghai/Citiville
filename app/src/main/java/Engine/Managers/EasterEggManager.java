package Engine.Managers;

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

import Engine.Classes.EasterEggs.*;
import Engine.Display.*;
import Engine.Interfaces.*;

//import flash.events.*;
//import flash.text.*;

    public class EasterEggManager
    {
        protected Vector<IEasterEgg> m_enabledEasterEggs;
        protected Array m_keysEntered ;
        protected boolean m_easterEggMode =false ;
        protected TextField m_easterEggText ;
public static EasterEggManager m_instance =null ;

        public  EasterEggManager ()
        {
            this.m_enabledEasterEggs = new Vector<IEasterEgg>();
            this.m_keysEntered = new Array();
            this.addEasterEgg(new CreditsEasterEgg());
            return;
        }//end

        protected void  addEasterEgg (IEasterEgg param1 )
        {
            this.m_enabledEasterEggs.push(param1);
            return;
        }//end

        protected void  removeEasterEgg (String param1 )
        {
            int _loc_2 =0;
            while (_loc_2 < this.m_enabledEasterEggs.length())
            {

                if (this.m_enabledEasterEggs.get(_loc_2).code == param1)
                {
                    this.m_enabledEasterEggs.splice(_loc_2, 1);
                    break;
                }
                _loc_2++;
            }
            return;
        }//end

        public void  processKeyStroke (KeyboardEvent event )
        {
            String _loc_2 =null ;
            String _loc_3 =null ;
            if (event.keyCode == 192 && this.m_easterEggMode == false)
            {
                this.enterEasterEggMode();
            }
            else if (this.m_easterEggMode && event.keyCode == 8)
            {
                this.m_keysEntered.pop();
                this.updateText();
            }
            else if (this.m_easterEggMode && event.keyCode == 13)
            {
                if (this.m_keysEntered.length > 0)
                {
                    _loc_2 = this.m_keysEntered.join("");
                    this.easterEgg(_loc_2);
                }
                this.exitEasterEggMode();
            }
            else if (this.m_keysEntered.length > 50)
            {
                this.exitEasterEggMode();
            }
            else if (this.m_easterEggMode)
            {
                _loc_3 = String.fromCharCode(event.charCode);
                this.m_keysEntered.push(_loc_3);
                this.updateText();
            }
            return;
        }//end

        protected void  updateText ()
        {
            if (this.m_easterEggText != null)
            {
                this.m_easterEggText.text = "Code: " + this.m_keysEntered.join("");
            }
            return;
        }//end

        protected void  enterEasterEggMode ()
        {
            this.m_keysEntered = new Array();
            this.m_easterEggMode = true;
            TextFormat _loc_1 =new TextFormat(null ,12,16777215,true );
            this.m_easterEggText = new StrokeTextField(0, 0.8);
            this.m_easterEggText.setTextFormat(_loc_1);
            this.m_easterEggText.background = true;
            this.m_easterEggText.backgroundColor = 16777215;
            this.m_easterEggText.y = 10;
            this.m_easterEggText.x = 10;
            this.updateText();
            GlobalEngine.stage.addChild(this.m_easterEggText);
            return;
        }//end

        protected void  exitEasterEggMode ()
        {
            this.m_keysEntered = new Array();
            this.m_easterEggMode = false;
            GlobalEngine.stage.removeChild(this.m_easterEggText);
            return;
        }//end

        protected void  easterEgg (String param1 )
        {
            int _loc_2 =0;
            while (_loc_2 < this.m_enabledEasterEggs.length())
            {

                if (this.m_enabledEasterEggs.get(_loc_2).code == param1)
                {
                    this.m_enabledEasterEggs.get(_loc_2).execute();
                    break;
                }
                _loc_2++;
            }
            return;
        }//end

        public static EasterEggManager  instance ()
        {
            if (m_instance == null)
            {
                m_instance = new EasterEggManager;
            }
            return m_instance;
        }//end

    }



