package Modules.franchise.data;

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

import Classes.util.*;

//import flash.utils.*;

    public class FranchiseData
    {
        private Vector<OwnedFranchiseData> m_ownedFranchises;
        private Dictionary m_friendFranchises ;

        public  FranchiseData (Vector<OwnedFranchiseData> param1 ,Dictionary param2 )
        {
            this.m_ownedFranchises = param1;
            this.m_friendFranchises = param2;
            return;
        }//end

        public OwnedFranchiseData Vector  ownedFranchises ().<>
        {
            return this.m_ownedFranchises;
        }//end

        public Dictionary  friendFranchises ()
        {
            return this.m_friendFranchises;
        }//end

        public static FranchiseData  loadObject (Object param1 )
        {
            Array _loc_3 =null ;
            int _loc_4 =0;
            Vector _loc_5.<OwnedFranchiseData >=null ;
            int _loc_6 =0;
            Array _loc_7 =null ;
            Dictionary _loc_8 =null ;
            String _loc_9 =null ;
            String _loc_10 =null ;
            Object _loc_11 =null ;
            Dictionary _loc_12 =null ;
            String _loc_13 =null ;
            Object _loc_14 =null ;
            FriendFranchiseData _loc_15 =null ;
            FranchiseData _loc_2 =null ;
            if (param1 !=null)
            {
                _loc_3 = param1.get("owned_franchises");
                _loc_4 = _loc_3.length;
                _loc_5 = new Vector<OwnedFranchiseData>(_loc_4);
                _loc_6 = 0;
                while (_loc_6 < _loc_4)
                {

                    _loc_5.put(_loc_6,  OwnedFranchiseData.loadObject(_loc_3.get(_loc_6)));
                    _loc_6++;
                }
                _loc_7 = param1.get("friend_franchises");
                _loc_8 = new Dictionary();
                for(int i0 = 0; i0 < _loc_7.size(); i0++)
                {
                		_loc_9 = _loc_7.get(i0);

                    _loc_10 = GameUtil.formatServerUid(_loc_9);
                    _loc_11 = _loc_7.get(_loc_9);
                    _loc_12 = new Dictionary();
                    for(int i0 = 0; i0 < _loc_11.size(); i0++)
                    {
                    		_loc_13 = _loc_11.get(i0);

                        _loc_14 = _loc_11.get(_loc_13);
                        _loc_15 = FriendFranchiseData.loadObject(_loc_14);
                        _loc_12.put(_loc_13,  _loc_15);
                    }
                    _loc_8.put(_loc_10,  _loc_12);
                }
                _loc_2 = new FranchiseData(_loc_5, _loc_8);
            }
            return _loc_2;
        }//end

    }



