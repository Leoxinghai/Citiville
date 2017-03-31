/*
 Copyright aswing.org, see the LICENCE.txt.
*/


package org.aswing;

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


import flash.events.EventDispatcher;
import flash.events.IEventDispatcher;
import org.aswing.event.SelectionEvent;

/**
 * Default data model for list selections.
 * @author iiley
 */
public class DefaultListSelectionModel extends EventDispatcher implements ListSelectionModel{

	/**
	 * Only can select one most item at a time.
	 */
	public static  int SINGLE_SELECTION =0;
	/**
	 * Can select any item at a time.
	 */
	public static  int MULTIPLE_SELECTION =1;

	private static int MIN =-1;
	private static int MAX =int.MAX_VALUE ;

	private Array value ;
	private int minIndex ;
	private int maxIndex ;
	private int anchorIndex ;
	private int leadIndex ;
	private int selectionMode ;

	public  DefaultListSelectionModel (){
		value       = new Array();
		minIndex    = MAX;
		maxIndex    = MIN;
		anchorIndex = -1;
		leadIndex   = -1;
		selectionMode = MULTIPLE_SELECTION;
	}

	public void  setSelectionInterval (int index0 ,int index1 ,boolean programmatic =true ){
		if (index0 < 0 || index1 < 0) {
			clearSelection(programmatic);
			return;
		}
		if (getSelectionMode() == SINGLE_SELECTION) {
			index0 = index1;
		}
		int i ;
		updateLeadAnchorIndices(index0, index1);
		int min =Math.min(index0 ,index1 );
		int max =Math.max(index0 ,index1 );
		boolean changed =false ;
		if(min == minIndex && max == maxIndex){
			for(i=min; i<=max; i++){
				if(value.get(i) != true){
					changed = true;
					break;
				}
			}
		}else{
			changed = true;
		}
		if(changed){
			minIndex = min;
			maxIndex = max;
			clearSelectionImp();
			for(i=minIndex; i<=maxIndex; i++){
				value.put(i,  true);
			}
			fireListSelectionEvent(min, max, programmatic);
		}
	}

	public void  addSelectionInterval (int index0 ,int index1 ,boolean programmatic =true ){
		if (index0 < 0 || index1 < 0) {
			return;
		}
		if (getSelectionMode() == SINGLE_SELECTION) {
			setSelectionInterval(index0, index1);
			return;
		}
		updateLeadAnchorIndices(index0, index1);
		int min =Math.min(index0 ,index1 );
		int max =Math.max(index0 ,index1 );
		boolean changed =false ;
		for(int i =min ;i <=max ;i ++){
			if(value.get(i) != true){
				value.put(i,  true);
				changed = true;
			}
		}
		minIndex = Math.min(min, minIndex);
		maxIndex = Math.max(max, maxIndex);
		if(changed){
			fireListSelectionEvent(min, max, programmatic);
		}
	}

	public void  removeSelectionInterval (int index0 ,int index1 ,boolean programmatic =true ){
		if (index0 < 0 || index1 < 0) {
			return;
		}
		int min =Math.min(index0 ,index1 );
		int max =Math.max(index0 ,index1 );
		min = Math.max(min, minIndex);
		max = Math.min(max, maxIndex);
		if(min > max){
			return;
		}

		updateLeadAnchorIndices(index0, index1);

		if(min == minIndex && max == maxIndex){
			clearSelection();
			return;
		}else if(min > minIndex && max < maxIndex){
		}else if(min > minIndex && max == maxIndex){
			maxIndex = min - 1;
		}else{//min==minIndex && max<maxIndex
			minIndex = max + 1;
		}
		for(int i =min ;i <=max ;i ++){
			value.put(i,  undefined);
		}
		fireListSelectionEvent(min, max, programmatic);
	}

	public int  getMinSelectionIndex (){
		if(isSelectionEmpty()){
			return -1;
		}else{
			return minIndex;
		}
	}

	public int  getMaxSelectionIndex (){
		return maxIndex;
	}

	public boolean  isSelectedIndex (int index ){
		return value.get(index) == true;
	}

	private void  updateLeadAnchorIndices (int anchor ,int lead ){
		anchorIndex = anchor;
		leadIndex   = lead;
	}

	public int  getAnchorSelectionIndex (){
		return anchorIndex;
	}

	public void  setAnchorSelectionIndex (int index ){
		anchorIndex = index;
	}

	public int  getLeadSelectionIndex (){
		return leadIndex;
	}

	public void  setLeadSelectionIndex (int index ){
		leadIndex = index;
	}

	public void  clearSelection (boolean programmatic =true ){
		if(!isSelectionEmpty()){
			int max =maxIndex ;
			minIndex	= MAX;
			maxIndex	= MIN;
			clearSelectionImp();
			fireListSelectionEvent(0, max, programmatic);
		}
	}

	private void  clearSelectionImp (){
		value = new Array();
	}

	public boolean  isSelectionEmpty (){
		return minIndex > maxIndex;
	}

