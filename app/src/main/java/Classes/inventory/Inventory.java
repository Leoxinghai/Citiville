package Classes.inventory;

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
import com.adobe.utils.*;
//import flash.utils.*;

    public class Inventory
    {
        private Dictionary m_items ;
        private int m_count =0;
        private boolean m_checkConsume =false ;

        public  Inventory (Player param1 ,Object param2 )
        {
            String _loc_3 =null ;
            int _loc_4 =0;
            this.m_items = new Dictionary();
            if (param2 == null || param2.count == null || param2.items == null)
            {
                return;
            }
            this.m_count = 0;
            for(int i0 = 0; i0 < param2.items.size(); i0++)
            {
            		_loc_3 = param2.items.get(i0);

                _loc_4 = int(param2.items.get(_loc_3));
                this.addItems(_loc_3, _loc_4, true, false);
            }
            return;
        }//end

        public int  totalCount ()
        {
            return this.m_count;
        }//end

        public void  resetConsumeFlag ()
        {
            this.m_checkConsume = false;
            return;
        }//end

        public boolean  consumeFlag ()
        {
            return this.m_checkConsume;
        }//end

        public int  capacity ()
        {
            return Global.gameSettings().getInt("maxInventoryCapacity", 0);
        }//end

        public int  spareCapacity ()
        {
            return this.capacity - this.totalCount;
        }//end

        public int  getItemCountByName (String param1 )
        {
            _loc_2 = Global.gameSettings().getItemByName(param1);
            return _loc_2 != null ? (this.getItemCount(_loc_2)) : (0);
        }//end

        public int  getItemCount (Item param1 )
        {
            if (this.m_items.get(param1) != null)
            {
                return int(this.m_items.get(param1));
            }
            return 0;
        }//end

        public Array  getItems ()
        {
            return DictionaryUtil.getKeys(this.m_items);
        }//end

        public Dictionary  getItemCountDictionaryByType (String param1 )
        {
            Item _loc_4 =null ;
            Dictionary _loc_2 =new Dictionary ();
            _loc_3 = this.getItems();
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            		_loc_4 = _loc_3.get(i0);

                if (_loc_4.type != param1)
                {
                    continue;
                }
                _loc_2.put(_loc_4,  this.m_items.get(_loc_4));
            }
            return _loc_2;
        }//end

        public int  getItemCountTotalByType (String param1 )
        {
            String _loc_4 =null ;
            _loc_2 = this.getItemCountDictionaryByType(param1);
            int _loc_3 =0;
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_4 = _loc_2.get(i0);

                _loc_3 = _loc_3 + _loc_2.get(_loc_4);
            }
            return _loc_3;
        }//end

        public boolean  canAddItems (String param1 ,int param2 )
        {
            if (param2 <= 0 || this.spareCapacity < param2)
            {
                return false;
            }
            _loc_3 = Global.gameSettings().getItemByName(param1);
            if (_loc_3 == null)
            {
                return false;
            }
            if (!this.m_items.get(_loc_3))
            {
                this.m_items.put(_loc_3,  0);
            }
            return _loc_3.inventoryLimit == -1 || this.m_items.get(_loc_3) + param2 <= _loc_3.inventoryLimit;
        }//end

        public boolean  isItemCountBelowInventoryLimit (String param1 ,int param2 )
        {
            if (param2 <= 0 || this.spareCapacity < param2)
            {
                return false;
            }
            _loc_3 = Global.gameSettings().getItemByName(param1);
            if (_loc_3 == null)
            {
                return false;
            }
            if (this.m_items.get(_loc_3) == null)
            {
                this.m_items.put(_loc_3,  0);
            }
            if (_loc_3.inventoryLimit > 0)
            {
                if (this.m_items.get(_loc_3) + param2 > _loc_3.inventoryLimit)
                {
                    if (this.m_items.get(_loc_3) < _loc_3.inventoryLimit)
                    {
                        return true;
                    }
                    return false;
                }
            }
            return true;
        }//end

        public boolean  addItems (String param1 ,int param2 ,boolean param3 =false ,boolean param4 =true ,boolean param5 =false )
        {
            String _loc_8 =null ;
            if (param2 <= 0)
            {
                return false;
            }
            _loc_6 = Global.gameSettings().getItemByName(param1);
            if (Global.gameSettings().getItemByName(param1) == null)
            {
                return false;
            }
            _loc_7 = InventoryCheckManager.getInstance();
            if (InventoryCheckManager.getInstance().hasInventoryChecks(_loc_6) && param5 == false)
            {
                _loc_8 = this.handleInventoryChecks("add", _loc_6, param2);
                if (_loc_8 == InventoryCheckManager.RESULT_SUCCESS_HALT)
                {
                    return true;
                }
                if (_loc_8 == InventoryCheckManager.RESULT_FAIL_HALT)
                {
                    return false;
                }
            }
            if (this.spareCapacity < param2 && !param3)
            {
                return false;
            }
            if (this.m_items.get(_loc_6) == null)
            {
                this.m_items.put(_loc_6,  0);
            }
            this.m_checkConsume = true;
            if (_loc_6.inventoryLimit > 0 && !param3)
            {
                if (this.m_items.get(_loc_6) + param2 > _loc_6.inventoryLimit)
                {
                    if (this.m_items.get(_loc_6) < _loc_6.inventoryLimit)
                    {
                        this.m_count = this.m_count + (_loc_6.inventoryLimit - this.m_items.get(_loc_6));
                        this.m_items.put(_loc_6,  _loc_6.inventoryLimit);
                        return true;
                    }
                    if (param4)
                    {
                        UI.displayInventoryMaxExceededDialog();
                        return false;
                    }
                }
            }
            this.m_items.put(_loc_6,  this.m_items.get(_loc_6) + param2);
            this.m_count = this.m_count + param2;
            if (Global.ui.inventoryView != null)
            {
                UI.deinitializeInventory();
            }
            return true;
        }//end

        public boolean  addSingletonItem (String param1 ,boolean param2 =false )
        {
            boolean _loc_3 =false ;
            if (this.verifySingletonItem(param1))
            {
                this.m_checkConsume = true;
                _loc_3 = this.addItems(param1, 1, param2);
            }
            return _loc_3;
        }//end

        public boolean  verifySingletonItem (String param1 )
        {
            Array _loc_5 =null ;
            ConstructionSite _loc_6 =null ;
            Array _loc_7 =null ;
            ISlottedContainer _loc_8 =null ;
            MapResource _loc_9 =null ;
            boolean _loc_2 =false ;
            _loc_3 = Global.gameSettings().getItemNamesInUpgradeHeirarchy(param1);
            String _loc_4 ="";
            if (!_loc_2)
            {
                for(int i0 = 0; i0 < _loc_3.size(); i0++)
                {
                		_loc_4 = _loc_3.get(i0);

                    _loc_2 = this.getItemCountByName(_loc_4) > 0;
                    if (_loc_2)
                    {
                        break;
                    }
                }
            }
            if (!_loc_2)
            {
                for(int i0 = 0; i0 < _loc_3.size(); i0++)
                {
                		_loc_4 = _loc_3.get(i0);

                    _loc_2 = Global.player.storageComponent.getItemsByName(_loc_4).length > 0;
                    if (_loc_2)
                    {
                        break;
                    }
                }
            }
            if (!_loc_2)
            {
                _loc_2 = Global.world.getObjectsByNames(_loc_3).length > 0;
            }
            if (!_loc_2)
            {
                _loc_5 = Global.world.getObjectsByClass(ConstructionSite);
                for(int i0 = 0; i0 < _loc_5.size(); i0++)
                {
                		_loc_6 = _loc_5.get(i0);

                    for(int i0 = 0; i0 < _loc_3.size(); i0++)
                    {
                    		_loc_4 = _loc_3.get(i0);

                        _loc_2 = _loc_6.targetName == _loc_4;
                        if (_loc_2)
                        {
                            break;
                        }
                    }
                }
            }
            if (!_loc_2)
            {
                _loc_7 = Global.world.getObjectsByClass(ISlottedContainer);
                for(int i0 = 0; i0 < _loc_7.size(); i0++)
                {
                		_loc_8 = _loc_7.get(i0);

                    for(int i0 = 0; i0 < _loc_8.slots.size(); i0++)
                    {
                    		_loc_9 = _loc_8.slots.get(i0);

                        if (_loc_9)
                        {
                            for(int i0 = 0; i0 < _loc_3.size(); i0++)
                            {
                            		_loc_4 = _loc_3.get(i0);

                                _loc_2 = _loc_9.getItem().name == _loc_4;
                                if (_loc_2)
                                {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            return !_loc_2;
        }//end

        public boolean  verifySingletonItemIgnoreInheritance (String param1 )
        {
            Array _loc_3 =null ;
            ConstructionSite _loc_4 =null ;
            Array _loc_5 =null ;
            ISlottedContainer _loc_6 =null ;
            MapResource _loc_7 =null ;
            boolean _loc_2 =false ;
            if (!_loc_2)
            {
                _loc_2 = this.getItemCountByName(param1) > 0;
            }
            if (!_loc_2)
            {
                _loc_2 = Global.player.storageComponent.getItemsByName(param1).length > 0;
            }
            if (!_loc_2)
            {
                _loc_2 = Global.world.getObjectsByNames(.get(param1)).length > 0;
            }
            if (!_loc_2)
            {
                _loc_3 = Global.world.getObjectsByClass(ConstructionSite);
                for(int i0 = 0; i0 < _loc_3.size(); i0++)
                {
                		_loc_4 = _loc_3.get(i0);

                    _loc_2 = _loc_4.targetName == param1;
                    if (_loc_2)
                    {
                        break;
                    }
                }
            }
            if (!_loc_2)
            {
                _loc_5 = Global.world.getObjectsByClass(ISlottedContainer);
                for(int i0 = 0; i0 < _loc_5.size(); i0++)
                {
                		_loc_6 = _loc_5.get(i0);

                    for(int i0 = 0; i0 < _loc_6.slots.size(); i0++)
                    {
                    		_loc_7 = _loc_6.slots.get(i0);

                        if (_loc_7)
                        {
                            _loc_2 = _loc_7.getItem().name == param1;
                            if (_loc_2)
                            {
                                break;
                            }
                        }
                    }
                }
            }
            return !_loc_2;
        }//end

        public boolean  removeItems (String param1 ,int param2 ,boolean param3 =true )
        {
            String _loc_6 =null ;
            if (param2 <= 0)
            {
                return false;
            }
            _loc_4 = Global.gameSettings().getItemByName(param1);
            if (Global.gameSettings().getItemByName(param1) == null)
            {
                return false;
            }
            _loc_5 = InventoryCheckManager.getInstance();
            if (InventoryCheckManager.getInstance().hasInventoryChecks(_loc_4))
            {
                _loc_6 = this.handleInventoryChecks("remove", _loc_4, param2);
                if (_loc_6 == InventoryCheckManager.RESULT_SUCCESS_HALT)
                {
                    return true;
                }
                if (_loc_6 == InventoryCheckManager.RESULT_FAIL_HALT)
                {
                    return false;
                }
            }
            if (param2 > this.getItemCountByName(param1))
            {
                return false;
            }
            this.m_count = this.m_count - param2;
            this.m_items.put(_loc_4,  this.m_items.get(_loc_4) - param2);
            if (this.m_items.get(_loc_4) == 0)
            {
                delete this.m_items.get(_loc_4);
            }
            if (Global.ui.inventoryView != null && param3)
            {
                UI.refreshInventory();
            }
            return true;
        }//end

        private String  handleInventoryChecks (String param1 ,Item param2 ,int param3 )
        {
            String _loc_5 =null ;
            InventoryCheckManager _loc_6 =null ;
            Dictionary _loc_7 =null ;
            String _loc_8 =null ;
            _loc_4 = param2.inventoryChecksConfig;
            if (param2.inventoryChecksConfig)
            {
                _loc_5 = _loc_4.getCallback(param1);
                _loc_6 = InventoryCheckManager.getInstance();
                if (_loc_6.hasOwnProperty(_loc_5))
                {
                    _loc_7 = _loc_4.getParams(param1);
                    _loc_9 = _loc_6;
                    _loc_8 = _loc_9.get(_loc_5)(param2, param3, _loc_7);
                    return _loc_8;
                }
            }
            return InventoryCheckManager.RESULT_PROCEED;
        }//end

    }



