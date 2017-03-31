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
import Display.CollectionsUI.*;
import Events.*;
import Transactions.*;
//import flash.utils.*;

    public class Collection extends GameSettingsObject
    {
        private int openedChests =0;
        private int m_totalItemsInCollection =0;
        private int m_cachedWishlistableCount =-1;
        private static int s_totalChestsFound =0;
        private static Dictionary s_openedChestsForCollection ;
        private static Dictionary s_itemToCollectionMapping ;
        private static  String COMPLETED ="COMPLETED_THE_COLLECTION";

        public  Collection (XML param1 )
        {
            super(param1);
            return;
        }//end

         protected String  getFriendlyNameLocFile ()
        {
            return "Collections";
        }//end

        public XMLList  getRewardXml ()
        {
            XMLList oneTimeReward ;
            if (m_xml.hasOwnProperty("oneTimeRewards"))
            {
                int _loc_3 =0;
                _loc_4 = m_xml.oneTimeRewards.oneTimeReward;
                XMLList _loc_2 =new XMLList("");
                Object _loc_5;
                for(int i0 = 0; i0 < _loc_4.size(); i0++)
                {
                	_loc_5 = _loc_4.get(i0);


                    with (_loc_5)
                    {
                        if (@count == Global.player.numTradeIns(this.name))
                        {
                            _loc_2.put(_loc_3++,  _loc_5);
                        }
                    }
                }
                oneTimeReward = _loc_2;
                if (oneTimeReward.length() > 0)
                {
                    return oneTimeReward;
                }
            }
            return m_xml.tradeInReward;
        }//end

        public Array  rewardCommodities ()
        {
            XML _loc_3 =null ;
            Object _loc_4 =null ;
            _loc_1 = this.getRewardXml();
            Array _loc_2 =new Array ();
            if (((XMLList)_loc_1.goods).length() > 0)
            {
                _loc_3 = _loc_1.goods.get(0);
                _loc_4 = new Object();
                _loc_4.name = "goods";
                _loc_4.amount = parseInt(_loc_3.@amount);
                _loc_2.push(_loc_4);
            }
            return _loc_2;
        }//end

        public Array  rewardItemNames ()
        {
            XML _loc_3 =null ;
            _loc_1 = this.getRewardXml();
            Array _loc_2 =new Array ();
            for(int i0 = 0; i0 < _loc_1.item.size(); i0++)
            {
            	_loc_3 = _loc_1.item.get(i0);

                _loc_2.push(new String(_loc_3.@name));
            }
            for(int i0 = 0; i0 < _loc_1.rewardItem.size(); i0++)
            {
            	_loc_3 = _loc_1.rewardItem.get(i0);

                _loc_2.push(new String(_loc_3.@name));
            }
            return _loc_2;
        }//end

        public Array  rewardCollectableNames ()
        {
            XML _loc_3 =null ;
            _loc_1 = this.getRewardXml();
            Array _loc_2 =new Array ();
            for(int i0 = 0; i0 < _loc_1.collectable.size(); i0++)
            {
            	_loc_3 = _loc_1.collectable.get(i0);

                _loc_2.push(new String(_loc_3.@name));
            }
            return _loc_2;
        }//end

        public int  rewardXp ()
        {
            XML _loc_3 =null ;
            _loc_1 = this.getRewardXml();
            int _loc_2 =0;
            for(int i0 = 0; i0 < _loc_1.xp.size(); i0++)
            {
            	_loc_3 = _loc_1.xp.get(i0);

                _loc_2 = _loc_2 + parseInt(_loc_3.@amount);
            }
            return _loc_2;
        }//end

        public int  rewardCoins ()
        {
            XML _loc_3 =null ;
            _loc_1 = this.getRewardXml();
            int _loc_2 =0;
            for(int i0 = 0; i0 < _loc_1.coin.size(); i0++)
            {
            	_loc_3 = _loc_1.coin.get(i0);

                _loc_2 = _loc_2 + parseInt(_loc_3.@amount);
            }
            return _loc_2;
        }//end

        public int  rewardEnergy ()
        {
            XML _loc_3 =null ;
            _loc_1 = this.getRewardXml();
            int _loc_2 =0;
            for(int i0 = 0; i0 < _loc_1.energy.size(); i0++)
            {
            	_loc_3 = _loc_1.energy.get(i0);

                _loc_2 = _loc_2 + parseInt(_loc_3.@amount);
            }
            return _loc_2;
        }//end

        public Array  rewardUnlocks ()
        {
            XML _loc_3 =null ;
            _loc_1 = this.getRewardXml();
            Array _loc_2 =new Array ();
            for(int i0 = 0; i0 < _loc_1.itemUnlock.size(); i0++)
            {
            	_loc_3 = _loc_1.itemUnlock.get(i0);

                _loc_2.push(new String(_loc_3.@name));
            }
            return _loc_2;
        }//end

        public String  icon ()
        {
            return Global.getAssetURL(m_xml.icon.@url);
        }//end

        public int  getTotalCollectablesCount ()
        {
            _loc_1 = m_xml.collectables.children();
            return _loc_1.length();
        }//end

        public boolean  hasPlayerCompleted ()
        {
            return Global.player.hasCompletedCollection(name);
        }//end

        public int  getCurrentUniqueCollectablesCount ()
        {
            XML _loc_3 =null ;
            int _loc_1 =0;
            if (m_xml == null)
            {
                return _loc_1;
            }
            _loc_2 = m_xml.collectables.children();
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            	_loc_3 = _loc_2.get(i0);

                if (Global.player.getNumCollectablesOwned(_loc_3.@name) > 0)
                {
                    _loc_1++;
                }
            }
            return _loc_1;
        }//end

        public int  getUniqueCollectablesCountForNextTradeIn ()
        {
            XML _loc_4 =null ;
            boolean _loc_5 =false ;
            int _loc_6 =0;
            int _loc_1 =0;
            Array _loc_2 =new Array ();
            if (m_xml == null)
            {
                return _loc_1;
            }
            _loc_3 = m_xml.collectables.children();
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            	_loc_4 = _loc_3.get(i0);

                _loc_2.push(Global.player.getNumCollectablesOwned(_loc_4.@name));
            }
            _loc_5 = false;
            while (!_loc_5)
            {

                _loc_6 = 0;
                while (_loc_6 < _loc_2.length())
                {

                    if (_loc_2.get(_loc_6) != 0)
                    {
                        _loc_7 = _loc_2;
                        _loc_8 = _loc_6;
                        _loc_9 = _loc_2.get(_loc_6)-1;
                        _loc_7.put(_loc_8,  _loc_9);
                    }
                    else
                    {
                        _loc_5 = true;
                        _loc_1++;
                    }
                    _loc_6++;
                }
            }
            return this.getTotalCollectablesCount() - _loc_1;
        }//end

        public XMLList  getCollectablesXMLList ()
        {
            _loc_1 = m_xml.collectables.children();
            return _loc_1;
        }//end

        public Array  getCollectableNames ()
        {
            XML _loc_3 =null ;
            Array _loc_1 =[] ;
            _loc_2 = m_xml.collectables.children();
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            	_loc_3 = _loc_2.get(i0);

                _loc_1.push(String(_loc_3.@name));
            }
            return _loc_1;
        }//end

        public XML  getCollectableXML (String param1 )
        {
            XML _loc_3 =null ;
            _loc_2 = m_xml.collectables.children();
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            	_loc_3 = _loc_2.get(i0);

                if (_loc_3.@name == param1)
                {
                    return _loc_3;
                }
            }
            return null;
        }//end

        public boolean  isReadyToTradeIn ()
        {
            return this.getTotalCollectablesCount() == this.getCurrentUniqueCollectablesCount();
        }//end

        public boolean  tradeIn ()
        {
            XML _loc_2 =null ;
            if (this.getTotalCollectablesCount() != this.getCurrentUniqueCollectablesCount())
            {
                return false;
            }
            _loc_1 = m_xml.collectables.children();
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            	_loc_2 = _loc_1.get(i0);

                Global.player.removeCollectable(_loc_2.@name);
            }
            Global.player.completeCollection(this.name);
            this.grantReward();
            if (this.shouldTrackTradeIns())
            {
                Global.player.trackTradeIns(this.name);
            }
            GameTransactionManager.addTransaction(new TCollectionTradeIn(this.name), true);
            _loc_3 = this.isReadyToTradeIn();
            Collection.setCompletedEarlier(this.name, _loc_3);
            return true;
        }//end

        protected boolean  shouldTrackTradeIns ()
        {
            return m_xml.hasOwnProperty("oneTimeRewards");
        }//end

        public boolean  completedCollection (String param1 )
        {
            XML collectable ;
            int numOwned ;
            nameOfAddedItem = param1;
            collectables = m_xml.collectables.children();
            int _loc_4 =0;
            _loc_5 = collectables;
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            	_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (@name == nameOfAddedItem)
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            if (_loc_3.length() != 1)
            {
                return false;
            }
            countOfAddedItem = Global.player.getNumCollectablesOwned(nameOfAddedItem);
            if (countOfAddedItem == 0)
            {
                return false;
            }


            for(int i0 = 0; i0 < collectables.size(); i0++)
            {
            	collectable = collectables.get(i0);


                this.m_totalItemsInCollection++;
                numOwned = Global.player.getNumCollectablesOwned(collectable.@name);
                if (numOwned == 0 || numOwned < countOfAddedItem)
                {
                    return false;
                }
            }
            return true;
        }//end

        public void  incrementOpenedChestCounter (String param1 )
        {
            Collection.s_openedChestsForCollection.get(name).get(param1) = -1;
            (Collection.s_totalChestsFound + 1);
            return;
        }//end

        public boolean  completedCollectionAndOpenedAllChests (String param1 )
        {
            Object _loc_3 =null ;
            if (!this.completedCollection(param1))
            {
                return false;
            }
            int _loc_2 =0;
            for(int i0 = 0; i0 < Collection.s_openedChestsForCollection.get(name).size(); i0++)
            {
            	_loc_3 = Collection.s_openedChestsForCollection.get(name).get(i0);

                _loc_2 = _loc_2 + 1;
            }
            if (_loc_2 == this.m_totalItemsInCollection)
            {
                return true;
            }
            return false;
        }//end

        public int  getNumWishlistableItems (boolean param1 =false )
        {
            String _loc_3 =null ;
            Item _loc_4 =null ;
            if (param1 && this.m_cachedWishlistableCount >= 0)
            {
                return this.m_cachedWishlistableCount;
            }
            int _loc_2 =0;
            for(int i0 = 0; i0 < this.getCollectableNames().size(); i0++)
            {
            		_loc_3 = this.getCollectableNames().get(i0);

                _loc_4 = Global.gameSettings().getItemByCode(_loc_3);
                if (!(Global.player.isItemOnWishlist(_loc_4.name) || Global.player.getNumCollectablesOwned(_loc_3) > 0))
                {
                    _loc_2++;
                }
            }
            this.m_cachedWishlistableCount = _loc_2;
            return _loc_2;
        }//end

        private void  grantReward ()
        {
            String _loc_5 =null ;
            Object _loc_6 =null ;
            String _loc_7 =null ;
            String _loc_8 =null ;
            Global.player.xp = Global.player.xp + this.rewardXp;
            Global.player.gold = Global.player.gold + this.rewardCoins;
            Global.player.updateEnergy(this.rewardEnergy, new Array("energy", "earnings", "rewards", ""));
            _loc_1 = this.rewardCommodities;
            if (_loc_1 && _loc_1.length > 0)
            {
                for(int i0 = 0; i0 < _loc_1.size(); i0++)
                {
                	_loc_6 = _loc_1.get(i0);

                    Global.player.commodities.add(_loc_6.name, _loc_6.amount);
                }
                Global.world.dispatchEvent(new FarmGameWorldEvent(FarmGameWorldEvent.USER_CHANGED));
            }
            _loc_2 = this.rewardItemNames;
            if (_loc_2 && _loc_2.length > 0)
            {
                for(int i0 = 0; i0 < _loc_2.size(); i0++)
                {
                	_loc_7 = _loc_2.get(i0);

                    Global.player.inventory.addItems(_loc_7, 1);
                }
                Global.world.dispatchEvent(new FarmGameWorldEvent(FarmGameWorldEvent.USER_CHANGED));
            }
            _loc_3 = this.rewardCollectableNames;
            if (_loc_3 && _loc_3.length > 0)
            {
                for(int i0 = 0; i0 < _loc_3.size(); i0++)
                {
                	_loc_8 = _loc_3.get(i0);

                    Global.player.addCollectable(_loc_8);
                }
                Global.world.dispatchEvent(new FarmGameWorldEvent(FarmGameWorldEvent.USER_CHANGED));
            }
            _loc_4 = this.rewardUnlocks;
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            	_loc_5 = _loc_4.get(i0);

                Global.player.setSeenFlag(_loc_5);
                Global.player.setSeenSessionFlag(_loc_5);
            }
            return;
        }//end

        public String  getRarity ()
        {
            String _loc_1 =this.xml.@rarity;
            String _loc_2 =null ;


            switch(_loc_1)
            {
                case "common":
                {
		    _loc_2 = ZLoc.t("Dialogs", "CommonFriendly");
		    break;
		}
                case "uncommon":
                {
	            _loc_2 = ZLoc.t("Dialogs", "UncommonFriendly");
                    break;
                }
                case "rare":
                {
	            _loc_2 = ZLoc.t("Dialogs", "RareFriendly");
                    break;
                }
                case "ultrarare":
                {
	            _loc_2 = ZLoc.t("Dialogs", "UltraRareFriendly");
                    break;
                }
                default:
                {
                    _loc_2 = "No rarity";
                    break;
                }
            }
            if(_loc_1.length > 0 ) return _loc_1;

            return null;
        }//end

        public static void  collectInfo (XML param1 )
        {
            int _loc_3 =0;
            XMLList _loc_4 =null ;
            XML _loc_5 =null ;
            Collection _loc_6 =null ;
            boolean _loc_7 =false ;
            String _loc_8 =null ;
            int _loc_9 =0;
            if (Collection.s_openedChestsForCollection == null)
            {
                Collection.s_openedChestsForCollection = new Dictionary();
                Collection.s_itemToCollectionMapping = new Dictionary();
            }
            _loc_2 = param1.@name;
            if (Collection.s_openedChestsForCollection.get(_loc_2) == null)
            {
                Collection.s_openedChestsForCollection.put(_loc_2,  new Dictionary());
                _loc_3 = 0;
                _loc_4 = param1.collectables.children();
                for(int i0 = 0; i0 < _loc_4.size(); i0++)
                {
                	_loc_5 = _loc_4.get(i0);

                    _loc_8 = String(_loc_5.@name);
                    Collection.s_itemToCollectionMapping.put(_loc_8,  _loc_2);
                    _loc_9 = Global.player.getNumCollectablesOwned(_loc_8);
                    if (_loc_9 > 0)
                    {
                        Collection.s_openedChestsForCollection.get(_loc_2).put(_loc_8,  _loc_9);
                        _loc_3 = _loc_3 + 1;
                        (Collection.s_totalChestsFound + 1);
                    }
                }
                _loc_6 = new Collection(param1);
                _loc_7 = _loc_6.isReadyToTradeIn();
                Collection.s_openedChestsForCollection.get(_loc_2).put(COMPLETED,  _loc_7);
            }
            return;
        }//end

        public static boolean  completedEarlier (String param1 )
        {
            if (s_openedChestsForCollection == null || s_openedChestsForCollection.get(param1) == null)
            {
                return false;
            }
            return s_openedChestsForCollection.get(param1).get(COMPLETED);
        }//end

        public static void  setCompletedEarlier (String param1 ,boolean param2 )
        {
            if (s_openedChestsForCollection == null || s_openedChestsForCollection.get(param1) == null)
            {
                return;
            }
            s_openedChestsForCollection.get(param1).put(COMPLETED,  param2);
            return;
        }//end

        public static int  totalChestsFound ()
        {
            return s_totalChestsFound;
        }//end

        public static String  getCollectionNameFromItemName (String param1 )
        {
            if (s_itemToCollectionMapping == null || s_itemToCollectionMapping.get(param1) == null)
            {
                Global.gameSettings().getCollectionByCollectableName(param1);
            }
            _loc_2 =s_itemToCollectionMapping.get(param1);
            return _loc_2;
        }//end

        public static int  getFoundItemsByCollectionName (String param1 )
        {
            String _loc_3 =null ;
            if (param1 == null)
            {
                return 0;
            }
            int _loc_2 =0;
            for(int i0 = 0; i0 < s_openedChestsForCollection.get(param1).size(); i0++)
            {
            	_loc_3 = s_openedChestsForCollection.get(param1).get(i0);

                _loc_2 = _loc_2 + 1;
            }
            return _loc_2;
        }//end

        public static void  addCollectionToPlayer (String param1 ,boolean param2 =true )
        {
            _loc_3 = getCollectionNameFromItemName(param1);
            _loc_4 = Global.player.hasCompletedCollection(_loc_3);
            Global.player.addCollectable(param1);
            _loc_5 = Global.player.hasCompletedCollection(_loc_3);
            if (!_loc_4 && _loc_5 && param2)
            {
                Sounds.play("collection_completed");
            }
            if (Global.ui.collectionView != null)
            {
                ((CollectionScrollingList)Global.ui.collectionView.asset.shelf).invalidateData();
            }
            return;
        }//end

    }





