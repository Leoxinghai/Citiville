package Classes.sim;

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

//import flash.utils.*;
    public class Population
    {
        protected String m_type =null ;
        protected int m_nowYield =0;
        protected Dictionary m_children =null ;
        protected int m_childrenSize =0;
        public static  String RESOURCE_ID ="population";
        public static  String MIXED ="mixed";
        public static  String CITIZEN ="citizen";
        public static  String TOURIST ="tourist";
        public static  String MONSTER ="monster";

        public  Population (int param1 ,String param2 )
        {
            this.m_type = param2;
            this.m_children = new Dictionary();
            if (!param2)
            {
                this.m_type = Population.MIXED;
            }
            if (Population.MIXED != this.m_type)
            {
                this.m_nowYield = param1;
            }
            return;
        }//end

        public void  loadObject (Object param1 )
        {
            String _loc_2 =null ;
            Object _loc_3 =null ;
            if (param1 instanceof Number)
            {
                this.m_type = Population.CITIZEN;
                this.m_nowYield =(int) param1;
            }
            else if (param1 == null)
            {
                this.m_type = Population.CITIZEN;
                this.m_nowYield = 0;
            }
            else
            {
                this.m_type = param1.type;
                this.m_nowYield = param1.nowYield;
                if (param1.hasOwnProperty("children"))
                {
                    for(int i0 = 0; i0 < param1.children.size(); i0++)
                    {
                    	_loc_2 = param1.children.get(i0);

                        _loc_3 = param1.children.get(_loc_2);
                        this.m_children.put(_loc_2,  new Population(_loc_3.nowYield, _loc_3.type));
                        this.m_childrenSize++;
                    }
                }
            }
            return;
        }//end

        public boolean  hasPopulationType (String param1 )
        {
            boolean _loc_2 =false ;
            if (param1 && (this.m_type == param1 || this.m_children.get(param1)))
            {
                _loc_2 = true;
            }
            return _loc_2;
        }//end

        public String  populationType ()
        {
            return this.m_type;
        }//end

        public Array  populationTypes ()
        {
            String _loc_2 =null ;
            Array _loc_1 =new Array ();
            for(int i0 = 0; i0 < this.m_children.size(); i0++)
            {
            	_loc_2 = this.m_children.get(i0);

                _loc_1.push(_loc_2);
            }
            if (_loc_1.length == 0 && Population.MIXED != this.m_type)
            {
                _loc_1.push(this.m_type);
            }
            return _loc_1;
        }//end

        public int  getYield (String param1)
        {
            int _loc_2 =0;
            if (!param1)
            {
                param1 = this.m_type;
            }
            Population _loc_3 =null ;
            if (param1 == this.m_type)
            {
                _loc_3 = this;
            }
            Population _loc_4 =null ;
            if (param1 && this.m_children.get(param1))
            {
                _loc_4 = this.m_children.get(param1);
            }
            if (_loc_3)
            {
                _loc_2 = this.m_nowYield;
            }
            else if (_loc_4)
            {
                _loc_2 = _loc_4.getYield(param1);
            }
            return _loc_2;
        }//end

        public int  addYield (int param1 ,String param2 )
        {
            Population _loc_4 =null ;
            Population _loc_5 =null ;
            int _loc_3 =0;
            if (!param2)
            {
                param2 = this.m_type;
            }
            if (param1 != 0 && param2 && Population.MIXED != param2)
            {
                _loc_4 = null;
                if (this.m_type == Population.MIXED || param2 == this.m_type)
                {
                    _loc_4 = this;
                }
                _loc_5 = null;
                if (this.m_children.get(param2))
                {
                    _loc_5 = this.m_children.get(param2);
                }
                _loc_3 = param1;
                if (_loc_4)
                {
                    this.m_nowYield = this.m_nowYield + param1;
                }
                if (_loc_5)
                {
                    _loc_5.addYield(param1, param2);
                }
            }
            return _loc_3;
        }//end

        public Dictionary  children ()
        {
            return this.m_children;
        }//end

        public void  merge (Population param1 )
        {
            String _loc_2 =null ;
            Population _loc_3 =null ;
            int _loc_4 =0;
            Dictionary _loc_5 =null ;
            int _loc_6 =0;
            int _loc_7 =0;
            if (param1 !=null)
            {
                _loc_2 = param1.populationType;
                _loc_3 = null;
                _loc_4 = 0;
                _loc_5 = param1.children;
                _loc_6 = 0;
                for(int i0 = 0; i0 < _loc_5.size(); i0++)
                {
                	_loc_2 = _loc_5.get(i0);

                    _loc_6++;
                    _loc_3 = _loc_5.get(_loc_2);
                    _loc_4 = _loc_3.getYield(_loc_2);
                    if (Population.MIXED != this.m_type)
                    {
                        this.m_children.put(this.m_type,  new Population(this.m_nowYield, this.m_type));
                        this.m_type = Population.MIXED;
                    }
                    this.addYield(_loc_4, _loc_2);
                    if (!this.m_children.get(_loc_2))
                    {
                        this.m_children.put(_loc_2,  _loc_3);
                        this.m_childrenSize++;
                    }
                }
                if (_loc_2 && Population.MIXED != _loc_2 && _loc_6 == 0)
                {
                    _loc_7 = param1.getYield(_loc_2);
                    if (this.m_childrenSize)
                    {
                        this.addYield(_loc_7, _loc_2);
                        if (!this.m_children.get(_loc_2))
                        {
                            this.m_children.put(_loc_2,  param1);
                            this.m_childrenSize++;
                        }
                    }
                    else if (Population.MIXED != this.m_type && this.m_type != _loc_2)
                    {
                        if (Population.MIXED != this.m_type)
                        {
                            this.m_children.put(this.m_type,  new Population(this.m_nowYield, this.m_type));
                            this.m_type = Population.MIXED;
                        }
                        this.addYield(_loc_7, _loc_2);
                        if (!this.m_children.get(_loc_2))
                        {
                            this.m_children.put(_loc_2,  param1);
                            this.m_childrenSize++;
                        }
                    }
                    else
                    {
                        this.m_type = _loc_2;
                        this.addYield(_loc_7, _loc_2);
                    }
                }
            }
            return;
        }//end

    }



