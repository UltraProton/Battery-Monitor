package com.example.batterycheck;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyOwnHolder> {
    public ArrayList<MainActivity.app_data> apps_list;
    Context ctx;
    String data1[], data2[];
    int img[];

    public MyAdapter(Context ct,ArrayList<MainActivity.app_data> apps_list) {
        ctx = ct;
        this.apps_list = apps_list;
    }

    /*Below method is the place where we will need the row data.
    On create view holder is the first function to be called when the control reaches adapter
    LayoutInflater
     */
    @NonNull
    @Override
    public MyOwnHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*
        Inflater classes are capable of inflating your xml file to your java file
         */
        LayoutInflater myInflator = LayoutInflater.from(ctx); //The way to get the object of layout inflater
        View myOwnView = myInflator.inflate(R.layout.app_list, parent, false);
        return new MyOwnHolder(myOwnView);
    }

    @Override
    //Data has been initialized and now the job is to bind this data to the correct source which
    //we already have prepared (dummy data)
    public void onBindViewHolder(@NonNull MyOwnHolder holder, int position) {

        if (apps_list.get(position) != null) {
            holder.app_name.setText(apps_list.get(position).getName());
            holder.app_package_name.setText(apps_list.get(position).getPackages());
            holder.extra_text.setText(apps_list.get(position).getName());
            holder.app_icon.setImageDrawable(apps_list.get(position).icon);
        }
    }

    @Override
    public int getItemCount() {
        return apps_list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    /*
        After the onCreateViewHolder() the control reaches the MyOwnHolder constructor as a new object
        of the MyOwnHolder is returned by that function
         */
    public class MyOwnHolder extends RecyclerView.ViewHolder {
        TextView app_name, app_package_name, extra_text;
        ImageView app_icon;

        public MyOwnHolder(@NonNull View itemView) {
            super(itemView);
            app_name = (TextView) itemView.findViewById(R.id.app_name);
            app_package_name = (TextView) itemView.findViewById(R.id.app_package_name);
            extra_text = (TextView) itemView.findViewById(R.id.extra_info);
            app_icon = (ImageView) itemView.findViewById(R.id.app_icon);
        }

    }
}
