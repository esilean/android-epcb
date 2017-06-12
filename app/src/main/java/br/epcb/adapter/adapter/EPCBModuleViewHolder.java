package br.epcb.adapter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.epcb.principal.principal.R;

public class EPCBModuleViewHolder extends RecyclerView.ViewHolder{
    protected TextView tvModuleName;
    protected TextView tvModuleDescription;
    protected TextView tvModuleDate;

    public EPCBModuleViewHolder(View view) {
        super(view);
        this.tvModuleName = (TextView) view.findViewById(R.id.tvModuleName);
        this.tvModuleDescription = (TextView) view.findViewById(R.id.tvModuleDescription);
        this.tvModuleDate = (TextView) view.findViewById(R.id.tvModuleDate);

    }

}
