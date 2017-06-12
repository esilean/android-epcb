package br.epcb.adapter.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.epcb.entity.entity.EPCBCourse;
import br.epcb.principal.principal.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Leandro Bevilaqua on 10/08/2016.
 */

public class EPCBCourseAdapter extends RecyclerView.Adapter<EPCBCourseViewHolder> {

    SharedPreferences prefs;

    private final static String LOG_TAG = EPCBCourseAdapter.class.getSimpleName();
    List<EPCBCourse> mEPCBCourseList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public EPCBCourseAdapter(Context context, List<EPCBCourse> epcbCourseList) {
        this.mContext = context;
        this.mEPCBCourseList = epcbCourseList;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        prefs = PreferenceManager.getDefaultSharedPreferences(this.mContext);

    }

    @Override
    public EPCBCourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.content_epcb_course_vh, parent, false);
        EPCBCourseViewHolder epcbCourseViewHolder = new EPCBCourseViewHolder(view);
        return epcbCourseViewHolder;
    }

    @Override
    public void onBindViewHolder(EPCBCourseViewHolder holder, int position) {
        EPCBCourse epcbCourse = mEPCBCourseList.get(position);
        String sCreationDate = prefs.getString("CD_EPCB", "");

        holder.tvCourseName.setText(epcbCourse.getEPCBModule().getName() + " - " + epcbCourse.getName());
        holder.tvCourseTitle.setText(epcbCourse.getTitle());

        try {
            //get date today
            Calendar cNow = Calendar.getInstance();

            //get creation date from the userapp logged in
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateCreationDate = sdf.parse(sCreationDate);

            //get locked date for module
            Calendar cLockedDate = Calendar.getInstance();
            cLockedDate.setTime(dateCreationDate);
            cLockedDate.add(Calendar.DAY_OF_YEAR, epcbCourse.getLockedDays());

            //Log.i(LOG_TAG, "Modulo: " + epcbModule.getName() + " Date Cadastro: " + dateCreationDate + " Data Atual: " + cNow.getTime() + " Data de Liberacao: " + cLockedDate.getTime());

            //validate module for locked days
            if (cLockedDate.getTimeInMillis() <= cNow.getTimeInMillis()) {
                holder.tvCourseDate.setTextColor(mContext.getResources().getColor(R.color.colorGreen));
                epcbCourse.setLocked(true);
            } else {
                holder.tvCourseDate.setTextColor(mContext.getResources().getColor(R.color.colorRed));
                epcbCourse.setLocked(false);
            }

            //format module date
            SimpleDateFormat sdfLockedDate = new SimpleDateFormat("dd/MM/yyyy");
            String formattedLockedDate = sdfLockedDate.format(cLockedDate.getTime());
            holder.tvCourseDate.setText(formattedLockedDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return (mEPCBCourseList == null) ? 0 : mEPCBCourseList.size();
    }

    public EPCBCourse getCourse(int position) {
        return (null != mEPCBCourseList ? mEPCBCourseList.get(position) : null);
    }

    public boolean getLocked(int position) {
        return (null != mEPCBCourseList ? mEPCBCourseList.get(position).getLocked() : false);
    }

    public void loadNewData(List<EPCBCourse> epcbCourseList) {
        this.mEPCBCourseList = epcbCourseList;
        notifyDataSetChanged();
    }

}
