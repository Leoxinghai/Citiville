package Engine.Managers;

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

import Engine.Classes.*;
//import flash.events.*;
//import flash.media.*;
//import flash.net.*;
//import flash.utils.*;

import com.xinghai.Debug;

    public class SoundManager
    {
       public static Dictionary m_sounds =new Dictionary ();
       public static Array m_activeChannels =new Array();
       public static boolean m_isMuted =false ;

        public  SoundManager ()
        {
            return;
        }//end

        public static Sound  addSound (String param1 ,String param2 ,boolean param3 =true ,Function param4 =null )
        {
            Sound sound ;
            Function onLoadCompleteWrapper ;
            id = param1;
            url = param2;
            playOnLoad = param3;
            onLoadComplete = param4;
            sound = m_sounds.get(id);
            if (sound == null)
            {
                sound = new Sound();
                sound.addEventListener(Event.COMPLETE, onSoundComplete);
                if (playOnLoad)
                {
                    sound.addEventListener(Event.COMPLETE, onPlayOnComplete);
                }
                if (onLoadComplete != null)
                {
                    onLoadCompleteWrapper =void  (Event event )
            {
                if(param1 == "bgMusic") {
                	Debug.debug7("SoundManager.loaded");
                }
                onLoadComplete(event);

                sound.removeEventListener(Event.COMPLETE, onLoadCompleteWrapper);
                return;
            }//end
            ;
                    sound.addEventListener(Event.COMPLETE, onLoadCompleteWrapper);
                }
                //sound.addEventListener(IOErrorEvent.IO_ERROR, onIOError);
                sound.load(new URLRequest(AssetUrlManager.instance.lookUpUrl(url)));
                m_sounds.put(id,  sound);
                GlobalEngine.log("Sound", "Add sound, id: " + id + ", url: " + url);
            }
            return sound;
        }//end

        public static SoundObject  playSound (String param1 ,double param2 =0,int param3 =0,SoundTransform param4 =null ,boolean param5 =false ,boolean param6 =false )
        {

            Sound sound ;
            id = param1;
            startTime = param2;
            loops = param3;
            transform = param4;
            isMusic = param5;
            repeatLoop = param6;
            SoundObject result ;
            Object error;

            Debug.debug7("SoundManager.playSound."+param1);
            if(param1 == "bgMusic") {
            	isMusic = true;
            }

            try
            {
                sound = getSoundById(id);
                GlobalEngine.log("Sound", "Play Sound: " + id + " (muted: " + isMuted() + ")");

                Debug.debug7("SoundManager.playSound." + sound.bytesTotal + ";" + sound.bytesLoaded);

                if (sound && sound.bytesTotal > 0 && sound.bytesLoaded == sound.bytesTotal)
                {
                    result = conditionallyPlaySound(sound, startTime, loops, transform, isMusic, repeatLoop);
                }
            }
            catch (e:Error)
            {
                error = e;
				Debug.debug7("SoundManager.playSound." + e);

                GlobalEngine.log("Sound", "Error: Can\'t play sound (" + id + ")");
            }
            return result;
        }//end

        public static void  stopSound (String param1 )
        {
            int _loc_3 =0;
            SoundObject _loc_4 =null ;
            _loc_2 = getSoundById(param1);
            if (_loc_2 != null)
            {
                _loc_3 = 0;
                while (_loc_3 < m_activeChannels.length())
                {

                    _loc_4 =(SoundObject) m_activeChannels.get(_loc_3);
                    if (_loc_4.sound.url == _loc_2.url)
                    {
                        _loc_4.stop();
                    }
                    _loc_3++;
                }
            }
            return;
        }//end

        public static void  stopAllMusic ()
        {
            SoundObject _loc_2 =null ;
            int _loc_1 =0;
            while (_loc_1 < m_activeChannels.length())
            {

                _loc_2 =(SoundObject) m_activeChannels.get(_loc_1);
                if (_loc_2 && _loc_2.isMusic == true)
                {
                    _loc_2.stop();
                }
                _loc_1++;
            }
            return;
        }//end

        public static void  stopAllSounds ()
        {
            SoundObject _loc_2 =null ;
            int _loc_1 =0;
            while (_loc_1 < m_activeChannels.length())
            {

                _loc_2 =(SoundObject) m_activeChannels.get(_loc_1);
                if (_loc_2 && _loc_2.isMusic == false)
                {
                    _loc_2.stop();
                }
                _loc_1++;
            }
            m_activeChannels = new Array();
            return;
        }//end

        private static SoundObject  conditionallyPlaySound (Sound param1 ,double param2 =0,int param3 =0,SoundTransform param4 =null ,boolean param5 =false ,boolean param6 =false )
        {
            SoundChannel _loc_8 =null ;
            SoundObject _loc_7 =null ;
            if (param5)
            {
                stopAllMusic();
            }
            if (param5 == true || isMuted() == false)
            {
                Debug.debug7("SoundManager.conditionallyPlaySound length " + param1.length());
                if (param6)
                {
                    _loc_8 = param1.play(param2 % param1.length, 0, param4);
                }
                else
                {
                    _loc_8 = param1.play(param2 % param1.length, param3, param4);

                }
                if (_loc_8)
                {
                    _loc_7 = new SoundObject(param1, _loc_8, param3, param4);
                    _loc_7.isMusic = param5;
                    m_activeChannels.push(_loc_7);
                    param1.addEventListener(Event.SOUND_COMPLETE, onSoundComplete);
                }
            }
            return _loc_7;
        }//end

        private static void  onSoundComplete (Event event )
        {
            Debug.debug7("SoundManager.onSoundComplete "+m_activeChannels.length());

            SoundObject _loc_4 =null ;
            _loc_2 =(Sound) event.target;
            int _loc_3 =0;
            while (_loc_3 < m_activeChannels.length())
            {

                _loc_4 =(SoundObject) m_activeChannels.get(_loc_3);

                Debug.debug7("SoundManager.onSoundComplete "+_loc_4.sound.url +";"+ _loc_2.url);

                if (_loc_4.sound.url == _loc_2.url)
                {

                   Debug.debug7("SoundManager.onSoundComplete "+_loc_4.canReloop());

                    if (_loc_4.canReloop())
                    {
                        _loc_4.stop(false);
                        _loc_2.removeEventListener(Event.SOUND_COMPLETE, onSoundComplete);
                        conditionallyPlaySound(_loc_2, 0, _loc_4.loops, new SoundTransform(_loc_4.volume));
                    }
                    m_activeChannels.splice(m_activeChannels.indexOf(_loc_4), 1);
                    break;
                }
                _loc_3++;
            }
            return;
        }//end

        private static void  onPlayOnComplete (Event event )
        {
            _loc_2 =(Sound) event.target;
            _loc_2.removeEventListener(Event.COMPLETE, onPlayOnComplete);
            if (_loc_2)
            {
                conditionallyPlaySound(_loc_2);
            }
            return;
        }//end

        public static Sound  getSoundById (String param1 )
        {
            return m_sounds.get(param1) as Sound;
        }//end

        public static SoundObject  getSoundObjectById (String param1 )
        {
            int _loc_4 =0;
            SoundObject _loc_5 =null ;
            _loc_2 = getSoundById(param1);
            SoundObject _loc_3 =null ;
            if (_loc_2 != null)
            {
                _loc_4 = 0;
                while (_loc_4 < m_activeChannels.length())
                {

                    _loc_5 =(SoundObject) m_activeChannels.get(_loc_4);
                    if (_loc_5.sound.url == _loc_2.url)
                    {
                        _loc_3 = _loc_5;
                        break;
                    }
                    _loc_4++;
                }
            }
            return _loc_3;
        }//end

        public static double  getPlayTimeElapsed (String param1 )
        {
            int _loc_4 =0;
            SoundObject _loc_5 =null ;
            _loc_2 = getSoundById(param1);
            double _loc_3 =0;
            if (_loc_2 != null)
            {
                _loc_4 = 0;
                while (_loc_4 < m_activeChannels.length())
                {

                    _loc_5 =(SoundObject) m_activeChannels.get(_loc_4);
                    if (_loc_5.sound.url == _loc_2.url)
                    {
                        _loc_3 = _loc_5.position;
                        break;
                    }
                    _loc_4++;
                }
            }
            return _loc_3;
        }//end

        public static void  mute ()
        {
            SoundObject _loc_2 =null ;
            m_isMuted = true;
            int _loc_1 =0;

            while (_loc_1 < m_activeChannels.length())
            {

                _loc_2 =(SoundObject) m_activeChannels.get(_loc_1);
                Debug.debug7("SoundManager.mute "+ _loc_2+";"+_loc_2.isMusic);
                if (_loc_2 && _loc_2.isMusic == false)
                {
                    _loc_2.volume = 0;
                }
                _loc_1++;
            }
            return;
        }//end

        public static void  unmute ()
        {
            SoundObject _loc_2 =null ;
            m_isMuted = false;
            int _loc_1 =0;
            while (_loc_1 < m_activeChannels.length())
            {

                _loc_2 =(SoundObject) m_activeChannels.get(_loc_1);
                Debug.debug7("SoundManager.unmute "+ _loc_2+";"+_loc_2.isMusic);
                if (_loc_2 && _loc_2.isMusic == false)
                {
                    _loc_2.volume = 1;
                }
                _loc_1++;
            }
            return;
        }//end

        public static void  toggleMute ()
        {
            if (isMuted())
            {
                unmute();
            }
            else
            {
                mute();
            }
            return;
        }//end

        public static boolean  isMuted ()
        {
            return m_isMuted;
        }//end

        public static void  chooseAndPlaySound (Array param1 )
        {
            double _loc_3 =0;
            String _loc_2 =null ;
            if (param1.length == 1)
            {
                _loc_2 =(String) param1.get(0);
            }
            else if (param1.length > 0)
            {
                _loc_3 = Math.floor(Math.random() * param1.length());
                _loc_2 =(String) param1.get(_loc_3);
            }
            if (_loc_2 != null)
            {
                playSound(_loc_2);
            }
            return;
        }//end

    }



