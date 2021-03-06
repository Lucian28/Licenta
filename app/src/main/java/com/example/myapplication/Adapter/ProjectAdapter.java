package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MessageActivity;
import com.example.myapplication.Model.Project;
import com.example.myapplication.ProjectActivity;
import com.example.myapplication.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {

    private final Context mContext;
    private final List<Project> mProjects;


    public ProjectAdapter(Context mContext, List<Project> mProjects){
        this.mContext = mContext;
        this.mProjects = mProjects;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.project_item, parent, false);
        return new ProjectAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final Project project = mProjects.get(position);

        String date = project.getStartDate()+" - "+project.getEndDate();

        holder.projectname.setText(project.getName());
        holder.Date.setText(date);
        holder.status.setText(project.getStatus());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProjectActivity.class);
                intent.putExtra("projectid", project.getId());
                intent.putExtra("projectName", project.getName());
                intent.putExtra("projectDescriere", project.getDescriere());
                mContext.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return mProjects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView projectname;
        public CircleImageView userimage;
        public TextView Date;
        public TextView status;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            projectname = itemView.findViewById(R.id.projectname);
            userimage = itemView.findViewById(R.id.image);
            Date = itemView.findViewById(R.id.Date);
            status = itemView.findViewById(R.id.status);
        }
    }
}
