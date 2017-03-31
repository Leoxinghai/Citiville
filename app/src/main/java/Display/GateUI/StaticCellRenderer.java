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

import Display.aswingui.*;
import Display.aswingui.inline.*;
import Display.aswingui.inline.layout.*;
import Display.aswingui.inline.style.*;
import Display.aswingui.inline.util.*;
import Events.*;
import Modules.matchmaking.*;
//import flash.display.*;
//import flash.events.*;
import org.aswing.*;
import org.aswing.colorchooser.*;

    public class StaticCellRenderer implements IStaticCellRenderer
    {
        private DisplayObject m_itemIcon ;
        private String m_itemLabel ;
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

        public  StaticCellRenderer ()
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
            IASwingNode _loc_8 =null ;
            double _loc_10 =0;
            IASwingImage _loc_11 =null ;
            IASwingNode _loc_12 =null ;
            AbstractButton _loc_13 =null ;
            JCountdownText _loc_14 =null ;
            this.timerHandler.source = null;
            _loc_2 = swing.richText(this.m_itemLabel,css.header);
            _loc_3 = swing.panel().layout(centered).add(_loc_2);
            _loc_4 = swing.image(css.image).source(this.m_itemIcon);
            _loc_5 = swing.panel(css.background).layout(vertical).add(_loc_4);
            _loc_6 = swing.panel().layout(centered).add(_loc_5);
            int _loc_7 =30;
            if (this.m_buttonEnabled)
            {
                _loc_8 = swing.button(this.m_buttonLabel, css.button).size(130, 32);
            }
            else
            {
                _loc_10 = MatchmakingManager.instance.askForBuildableEnabledTime();
                _loc_11 = swing.image().source(checkmarkIcon);
                _loc_12 = swing.panel(css.countdown).size(90, 20).layout(swing.layout.border).add(swing.countdown("timer").timeRemaining(_loc_10));
                _loc_8 = swing.panel().layout(swing.layout.vertical.align(VerticalLayout.CENTER).gap(3)).add(_loc_12).add(_loc_11);
                _loc_7 = 23;
            }
            _loc_9 = swing.panel("footer").layout(centered).add(_loc_8);
            param1.append(_loc_3.component);
            param1.append(_loc_6.component);
            param1.append(swing.verticalSpacer(_loc_7).component);
            param1.append(_loc_9.component);
            if (this.m_buttonEnabled)
            {
                _loc_13 =(AbstractButton) _loc_8.component;
                _loc_13.addActionListener(this.m_buttonClickHandler, 0, true);
            }
            else if (this.m_refreshHandler != null)
            {
                _loc_14 =(JCountdownText) _loc_9.get("timer").component;
                this.timerHandler.source = _loc_14;
            }
            _loc_3.destroy();
            _loc_6.destroy();
            _loc_9.destroy();
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
            _loc_1 = GateDialog.assets.buildables_check;
            checkmarkIcon =(DisplayObject) new _loc_1;
            _loc_2 = GateDialog.assets.buildables_item;
            DisplayObject _loc_3 =(DisplayObject)new _loc_2;
            css = {header:{color:ASwingColor.BROWN, "font-family":ASwingFont.TITLE_FONT, "font-size":16, "font-size-variants":{es:10, ja:16, other:8}, "text-transform":FontStyle.SMALLCAPS}, background:{"background-image":_loc_3, "background-width":_loc_3.width, "background-height":_loc_3.height, "padding-top":5}, image:{"border-style":BorderStyle.EMPTY, "padding-left":10, "padding-top":10}, countdown:{color:ASwingColor.WHITE, "font-size":16, "font-family":ASwingFont.DEFAULT_FONT_BOLD, "background-color":ASwingColor.DARK_BLUE, "corner-radius":5}, button:{"skin-class":"AskForKeyButtonUI", "font-family":ASwingFont.TITLE_FONT, "font-size":18, "font-size-variants":{ja:18, other:10}}};
            centered = layouts.box.align(SoftBoxLayout.CENTER).gap(0);
            vertical = layouts.vertical.align(SoftBoxLayout.TOP).gap(0);
            return;
        }//end

    }



