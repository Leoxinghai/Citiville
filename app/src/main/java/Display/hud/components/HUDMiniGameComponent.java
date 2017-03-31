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
import Display.MarketUI.assets.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Modules.minigames.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
import org.aswing.*;
import org.aswing.geom.*;
import Classes.sim.*;

    public class HUDMiniGameComponent extends HUDComponent implements IPlayerStateObserver
    {
        private AssetPane m_iconHolder ;
        private JTextField m_heartText ;
        private JTextField m_heartText2 ;
        private boolean m_locked =false ;
        private boolean m_created =false ;

        public  HUDMiniGameComponent ()
        {
            this.visible = false;
            Global.player.addObserver(this);
            this.reset();
            return;
        }//end

         public void  reset ()
        {
            this.m_locked = false;
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
            m_jPanel.append(ASwingHelper.verticalStrut(0));
            this.m_heartText = ASwingHelper.makeTextField("0", EmbeddedArt.titleFont, 16, EmbeddedArt.titleColor, 3);
            this.m_heartText.filters = EmbeddedArt.newtitleFilters;
            this.m_heartText2 = ASwingHelper.makeTextField(CardUtil.formatTime(60), EmbeddedArt.defaultFontNameBold, 16, 16777215, 0, _loc_1);
            LoadingManager.load(Global.getAssetURL("assets/hud/Mini_Game/slices_bg.png"), this.onIconLoaded);
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical ();
            _loc_3 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.RIGHT );
            _loc_2.append(ASwingHelper.verticalStrut(-4));
            _loc_3.append(this.m_heartText);
            _loc_2.append(_loc_3);
            m_jPanel.append(_loc_2);
            _loc_4 = ASwingHelper.makeSoftBoxJPanelVertical ();
            _loc_5 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_4.append(ASwingHelper.verticalStrut(40));
            _loc_5.append(this.m_heartText2, BorderLayout.CENTER);
            _loc_4.append(_loc_5);
            m_jPanel.append(_loc_4);
            m_jWindow.setContentPane(m_jPanel);
            ASwingHelper.prepare(m_jWindow);
            m_jWindow.cacheAsBitmap = true;
            m_jWindow.show();
            this.alpha = 0;
            if (!this.m_locked)
            {
                Z_TweenLite.to(this, 1, {alpha:1});
                this.addTooltip();
                this.addCounter();
            }
            return;
        }//end

        private void  onIconLoaded2 (Event event )
        {
            Bitmap _loc_3 =null ;
            Bitmap _loc_4 =null ;
            _loc_2 =(Loader) event.target.loader;
            if (_loc_2.content instanceof Bitmap)
            {
                _loc_3 =(Bitmap) _loc_2.content;
                _loc_4 =(Bitmap) _loc_2.content;
                _loc_4.x = 9;
                _loc_4.y = 22;
                addChildAt(_loc_4, 1);
                ASwingHelper.prepare(m_jWindow);
            }
            return;
        }//end

        private void  onIconLoaded (Event event )
        {
            Bitmap _loc_3 =null ;
            Bitmap _loc_4 =null ;
            _loc_2 =(Loader) event.target.loader;
            if (_loc_2.content instanceof Bitmap)
            {
                _loc_3 =(Bitmap) _loc_2.content;
                _loc_4 =(Bitmap) _loc_2.content;
                _loc_4.x = 0;
                _loc_4.y = 12;
                addChildAt(_loc_4, 0);
                m_jPanel.setPreferredSize(new IntDimension(_loc_4.width, _loc_4.height + 12));
                m_jPanel.setMinimumSize(new IntDimension(_loc_4.width, _loc_4.height + 12));
                m_jPanel.setMaximumSize(new IntDimension(_loc_4.width, _loc_4.height + 12));
                ASwingHelper.prepare(m_jWindow);
            }
            LoadingManager.load(Global.getAssetURL("assets/hud/Mini_Game/slices_icon_ghost.png"), this.onIconLoaded2);
            return;
        }//end

        private void  onMouseClick (Event event )
        {
            return;
        }//end

        private void  addTooltip ()
        {
            return;
        }//end

        private void  addCounter ()
        {
            return;
        }//end

         public void  updateCounter (...args )
        {
            MiniGame argsvalue =null ;
            if (args.get(0) != -1)
            {
                if (this.m_heartText2)
                {
                    this.m_heartText2.setText(CardUtil.formatTime(60 - (args.get(0) as int)));
                    if (this.m_heartText2.getText() != "0")
                    {
                        this.visible = true;
                    }
                }
                else
                {
                    this.visible = false;
                }
            }
            else
            {
                argsvalue = MiniGame.getMiniGame();
                if (argsvalue != null && argsvalue.isActive())
                {
                    this.visible = true;
                    if (this.m_heartText)
                    {
                        this.m_heartText.setText(argsvalue.getScore() + " ");
                    }
                }
                else
                {
                    this.visible = false;
                }
            }
            ASwingHelper.prepare(m_jWindow);
            return;
        }//end

        public void  levelChanged (int param1 )
        {
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


