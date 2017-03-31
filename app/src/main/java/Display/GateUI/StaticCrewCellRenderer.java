package Display.GateUI;

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
import Display.aswingui.*;
import Display.aswingui.inline.*;
import Display.aswingui.inline.layout.*;
import Display.aswingui.inline.style.*;
import Display.aswingui.inline.util.*;
import Events.*;
import Modules.matchmaking.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
import org.aswing.*;
import org.aswing.colorchooser.*;

    public class StaticCrewCellRenderer implements IStaticCellRenderer
    {
        private DisplayObject m_itemIcon ;
        private String m_itemLabel ;
        private String m_footerLabel ;
        private String m_buttonLabel ;
        private boolean m_buttonEnabled =true ;
        private Function m_buttonClickHandler ;
        private Function m_refreshHandler ;
        private EventHandler timerHandler ;
        private static ASwingLayoutFactory layouts ;
        private static ASwingFactory swing ;
        private static IASwingLayout centered ;
        private static IASwingLayout vertical ;
        private static DisplayObject checkmarkIcon ;
        private static Object css ;

        public  StaticCrewCellRenderer ()
        {
            if (!swing)
            {
                initializeHelpers();
            }
            //this.timerHandler = handle(Event.COMPLETE, this.timer_completeHandler);
            return;
        }//end

        public void  label (String param1 )
        {
            this.m_itemLabel = param1;
            return;
        }//end

        public void  buttonLabel (String param1 )
        {
            this.m_buttonLabel = param1;
            return;
        }//end

        public void  footerLabel (String param1 )
        {
            this.m_footerLabel = param1;
            return;
        }//end

        public void  buttonEnabled (boolean param1 )
        {
            this.m_buttonEnabled = param1;
            return;
        }//end

        public void  buttonClickHandler (Function param1 )
        {
            this.m_buttonClickHandler = param1;
            return;
        }//end

        public void  refreshHandler (Function param1 )
        {
            this.m_refreshHandler = param1;
            return;
        }//end

        public void  icon (DisplayObject param1 )
        {
            this.m_itemIcon = param1;
            return;
        }//end

        public void  render (Container param1 )
        {
            IASwingPanel _loc_9 =null ;
            IASwingButton _loc_12 =null ;
            IASwingText _loc_13 =null ;
            double _loc_14 =0;
            IASwingNode _loc_15 =null ;
            IASwingImage _loc_16 =null ;
            IASwingNode _loc_17 =null ;
            AbstractButton _loc_18 =null ;
            JCountdownText _loc_19 =null ;
            this.timerHandler.source = null;
            _loc_2 = swing.layout.vertical.align(SoftBoxLayout.CENTER).gap(0);
            _loc_3 = swing.panel().layout(_loc_2);
            _loc_4 = swing.richText(this.m_itemLabel).size(110,-1).multiline().wordWrap();
            _loc_5 = swing.panel(css.header).size(110,35).layout(swing.layout.border).add(_loc_4);
            _loc_4.bottom = 0;
            _loc_6 = swing.image(css.image).size(50,50).source(this.m_itemIcon);
            _loc_7 = swing.panel(css.background).layout(vertical).add(_loc_6);
            _loc_8 = swing.panel().layout(centered).add(_loc_7);
            _loc_10 = swing.layout.box.align(SoftBoxLayout.CENTER).gap(5);
            _loc_11 = swing.panel(css.footer).layout(_loc_10);
            if (this.m_buttonEnabled)
            {
                _loc_12 = swing.button(this.m_buttonLabel, css.button).size(52, 23);
                _loc_9 = swing.panel().layout(centered).add(_loc_12);
                _loc_13 = swing.text(this.m_footerLabel, css.footer);
                _loc_11.add(_loc_13);
            }
            else
            {
                _loc_14 = MatchmakingManager.instance.askForCrewEnabledTime();
                _loc_15 = swing.panel(css.countdown).size(70, 18).layout(vertical).add(swing.panel().size(70, 16).layout(swing.layout.border).add(swing.countdown("timer").timeRemaining(_loc_14)));
                _loc_16 = swing.image().source(checkmarkIcon);
                _loc_17 = swing.panel().layout(swing.layout.vertical.align(VerticalLayout.CENTER).gap(0)).add(_loc_15).add(_loc_16);
                _loc_11.add(_loc_17);
            }
            param1.append(_loc_3.add(_loc_5).add(_loc_8).add(_loc_11).add(_loc_9).component);
            if (this.m_buttonEnabled)
            {
                _loc_18 =(AbstractButton) _loc_3.get(this.m_buttonLabel).component;
                _loc_18.addActionListener(this.m_buttonClickHandler, 0, true);
            }
            else
            {
                _loc_19 =(JCountdownText) _loc_3.get("timer").component;
                this.timerHandler.source = _loc_19;
            }
            _loc_3.destroy();
            return;
        }//end

        private void  timer_completeHandler (Event event )
        {
            this.timerHandler.source = null;
            if (this.m_refreshHandler != null)
            {
                this.m_refreshHandler(event);
            }
            return;
        }//end

        private static void  initializeHelpers ()
        {
            swing = ASwingFactory.getInstance();
            layouts = ASwingLayoutFactory.getInstance();
            _loc_1 = GateDialog.assets.checkmark_green;
            checkmarkIcon =(DisplayObject) new _loc_1;
            _loc_2 = EmbeddedArt.crewFriendBackground;
            DisplayObject _loc_3 =(DisplayObject)new _loc_2;
            css = {header:{color:ASwingColor.FADED_ORANGE, "font-family":ASwingFont.TITLE_FONT, "font-size":9, "text-align":FontStyle.ALIGN_CENTER, "text-transform":FontStyle.SMALLCAPS, "border-style":BorderStyle.EMPTY, "padding-top":1, "padding-bottom":-3}, background:{"background-image":_loc_3, "background-width":_loc_3.width, "background-height":_loc_3.height}, image:{"border-style":BorderStyle.EMPTY, "padding-left":9, "padding-top":9}, countdown:{color:ASwingColor.WHITE, "font-size":13, "font-family":ASwingFont.DEFAULT_FONT_BOLD, "background-color":ASwingColor.DARK_BLUE, "corner-radius":5, "border-style":BorderStyle.EMPTY, "padding-top":3, "padding-bottom":1}, footer:{color:ASwingColor.PALE_BLUE, "font-family":ASwingFont.DEFAULT_FONT_BOLD, "font-size":14, filters:.get(new GlowFilter(16777215, 1, 2, 2, 10)), "border-style":BorderStyle.EMPTY, "padding-bottom":1}, button:{"skin-class":"BuyGateKeyButtonUI", "padding-top":0, "padding-left":2, "padding-bottom":3, "padding-right":2}};
            centered = layouts.box.align(SoftBoxLayout.CENTER).gap(0);
            vertical = layouts.vertical.align(SoftBoxLayout.TOP).gap(0);
            return;
        }//end

    }



