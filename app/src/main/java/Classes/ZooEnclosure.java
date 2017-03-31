package Classes;

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
import Display.*;
import Display.MarketUI.*;
import Engine.Managers.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import Modules.zoo.*;
import Modules.zoo.events.*;
import Modules.zoo.transactions.*;
import com.adobe.crypto.*;
import com.adobe.utils.*;
//import flash.utils.*;

    public class ZooEnclosure extends Municipal implements IMechanicPrePlayAction
    {
        private  String ZOO ="zooEnclosure";
        private  int FEED_UPDATE_INTERVAL =300;
        private double m_zooFeedUpdateTime =0;

        public  ZooEnclosure (String param1 )
        {
            super(param1);
            m_objectType = WorldObjectTypes.ZOO_ENCLOSURE;
            m_typeName = this.ZOO;
            return;
        }//end

         public void  onBuildingConstructionCompleted_PreServerUpdate ()
        {
            this.addRandomAnimal(ZooManager.COMMON);
            super.onBuildingConstructionCompleted_PreServerUpdate();
            return;
        }//end

        public void  onPrePlayTrack ()
        {
            StatsManager.sample(100, "Game_action", "Zoo", "click_on_zoo_not_havest", this.getItemName());
            return;
        }//end

        public int  getAnimals ()
        {
            _loc_1 = (IStorageMechanic)MechanicManager.getInstance().getMechanicInstance(this,ZooManager.MECHANIC_STORAGE,MechanicManager.ALL)
            _loc_2 = (ISlotMechanic)MechanicManager.getInstance().getMechanicInstance(this,ZooManager.MECHANIC_SLOTS,MechanicManager.ALL)
            if (_loc_1 && _loc_2)
            {
                return _loc_1.getTotalCount() + _loc_2.numFilledSlots;
            }
            return 0;
        }//end

        public int  getNumSpecificAnimal (String param1 )
        {
            _loc_2 = (IStorageMechanic)MechanicManager.getInstance().getMechanicInstance(this,ZooManager.MECHANIC_STORAGE,MechanicManager.ALL)
            _loc_3 = (ISlotMechanic)MechanicManager.getInstance().getMechanicInstance(this,ZooManager.MECHANIC_SLOTS,MechanicManager.ALL)
            return _loc_2.getCount(param1) + _loc_3.getNumItems(param1);
        }//end

        public void  addRandomAnimal (String param1 )
        {
            _loc_2 = (IStorageMechanic)MechanicManager.getInstance().getMechanicInstance(this,ZooManager.MECHANIC_STORAGE,MechanicManager.ALL)
            _loc_3 =Global.zooManager.getAllAnimalsByRarity(m_item.name );
            _loc_4 = _loc_3.get(param1);
            _loc_5 =Global.player.uid +"::"+getItemName ();
            _loc_6 = MD5"0x"+.hash(_loc_5).substring(0,8);
            _loc_7 = double(_loc_6);
            _loc_8 = double(_loc_6)% _loc_4.length;
            _loc_9 = _loc_4.get(_loc_8);
            if (_loc_2)
            {
                _loc_2.sendTransactions = false;
                _loc_2.add(_loc_9, 1);
            }
            return;
        }//end

        public boolean  hasAllAnimals ()
        {
            Array _loc_1 =null ;
            Item _loc_5 =null ;
            Dictionary _loc_2 =new Dictionary ();
            _loc_3 = (IStorageMechanic)MechanicManager.getInstance().getMechanicInstance(this,ZooManager.MECHANIC_STORAGE,MechanicManager.ALL)
            _loc_4 = (ISlotMechanic)MechanicManager.getInstance().getMechanicInstance(this,ZooManager.MECHANIC_SLOTS,MechanicManager.ALL)
            if (_loc_3 && _loc_4)
            {
                _loc_1 = Global.gameSettings().getItemsByKeywords(_loc_3.restrictedKeywords);
                for(int i0 = 0; i0 < _loc_1.size(); i0++)
                {
                	_loc_5 = _loc_1.get(i0);

                    if (_loc_3.getCount(_loc_5.name) > 0 && _loc_2.get(_loc_5.name) == null)
                    {
                        _loc_2.put(_loc_5.name,  true);
                    }
                    if (_loc_4.getNumItems(_loc_5.name) > 0 && _loc_2.get(_loc_5.name) == null)
                    {
                        _loc_2.put(_loc_5.name,  true);
                    }
                }
            }
            return DictionaryUtil.getKeys(_loc_2).length >= _loc_1.length;
        }//end

        public int  getPayout ()
        {
            Item _loc_3 =null ;
            _loc_1 =Global.zooManager.getAllAnimals(this.getItemName ());
            int _loc_2 =0;
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            	_loc_3 = _loc_1.get(i0);

                _loc_2 = _loc_2 + _loc_3.inventoryLimit;
            }
            return this.getAnimals() * 100 / _loc_2;
        }//end

        public boolean  unlockNextEnclosure (String param1 ="readyToUnlock")
        {
            boolean _loc_2 =false ;
            boolean _loc_3 =false ;
            switch(param1)
            {
                case "readyToUnlock":
                {
                    _loc_3 = this.readyToUnlock();
                    break;
                }
                case "readyToUnlockMinusAnimals":
                {
                    _loc_3 = this.readyToUnlockMinusAnimals();
                    break;
                }
                default:
                {
                    break;
                }
            }
            if (_loc_3)
            {
                Global.player.setSeenSessionFlag(ZooManager.ZOO_UNLOCK + m_item.unlocksItem);
                Global.player.inventory.addSingletonItem(m_item.unlocksItem);
                GameTransactionManager.addTransaction(new TZooUnlock(m_item));
                Global.ui.dispatchEvent(new ZooDialogEvent(ZooDialogEvent.UNLOCK_ENCLOSURE, m_item.unlocksItem));
                _loc_2 = true;
            }
            return _loc_2;
        }//end

        public boolean  readyToUnlock ()
        {
            return m_item.unlocksItem && this.hasAllAnimals() && BuyLogic.isLocked(Global.gameSettings().getItemByName(m_item.unlocksItem));
        }//end

        public boolean  readyToUnlockMinusAnimals ()
        {
            return m_item.unlocksItem && BuyLogic.isLocked(Global.gameSettings().getItemByName(m_item.unlocksItem));
        }//end

         public void  onUpdate (double param1 )
        {
            super.onUpdate(param1);
            this.m_zooFeedUpdateTime = this.m_zooFeedUpdateTime + param1;
            if (this.m_zooFeedUpdateTime > this.FEED_UPDATE_INTERVAL)
            {
                this.m_zooFeedUpdateTime = 0;
                if (Global.world.viralMgr.canPost(getItem().feed))
                {
                    updateStagePickEffect();
                }
            }
            return;
        }//end

         public void  sell ()
        {
            String _loc_1 ="";
            if (this.getAnimals() > 0)
            {
                _loc_1 = ZLoc.t("Main", "SendToInventoryZooAnimalsWarning", {item:getItemFriendlyName(), coins:getSellPrice()});
                UI.displayMessage(_loc_1, GenericPopup.TYPE_YESNO, sellConfirmationHandler);
            }
            else
            {
                super.sell();
            }
            return;
        }//end

    }



