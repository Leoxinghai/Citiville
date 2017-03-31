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

import Classes.PopulationTriggers.*;
import Display.*;
import Display.DialogUI.*;
import Display.Toaster.*;
import com.adobe.utils.*;
//import flash.utils.*;
import Classes.sim.*;

    public class PopulationMilestoneManager implements IPopulationStateObserver
    {
        private int m_lastPopulation ;
        protected Dictionary m_populationTriggers ;
        public static  String POPULATION_MILESTONE_NOTIFICATION_ACTIVATION_KEY ="populationMilestoneNotification";

        public  PopulationMilestoneManager ()
        {
            Global.world.citySim.addObserver(this);
            if (!Global.isVisiting())
            {
                this.m_lastPopulation = Global.world.citySim.getPopulation();
                this.loadPopulationTriggers();
            }
            return;
        }//end

        protected void  loadPopulationTriggers ()
        {
            XML _loc_2 =null ;
            IPopulationTrigger _loc_3 =null ;
            int _loc_4 =0;
            this.m_populationTriggers = new Dictionary();
            _loc_1 = Global.gameSettings().getPopulationTriggersXML();
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            	_loc_2 = _loc_1.get(i0);

                if (String(_loc_2.@experiment).length > 0 && String(_loc_2.@expectedVariant).length > 0)
                {
                    if (Global.experimentManager.getVariant(String(_loc_2.@experiment)) != parseInt(_loc_2.@expectedVariant))
                    {
                        continue;
                    }
                }
                if (String(_loc_2.@className).length <= 0 || String(_loc_2.@requiredPopulation).length <= 0)
                {
                    continue;
                }
                _loc_3 = PopulationTriggerFactory.createPopulationTrigger(_loc_2);
                if (!_loc_3)
                {
                    continue;
                }
                _loc_4 = parseInt(_loc_2.@requiredPopulation);
                if (!this.m_populationTriggers.get(_loc_4))
                {
                    this.m_populationTriggers.put(_loc_4,  new Array());
                }
                ((Array)this.m_populationTriggers.get(_loc_4)).push(_loc_3);
            }
            return;
        }//end

        public void  onPopulationInit (int param1 ,int param2 ,int param3 )
        {
            return;
        }//end

        public void  onPopulationChanged (int param1 ,int param2 ,int param3 ,int param4 )
        {
            int populationDelta ;
            double currTime ;
            int lastNotificationTime ;
            XMLList milestoneItems ;
            XMLList thresholdPassingItems ;
            Array triggerIndices ;
            int index ;
            XMLList nextMilestoneThresholdItems ;
            XML nextMilestoneThresholdItem ;
            XML currItem ;
            ItemToaster toaster ;
            String thresholdId ;
            XML lastThresholdPassingItem ;
            XML currThresholdItem ;
            String strButtonText ;
            String strDialogTitle ;
            String strDialogText ;
            String strDialogIcon ;
            XMLList nextThresholdPassingItems ;
            String seenFlag ;
            XML nextThresholdPassingItem ;
            XML currNextThresholdItem ;
            GenericPictureDialog dlg ;
            IPopulationTrigger trigger ;
            newRaw = param1;
            deltaRaw = param2;
            newScaled = param3;
            newTotal = param4;
	    int _loc_7 =0;
	    XMLList _loc_8 ;
	    XMLList _loc_6 ;
            Object _loc_9;
            Object _loc_10;

            if (!Global.isVisiting())
            {
                populationDelta = newRaw - this.m_lastPopulation;
                if (populationDelta > 0)
                {
                    currTime = GlobalEngine.getTimer() / 1000;
                    lastNotificationTime = Global.player.getLastActivationTime(POPULATION_MILESTONE_NOTIFICATION_ACTIVATION_KEY);
                    _loc_7 = 0;
                    _loc_8 = Global.gameSettings().getItems();
                    _loc_6 = new XMLList("");
                    for(int i0 = 0; i0 < _loc_8.size(); i0++)
                    {
                    	_loc_9 = _loc_8.get(i0);


                        with (_loc_9)
                        {
                            if (attribute("isPopulationMilestoneTarget") == "true")
                            {
                                _loc_6.put(_loc_7++,  _loc_9);
                            }
                        }
                    }
                    milestoneItems = _loc_6;
                    if (currTime - lastNotificationTime > Global.gameSettings().getNumber("populationMilestoneNoticeInterval", 0))
                    {
                        _loc_7 = 0;
                        _loc_8 = milestoneItems;
                        _loc_6 = new XMLList("");
                        for(int i0 = 0; i0 < _loc_8.size(); i0++)
                        {
                        	_loc_9 = _loc_8.get(i0);


                            with (_loc_9)
                            {
                                if (child("requiredPopulation") > newRaw && newRaw >= child("requiredPopulation") * Global.gameSettings().getNumber("populationMilestoneNoticeRatio", 0.5))
                                {
                                    _loc_6.put(_loc_7++,  _loc_9);
                                }
                            }
                        }
                        nextMilestoneThresholdItems = _loc_6;
                        if (nextMilestoneThresholdItems.length() > 0)
                        {
                            nextMilestoneThresholdItem = nextMilestoneThresholdItems.get(0);
                            int _loc_62 =0;
                            _loc_72 = nextMilestoneThresholdItems;
                            for(int i0 = 0; i0 < nextMilestoneThresholdItems.size(); i0++)
                            {
                            		currItem = nextMilestoneThresholdItems.get(i0);


                                if (parseInt(currItem.child("requiredPopulation")) < parseInt(nextMilestoneThresholdItem.child("requiredPopulation")))
                                {
                                    nextMilestoneThresholdItem = currItem;
                                }
                            }
                            toaster = new ItemToaster(ZLoc.t("Main", "ItemToasterGrowingTitle"), ZLoc.t("Main", "ItemToasterGrowingBody", {item:Global.gameSettings().getItemFriendlyName(nextMilestoneThresholdItem.attribute("name")), population:(nextMilestoneThresholdItem.child("requiredPopulation") - newRaw) * Global.gameSettings().getNumber("populationScale", 1)}), Global.gameSettings().getImageByName(nextMilestoneThresholdItem.attribute("name"), "icon"));
                            Global.ui.toaster.show(toaster);
                            Global.player.setLastActivationTime(POPULATION_MILESTONE_NOTIFICATION_ACTIVATION_KEY, currTime);
                        }
                    }
                    _loc_7 = 0;
                    _loc_8 = milestoneItems;
                    _loc_6 = new XMLList("");
                    for(int i0 = 0; i0 < _loc_8.size(); i0++)
                    {
                    	_loc_9 = _loc_8.get(i0);


                        with (_loc_9)
                        {
                            if (child("requiredPopulation") > m_lastPopulation && child("requiredPopulation") <= newRaw)
                            {
                                _loc_6.put(_loc_7++,  _loc_9);
                            }
                        }
                    }
                    thresholdPassingItems = _loc_6;
                    if (thresholdPassingItems.length() > 0)
                    {
                        thresholdId;
                        lastThresholdPassingItem = thresholdPassingItems.get(0);
                        int _loc_63 =0;
                        _loc_73 = thresholdPassingItems;
                        for(int i0 = 0; i0 < thresholdPassingItems.size(); i0++)
                        {
                        	currThresholdItem = thresholdPassingItems.get(i0);


                            if (parseInt(currThresholdItem.child("requiredPopulation")) > parseInt(lastThresholdPassingItem.child("requiredPopulation")))
                            {
                                lastThresholdPassingItem = currThresholdItem;
                                thresholdId = currThresholdItem.child("requiredPopulation").toString();
                            }
                        }
                        strButtonText;
                        strDialogTitle = ZLoc.t("Dialogs", "PopulationMilestone_title", {population:newScaled});
                        strDialogText = ZLoc.t("Dialogs", "PopulationMilestone_message_end", {population:newScaled});
                        strDialogIcon;
                        _loc_7 = 0;
                        _loc_8 = milestoneItems;
                        _loc_6 = new XMLList("");
                        for(int i0 = 0; i0 < _loc_8.size(); i0++)
                        {
                        	_loc_9 = _loc_8.get(i0);


                            with (_loc_9)
                            {
                                if (child("requiredPopulation") > newRaw)
                                {
                                    _loc_6.put(_loc_7++,  _loc_9);
                                }
                            }
                        }
                        nextThresholdPassingItems = _loc_6;
                        if (nextThresholdPassingItems.length() > 0)
                        {
                            nextThresholdPassingItem = nextThresholdPassingItems.get(0);
                            int _loc_64 =0;
                            _loc_74 = nextThresholdPassingItems;
                            for(int i0 = 0; i0 < nextThresholdPassingItems.size(); i0++)
                            {
                            	currNextThresholdItem = nextThresholdPassingItems.get(i0);


                                if (parseInt(currNextThresholdItem.child("requiredPopulation")) < parseInt(nextThresholdPassingItem.child("requiredPopulation")))
                                {
                                    nextThresholdPassingItem = currNextThresholdItem;
                                }
                            }
                            strDialogText = ZLoc.t("Dialogs", "PopulationMilestone_message", {item:Global.gameSettings().getItemFriendlyName(nextThresholdPassingItem.attribute("name")), population:nextThresholdPassingItem.child("requiredPopulation") * Global.gameSettings().getNumber("populationScale", 1), cityName:Global.player.cityName});
                        }
                        seenFlag = "population_trigger_" + thresholdId;
                        if (!Global.player.getSeenSessionFlag(seenFlag))
                        {
                            dlg = new GenericPictureDialog(strDialogText, strDialogTitle, GenericDialogView.TYPE_OK, null, strDialogTitle, "", true, 0, strButtonText, strDialogIcon);
                            UI.displayPopup(dlg);
                            Global.player.setSeenSessionFlag(seenFlag);
                        }
                    }
                    triggerIndices = DictionaryUtil.getKeys(this.m_populationTriggers);
                    triggerIndices.sort(Array.NUMERIC);


                    for(int i0 = 0; i0 <  i0 = 0; i0 < ex in triggerIndices.size(); i0++.size(); i0++)
                    {
                    		ex =  i0 = 0; i0 < ex in triggerIndices.size(); i0++.get(i0);
                    	index = ex in triggerIndices.get(i0);


                        if (index <= this.m_lastPopulation)
                        {
                            continue;
                        }
                        else if (index > newRaw)
                        {
                            break;
                        }


                        for(int i0 = 0; i0 < this.m_populationTriggers.get(index).size(); i0++)
                        {
                        	trigger = this.m_populationTriggers.get(index).get(i0);


                            if (trigger.hasTriggered())
                            {
                                continue;
                            }
                            trigger.trigger();
                        }
                    }
                }
                this.m_lastPopulation = Global.world.citySim.getPopulation();
            }
            return;
        }//end

        public void  onPotentialPopulationChanged (int param1 ,int param2 )
        {
            return;
        }//end

        public void  onPopulationCapChanged (int param1 )
        {
            return;
        }//end

    }



