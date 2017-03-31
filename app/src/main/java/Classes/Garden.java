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
import Engine.*;
import Engine.Managers.*;
import Events.*;
import Mechanics.*;
import Mechanics.ClientDisplayMechanics.*;
import Mechanics.GameEventMechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import Modules.garden.*;
import Modules.garden.ui.*;
import Transactions.*;
//import flash.utils.*;

    public class Garden extends MechanicMapResource
    {
        protected BonusShockwave m_bonusShockwave =null ;
public static  String GARDEN ="garden";
public static  String SECURE_RAND_FEATURE_NAME ="gardens";
public static  String GARDEN_FTUE_SEEN_FLAG ="gardens_FTUE";
public static  String GARDEN_FTUE ="GardenIntroGuide";
        public static  String RARITY_COMMON ="common";
        public static  String RARITY_UNCOMMON ="uncommon";
        public static  String RARITY_RARE ="rare";
        public static  String RARITY_ANY ="any";

        public  Garden (String param1 )
        {
            super(param1);
            m_objectType = WorldObjectTypes.GARDEN;
            m_typeName = GARDEN;
            this.m_bonusShockwave = new BonusShockwave(this);
            return;
        }//end

        public String  getGardenName ()
        {
            return "rose garden";
        }//end

         public void  loadObject (Object param1 )
        {
            super.loadObject(param1);
            return;
        }//end

         public double  getAOEPercentModifier (ItemBonus param1 )
        {
            String _loc_6 =null ;
            Item _loc_7 =null ;
            int _loc_2 =0;
            int _loc_3 =0;
            _loc_4 = MechanicManager.getInstance ().getMechanicInstance(this ,"slots","all")as ISlotMechanic ;
            int _loc_5 =0;
            while (_loc_5 < _loc_4.numSlots)
            {

                _loc_6 = _loc_4.getSlot(_loc_5);
                if (_loc_6)
                {
                    _loc_7 = Global.gameSettings().getItemByName(_loc_6);
                    if (_loc_7.rarity == RARITY_UNCOMMON)
                    {
                        _loc_2 = _loc_2 + 1;
                    }
                    else if (_loc_7.rarity == RARITY_RARE)
                    {
                        _loc_3 = _loc_3 + 1;
                    }
                }
                _loc_5++;
            }
            return param1.dynamicModifierArray.get("base") + _loc_3 * param1.dynamicModifierArray.get("rare") + _loc_2 * param1.dynamicModifierArray.get("uncommon");
        }//end

         public double  getRadius (ItemBonus param1 )
        {
            _loc_2 = param1.radiusMin;
            _loc_3 = (TimerHarvestMechanic)MechanicManager.getInstance().getMechanicInstance(this,"harvestState",MechanicManager.PLAY)
            if (_loc_3)
            {
                if (this.openTS != -1)
                {
                    if (_loc_3.openTimeLeft > 0)
                    {
                        _loc_2 = param1.radiusMax;
                    }
                }
            }
            return _loc_2;
        }//end

        public Array  slots ()
        {
            MechanicConfigData _loc_3 =null ;
            int _loc_4 =0;
            int _loc_5 =0;
            int _loc_6 =0;
            _loc_1 =(Array) mechanicData.get("slots");
            if (!_loc_1)
            {
                _loc_1 = new Array();
                _loc_3 = getMechanicConfig("slots");
                if (_loc_3)
                {
                    _loc_4 = int(_loc_3.params.get("numSlots"));
                    _loc_5 = 0;
                    while (_loc_5 < _loc_4)
                    {

                        _loc_1.put(_loc_5,  null);
                        _loc_5++;
                    }
                }
            }
            _loc_2 = _loc_1.length ;
            if (_loc_1)
            {
                _loc_6 = 0;
                while (_loc_6 < _loc_2)
                {

                    if (_loc_1.get(_loc_6) == "" || !_loc_1.get(_loc_6))
                    {
                        _loc_1.put(_loc_6,  null);
                    }
                    _loc_6++;
                }
            }
            _loc_1 = _loc_1.concat();
            return _loc_1;
        }//end

        public void  slots (Object param1)
        {
            Array _loc_2 =null ;
            Array _loc_3 =null ;
            int _loc_4 =0;
            Array _loc_5 =null ;
            MechanicConfigData _loc_6 =null ;
            int _loc_7 =0;
            String _loc_8 =null ;
            int _loc_9 =0;
            Item _loc_10 =null ;
            String _loc_11 =null ;
            if (param1 && param1 instanceof Array)
            {
                _loc_2 =(Array) param1;
                _loc_3 =(Array) this.slots;
                _loc_4 = _loc_2.length;
                _loc_5 = new Array();
                if (_loc_4 == _loc_3.length())
                {
                    _loc_7 = 0;
                    while (_loc_7 < _loc_4)
                    {

                        if (_loc_2.get(_loc_7) != _loc_3.get(_loc_7))
                        {
                            _loc_5.push({slot:_loc_7, newValue:_loc_2.get(_loc_7), oldValue:_loc_3.get(_loc_7)});
                        }
                        _loc_7++;
                    }
                    if (_loc_5.length == 1)
                    {
                        _loc_8 = _loc_5.get(0).get("newValue");
                        _loc_9 = 0;
                        if (_loc_8 != null)
                        {
                            _loc_9 = -1;
                        }
                        else
                        {
                            _loc_8 = _loc_5.get(0).get("oldValue");
                            _loc_9 = 1;
                        }
                        _loc_10 = getItem();
                        _loc_11 = _loc_10.gardenType;
                        if (_loc_10 && GardenManager.instance.isFlower(_loc_8) && GardenManager.instance.canAddFlower(_loc_11, _loc_8, _loc_9))
                        {
                            GardenManager.instance.addFlower(_loc_11, _loc_8, _loc_9);
                            GardenManager.instance.refreshFeatureData();
                        }
                        else if (_loc_9 < 0)
                        {
                            ErrorManager.addError("Item " + _loc_8 + " instanceof not plantable in a graden");
                            return;
                        }
                    }
                    else if (_loc_5.length > 1)
                    {
                        ErrorManager.addError("Updating incorrect number of slots in garden: " + _loc_5.length());
                        return;
                    }
                }
                else
                {
                    ErrorManager.addError("Slots in garden didn\'t match count, new:" + _loc_4 + " old: " + _loc_3.length());
                    return;
                }
                mechanicData.put("slots", param1);
                _loc_6 = getMechanicConfig("slots");
                if (_loc_6)
                {
                    if (this.numEmptySlots == _loc_6.params.get("numSlots") && this.isGardenOpen())
                    {
                        setDataForMechanic("harvestState", null, MechanicManager.PLAY);
                    }
                    else if (this.numEmptySlots != _loc_6.params.get("numSlots") && !this.isGardenOpen())
                    {
                        setDataForMechanic("openTS", GlobalEngine.getTimer() / 1000, MechanicManager.PLAY);
                    }
                }
            }
            return;
        }//end

        public int  numEmptySlots ()
        {
            int _loc_3 =0;
            int _loc_1 =0;
            _loc_2 = (Array)getDataForMechanic("slots")
            if (_loc_2)
            {
                _loc_3 = 0;
                while (_loc_3 < _loc_2.length())
                {

                    if (_loc_2.get(_loc_3) == null)
                    {
                        _loc_1++;
                    }
                    _loc_3++;
                }
            }
            return _loc_1;
        }//end

        public boolean  isGardenOpen ()
        {
            _loc_1 = getDataForMechanic("harvestState");
            return _loc_1 != null && _loc_1 != MechanicManager.DATA_NOT_INITIALIZED && this.openTS != -1;
        }//end

        public Object storage ()
        {
            return GardenManager.instance.featureData;
        }//end

        public void  storage (Object param1)
        {
            GardenManager.instance.featureData = param1;
            return;
        }//end

         public String  getToolTipStatus ()
        {
            TimerHarvestMechanic _loc_2 =null ;
            String _loc_3 =null ;
            String _loc_4 =null ;
            String _loc_1 ="";
            if (!Global.isVisiting())
            {
                _loc_2 =(TimerHarvestMechanic) MechanicManager.getInstance().getMechanicInstance(this, "harvestState", MechanicManager.PLAY);
                if (_loc_2)
                {
                    if (this.openTS != -1)
                    {
                        if (_loc_2.openTimeLeft > 0)
                        {
                            _loc_3 = GameUtil.formatMinutesSeconds(_loc_2.openTimeLeft);
                            _loc_1 = ZLoc.t("Main", "Garden_status_waterIn", {time:_loc_3});
                        }
                        else
                        {
                            _loc_1 = ZLoc.t("Main", "Garden_status_needWater");
                        }
                    }
                    else
                    {
                        _loc_4 = ZLoc.t("Feeds", "garden_" + this.getItem().gardenType);
                        _loc_1 = ZLoc.t("Main", "Garden_status_plantFlowers", {garden:_loc_4});
                    }
                }
            }
            return _loc_1;
        }//end

         public String  getToolTipAction ()
        {
            TimerHarvestMechanic _loc_2 =null ;
            String _loc_1 ="";
            if (!Global.isVisiting())
            {
                _loc_2 =(TimerHarvestMechanic) MechanicManager.getInstance().getMechanicInstance(this, "harvestState", MechanicManager.PLAY);
                if (_loc_2)
                {
                    if (this.openTS != -1)
                    {
                        if (_loc_2.openTimeLeft <= 0)
                        {
                            _loc_1 = ZLoc.t("Main", "Garden_action_water");
                        }
                    }
                    else
                    {
                        _loc_1 = ZLoc.t("Main", "Garden_action_plant");
                    }
                }
            }
            return _loc_1;
        }//end

         protected int  getSellPrice ()
        {
            return m_item.baseCost * Global.gameSettings().getNumber("sellBackRatio");
        }//end

         public void  onBuildingConstructionCompleted_PreServerUpdate ()
        {
            super.onBuildingConstructionCompleted_PreServerUpdate();
            Global.itemCountManager.addItem(this.getItemName(), 1);
            this.grantFirstTimeFlower();
            GardenManager.instance.refreshFeatureData();
            this.runFTUE();
            UI.refreshCatalogItems(.get(getItemName()), true);
            return;
        }//end

         public void  onSell (GameObject param1)
        {
            super.onSell(param1);
            Global.itemCountManager.addItem(this.getItemName(), -1);
            GardenManager.instance.refreshFeatureData();
            UI.refreshCatalogItems(.get(getItemName()), true);
            return;
        }//end

         public void  rotate ()
        {
            super.rotate();
            dispatchEvent(new GenericObjectEvent(GenericObjectEvent.MECHANIC_DATA_CHANGED, "ILOVELAMP", true, MechanicManager.ALL));
            return;
        }//end

         public void  prepareForStorage (MapResource param1)
        {
            super.prepareForStorage(param1);
            return;
        }//end

        public void  purchaseMysteryCart (int param1 )
        {
            if (Global.player.cash > param1)
            {
                Global.player.cash = Global.player.cash - param1;
            }
            return;
        }//end

        public ItemImageInstance  getImageNameByState (Item param1 ,int param2 ,int param3 )
        {
            ItemImageInstance _loc_4 =null ;
            Object _loc_6 =null ;
            String _loc_7 =null ;
            String _loc_8 =null ;
            TimerHarvestMechanic _loc_9 =null ;
            _loc_5 = this.getItem();
            if (param1 && _loc_5)
            {
                _loc_4 = param1.getCachedImage(MultiAssetRenderer.DEFAULT_IMAGE_NAME, null, param3);
                _loc_6 = getDataForMechanic("harvestState");
                if (_loc_6)
                {
                    _loc_7 = "_" + _loc_5.sizeX + "x" + _loc_5.sizeY;
                    _loc_8 = "";
                    _loc_9 =(TimerHarvestMechanic) MechanicManager.getInstance().getMechanicInstance(this, "harvestState", MechanicManager.PLAY);
                    if (_loc_9)
                    {
                        if (_loc_9.isOpen())
                        {
                            _loc_8 = "full";
                        }
                        else
                        {
                            _loc_8 = "thin";
                        }
                        if (param1.hasCachedImageByName(_loc_8 + _loc_7))
                        {
                            _loc_4 = param1.getCachedImage(_loc_8 + _loc_7, null, param3);
                        }
                    }
                }
            }
            return _loc_4;
        }//end

        public void  purchaseRandomFlower (int param1 ,Function param2 )
        {
            MechanicMapResource self ;
            amount = param1;
            callback = param2;
            if (Global.player.cash >= amount)
            {
                Global.player.cash = Global.player.cash - amount;
                self;
                GameTransactionManager .addTransaction (new TPurchaseRandomGardenFlower (this ,void  (Object param1 )
            {
                if (param1 == null || param1.data == null)
                {
                    ErrorManager.addError("Invalid result returned from random garden flower transaction");
                    return;
                }
                Global.player.inventory.addItems(param1.data.loot, 1);
                _loc_2 = self.getDataForMechanic("giftSenders");
                if (!_loc_2)
                {
                    _loc_2 = new Dictionary();
                }
                _loc_2.put("-1", param1.data.loot);
                self.setDataForMechanic("giftSenders", _loc_2, "all");
                callback();
                MechanicManager.getInstance().handleAction(self, MechanicManager.PLAY);
                return;
            }//end
            ), true, true);
            }
            return;
        }//end

        protected void  grantFirstTimeFlower ()
        {
            String _loc_3 =null ;
            Item _loc_4 =null ;
            int _loc_5 =0;
            Array _loc_6 =null ;
            int _loc_7 =0;
            String _loc_8 =null ;
            int _loc_9 =0;
            String _loc_10 =null ;
            _loc_1 = getItem();
            int _loc_2 =0;
            if (_loc_1 && _loc_1.gardenType)
            {
                for (_loc_3 in Global.itemCountManager.featureData)
                {

                    if (_loc_3)
                    {
                        _loc_4 = Global.gameSettings().getItemByName(_loc_3);
                        if (_loc_4 && _loc_4.gardenType == _loc_1.gardenType && Global.itemCountManager.featureData.get(_loc_3) > 0)
                        {
                            _loc_2++;
                        }
                        if (_loc_2 > 1)
                        {
                            return;
                        }
                    }
                }
                if (_loc_2 == 1)
                {
                    _loc_5 = GardenManager.instance.getFlowerAmount(_loc_1.gardenType);
                    if (_loc_5 == 0)
                    {
                        _loc_6 = this.getFlowerConfigs(_loc_1.gardenType, RARITY_COMMON);
                        _loc_6.sort();
                        if (_loc_6.length > 0)
                        {
                            _loc_7 = _loc_6.length - 1;
                            _loc_8 = "first time flower on " + _loc_1.gardenType;
                            _loc_9 = SecureRand.randPerFeature(0, _loc_7, SECURE_RAND_FEATURE_NAME, _loc_8);
                            _loc_10 = _loc_6.get(_loc_9);
                            if (GardenManager.instance.canAddFlower(_loc_1.gardenType, _loc_10, 1))
                            {
                                GardenManager.instance.addFlower(_loc_1.gardenType, _loc_10, 1);
                            }
                        }
                    }
                }
            }
            return;
        }//end

        protected void  runFTUE ()
        {
            if (!Global.player.getSeenFlag(GARDEN_FTUE_SEEN_FLAG))
            {
                Global.player.setSeenFlag(GARDEN_FTUE_SEEN_FLAG);
                Global.guide.notify(GARDEN_FTUE);
            }
            return;
        }//end

        public Array  getFlowerConfigs (String param1 ,String param2 ="any")
        {
            Array _loc_4 =null ;
            Item _loc_5 =null ;
            Array _loc_3 =new Array();
            if (param1 !=null)
            {
                _loc_4 = Global.gameSettings().getItemsByKeywords(.get("garden_flower"));
                for(int i0 =0; i0 < _loc4.size(); i0++ )
                {
					_loc_5 = _loc_4.get(i0);

                    if (_loc_5 && _loc_5.gardenType == param1 && (param2 == RARITY_ANY || _loc_5.rarity == param2))
                    {
                        _loc_3.push(_loc_5.name);
                    }
                }
            }
            return _loc_3;
        }//end

        public void  openTS (double param1 )
        {
            if (mechanicData.get("harvestState") == null)
            {
                mechanicData.put("harvestState",  new Object());
            }
            mechanicData.get("harvestState").put("openTS",  param1);
            return;
        }//end

        public double  openTS ()
        {
            if (mechanicData != null && mechanicData.hasOwnProperty("harvestState") && mechanicData.get("harvestState") != null && mechanicData.get("harvestState").hasOwnProperty("openTS"))
            {
                return mechanicData.get("harvestState").get("openTS");
            }
            return -1;
        }//end

        public double  endTS ()
        {
            if (this.openTS == -1)
            {
                return -1;
            }
            _loc_1 = MechanicManager.getInstance ().getMechanicInstance(this ,"harvestState",MechanicManager.PLAY )as TimerHarvestMechanic ;
            if (_loc_1)
            {
                return _loc_1.closeTS;
            }
            return -1;
        }//end

        public void  harvestState (Object param1)
        {
            ItemBonus _loc_4 =null ;
            mechanicData.put("harvestState",  param1);
            _loc_2 = getMechanicConfig("slots");
            _loc_3 = int(_loc_2.params.get("numSlots"));
            if (param1 == null && this.numEmptySlots < _loc_3)
            {
                displayStatus(ZLoc.t("Main", "Garden_water_bonus"), "", Constants.COLOR_HIGHLIGHT_BLUE);
                _loc_4 =(ItemBonus) getItem().bonuses.get(0);
                this.m_bonusShockwave.start(Constants.COLOR_HIGHLIGHT_BLUE, _loc_4.radiusMin, _loc_4.radiusMax);
            }
            return;
        }//end

        public Object harvestState ()
        {
            return mechanicData.get("harvestState");
        }//end

        public Object giftSenders ()
        {
            Object _loc_1 =new Object ();
            _loc_2 = this.getItem();
            if (_loc_2 && GardenManager.instance.featureData)
            {
                _loc_1 = GardenManager.instance.getGiftSenders(_loc_2.gardenType);
            }
            return _loc_1;
        }//end

        public void  giftSenders (Object param1)
        {
            _loc_2 = this.getItem();
            if (_loc_2 && GardenManager.instance.featureData)
            {
                GardenManager.instance.setGiftSenders(_loc_2.gardenType, param1);
            }
            return;
        }//end

        public double  getGardenCoinDrop ()
        {
            int _loc_1 =100;
            _loc_2 = this.getItem();
            _loc_3 = _loc_2&& _loc_2.cash != 0 ? (2) : (1);
            Array _loc_4 =.get(RARITY_UNCOMMON ,RARITY_RARE) ;
            Array _loc_5 =.get(1 ,1) ;
            if (_loc_2.sizeX == 2 && _loc_2.sizeY == 2)
            {
                _loc_5 = .get(150, 300);
            }
            else if (_loc_2.sizeX == 4 && _loc_2.sizeY == 4)
            {
                _loc_5 = .get(150, 400);
            }
            return this.getGardenPayout(_loc_4, _loc_5, _loc_1 * _loc_3);
        }//end

        private double  getGardenPayout (Array param1 ,Array param2 ,int param3 =0)
        {
            Array _loc_6 =null ;
            int _loc_7 =0;
            int _loc_8 =0;
            String _loc_9 =null ;
            Item _loc_10 =null ;
            int _loc_11 =0;
            double _loc_4 =0;
            _loc_5 = getItem();
            if (getItem())
            {
                _loc_4 = param3;
                _loc_6 = this.slots;
                _loc_7 = _loc_6.length;
                _loc_8 = 0;
                while (_loc_8 < _loc_7)
                {

                    _loc_9 = _loc_6.get(_loc_8);
                    if (_loc_9)
                    {
                        _loc_10 = Global.gameSettings().getItemByName(_loc_9);
                        _loc_11 = -1;
                        if (_loc_10)
                        {
                            if (_loc_10.rarity)
                            {
                                _loc_11 = param1.indexOf(_loc_10.rarity);
                            }
                        }
                        if (_loc_11 >= 0)
                        {
                            _loc_4 = _loc_4 + param2.get(_loc_11);
                        }
                    }
                    _loc_8++;
                }
            }
            return Number(_loc_4);
        }//end

         public void  sell ()
        {
            _loc_1 = ZLoc.t("Main","SellGardenWarning",{itemgetItemFriendlyName(),coins.getSellPrice(),garden_type(),reduce_cap.gardenCapacityBonus});
            UI.displayMessage(_loc_1, GenericPopup.TYPE_YESNO, this.sellConfirmationHandler);
            return;
        }//end

         protected void  sellConfirmationHandler (GenericPopupEvent event )
        {
            ISlotMechanic _loc_2 =null ;
            int _loc_3 =0;
            if (event.button == GenericPopup.YES)
            {
                _loc_2 =(ISlotMechanic) MechanicManager.getInstance().getMechanicInstance(this, "slots", "all");
                if (_loc_2)
                {
                    _loc_3 = 0;
                    while (_loc_3 < _loc_2.numSlots)
                    {

                        _loc_2.emptySlot(_loc_3);
                        _loc_3++;
                    }
                }
            }
            super.sellConfirmationHandler(event);
            return;
        }//end

    }



