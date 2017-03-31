package com.facebook.data;

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

import com.adobe.serialization.json.*;
import com.facebook.commands.data.*;
import com.facebook.data.admin.*;
import com.facebook.data.application.*;
import com.facebook.data.auth.*;
import com.facebook.data.batch.*;
import com.facebook.data.data.*;
import com.facebook.data.events.*;
import com.facebook.data.fbml.*;
import com.facebook.data.feed.*;
import com.facebook.data.friends.*;
import com.facebook.data.groups.*;
import com.facebook.data.notes.*;
import com.facebook.data.notifications.*;
import com.facebook.data.pages.*;
import com.facebook.data.photos.*;
import com.facebook.data.status.*;
import com.facebook.data.users.*;
import com.facebook.errors.*;
import com.facebook.utils.*;
//import flash.events.*;

    public class XMLDataParser implements IFacebookResultParser
    {
        protected Namespace fb_namespace ;

        public  XMLDataParser ()
        {
            this.fb_namespace = new Namespace("http://api.facebook.com/1.0/");
            return;
        }//end

        protected ArrayResultData  parseSendEmail (XML param1 )
        {
            ArrayResultData _loc_2 =new ArrayResultData ();
            _loc_2.arrayResult = FacebookXMLParserUtils.toArray(param1);
            return _loc_2;
        }//end

        protected GetPhotosData  parseGetPhotos (XML param1 )
        {
            _loc_4 = null;
            PhotoData _loc_5 =null ;
            GetPhotosData _loc_2 =new GetPhotosData ();
            PhotoCollection _loc_3 =new PhotoCollection ();
            for(int i0 = 0; i0 < param1..photo.size(); i0++)
            {
            		_loc_4 = param1..photo.get(i0);

                _loc_5 = new PhotoData();
                _loc_5.pid = this.fb_namespace::pid;
                _loc_5.aid = this.fb_namespace::aid;
                _loc_5.owner = this.fb_namespace::owner;
                _loc_5.src = this.fb_namespace::src;
                _loc_5.src_big = this.fb_namespace::src_big;
                _loc_5.src_small = this.fb_namespace::src_small;
                _loc_5.caption = this.fb_namespace::caption;
                _loc_5.created = FacebookXMLParserUtils.toDate(this.fb_namespace::created);
                _loc_3.addPhoto(_loc_5);
            }
            _loc_2.photoCollection = _loc_3;
            return _loc_2;
        }//end

        protected GetNotificationData  parseGetNotifications (XML param1 )
        {
            _loc_4 = null;
            _loc_5 = null;
            _loc_6 = null;
            NotificationMessageData _loc_7 =null ;
            NotificationPokeData _loc_8 =null ;
            NotificationShareData _loc_9 =null ;
            GetNotificationData _loc_2 =new GetNotificationData ();
            NotificationCollection _loc_3 =new NotificationCollection ();
            for(int i0 = 0; i0 < this.fb_namespace::messages.size(); i0++)
            {
            		_loc_4 = this.fb_namespace::messages.get(i0);

                _loc_7 = new NotificationMessageData();
                _loc_7.unread = this.fb_namespace::unread;
                _loc_7.most_recent = this.fb_namespace::most_recent;
                _loc_3.addItem(_loc_7);
            }
            for(int i0 = 0; i0 < this.fb_namespace::pokes.size(); i0++)
            {
            		_loc_5 = this.fb_namespace::pokes.get(i0);

                _loc_8 = new NotificationPokeData();
                _loc_8.unread = this.fb_namespace::unread;
                _loc_8.most_recent = this.fb_namespace::most_recent;
                _loc_3.addItem(_loc_8);
            }
            for(int i0 = 0; i0 < this.fb_namespace::shares.size(); i0++)
            {
            		_loc_6 = this.fb_namespace::shares.get(i0);

                _loc_9 = new NotificationShareData();
                _loc_9.unread = this.fb_namespace::unread;
                _loc_9.most_recent = this.fb_namespace::most_recent;
                _loc_3.addItem(_loc_9);
            }
            _loc_2.friendsRequests = FacebookXMLParserUtils.toUIDArray(this.fb_namespace::friend_requests.get(0));
            _loc_2.group_invites = FacebookXMLParserUtils.toUIDArray(this.fb_namespace::group_invites.get(0));
            _loc_2.event_invites = FacebookXMLParserUtils.toUIDArray(this.fb_namespace::event_invites.get(0));
            _loc_2.notificationCollection = _loc_3;
            return _loc_2;
        }//end

        public FacebookError  createFacebookError (Object param1 ,String param2 )
        {
            FacebookError _loc_3 =new FacebookError ();
            _loc_3.rawResult = param2;
            _loc_3.errorCode = FacebookErrorCodes.SERVER_ERROR;
            if (param1 instanceof Error)
            {
                _loc_3.error =(Error) param1;
            }
            else
            {
                _loc_3.errorEvent =(ErrorEvent) param1;
            }
            return _loc_3;
        }//end

        protected AffiliationCollection  getAffiliation (XML param1 )
        {
            _loc_3 = null;
            AffiliationData _loc_4 =null ;
            AffiliationCollection _loc_2 =new AffiliationCollection ();
            for(int i0 = 0; i0 < param1..afflication.size(); i0++)
            {
            		_loc_3 = param1..afflication.get(i0);

                _loc_4 = new AffiliationData();
                _loc_4.nid = this.fb_namespace::nid;
                _loc_4.name = this.fb_namespace::name;
                _loc_4.type = this.fb_namespace::type;
                _loc_4.status = this.fb_namespace::status;
                _loc_4.year = this.fb_namespace::year;
                _loc_2.addAffiliation(_loc_4);
            }
            return _loc_2;
        }//end

        protected GetUserPreferencesData  parseGetUserPreferences (XML param1 )
        {
            _loc_4 = null;
            PreferenceData _loc_5 =null ;
            GetUserPreferencesData _loc_2 =new GetUserPreferencesData ();
            PreferenceCollection _loc_3 =new PreferenceCollection ();
            for(int i0 = 0; i0 < param1..preference.size(); i0++)
            {
            		_loc_4 = param1..preference.get(i0);

                _loc_5 = new PreferenceData();
                _loc_5.pref_id = this.fb_namespace::pref_id;
                _loc_5.value = this.fb_namespace::value;
                _loc_3.addItem(_loc_5);
            }
            _loc_2.perferenceCollection = _loc_3;
            return _loc_2;
        }//end

        public FacebookData  parse (String param1 ,String param2 )
        {
            FacebookData _loc_3 =null ;
            XML _loc_4 =new XML(param1 );
            switch(param2)
            {
                case "application.getPublicInfo":
                {
                    _loc_3 = this.parseGetPublicInfo(_loc_4);
                    break;
                }
                case "data.getCookies":
                {
                    _loc_3 = this.parseGetCookies(_loc_4);
                    break;
                }
                case "admin.getAllocation":
                {
                    _loc_3 = this.parseGetAllocation(_loc_4);
                    break;
                }
                case "admin.getAppProperties":
                {
                    _loc_3 = this.parseGetAppProperties(_loc_4);
                    break;
                }
                case "admin.getMetrics":
                {
                    _loc_3 = this.parseGetMetrics(_loc_4);
                    break;
                }
                case "auth.getSession":
                {
                    _loc_3 = new GetSessionData();
                    ((GetSessionData)_loc_3).expires = FacebookXMLParserUtils.toDate(this.fb_namespace::expires);
                    ((GetSessionData)_loc_3).uid = FacebookXMLParserUtils.toStringValue(this.fb_namespace::uid.get(0));
                    ((GetSessionData)_loc_3).session_key = this.fb_namespace::session_key.toString();
                    ((GetSessionData)_loc_3).secret = String(this.fb_namespace::secret);
                    break;
                }
                case "feed.getRegisteredTemplateBundles":
                {
                    _loc_3 = this.parseGetRegisteredTemplateBundles(_loc_4);
                    break;
                }
                case "friends.areFriends":
                {
                    _loc_3 = this.parseAreFriends(_loc_4);
                    break;
                }
                case "notes.get":
                {
                    _loc_3 = this.parseGetNotes(_loc_4);
                    break;
                }
                case "friends.get":
                {
                    _loc_3 = this.parseGetFriends(_loc_4);
                    break;
                }
                case "friends.getAppUsers":
                {
                    _loc_3 = this.parseGetAppUsersData(_loc_4);
                    break;
                }
                case "friends.getLists":
                {
                    _loc_3 = this.parseGetLists(_loc_4);
                    break;
                }
                case "groups.get":
                {
                    _loc_3 = this.parseGetGroups(_loc_4);
                    break;
                }
                case "data.getAssociationDefinitions":
                {
                    _loc_3 = new FacebookData();
                    break;
                }
                case "data.getAssociationDefinition":
                {
                    _loc_3 = new FacebookData();
                    break;
                }
                case "data.getObject":
                case "data.getObjects":
                {
                    _loc_3 = new FacebookData();
                    break;
                }
                case "groups.getMembers":
                {
                    _loc_3 = this.parseGetGroupMembers(_loc_4);
                    break;
                }
                case "users.getInfo":
                {
                    _loc_3 = this.parseGetInfo(_loc_4);
                    break;
                }
                case "data.createObject":
                case "data.setHashValue":
                case "connect.getUnconnectedFriendsCount":
                case "feed.registerTemplateBundle":
                {
                    _loc_3 = new NumberResultData();
                    ((NumberResultData)_loc_3).value = FacebookXMLParserUtils.toNumber(_loc_4);
                    break;
                }
                case "notifications.get":
                {
                    _loc_3 = this.parseGetNotifications(_loc_4);
                    break;
                }
                case "feed.getRegisteredTemplateBundleByID":
                {
                    _loc_3 = this.parseGetRegisteredTemplateBundleByID(_loc_4);
                    break;
                }
                case "users.getStandardInfo":
                {
                    _loc_3 = this.parseGetStandardInfo(_loc_4);
                    break;
                }
                case "feed.getRegisteredTemplateBundles":
                {
                    _loc_3 = this.parseGetRegisteredTemplateBundles(_loc_4);
                    break;
                }
                case "data.getUserPreferences":
                {
                    _loc_3 = this.parseGetUserPreferences(_loc_4);
                    break;
                }
                case "users.isAppUser":
                case "users.hasAppPermission":
                case "users.setStatus":
                case "pages.isFan":
                case "pages.isAppAdded":
                case "pages.isAdmin":
                case "admin.setAppProperties":
                case "auth.expireSession":
                case "auth.revokeAuthorization":
                case "events.cancel":
                case "events.edit":
                case "events.rsvp":
                case "liveMessage.send":
                case "data.undefineAssociation":
                case "data.defineAssociation":
                case "data.removeHashKeys":
                case "data.removeHashKey":
                case "data.incHashValue":
                case "data.updateObject":
                case "data.deleteObject":
                case "data.deleteObjects":
                case "data.renameAssociation":
                case "data.setObjectProperty":
                case "profile.setInfo":
                case "profile.setInfoOptions":
                case "feed.deactivateTemplateBundleByID":
                case "feed.publishTemplatizedAction":
                case "admin.setRestrictionInfo":
                case "data.setCookie":
                case "data.createObjectType":
                case "notes.delete":
                case "notes.edit":
                case "data.setUserPreference":
                case "data.dropObjectType":
                case "data.renameObjectType":
                case "fbml.registerCustomTags":
                case "fbml.deleteCustomTags":
                case "fbml.refreshRefUrl":
                case "fbml.refreshImgSrc":
                case "fbml.setRefHandle":
                case "data.setUserPreferences":
                case "data.defineObjectProperty":
                case "photos.addTag":
                case "stream.addLike":
                case "stream.removeLike":
                case "stream.removeComment":
                case "sms.canSend":
                {
                    _loc_3 = new BooleanResultData();
                    ((BooleanResultData)_loc_3).value = FacebookXMLParserUtils.toBoolean(_loc_4);
                    break;
                }
                case "feed.publishUserAction":
                {
                    _loc_3 = new BooleanResultData();
                    ((BooleanResultData)_loc_3).value = FacebookXMLParserUtils.toBoolean(_loc_4.children().get(0));
                    break;
                }
                case "notifications.sendEmail":
                {
                    _loc_3 = this.parseSendEmail(_loc_4);
                    break;
                }
                case "data.getObjectTypes":
                {
                    _loc_3 = this.parseGetObjectTypes(_loc_4);
                    break;
                }
                case "users.getStandardInfo":
                {
                    _loc_3 = this.parseGetStandardInfo(_loc_4);
                    break;
                }
                case "data.getObjectType":
                {
                    _loc_3 = this.parseGetObjectType(_loc_4);
                    break;
                }
                case "events.get":
                {
                    _loc_3 = this.parseGetEvent(_loc_4);
                    break;
                }
                case "events.getMembers":
                {
                    _loc_3 = this.parseGetMembers(_loc_4);
                    break;
                }
                case "fql.multiquery":
                {
                    _loc_3 = new FacebookData();
                    break;
                }
                case "fql.query":
                {
                    _loc_3 = new FacebookData();
                    break;
                }
                case "photos.createAlbum":
                {
                    _loc_3 = this.parseCreateAlbum(_loc_4);
                    break;
                }
                case "photos.get":
                {
                    _loc_3 = this.parseGetPhotos(_loc_4);
                    break;
                }
                case "photos.getTags":
                {
                    _loc_3 = this.parseGetTags(_loc_4);
                    break;
                }
                case "photos.getAlbums":
                {
                    _loc_3 = this.parseGetAlbums(_loc_4);
                    break;
                }
                case "photos.upload":
                {
                    _loc_3 = this.parseFacebookPhoto(_loc_4);
                    break;
                }
                case "pages.getInfo":
                {
                    _loc_3 = this.parsePageGetInfo(_loc_4);
                    break;
                }
                case "batch.run":
                {
                    _loc_3 = this.parseBatchRun(_loc_4);
                    break;
                }
                case "fbml.getCustomTags":
                {
                    _loc_3 = this.parseGetCustomTags(_loc_4);
                    break;
                }
                case "connect.unregisterUsers":
                case "connect.registerUsers":
                {
                    _loc_3 = new ArrayResultData();
                    ((ArrayResultData)_loc_3).arrayResult = FacebookXMLParserUtils.toArray(_loc_4);
                    break;
                }
                case "status.get":
                {
                    _loc_3 = this.parseGetStatus(_loc_4);
                    break;
                }
                case "stream.get":
                {
                    _loc_3 = FacebookStreamXMLParser.createStream(_loc_4, this.fb_namespace);
                    break;
                }
                case "stream.getComments":
                {
                    _loc_3 = FacebookStreamXMLParser.createGetCommentsData(_loc_4, this.fb_namespace);
                    break;
                }
                case "stream.getFilters":
                {
                    _loc_3 = FacebookStreamXMLParser.createStreamFilterCollection(_loc_4, this.fb_namespace);
                    break;
                }
                case "auth.createToken":
                case "events.create":
                case "links.post":
                case "auth.promoteSession":
                case "admin.getRestrictionInfo":
                case "data.getObjectProperty":
                case "notifications.send":
                case "notes.create":
                case "data.getUserPreference":
                case "profile.setFBML":
                case "users.getLoggedInUser":
                case "stream.addComment":
                {
                }
                default:
                {
                    _loc_3 = new StringResultData();
                    ((StringResultData)_loc_3).value = FacebookXMLParserUtils.toStringValue(_loc_4);
                    break;
                    break;
                }
            }
            _loc_3.rawResult = param1;
            return _loc_3;
        }//end

        protected GetStandardInfoData  parseGetStandardInfo (XML param1 )
        {
            _loc_4 = null;
            UserData _loc_5 =null ;
            GetStandardInfoData _loc_2 =new GetStandardInfoData ();
            UserCollection _loc_3 =new UserCollection ();
            for(int i0 = 0; i0 < param1..user.size(); i0++)
            {
            		_loc_4 = param1..user.get(i0);

                _loc_5 = new UserData();
                _loc_5.uid = this.fb_namespace::uid;
                _loc_5.affiations = this.getAffiliation(XML(this.fb_namespace::affiliations.toXMLString()));
                _loc_5.first_name = this.fb_namespace::first_name;
                _loc_5.last_name = this.fb_namespace::last_name;
                _loc_5.name = this.fb_namespace::name;
                _loc_5.timezone = this.fb_namespace::timezone;
                _loc_3.addItem(_loc_5);
            }
            _loc_2.userCollection = _loc_3;
            return _loc_2;
        }//end

        protected GetAppPropertiesData  parseGetAppProperties (XML param1 )
        {
            GetAppPropertiesData _loc_2 =new GetAppPropertiesData ();
            _loc_2.appProperties = com.adobe.serialization.json.JSON.decode(param1.toString());
            return _loc_2;
        }//end

        protected GetRegisteredTemplateBundleData  parseGetRegisteredTemplateBundles (XML param1 )
        {
            _loc_5 = null;
            GetRegisteredTemplateBundleData _loc_2 =new GetRegisteredTemplateBundleData ();
            TemplateBundleCollection _loc_3 =new TemplateBundleCollection ();
            TemplateCollection _loc_4 =new TemplateCollection ();
            for(int i0 = 0; i0 < param1..template_bundle.size(); i0++)
            {
            		_loc_5 = param1..template_bundle.get(i0);

                this.getTemplate(this.fb_namespace::one_line_story_template, _loc_4);
                this.getTemplate(this.fb_namespace::short_story_templates, _loc_4);
                this.getTemplate(this.fb_namespace::full_story_template, _loc_4);
                _loc_4.template_bundle_id = this.fb_namespace::template_bundle_id;
                _loc_4.time_created = FacebookXMLParserUtils.toDate(this.fb_namespace::time_created);
            }
            _loc_2.bundleCollection = _loc_4;
            return _loc_2;
        }//end

        protected GetRegisteredTemplateBundleByIDData  parseGetRegisteredTemplateBundleByID (XML param1 )
        {
            GetRegisteredTemplateBundleByIDData _loc_2 =new GetRegisteredTemplateBundleByIDData ();
            TemplateCollection _loc_3 =new TemplateCollection ();
            this.getTemplate(this.fb_namespace::one_line_story_template, _loc_3);
            this.getTemplate(this.fb_namespace::short_story_templates, _loc_3);
            this.getTemplate(this.fb_namespace::full_story_template, _loc_3);
            _loc_3.template_bundle_id = this.fb_namespace::template_bundle_id;
            _loc_3.time_created = FacebookXMLParserUtils.toDate(this.fb_namespace::time_created);
            _loc_2.templateCollection = _loc_3;
            return _loc_2;
        }//end

        protected String  responseNodeNameToMethodName (String param1 )
        {
            _loc_2 = param1.split("_");
            _loc_2.pop();
            return _loc_2.join(".");
        }//end

        protected GetObjectTypesData  parseGetObjectTypes (XML param1 )
        {
            _loc_4 = null;
            ObjectTypesData _loc_5 =null ;
            GetObjectTypesData _loc_2 =new GetObjectTypesData ();
            ObjectTypesCollection _loc_3 =new ObjectTypesCollection ();
            for(int i0 = 0; i0 < param1..object_type_info.size(); i0++)
            {
            		_loc_4 = param1..object_type_info.get(i0);

                _loc_5 = new ObjectTypesData();
                _loc_5.name = this.fb_namespace::name;
                _loc_5.object_class = this.fb_namespace::object_class;
                _loc_3.addItem(_loc_5);
            }
            _loc_2.objectTypeCollection = _loc_3;
            return _loc_2;
        }//end

        protected FacebookPhoto  parseFacebookPhoto (XML param1 )
        {
            FacebookPhoto _loc_2 =new FacebookPhoto ();
            _loc_2.pid = FacebookXMLParserUtils.toStringValue(this.fb_namespace::pid.get(0));
            _loc_2.aid = FacebookXMLParserUtils.toStringValue(this.fb_namespace::aid.get(0));
            _loc_2.owner = FacebookXMLParserUtils.toNumber(this.fb_namespace::owner.get(0));
            _loc_2.src = FacebookXMLParserUtils.toStringValue(this.fb_namespace::src.get(0));
            _loc_2.src_big = FacebookXMLParserUtils.toStringValue(this.fb_namespace::src_big.get(0));
            _loc_2.src_small = FacebookXMLParserUtils.toStringValue(this.fb_namespace::src_small.get(0));
            _loc_2.link = FacebookXMLParserUtils.toStringValue(this.fb_namespace::link.get(0));
            _loc_2.caption = FacebookXMLParserUtils.toStringValue(this.fb_namespace::caption.get(0));
            return _loc_2;
        }//end

        protected GetObjectTypeData  parseGetObjectType (XML param1 )
        {
            GetObjectTypeData _loc_2 =new GetObjectTypeData ();
            _loc_2.name = this.fb_namespace::name;
            _loc_2.data_type = this.fb_namespace::data_type;
            _loc_2.index_type = this.fb_namespace::index_type;
            return _loc_2;
        }//end

        protected Object createTagObject (XML param1 ,Array param2)
        {
            AbstractTagData _loc_5 =null ;
            Object _loc_7 =null ;
            _loc_3 = param1.children ().length ();
            _loc_4 = param1.children ().get(0).toLowerCase ();
            if (param1.children().get(0).toLowerCase() == "leaf")
            {
                _loc_5 = new LeafTagData(null, null, null, null, null);
                ((LeafTagData)_loc_5).fbml = param1.children().get(9);
            }
            else
            {
                _loc_5 = new ContainerTagData(null, null, null, null, null, null, null);
                ((ContainerTagData)_loc_5).open_tag_fbml = param1.children().get(2);
                ((ContainerTagData)_loc_5).close_tag_fbml = param1.children().get(4);
            }
            double _loc_6 =0;
            while (_loc_6 < _loc_3)
            {

                _loc_7 = param1.children().get(_loc_6);
                switch(param2.get(_loc_6))
                {
                    case "name":
                    case "type":
                    case "description":
                    case "is_public":
                    case "header_fbml":
                    case "footer_fbml":
                    {
                        _loc_5.get(param2.put(_loc_6),  _loc_7.text());
                        break;
                    }
                    case "attributes":
                    {
                        if (_loc_7.children() instanceof XMLList)
                        {
                            if (_loc_7.children().length() == 0)
                            {
                                _loc_5.get(param2.put(_loc_6),  null);
                            }
                        }
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
                _loc_6 = _loc_6 + 1;
            }
            return _loc_5;
        }//end

        protected GetNotesData  parseGetNotes (XML param1 )
        {
            _loc_4 = null;
            NoteData _loc_5 =null ;
            GetNotesData _loc_2 =new GetNotesData ();
            NotesCollection _loc_3 =new NotesCollection ();
            for(int i0 = 0; i0 < param1..note.size(); i0++)
            {
            		_loc_4 = param1..note.get(i0);

                _loc_5 = new NoteData();
                _loc_5.note_id = this.fb_namespace::note_id;
                _loc_5.title = this.fb_namespace::title;
                _loc_5.content = this.fb_namespace::content;
                _loc_5.created_time = FacebookXMLParserUtils.toDate(this.fb_namespace::created_time);
                _loc_5.updated_time = FacebookXMLParserUtils.toDate(this.fb_namespace::updated_time);
                _loc_5.uid = this.fb_namespace::uid;
                _loc_3.addItem(_loc_5);
            }
            _loc_2.notesCollection = _loc_3;
            return _loc_2;
        }//end

        protected GetMetricsData  parseGetMetrics (XML param1 )
        {
            _loc_4 = null;
            MetricsData _loc_5 =null ;
            GetMetricsData _loc_2 =new GetMetricsData ();
            MetricsDataCollection _loc_3 =new MetricsDataCollection ();
            for(int i0 = 0; i0 < param1..metrics.size(); i0++)
            {
            		_loc_4 = param1..metrics.get(i0);

                _loc_5 = new MetricsData();
                _loc_5.end_time = FacebookXMLParserUtils.toDate(this.fb_namespace::end_time);
                _loc_5.active_users = this.fb_namespace::active_users;
                _loc_5.canvas_page_views = this.fb_namespace::canvas_page_views;
                _loc_3.addItem(_loc_5);
            }
            _loc_2.metricsCollection = _loc_3;
            return _loc_2;
        }//end

        protected GetPageInfoData  parsePageGetInfo (XML param1 )
        {
            Object _loc_5 =null ;
            PageInfoData _loc_6 =null ;
            GetPageInfoData _loc_2 =new GetPageInfoData ();
            PageInfoCollection _loc_3 =new PageInfoCollection ();
            _loc_4 = this.fb_namespace ;
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            		_loc_5 = _loc_4.get(i0);

                _loc_6 = new PageInfoData();
                _loc_6.page_id = this.fb_namespace::page_id;
                _loc_6.name = this.fb_namespace::name;
                _loc_6.pic_small = this.fb_namespace::pic_small;
                _loc_6.pic_big = this.fb_namespace::pic_big;
                _loc_6.pic_square = this.fb_namespace::pic_square;
                _loc_6.pic_large = this.fb_namespace::pic_large;
                _loc_6.type = this.fb_namespace::type;
                _loc_6.website = this.fb_namespace::website;
                _loc_6.location = FacebookXMLParserUtils.createLocation(this.fb_namespace::location.get(0), this.fb_namespace);
                _loc_6.hours = this.fb_namespace::hours;
                _loc_6.band_members = this.fb_namespace::band_members;
                _loc_6.bio = this.fb_namespace::bio;
                _loc_6.hometown = this.fb_namespace::hometown;
                _loc_6.genre = FacebookXMLParserUtils.toStringValue(this.fb_namespace::genre.get(0));
                _loc_6.record_label = this.fb_namespace::record_label;
                _loc_6.influences = this.fb_namespace::influences;
                _loc_6.has_added_app = FacebookXMLParserUtils.toBoolean(this.fb_namespace::has_added_app.get(0));
                _loc_6.founded = this.fb_namespace::founded;
                _loc_6.company_overview = this.fb_namespace::company_overview;
                _loc_6.mission = this.fb_namespace::mission;
                _loc_6.products = this.fb_namespace::products;
                _loc_6.release_date = this.fb_namespace::release_date;
                _loc_6.starring = this.fb_namespace::starring;
                _loc_6.written_by = this.fb_namespace::written_by;
                _loc_6.directed_by = this.fb_namespace::directed_by;
                _loc_6.produced_by = this.fb_namespace::produced_by;
                _loc_6.studio = this.fb_namespace::studio;
                _loc_6.awards = this.fb_namespace::awards;
                _loc_6.plot_outline = this.fb_namespace::plot_outline;
                _loc_6.network = this.fb_namespace::network;
                _loc_6.season = this.fb_namespace::season;
                _loc_6.schedule = this.fb_namespace::schedule;
                _loc_3.addPageInfo(_loc_6);
            }
            _loc_2.pageInfoCollection = _loc_3;
            return _loc_2;
        }//end

        protected GetTagsData  parseGetTags (XML param1 )
        {
            _loc_4 = null;
            TagData _loc_5 =null ;
            GetTagsData _loc_2 =new GetTagsData ();
            PhotoTagCollection _loc_3 =new PhotoTagCollection ();
            for(int i0 = 0; i0 < param1..photo_tag.size(); i0++)
            {
            		_loc_4 = param1..photo_tag.get(i0);

                _loc_5 = new TagData();
                _loc_5.text = this.fb_namespace::text;
                _loc_5.pid = this.fb_namespace::pid;
                _loc_5.subject = this.fb_namespace::subject;
                _loc_5.xcoord = this.fb_namespace::xcoord;
                _loc_5.ycoord = this.fb_namespace::ycoord;
                _loc_5.created = FacebookXMLParserUtils.toDate(this.fb_namespace::created);
                _loc_3.addPhotoTag(_loc_5);
            }
            _loc_2.photoTagsCollection = _loc_3;
            return _loc_2;
        }//end

        protected GetMemberData  parseGetGroupMembers (XML param1 )
        {
            GetMemberData _loc_2 =new GetMemberData ();
            _loc_2.members = FacebookXMLParserUtils.toUIDArray(this.fb_namespace::members.get(0));
            _loc_2.admins = FacebookXMLParserUtils.toUIDArray(this.fb_namespace::admins.get(0));
            _loc_2.officers = FacebookXMLParserUtils.toUIDArray(this.fb_namespace::officers.get(0));
            _loc_2.notReplied = FacebookXMLParserUtils.toUIDArray(this.fb_namespace::not_replied.get(0));
            return _loc_2;
        }//end

        protected GetGroupData  parseGetGroups (XML param1 )
        {
            _loc_4 = null;
            GroupData _loc_5 =null ;
            GetGroupData _loc_2 =new GetGroupData ();
            GroupCollection _loc_3 =new GroupCollection ();
            for(int i0 = 0; i0 < param1..group.size(); i0++)
            {
            		_loc_4 = param1..group.get(i0);

                _loc_5 = new GroupData();
                _loc_5.gid = this.fb_namespace::gid;
                _loc_5.name = this.fb_namespace::name;
                _loc_5.nid = this.fb_namespace::nid;
                _loc_5.description = this.fb_namespace::description;
                _loc_5.group_type = this.fb_namespace::group_type;
                _loc_5.group_subtype = this.fb_namespace::group_subtype;
                _loc_5.recent_news = this.fb_namespace::recent_news;
                _loc_5.pic = this.fb_namespace::pic;
                _loc_5.pic_big = this.fb_namespace::pic_big;
                _loc_5.pic_small = this.fb_namespace::pic_small;
                _loc_5.creator = this.fb_namespace::creator;
                _loc_5.update_time = FacebookXMLParserUtils.toDate(this.fb_namespace::update_time);
                _loc_5.office = this.fb_namespace::office;
                _loc_5.website = this.fb_namespace::website;
                _loc_5.venue = FacebookXMLParserUtils.createLocation(this.fb_namespace::venue.get(0), this.fb_namespace);
                _loc_5.privacy = this.fb_namespace::privacy;
                _loc_3.addGroup(_loc_5);
            }
            _loc_2.groups = _loc_3;
            return _loc_2;
        }//end

        protected GetCreateAlbumData  parseCreateAlbum (XML param1 )
        {
            GetCreateAlbumData _loc_2 =new GetCreateAlbumData ();
            AlbumData _loc_3 =new AlbumData ();
            _loc_3.aid = this.fb_namespace::aid;
            _loc_3.cover_pid = this.fb_namespace::cover_pid;
            _loc_3.owner = this.fb_namespace::owner;
            _loc_3.name = this.fb_namespace::name;
            _loc_3.created = FacebookXMLParserUtils.toDate(this.fb_namespace::created);
            _loc_3.modified = FacebookXMLParserUtils.toDate(this.fb_namespace::modified);
            _loc_3.description = this.fb_namespace::description;
            _loc_3.location = this.fb_namespace::location;
            _loc_3.link = this.fb_namespace::link;
            _loc_3.size = this.fb_namespace::size;
            _loc_3.visible = this.fb_namespace::visible;
            _loc_2.albumData = _loc_3;
            return _loc_2;
        }//end

        protected GetAllocationData  parseGetAllocation (XML param1 )
        {
            GetAllocationData _loc_2 =new GetAllocationData ();
            _loc_2.allocationLimit = Number(param1.toString());
            return _loc_2;
        }//end

        protected GetCookiesData  parseGetCookies (XML param1 )
        {
            GetCookiesData _loc_2 =new GetCookiesData ();
            _loc_2.uid = this.fb_namespace::uid;
            _loc_2.name = this.fb_namespace::name;
            _loc_2.value = this.fb_namespace::value;
            _loc_2.expires = this.fb_namespace::expires;
            _loc_2.path = this.fb_namespace::path;
            return _loc_2;
        }//end

        protected GetCustomTagsData  parseGetCustomTags (XML param1 )
        {
            _loc_5 = null;
            Array _loc_2 =.get( "type","name","open_tag_fbml","description","close_tag_fbml","is_public","attributes","header_fbml","footer_fbml","fbml") ;
            GetCustomTagsData _loc_3 =new GetCustomTagsData ();
            TagCollection _loc_4 =new TagCollection ();
            for(int i0 = 0; i0 < param1..custom_tag.size(); i0++)
            {
            		_loc_5 = param1..custom_tag.get(i0);

                _loc_4.addItem(this.createTagObject(_loc_5, _loc_2));
            }
            _loc_3.tagCollection = _loc_4;
            return _loc_3;
        }//end

        protected GetAlbumsData  parseGetAlbums (XML param1 )
        {
            GetAlbumsData _loc_2 =new GetAlbumsData ();
            _loc_2.albumCollection = FacebookXMLParserUtils.createAlbumCollection(param1, this.fb_namespace);
            return _loc_2;
        }//end

        protected GetInfoData  parseGetInfo (XML param1 )
        {
            FacebookUser _loc_7 =null ;
            FacebookUserCollection _loc_2 =new FacebookUserCollection ();
            _loc_3 = param1..user ;
            _loc_4 = _loc_3.length ();
            int _loc_5 =0;
            while (_loc_5 < _loc_4)
            {

                _loc_7 = FacebookUserXMLParser.createFacebookUser(_loc_3.get(_loc_5), this.fb_namespace);
                _loc_2.addItem(_loc_7);
                _loc_5 = _loc_5 + 1;
            }
            GetInfoData _loc_6 =new GetInfoData ();
            _loc_6.userCollection = _loc_2;
            return _loc_6;
        }//end

        protected GetListsData  parseGetLists (XML param1 )
        {
            _loc_4 = null;
            ListsData _loc_5 =null ;
            GetListsData _loc_2 =new GetListsData ();
            Array _loc_3 =new Array();
            for(int i0 = 0; i0 < param1..friendlist.size(); i0++)
            {
            		_loc_4 = param1..friendlist.get(i0);

                _loc_5 = new ListsData();
                _loc_5.flid = this.fb_namespace::flid;
                _loc_5.name = this.fb_namespace::name;
                _loc_3.push(_loc_5);
            }
            _loc_2.lists = _loc_3;
            return _loc_2;
        }//end

        protected GetAppUserData  parseGetAppUsersData (XML param1 )
        {
            _loc_2 = FacebookXMLParserUtils.toUIDArray(param1);
            GetAppUserData _loc_3 =new GetAppUserData ();
            _loc_3.uids = _loc_2;
            return _loc_3;
        }//end

        protected GetStatusData  parseGetStatus (XML param1 )
        {
            XML _loc_7 =null ;
            Status _loc_8 =null ;
            GetStatusData _loc_2 =new GetStatusData ();
            Array _loc_3 =new Array();
            _loc_4 = param1.children ();
            _loc_5 = param1.children ().length ();
            int _loc_6 =0;
            while (_loc_6 < _loc_5)
            {

                _loc_7 = _loc_4.get(_loc_6);
                _loc_8 = new Status();
                _loc_8.uid = FacebookXMLParserUtils.toStringValue(this.fb_namespace::uid.get(0));
                _loc_8.status_id = FacebookXMLParserUtils.toStringValue(this.fb_namespace::status_id.get(0));
                _loc_8.time = FacebookXMLParserUtils.toDate(this.fb_namespace::time.get(0));
                _loc_8.source = FacebookXMLParserUtils.toStringValue(this.fb_namespace::source.get(0));
                _loc_8.message = FacebookXMLParserUtils.toStringValue(this.fb_namespace::message.get(0));
                _loc_3.push(_loc_8);
                _loc_6 = _loc_6 + 1;
            }
            _loc_2.status = _loc_3;
            return _loc_2;
        }//end

        protected GetMembersData  parseGetMembers (XML param1 )
        {
            GetMembersData _loc_2 =new GetMembersData ();
            _loc_2.attending = FacebookXMLParserUtils.toUIDArray(param1..attending.get(0));
            _loc_2.unsure = FacebookXMLParserUtils.toUIDArray(param1..unsure.get(0));
            _loc_2.declined = FacebookXMLParserUtils.toUIDArray(param1..declined.get(0));
            _loc_2.not_replied = FacebookXMLParserUtils.toUIDArray(param1..not_replied.get(0));
            return _loc_2;
        }//end

        protected GetEventsData  parseGetEvent (XML param1 )
        {
            _loc_4 = null;
            EventData _loc_5 =null ;
            GetEventsData _loc_2 =new GetEventsData ();
            EventCollection _loc_3 =new EventCollection ();
            for(int i0 = 0; i0 < param1..event.size(); i0++)
            {
            		_loc_4 = param1..event.get(i0);

                _loc_5 = new EventData();
                _loc_5.eid = this.fb_namespace::eid;
                _loc_5.name = this.fb_namespace::name;
                _loc_5.tagline = this.fb_namespace::tagline;
                _loc_5.nid = this.fb_namespace::nid;
                _loc_5.pic = this.fb_namespace::pic;
                _loc_5.pic_big = this.fb_namespace::pic_big;
                _loc_5.pic_small = this.fb_namespace::pic_small;
                _loc_5.host = this.fb_namespace::host;
                _loc_5.description = this.fb_namespace::description;
                _loc_5.event_type = this.fb_namespace::event_type;
                _loc_5.event_subtype = this.fb_namespace::event_subtype;
                _loc_5.start_time = FacebookXMLParserUtils.toDate(this.fb_namespace::start_time);
                _loc_5.end_time = FacebookXMLParserUtils.toDate(this.fb_namespace::end_time);
                _loc_5.creator = this.fb_namespace::end_time;
                _loc_5.update_time = FacebookXMLParserUtils.toDate(this.fb_namespace::update_time);
                _loc_5.location = this.fb_namespace::location;
                _loc_5.venue = FacebookXMLParserUtils.createLocation(this.fb_namespace::venue.get(0), this.fb_namespace);
                _loc_3.addItem(_loc_5);
            }
            _loc_2.eventCollection = _loc_3;
            return _loc_2;
        }//end

        protected GetFriendsData  parseGetFriends (XML param1 )
        {
            _loc_4 = null;
            FacebookUser _loc_5 =null ;
            GetFriendsData _loc_2 =new GetFriendsData ();
            FacebookUserCollection _loc_3 =new FacebookUserCollection ();
            for(int i0 = 0; i0 < param1..uid.size(); i0++)
            {
            		_loc_4 = param1..uid.get(i0);

                _loc_5 = new FacebookUser();
                _loc_5.uid = _loc_4;
                _loc_3.addItem(_loc_5);
            }
            _loc_2.friends = _loc_3;
            return _loc_2;
        }//end

        protected GetPublicInfoData  parseGetPublicInfo (XML param1 )
        {
            GetPublicInfoData _loc_2 =new GetPublicInfoData ();
            _loc_2.app_id = this.fb_namespace::app_id;
            _loc_2.api_key = this.fb_namespace::api_key;
            _loc_2.canvas_name = this.fb_namespace::canvas_name;
            _loc_2.display_name = this.fb_namespace::display_name;
            _loc_2.icon_url = this.fb_namespace::icon_url;
            _loc_2.logo_url = this.fb_namespace::logo_url;
            _loc_2.developers = this.fb_namespace::developers;
            _loc_2.company_name = this.fb_namespace::company_name;
            _loc_2.developers = this.fb_namespace::developers;
            _loc_2.daily_active_users = this.fb_namespace::daily_active_users;
            _loc_2.weekly_active_users = this.fb_namespace::weekly_active_users;
            _loc_2.monthly_active_users = this.fb_namespace::monthly_active_users;
            _loc_2.description = this.fb_namespace::description;
            return _loc_2;
        }//end

        protected AreFriendsData  parseAreFriends (XML param1 )
        {
            _loc_4 = null;
            FriendsData _loc_5 =null ;
            AreFriendsData _loc_2 =new AreFriendsData ();
            FriendsCollection _loc_3 =new FriendsCollection ();
            for(int i0 = 0; i0 < param1..friend_info.size(); i0++)
            {
            		_loc_4 = param1..friend_info.get(i0);

                _loc_5 = new FriendsData();
                _loc_5.uid1 = this.fb_namespace::uid1;
                _loc_5.uid2 = this.fb_namespace::uid2;
                _loc_5.are_friends = FacebookXMLParserUtils.toBoolean(XML(this.fb_namespace::are_friends.toXMLString()));
                _loc_3.addItem(_loc_5);
            }
            _loc_2.friendsCollection = _loc_3;
            return _loc_2;
        }//end

        public FacebookError  validateFacebookResponce (String param1 )
        {
            XML xml ;
            Error xmlError ;
            result = param1;
            FacebookError error ;
            boolean hasXMLError ;
            try
            {
                xml = new XML(result);
            }
            catch (e:Error)
            {
                xmlError = e;
                hasXMLError;
            }
            if (hasXMLError == false)
            {
                if (xml.localName() == "error_response")
                {
                    error = new FacebookError();
                    error.rawResult = result;
                    error.errorCode = Number(this.fb_namespace::error_code);
                    error.errorMsg = this.fb_namespace::error_msg;
                    error.requestArgs = FacebookXMLParserUtils.xmlToUrlVariables(xml..arg);
                }
                return error;
            }
            if (hasXMLError == true)
            {
                error = new FacebookError();
                error.error = xmlError;
                error.errorCode = -1;
            }
            return error;
        }//end

        protected FacebookData  parseBatchRun (XML param1 )
        {
            String _loc_7 =null ;
            XML _loc_8 =null ;
            FacebookError _loc_9 =null ;
            String _loc_10 =null ;
            FacebookData _loc_11 =null ;
            _loc_2 = param1..batch_run_response_elt ;
            _loc_3 = _loc_2.length ();
            Array _loc_4 =new Array();
            int _loc_5 =0;
            while (_loc_5 < _loc_3)
            {

                _loc_7 = _loc_2.get(_loc_5).toString();
                _loc_8 = new XML(_loc_7);
                _loc_9 = this.validateFacebookResponce(_loc_7);
                if (_loc_9 === null)
                {
                    _loc_10 = this.responseNodeNameToMethodName(_loc_8.localName().toString());
                    _loc_11 = this.parse(_loc_7, _loc_10);
                    _loc_4.push(_loc_11);
                }
                else
                {
                    _loc_4.push(_loc_9);
                }
                _loc_5 = _loc_5 + 1;
            }
            BatchResult _loc_6 =new BatchResult ();
            _loc_6.results = _loc_4;
            return _loc_6;
        }//end

        protected void  getTemplate (XMLList param1 ,TemplateCollection param2 )
        {
            _loc_3 = null;
            TemplateData _loc_4 =null ;
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            		_loc_3 = param1.get(i0);

                _loc_4 = new TemplateData();
                _loc_4.type = _loc_3.localName();
                _loc_4.template_body = this.fb_namespace::template_body;
                _loc_4.template_title = this.fb_namespace::template_title;
                param2.addTemplateData(_loc_4);
            }
            return;
        }//end

    }


