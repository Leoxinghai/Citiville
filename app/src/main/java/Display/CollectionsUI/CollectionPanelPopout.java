package Display.CollectionsUI;

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
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Display.aswingui.*;
import Display.hud.*;
import com.greensock.*;
import com.greensock.easing.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
//import flash.utils.*;
import org.aswing.*;

import com.xinghai.Debug;

    public class CollectionPanelPopout extends GenericDialog
    {
        protected int m_itemCount =0;
        protected Collection m_currentCollection ;
        protected boolean m_isOpen ;
        protected boolean m_mouseIsOver ;
        protected boolean m_waitingForMouseOut =false ;
        protected double m_shownTime ;
        protected boolean m_isActive ;
        protected Array m_completedCollectionsShown ;
        protected boolean m_fadeInCellLoaded =false ;
public static CollectionPanelPopout m_instance ;
public static  int OFFSET_X =130;
public static  int OFFSET_Y =90;
        public static  int MAX_SLOTS =5;
        public static  int SLOT_SIDE_LENGTH =54;
        public static  int GRID_MARGIN =5;
        public static  int TOTAL_WIDTH =280;
        public static  int NAME_HEIGHT =25;
        public static  int DISAPPEAR_TIMEOUT =2;

        public  CollectionPanelPopout (Function param1)
        {
            super("", "", 0, param1, "", "", false);
            m_centered = false;
            this.m_completedCollectionsShown = new Array();
            Debug.debug6("CollectionPanelPopout");
            return;
        }//end

         protected void  init ()
        {
            m_holder = new Sprite();
            this.addChild(m_holder);
            m_jwindow = new JWindow(m_holder);
            m_content = m_holder;
            m_content.addEventListener("close", closeMe, false, 0, true);
            this.x = this.getOffstageX();
            this.y = this.getStageY();
            this.m_isOpen = false;
            this.m_isActive = false;
            return;
        }//end

         protected boolean  doTrackDialogActions ()
        {
            return false;
        }//end

         protected void  loadAssets ()
        {
            Global.delayedAssets.get(DelayedAssetLoader.INVENTORY_ASSETS, makeAssets);
            return;
        }//end

         protected void  onAssetsLoaded (Event event =null )
        {
            Dictionary _loc_2 =new Dictionary ();
            _loc_2.put("collectionFlyout",  new (DisplayObject)m_comObject.collectionFlyout());
            m_jpanel = new CollectionPanelPopoutView(_loc_2, m_message, m_title, m_type, m_callback);
            finalizeAndShow();
            ASwingHelper.prepare(m_jpanel);
            ASwingHelper.prepare(m_jwindow);
            this.buttonMode = true;
            this.mouseChildren = false;
            Global.hud.addChild(this);
            this.addEventListener(MouseEvent.CLICK, this.onClick);
            this.addEventListener(MouseEvent.MOUSE_OVER, this.onMouseOver);
            this.addEventListener(MouseEvent.MOUSE_OUT, this.onMouseOut);
            Global.stage.addEventListener(FullScreenEvent.FULL_SCREEN, this.onFullScreen);
            Global.stage.addEventListener(Event.RESIZE, this.onResize);
            this.onResize(null);
            return;
        }//end

        private void  onClick (Event event )
        {
            Debug.debug6("CollectionPanelPopout.onClick");
            if (!Global.guide.isActive())
            {
                UI.displayCollections(this.m_currentCollection.name);
            }
            return;
        }//end

        public void  onUpdate (double param1 )
        {

            if (!this.m_isActive || !this.m_isOpen || UI.isModalDialogOpen)
            {
                return;
            }
            if (this.m_shownTime >= DISAPPEAR_TIMEOUT)
            {
                if (this.m_fadeInCellLoaded)
                {
                    this.stopAnim();
                    this.m_isActive = false;
                }
            }
            this.m_shownTime = this.m_shownTime + param1;
            return;
        }//end

        protected void  restartDisappearTimer ()
        {
            this.m_shownTime = 0;
            this.m_isActive = true;
            return;
        }//end

        public boolean  addItemByName (String param1 ,Point param2 ,Point param3 )
        {
            Debug.debug6("CollectionPanelPopout.addItemByName"+param1);

            Array _loc_6 =null ;
            int _loc_7 =0;
            _loc_4 = Global.gameSettings().getItemByName(param1);
            _loc_5 = Global.gameSettings().getCollectionByCollectableName(_loc_4.name);
            if (Global.gameSettings().getCollectionByCollectableName(_loc_4.name))
            {
                this.m_itemCount++;
                this.restartDisappearTimer();
                this.openPopout(_loc_5);
                _loc_6 = _loc_5.getCollectableNames();
                _loc_7 = 0;
                while (_loc_7 < _loc_6.length())
                {

                    if (_loc_6.get(_loc_7) == param1)
                    {
                        if (param2)
                        {
                            param2.x = this.getOnstageX() + GRID_MARGIN + (SLOT_SIDE_LENGTH + 2) * _loc_7;
                            param2.y = this.getStageY();
                        }
                        if (param3)
                        {
                            param3.x = SLOT_SIDE_LENGTH;
                            param3.y = SLOT_SIDE_LENGTH + 4;
                        }
                        return true;
                    }
                    _loc_7++;
                }
            }
            return false;
        }//end

        protected void  openPopout (Collection param1 )
        {
            Debug.debug6("CollectionPanelPopout.openPopout");

            if (!this.m_isOpen && hasLoaded() && param1)
            {
                this.rebuildCells(param1);
                this.startAnim();
            }
            return;
        }//end

        protected void  rebuildCells (Collection param1 )
        {
            Debug.debug6("CollectionPanelPopout.rebuildCells");
            this.m_currentCollection = param1;
            if (this.m_currentCollection)
            {
                ((CollectionPanelPopoutView)m_jpanel).prepopulatePanelSlots(this.m_currentCollection);
                ((CollectionPanelPopoutView)m_jpanel).invalidateData();
            }
            return;
        }//end

        public void  itemAppear (String param1 )
        {
            Debug.debug6("CollectionPanelPopout.itemAppear");

            this.verifyCollection(param1);
            _loc_2 = Global.gameSettings().getItemByName(param1);
            _loc_3 = Global.gameSettings().getCollectionByCollectableName(_loc_2.name);
            ((CollectionPanelPopoutView)m_jpanel).invalidateCell(_loc_2.name, _loc_3);
            ((CollectionPanelPopoutView)m_jpanel).playFadeIn(_loc_2.name, _loc_3, this.onFadeInComplete, this.onFadeInCellLoaded);
            return;
        }//end

        public void  verifyCollection (String param1 )
        {
            Debug.debug6("CollectionPanelPopout.verifyCollection");

            _loc_2 =Global.gameSettings().getItemByName(param1 );
            _loc_3 =Global.gameSettings().getCollectionByCollectableName(_loc_2.name );
            this.restartDisappearTimer();
            this.openPopout(_loc_3);
            if (this.m_currentCollection == null || _loc_3.name != this.m_currentCollection.name)
            {
                this.rebuildCells(_loc_3);
            }
            return;
        }//end

        protected void  onFadeInComplete ()
        {
            Debug.debug6("CollectionPanelPopout.onFadeInComplete");

            GenericDialog _loc_1 =null ;
            this.m_itemCount--;
            if (this.m_currentCollection.isReadyToTradeIn() && this.m_completedCollectionsShown.indexOf(this.m_currentCollection.name) == -1)
            {
                this.m_completedCollectionsShown.push(this.m_currentCollection.name);
                if (Collection.completedEarlier(this.m_currentCollection.name))
                {
                    Sounds.play("collectionComplete");
                }
                else
                {
                    _loc_1 = new GenericDialog(ZLoc.t("Collections", "completed_collection", {collection_name:this.m_currentCollection.localizedName}));
                    UI.displayPopup(_loc_1);
                    Sounds.play("collectionComplete");
                    Collection.setCompletedEarlier(this.m_currentCollection.name, true);
                }
            }
            return;
        }//end

        protected double  getOffstageX ()
        {
            Debug.debug6("CollectionPanelPopout.getOffstageX");

            int _loc_1 =0;
            _loc_1 = (UI.DEFAULT_WIDTH - Global.ui.screenWidth) / 2;
            return Global.ui.screenWidth + _loc_1;
        }//end

        protected double  getOnstageX ()
        {
            Debug.debug6("CollectionPanelPopout.getOnstageX");

            _loc_1 = Global.hud.getComponent(HUD.COMP_CITYLEVEL).x;
            return _loc_1 - TOTAL_WIDTH + OFFSET_X;
        }//end

        protected double  getStageY ()
        {
            _loc_1 = Global.hud.getComponent(HUD.COMP_CITYLEVEL).y;
            return _loc_1 + OFFSET_Y;
        }//end

        protected void  startAnim ()
        {
            this.m_isOpen = true;
            this.x = this.getOffstageX();
            this.y = this.getStageY();
            show();
            TweenLite.to(this, 0.6, {x:this.getOnstageX(), ease:Back.easeOut});
            return;
        }//end

        protected void  stopAnim ()
        {
            Debug.debug6("CollectionPanelPopout.stopAnim");
            if (this.m_mouseIsOver)
            {
                this.m_waitingForMouseOut = true;
            }
            else
            {
                this.tweenBack();
            }
            return;
        }//end

         protected void  onHideComplete ()
        {
            Debug.debug6("CollectionPanelPopout.onHideComplete");
            super.onHideComplete();
            this.m_isOpen = false;
            if (this.m_itemCount > 0)
            {
                this.openPopout(this.m_currentCollection);
            }
            return;
        }//end

        private void  tweenBack ()
        {
void             TweenLite .to (this ,0.4,{this x .getOffstageX (),Back ease .easeIn , onComplete ()
            {
                hide();
                return;
            }//end
            });
            return;
        }//end

        protected void  onMouseOver (MouseEvent event )
        {
            Debug.debug6("CollectionPanelPopout.onMouseOver");

            this.m_mouseIsOver = true;
            return;
        }//end

        protected void  onMouseOut (MouseEvent event )
        {
            Debug.debug6("CollectionPanelPopout.onMouseOut");
            this.m_mouseIsOver = false;
            if (this.m_waitingForMouseOut)
            {
                this.m_waitingForMouseOut = false;
                this.tweenBack();
            }
            return;
        }//end

         protected void  hideTween (Function param1 )
        {
            boolean _loc_2 =false ;
            this.mouseChildren = false;
            this.mouseEnabled = _loc_2;
            param1();
            Sounds.play("dialogClose");
            return;
        }//end

         protected void  showTween ()
        {
            Debug.debug6("CollectionPanelPopout.showTween");

            boolean _loc_3 =false ;
            this.mouseChildren = false;
            this.mouseEnabled = _loc_3;
            Point _loc_1 =new Point(m_content.width ,m_content.height );
            Matrix _loc_2 =new Matrix ();
            _loc_2.translate((-_loc_1.x) / 2, (-_loc_1.y) / 2);
            _loc_2.scale(1, 1);
            _loc_2.translate(_loc_1.x / 2, _loc_1.y / 2);
            m_content.transform.matrix = _loc_2;
            if (m_centered)
            {
                centerPopup();
            }
            onShowComplete();
            return;
        }//end

         protected void  onFullScreen (FullScreenEvent event )
        {
            if (this.m_isActive && this.m_isOpen)
            {
                this.x = this.getOnstageX();
            }
            else
            {
                this.x = this.getOffstageX();
            }
            this.y = this.getStageY();
            return;
        }//end

         protected void  onResize (Event event )
        {
            this.onFullScreen(null);
            return;
        }//end

        public void  onFadeInCellLoaded (boolean param1 )
        {
            this.m_fadeInCellLoaded = param1;
            return;
        }//end

        public static CollectionPanelPopout  getInstance ()
        {
            if (!m_instance)
            {
                m_instance = new CollectionPanelPopout;
            }
            return m_instance;
        }//end

    }




