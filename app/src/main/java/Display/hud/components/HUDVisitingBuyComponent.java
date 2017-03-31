package Display.hud.components;

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
import Display.*;
import Display.aswingui.*;
import Engine.Managers.*;
import GameMode.*;

//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
import org.aswing.*;
import org.aswing.geom.*;
import scripting.*;
import Classes.sim.*;
import scripting.*;

    public class HUDVisitingBuyComponent extends HUDComponent
    {
        private String m_icon ;
        private boolean m_locked =false ;
        private XML m_xml ;
        private Vector<Script> m_validationScripts;

        public  HUDVisitingBuyComponent ()
        {
            return;
        }//end

         public void  initWithXML (XML param1 )
        {
            this.m_xml = param1;
            this.m_validationScripts = parseValidationScripts(this.m_xml);
            this.reset();
            if (!this.m_locked)
            {
                this.m_icon = param1.attribute("icon");
                LoadingManager.load(Global.getAssetURL(this.m_icon), this.onIconLoaded);
            }
            return;
        }//end

         public boolean  isVisible ()
        {
            if (this.m_locked)
            {
                return false;
            }
            return Global.world.isOwnerCitySam || Global.world.getTopGameMode() instanceof GMVisitBuy;
        }//end

        protected void  onMouseClick (MouseEvent event )
        {
            if (Global.world.getTopGameMode() instanceof GMVisitBuy)
            {
                Global.world.addGameMode(new GMVisit(Global.getVisiting()));
            }
            else
            {
                Global.world.addGameMode(new GMVisitBuy(Global.getVisiting()));
            }
            StatsManager.count("hud_clicks", "visit_buy", "purchase_toggle");
            return;
        }//end

         protected void  buildComponent ()
        {
            m_jWindow = new JWindow(this);
            m_jPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT, 0);
            m_toolTip = new ToolTip(this.composeToolTip);
            m_toolTip.attachToolTip(this);
            m_toolTip.hideCursor = true;
            m_jWindow.setContentPane(m_jPanel);
            ASwingHelper.prepare(m_jWindow);
            m_jWindow.cacheAsBitmap = true;
            m_jWindow.show();
            addEventListener(MouseEvent.CLICK, this.onMouseClick, false, 0, true);
            addEventListener(MouseEvent.MOUSE_OVER, this.onButtonMouseOver);
            addEventListener(MouseEvent.MOUSE_OUT, this.onButtonMouseOut);
            return;
        }//end

        protected String  composeToolTip ()
        {
            return ZLoc.t("Main", "VisitBuy_ToolTip");
        }//end

        private void  onButtonMouseOut (MouseEvent event )
        {
            this.filters = null;
            return;
        }//end

        private void  onButtonMouseOver (MouseEvent event )
        {
            GlowFilter _loc_2 =new GlowFilter(EmbeddedArt.HOVER_GLOW_COLOR ,1,8,8,5);
            this.filters = .get(_loc_2);
            return;
        }//end

        protected void  onIconLoaded (Event event )
        {
            _loc_2 =(Loader) event.target.loader;
            _loc_3 =(Bitmap) _loc_2.content;
            _loc_3.x = 0;
            _loc_3.y = 0;
            this.visible = true;
            this.addChild(_loc_3);
            m_jPanel.setPreferredSize(new IntDimension(_loc_3.width, _loc_3.height));
            m_jPanel.setMinimumSize(new IntDimension(_loc_3.width, _loc_3.height));
            m_jPanel.setMaximumSize(new IntDimension(_loc_3.width, _loc_3.height));
            ASwingHelper.prepare(m_jWindow);
            m_jWindow.useHandCursor = true;
            m_jWindow.buttonMode = true;
            if (!isVisibilityControlledInternally)
            {
                Z_TweenLite.to(this, 2, {alpha:1});
            }
            return;
        }//end

        protected boolean  lockedCheck ()
        {
            Array _loc_1 =null ;
            boolean _loc_4 =false ;
            int _loc_5 =0;
            int _loc_6 =0;
            if (this.m_locked)
            {
                return true;
            }
            _loc_2 = this.m_xml.@variant.get(0);
            if (_loc_2 != null)
            {
                _loc_1 = _loc_2.toString().split(",");
                _loc_5 = _loc_1.length;
                _loc_6 = 0;
                while (_loc_6 < _loc_5)
                {

                    _loc_1.put(_loc_6,  parseInt(_loc_1.get(_loc_6)));
                    _loc_6++;
                }
            }
            else
            {
                _loc_1 = .get(1);
            }
            _loc_3 = this.m_xml.@experiment;
            if (_loc_3 != null && _loc_3.length > 0)
            {
                _loc_4 = _loc_1.indexOf(Global.experimentManager.getVariant(_loc_3)) >= 0;
            }
            else
            {
                _loc_4 = true;
            }
            if (!_loc_4)
            {
                return true;
            }
            return !this.evaluateValidationScripts();
        }//end

        private boolean  evaluateValidationScripts ()
        {
            Script _loc_1 =null ;
            if (this.m_validationScripts == null || this.m_validationScripts.length == 0)
            {
                return true;
            }
            for(int i0 = 0; i0 < this.m_validationScripts.size(); i0++)
            {
            	_loc_1 = this.m_validationScripts.get(i0);

                if (!_loc_1.validates())
                {
                    return false;
                }
            }
            return true;
        }//end

         public void  reset ()
        {
            this.m_locked = this.lockedCheck();
            return;
        }//end

        private static Script Vector  parseValidationScripts (XML param1 ).<>
        {
            XML _loc_5 =null ;
            _loc_2 = param1.script ;
            if (_loc_2.length() <= 0)
            {
                return null;
            }
            DummyScriptingContext _loc_3 =new DummyScriptingContext ();
            Vector<Script> _loc_4 =new Vector<Script>();
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            	_loc_5 = _loc_2.get(i0);

                _loc_4.push(Global.gameSettings().parseScriptTag(_loc_5, _loc_3));
            }
            return _loc_4;
        }//end

    }

import scripting.*;

class DummyScriptingContext implements IScriptingContext

     DummyScriptingContext ()
    {
        return;
    }//end

    public void  attachScript (Script param1 )
    {
        return;
    }//end

    public boolean  validate ()
    {
        return true;
    }//end

    public void  onValidate ()
    {
        return;
    }//end





