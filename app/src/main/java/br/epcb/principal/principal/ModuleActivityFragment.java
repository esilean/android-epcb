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
import br.epcb.adapter.adapter.EPCBModuleAdapter;
import br.epcb.adapter.adapter.RecyclerItemClickListener;
import br.epcb.api.volley.CustomJSONCall;
import br.epcb.entity.entity.EPCBModule;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static br.epcb.principal.principal.BaseActivity.MODULE_TRANSFER;

/**
 * A placeholder fragment containing a simple view.
 */
public class ModuleActivityFragment extends Fragment {

    private static final String LOG_TAG = ModuleActivityFragment.class.getSimpleName();
    private List<EPCBModule> mEPCBModuleList = new ArrayList<EPCBModule>();
    private RecyclerView mRecyclerView;
    private EPCBModuleAdapter epcbModuleAdapter;


    public ModuleActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        epcbModuleAdapter = new EPCBModuleAdapter(getContext(), (new ArrayList<EPCBModule>()));

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvEPCBModule);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setAdapter(epcbModuleAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Toast.makeText(getActivity(), "Módulo: " + epcbModuleAdapter.getModule(position).getName(), Toast.LENGTH_SHORT).show();

                boolean locked = false;
                locked = epcbModuleAdapter.getLocked(position);

                if(locked) {
                    Intent intent = new Intent(getActivity(), CourseActivity.class);
                    intent.putExtra(MODULE_TRANSFER, epcbModuleAdapter.getModule(position));
                    startActivity(intent);
                }
                else
                {
                    //Toast.makeText(getActivity(), epcbModuleAdapter.getModule(position).getName() + " não está liberado.", Toast.LENGTH_SHORT).show();
                    Snackbar.make(view, epcbModuleAdapter.getModule(position).getName() + " não está liberado.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
             //   Toast.makeText(getActivity(), "Long tap", Toast.LENGTH_SHORT).show();
            }
        }));

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Download de data
        ProcessEPCBModule processEPCBModule = new ProcessEPCBModule(getContext());
        processEPCBModule.execute("module", Request.Method.GET);

        //Log.v(LOG_TAG, "EPCBModule processed.");

    }

    public class ProcessEPCBModule extends CustomJSONCall {

        public ProcessEPCBModule(Context context) {
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
            epcbModuleAdapter.loadNewData(mEPCBModuleList);
        }

        private void convertJSONToObject(Object response) {
            try {

                final String EPCB_ID = "id";
                final String EPCB_NAME = "name";
                final String EPCB_DESCRIPTION = "description";
                final String EPCB_LOCKEDDAYS = "lockedDays";
                final String EPCB_ORDER = "order";
                final String EPCB_ACTIVE = "active";

                JSONArray itemsArray = (JSONArray) response;

                EPCBModule epcbModule;
                for (int i = 0; i < itemsArray.length(); i++) {
                    JSONObject jsonEPCB = itemsArray.getJSONObject(i);

                    epcbModule = new EPCBModule();
                    epcbModule.setId(jsonEPCB.getString(EPCB_ID));
                    epcbModule.setName(jsonEPCB.getString(EPCB_NAME));
                    epcbModule.setDescription(jsonEPCB.getString(EPCB_DESCRIPTION));
                    epcbModule.setLockedDays(jsonEPCB.getInt(EPCB_LOCKEDDAYS));
                    epcbModule.setOrder(jsonEPCB.getInt(EPCB_ORDER));
                    epcbModule.setActive(jsonEPCB.getBoolean(EPCB_ACTIVE));
                    mEPCBModuleList.add(epcbModule);
                }

            } catch (Exception jsone) {
                //Log.e(LOG_TAG, "JSON Error: > " + jsone.getMessage());
            }
        }
    }

}
