package com.example.batterycheck;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyOwnHolder> {
    private ArrayList<app_data> apps_list;
    Context ctx;
    String data1[],data2[];
    int img[];

    public MyAdapter(Context ct){
        ctx=ct;
        apps_list=get_apps_list();
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
        LayoutInflater myInflator= LayoutInflater.from(ctx); //The way to get the object of layout inflater
        View myOwnView= myInflator.inflate(R.layout.app_list,parent,false);
        return new MyOwnHolder(myOwnView);
    }

    @Override
    //Data has been initialized and now the job is to bind this data to the correct source which
    //we already have prepared (dummy data)
    public void onBindViewHolder(@NonNull MyOwnHolder holder, int position) {

        if(apps_list.get(position)!=null){
            holder.app_name.setText(apps_list.get(position).name);
            holder.app_package_name.setText(apps_list.get(position).packages);
            holder.extra_text.setText(apps_list.get(position).name);
            holder.app_icon.setImageDrawable(apps_list.get(position).icon);
//            if(data1[position].equals("Rabbit")){
//                holder.itemView.setBackgroundColor(0xffffff00);
//            }
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
    public class MyOwnHolder extends RecyclerView.ViewHolder{
        TextView app_name,app_package_name, extra_text;
        ImageView app_icon;
        public MyOwnHolder(@NonNull View itemView) {
            super(itemView);
            app_name= (TextView)itemView.findViewById(R.id.app_name);
            app_package_name= (TextView)itemView.findViewById(R.id.app_package_name);
            extra_text= (TextView)itemView.findViewById(R.id.extra_info);
            app_icon= (ImageView)itemView.findViewById(R.id.app_icon);
        }
    }


    public class app_data {
        private String name;
        Drawable icon;
        private String packages;

        //        private String a; //declare more parameters
//        private int d; //declare more parameters
        public app_data(String name, Drawable icon, String packages) {   //}, String a, int d) {
            this.name = name;
            this.icon = icon;
            this.packages = packages;
//            this.a = a;
//            this.d = d;
        }

        public String getName() {
            return name;
        }

        public Drawable getIcon() {
            return icon;
        }

        public String getPackages() {
            return packages;
        }
    }

    private ArrayList<app_data> get_apps_list() {
        PackageManager pm = ctx.getPackageManager();
        ArrayList<app_data> apps = new ArrayList<app_data>();
        List<PackageInfo> packs = pm.getInstalledPackages(0);
        //List<PackageInfo> packs = getPackageManager().getInstalledPackages(PackageManager.GET_PERMISSIONS);
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            if ((notSystemApp(p))) {
                String appName = p.applicationInfo.loadLabel(pm).toString();
                Drawable icon = p.applicationInfo.loadIcon(pm);
                String packages = p.applicationInfo.packageName;
                apps.add(new app_data(appName, icon, packages));
            }
        }
        return apps;
    }

    private boolean notSystemApp(PackageInfo pkgInfo) {
        return (pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0;
    }

}

