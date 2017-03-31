package de.polygonal.ds;

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
    public class PriorityQueue implements Collection
    {
        private Array _heap ;
        private int _size ;
        private int _count ;
        private Dictionary _posLookup ;

        public  PriorityQueue (int param1 )
        {
            this._size = param1 + 1;
            this._heap = new Array(++param1);
            this._posLookup = new Dictionary(true);
            this._count = 0;
            return;
        }//end

        public Prioritizable  front ()
        {
            return this._heap.get(1);
        }//end

        public int  maxSize ()
        {
            return this._size;
        }//end

        public boolean  enqueue (Prioritizable param1 )
        {
            if ((this._count + 1) < this._size)
            {

                this._count++;
                this._heap.put(this._count,  param1);
                this._posLookup.put(param1,  this._count);
                this.walkUp(this._count);
                return true;
            }
            return false;
        }//end

        public Prioritizable  dequeue ()
        {
            _loc_1 = null;
            if (this._count >= 1)
            {
                _loc_1 = this._heap.get(1);
                delete this._posLookup.get(_loc_1);
                this._heap.put(1,  this._heap.get(this._count));
                this.walkDown(1);
                delete this._heap.get(this._count);

                this._count--;

                return _loc_1;
            }
            return null;
        }//end

        public boolean  reprioritize (Prioritizable param1 ,int param2 )
        {
            if (!this._posLookup.get(param1))
            {
                return false;
            }
            _loc_3 = param1.priority ;
            param1.priority = param2;
            _loc_4 = this._posLookup.get(param1) ;
            if (param2 > _loc_3)
            {
                this.walkUp(_loc_4);
            }
            else
            {
                this.walkDown(_loc_4);
            }
            return true;
        }//end

        public boolean  remove (Prioritizable param1 )
        {
            int _loc_2 =0;
            _loc_3 = null;
            if (this._count >= 1)
            {
                _loc_2 = this._posLookup.get(param1);
                _loc_3 = this._heap.get(_loc_2);
                delete this._posLookup.get(_loc_3);
                this._heap.put(_loc_2,  this._heap.get(this._count));
                this.walkDown(_loc_2);
                delete this._heap.get(this._count);
                delete this._posLookup.get(this._count);

                this._count--;

                return true;
            }
            return false;
        }//end

        public boolean  contains (Object param1)
        {
            int _loc_2 =1;
            while (_loc_2 <= this._count)
            {

                if (this._heap.get(_loc_2) === param1)
                {
                    return true;
                }
                _loc_2++;
            }
            return false;
        }//end

        public void  clear ()
        {
            this._heap = new Array(this._size);
            this._posLookup = new Dictionary(true);
            this._count = 0;
            return;
        }//end

        public Iterator  getIterator ()
        {
            return new PriorityQueueIterator(this);
        }//end

        public int  size ()
        {
            return this._count;
        }//end

        public boolean  isEmpty ()
        {
            return this._count == 0;
        }//end

        public Array  toArray ()
        {
            return this._heap.slice(1, (this._count + 1));
        }//end

        public String  toString ()
        {
            return "[PriorityQueue, size=" + this._size + "]";
        }//end

        public String  dump ()
        {
            if (this._count == 0)
            {
                return "PriorityQueue (empty)";
            }
            String _loc_1 ="PriorityQueue\n{\n";
            _loc_2 = this._count +1;
            int _loc_3 =1;
            while (_loc_3 < _loc_2)
            {

                _loc_1 = _loc_1 + ("\t" + this._heap.get(_loc_3) + "\n");
                _loc_3++;
            }
            _loc_1 = _loc_1 + "\n}";
            return _loc_1;
        }//end

        private void  walkUp (int param1 )
        {
            Prioritizable _loc_3 =null ;
            _loc_2 = param1>>1;
            _loc_4 = this._heap.get(param1) ;
            _loc_5 = this._heap.get(param1).priority ;
            while (_loc_2 > 0)
            {

                _loc_3 = this._heap.get(_loc_2);
                if (_loc_5 - _loc_3.priority > 0)
                {
                    this._heap.put(param1,  _loc_3);
                    this._posLookup.put(_loc_3,  param1);
                    param1 = _loc_2;
                    _loc_2 = _loc_2 >> 1;
                    continue;
                }
                break;
            }
            this._heap.put(param1,  _loc_4);
            this._posLookup.put(_loc_4,  param1);
            return;
        }//end

        private void  walkDown (int param1 )
        {
            Prioritizable _loc_3 =null ;
            _loc_2 = param1<<1;
            _loc_4 = this._heap.get(param1) ;
            _loc_5 = this._heap.get(param1).priority ;
            while (_loc_2 < this._count)
            {

                if (_loc_2 < (this._count - 1))
                {
                    if (this._heap.get(_loc_2).priority - this._heap.get(int((_loc_2 + 1))).priority < 0)
                    {
                        _loc_2++;
                    }
                }
                _loc_3 = this._heap.get(_loc_2);
                if (_loc_5 - _loc_3.priority < 0)
                {
                    this._heap.put(param1,  _loc_3);
                    this._posLookup.put(_loc_3,  param1);
                    this._posLookup.put(_loc_4,  _loc_2);
                    param1 = _loc_2;
                    _loc_2 = _loc_2 << 1;
                    continue;
                }
                break;
            }
            this._heap.put(param1,  _loc_4);
            this._posLookup.put(_loc_4,  param1);
            return;
        }//end

    }

import de.polygonal.ds.*;

class PriorityQueueIterator implements Iterator
    private Array _values ;
    private int _length ;
    private int _cursor ;

     PriorityQueueIterator (PriorityQueue param1 )
    {
        this._values = param1.toArray();
        this._length = this._values.length;
        this._cursor = 0;
        return;
    }//end

    public Object data ()
    {
        return this._values.get(this._cursor);
    }//end

    public void  data (Object param1)
    {
        this._values.put(this._cursor,  param1);
        return;
    }//end

    public void  start ()
    {
        this._cursor = 0;
        return;
    }//end

    public boolean  hasNext ()
    {
        return this._cursor < this._length;
    }//end

    public Object next ()
    {


        return this._values.get(this._cursor++);
    }//end



