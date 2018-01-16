package transport.school.com.schoolapp;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import frameworks.retrofit.imageloader.GlideImageLoaderImpl;
import transport.school.com.schoolapp.bean.Student;
/**
 * Created by Naveen.Goyal on 11/29/2017.
 */
public class StudentAttendanceAdapter extends RecyclerView.Adapter<StudentAttendanceAdapter.MyViewHolder> implements StickyRecyclerHeadersAdapter<StudentAttendanceAdapter.StudentListHeaderHolder> {
    private Context mContext;

    public void setStudentList(List<Student> studentList) {
        Collections.sort(studentList);
        this.studentList = studentList;
        notifyDataSetChanged();
    }

    private List<Student> studentList = new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.thumbnail)
        ImageView thumbnail;
        @BindView(R.id.student_name)
        TextView studentName;
        @BindView(R.id.card_view)
        CardView cardView;
        Student student;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(view.getContext(), MainActivity.class);
                    i.putExtra("student", student);
                    view.getContext().startActivity(i);
                }
            });
        }

        public void setStudent(Student student) {
            studentName.setText(student.getStudentname());
            new GlideImageLoaderImpl(mContext).loadImage(student.getProfilePic(), thumbnail, R.drawable.blank_person);
            this.student = student;
        }
    }

    public class StudentListHeaderHolder extends RecyclerView.ViewHolder {
        public StudentListHeaderHolder(View itemView) {
            super(itemView);
        }
    }

    public StudentAttendanceAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.attendance_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.setStudent(studentList.get(position));
    }

    @Override
    public long getHeaderId(int position) {
        return Long.parseLong(studentList.get(position).getStudentid());
    }

    @Override
    public StudentListHeaderHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_header, parent, false);
        return new StudentAttendanceAdapter.StudentListHeaderHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(StudentListHeaderHolder holder, int position) {
        TextView textView = (TextView) holder.itemView;
        textView.setText(String.valueOf(studentList.get(position).getStudentid()));
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }
}
