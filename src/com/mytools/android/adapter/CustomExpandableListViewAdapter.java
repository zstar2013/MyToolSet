package com.mytools.android.adapter;

import java.util.ArrayList;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;

public abstract class CustomExpandableListViewAdapter extends BaseExpandableListAdapter{
	private Context mContext;
	private Resources res;
	private ExpandableListView mEplv;
	Handler handler;
	private ArrayList<String> checkChanges;
	private ArrayList<String> groupcheckChanges;
	private String separator =",";
	 //设置组视图的图片
    String[] logos ;
    
    private boolean isMultiExpandable=false;
	
	public CustomExpandableListViewAdapter(Context context,ExpandableListView eplv) {  
        mContext=context;
        res=mContext.getResources();
		handler = new Handler(){  
  
            @Override  
            public void handleMessage(Message msg) {  
              notifyDataSetChanged();  
              super.handleMessage(msg);  
            }  
        };  
        checkChanges=new ArrayList<String>();
		groupcheckChanges=new ArrayList<String>();
		mEplv=eplv;
    } 
   
    
    /**
     * 控制只有一项被展开
     */
    @Override
    public void onGroupExpanded(int groupPosition) {
    	super.onGroupExpanded(groupPosition);
    	if(isMultiExpandable){
    		for (int i = 0; i < getGroupCount(); i++) {
    	         // ensure only one expanded Group exists at every time
    	         if (groupPosition != i && mEplv.isGroupExpanded(groupPosition)) {
    	        	 mEplv.collapseGroup(i);
    	            }
    	         }
    	}
    }
    
    /**
     * 判断该组是否被选中
     * @param gourpId
     * @return
     */
    public boolean isGroupCheck(int gourpId){
    	String selection=String.valueOf(gourpId);
    	return groupcheckChanges.contains(selection);
    	
    }
    
    public boolean isChildCheck(int groupId,int childId){
        String selection=String.valueOf(groupId)+"#"+String.valueOf(childId);

    	return checkChanges.contains(selection);
    	
    }
    
    
    /**
     * 设置是否允许多项展开
     * @param bl
     */
    public void setMulitExpandable(boolean bl){
    	this.isMultiExpandable=bl;
    }
    
    /**
     * 点击child中的checkbox
     * @param grouppostion
     * @param position
     */
    void updateCheck(int grouppostion,int position,ImageView view){
    	String selection=String.valueOf(grouppostion)+"#"+String.valueOf(position);
    	if(checkChanges.contains(selection)){
    		checkChanges.remove(selection);
    		//if(view!=null)
    		//view.setImageResource(R.drawable.btn_checkbox_false);
    		//如果child的父项为勾选,则取消该项全选状态
    		if(groupcheckChanges.contains(String.valueOf(grouppostion))){
    			groupcheckChanges.remove(String.valueOf(grouppostion));
    		}
    		refresh();
    	}else{
    		checkChanges.add(selection);
    		//if(view!=null)
    		//view.setImageResource(R.drawable.btn_checkbox_true);
    	}
    }
    
    /**
     * 点击group中的checkbox
     * @param grouppostion
     * @param position
     */
    void updateCheck(int grouppostion,ImageView view){
    	String selection=String.valueOf(grouppostion);
    	if(groupcheckChanges.contains(selection)){
    		groupcheckChanges.remove(selection);
    		//删除group中的所有选择项
    		for(int i=0;i<getChildrenCount(grouppostion);i++){
				String select=String.valueOf(grouppostion)+"#"+String.valueOf(i);
				if(checkChanges.contains(select)){
	        		checkChanges.remove(select);
	        	}
			}
    		//如果该项展开，那么用重新展开来刷新view
    		if(mEplv.isGroupExpanded(grouppostion)){
    			mEplv.collapseGroup(grouppostion);  
    			mEplv.expandGroup(grouppostion);  
    		}
    		//if(view!=null)
    		//view.setImageResource(R.drawable.btn_checkbox_false);
    	}else{
    		groupcheckChanges.add(selection);
    		//勾选group中的所有选择项
    		for(int i=0;i<getChildrenCount(grouppostion);i++){
				String select=String.valueOf(grouppostion)+"#"+String.valueOf(i);
				if(!checkChanges.contains(select)){
	        		checkChanges.add(select);
	        	}
			}
    		//如果该项展开，那么用重新展开来刷新view
    		if(mEplv.isGroupExpanded(grouppostion)){
    			mEplv.collapseGroup(grouppostion);  
    			mEplv.expandGroup(grouppostion);  
    		}
    		//if(view!=null)
    		//view.setImageResource(R.drawable.btn_checkbox_true);
    	}
    }
    
    public void refresh() {  
        handler.sendMessage(new Message());  
    } 
    

    
    //重写ExpandableListAdapter中的各个方法



    @Override
    public long getGroupId(int groupPosition) {
        // TODO Auto-generated method stub
        return groupPosition;
    }





    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return true;
    }
    
    

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition,
            int childPosition) {
        // TODO Auto-generated method stub
        return true;
    }
    
    public String getGroupCheckResult(){
    	StringBuilder sb=new StringBuilder();
    	for(String s:checkChanges){
    		sb.append(s);
    		sb.append(separator);
    	}
    	String result=sb.toString();
    	return result.substring(0,result.length()-1);
    }
    
    public String getItemCheckResult(){
    	StringBuilder sb=new StringBuilder();
    	for(String s:groupcheckChanges){
    		sb.append(s);
    		sb.append(separator);
    	}
    	String result=sb.toString();
    	return result.substring(0,result.length()-1);
    }


}
