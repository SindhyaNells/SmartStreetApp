package com.sjsu.assignment.smartstreet.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.sjsu.assignment.smartstreet.R;
import com.sjsu.assignment.smartstreet.data.ImageFileItem;

import java.util.ArrayList;

/**
 * Created by sindhya on 9/19/17.
 */
public class PhotoListAdapter extends ArrayAdapter {


    private Context mContext;
    private int layoutResourceId;
    private ArrayList<ImageFileItem> img_list = new ArrayList();

    public PhotoListAdapter(Context context, int layoutResourceId, ArrayList<ImageFileItem> imgList) {
        super(context, layoutResourceId, imgList);
        this.layoutResourceId = layoutResourceId;
        this.mContext = context;
        this.img_list = imgList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.img = (ImageView) row.findViewById(R.id.img_view_photo);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        ImageFileItem imageFileItem=img_list.get(position);
        Bitmap img = imageFileItem.getImgFile();
        holder.img.setImageBitmap(img);
        return row;
    }

    static class ViewHolder {
        ImageView img;
    }
}
