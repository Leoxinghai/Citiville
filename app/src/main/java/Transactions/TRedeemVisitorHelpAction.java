package Transactions;

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
import Classes.orders.*;
import Classes.util.*;
import Engine.Classes.*;
import Engine.Transactions.*;

    public class TRedeemVisitorHelpAction extends Transaction
    {
        private String m_visitorId ;
        private int m_targetId ;
        private String m_targetClass ;
        private String m_targetName ;
        private String m_action ;
        private VisitorHelpOrder m_order ;
        protected int m_clientEnqueueTime =0;

        public  TRedeemVisitorHelpAction (String param1 ,int param2 ,String param3 ="",String param4 ="",String param5 ="",VisitorHelpOrder param6 =null )
        {
            this.m_visitorId = param1;
            this.m_targetId = param2;
            this.m_targetClass = param3;
            this.m_targetName = param4;
            this.m_action = param5;
            this.m_order = param6;
            return;
        }//end

         public void  perform ()
        {
            signedCall("VisitorService.redeemVisitorHelpAction", this.m_visitorId, this.m_targetId, this.m_targetClass, this.m_targetName, this.m_action, this.m_clientEnqueueTime);
            return;
        }//end

         protected void  onComplete (Object param1 )
        {
            MapResource _loc_2 =null ;
            if (param1.rewardResult != null)
            {
                if (param1.rewardResult.doobers != null && param1.rewardResult.secureRands != null)
                {
                    _loc_2 =(MapResource) Global.world.getObjectById(this.m_targetId);
                    if (_loc_2 != null)
                    {
                        _loc_2.parseAndCheckDooberResults(param1.rewardResult);
                    }
                }
            }
            return;
        }//end

        public WorldObject  getWorldObject ()
        {
            return Global.world.getObjectById(this.m_targetId);
        }//end

        public boolean  shouldGiveMastery ()
        {
            boolean _loc_1 =false ;
            if (this.m_order)
            {
                _loc_1 = this.m_order.shouldHelpTargetGiveMastery(this.m_targetId);
            }
            return _loc_1;
        }//end

        public Class  getEquivalentTransaction (WorldObject param1)
        {
            _loc_2 = param1==null ? (Global.world.getObjectById(this.m_targetId)) : (param1);
            Class _loc_3 =null ;
            if (!_loc_2)
            {
                return null;
            }
            switch(_loc_2.getClassName())
            {
                case "Residence":
                {
                    _loc_3 = THarvest;
                    break;
                }
                case "Wilderness":
                {
                    _loc_3 = TClear;
                    break;
                }
                case "Business":
                {
                    break;
                }
                case "Plot":
                case "Ship":
                {
                    switch(((HarvestableResource)_loc_2).getState())
                    {
                        case HarvestableResource.STATE_WITHERED:
                        {
                            break;
                        }
                        case HarvestableResource.STATE_GROWN:
                        {
                            _loc_3 = THarvest;
                            break;
                        }
                        case HarvestableResource.STATE_PLANTED:
                        {
                            break;
                        }
                        default:
                        {
                            break;
                        }
                    }
                    break;
                }
                case "ConstructionSite":
                {
                    _loc_3 = TConstructionBuild;
                    break;
                }
                default:
                {
                    break;
                }
            }
            return _loc_3;
        }//end

         public void  preAddTransaction ()
        {
            super.preAddTransaction();
            this.m_clientEnqueueTime = DateUtil.getUnixTime();
            return;
        }//end

    }



