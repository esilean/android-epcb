package br.epcb.principal.principal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import br.epcb.adapter.adapter.EPCBCourseAdapter;
import br.epcb.adapter.adapter.RecyclerItemClickListener;
import br.epcb.api.volley.CustomJSONCall;
import br.epcb.entity.entity.EPCBCourse;
import br.epcb.entity.entity.EPCBModule;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static br.epcb.principal.principal.BaseActivity.COURSE_TRANSFER;
import static br.epcb.principal.principal.BaseActivity.MODULE_TRANSFER;

/**
 * Created by Leandro Bevilaqua on 10/08/2016.
 */

public class CourseActivityFragment extends Fragment {

    private static final String LOG_TAG = CourseActivityFragment.class.getSimpleName();
    private List<EPCBCourse> mEPCBCourseList = new ArrayList<EPCBCourse>();
    private RecyclerView mRecyclerView;
    private EPCBCourseAdapter epcbCourseAdapter;

    private String mModuleId;

    public CourseActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_course, container, false);

        Intent intent = getActivity().getIntent();
        EPCBModule epcbModule = (EPCBModule) intent.getSerializableExtra(MODULE_TRANSFER);
        mModuleId = epcbModule.getId();
        //Toast.makeText(getActivity(), "Id do : " + epcbModule.getName() + ": " + epcbModule.getId(), Toast.LENGTH_SHORT).show();

        epcbCourseAdapter = new EPCBCourseAdapter(getContext(), (new ArrayList<EPCBCourse>()));

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvEPCBCourse);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setAdapter(epcbCourseAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Toast.makeText(getActivity(), "Aula: " + epcbCourseAdapter.getCourse(position).getName(), Toast.LENGTH_SHORT).show();

                boolean locked = false;
                locked = epcbCourseAdapter.getLocked(position);

                if (locked) {
                    Intent intent = new Intent(getActivity(), VideoDescriptionActivity.class);
                    intent.putExtra(COURSE_TRANSFER, epcbCourseAdapter.getCourse(position));
                    startActivity(intent);
                } else {
                    //Toast.makeText(getActivity(), epcbCourseAdapter.getCourse(position).getName() + " não está liberada.", Toast.LENGTH_SHORT).show();
                    Snackbar.make(view, epcbCourseAdapter.getCourse(position).getName() + " - " + epcbCourseAdapter.getCourse(position).getEPCBModule().getName() + " não está liberada.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
                //Toast.makeText(getActivity(), "Long tap", Toast.LENGTH_SHORT).show();
            }
        }));

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Download de data
        ProcessEPCBCourse processEPCBCourse = new ProcessEPCBCourse(getContext());
        processEPCBCourse.execute("module/course/" + mModuleId, Request.Method.GET);

        //Log.v(LOG_TAG, "EPCBCourse processed.");
    }

    public class ProcessEPCBCourse extends CustomJSONCall {

        public ProcessEPCBCourse(Context context) {
            super(context);
        }

        @Override
        public void execute(String functionName, int method) {
            super.execute(functionName, method);
        }

        @Override
        public void cancel() {
            super.cancel();
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            super.onErrorResponse(error);
        }

        @Override
        public void onResponse(Object response) {
            super.onResponse(response);

            convertJSONToObject(response);
            epcbCourseAdapter.loadNewData(mEPCBCourseList);
        }

        private void convertJSONToObject(Object response) {
            try {

                final String EPCB_ID = "id";
                final String EPCB_NAME = "name";
                final String EPCB_TITLE = "title";
                final String EPCB_DESCRIPTION = "description";
                final String EPCB_LOCKEDDAYS = "lockedDays";
                final String EPCB_URLOTHERVIDEO = "urlOtherVideo";
                final String EPCB_ORDER = "order";
                final String EPCB_ACTIVE = "active";

                final String EPCB_COURSES = "courses";

                JSONArray itemsArray = (JSONArray) response;

                EPCBCourse epcbCourse;
                EPCBModule epcbModule;
                for (int i = 0; i < itemsArray.length(); i++) {
                    JSONObject jsonEPCBModule = itemsArray.getJSONObject(i);
                    JSONObject jsonEPCBCourse = jsonEPCBModule.getJSONArray(EPCB_COURSES).getJSONObject((0));

                    epcbCourse = new EPCBCourse();

                    epcbModule = new EPCBModule();
                    epcbModule.setId(jsonEPCBModule.getString(EPCB_ID));
                    epcbModule.setName(jsonEPCBModule.getString(EPCB_NAME));
                    epcbCourse.setEPCBModule(epcbModule);


                    epcbCourse.setId(jsonEPCBCourse.getString(EPCB_ID));
                    epcbCourse.setName(jsonEPCBCourse.getString(EPCB_NAME));
                    epcbCourse.setTitle(jsonEPCBCourse.getString(EPCB_TITLE));
                    epcbCourse.setDescription(jsonEPCBCourse.getString(EPCB_DESCRIPTION));
                    epcbCourse.setLockedDays(jsonEPCBCourse.getInt(EPCB_LOCKEDDAYS));
                    epcbCourse.setURLOtherVideo(jsonEPCBCourse.getString(EPCB_URLOTHERVIDEO));
                    epcbCourse.setOrder(jsonEPCBCourse.getInt(EPCB_ORDER));
                    epcbCourse.setActive(jsonEPCBCourse.getBoolean(EPCB_ACTIVE));
                    mEPCBCourseList.add(epcbCourse);
                }

            } catch (Exception jsone) {
                //Log.e(LOG_TAG, "JSON Error: > " + jsone.getMessage());
            }
        }
    }

}
