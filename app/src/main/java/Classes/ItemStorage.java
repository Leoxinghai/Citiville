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

import Classes.effects.*;
import Classes.gates.*;
import Classes.util.*;
import Display.*;
import GameMode.*;
import Modules.crew.*;
import Modules.storage.*;
import Modules.storage.ui.*;


    public class ItemStorage extends Decoration
    {
        private IGate m_gate ;
        private GateFactory m_gateFactory ;
        private GameMode m_overrideGameMode ;

        public  ItemStorage (String param1)
        {
            super(param1);
            this.m_gateFactory = new GateFactory();
            this.m_gateFactory.register(GateType.CREW, CrewGate);
            this.m_overrideGameMode = null;
            return;
        }//end

         public void  loadObject (Object param1 )
        {
            super.loadObject(param1);
            this.m_gate = this.m_gateFactory.loadGateFromObject(param1, getItem(), this, null);
            return;
        }//end

         public void  onBuildingConstructionCompleted_PreServerUpdate ()
        {
            super.onBuildingConstructionCompleted_PreServerUpdate();
            _loc_1 = this.initStorageUnitFromItem();
            this.m_gate = this.m_gateFactory.loadGateFromXML(getItem(), this, "storage", null);
            return;
        }//end

         public void  onAddCrewMember ()
        {
            _loc_1 = Global.player.storageComponent.getStorageUnit(this.getStorageType(),this.getStorageKey());
            _loc_1.capacity = this.getStorageCapacity();
            refreshArrow();
            return;
        }//end

         public String  getToolTipHeader ()
        {
            if (UI.isViewOpen(StorageView))
            {
                return "";
            }
            return super.getToolTipHeader();
        }//end

         public String  getToolTipAction ()
        {
            String _loc_1 =null ;
            if (UI.isViewOpen(StorageView))
            {
                return "";
            }
            if (!Global.isVisiting())
            {
                if (isUpgradePossible())
                {
                    _loc_1 = ZLoc.t("Dialogs", "UpgradeToolTip");
                }
                else
                {
                    _loc_1 = ZLoc.t("Items", "warehouse_defaultToolTip");
                }
            }
            return _loc_1;
        }//end

         public void  onPlayAction ()
        {
            super.onPlayAction();
            if (!Global.isVisiting())
            {
                if (isUpgradePossible())
                {
                    upgradeBuildingIfPossible();
                }
                else
                {
                    UI.displayItemStorageDialog(this);
                }
            }
            return;
        }//end

         protected void  updateArrow ()
        {
            if (isUpgradePossible())
            {
                this.createStagePickEffect();
            }
            return;
        }//end

         protected void  createStagePickEffect ()
        {
            EffectsUtil.createStagePickEffect(StagePickEffect.PICK_UPGRADE, this);
            return;
        }//end

         public void  sell ()
        {
            if (this.canFinishSell())
            {
                super.sell();
            }
            else
            {
                UI.displayMessage(ZLoc.t("Main", "CannotSellStorageItem", {item:getItemFriendlyName()}));
            }
            return;
        }//end

         public void  onSell (GameObject param1)
        {
            super.onSell(param1);
            Global.player.storageComponent.removeStorageUnit(this.getStorageType(), this.getStorageKey());
            return;
        }//end

        private boolean  canFinishSell ()
        {
            boolean _loc_1 =true ;
            _loc_2 = Global.player.storageComponent.getStorageUnit(this.getStorageType(),this.getStorageKey());
            if (_loc_2 && _loc_2.size)
            {
                _loc_1 = false;
            }
            return _loc_1;
        }//end

         public void  sendToInventory ()
        {
            super.sendToInventory();
            Global.player.storageComponent.removeStorageUnit(this.getStorageType(), this.getStorageKey());
            return;
        }//end

         public void  onUpgrade (Item param1 ,Item param2 ,boolean param3 =true )
        {
            Vector _loc_6.<ItemInstance >=null ;
            ItemInstance _loc_7 =null ;
            super.onUpgrade(param1, param2);
            _loc_4 = Global.player.storageComponent.getStorageUnit(StorageType.createEnum(param1.storageType),param1.storageKey);
            _loc_5 = Global.player.storageComponent.getStorageUnit(StorageType.createEnum(param2.storageType),param2.storageKey);
            if (Global.player.storageComponent.getStorageUnit(StorageType.createEnum(param2.storageType), param2.storageKey) == null)
            {
                _loc_5 = this.initStorageUnitFromItem(_loc_4.capacity);
            }
            if (_loc_4 != _loc_5)
            {
                _loc_6 = _loc_4.getAllItems();
                for(int i0 = 0; i0 < _loc_6.size(); i0++)
                {
                		_loc_7 = _loc_6.get(i0);

                    _loc_4.remove(_loc_7);
                    _loc_5.add(_loc_7);
                }
                Global.player.storageComponent.removeStorageUnit(StorageType.createEnum(param1.storageType), param1.storageKey);
            }
            this.m_gate = this.m_gateFactory.loadGateFromXML(getItem(), this, "storage", null);
            _loc_5.capacity = this.getStorageCapacity();
            return;
        }//end

        private int  getStorageCapacity ()
        {
            _loc_1 = this.getInitialCapacity();
            _loc_2 = Global.crews.getCrewById(getId());
            _loc_3 = _loc_2? (_loc_2.count) : (0);
            return _loc_1 + _loc_3 + Global.player.additionalWareHouseSlots;
        }//end

        public StorageType  getStorageType ()
        {
            return StorageType.createEnum(m_item.storageType);
        }//end

        public String  getStorageKey ()
        {
            return m_item.storageKey;
        }//end

        public int  getInitialCapacity ()
        {
            return m_item.storageInitCapacity;
        }//end

        public int  getMaxCapacity ()
        {
            return m_item.storageMaxCapacity + Global.player.additionalWareHouseSlots;
        }//end

        public IGate  getGate ()
        {
            return this.m_gate;
        }//end

        protected BaseStorageUnit  initStorageUnitFromItem (int param1 =0)
        {
            _loc_2 = Global.player.storageComponent.addStorageUnit(this.getStorageType(),this.getStorageKey());
            _loc_2.maxCapacity = this.getMaxCapacity();
            if (param1 > 0)
            {
                _loc_2.capacity = param1;
            }
            else
            {
                _loc_2.capacity = this.getStorageCapacity();
            }
            return _loc_2;
        }//end

    }



