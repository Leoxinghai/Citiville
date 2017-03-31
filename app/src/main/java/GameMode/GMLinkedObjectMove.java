package GameMode;

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
import Engine.*;
import Engine.Helpers.*;
import Engine.Managers.*;
import Transactions.*;
//import flash.events.*;

    public class GMLinkedObjectMove extends GMLinkedObjectEdit
    {

        public  GMLinkedObjectMove (MapResource param1 ,boolean param2 =false )
        {
            super(param1, param2);
            return;
        }//end  

         protected void  initializeGameModes ()
        {
            GMObjectMove _loc_1 =null ;
            MapResource _loc_2 =null ;
            this.preMoveLinkedObjects();
            _loc_1 = new GMObjectMove(m_parentObject, null, null, Constants.INDEX_NONE, false, m_parentObject.getPosition());
            _loc_1.setLinkedObjectMode(true);
            m_gmObjectEditModes.push(_loc_1);
            for(int i0 = 0; i0 < m_linkedObjects.size(); i0++) 
            {
            	_loc_2 = m_linkedObjects.get(i0);
                
                _loc_1 = new GMObjectMove(_loc_2, null, null, Constants.INDEX_NONE, false, _loc_2.getPosition());
                _loc_1.setLinkedObjectMode(true);
                m_gmObjectEditModes.push(_loc_1);
            }
            return;
        }//end  

        protected void  preMoveLinkedObjects ()
        {
            Array _loc_1 =null ;
            Vector3 _loc_2 =null ;
            Vector3 _loc_3 =null ;
            MapResource _loc_4 =null ;
            MapResource _loc_5 =null ;
            switch(m_parentObject.getItemName())
            {
                case "dock_house":
                {
                    _loc_1 = new Array();
                    _loc_1 = Global.world.getObjectsByClass(Dock);
                    _loc_2 = m_parentObject.getPosition();
                    for(int i0 = 0; i0 < _loc_1.size(); i0++) 
                    {
                    	_loc_5 = _loc_1.get(i0);
                        
                        _loc_2.x = _loc_2.x - 2;
                        _loc_3 = _loc_5.getPosition();
                        _loc_5.setPosition(_loc_2.x, _loc_2.y, _loc_2.z);
                        _loc_5.onObjectDropPreTansaction(_loc_3);
                        _loc_5.conditionallyReattach(true);
                        _loc_5.onObjectDrop(_loc_3);
                    }
                    _loc_2 = m_parentObject.getPosition();
                    _loc_4 = Global.world.getObjectsByClass(HarvestableShip).get(0);
                    _loc_4.setPosition(_loc_2.x - 4, _loc_2.y + 2, _loc_2.z);
                    _loc_3 = _loc_4.getPosition();
                    _loc_4.onObjectDropPreTansaction(_loc_3);
                    _loc_4.conditionallyReattach(true);
                    _loc_4.onObjectDrop(_loc_3);
                    break;
                }
                default:
                {
                    break;
                }
            }
            return;
        }//end  

         public boolean  onMouseUp (MouseEvent event )
        {
            GMObjectMove _loc_3 =null ;
            boolean _loc_2 =true ;
            for(int i0 = 0; i0 < m_gmObjectEditModes.size(); i0++) 
            {
            	_loc_3 = m_gmObjectEditModes.get(i0);
                
                _loc_2 = _loc_3.isPositionValid();
                if (!_loc_2)
                {
                    break;
                }
            }
            if (_loc_2)
            {
                for(int i0 = 0; i0 < m_gmObjectEditModes.size(); i0++) 
                {
                	_loc_3 = m_gmObjectEditModes.get(i0);
                    
                    _loc_3.onMouseUp(event);
                }
                Global.world.popGameMode();
                Sounds.play("place_building");
                TransactionManager.addTransaction(new TLinkedObjectMove(m_parentObject, m_linkedObjects));
            }
            super.onMouseUp(event);
            return true;
        }//end  

    }



