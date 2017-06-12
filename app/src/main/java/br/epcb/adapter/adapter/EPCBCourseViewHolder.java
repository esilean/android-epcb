package br.epcb.adapter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.epcb.principal.principal.R;

/**
 * Created by Leandro Bevilaqua on 10/08/2016.
 */

public class EPCBCourseViewHolder extends RecyclerView.ViewHolder {
    protected TextView tvCourseName;
    protected TextView tvCourseTitle;
    protected TextView tvCourseDate;

    public EPCBCourseViewHolder(View view) {
        super(view);
        this.tvCourseName = (TextView) view.findViewById(R.id.tvCourseName);
        this.tvCourseTitle = (TextView) view.findViewById(R.id.tvCourseTitle);
        this.tvCourseDate = (TextView) view.findViewById(R.id.tvCourseDate);

    }
}
