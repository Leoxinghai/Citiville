package Display.MarketUI;

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
import Classes.bonus.*;
import Classes.doobers.*;
import Modules.goals.*;
import Modules.goals.mastery.*;

    public class ItemSorting
    {
        private static  int PRICE_CATEGORY_COINS =0;
        private static  int PRICE_CATEGORY_CASH =1;
        private static  int PRICE_CATEGORY_GOODS =2;
        private static  int PRICE_CATEGORY_FBC =3;

        public  ItemSorting ()
        {
            return;
        }//end

        private static int  getItemPriceCategory (Item param1 )
        {
            if (param1.goods > 0)
            {
                return PRICE_CATEGORY_GOODS;
            }
            if (param1.cash > 0)
            {
                return PRICE_CATEGORY_CASH;
            }
            if (param1.fbc > 0)
            {
                return PRICE_CATEGORY_FBC;
            }
            return PRICE_CATEGORY_COINS;
        }//end

        private static int  getMinBusinessCoinPayout (Item param1 )
        {
            return Math.ceil(param1.getDooberMinimums(Doober.DOOBER_COIN) * param1.commodityReq);
        }//end

        private static int  getMinAttractionCoinPayout (Item param1 )
        {
            if (param1.harvestBonuses == null || param1.harvestBonuses.length == 0)
            {
                return 0;
            }
            _loc_2 =(CustomerMaintenanceBonus) param1.harvestBonuses.get(0);
            if (_loc_2 == null)
            {
                return 0;
            }
            return _loc_2.minPayout;
        }//end

        private static int  getMinItemCoinPayout (Item param1 )
        {
            switch(param1.type)
            {
                case "business":
                {
                    return getMinBusinessCoinPayout(param1);
                }
                case "attraction":
                {
                    return getMinAttractionCoinPayout(param1);
                }
                default:
                {
                    break;
                }
            }
            return 0;
        }//end

        private static double  getMinItemGoodsPayout (Item param1 )
        {
            _loc_2 =Global.goalManager.getGoal(GoalManager.GOAL_MASTERY )as MasteryGoal ;
            _loc_3 = _loc_2.getBonusMultiplier(param1.name )/100+1;
            _loc_4 = param1.getDooberMinimums(Doober.DOOBER_GOODS );
            if (param1.getDooberMinimums(Doober.DOOBER_GOODS) <= 0)
            {
                _loc_4 = param1.getDooberMinimums(Doober.DOOBER_PREMIUM_GOODS);
            }
            return _loc_4 * _loc_3;
        }//end

        private static boolean  isHarvestableForGoods (Item param1 )
        {
            _loc_2 = param1.type ;
            return _loc_2 == "plot_contract" || _loc_2 == "ship_contract";
        }//end

        public static int  ascending (Object param1 , Object param2)
        {
            String _loc_4 =null ;
            _loc_3 = (String)param1;
            if (_loc_3 != null)
            {
                _loc_4 =(String) param2;
                if (_loc_4 != null)
                {
                    return _loc_3.localeCompare(_loc_4);
                }
            }
            if (param1 < param2)
            {
                return -1;
            }
            if (param2 < param1)
            {
                return 1;
            }
            return 0;
        }//end

        public static int  descending (Object param1 , Object param2)
        {
            return ascending(param2, param1);
        }//end

        public static Function  caseInsensitive (Function param1 )
        {
            compare = param1;
            return int  (String param1 ,String param2 )
            {
                return compare(param1.toLocaleLowerCase(), param2.toLocaleLowerCase());
            }//end
            ;
        }//end

        public static Function  byProperty (String param1 ,Function param2 )
        {
            propName = param1;
            compare = param2;
            return int  (Item param1 ,Item param2 )
            {
                return compare(param1.get(propName), param2.get(propName));
            }//end
            ;
        }//end

        public static Function  byMarketPrice (Function param1 )
        {
            compare = param1;
            return int  (Item param1 ,Item param2 )
            {
                _loc_3 = getItemPriceCategory(param1);
                _loc_4 = getItemPriceCategory(param2);
                if (_loc_3 != _loc_4)
                {
                    return _loc_3 - _loc_4;
                }
                return compare(param1.GetItemSalePrice(), param2.GetItemSalePrice());
            }//end
            ;
        }//end

        public static Function  byPremiumMarketPrice (Function param1 )
        {
            compare = param1;
            return int  (Item param1 ,Item param2 )
            {
                _loc_3 = getItemPriceCategory(param1);
                _loc_4 = getItemPriceCategory(param2);
                if (_loc_3 != _loc_4)
                {
                    if (_loc_3 == PRICE_CATEGORY_CASH)
                    {
                        return -1;
                    }
                    if (_loc_4 == PRICE_CATEGORY_CASH)
                    {
                        return 1;
                    }
                    return _loc_3 - _loc_4;
                }
                return compare(param1.GetItemSalePrice(), param2.GetItemSalePrice());
            }//end
            ;
        }//end

        public static Function  byMinCoinPayout (Function param1 )
        {
            compare = param1;
            return int  (Item param1 ,Item param2 )
            {
                return compare(getMinItemCoinPayout(param1), getMinItemCoinPayout(param2));
            }//end
            ;
        }//end

        public static Function  byMinGoodsPayout (Function param1 )
        {
            compare = param1;
            return int  (Item param1 ,Item param2 )
            {
                return compare(getMinItemGoodsPayout(param1), getMinItemGoodsPayout(param2));
            }//end
            ;
        }//end

        public static Function  byGoodsHarvestTime (Function param1 )
        {
            compare = param1;
            return int  (Item param1 ,Item param2 )
            {
                if (isHarvestableForGoods(param1))
                {
                    if (!isHarvestableForGoods(param2))
                    {
                        return -1;
                    }
                }
                else if (isHarvestableForGoods(param2))
                {
                    return 1;
                }
                return compare(param1.growTime, param2.growTime);
            }//end
            ;
        }//end

    }


