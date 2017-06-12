package br.epcb.principal.principal;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import br.epcb.entity.entity.EPCBCourse;

import static br.epcb.principal.principal.BaseActivity.COURSE_TRANSFER;
import static br.epcb.principal.principal.BaseActivity.VIDEO_TRANSFER;

/**
 * Created by Leandro Bevilaqua on 10/08/2016.
 */

public class VideoDescriptionActivityFragment extends Fragment {

    private static final String LOG_TAG = VideoDescriptionActivityFragment.class.getSimpleName();

    private TextView tvCourseNameVideo;
    private TextView tvCourseTitleVideo;
    private TextView tvCourseDescriptionVideo;
    private ImageButton imgBtPlay;

    private EPCBCourse mEPCBCourse;

    public VideoDescriptionActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_video_description, container, false);

        Intent intent = getActivity().getIntent();
        mEPCBCourse = (EPCBCourse) intent.getSerializableExtra(COURSE_TRANSFER);

        //Toast.makeText(getActivity(), "Aula: " + epcbCourse.getName(), Toast.LENGTH_SHORT).show();

        tvCourseNameVideo = (TextView) view.findViewById(R.id.tvCourseNameVideo);
        tvCourseTitleVideo = (TextView) view.findViewById(R.id.tvCourseTitleVideo);
        tvCourseDescriptionVideo = (TextView) view.findViewById(R.id.tvCourseDescriptionVideo);

        tvCourseNameVideo.setText(mEPCBCourse.getName() + " - " + mEPCBCourse.getEPCBModule().getName());
        tvCourseTitleVideo.setText(mEPCBCourse.getTitle());
        tvCourseDescriptionVideo.setText(mEPCBCourse.getDescription());

        imgBtPlay = (ImageButton) view.findViewById(R.id.imgBtPlay);
        imgBtPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VideoDetailActivity.class);
                intent.putExtra(VIDEO_TRANSFER, mEPCBCourse);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }
}
