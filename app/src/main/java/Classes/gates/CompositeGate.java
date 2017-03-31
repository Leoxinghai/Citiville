package Classes.gates;

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
import Display.LandmarkUI.*;
import Modules.hotels.*;
import Modules.ships.*;
//import flash.utils.*;

    public class CompositeGate extends AbstractGate
    {
        protected Array m_checkedGates ;
        protected String m_customDialog ;
        private static Array m_allowedDialogClasses =.get(LandmarkBuildDialog ,HotelsDialog ,PierUpgradesDialog) ;

        public  CompositeGate (String param1 )
        {
            super(param1);
            return;
        }//end

         public void  loadFromXML (XML param1 )
        {
            super.loadFromXML(param1);
            this.m_customDialog = String(param1.@dialog);
            return;
        }//end

         public boolean  checkForKeys ()
        {
            MapResource _loc_3 =null ;
            IGate _loc_4 =null ;
            String _loc_5 =null ;
            IGate _loc_6 =null ;
            boolean _loc_1 =true ;
            this.m_checkedGates = new Array();
            _loc_2 = this.factory ;
            if (_loc_2)
            {
                _loc_3 =(MapResource) Global.world.getObjectById(m_targetObjectId);
                if (_loc_3)
                {
                    for(int i0 = 0; i0 < m_keys.size(); i0++)
                    {
                    		_loc_5 = m_keys.get(i0);

                        _loc_4 = _loc_2.loadGateFromXML(Global.gameSettings().getItemByName(m_unlockItemName), _loc_3, _loc_5, null);
                        if (_loc_4 && _loc_4.loadType != AbstractGate.LOAD_TYPE_DYNAMIC)
                        {
                            _loc_6 = _loc_2.loadGateFromObject(_loc_3.getSaveObject(), _loc_3.getItem(), _loc_3, null);
                            if (_loc_6)
                            {
                                _loc_4 = _loc_6;
                            }
                        }
                        if (_loc_4 && _loc_4.checkForKeys())
                        {
                            this.m_checkedGates.push(_loc_4);
                            continue;
                        }
                        _loc_1 = false;
                        break;
                    }
                }
                else
                {
                    _loc_1 = false;
                }
            }
            return _loc_1;
        }//end

         public int  keyProgress (String param1 )
        {
            this.checkForKeys();
            return this.m_checkedGates.length;
        }//end

         public void  displayGate (String param1 ,String param2 ,Object param3 =null )
        {
            Class _loc_4 =null ;
            Item _loc_5 =null ;
            _loc_6 = null;
            if (this.m_customDialog)
            {
                _loc_4 =(Class) getDefinitionByName(this.m_customDialog);
                _loc_5 = Global.gameSettings().getItemByName(m_unlockItemName);
                _loc_6 = new _loc_4(Global.world.getObjectById(m_targetObjectId), _loc_5);
                UI.displayPopup(_loc_6, false, "compositeGateDialog", false);
            }
            return;
        }//end

         protected void  takeKeys ()
        {
            IGate _loc_1 =null ;
            for(int i0 = 0; i0 < this.m_checkedGates.size(); i0++)
            {
            		_loc_1 = this.m_checkedGates.get(i0);

                if (!_loc_1.unlockGate())
                {
                    throw new Error("CompositeGate.takeKeys() failed because unlockGate failed on sub-gate " + _loc_1.name);
                }
            }
            return;
        }//end

        public IGate  getGate (MapResource param1 ,String param2 )
        {
            IGate _loc_3 =null ;
            Item _loc_5 =null ;
            _loc_4 = getKeyArray();
            if (getKeyArray().indexOf(param2) >= 0)
            {
                _loc_5 = Global.gameSettings().getItemByName(m_unlockItemName);
                _loc_3 =(AbstractGate) factory.loadGateFromXML(_loc_5, param1, param2, null);
            }
            return _loc_3;
        }//end

    }


