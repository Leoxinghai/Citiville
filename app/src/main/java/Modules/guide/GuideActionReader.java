package Modules.guide;

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

import Engine.Managers.*;
import Modules.guide.actions.*;

    public class GuideActionReader
    {
        protected Guide m_guide ;
public static  Object ACTIONS ={TutorialStart GATutorialStart ,TutorialEnd ,DisplayGuideTile ,DisplayDialog ,DisplayArrow ,DisplayMask ,DisplaySpotlight ,GameModePlayFiltered ,Notify ,Function ,AllowQuests ,ShowNews ,WaitForTransaction ,WaitForPredicate ,WaitForClickAnywhere ,WaitForZoom ,WaitForWilderness ,RemoveElements ,Pause ,WaitForTransactionOrGameMode ,WaitForTransactionOrNotGameMode ,WaitForTransactionOrClickGround ,PanMap ,PanAndFollow ,WaitForPredicateOrClickAnywhere ,RefreshResources ,DisplayMarketplaceItem };

        public  GuideActionReader (Guide param1 )
        {
            this.m_guide = param1;
            return;
        }//end

        public void  readActions ()
        {
            XML _loc_3 =null ;
            GuideSequence _loc_4 =null ;
            boolean _loc_5 =false ;
            XMLList _loc_6 =null ;
            XML _loc_7 =null ;
            String _loc_8 =null ;
            Class _loc_9 =null ;
            GuideAction _loc_10 =null ;
            _loc_1 =Global.gameSettings().getXML ();
            if (!_loc_1)
            {
                ErrorManager.addError("Missing game settings!");
                return;
            }
            _loc_2 = _loc_1.guides.guide ;
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                _loc_4 = new GuideSequence(this.m_guide);
                _loc_5 = _loc_4.createFromXml(_loc_3);
                if (_loc_5)
                {
                    this.m_guide.registerSequence(_loc_4);
                    _loc_6 = _loc_3.action;
                    for(int i0 = 0; i0 < _loc_6.size(); i0++)
                    {
                    		_loc_7 = _loc_6.get(i0);

                        _loc_8 = String(_loc_7.@name);
                        _loc_9 = ACTIONS.get(_loc_8);
                        if (_loc_9 != null)
                        {
                            _loc_10 =(GuideAction) new _loc_9;
                            _loc_10.setGuide(this.m_guide, _loc_4);
                            if (_loc_10.createFromXml(_loc_7))
                            {
                                _loc_4.addAction(_loc_10);
                            }
                            else
                            {
                                ErrorManager.addError("Failed to parse tutorial step for " + _loc_8);
                            }
                            continue;
                        }
                        ErrorManager.addError("Unknown tutorial action: " + _loc_8);
                    }
                }
            }
            return;
        }//end

    }



