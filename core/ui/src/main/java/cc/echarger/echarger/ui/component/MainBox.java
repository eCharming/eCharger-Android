package cc.echarger.echarger.ui.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cc.echarger.echarger.model.vo.ChargerVo;
import cc.echarger.echarger.ui.R;
import cc.echarger.echarger.ui.adapter.ChargerListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainBox extends ConstraintLayout {

    private final List<ChargerVo> chargerVoList = new ArrayList<>();
    private ChargerListAdapter adapter;

    public MainBox(@NonNull Context context) {
        super(context);
    }

    public MainBox(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.main_box, this, true);
        RecyclerView recyclerView = findViewById(R.id.recycle_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ChargerListAdapter(chargerVoList);
        recyclerView.setAdapter(adapter);
    }

    public ChargerListAdapter getAdapter() {
        return adapter;
    }

    public List<ChargerVo> getChargerVoList() {
        return chargerVoList;
    }

}
