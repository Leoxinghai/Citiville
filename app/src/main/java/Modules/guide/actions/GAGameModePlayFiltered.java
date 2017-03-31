package Modules.guide.actions;

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
import Engine.Classes.*;
import GameMode.*;
//import flash.utils.*;

    public class GAGameModePlayFiltered extends GuideAction
    {
        protected Array m_classes ;
        protected Array m_itemNames ;

        public  GAGameModePlayFiltered ()
        {
            this.m_classes = new Array();
            this.m_itemNames = new Array();
            return;
        }//end

         public boolean  createFromXml (XML param1 )
        {
            XML inclXML ;
            String className ;
            Object def ;
            xml = param1;
            incl = checkAndGetElement(xml,"include");
            if (!incl)
            {
                return false;
            }


            for(int i0 = 0; i0 <  i0 = 0; i0 < lXML in incl.size(); i0++.size(); i0++)
            {
            		lXML =  i0 = 0; i0 < lXML in incl.size(); i0++.get(i0);
            		inclXML = lXML in incl.get(i0);


                className = inclXML.@className;
                if (className != null && className != "")
                {
                    try
                    {
                        def = getDefinitionByName("Classes." + className);
                        this.m_classes.push((Class)def);
                    }
                    catch (e:Error)
                    {
                        return false;
                    }
                }
                if (String(inclXML.@itemName) != "")
                {
                    this.m_itemNames.push(String(inclXML.@itemName));
                }
            }
            return true;
        }//end

         public void  update (double param1 )
        {
            super.update(param1);
            Global.world.addGameMode(new GMPlayFiltered(this.testWorldObject));
            removeState(this);
            return;
        }//end

        protected boolean  testWorldObject (WorldObject param1 )
        {
            Class _loc_3 =null ;
            String _loc_4 =null ;
            boolean _loc_2 =false ;
            if (this.m_classes != null && this.m_classes.length > 0)
            {
                for(int i0 = 0; i0 < this.m_classes.size(); i0++)
                {
                		_loc_3 = this.m_classes.get(i0);

                    if (param1 instanceof _loc_3)
                    {
                        _loc_2 = true;
                        break;
                    }
                }
            }
            else if (this.m_itemNames != null && this.m_itemNames.length > 0)
            {
                for(int i0 = 0; i0 < this.m_itemNames.size(); i0++)
                {
                		_loc_4 = this.m_itemNames.get(i0);

                    if (param1 instanceof MapResource && (param1 as MapResource).getItemName() == _loc_4)
                    {
                        _loc_2 = true;
                        break;
                    }
                }
            }
            return _loc_2;
        }//end

    }



