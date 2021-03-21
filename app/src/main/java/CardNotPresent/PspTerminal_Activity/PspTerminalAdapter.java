package CardNotPresent.PspTerminal_Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import CardNotPresent.R;
import CardNotPresent.webservice.generate_links_inner_models.Model_Terminal_List;

import java.util.List;

public class PspTerminalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Model_Terminal_List> contactParentsList;
    private Context mContext;
    private List<Model_Terminal_List> contactParentsListFull;
    PspTerminalActivity pspTerminalActivity;

    PspTerminalAdapter(List<Model_Terminal_List> contactParentsList, Context mContext , PspTerminalActivity pspTerminalActivity) {
        this.contactParentsListFull = contactParentsList;
        this.contactParentsList = contactParentsList;
        this.mContext = mContext;
        this.pspTerminalActivity = pspTerminalActivity;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_persons_psp_terminals, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        final Model_Terminal_List modelTerminalList = contactParentsList.get(position);

        viewHolder.txtPspTerminalTerminal.setText(modelTerminalList.getTerminalId());
        viewHolder.constraintLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                pspTerminalActivity.generatePersonalPaymentLinkAfterTerminalClick(modelTerminalList);
            }
        });

    }

    @Override
    public int getItemCount() {
        return contactParentsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView
                txtPspTerminalTerminal;

        ConstraintLayout constraintLayout ;

        ViewHolder(View view) {
            super(view);

            txtPspTerminalTerminal = view.findViewById(R.id.actv_psp_terminal_terminal_list_item_terminal_id_body);
            constraintLayout = view.findViewById(R.id.actv_psp_terminal_item_main_layout);

        }
    }


}