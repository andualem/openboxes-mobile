package org.health.supplychain.view;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.health.supplychain.R;

import org.health.supplychain.service.ISyncMetadataService;
import org.health.supplychain.service.SyncMetadataService;
import org.health.supplychain.utility.Utils;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by Aworkneh on 4/17/2018.
 */

public class CustomSyncItemAdapter extends SimpleAdapter {

    private static final String KEY_IMAGE = "image", KEY_TITLE = "title", KEY_DETAIL = "detail", KEY_GROUP = "group";;
    private List<WeakHashMap<String, String>>  mDisplayedValues;
    private LayoutInflater inflater;
    private Context context;
    private ISyncMetadataService syncMetadataService;



    public CustomSyncItemAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.context = context;
        mDisplayedValues = (List<WeakHashMap<String, String>>) data;
        inflater = LayoutInflater.from(context);
        syncMetadataService = new SyncMetadataService();
    }

    @Override
    public int getCount() {
        return mDisplayedValues != null? mDisplayedValues.size():0;
    }

    @Override
    public Object getItem(int position) {
        return mDisplayedValues != null?mDisplayedValues.get(position):null;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        super.getView(position, convertView, parent);
        ImageView imageView;
        TextView txtViewTitle;
        TextView txtViewDetail;

        if(convertView==null){
            convertView = inflater.inflate(R.layout.two_item_status, null);
        }

        txtViewTitle = (TextView) convertView.findViewById(R.id.title);
        txtViewDetail = (TextView) convertView.findViewById(R.id.detail);
        imageView = (ImageView) convertView.findViewById(R.id.ivSyncStatus);


        if(mDisplayedValues != null && mDisplayedValues.size() > position) {
            String unsyncedIconPath = Utils.getURLForResource(R.drawable.sync_red);
            String syncedIconPath = Utils.getURLForResource(R.drawable.sync_green);
            boolean hasUnsyncedData = groupHasUnsyncedData(Integer.valueOf(mDisplayedValues.get(position).get(KEY_GROUP)));

            txtViewTitle.setText(mDisplayedValues.get(position).get(KEY_TITLE));
            txtViewDetail.setText(mDisplayedValues.get(position).get(KEY_DETAIL));
            if(hasUnsyncedData)
                imageView.setImageURI(Uri.parse(unsyncedIconPath));
            else
                imageView.setImageURI(Uri.parse(syncedIconPath));
        }

        return convertView;
    }

    private boolean groupHasUnsyncedData(int groupId){
        return true;
//        return syncMetadataService.groupHasUnsyncedData(groupId);
    }


}
