package Modules.socialinventory;

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
import Classes.effects.*;
import Classes.inventory.*;
import Display.Toaster.*;
import Engine.*;
import Engine.Classes.*;
import Mechanics.GameMechanicInterfaces.*;
import Modules.stats.experiments.*;
import Modules.stats.types.*;
//import flash.geom.*;
//import flash.utils.*;

    public class SocialInventoryManager extends SupplyStorage
    {
        protected Dictionary m_samObjectIds ;
        public static  String MECHANIC_DATA ="socialInventory";
        private static  int MIN_LEVEL =10;
        private static  int DEFAULT_HEART_CAPACITY =5;
        private static  Point ANIM_DEST =new Point(711,125);
        private static SocialInventoryManager s_instance ;

        public  SocialInventoryManager ()
        {
            super(null);
            return;
        }//end

        public void  setCapacitiesFromXml (int param1 )
        {
            _loc_2 =Global.gameSettings().getReputationLevelXML(param1 );
            if (_loc_2.attribute("heartCapacity"))
            {
                this.setCapacity("heart", parseInt(_loc_2.@heartCapacity));
            }
            return;
        }//end

         public boolean  isValidName (String param1 )
        {
            return true;
        }//end

        public Array  getCitySamObjectIds ()
        {
            String _loc_2 =null ;
            Array _loc_1 =new Array();
            if (this.m_samObjectIds)
            {
                for(int i0 = 0; i0 < this.m_samObjectIds.size(); i0++)
                {
                		_loc_2 = this.m_samObjectIds.get(i0);

                    _loc_1.push(parseInt(_loc_2));
                }
            }
            return _loc_1;
        }//end

        public boolean  hasCollectedCitySamObject (WorldObject param1 )
        {
            return this.m_samObjectIds.get(param1.getId().toString()) != null;
        }//end

        public void  addCitySamObject (WorldObject param1 )
        {
            if (!this.m_samObjectIds)
            {
                this.m_samObjectIds = new Dictionary();
            }
            this.m_samObjectIds.put(param1.getId().toString(),  0);
            return;
        }//end

        public static SocialInventoryManager  instance ()
        {
            if (!s_instance)
            {
                s_instance = new SocialInventoryManager;
            }
            return s_instance;
        }//end

        public static void  clearInstance ()
        {
            s_instance = null;
            return;
        }//end

        public static boolean  isFeatureAvailable ()
        {
            _loc_1 =Global.player.level >=MIN_LEVEL ;
            _loc_2 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_NEIGHBOR_VISIT_2 )==ExperimentDefinitions.NEIGHBOR_VISIT_2_FEATURE ;
            return _loc_1 && _loc_2;
        }//end

        public static void  setData (Object param1 )
        {
            Object _loc_2 =null ;
            Dictionary _loc_3 =null ;
            String _loc_4 =null ;
            if (param1.hasOwnProperty("inventory"))
            {
                _loc_2 = {storage:param1.get("inventory")};
                instance.loadObject(_loc_2);
            }
            if (param1.hasOwnProperty("samObjectIds"))
            {
                _loc_3 = new Dictionary();
                for(int i0 = 0; i0 < param1.get("samObjectIds").size(); i0++)
                {
                		_loc_4 = param1.get("samObjectIds").get(i0);

                    _loc_3.put(_loc_4,  param1.get("samObjectIds").get(_loc_4));
                }
                instance.m_samObjectIds = _loc_3;
            }
            return;
        }//end

        public static void  onNeighborVisit (Object param1)
        {
            Array _loc_2 =null ;
            int _loc_3 =0;
            if (isFeatureAvailable())
            {
                if (Global.getVisiting() == "-1")
                {
                    _loc_2 = instance.getCitySamObjectIds();
                    _loc_3 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_BUY_ON_VISIT);
                    if (_loc_3 == ExperimentDefinitions.BUY_ON_VISIT_EXPERIMENT)
                    {
                        if (Global.player.visitedCitySamVersion == -1)
                        {
                            Global.guide.notify("GuidePurchaseBuildings");
                        }
                        Global.player.hasVisitedCitySam = true;
                        Global.player.visitedCitySamVersion = Global.gameSettings().getCitySamVersion();
                        Global.player.updateCitySamVisit();
                    }
                }
                showAllHearts(_loc_2);
            }
            return;
        }//end

        public static double  getSocialDropMultiplierForObject (IMechanicUser param1 )
        {
            _loc_2 = param1.getDataForMechanic(MECHANIC_DATA );
            if (_loc_2 && _loc_2.get("heart"))
            {
                return 1.25;
            }
            return 1;
        }//end

        public static void  showIneligibleToaster ()
        {
            String _loc_1 ="";
            _loc_2 = ZLoc.t("Dialogs","NeighborVisits2_IneligibleToaster");
            TickerToaster _loc_3 =new TickerToaster(_loc_2 );
            Global.ui.toaster.show(_loc_3, true);
            return;
        }//end

        public static void  showFirstToaster ()
        {
            String _loc_1 =null ;
            String _loc_2 =null ;
            String _loc_3 =null ;
            ItemToaster _loc_4 =null ;
            if (Global.player.getSeenSessionFlag("socialInventory_toaster1") != true)
            {
                _loc_1 = "";
                _loc_2 = ZLoc.t("Dialogs", "NeighborVisits2_PlaceHeartsToaster1");
                _loc_3 = Global.getAssetURL("assets/neighbor_visit/neighborVisits2_counter.png");
                _loc_4 = new ItemToaster(_loc_1, _loc_2, _loc_3);
                Global.ui.toaster.show(_loc_4);
                Global.player.setSeenSessionFlag("socialInventory_toaster1");
            }
            return;
        }//end

        public static void  showSecondToaster ()
        {
            String _loc_1 =null ;
            String _loc_2 =null ;
            String _loc_3 =null ;
            ItemToaster _loc_4 =null ;
            if (Global.player.getSeenSessionFlag("socialInventory_toaster2") != true)
            {
                _loc_1 = "";
                _loc_2 = ZLoc.t("Dialogs", "NeighborVisits2_PlaceHeartsToaster2");
                _loc_3 = Global.getAssetURL("assets/neighbor_visit/neighborVisits2_counter.png");
                _loc_4 = new ItemToaster(_loc_1, _loc_2, _loc_3);
                Global.ui.toaster.show(_loc_4);
                Global.player.setSeenSessionFlag("socialInventory_toaster2");
            }
            return;
        }//end

        public static void  showThirdToaster ()
        {
            String _loc_1 =null ;
            String _loc_2 =null ;
            String _loc_3 =null ;
            ItemToaster _loc_4 =null ;
            if (Global.player.getSeenSessionFlag("socialInventory_toaster3") != true)
            {
                _loc_1 = "";
                _loc_2 = ZLoc.t("Dialogs", "NeighborVisits2_PlaceHeartsToaster3");
                _loc_3 = Global.getAssetURL("assets/neighbor_visit/neighborVisits2_counter.png");
                _loc_4 = new ItemToaster(_loc_1, _loc_2, _loc_3, StatsPhylumType.NEIGHBORS2_TOASTER3);
                Global.ui.toaster.hide(true);
                Global.ui.toaster.show(_loc_4);
                Global.player.setSeenSessionFlag("socialInventory_toaster3");
            }
            return;
        }//end

        public static void  showAllHearts (Array param1)
        {
            MechanicMapResource _loc_2 =null ;
            Object _loc_3 =null ;
            for(int i0 = 0; i0 < Global.world.getObjectsByClass(MechanicMapResource).size(); i0++)
            {
            		_loc_2 = Global.world.getObjectsByClass(MechanicMapResource).get(i0);

                if (param1 && param1.indexOf(_loc_2.getId()) != -1)
                {
                    continue;
                }
                _loc_3 = _loc_2.getDataForMechanic(MECHANIC_DATA);
                if (_loc_3)
                {
                    if (_loc_3.get("heart"))
                    {
                        attachHeartIndicator(_loc_2, _loc_3.get("heart"));
                        continue;
                    }
                    detachHeartIndicator(_loc_2);
                }
            }
            return;
        }//end

        public static void  hideAllHearts ()
        {
            MechanicMapResource _loc_1 =null ;
            Object _loc_2 =null ;
            for(int i0 = 0; i0 < Global.world.getObjectsByClass(MechanicMapResource).size(); i0++)
            {
            		_loc_1 = Global.world.getObjectsByClass(MechanicMapResource).get(i0);

                _loc_2 = _loc_1.getDataForMechanic(MECHANIC_DATA);
                if (_loc_2)
                {
                    if (_loc_2.get("heart"))
                    {
                        detachHeartIndicator(_loc_1);
                    }
                }
            }
            return;
        }//end

        public static void  attachHeartIndicator (MapResource param1 ,int param2)
        {
            _loc_3 = (StagePickEffect)MapResourceEffectFactory.createEffect(param1,EffectType.STAGE_PICK)
            if (param1.stagePickEffect)
            {
                _loc_3.pickOffset = param1.getToolTipFloatOffset() + 5;
            }
            _loc_3.drawBackground = false;
            _loc_3.setPickType(StagePickEffect.PICK_HEART);
            _loc_3.queuedFloat();
            param1.addAnimatedEffectFromInstance(_loc_3);
            return;
        }//end

        public static void  detachHeartIndicator (MapResource param1 )
        {
            _loc_2 = param1.getAnimatedEffectByClass(StagePickEffect )as StagePickEffect ;
            if (_loc_2)
            {
                _loc_2.stopFloat();
                param1.removeAnimatedEffectByClass(StagePickEffect);
            }
            return;
        }//end

        public static void  sendDooberToWorldObject (MapResource param1 )
        {
            Point _loc_2 =null ;
            if (param1 !=null)
            {
                _loc_2 = IsoMath.viewportToStage(param1.tileScreenPosition);
                _loc_2.y = _loc_2.y - param1.getDisplayObject().height;
                Global.world.dooberManager.createDummyDoober("doober_rep", ANIM_DEST, _loc_2);
            }
            return;
        }//end

        public static void  sendDooberFromWorldObject (MapResource param1 )
        {
            Point _loc_2 =null ;
            if (param1 !=null)
            {
                _loc_2 = IsoMath.viewportToStage(param1.tileScreenPosition);
                _loc_2.y = _loc_2.y - param1.getDisplayObject().height;
                Global.world.dooberManager.createDummyDoober("doober_rep", _loc_2, ANIM_DEST);
            }
            return;
        }//end

    }



