package tool;

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
import Display.aswingui.*;
//import flash.display.*;
//import flash.system.*;
import org.aswing.*;
import org.aswing.event.*;

    public class OffsetEditorMover extends JPanel
    {
        private DisplayObject m_child ;
        private JLabel m_label ;
        private GameObject m_gameObject ;
        private boolean m_parent =false ;
        private int m_alphaCounter =0;

        public  OffsetEditorMover (DisplayObject param1 ,GameObject param2 ,boolean param3 =false )
        {
            this.m_child = param1;
            this.m_gameObject = param2;
            this.m_parent = param3;
            this.m_alphaCounter = 0;
            super(new FlowLayout(FlowLayout.CENTER, 2, 2, true));
            this.m_label = new JLabel("x=000" + param1.x + "/y=000" + param1.y);
            this.m_label.setFont(this.m_label.getFont().changeSize(16));
            this.setText();
            JButton _loc_4 =new JButton("U1");
            _loc_4.addActionListener(this.up1, 0, true);
            JButton _loc_5 =new JButton("D1");
            _loc_5.addActionListener(this.down1, 0, true);
            JButton _loc_6 =new JButton("L1");
            _loc_6.addActionListener(this.left1, 0, true);
            JButton _loc_7 =new JButton("R1");
            _loc_7.addActionListener(this.right1, 0, true);
            JButton _loc_8 =new JButton("U10");
            _loc_8.addActionListener(this.up10, 0, true);
            JButton _loc_9 =new JButton("D10");
            _loc_9.addActionListener(this.down10, 0, true);
            JButton _loc_10 =new JButton("L10");
            _loc_10.addActionListener(this.left10, 0, true);
            JButton _loc_11 =new JButton("R10");
            _loc_11.addActionListener(this.right10, 0, true);
            _loc_12 = ASwingHelper.makeSoftBoxJPanel ();
            JButton _loc_13 =new JButton("Trans");
            _loc_13.addActionListener(this.ToggleTrans, 0, true);
            _loc_12.appendAll(ASwingHelper.horizontalStrut(10), this.m_label, _loc_4, _loc_5, _loc_6, _loc_7, _loc_8, _loc_9, _loc_10, _loc_11, _loc_13, ASwingHelper.horizontalStrut(50));
            ASwingHelper.prepare(_loc_12);
            append(_loc_12);
            ASwingHelper.prepare(this);
            return;
        }//end

        private void  ToggleTrans (AWEvent event )
        {
            this.m_alphaCounter++;
            if (this.m_alphaCounter == 1)
            {
                this.m_child.alpha = 0.7;
            }
            else if (this.m_alphaCounter == 2)
            {
                this.m_child.alpha = 0.3;
            }
            else
            {
                this.m_child.alpha = 1;
                this.m_alphaCounter = 0;
            }
            return;
        }//end

        private void  setText ()
        {
            this.m_label.setText((this.m_parent ? ("Parent:") : ("child:")) + "x=" + this.m_child.x + "/y=" + this.m_child.y);
            if (!this.m_child.stage)
            {
                OffsetEditor.selected = null;
                OffsetEditor.instance.consider(this.m_gameObject);
            }
            ASwingHelper.prepare(this);
            return;
        }//end

        private void  up1 (AWEvent event )
        {
            (this.m_child.y - 1);
            System.setClipboard("offsetX=\"" + this.m_child.x + "\" offsetY=\"" + this.m_child.y + "\"");
            this.setText();
            return;
        }//end

        private void  down1 (AWEvent event )
        {
            (this.m_child.y + 1);
            System.setClipboard("offsetX=\"" + this.m_child.x + "\" offsetY=\"" + this.m_child.y + "\"");
            this.setText();
            return;
        }//end

        private void  left1 (AWEvent event )
        {
            (this.m_child.x - 1);
            System.setClipboard("offsetX=\"" + this.m_child.x + "\" offsetY=\"" + this.m_child.y + "\"");
            this.setText();
            return;
        }//end

        private void  right1 (AWEvent event )
        {
            (this.m_child.x + 1);
            System.setClipboard("offsetX=\"" + this.m_child.x + "\" offsetY=\"" + this.m_child.y + "\"");
            this.setText();
            return;
        }//end

        private void  up10 (AWEvent event )
        {
            this.m_child.y = this.m_child.y - 10;
            System.setClipboard("offsetX=\"" + this.m_child.x + "\" offsetY=\"" + this.m_child.y + "\"");
            this.setText();
            return;
        }//end

        private void  down10 (AWEvent event )
        {
            this.m_child.y = this.m_child.y + 10;
            System.setClipboard("offsetX=\"" + this.m_child.x + "\" offsetY=\"" + this.m_child.y + "\"");
            this.setText();
            return;
        }//end

        private void  left10 (AWEvent event )
        {
            this.m_child.x = this.m_child.x - 10;
            System.setClipboard("offsetX=\"" + this.m_child.x + "\" offsetY=\"" + this.m_child.y + "\"");
            this.setText();
            return;
        }//end

        private void  right10 (AWEvent event )
        {
            this.m_child.x = this.m_child.x + 10;
            System.setClipboard("offsetX=\"" + this.m_child.x + "\" offsetY=\"" + this.m_child.y + "\"");
            this.setText();
            return;
        }//end

    }



