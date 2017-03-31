package Modules.guide;

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

import Display.DialogUI.*;
import Engine.*;
import Engine.Classes.*;
import Engine.Helpers.*;
import Engine.Managers.*;
import Modules.guide.ui.*;
import com.adobe.utils.*;
//import flash.display.*;
//import flash.geom.*;
//import flash.utils.*;

    public class Guide extends GuideObject
    {
        protected GuideCallbackHelper m_callbackHelper ;
        protected GuideActionReader m_reader ;
        protected Dictionary m_triggers ;
        protected Dictionary m_callbacks ;
        protected Dictionary m_names ;
        protected Dictionary m_sequences ;
        protected GuideMask m_mask =null ;
        private GuideMask m_loadingMask =null ;
        private int m_disableZoomCount =0;
        protected Array m_arrows ;
        protected Array m_dialogs ;
        protected Array m_guideTiles ;
        private double m_lastUpdateTime =0;

        public void  Guide ()
        {
            this.m_triggers = new Dictionary();
            this.m_callbacks = new Dictionary();
            this.m_names = new Dictionary();
            this.m_sequences = new Dictionary();
            this.m_arrows = new Array();
            this.m_dialogs = new Array();
            this.m_guideTiles = new Array();
            this.m_callbackHelper = new GuideCallbackHelper();
            this.m_callbackHelper.registerHandlers(this);
            this.m_reader = new GuideActionReader(this);
            this.m_reader.readActions();
            return;
        }//end

        public boolean  isActive ()
        {
            GuideSequence _loc_1 =null ;
            for(int i0 = 0; i0 < DictionaryUtil.getValues(this.m_sequences).size(); i0++)
            {
            		_loc_1 = DictionaryUtil.getValues(this.m_sequences).get(i0);

                if (_loc_1.isActive())
                {
                    return true;
                }
            }
            return false;
        }//end

        public void  loadFirstStepAssets ()
        {
            Global.delayedAssets.startPreloadingUrls(GuideUtils.GUIDE_ASSETS_FIRST_STEP, LoadingManager.PRIORITY_HIGH);
            return;
        }//end

        public void  loadLaterStepAssets ()
        {
            Global.delayedAssets.startPreloadingUrls(GuideUtils.GUIDE_ASSETS_LATER_STEPS, LoadingManager.PRIORITY_LOW);
            return;
        }//end

        public void  notify (String param1 )
        {
            GuideSequence _loc_3 =null ;
            if (param1 == null || param1.length < 1)
            {
                return;
            }
            _loc_2 = this.m_triggers.get(param1) ;
            if (_loc_2 != null)
            {
                for(int i0 = 0; i0 < _loc_2.size(); i0++)
                {
                		_loc_3 = _loc_2.get(i0);

                    _loc_3.start();
                }
            }
            return;
        }//end

        public void  update ()
        {
            double _loc_2 =0;
            GuideSequence _loc_3 =null ;
            _loc_1 = getTimer();
            if (this.m_lastUpdateTime > 0)
            {
                _loc_2 = (_loc_1 - this.m_lastUpdateTime) / 1000;
                this.m_lastUpdateTime = _loc_1;
                for(int i0 = 0; i0 < this.m_sequences.size(); i0++)
                {
                		_loc_3 = this.m_sequences.get(i0);

                    _loc_3.update(_loc_2);
                }
            }
            else
            {
                this.m_lastUpdateTime = _loc_1;
            }
            return;
        }//end

        public void  cleanup ()
        {
            GuideSequence _loc_1 =null ;
            for(int i0 = 0; i0 < this.m_sequences.size(); i0++)
            {
            		_loc_1 = this.m_sequences.get(i0);

                _loc_1.cleanup();
            }
            this.removeMask();
            this.removeArrows();
            this.removeDialogs();
            this.removeGuideTiles();
            return;
        }//end

        public void  displayLoadingMask ()
        {
            if (this.m_loadingMask == null)
            {
                this.m_loadingMask = new GuideMask();
                this.m_loadingMask.displayMask(GuideConstants.MASK_GAME_AND_BOTTOMBAR);
                this.disableZoom();
            }
            return;
        }//end

        public void  removeLoadingMask ()
        {
            if (this.m_loadingMask != null)
            {
                this.m_loadingMask.removeMask();
                this.m_loadingMask = null;
                this.enableZoom();
            }
            return;
        }//end

        public void  displayMaskWithParent (DisplayObjectContainer param1 ,int param2 =-1,boolean param3 =true )
        {
            if (this.m_mask == null && param1 != null)
            {
                this.m_mask = new GuideMask();
                this.m_mask.displayMaskWithParent(param1, param2, param3);
                this.disableZoom();
            }
            return;
        }//end

        public void  displayMaskUI (int param1 =0,double param2)
        {
            if (this.m_mask == null)
            {
                this.m_mask = new GuideMask();
                this.m_mask.displayMask(param1, param2);
                this.disableZoom();
            }
            return;
        }//end

        public void  removeMask ()
        {
            if (this.m_mask != null)
            {
                this.m_mask.removeMask();
                this.m_mask = null;
                this.enableZoom();
            }
            return;
        }//end

        public void  removeArrows ()
        {
            GuideArrow _loc_1 =null ;
            for(int i0 = 0; i0 < this.m_arrows.size(); i0++)
            {
            		_loc_1 = this.m_arrows.get(i0);

                _loc_1.release();
            }
            this.m_arrows = new Array();
            return;
        }//end

        public void  removeDialogs ()
        {
            GenericDialog _loc_1 =null ;
            for(int i0 = 0; i0 < this.m_dialogs.size(); i0++)
            {
            		_loc_1 = this.m_dialogs.get(i0);

                _loc_1.close();
            }
            this.m_dialogs = new Array();
            return;
        }//end

        public void  displaySpotLightObject (WorldObject param1 ,Point param2 ,int param3 ,int param4 ,double param5 =0,boolean param6 =false )
        {
            _loc_7 = param1.getPositionNoClone ();
            Point _loc_8 =new Point(_loc_7.x ,_loc_7.y );
            this.displaySpotLightOnTile(_loc_8, param2, param3, param4, param5, param6);
            return;
        }//end

        public void  displaySpotLightOnTile (Point param1 ,Point param2 ,int param3 ,int param4 ,double param5 =0,boolean param6 =false )
        {
            _loc_7 = this.tilePosToStagePos(param1 );
            this.displaySpotLight(Global.ui, _loc_7, param2, param3, param4, param5, param6, Utilities.bind(this.tilePosToStagePos, null, [param1]));
            return;
        }//end

        public void  displaySpotLight (DisplayObjectContainer param1 ,Point param2 ,Point param3 ,int param4 ,int param5 ,double param6 =0,boolean param7 =false ,Function param8 =null )
        {
            if (this.m_mask != null)
            {
                ErrorManager.addError("Spotlight applied twice!");
                return;
            }
            if (param1 == null)
            {
                ErrorManager.addError("Failed to attach spotlight!");
            }
            this.m_mask = new GuideMask();
            this.m_mask.displaySpotLight(param1, param2, param3, param4, param5, param6, param7, param8);
            this.disableZoom();
            return;
        }//end

        public void  displayWeakSpotLight (DisplayObjectContainer param1 ,Point param2 ,Point param3 ,int param4 ,int param5 ,double param6 =0,boolean param7 =false ,Function param8 =null )
        {
            if (this.m_mask != null)
            {
                ErrorManager.addError("Spotlight applied twice!");
                return;
            }
            if (param1 == null)
            {
                ErrorManager.addError("Failed to attach spotlight!");
            }
            this.m_mask = new WeakGuideMask(this);
            this.m_mask.displaySpotLight(param1, param2, param3, param4, param5, param6, param7, param8);
            this.disableZoom();
            return;
        }//end

        public GuideArrow  displayLotArrow (String param1 ,double param2 ,double param3 ,double param4 =3,double param5 =3,int param6 =3,boolean param7 =false )
        {
            Point _loc_8 =new Point(param2 ,param3 );
            _loc_8 = IsoMath.viewportToStage(_loc_8);
            IsoMath.viewportToStage(_loc_8).x = _loc_8.x - Global.ui.x;
            GuideArrow _loc_9 =new GuideArrow(param1 ,_loc_8.x ,_loc_8.y ,param4 ,param5 ,param6 ,param7 );
            this.trackArrow(_loc_9);
            return _loc_9;
        }//end

        public GuideArrow  displayArrow (String param1 ,double param2 ,double param3 ,double param4 =3,double param5 =3,int param6 =3,boolean param7 =false ,boolean param8 =false ,boolean param9 =false )
        {
            GuideArrow _loc_10 =new GuideArrow(param1 ,param2 ,param3 ,param4 ,param5 ,param6 ,param7 ,param8 ,param9 );
            this.trackArrow(_loc_10);
            return _loc_10;
        }//end

        public GuideTile  displayGuideTile (Vector3 param1 ,double param2 ,double param3 )
        {
            GuideTile _loc_4 =new GuideTile(param1 ,param2 ,param3 );
            _loc_4.drawGuideTile(Constants.COLOR_VALID_PLACEMENT);
            this.trackGuideTile(_loc_4);
            return _loc_4;
        }//end

        public void  removeGuideTiles ()
        {
            GuideTile _loc_1 =null ;
            for(int i0 = 0; i0 < this.m_guideTiles.size(); i0++)
            {
            		_loc_1 = this.m_guideTiles.get(i0);

                _loc_1.cleanup();
            }
            this.m_guideTiles = new Array();
            return;
        }//end

        public void  trackGuideTile (GuideTile param1 )
        {
            this.m_guideTiles.push(param1);
            return;
        }//end

        public Array  guideTiles ()
        {
            return this.m_guideTiles;
        }//end

        public void  onResize ()
        {
            GuideArrow _loc_1 =null ;
            if (this.m_mask != null)
            {
                this.m_mask.resize();
            }
            for(int i0 = 0; i0 < this.m_arrows.size(); i0++)
            {
            		_loc_1 = this.m_arrows.get(i0);

                _loc_1.onResize();
            }
            return;
        }//end

        public void  trackArrow (GuideArrow param1 )
        {
            this.m_arrows.push(param1);
            return;
        }//end

        public void  trackDialog (GenericDialog param1 )
        {
            this.m_dialogs.push(param1);
            return;
        }//end

        public void  registerTrigger (String param1 ,GuideSequence param2 )
        {
            if (param1.length < 1 || param2 == null)
            {
                return;
            }
            if (this.m_triggers.get(param1) == null)
            {
                this.m_triggers.put(param1,  new Array());
                this.m_triggers.get(param1).push(param2);
            }
            else
            {
                this.m_triggers.get(param1).push(param2);
            }
            return;
        }//end

        public void  registerSequence (GuideSequence param1 )
        {
            if (param1 != null)
            {
                if (this.m_sequences.get(param1.m_name) == null)
                {
                    this.m_sequences.put(param1.m_name,  param1);
                    this.registerTrigger(param1.m_trigger, param1);
                    ;
                }
            }
            return;
        }//end

        public void  registerCallback (String param1 ,Function param2 )
        {
            if (param1 != null && param1.length > 0 && param2 != null)
            {
                if (this.m_callbacks.get(param1) == null)
                {
                    this.m_callbacks.put(param1,  param2);
                }
            }
            return;
        }//end

        public Function  verifyCallback (String param1 )
        {
            if (param1 != null && param1.length > 0)
            {
                return this.m_callbacks.get(param1);
            }
            return null;
        }//end

        public void  unregisterCallback (String param1 )
        {
            if (this.m_callbacks.get(param1) != null)
            {
                delete this.m_callbacks.get(param1);
            }
            return;
        }//end

        public void  registerObject (String param1 ,DisplayObject param2 )
        {
            if (param1 != null && param1.length > 0 && param2 != null)
            {
                this.m_names.put(param1,  param2);
            }
            return;
        }//end

        public void  unregisterObject (String param1 )
        {
            if (this.m_names.get(param1) != null)
            {
                delete this.m_names.get(param1);
            }
            return;
        }//end

        public DisplayObject  getObjectByName (String param1 )
        {
            String firstChar ;
            int hasHier ;
            String regname ;
            Array parts ;
            name = param1;
            if (name == null || name.length < 1)
            {
                return null;
            }
            try
            {
                firstChar = name.charAt(0);
                hasHier = name.indexOf(".");
                if (firstChar == "@" && hasHier == -1)
                {
                    regname = name.substr(1);
                    return this.getObjectByRegistration(regname);
                }
                else
                {
                    parts = name.split(".");
                    return this.getObjectByHier(parts);
                }
            }
            catch (err:Error)
            {
                return null;
            }
            return null;
        }//end

        public DisplayObject  getObjectByRegistration (String param1 )
        {
            if (param1 == null || param1.length < 1)
            {
                return null;
            }
            return this.m_names.get(param1);
        }//end

        public DisplayObject  getObjectByHier (Array param1 )
        {
            String _loc_4 =null ;
            String _loc_5 =null ;
            if (param1 == null || param1.length < 1)
            {
                return null;
            }
            DisplayObject _loc_2 =null ;
            DisplayObject _loc_3 =null ;
            _loc_6 = param1.length ;
            int _loc_7 =0;
            while (_loc_7 < _loc_6)
            {

                _loc_5 = param1.get(_loc_7);
                _loc_4 = _loc_5.charAt(0);
                if (_loc_4 == "@")
                {
                    _loc_5 = _loc_5.substr(1);
                    _loc_2 = this.getObjectByRegistration(_loc_5);
                }
                else
                {
                    if (_loc_3 == null)
                    {
                        _loc_3 = Global.stage;
                    }
                    _loc_2 = GuideUtils.findChild((DisplayObjectContainer)_loc_3, _loc_5);
                }
                if (_loc_2 == null)
                {
                    return null;
                }
                _loc_3 = _loc_2;
                _loc_7++;
            }
            return _loc_2;
        }//end

        public GuideSequence  getActiveSequence ()
        {
            GuideSequence _loc_1 =null ;
            for(int i0 = 0; i0 < DictionaryUtil.getValues(this.m_sequences).size(); i0++)
            {
            		_loc_1 = DictionaryUtil.getValues(this.m_sequences).get(i0);

                if (_loc_1.isActive())
                {
                    return _loc_1;
                }
            }
            return null;
        }//end

        protected void  disableZoom ()
        {
            this.m_disableZoomCount++;
            if (this.m_disableZoomCount > 0)
            {
                Global.world.enableZoom(false);
            }
            return;
        }//end

        private void  enableZoom ()
        {
            this.m_disableZoomCount--;
            if (this.m_disableZoomCount <= 0)
            {
                this.m_disableZoomCount = 0;
                Global.world.enableZoom(true);
            }
            return;
        }//end

        private Point  tilePosToStagePos (Point param1 )
        {
            return IsoMath.viewportToStage(IsoMath.tilePosToPixelPos(param1.x, param1.y));
        }//end

    }



