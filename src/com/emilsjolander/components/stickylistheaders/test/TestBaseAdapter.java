package com.emilsjolander.components.stickylistheaders.test;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.emilsjolander.components.stickylistheaders.StickyListHeadersAdapter;


/**
 * @author Emil Sj�lander
 *
Copyright 2012 Emil Sj�lander

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
public class TestBaseAdapter extends BaseAdapter implements StickyListHeadersAdapter, SectionIndexer{

	private String[] countries;
	private ArrayList<String> sections;
	private LayoutInflater inflater;

	public TestBaseAdapter(Context context) {
		inflater = LayoutInflater.from(context);
		countries = context.getResources().getStringArray(R.array.countries);
		sections = new ArrayList<String>();
		for(String s : countries){
			if(!sections.contains(""+s.charAt(0))){
				sections.add(""+s.charAt(0));
			}
		}
	}

	@Override
	public int getCount() {
		return countries.length;
	}

	@Override
	public Object getItem(int position) {
		return countries[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.test_list_item_layout, parent, false);
			holder.text = (TextView) convertView.findViewById(R.id.text);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.text.setText(countries[position]);

		return convertView;
	}

	@Override public View getHeaderView(int position, View convertView, ViewGroup parent) {
		HeaderViewHolder holder;
		if (convertView == null) {
			holder = new HeaderViewHolder();
			convertView = inflater.inflate(R.layout.header, parent, false);
			holder.text = (TextView) convertView.findViewById(R.id.text);
			convertView.setTag(holder);
		} else {
			holder = (HeaderViewHolder) convertView.getTag();
		}
		//set header text as first char in name
		String headerText = "" + countries[position].charAt(0);
		holder.text.setText(headerText);
		return convertView;
	}

	//remember that these have to be static, postion=1 should always return the same Id that is.
	@Override
	public long getHeaderId(int position) {
		//return the first character of the country as ID because this is what headers are based upon
		return countries[position].charAt(0);
	}

	class HeaderViewHolder {
		TextView text;
	}

	class ViewHolder {
		TextView text;
	}

	@Override
	public int getPositionForSection(int section) {
		if(section >= sections.size()){
			section = sections.size()-1;
		}else if(section < 0){
			section = 0;
		}
		
		int position = 0;
		char sectionChar = sections.get(section).charAt(0);
		for(int i = 0 ; i<countries.length ; i++){
			if(sectionChar == countries[i].charAt(0)){
				position = i;
				break;
			}
		}
		return position;
	}

	@Override
	public int getSectionForPosition(int position) {
		if(position >= countries.length){
			position = countries.length-1;
		}else if(position < 0){
			position = 0;
		}
		
		return sections.indexOf(""+countries[position].charAt(0));
	}

	@Override
	public Object[] getSections() {
		return sections.toArray(new String[sections.size()]);
	}
	
}
