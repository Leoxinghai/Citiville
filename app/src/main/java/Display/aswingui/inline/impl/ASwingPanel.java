package Display.aswingui.inline.impl;

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

import Display.aswingui.inline.*;
import Display.aswingui.inline.layout.*;
import Display.aswingui.inline.style.*;

import org.aswing.*;

    public class ASwingPanel extends ASwingObject implements IASwingPanel
    {
        private IASwingLayout m_layout ;
        private Vector<IASwingNode> m_children;
        private Object m_childMap ;
        private int m_childCount ;
        private JPanel m_component ;

        public  ASwingPanel (String param1)
        {
            super(param1);
            return;
        }//end

         public void  destroy ()
        {
            IASwingNode _loc_1 =null ;
            this.m_component = null;
            if (this.m_children)
            {
                for(int i0 = 0; i0 < this.m_children.size(); i0++)
                {
                		_loc_1 = this.m_children.get(i0);

                    _loc_1.destroy();
                }
                this.m_children.length = 0;
                this.m_children = null;
                this.m_childCount = 0;
                this.m_childMap = null;
            }
            super.destroy();
            return;
        }//end

        public IASwingPanel  strings (String param1 )
        {
            m_stringPackage = param1;
            return this;
        }//end

        public IASwingPanel  replacements (Object param1 )
        {
            m_replacements = param1;
            return this;
        }//end

        public IASwingPanel  position (int param1 ,int param2 )
        {
            this.m_x = param1;
            this.m_y = param2;
            return this;
        }//end

        public IASwingPanel  size (int param1 ,int param2 )
        {
            this.m_width = param1;
            this.m_height = param2;
            return this;
        }//end

        public IASwingPanel  style (IASwingStyle param1 )
        {
            m_style = param1;
            return this;
        }//end

        public IASwingPanel  layout (IASwingLayout param1 )
        {
            this.m_layout = param1;
            return this;
        }//end

        public IASwingPanel  add (IASwingNode param1 )
        {
            IASwingNode _loc_2 =null ;
            if (param1 !=null)
            {
                if (!this.m_children)
                {
                    this.m_children = new Vector<IASwingNode>();
                    this.m_childMap = {};
                }
                _loc_2 = this.m_childMap.get(param1.id);
                if (_loc_2)
                {
                    this.m_children.splice(this.m_children.indexOf(_loc_2), 1);
                    this.m_childCount--;
                }
                this.m_children.push(param1);
                this.m_childMap.put(param1.id,  param1);
                this.m_childCount++;
            }
            return this;
        }//end

        public IASwingNode  (String param1 )
        {
            IASwingNode _loc_2 =null ;
            int _loc_3 =0;
            IASwingPanel _loc_4 =null ;
            if (this.m_childMap)
            {
                if (this.m_childMap.hasOwnProperty(param1))
                {
                    _loc_2 = this.m_childMap.get(param1);
                }
                else
                {
                    _loc_3 = 0;
                    while (!_loc_2 && _loc_3 < this.m_childCount)
                    {

                        _loc_4 =(IASwingPanel) this.m_children.get(_loc_3);
                        if (_loc_4)
                        {
                            _loc_2 = _loc_4.get(param1);
                        }
                        _loc_3++;
                    }
                }
            }
            return _loc_2;
        }//end

        public Component  component ()
        {
            IASwingNode _loc_1 =null ;
            if (!this.m_layout)
            {
                this.m_layout = ASwingLayoutFactory.getInstance().empty;
            }
            if (!this.m_component)
            {
                this.m_component = new JPanel(this.m_layout.manager);
                this.initialize(this.m_component);
                for(int i0 = 0; i0 < this.m_children.size(); i0++)
                {
                		_loc_1 = this.m_children.get(i0);

                    _loc_1.styles.inherit(m_style);
                    if (m_stringPackageSet && !_loc_1.stringPackageSet)
                    {
                        _loc_1.stringPackage = m_stringPackage;
                    }
                    this.m_component.append(_loc_1.component, this.m_layout.constrain(_loc_1));
                }
            }
            return this.m_component;
        }//end

    }


