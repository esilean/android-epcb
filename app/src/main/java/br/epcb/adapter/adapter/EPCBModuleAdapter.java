package br.epcb.adapter.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.epcb.entity.entity.EPCBModule;
import br.epcb.principal.principal.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EPCBModuleAdapter extends RecyclerView.Adapter<EPCBModuleViewHolder> {

    SharedPreferences prefs;

    private final static String LOG_TAG = EPCBModuleAdapter.class.getSimpleName();
    List<EPCBModule> mEPCBModuleList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    //private float scale;
    //private int width, heith, roundPixels;

    public EPCBModuleAdapter(Context context, List<EPCBModule> epcbModuleList) {
        this.mContext = context;
        this.mEPCBModuleList = epcbModuleList;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //scale = mContext.getResources().getDisplayMetrics().density;
        // width = mContext.getResources().getDisplayMetrics().widthPixels - (int) (14 * scale + 0.5f);
        //heith = (width / 16) * 9;

        //roundPixels = (int) (2 * scale + 0.5f);

        prefs = PreferenceManager.getDefaultSharedPreferences(this.mContext);

    }

    @Override
    public EPCBModuleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.content_epbc_vh, parent, false);
        EPCBModuleViewHolder epcbContentViewHolder = new EPCBModuleViewHolder(view);
        return epcbContentViewHolder;
    }

    @Override
    public void onBindViewHolder(EPCBModuleViewHolder holder, int position) {
        EPCBModule epcbModule = mEPCBModuleList.get(position);
        String sCreationDate = prefs.getString("CD_EPCB", "");

        holder.tvModuleName.setText(epcbModule.getName());
        holder.tvModuleDescription.setText(epcbModule.getDescription());

        try {
            //get date today
            Calendar cNow = Calendar.getInstance();

            //get creation date from the userapp logged in
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateCreationDate = sdf.parse(sCreationDate);

            //get locked date for module
            Calendar cLockedDate = Calendar.getInstance();
            cLockedDate.setTime(dateCreationDate);
            cLockedDate.add(Calendar.DAY_OF_YEAR, epcbModule.getLockedDays());

            //Log.i(LOG_TAG, "Modulo: " + epcbModule.getName() + " Date Cadastro: " + dateCreationDate + " Data Atual: " + cNow.getTime() + " Data de Liberacao: " + cLockedDate.getTime());

            //validate module for locked days
            if (cLockedDate.getTimeInMillis() <= cNow.getTimeInMillis()) {
                holder.tvModuleDate.setTextColor(mContext.getResources().getColor(R.color.colorGreen));
                epcbModule.setLocked(true);
            } else {
                holder.tvModuleDate.setTextColor(mContext.getResources().getColor(R.color.colorRed));
                epcbModule.setLocked(false);
            }

            //format module date
            SimpleDateFormat sdfLockedDate = new SimpleDateFormat("dd/MM/yyyy");
            String formattedLockedDate = sdfLockedDate.format(cLockedDate.getTime());
            holder.tvModuleDate.setText(formattedLockedDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        //Log.i(LOG_TAG, "Data de Atual: " + c.getTime());
        //Log.i(LOG_TAG, "Data de Cadastro: " + sCreationDate);


//        ControllerListener listener = new BaseControllerListener() {
//            @Override
//            public void onSubmit(String id, Object callerContext) {
//                super.onSubmit(id, callerContext);
//                Log.i(LOG_TAG, "onSubmit");
//            }
//
//            @Override
//            public void onFinalImageSet(String id, Object imageInfo, Animatable animatable) {
//                super.onFinalImageSet(id, imageInfo, animatable);
//                Log.i(LOG_TAG, "onFinalImageSet");
//            }
//
//            @Override
//            public void onIntermediateImageSet(String id, Object imageInfo) {
//                super.onIntermediateImageSet(id, imageInfo);
//                Log.i(LOG_TAG, "onIntermediateImageSet");
//            }
//
//            @Override
//            public void onIntermediateImageFailed(String id, Throwable throwable) {
//                super.onIntermediateImageFailed(id, throwable);
//                Log.i(LOG_TAG, "onIntermediateImageFailed");
//            }
//
//            @Override
//            public void onFailure(String id, Throwable throwable) {
//                super.onFailure(id, throwable);
//                Log.i(LOG_TAG, "onFailure");
//            }
//
//            @Override
//            public void onRelease(String id) {
//                super.onRelease(id);
//                Log.i(LOG_TAG, "onRelease");
//            }
//        };
//
//        Uri uri = Uri.parse(epcbContent.getUrlImageCard());
//        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
//                .setUri(uri)
//                .setControllerListener(listener)
//                .setOldController(holder.imgvCard.getController())
//                .build();
//
//        RoundingParams roundingParams = RoundingParams.fromCornersRadii(roundPixels, roundPixels, 0, 0);
//
//        holder.imgvCard.setController(draweeController);
//        holder.imgvCard.getHierarchy().setRoundingParams(roundingParams);
//

    }

    @Override
    public int getItemCount() {
        return (mEPCBModuleList == null) ? 0 : mEPCBModuleList.size();
    }

    public EPCBModule getModule(int position) {
        return (null != mEPCBModuleList ? mEPCBModuleList.get(position) : null);
    }

    public boolean getLocked(int position)
    {
        return (null != mEPCBModuleList ? mEPCBModuleList.get(position).getLocked() : false);
    }

    public void loadNewData(List<EPCBModule> epcbModuleList) {
        this.mEPCBModuleList = epcbModuleList;
        notifyDataSetChanged();
    }


}
