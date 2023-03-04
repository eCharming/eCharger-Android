package cc.echarger.echarger.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import cc.echarger.echarger.model.vo.ChargerVo;
import cc.echarger.echarger.ui.R;
import cc.echarger.echarger.ui.databinding.ChargerBinding;

import java.util.List;

public class ChargerListAdapter extends RecyclerView.Adapter<ChargerListAdapter.ViewHolder> {

    private final List<ChargerVo> mChargerVoList;

    static class ViewHolder extends RecyclerView.ViewHolder {

        final ChargerBinding binding;

        public ChargerBinding getBinding() {
            return binding;
        }

        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

    }

    public ChargerListAdapter(List<ChargerVo> mChargerVoList) {
        this.mChargerVoList = mChargerVoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.charger, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChargerVo chargerVo = mChargerVoList.get(position);
        holder.binding.setChargerVo(chargerVo);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mChargerVoList.size();
    }
}
