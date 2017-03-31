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
import Classes.actions.*;
import Classes.announcers.announcerinteractions.*;
import Engine.Helpers.*;
import Engine.Managers.*;

import com.greensock.*;
//import flash.events.*;
//import flash.geom.*;
//import flash.utils.*;
import scripting.*;

    public class AnnouncerData extends EventDispatcher implements IScriptingContext
    {
        private String m_announcerId ;
        private Point m_tilePosition ;
        private Announcer m_npc ;
        private String m_layerName ;
        private String m_npcName ;
        private String m_npcTooltip ;
        private String m_guitarPickName ;
        private Vector<Script> m_validations;
        private boolean m_isSilent ;
        private Dictionary m_interactions ;
        private Vector<AnnouncerActionData> m_npcActionData;

        public  AnnouncerData (XML param1 )
        {
            String _loc_3 =null ;
            XML _loc_4 =null ;
            XMLList _loc_5 =null ;
            XML _loc_6 =null ;
            String _loc_7 =null ;
            Dictionary _loc_8 =null ;
            XML _loc_9 =null ;
            XMLList _loc_10 =null ;
            XML _loc_11 =null ;
            XMLList _loc_12 =null ;
            String _loc_13 =null ;
            XML _loc_14 =null ;
            String _loc_15 =null ;
            this.m_announcerId = param1.attribute("id").toString();
            this.m_validations = new Vector<Script>();
            this.m_isSilent = param1.attribute("silent").length() > 0 && param1.attribute("silent").toString() == "true";
            this.m_tilePosition = new Point();
            this.m_interactions = new Dictionary();
            Script _loc_2 =null ;
            if (param1.attribute("validate").length() > 0)
            {
                _loc_2 = new Script(this);
                _loc_3 = param1.attribute("validate").toString();
                _loc_2.addValidator(_loc_3);
            }
            else
            {
                _loc_2 = Global.gameSettings().parseScriptTag(param1.child("script").get(0), this);
            }
            this.attachScript(_loc_2);
            if (param1.hasOwnProperty("npc"))
            {
                _loc_4 = param1.child("npc").get(0);
                this.m_npcName = _loc_4.attribute("name").toString();
                if (_loc_4.attribute("tooltip").length() > 0)
                {
                    this.m_npcTooltip = ZLoc.t("Announcers", _loc_4.attribute("tooltip").toString());
                }
                if (_loc_4.attribute("x").length() > 0)
                {
                    this.m_tilePosition.x = parseInt(_loc_4.attribute("x").toString());
                }
                if (_loc_4.attribute("y").length() > 0)
                {
                    this.m_tilePosition.y = parseInt(_loc_4.attribute("y").toString());
                }
                this.m_layerName = _loc_4.attribute("layer").length() > 0 ? (param1.child("npc").attribute("layer").toString()) : ("npc");
                if (_loc_4.hasOwnProperty("guitarPick"))
                {
                    this.m_guitarPickName = _loc_4.child("guitarPick").attribute("name").toString();
                }
                if (_loc_4.hasOwnProperty("actions"))
                {
                    this.m_npcActionData = new Vector<AnnouncerActionData>();
                    _loc_5 = _loc_4.child("actions").child("action");
                    for(int i0 = 0; i0 < _loc_5.size(); i0++)
                    {
                    		_loc_6 = _loc_5.get(i0);

                        _loc_7 = _loc_6.attribute("name").toString();
                        if (_loc_7 && _loc_7 != "")
                        {
                            _loc_8 = new Dictionary();
                            for(int i0 = 0; i0 < _loc_6.attributes().size(); i0++)
                            {
                            		_loc_9 = _loc_6.attributes().get(i0);

                                _loc_8.put(_loc_9.name().toString(),  _loc_9.toString());
                            }
                            this.m_npcActionData.push(new AnnouncerActionData(_loc_7, _loc_8));
                        }
                    }
                }
            }
            if (!this.m_isSilent && param1.hasOwnProperty("interactions"))
            {
                _loc_10 = param1.child("interactions").child("interaction");
                for(int i0 = 0; i0 < _loc_10.size(); i0++)
                {
                		_loc_11 = _loc_10.get(i0);

                    _loc_12 = _loc_11.children();
                    _loc_13 = _loc_11.attribute("type").toString();
                    if (_loc_13)
                    {
                        for(int i0 = 0; i0 < _loc_12.size(); i0++)
                        {
                        		_loc_14 = _loc_12.get(i0);

                            _loc_15 = _loc_14.name().localName;
                            if (this.m_interactions.get(_loc_13) == null)
                            {
                                this.m_interactions.put(_loc_13,  new Array());
                            }
                            switch(_loc_15)
                            {
                                case "dialog":
                                {
                                    this.m_interactions.get(_loc_13).push(new AnnouncerInteractionDialog(this, _loc_14));
                                    break;
                                }
                                case "speech":
                                {
                                    this.m_interactions.get(_loc_13).push(new AnnouncerInteractionSpeechBubble(this, _loc_14));
                                    break;
                                }
                                case "customHandler":
                                {
                                    this.m_interactions.get(_loc_13).push(new AnnouncerInteractionCustomHandler(this, _loc_14));
                                    break;
                                }
                                case "expansionOutline":
                                {
                                    this.m_interactions.get(_loc_13).push(new AnnouncerExpansionOutline(this, _loc_14));
                                    break;
                                }
                                default:
                                {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            return;
        }//end

        public String  announcerId ()
        {
            return this.m_announcerId;
        }//end

        public Announcer  npc ()
        {
            return this.m_npc;
        }//end

        public void  showAnnouncerNPC (boolean param1 =false )
        {
            Road _loc_3 =null ;
            AnnouncerActionData _loc_4 =null ;
            Vector3 _loc_2 =new Vector3(this.m_tilePosition.x ,this.m_tilePosition.y );
            if (this.m_layerName == "npc" || this.m_layerName == "default")
            {
                _loc_3 = Global.world.citySim.roadManager.findClosestRoad(_loc_2);
                if (_loc_3)
                {
                    _loc_2 = _loc_3.getPosition();
                }
            }
            this.m_npc =(Announcer) Global.world.citySim.npcManager.createWalkerByClass(Announcer, this.m_npcName, _loc_2, false);
            this.m_npc.customlayerName = this.m_layerName;
            this.m_npc.tooltip = this.m_npcTooltip;
            if (this.m_npcActionData && this.m_npcActionData.length > 0)
            {
                for(int i0 = 0; i0 < this.m_npcActionData.size(); i0++)
                {
                		_loc_4 = this.m_npcActionData.get(i0);

                    this.m_npc.getStateMachine().addActions(_loc_4.getAction(this.m_npc));
                }
            }
            else
            {
                this.m_npc.getStateMachine().addActions(new NPCAction(this.m_npc));
            }
            this.m_npc.attach();
            if (this.m_guitarPickName)
            {
                this.m_npc.forceUpdateArrowWithCustomIcon(this.m_guitarPickName);
            }
            if (!this.m_isSilent)
            {
                this.m_npc.playActionCallback = this.onNPCClick;
            }
            if (param1 !=null)
            {
                this.m_npc.alpha = 0;
                TweenLite.to(this.m_npc, 1, {alpha:1});
            }
            return;
        }//end

        public void  hideAnnouncerNPC (boolean param1 =false )
        {
            if (param1 !=null)
            {
                if (this.m_npc)
                {
                    TweenLite.to(this.m_npc, 1, {alpha:0, onComplete:this.cleanUp});
                }
            }
            else
            {
                this.cleanUp();
            }
            return;
        }//end

        protected void  cleanUp ()
        {
            if (this.m_npc)
            {
                this.m_npc.clearStates();
                this.m_npc.detach();
                this.m_npc.playActionCallback = null;
                this.m_npc = null;
            }
            return;
        }//end

        public void  activateInteraction (String param1 )
        {
            IAnnouncerInteraction _loc_2 =null ;
            if (this.m_interactions.get(param1))
            {
                for(int i0 = 0; i0 < this.m_interactions.get(param1).size(); i0++)
                {
                		_loc_2 = this.m_interactions.get(param1).get(i0);

                    _loc_2.activate();
                }
                StatsManager.count("announcer", this.m_announcerId, param1, this.m_npcName);
            }
            return;
        }//end

        private void  onNPCClick ()
        {
            this.activateInteraction(AnnouncerInteractionTypes.CLICK);
            return;
        }//end

        public void  attachScript (Script param1 )
        {
            this.m_validations.push(param1);
            return;
        }//end

        public boolean  validate ()
        {
            Script _loc_1 =null ;
            for(int i0 = 0; i0 < this.m_validations.size(); i0++)
            {
            		_loc_1 = this.m_validations.get(i0);

                if (!_loc_1.validates())
                {
                    return false;
                }
            }
            return true;
        }//end

        public void  onValidate ()
        {
            return;
        }//end

    }



