package Modules.pickthings;

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
import Modules.pickthings.Display.*;
import Modules.pickthings.transactions.*;
import deng.utils.*;
//import flash.utils.*;
import org.aswing.flyfish.util.*;

    public class PickThingsManager
    {
        private Object m_featureData ;
        private Dictionary m_parsedFeatureData ;
        private Object m_additionalClientData ;
        public static  String FEATURE_TYPE ="pickThingsGame";
        public static  int UNIQUE_THRESHOLD =2;
        private static PickThingsManager m_instance ;

        public  PickThingsManager ()
        {
            return;
        }//end

        public void  loadFeatureData (Object param1 )
        {
            this.m_featureData = param1;
            this.m_parsedFeatureData = new Dictionary();
            return;
        }//end

        public int  getNumRewards (String param1 )
        {
            return this.m_parsedFeatureData.get(param1).get("rewardCount");
        }//end

        public Array  getPieces (String param1 )
        {
            ByteArray _loc_2 =new ByteArray ();
            _loc_2.writeMultiByte(param1, "iso-8859-1");
            _loc_3 = this.m_parsedFeatureData.get(param1) ;
            _loc_4 = ChecksumUtil.Adler32(_loc_2,0,_loc_2.length());
            Array _loc_5 =new Array ();
            double _loc_6 =0;
            while (_loc_6 < 30)
            {

                _loc_4 = _loc_4 * 16807 % 2147483647;
                _loc_5.push(_loc_4 % 3);
                _loc_6 = _loc_6 + 1;
            }
            return _loc_5;
        }//end

        public Dictionary  getDataForGameBoard (String param1 )
        {
            Dictionary _loc_2 =null ;
            Object _loc_3 =null ;
            Dictionary _loc_4 =null ;
            Array _loc_5 =null ;
            Array _loc_6 =null ;
            String _loc_7 =null ;
            int _loc_8 =0;
            String _loc_9 =null ;
            String _loc_10 =null ;
            double _loc_11 =0;
            String _loc_12 =null ;
            int _loc_13 =0;
            int _loc_14 =0;
            if (this.m_parsedFeatureData.hasOwnProperty(param1))
            {
                return this.m_parsedFeatureData.get(param1);
            }
            _loc_2 = Global.gameSettings().getPickThingsConfig(param1);
            _loc_4 = new Dictionary();
            _loc_5 = new Array();
            this.m_parsedFeatureData.put(param1,  _loc_4);
            if (!this.m_featureData.hasOwnProperty(param1) || !this.m_featureData.get(param1))
            {
                _loc_3 = new Object();
                _loc_3.put("numPlays",  1);
                _loc_3.put("freeTurns",  1);
                _loc_3.put("rewardsEarned",  new Object());
                _loc_10 = "";
                _loc_11 = 0;
                while (_loc_11 < PickThingsDialogView.NUM_THINGS)
                {

                    _loc_10 = _loc_10 + "0";
                    _loc_11 = _loc_11 + 1;
                }
                _loc_3.put("boardState",  _loc_10);
                for(int i0 = 0; i0 < _loc_2.get("distribution").size(); i0++)
                {
                		_loc_12 = _loc_2.get("distribution").get(i0);

                    _loc_3.get("rewardsEarned").put(_loc_12,  0);
                }
                this.m_featureData.put(param1,  _loc_3);
                _loc_13 = 0;
                while (_loc_13 < PickThingsDialogView.NUM_THINGS)
                {

                    _loc_5.push(false);
                    _loc_13++;
                }
            }
            else
            {
                _loc_3 = this.m_featureData.get(param1);
                _loc_14 = 0;
                while (_loc_14 < PickThingsDialogView.NUM_THINGS)
                {

                    _loc_5.push(((String)_loc_3.get("boardState")).charAt(_loc_14) == "1");
                    _loc_14++;
                }
            }
            _loc_4.put("boardState",  _loc_5);
            _loc_4.put("pieces",  this.getPieces(param1));
            _loc_4.put("rewardsEarned",  new Dictionary());
            for(int i0 = 0; i0 < _loc_2.get("distribution").size(); i0++)
            {
            		_loc_7 = _loc_2.get("distribution").get(i0);

                if (_loc_3.get("rewardsEarned").get(_loc_7))
                {
                    _loc_4.get("rewardsEarned").put(_loc_7,  _loc_3.get("rewardsEarned").get(_loc_7));
                    continue;
                }
                _loc_4.get("rewardsEarned").put(_loc_7,  0);
            }
            _loc_4.put("numPlays",  _loc_3.get("numPlays"));
            _loc_4.put("freeTurns",  _loc_3.get("freeTurns"));
            _loc_8 = 0;
            for(int i0 = 0; i0 < _loc_4.get("rewardsEarned").size(); i0++)
            {
            		_loc_9 = _loc_4.get("rewardsEarned").get(i0);

                _loc_8 = _loc_8 + _loc_4.get("rewardsEarned").get(_loc_9);
            }
            _loc_4.put("rewardCount",  _loc_8);
            return _loc_4;
        }//end

        private void  resetMiniGame (String param1 )
        {
            String _loc_2 =null ;
            Array _loc_3 =null ;
            int _loc_4 =0;
            for(int i0 = 0; i0 < this.m_parsedFeatureData.get(param1).get("rewardsEarned").size(); i0++)
            {
            		_loc_2 = this.m_parsedFeatureData.get(param1).get("rewardsEarned").get(i0);

                this.m_parsedFeatureData.get(param1).get("rewardsEarned").put(_loc_2,  0);
            }
            _loc_5 = this.m_parsedFeatureData.get(param1) ;
            String _loc_6 ="numPlays";
            _loc_7 = this.m_parsedFeatureData.get(param1).get( "numPlays") +1;
            _loc_5.put(_loc_6,  _loc_7);
            this.m_parsedFeatureData.get(param1).put("rewardCount",  0);
            _loc_3 = new Array();
            _loc_4 = 0;
            while (_loc_4 < PickThingsDialogView.NUM_THINGS)
            {

                _loc_3.push(false);
                _loc_4++;
            }
            this.m_parsedFeatureData.get(param1).put("boardState",  _loc_3);
            this.m_parsedFeatureData.get(param1).put("pieces",  this.getPieces(param1));
            return;
        }//end

        private Array  rollNewReward (String param1 )
        {
            String _loc_7 =null ;
            String _loc_8 =null ;
            double _loc_9 =0;
            int _loc_10 =0;
            _loc_2 =Global.gameSettings().getPickThingsConfig(param1 ).get( "distribution") ;
            _loc_3 =Global.gameSettings().getPickThingsConfig(param1 ).get( "rewards") ;
            _loc_4 =Global.gameSettings().getPickThingsConfig(param1 ).get( "multiply") ;
            _loc_5 = this.m_parsedFeatureData.get(param1) ;
            Array _loc_6 =new Array();
            _loc_10 = 0;
            while (_loc_10 < _loc_3.length())
            {

                _loc_7 = _loc_3.get(_loc_10);
                _loc_8 = _loc_4.get(_loc_10);
                if (_loc_5.get("rewardCount") > UNIQUE_THRESHOLD || _loc_5.get("rewardsEarned").get(_loc_7) == 0)
                {
                    _loc_9 = 0;
                    while (_loc_9 < _loc_2.get(_loc_7) - _loc_5.get("rewardsEarned").get(_loc_7))
                    {

                        _loc_6.push(.get(_loc_7, _loc_8));
                        _loc_9 = _loc_9 + 1;
                    }
                }
                _loc_10++;
            }
            _loc_11 = SecureRand.randPerFeature(0,(_loc_6.length-1),FEATURE_TYPE);
            return _loc_6.get(_loc_11);
        }//end

        public double  getFreePlays (String param1 )
        {
            return this.getDataForGameBoard(param1).get("freeTurns");
        }//end

        public Array  getNewReward (String param1 ,int param2 )
        {
            int _loc_8 =0;
            _loc_3 = this.m_parsedFeatureData.get(param1) ;
            if (!_loc_3)
            {
                return .get("fail!", "0");
            }
            _loc_3.get("boardState").put(param2,  true);
            _loc_4 = this.rollNewReward(param1 );
            _loc_5 = this.rollNewReward(param1 ).get(0) ;
            _loc_6 = MathUtils.parseInteger(_loc_4.get(1) );
            _loc_9 = _loc_3.get("rewardsEarned") ;
            _loc_10 = _loc_5;
            _loc_11 = _loc_3.get("rewardsEarned").get(_loc_5) +1;
            _loc_9.put(_loc_10,  _loc_11);

            _loc_3.put("rewardCount",  _loc_3.get("rewardCount") + 1);

            Global.player.inventory.addItems(_loc_5, _loc_6, true);
            _loc_7 =Global.gameSettings().getPickThingsConfig(param1 ).get( "replayCost") ;
            if (_loc_3.get("freeTurns") > 0)
            {
                _loc_3.put("freeTurns",  Math.max(0, (_loc_3.get("freeTurns") - 1)));
            }
            else if (Global.player.cash >= _loc_7)
            {
                _loc_8 = Global.player.cash - _loc_7;
                Global.player.cash = _loc_8;
            }
            GameTransactionManager.addTransaction(new TPickThingsGetNewReward(param1, param2));
            if (_loc_3.get("rewardCount") >= PickThingsDialogView.NUM_THINGS)
            {
                this.resetMiniGame(param1);
            }
            return _loc_4;
        }//end

        public static PickThingsManager  instance ()
        {
            if (!PickThingsManager.m_instance)
            {
                PickThingsManager.m_instance = new PickThingsManager;
            }
            return PickThingsManager.m_instance;
        }//end

    }



