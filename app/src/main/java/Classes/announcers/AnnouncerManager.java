package Classes.announcers;

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
import Classes.announcers.announcerinteractions.*;
import Engine.Helpers.*;

import Classes.sim.*;

    public class AnnouncerManager implements IGameWorldStateObserver
    {
        private GameWorld m_world ;
        private Vector<AnnouncerData> m_announcerDataCollection;

        public  AnnouncerManager (GameWorld param1 )
        {
            XML _loc_3 =null ;
            this.m_world = param1;
            param1.addObserver(this);
            this.m_announcerDataCollection = new Vector<AnnouncerData>();
            _loc_2 =Global.gameSettings().getAnnouncers ();
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                this.m_announcerDataCollection.push(new AnnouncerData(_loc_3));
            }
            return;
        }//end

        public AnnouncerData  findAnnouncerDataByName (String param1 )
        {
            AnnouncerData _loc_3 =null ;
            AnnouncerData _loc_2 =null ;
            for(int i0 = 0; i0 < this.m_announcerDataCollection.size(); i0++)
            {
            		_loc_3 = this.m_announcerDataCollection.get(i0);

                if (_loc_3.announcerId == param1)
                {
                    _loc_2 = _loc_3;
                    break;
                }
            }
            return _loc_2;
        }//end

        public void  scrollToAnnouncer (String param1 )
        {
            AnnouncerData _loc_2 =null ;
            for(int i0 = 0; i0 < this.m_announcerDataCollection.size(); i0++)
            {
            		_loc_2 = this.m_announcerDataCollection.get(i0);

                if (_loc_2.announcerId == param1)
                {
                    this.m_world.centerOnObject(_loc_2.npc);
                    _loc_2.activateInteraction(AnnouncerInteractionTypes.SCROLL_TO);
                    break;
                }
            }
            return;
        }//end

        public void  initialize ()
        {
            return;
        }//end

        public void  cleanUp ()
        {
            return;
        }//end

        public void  onGameLoaded (Object param1 )
        {
            AnnouncerData _loc_2 =null ;
            for(int i0 = 0; i0 < this.m_announcerDataCollection.size(); i0++)
            {
            		_loc_2 = this.m_announcerDataCollection.get(i0);

                _loc_2.hideAnnouncerNPC();
                if (!Global.isVisiting() && _loc_2.validate())
                {
                    _loc_2.showAnnouncerNPC();
                }
            }
            return;
        }//end

        public void  cycleAllAnnouncers ()
        {
            AnnouncerData _loc_1 =null ;
            for(int i0 = 0; i0 < this.m_announcerDataCollection.size(); i0++)
            {
            		_loc_1 = this.m_announcerDataCollection.get(i0);

                if (!Global.isVisiting() && _loc_1.npc && !_loc_1.validate())
                {
                    _loc_1.hideAnnouncerNPC(true);
                    continue;
                }
                if (!Global.isVisiting() && !_loc_1.npc && _loc_1.validate())
                {
                    _loc_1.showAnnouncerNPC(true);
                }
            }
            return;
        }//end

        public void  onResourceChange (MapResource param1 ,Vector3 param2 ,Vector3 param3 )
        {
            return;
        }//end

    }