	public void  insertIndexInterval (int index ,int length ,boolean before ,boolean programmatic =true ){
		/* The first new index will appear at insMinIndex and the last
		 * one will appear at insMaxIndex
		 */
		int insMinIndex =(before )? index : index + 1;
		int insMaxIndex =(insMinIndex +length )-1;

		boolean needInstertArray =false ;

		if(isSelectionEmpty()){
			//need do nothing
		}else if(minIndex >= insMinIndex){
			minIndex += length;
			maxIndex += length;
			needInstertArray = true;
		}else if(maxIndex < insMinIndex){
			//do nothing
		}else if(insMinIndex > minIndex && insMinIndex <= maxIndex){
			maxIndex += length;
			needInstertArray = true;
		}

		if(needInstertArray){
			if(insMinIndex == 0){
				value = (new Array(length)).concat(value);
			}else{
				Array right =value.splice(insMinIndex );
				value = value.concat(new Array(length)).concat(right);
			}
		}

		int leadIn =leadIndex ;
		if (leadIn > index || (before && leadIn == index)) {
			leadIn = leadIndex + length;
		}
		int anchorIn =anchorIndex ;
		if (anchorIn > index || (before && anchorIn == index)) {
			anchorIn = anchorIndex + length;
		}
		if (leadIn != leadIndex || anchorIn != anchorIndex) {
			updateLeadAnchorIndices(anchorIn, leadIn);
		}

		if(needInstertArray){
			fireListSelectionEvent(insMinIndex, insMaxIndex+length, programmatic);
		}
	}

	public void  removeIndexInterval (int index0 ,int index1 ,boolean programmatic =true ){
		int rmMinIndex =Math.min(index0 ,index1 );
		int rmMaxIndex =Math.max(index0 ,index1 );
		int gapLength =(rmMaxIndex -rmMinIndex )+1;

		boolean needFireEvent =true ;
		int i ;

		if(isSelectionEmpty()){
			//need do nothing
			needFireEvent = false;
		}else if(minIndex >= rmMinIndex && maxIndex <= rmMaxIndex){
			minIndex	= MAX;
			maxIndex	= MIN;
			clearSelectionImp();
		}else if(maxIndex < rmMinIndex){
			value.splice(rmMinIndex, gapLength);
		}else if(minIndex > rmMaxIndex){
			value.splice(rmMinIndex, gapLength);
			minIndex -= gapLength;
			maxIndex -= gapLength;
		}else if(minIndex < rmMinIndex && maxIndex >= rmMinIndex && maxIndex <= rmMaxIndex){
			value.splice(rmMinIndex, gapLength);
			for(i = rmMinIndex-1; i>=minIndex; i--){
				maxIndex = i;
				if(value.get(i) == true){
					break;
				}
			}
		}else if(minIndex >= rmMinIndex && maxIndex > rmMaxIndex){
			value.splice(rmMinIndex, gapLength);
			maxIndex -= gapLength;
			for(i = rmMinIndex-1; i<=maxIndex; i++){
				minIndex = i;
				if(value.get(i) == true){
					break;
				}
			}
		}else if(minIndex < rmMinIndex && maxIndex > rmMaxIndex){
			value.splice(rmMinIndex, gapLength);
			maxIndex -= gapLength;
		}else{
			needFireEvent = false;
		}

		int leadIn =leadIndex ;
		if (leadIn == 0 && rmMinIndex == 0) {
			// do nothing
		} else if (leadIn > rmMaxIndex) {
			leadIn = leadIndex - gapLength;
		} else if (leadIn >= rmMinIndex) {
			leadIn = rmMinIndex - 1;
		}

		int anchorIn =anchorIndex ;
		if (anchorIn == 0 && rmMinIndex == 0) {
			// do nothing
		} else if (anchorIn > rmMaxIndex) {
			anchorIn = anchorIndex - gapLength;
		} else if (anchorIn >= rmMinIndex) {
			anchorIn = rmMinIndex - 1;
		}

		if (leadIn != leadIndex || anchorIn != anchorIndex) {
			updateLeadAnchorIndices(anchorIn, leadIn);
		}

		if(needFireEvent){
			fireListSelectionEvent(rmMinIndex, rmMaxIndex+gapLength, programmatic);
		}
	}

	/**
	 * Sets the selection mode.  The default is
	 * MULTIPLE_SELECTION.
	 * @param selectionMode  one of three values:
	 * <ul>
	 * <li>SINGLE_SELECTION
	 * <li>MULTIPLE_SELECTION
	 * </ul>
	 */
	public void  setSelectionMode (int selectionMode ){
		this.selectionMode = selectionMode;
	}

	public int  getSelectionMode (){
		return selectionMode;
	}

	public void  addListSelectionListener (Function listener ){
		addEventListener(SelectionEvent.LIST_SELECTION_CHANGED, listener);
	}

	public void  removeListSelectionListener (Function listener ){
		removeEventListener(SelectionEvent.LIST_SELECTION_CHANGED, listener);
	}

	protected void  fireListSelectionEvent (int firstIndex ,int lastIndex ,boolean programmatic ){
		dispatchEvent(new SelectionEvent(SelectionEvent.LIST_SELECTION_CHANGED, firstIndex, lastIndex, programmatic));
	}

	 public String  toString (){
		return "DefaultListSelectionModel[]";
	}
}


