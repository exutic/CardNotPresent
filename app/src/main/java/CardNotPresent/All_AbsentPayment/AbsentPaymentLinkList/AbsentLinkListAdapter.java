package CardNotPresent.All_AbsentPayment.AbsentPaymentLinkList;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import CardNotPresent.Home.HomePanel;
import CardNotPresent.R;

import java.util.ArrayList;
import java.util.List;

public class AbsentLinkListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MerchantModel> contactParentsList;
    private Context mContext;

    ArrayList<Integer> arrayList = new ArrayList<>();

    HomePanel personalPanel_activityTemp;

    AbsentLinkListAdapter(List<MerchantModel> contactParentsList, Context mContext) {
        this.contactParentsList = contactParentsList;
        this.mContext = mContext;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_raw_link, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        final MerchantModel merchantModelTest = contactParentsList.get(position);

        onBindActions(viewHolder, merchantModelTest, position);

    }

    @Override
    public int getItemCount() {
        return contactParentsList.size();
    }

    public void filterList(List<MerchantModel> filteredList) {
        contactParentsList = filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView
                txtPspName,
                txtPspType,
                txtMerchantId,
                txtTerminalId,
                txtPaymentLink;

        TextView
                txtPspNameHeader,
                txtPspTypeHeader,
                txtMerchantIdHeader,
                txtTerminalIdHeader,
                txtPaymentLinkHeader;


        Button btnShare;
        Button btnCopy;

        FrameLayout frameShare;
        FrameLayout frameCopy;

        LinearLayout linearLayout, linear;


        ViewHolder(View view) {
            super(view);

            txtPspNameHeader = view.findViewById(R.id.frag_link_item_psp_name_head);
            txtMerchantIdHeader = view.findViewById(R.id.frag_link_item_merchant_id_head);
            txtTerminalIdHeader = view.findViewById(R.id.frag_link_item_terminal_id_head);
            txtPaymentLinkHeader = view.findViewById(R.id.frag_link_item_payment_link_head);

            txtPspName = view.findViewById(R.id.frag_link_item_psp_name_body);
            txtMerchantId = view.findViewById(R.id.frag_link_item_merchant_id_body);
            txtTerminalId = view.findViewById(R.id.frag_link_item_terminal_id_body);
            txtPaymentLink = view.findViewById(R.id.frag_link_item_payment_link_body);

            btnShare = view.findViewById(R.id.frag_link_item_btn_share_raw_link);
            btnCopy = view.findViewById(R.id.frag_link_item_btn_copy_raw_link);

            frameShare = view.findViewById(R.id.frag_link_raw_btn_share_container);
            frameShare.setVisibility(View.VISIBLE);

            frameCopy = view.findViewById(R.id.frag_link_raw_btn_copy_container);
            frameCopy.setVisibility(View.VISIBLE);

//            txtPspNameHeader.setVisibility(View.GONE);
//            txtMerchantIdHeader.setVisibility(View.GONE);
//            txtTerminalIdHeader.setVisibility(View.GONE);
//            txtPaymentLinkHeader.setVisibility(View.GONE);
//
//            txtPspName.setVisibility(View.GONE);
//            txtMerchantId.setVisibility(View.GONE);
//            txtTerminalId.setVisibility(View.GONE);
//            txtPaymentLink.setVisibility(View.GONE);

        }
    }

    public void onBindActions(final ViewHolder viewHolder, final MerchantModel merchantModel, final int position) {
        viewHolder.txtPspName.setText(merchantModel.getPspName());
//        viewHolder.txtPspType.setText(personModelTest.getPspType());
        viewHolder.txtMerchantId.setText(merchantModel.getMerchantId());
        viewHolder.txtTerminalId.setText(merchantModel.getTerminalId());
//        viewHolder.txtPaymentLink.setText(merchantModel.getPaymentLink());
        if (merchantModel.getPaymentLink()!=null)
        {
//            viewHolder.txtPaymentLink.setText("آماده ارسال");
            viewHolder.txtPaymentLink.setText(merchantModel.getPaymentLink());
        }

        viewHolder.btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Link: ", merchantModel.getPaymentLink());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(mContext, "لینک کپی شد", Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String shareBody =
                            merchantModel.getPspName() +"\n"+
                            "شماره ترمینال: "+merchantModel.getTerminalId() +"\n"+
                            "شماره پذیرنده: "+merchantModel.getMerchantId() +"\n"+
                            merchantModel.getPaymentLink();

                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Payment Link:");
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                    mContext.startActivity(Intent.createChooser(sharingIntent, "ارسال لینک پرداخت"));

                } catch (Exception e) {
                    Log.d("AbsentLinkListAdapter", "خطا در به اشتراک گزاری");
                }

            }
        });
    }
}
