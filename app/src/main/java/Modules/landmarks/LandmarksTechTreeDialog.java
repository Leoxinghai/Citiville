package Modules.landmarks;

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
import Display.DialogUI.*;
import Modules.saga.*;

import com.greensock.*;
//import flash.display.*;
//import flash.utils.*;

    public class LandmarksTechTreeDialog extends GenericDialog
    {
        protected Vector<WonderDatum> m_wondersData;
        protected String m_chosenWonderName ;

        public  LandmarksTechTreeDialog (Item param1 )
        {
            String prerequisiteSaga ;
            Vector<LandmarkDatum> landmarksData;
            String landmark ;
            String landmarkToPass ;
            Item item ;
            Vector<SagaActDatum> sagaActData;
            wonder = param1;
            this.m_wondersData = new Vector<WonderDatum>();
            landmarks = wonder.requiredLandmarks;
            if (landmarks != null)
            {
                landmarksData = new Vector<LandmarkDatum>();
                int _loc_3 =0;
                _loc_4 = landmarks;
                for(int i0 = 0; i0 < landmarks.size(); i0++)
                {
                		landmark = landmarks.get(i0);


                    landmarkToPass = landmark;
                    item = Global.gameSettings().getItemByName(landmark);
                    if (item != null && item.blockedByExperiments)
                    {
                        landmarkToPass;
                    }
                    landmarksData.push(new LandmarkDatum(landmarkToPass));
                }
            }
            prerequisiteSaga = wonder.prerequisiteSaga;
            if (prerequisiteSaga != null && prerequisiteSaga.length > 0)
            {
                sagaActData = new Vector<SagaActDatum>();
                SagaManager .instance .forEachActInSaga (prerequisiteSaga ,void  (String param1 )
            {
                sagaActData.push(new SagaActDatum(prerequisiteSaga, param1));
                return;
            }//end
            );
            }
            this.m_wondersData.push(new WonderDatum(wonder.name, landmarksData, sagaActData));
            this.m_chosenWonderName = wonder.name;
            super("", "LandmarksTechTree", GenericDialogView.TYPE_NOBUTTONS, null, "", "", true);
            return;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            return new LandmarksTechTreeDialogView(param1, this.m_chosenWonderName, this.m_wondersData);
        }//end

         protected Array  getAssetDependencies ()
        {
            WonderDatum _loc_2 =null ;
            Dictionary _loc_3 =null ;
            LandmarkDatum _loc_4 =null ;
            SagaActDatum _loc_5 =null ;
            Array _loc_1 =new Array ();
            _loc_1.push(DelayedAssetLoader.LANDMARKS_ASSETS, DelayedAssetLoader.GENERIC_DIALOG_ASSETS);
            for(int i0 = 0; i0 < this.m_wondersData.size(); i0++)
            {
            		_loc_2 = this.m_wondersData.get(i0);

                _loc_3 = Global.gameSettings().getDisplayedStatsByName(_loc_2.name);
                _loc_1.push(_loc_3.get("picture"));
                if (_loc_2.landmarks != null)
                {
                    for(int i0 = 0; i0 < _loc_2.landmarks.size(); i0++)
                    {
                    		_loc_4 = _loc_2.landmarks.get(i0);

                        if (_loc_4.name != "dummy")
                        {
                            _loc_1.push(Global.gameSettings().getImageByNameRelativeUrl(_loc_4.name, "icon"));
                        }
                    }
                }
                if (_loc_2.sagaActs != null)
                {
                    for(int i0 = 0; i0 < _loc_2.sagaActs.size(); i0++)
                    {
                    		_loc_5 = _loc_2.sagaActs.get(i0);

                        if (_loc_5.iconUrl != null)
                        {
                            _loc_1.push(_loc_5.iconUrl);
                        }
                    }
                }
            }
            return _loc_1;
        }//end

         protected Dictionary  createAssetDict ()
        {
            String _loc_3 =null ;
            Dictionary _loc_1 =new Dictionary ();
            _loc_1.put("btn_right_normal",  m_comObject.gridlist_nav_right_normal);
            _loc_1.put("btn_right_over",  m_comObject.gridlist_nav_right_over);
            _loc_1.put("btn_right_down",  m_comObject.gridlist_nav_right_down);
            _loc_1.put("btn_left_normal",  m_comObject.gridlist_nav_left_normal);
            _loc_1.put("btn_left_over",  m_comObject.gridlist_nav_left_over);
            _loc_1.put("btn_left_down",  m_comObject.gridlist_nav_left_down);
            _loc_2 = m_assetDependencies.get(DelayedAssetLoader.LANDMARKS_ASSETS) ;
            _loc_1.put("wonders_bg_white", (DisplayObject) new _loc_2.wonders_bg_white());
            _loc_1.put("wonders_burst_lg",  _loc_2.wonders_burst_lg);
            _loc_1.put("wonders_check_lrg",  _loc_2.wonders_check_lrg);
            _loc_1.put("wonders_quests_background_full", (DisplayObject) new _loc_2.wonders_quests_background_full());
            _loc_1.put("wonders_sam",  _loc_2.wonders_sam);
            _loc_1.put("wonders_burst_tiny",  _loc_2.wonders_burst_tiny);
            _loc_1.put("wonders_flowArrow",  _loc_2.wonders_flowArrow);
            _loc_1.put("wonders_lock_icon",  _loc_2.wonders_lock_icon);
            _loc_1.put("wonders_questionMark_icon",  _loc_2.wonders_questionMark_icon);
            for(int i0 = 0; i0 < m_assetDependencies.size(); i0++)
            {
            		_loc_3 = m_assetDependencies.get(i0);

                if (m_assetDependencies.get(_loc_3) instanceof Bitmap)
                {
                    _loc_1.put(_loc_3,  m_assetDependencies.get(_loc_3));
                }
            }
            return _loc_1;
        }//end

         protected void  showTween ()
        {
            boolean _loc_1 =false ;
            this.mouseChildren = false;
            this.mouseEnabled = false;
            centerPopup();
            m_content.alpha = 0;

            m_content.scaleY = 1;
            m_content.scaleX = 1;
            TweenLite.to(m_content, TWEEN_TIME, {alpha:1, onComplete:onShowComplete});
            return;
        }//end

    }



