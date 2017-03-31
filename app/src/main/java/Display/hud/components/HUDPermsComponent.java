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
import Events.*;
import Modules.stats.experiments.*;
import Modules.stats.types.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
import org.aswing.*;
import org.aswing.geom.*;
import Classes.sim.*;

    public class HUDPermsComponent extends HUDComponent
    {
        private DisplayObject m_icon =null ;
        private AssetPane m_iconHolder ;
        private boolean m_locked =true ;
        private boolean m_hidden =false ;
        private boolean m_created =false ;
        protected XML m_xml ;
        private static  String IMAGE_URL ="assets/hud/permissions_icon.png";

        public  HUDPermsComponent ()
        {
            return;
        }//end

         public void  initWithXML (XML param1 )
        {
            this.m_xml = param1;
            this.reset();
            return;
        }//end

         public void  reset ()
        {
            int _loc_2 =0;
            String _loc_3 =null ;
            Array _loc_4 =null ;
            String _loc_5 =null ;
            this.m_locked = true;
            _loc_1 = (String)(this.m_xml.@experiment);
            if (_loc_1)
            {
                _loc_2 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_NEWPERMS);
                _loc_3 = String(this.m_xml.@variant);
                if (_loc_3)
                {
                    _loc_4 = _loc_3.split(",");
                    for(int i0 = 0; i0 < _loc_4.size(); i0++) 
                    {
                    	_loc_5 = _loc_4.get(i0);

                        if (_loc_2 == parseInt(_loc_5))
                        {
                            this.m_locked = false;
                            break;
                        }
                    }
                }
                else if (_loc_2 > 0)
                {
                    this.m_locked = false;
                }
            }
            this.m_locked = this.m_locked || Global.player.hasExtendedPermissions;
            if (!this.m_locked && !this.m_created)
            {
                this.create();
                this.m_created = true;
            }
            return;
        }//end

         public boolean  isVisible ()
        {
            return this.m_created && !this.m_locked;
        }//end

        public void  hide ()
        {
            removeEventListener(MouseEvent.CLICK, this.onMouseClick, false);
            Z_TweenLite.to(this, 2, {alpha:0, onComplete:this.doHide});
            this.m_locked = true;
            this.m_hidden = true;
            return;
        }//end

        protected void  doHide ()
        {
            if (m_jWindow)
            {
                m_jWindow.visible = false;
                m_jWindow.buttonMode = false;
            }
            this.m_locked = true;
            this.m_hidden = true;
            return;
        }//end

        private void  create ()
        {
            m_jWindow = new JWindow(this);
            m_jPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            TextFormat _loc_1 =new TextFormat(EmbeddedArt.defaultFontNameBold ,18,16777215,true );
            _loc_1.align = TextFormatAlign.RIGHT;
            this.addEventListener(MouseEvent.CLICK, this.onMouseClick, false, 0, true);
            m_jPanel.append(ASwingHelper.verticalStrut(15));
            LoadingManager.load(Global.getAssetURL(IMAGE_URL), this.onIconLoaded);
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_2.append(ASwingHelper.horizontalStrut(0));
            m_jWindow.setContentPane(m_jPanel);
            ASwingHelper.prepare(m_jWindow);
            m_jWindow.cacheAsBitmap = true;
            m_jWindow.show();
            this.alpha = 0;
            this.visible = false;
            if (!this.m_locked)
            {
                this.visible = true;
                this.addTooltip();
            }
            return;
        }//end

        private void  onIconLoaded (Event event )
        {
            _loc_2 =(Loader) event.target.loader;
            this.m_icon = _loc_2.content;
            addChildAt(this.m_icon, 0);
            m_jPanel.setPreferredSize(new IntDimension(this.m_icon.width, this.m_icon.height));
            m_jPanel.setMinimumSize(new IntDimension(this.m_icon.width, this.m_icon.height));
            m_jPanel.setMaximumSize(new IntDimension(this.m_icon.width, this.m_icon.height));
            ASwingHelper.prepare(m_jWindow);
            m_jWindow.useHandCursor = true;
            m_jWindow.buttonMode = true;
            alpha = 0;
            Z_TweenLite.to(this, 2, {alpha:1});
            return;
        }//end

        private void  onMouseClick (Event event )
        {
            if (!this.m_locked)
            {
                StatsManager.count(StatsCounterType.HUD_COUNTER, "newperms", "icon_clicked");
                UI.displayMessage(ZLoc.t("Dialogs", "HUDPerms_message"), GenericPopup.TYPE_YESNO, this.acceptHandler, "permsDialog", true);
            }
            return;
        }//end

        private void  acceptHandler (GenericPopupEvent event )
        {
            if (event.button == GenericPopup.ACCEPT)
            {
                StatsManager.count(StatsCounterType.DIALOG_UNSAMPLED, "newperms", "hud_accepted");
                Global.world.viralMgr.showStreamExtendedPermissions();
                this.hide();
            }
            return;
        }//end

        private void  addTooltip ()
        {
            m_toolTip =new ToolTip (String  ()
            {
                return ZLoc.t("Dialogs", "HUDPerms_tooltip");
            }//end
            );
            m_toolTip.attachToolTip(m_jPanel);
            m_toolTip.hideCursor = true;
            return;
        }//end

    }



