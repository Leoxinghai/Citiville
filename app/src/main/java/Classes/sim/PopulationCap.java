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
    public class PopulationCap
    {
        protected String m_type =null ;
        protected int m_capYield =0;
        protected Dictionary m_children ;
        protected int m_childrenSize =0;
        public static  String RESOURCE_ID ="populationCap";

        public  PopulationCap (int param1 =0,String param2 ="mixed")
        {
            this.m_children = new Dictionary();
            this.m_type = param2;
            if (!param2)
            {
                this.m_type = Population.MIXED;
            }
            if (Population.MIXED != this.m_type)
            {
                this.m_capYield = param1;
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
                this.m_capYield =(Number) param1;
            }
            else if (param1 == null)
            {
                this.m_type = Population.CITIZEN;
                this.m_capYield = 0;
            }
            else
            {
                this.m_type = param1.type;
                this.m_capYield = param1.capYield;
                if (param1.hasOwnProperty("children"))
                {
                    for(int i0 = 0; i0 < param1.children.size(); i0++)
                    {
                    	_loc_2 = param1.children.get(i0);

                        _loc_3 = param1.children.get(_loc_2);
                        this.m_children.put(_loc_2,  new PopulationCap(_loc_3.capYield, _loc_3.type));
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

        public int  getCapYield (String param1)
        {
            PopulationCap _loc_3 =null ;
            int _loc_2 =0;
            if (!param1)
            {
                param1 = this.m_type;
            }
            if (param1 && param1 == this.m_type)
            {
                _loc_2 = this.m_capYield;
            }
            else if (param1 && this.m_children.get(param1))
            {
                _loc_3 = this.m_children.get(param1);
                _loc_2 = _loc_3.getCapYield(param1);
            }
            return _loc_2;
        }//end

        public int  addCapYield (int param1 ,String param2 )
        {
            PopulationCap _loc_4 =null ;
            PopulationCap _loc_5 =null ;
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
                _loc_3 = 0;
                if (_loc_4)
                {
                    this.m_capYield = this.m_capYield + param1;
                    if (this.m_capYield < 0)
                    {
                        this.m_capYield = 0;
                    }
                    _loc_3 = param1;
                }
                if (_loc_5)
                {
                    _loc_5.addCapYield(param1, param2);
                    _loc_3 = param1;
                }
            }
            return _loc_3;
        }//end

        public Dictionary  children ()
        {
            return this.m_children;
        }//end

        public void  merge (PopulationCap param1 )
        {
            String _loc_2 =null ;
            PopulationCap _loc_3 =null ;
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
                    _loc_4 = _loc_3.getCapYield(_loc_2);
                    if (Population.MIXED != this.m_type)
                    {
                        this.m_children.put(this.m_type,  new PopulationCap(this.m_capYield, this.m_type));
                        this.m_type = Population.MIXED;
                    }
                    this.addCapYield(_loc_4, _loc_2);
                    if (!this.m_children.get(_loc_2))
                    {
                        this.m_children.put(_loc_2,  _loc_3);
                        this.m_childrenSize++;
                    }
                }
                if (_loc_2 && Population.MIXED != _loc_2 && _loc_6 == 0)
                {
                    _loc_7 = param1.getCapYield(_loc_2);
                    if (this.m_childrenSize)
                    {
                        this.addCapYield(_loc_7, _loc_2);
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
                            this.m_children.put(this.m_type,  new PopulationCap(this.m_capYield, this.m_type));
                            this.m_type = Population.MIXED;
                        }
                        this.addCapYield(_loc_7, _loc_2);
                        if (!this.m_children.get(_loc_2))
                        {
                            this.m_children.put(_loc_2,  param1);
                            this.m_childrenSize++;
                        }
                    }
                    else
                    {
                        this.m_type = _loc_2;
                        this.addCapYield(_loc_7, _loc_2);
                    }
                }
            }
            return;
        }//end

    }



