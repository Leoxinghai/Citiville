package Modules.garden;

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
import Classes.virals.*;
import Display.*;
import Display.DialogUI.*;
import Events.*;
//import flash.events.*;

    public class GardenManager
    {
        private Object m_featureData ;
        public static  String MECHANIC_SLOTS ="slots";
        public static  String GIFT_SENDERS ="giftSenders";
        private static GardenManager m_instance ;

        public  GardenManager ()
        {
            return;
        }//end

        public void  loadFeatureData (Object param1 )
        {
            this.featureData = param1;
            if (this.featureData == null)
            {
                this.featureData = new Object();
            }
            return;
        }//end

        public Object featureData ()
        {
            return this.m_featureData;
        }//end

        public void  featureData (Object param1)
        {
            this.m_featureData = param1;
            return;
        }//end

        public boolean  isFlower (String param1 )
        {
            _loc_2 =Global.gameSettings().getItemByName(param1 );
            return _loc_2 && _loc_2.itemHasKeyword("garden_flower");
        }//end

        public boolean  canAddFlower (String param1 ,String param2 ,int param3 )
        {
            int _loc_5 =0;
            int _loc_6 =0;
            int _loc_7 =0;
            _loc_4 =Global.gameSettings().getItemByName(param2 );
            if (Global.gameSettings().getItemByName(param2))
            {
                if (param3 != 0)
                {
                    _loc_5 = this.getCurrentAmount(param1, param2);
                    _loc_6 = this.getMaxFlowerCapacity(param1);
                    _loc_7 = _loc_5 + param3;
                    if (_loc_7 < 0 || _loc_7 > _loc_6)
                    {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }//end

        public void  addFlower (String param1 ,String param2 ,int param3 =1)
        {
            if (!param1 || !param2)
            {
                return;
            }
            _loc_4 = this.getGarden(param1 );
            this.getGarden(param1).get(GardenManager.MECHANIC_SLOTS).put(param2,  Math.max(this.getCurrentAmount(param1, param2) + param3, 0));
            return;
        }//end

        public int  getCurrentAmount (String param1 ,String param2 )
        {
            if (!param1 || !param2)
            {
                return 0;
            }
            _loc_3 = this.getGarden(param1 );
            _loc_4 = (int)(_loc_3.get(GardenManager.MECHANIC_SLOTS).get(param2) );
            return int(_loc_3.get(GardenManager.MECHANIC_SLOTS).get(param2));
        }//end

        public Object  getGarden (String param1 ,boolean param2 =true )
        {
            _loc_3 = this.m_featureData.get(param1) ;
            if (!_loc_3 && param2)
            {
                _loc_3 = this.addGarden(param1);
            }
            return _loc_3;
        }//end

        public Object  getFlowers (String param1 )
        {
            Object _loc_2 =null ;
            if (param1 !=null)
            {
                _loc_2 = this.getGarden(param1);
                return _loc_2.get(GardenManager.MECHANIC_SLOTS);
            }
            return new Object();
        }//end

        public Object  getGiftSenders (String param1 )
        {
            _loc_2 = this.getGarden(param1 );
            return _loc_2.get(GardenManager.GIFT_SENDERS);
        }//end

        public void  setGiftSenders (String param1 ,Object param2 )
        {
            Object _loc_3 =null ;
            if (param2)
            {
                _loc_3 = this.getGarden(param1);
                _loc_3.put(GardenManager.GIFT_SENDERS,  param2);
            }
            return;
        }//end

        public Object  addGarden (String param1 )
        {
            if (!this.m_featureData.get(param1))
            {
                this.m_featureData.put(param1,  {});
                this.m_featureData.get(param1).put(GardenManager.GIFT_SENDERS,  new Object());
                this.m_featureData.get(param1).put(GardenManager.MECHANIC_SLOTS,  new Object());
            }
            return this.m_featureData.get(param1);
        }//end

        public void  refreshFeatureData ()
        {
            String _loc_2 =null ;
            Object _loc_3 =null ;
            String _loc_4 =null ;
            int _loc_5 =0;
            int _loc_1 =0;
            for(int i0 = 0; i0 < this.featureData.size(); i0++)
            {
            		_loc_2 = this.featureData.get(i0);

                _loc_1 = this.getMaxFlowerCapacity(_loc_2);
                _loc_3 = this.featureData.get(_loc_2);
                if (_loc_3)
                {
                    for(int i0 = 0; i0 < _loc_3.get(GardenManager.MECHANIC_SLOTS).size(); i0++)
                    {
                    		_loc_4 = _loc_3.get(GardenManager.MECHANIC_SLOTS).get(i0);

                        _loc_5 = _loc_3.get(GardenManager.MECHANIC_SLOTS).get(_loc_4);
                        _loc_3.get(GardenManager.MECHANIC_SLOTS).put(_loc_4,  int(Math.max(Math.min(_loc_5, _loc_1), 0)));
                    }
                }
            }
            return;
        }//end

        public int  getFlowerAmount (String param1 )
        {
            String _loc_4 =null ;
            _loc_2 = this.getGarden(param1 );
            int _loc_3 =0;
            for(int i0 = 0; i0 < _loc_2.get(GardenManager.MECHANIC_SLOTS).size(); i0++)
            {
            		_loc_4 = _loc_2.get(GardenManager.MECHANIC_SLOTS).get(i0);

                _loc_3 = _loc_3 + _loc_2.get(GardenManager.MECHANIC_SLOTS).get(_loc_4);
            }
            return _loc_3;
        }//end

        public int  getAmountForFlower (String param1 ,String param2 )
        {
            _loc_3 = this.getGarden(param1 );
            int _loc_4 =0;
            if (param2 in _loc_3.get(GardenManager.MECHANIC_SLOTS))
            {
                _loc_4 = _loc_3.get(GardenManager.MECHANIC_SLOTS).get(param2);
            }
            return _loc_4;
        }//end

        public int  getFlowerCapacity (String param1 )
        {
            Garden _loc_4 =null ;
            int _loc_5 =0;
            int _loc_2 =0;
            _loc_3 =Global.world.getObjectsByNames(.get(param1) );
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            		_loc_4 = _loc_3.get(i0);

                if (_loc_4)
                {
                    _loc_5 = _loc_4.getItem().gardenCapacityBonus;
                    _loc_2 = _loc_2 + _loc_5;
                }
            }
            return _loc_2;
        }//end

        public int  getMaxFlowerCapacity (String param1 )
        {
            String _loc_4 =null ;
            Item _loc_5 =null ;
            int _loc_6 =0;
            _loc_2 =Global.gameSettings().getInt("gardenBasePoolLimit",0);
            _loc_3 =Global.itemCountManager.featureData ;
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            		_loc_4 = _loc_3.get(i0);

                _loc_5 = Global.gameSettings().getItemByName(_loc_4);
                if (_loc_5 && _loc_5.gardenType == param1)
                {
                    _loc_6 = _loc_5.gardenCapacityBonus;
                    _loc_2 = _loc_2 + _loc_6 * int(_loc_3.get(_loc_4));
                }
            }
            _loc_2 = Math.max(_loc_2, 0);
            return _loc_2;
        }//end

        public Array  getGardenTypes ()
        {
            String _loc_2 =null ;
            Array _loc_1 =new Array();
            for(int i0 = 0; i0 < this.featureData.size(); i0++)
            {
            		_loc_2 = this.featureData.get(i0);

                _loc_1.push(_loc_2);
            }
            return _loc_1;
        }//end

        public void  postSendSeedFeed (String param1 ,String param2 ,boolean param3 )
        {
            if (Global.world.viralMgr.canPost(ViralType.SEND_GARDEN_SEED))
            {
                if (!param3)
                {
                    this.displaySharePopup(param1, param2);
                }
                else
                {
                    this.displaySharePopup(param1, param2, "_capacity");
                }
            }
            return;
        }//end

        private void  displaySharePopup (String param1 ,String param2 ,String param3 ="")
        {
            String _loc_5 =null ;
            int _loc_6 =0;
            Function _loc_7 =null ;
            String _loc_8 =null ;
            String _loc_9 =null ;
            boolean _loc_10 =false ;
            int _loc_11 =0;
            String _loc_12 =null ;
            Function _loc_13 =null ;
            String _loc_14 =null ;
            boolean _loc_15 =false ;
            String _loc_16 =null ;
            int _loc_17 =0;
            String _loc_18 =null ;
            String _loc_19 =null ;
            GenericDialog _loc_20 =null ;
            _loc_4 =Global.gameSettings().getItemByName(param2 );
            if (Global.gameSettings().getItemByName(param2))
            {
                _loc_5 = "";
                _loc_6 = GenericDialogView.TYPE_CUSTOM_OK;
                _loc_7 = Delegate.create(this, this.sendSeedFeedCloseCallback, [param1]);
                _loc_8 = "GardenDialog_share_flower_dialog" + param3;
                _loc_9 = "assets/dialogs/rita_bubble.png";
                _loc_10 = true;
                _loc_11 = 0;
                _loc_12 = "";
                _loc_13 = null;
                _loc_14 = ZLoc.t("Dialogs", "GardenDialog_share_flower_dialog" + param3 + "_button");
                _loc_15 = true;
                _loc_16 = ZLoc.t("Feeds", "garden_" + param1);
                _loc_17 = this.getMaxFlowerCapacity(param1);
                _loc_18 = _loc_4.localizedName;
                _loc_19 = ZLoc.t("Dialogs", "GardenDialog_share_flower_dialog" + param3 + "_message", {gardenFlower:_loc_16, flowerName:_loc_18, capacity:_loc_17});
                _loc_20 = new CharacterResponseDialog(_loc_19, _loc_5, _loc_6, _loc_7, _loc_8, _loc_9, _loc_10, _loc_11, _loc_12, _loc_13, _loc_14, _loc_15);
                UI.displayPopup(_loc_20);
            }
            return;
        }//end

        private void  sendSeedFeedCloseCallback (Event event ,String param2 )
        {
            if (event instanceof GenericPopupEvent)
            {
                if (((GenericPopupEvent)event).button == GenericDialogView.YES)
                {
                    Global.world.viralMgr.sendGardenSeedFeed(param2, 0, Global.player.cityName);
                }
            }
            return;
        }//end

        public static GardenManager  instance ()
        {
            if (!GardenManager.m_instance)
            {
                GardenManager.m_instance = new GardenManager;
            }
            return GardenManager.m_instance;
        }//end

    }



