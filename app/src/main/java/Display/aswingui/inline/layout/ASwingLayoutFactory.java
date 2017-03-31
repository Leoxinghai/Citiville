package Display.aswingui.inline.layout;

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

import org.aswing.*;
import org.aswing.colorchooser.*;

    public class ASwingLayoutFactory
    {
        private static ASwingLayoutFactory layouts ;

        public  ASwingLayoutFactory (PrivateConstructor param1 )
        {
            return;
        }//end

        public IASwingLayout  center ()
        {
            return new Layout(this.centerGenerator);
        }//end

        private LayoutManager  centerGenerator (IASwingLayout param1 )
        {
            return new CenterLayout();
        }//end

        public IASwingLayout  border ()
        {
            return new Layout(this.borderGenerator, [BorderLayout.NORTH, BorderLayout.WEST, BorderLayout.SOUTH, BorderLayout.EAST, BorderLayout.CENTER]);
        }//end

        private LayoutManager  borderGenerator (IASwingLayout param1 )
        {
            _loc_2 = param1as Layout ;
            _loc_3 = isNaN(_loc_2.m_verticalGap)? (_loc_2.m_gap) : (_loc_2.m_verticalGap);
            _loc_4 = isNaN(_loc_2.m_horizontalGap)? (_loc_2.m_gap) : (_loc_2.m_horizontalGap);
            return new BorderLayout(_loc_4, _loc_3);
        }//end

        public IASwingLayout  box ()
        {
            return new Layout(this.boxGenerator);
        }//end

        private LayoutManager  boxGenerator (IASwingLayout param1 )
        {
            _loc_2 = param1as Layout ;
            SoftBoxLayout _loc_3 =new SoftBoxLayout ();
            _loc_4 = _loc_2.m_align ;
            _loc_5 = _loc_2.m_gap ;
            _loc_3.setAlign(_loc_4);
            _loc_3.setGap(_loc_5);
            return _loc_3;
        }//end

        public IASwingLayout  empty ()
        {
            return new Layout(this.emptyGenerator);
        }//end

        private LayoutManager  emptyGenerator (IASwingLayout param1 )
        {
            return new EmptyLayout();
        }//end

        public IASwingLayout  flow ()
        {
            return new Layout(this.flowGenerator);
        }//end

        private LayoutManager  flowGenerator (IASwingLayout param1 )
        {
            _loc_2 = param1as Layout ;
            FlowLayout _loc_3 =new FlowLayout ();
            _loc_4 = isNaN(_loc_2.m_verticalGap)? (_loc_2.m_gap) : (_loc_2.m_verticalGap);
            _loc_5 = isNaN(_loc_2.m_horizontalGap)? (_loc_2.m_gap) : (_loc_2.m_horizontalGap);
            _loc_3.setVgap(_loc_4);
            _loc_3.setHgap(_loc_5);
            _loc_3.setAlign(_loc_2.m_align);
            _loc_3.setMargin(_loc_2.m_margin);
            return _loc_3;
        }//end

        public IASwingLayout  horizontal ()
        {
            return new Layout(this.horizontalGenerator);
        }//end

        private LayoutManager  horizontalGenerator (IASwingLayout param1 )
        {
            _loc_2 = param1as Layout ;
            SoftBoxLayout _loc_3 =new SoftBoxLayout(SoftBoxLayout.X_AXIS );
            _loc_4 = isNaN(_loc_2.m_horizontalGap)? (_loc_2.m_gap) : (_loc_2.m_horizontalGap);
            _loc_5 = isNaN(_loc_2.m_horizontalAlign)? (_loc_2.m_align) : (_loc_2.m_horizontalAlign);
            _loc_3.setGap(_loc_4);
            _loc_3.setAlign(_loc_5);
            return _loc_3;
        }//end

        public IASwingLayout  vertical ()
        {
            return new Layout(this.verticalGenerator);
        }//end

        private LayoutManager  verticalGenerator (IASwingLayout param1 )
        {
            _loc_2 = param1as Layout ;
            _loc_3 = isNaN(_loc_2.m_verticalAlign)? (_loc_2.m_align) : (_loc_2.m_verticalAlign);
            _loc_4 = isNaN(_loc_2.m_verticalGap)? (_loc_2.m_gap) : (_loc_2.m_verticalGap);
            _loc_5 = _loc_3==VerticalLayout.CENTER || _loc_3 == VerticalLayout.LEFT || _loc_3 == VerticalLayout.RIGHT ? (new VerticalLayout(_loc_3, _loc_4)) : (new SoftBoxLayout(SoftBoxLayout.Y_AXIS, _loc_4, _loc_3));
            return _loc_3 == VerticalLayout.CENTER || _loc_3 == VerticalLayout.LEFT || _loc_3 == VerticalLayout.RIGHT ? (new VerticalLayout(_loc_3, _loc_4)) : (new SoftBoxLayout(SoftBoxLayout.Y_AXIS, _loc_4, _loc_3));
        }//end

        public static ASwingLayoutFactory  getInstance ()
        {
            if (!layouts)
            {
                layouts = new ASwingLayoutFactory(new PrivateConstructor());
            }
            return layouts;
        }//end

    }
class PrivateConstructor

     PrivateConstructor ()
    {
        return;
    }//end


import Display.aswingui.inline.layout.*;
import Display.aswingui.inline.*
import org.aswing.LayoutManager;

class Layout implements IASwingLayout
    public int m_gap =1;
    public int m_align ;
    public double m_horizontalGap ;
    public double m_verticalGap ;
    public double m_horizontalAlign ;
    public double m_verticalAlign ;
    public boolean m_margin =true ;
    private LayoutManager m_layout ;
    private Function m_generator ;
    private Array m_constraints ;

     Layout (Function param1 ,Array param2 )
    {
        this.m_generator = param1;
        this.m_constraints = param2;
        return;
    }//end

    public IASwingLayout  gap (int param1 )
    {
        this.m_gap = param1;
        return this;
    }//end

    public IASwingLayout  align (int param1 )
    {
        this.m_align = param1;
        return this;
    }//end

    public IASwingLayout  horizontalGap (int param1 )
    {
        this.m_horizontalGap = param1;
        return this;
    }//end

    public IASwingLayout  verticalGap (int param1 )
    {
        this.m_verticalGap = param1;
        return this;
    }//end

    public IASwingLayout  horizontalAlign (int param1 )
    {
        this.m_horizontalAlign = param1;
        return this;
    }//end

    public IASwingLayout  verticalAlign (int param1 )
    {
        this.m_verticalAlign = param1;
        return this;
    }//end

    public IASwingLayout  margin (boolean param1 )
    {
        this.m_margin = param1;
        return this;
    }//end

    public LayoutManager  manager ()
    {
        if (!this.m_layout)
        {
            this.m_layout = this.m_generator(this);
        }
        return this.m_layout;
    }//end

    public Object  constrain (IASwingNode param1 )
    {
        Object _loc_2 =null ;
        if (this.m_constraints)
        {
            if (!isNaN(param1.top))
            {
                _loc_2 = this.m_constraints.get(0);
            }
            if (!isNaN(param1.left))
            {
                _loc_2 = this.m_constraints.get(1);
            }
            if (!isNaN(param1.bottom))
            {
                _loc_2 = this.m_constraints.get(2);
            }
            if (!isNaN(param1.right))
            {
                _loc_2 = this.m_constraints.get(3);
            }
            if (!_loc_2)
            {
                _loc_2 = this.m_constraints.get(4);
            }
        }
        return _loc_2;
    }//end




