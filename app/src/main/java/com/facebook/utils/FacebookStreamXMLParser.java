package com.facebook.utils;

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

import com.facebook.data.stream.*;
    public class FacebookStreamXMLParser
    {

        public  FacebookStreamXMLParser ()
        {
            return;
        }//end

        public static Array  createCommentsArray (XMLList param1 ,Namespace param2 )
        {
            XML _loc_6 =null ;
            PostCommentData _loc_7 =null ;
            Array _loc_3 =new Array();
            _loc_4 = param1.length ();
            int _loc_5 =0;
            while (_loc_5 < _loc_4)
            {

                _loc_6 = param1.get(_loc_5);
                _loc_7 = new PostCommentData();
                _loc_7.fromid = FacebookXMLParserUtils.toStringValue(param2::fromid.get(0));
                _loc_7.id = FacebookXMLParserUtils.toStringValue(param2::id.get(0));
                _loc_7.text = FacebookXMLParserUtils.toStringValue(param2::text.get(0));
                _loc_7.time = FacebookXMLParserUtils.toDate(param2::time.get(0));
                _loc_3.push(_loc_7);
                _loc_5 = _loc_5 + 1;
            }
            return _loc_3;
        }//end

        public static Array  createMediaArray (XML param1 ,Namespace param2 )
        {
            XML _loc_7 =null ;
            StreamMediaData _loc_8 =null ;
            if (param1 == null)
            {
                return null;
            }
            Array _loc_3 =new Array();
            _loc_4 = param1.children ();
            _loc_5 = param1.children ().length ();
            int _loc_6 =0;
            while (_loc_6 < _loc_5)
            {

                _loc_7 = _loc_4.get(_loc_6);
                _loc_8 = new StreamMediaData();
                _loc_8.type = FacebookXMLParserUtils.toStringValue(param2::type.get(0));
                _loc_8.alt = FacebookXMLParserUtils.toStringValue(param2::alt.get(0));
                _loc_8.href = FacebookXMLParserUtils.toStringValue(param2::href.get(0));
                _loc_8.src = FacebookXMLParserUtils.toStringValue(param2::src.get(0));
                _loc_8.video = createVideoMedia(param2::video.get(0), param2);
                _loc_8.photo = createPhotoMedia(param2::photo.get(0), param2);
                _loc_8.flash = createFlashMedia(param2::swf.get(0), param2);
                _loc_8.music = createMusicMedia(param2::music.get(0), param2);
                _loc_3.push(_loc_8);
                _loc_6 = _loc_6 + 1;
            }
            return _loc_3;
        }//end

        public static GetFiltersData  createStreamFilterCollection (XML param1 ,Namespace param2 )
        {
            XML _loc_8 =null ;
            StreamFilterData _loc_9 =null ;
            _loc_3 = new GetFiltersData ();
            _loc_4 = new StreamFilterCollection ();
            _loc_5 = param1..param2 ;
            _loc_6 = param1..param2.length ();
            int _loc_7 =0;
            while (_loc_7 < _loc_6)
            {

                _loc_8 = _loc_5.get(_loc_7);
                _loc_9 = new StreamFilterData();
                _loc_9.filter_key = FacebookXMLParserUtils.toStringValue(param2::filter_key.get(0));
                _loc_9.icon_url = FacebookXMLParserUtils.toStringValue(param2::icon_url.get(0));
                _loc_9.is_visible = FacebookXMLParserUtils.toBoolean(param2::is_visible.get(0));
                _loc_9.name = FacebookXMLParserUtils.toStringValue(param2::name.get(0));
                _loc_9.rank = FacebookXMLParserUtils.toNumber(param2::rank.get(0));
                _loc_9.type = FacebookXMLParserUtils.toStringValue(param2::type.get(0));
                _loc_9.uid = FacebookXMLParserUtils.toStringValue(param2::uid.get(0));
                _loc_9.value = FacebookXMLParserUtils.toStringValue(param2::value.get(0));
                _loc_4.addItem(_loc_9);
                _loc_7 = _loc_7 + 1;
            }
            _loc_3.filters = _loc_4;
            return _loc_3;
        }//end

        public static VideoMedia  createVideoMedia (XML param1 ,Namespace param2 )
        {
            if (param1 == null)
            {
                return null;
            }
            _loc_3 = new VideoMedia ();
            _loc_3.display_url = FacebookXMLParserUtils.toStringValue(param2::display_url.get(0));
            _loc_3.owner = FacebookXMLParserUtils.toStringValue(param2::owner.get(0));
            _loc_3.permalink = FacebookXMLParserUtils.toStringValue(param2::permalink.get(0));
            _loc_3.source_url = FacebookXMLParserUtils.toStringValue(param2::source_url.get(0));
            _loc_3.preview_img = FacebookXMLParserUtils.toStringValue(param2::preview_img.get(0));
            return _loc_3;
        }//end

        public static FlashMedia  createFlashMedia (XML param1 ,Namespace param2 )
        {
            if (param1 == null)
            {
                return null;
            }
            _loc_3 = new FlashMedia ();
            _loc_3.source_url = FacebookXMLParserUtils.toStringValue(param2::source_url.get(0));
            _loc_3.preview_img = FacebookXMLParserUtils.toStringValue(param2::preview_img.get(0));
            return _loc_3;
        }//end

        public static GetCommentsData  createGetCommentsData (XML param1 ,Namespace param2 )
        {
            _loc_3 = param1..param2 ;
            _loc_4 = new GetCommentsData ();
            _loc_4.comments = createCommentsArray(_loc_3, param2);
            return _loc_4;
        }//end

        public static Array  createActionLinksArray (XML param1 ,Namespace param2 )
        {
            XML _loc_7 =null ;
            ActionLinkData _loc_8 =null ;
            if (param1 == null)
            {
                return null;
            }
            Array _loc_3 =new Array();
            _loc_4 = param1.children ();
            _loc_5 = param1.children ().length ();
            int _loc_6 =0;
            while (_loc_6 < _loc_5)
            {

                _loc_7 = _loc_4.get(_loc_6);
                _loc_8 = new ActionLinkData();
                _loc_8.text = FacebookXMLParserUtils.toStringValue(param2::text.get(0));
                _loc_8.href = FacebookXMLParserUtils.toStringValue(param2::href.get(0));
                _loc_3.push(_loc_8);
                _loc_6 = _loc_6 + 1;
            }
            return _loc_3;
        }//end

        public static MusicMedia  createMusicMedia (XML param1 ,Namespace param2 )
        {
            if (param1 == null)
            {
                return null;
            }
            _loc_3 = new MusicMedia ();
            _loc_3.source_url = FacebookXMLParserUtils.toStringValue(param2::source_url.get(0));
            _loc_3.artist = FacebookXMLParserUtils.toStringValue(param2::artist.get(0));
            _loc_3.title = FacebookXMLParserUtils.toStringValue(param2::title.get(0));
            return _loc_3;
        }//end

        public static GetStreamData  createStream (XML param1 ,Namespace param2 )
        {
            int _loc_6 =0;
            int _loc_7 =0;
            XML _loc_10 =null ;
            StreamStoryData _loc_11 =null ;
            XML _loc_12 =null ;
            AttachmentData _loc_13 =null ;
            LikesData _loc_14 =null ;
            XML _loc_15 =null ;
            XML _loc_16 =null ;
            ProfileData _loc_17 =null ;
            XML _loc_18 =null ;
            _loc_3 = new GetStreamData ();
            _loc_4 = new StreamStoryCollection ();
            _loc_5 = new ProfileCollection ();
            _loc_3.stories = _loc_4;
            _loc_3.profiles = _loc_5;
            _loc_8 = param2.children();
            _loc_6 = param2::posts.children().length();
            _loc_7 = 0;
            while (_loc_7 < _loc_6)
            {

                _loc_10 = _loc_8.get(_loc_7);
                _loc_11 = new StreamStoryData();
                _loc_11.sourceXML = _loc_10;
                _loc_12 = param2::attachment.get(0);
                _loc_13 = new AttachmentData();
                _loc_13.name = FacebookXMLParserUtils.toStringValue(param2::name.get(0));
                _loc_13.text = FacebookXMLParserUtils.toStringValue(param2::text.get(0));
                _loc_13.body = FacebookXMLParserUtils.toStringValue(param2::body.get(0));
                _loc_13.icon = FacebookXMLParserUtils.toStringValue(param2::icon.get(0));
                _loc_13.label = FacebookXMLParserUtils.toStringValue(param2::label.get(0));
                _loc_13.media = createMediaArray(param2::media.get(0), param2);
                _loc_13.title = FacebookXMLParserUtils.toStringValue(param2::title.get(0));
                _loc_13.href = FacebookXMLParserUtils.toStringValue(param2::href.get(0));
                _loc_13.caption = FacebookXMLParserUtils.toStringValue(param2::caption.get(0));
                _loc_13.description = FacebookXMLParserUtils.toStringValue(param2::description.get(0));
                _loc_13.properties = FacebookXMLParserUtils.xmlListToObjectArray(_loc_12..param2::stream_property);
                _loc_11.attachment = _loc_13;
                _loc_11.actor_id = FacebookXMLParserUtils.toStringValue(param2::actor_id.get(0));
                _loc_11.comments = createComments(param2::comments.get(0), param2);
                _loc_14 = new LikesData();
                _loc_15 = param2::likes.get(0);
                _loc_14.can_like = FacebookXMLParserUtils.toBoolean(param2::can_like.get(0));
                _loc_14.user_likes = FacebookXMLParserUtils.toBoolean(param2::user_likes.get(0));
                _loc_14.count = FacebookXMLParserUtils.toNumber(param2::count.get(0));
                _loc_14.friends = FacebookXMLParserUtils.toUIDArray(param2::friends.get(0));
                _loc_14.sample = FacebookXMLParserUtils.toUIDArray(param2::sample.get(0));
                _loc_14.href = FacebookXMLParserUtils.toStringValue(param2::href.get(0));
                _loc_11.likes = _loc_14;
                _loc_11.attribution = FacebookXMLParserUtils.toStringValue(param2::attribution.get(0));
                _loc_11.app_id = FacebookXMLParserUtils.toStringValue(param2::app_id.get(0));
                _loc_11.metadata = FacebookXMLParserUtils.nodeToObject(param2::metadata);
                _loc_11.message = FacebookXMLParserUtils.toStringValue(param2::message.get(0));
                _loc_11.source_id = FacebookXMLParserUtils.toStringValue(param2::source_id.get(0));
                _loc_11.target_id = FacebookXMLParserUtils.toStringValue(param2::target_id.get(0));
                _loc_11.post_id = FacebookXMLParserUtils.toStringValue(param2::post_id.get(0));
                _loc_11.updated_time = FacebookXMLParserUtils.toDate(param2::updated_time.get(0));
                _loc_11.created_time = FacebookXMLParserUtils.toDate(param2::created_time.get(0));
                _loc_11.type = FacebookXMLParserUtils.toNumber(param2::type.get(0));
                _loc_11.viewer_id = FacebookXMLParserUtils.toStringValue(param2::viewer_id.get(0));
                _loc_16 = param2::privacy.get(0);
                _loc_11.privacy = FacebookXMLParserUtils.toStringValue(param2::value.get(0));
                _loc_11.filter_key = FacebookXMLParserUtils.toStringValue(param2::filter_key.get(0));
                _loc_11.permalink = FacebookXMLParserUtils.toStringValue(param2::permalink.get(0));
                _loc_11.is_hidden = FacebookXMLParserUtils.toBoolean(param2::is_hidden.get(0));
                _loc_11.action_links = createActionLinksArray(param2::action_links.get(0), param2);
                _loc_4.addItem(_loc_11);
                _loc_7 = _loc_7 + 1;
            }
            _loc_9 = param2.children();
            _loc_6 = param2::profiles.children().length();
            _loc_7 = 0;
            while (_loc_7 < _loc_6)
            {

                _loc_17 = new ProfileData();
                _loc_18 = _loc_9.get(_loc_7);
                _loc_17.id = FacebookXMLParserUtils.toStringValue(param2::id.get(0));
                _loc_17.name = FacebookXMLParserUtils.toStringValue(param2::name.get(0));
                _loc_17.pic_square = FacebookXMLParserUtils.toStringValue(param2::pic_square.get(0));
                _loc_17.url = FacebookXMLParserUtils.toStringValue(param2::url.get(0));
                _loc_5.addItem(_loc_17);
                _loc_7 = _loc_7 + 1;
            }
            _loc_3.albums = FacebookXMLParserUtils.createAlbumCollection(param2::albums.get(0), param2);
            return _loc_3;
        }//end

        public static PhotoMedia  createPhotoMedia (XML param1 ,Namespace param2 )
        {
            if (param1 == null)
            {
                return null;
            }
            _loc_3 = new PhotoMedia ();
            _loc_3.aid = FacebookXMLParserUtils.toStringValue(param2::aid.get(0));
            _loc_3.index = FacebookXMLParserUtils.toNumber(param2::index.get(0));
            _loc_3.owner = FacebookXMLParserUtils.toStringValue(param2::owner.get(0));
            _loc_3.pid = FacebookXMLParserUtils.toStringValue(param2::pid.get(0));
            return _loc_3;
        }//end

        public static CommentsData  createComments (XML param1 ,Namespace param2 )
        {
            _loc_3 = new CommentsData ();
            _loc_3.can_remove = FacebookXMLParserUtils.toBoolean(param2::can_remove.get(0));
            _loc_3.can_post = FacebookXMLParserUtils.toBoolean(param2::can_post.get(0));
            _loc_3.count = FacebookXMLParserUtils.toNumber(param2::count.get(0));
            _loc_4 = param2.children ();
            _loc_3.posts = createCommentsArray(_loc_4, param2);
            return _loc_3;
        }//end

    }

