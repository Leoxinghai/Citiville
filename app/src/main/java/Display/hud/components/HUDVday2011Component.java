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
import Classes.sim.*;
import Display.*;
import Display.DialogUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Modules.stats.types.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
import org.aswing.*;
import org.aswing.geom.*;
import Classes.sim.*;

    public class HUDVday2011Component extends HUDComponent implements IPlayerStateObserver
    {
        private AutoAnimatedBitmap m_icon =null ;
        private AssetPane m_iconHolder ;
        private JTextField m_heartText ;
        private boolean m_locked =false ;
        private boolean m_created =false ;

        public  HUDVday2011Component ()
        {
            Global.player.addObserver(this);
            if (Global.player.level < 5)
            {
                this.m_locked = true;
            }
            if (!this.m_locked && !this.m_created)
            {
                this.create();
                this.m_created = true;
            }
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
            this.m_heartText = ASwingHelper.makeTextField(ValentineManager.getTotalAdmirers().toString(), EmbeddedArt.defaultFontNameBold, 16, 16777215, 0, _loc_1);
            LoadingManager.load(Global.getAssetURL("assets/hud/heart2_71x66.png"), this.onIconLoaded);
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_2.append(ASwingHelper.horizontalStrut(0));
            _loc_2.append(this.m_heartText, BorderLayout.CENTER);
            m_jPanel.append(_loc_2);
            m_jWindow.setContentPane(m_jPanel);
            ASwingHelper.prepare(m_jWindow);
            m_jWindow.cacheAsBitmap = true;
            m_jWindow.show();
            this.alpha = 0;
            this.visible = false;
            if (!this.m_locked)
            {
                this.visible = true;
                Z_TweenLite.to(this, 1, {alpha:1});
                this.addTooltip();
                this.addCounter();
            }
            return;
        }//end

        private void  onIconLoaded (Event event )
        {
            Bitmap _loc_3 =null ;
            _loc_2 =(Loader) event.target.loader;
            if (_loc_2.content instanceof Bitmap)
            {
                _loc_3 =(Bitmap) _loc_2.content;
                this.m_icon = new AutoAnimatedBitmap(_loc_3.bitmapData, 30, 71, 66, 30);
                this.m_icon.x = 0;
                this.m_icon.y = 0;
                if (ValentineManager.getNewValentines().length())
                {
                    this.m_icon.play();
                }
                else
                {
                    this.m_icon.stop();
                }
                addChildAt(this.m_icon, 0);
                m_jPanel.setPreferredSize(new IntDimension(this.m_icon.width, this.m_icon.height));
                m_jPanel.setMinimumSize(new IntDimension(this.m_icon.width, this.m_icon.height));
                m_jPanel.setMaximumSize(new IntDimension(this.m_icon.width, this.m_icon.height));
                ASwingHelper.prepare(m_jWindow);
            }
            return;
        }//end

        private void  onMouseClick (Event event )
        {
            if (!this.m_locked)
            {
                StatsManager.count(StatsCounterType.CARDMAKER, StatsKingdomType.VDAY_2011, StatsPhylumType.HUD, "icon_click");
                UI.displayValentineDialog();
                this.m_icon.currentFrame = 0;
                this.m_icon.updateMe(null);
                this.m_icon.stop();
            }
            return;
        }//end

        private void  addTooltip ()
        {
            m_toolTip =new ToolTip (String  ()
            {
                return ZLoc.t("Dialogs", "ValUI_HUD_tooltip");
            }//end
            );
            m_toolTip.attachToolTip(m_jPanel);
            m_toolTip.hideCursor = true;
            return;
        }//end

        private void  addCounter ()
        {
            this.m_heartText.setText(ValentineManager.getTotalAdmirers().toString());
            return;
        }//end

         public void  updateCounter (...args )
        {
            this.m_heartText.setText(ValentineManager.getTotalAdmirers().toString());
            return;
        }//end

        public void  levelChanged (int param1 )
        {
            GenericPictureDialog _loc_2 =null ;
            if (param1 == 5)
            {
                this.m_locked = false;
                if (this.m_created == false)
                {
                    this.create();
                    this.m_created = true;
                }
                this.visible = true;
                Z_TweenLite.to(this, 1, {alpha:1});
                this.addTooltip();
                this.addCounter();
                if (!Global.player.getSeenFlag("vday_newbie"))
                {
                    _loc_2 = new GenericPictureDialog(ZLoc.t("Announcements", "Announcement09_text"), "", GenericDialogView.TYPE_OK, null, ZLoc.t("Announcements", "Announcement09_title"), "", true, 0, "Okay", "assets/announcements/valentines_announceimage_452x352.png");
                    UI.displayPopup(_loc_2);
                    Global.player.setSeenFlag("vday_newbie");
                }
            }
            return;
        }//end

        public void  xpChanged (int param1 )
        {
            return;
        }//end

        public void  commodityChanged (int param1 ,String param2 )
        {
            return;
        }//end

        public void  energyChanged (double param1 )
        {
            return;
        }//end

        public void  goldChanged (int param1 )
        {
            return;
        }//end

        public void  cashChanged (int param1 )
        {
            return;
        }//end

    }


