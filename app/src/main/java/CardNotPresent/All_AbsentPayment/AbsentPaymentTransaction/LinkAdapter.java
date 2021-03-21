package CardNotPresent.All_AbsentPayment.AbsentPaymentTransaction;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import CardNotPresent.DC;
import CardNotPresent.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

class LinkAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<LinkModel> contactParentsList;
    private Context mContext;
    private List<LinkModel> contactParentsListFull;

    private ArrayList<Integer> arrayList = new ArrayList<>();
    private LinkFragment persons_transaction_listFragment;

    LinkAdapter(List<LinkModel> contactParentsList, Context mContext, LinkFragment fragment) {
        this.contactParentsList = contactParentsList;
        contactParentsListFull = new ArrayList<>(contactParentsList);
        this.mContext = mContext;
        this.persons_transaction_listFragment = fragment;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_persons_transaction_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        final ViewHolder viewHolder = (ViewHolder) holder;
        final LinkModel personsTransModel = (LinkModel) contactParentsList.get(position);

        onBindActions(viewHolder,personsTransModel,position);
    }

    public void filterList(List<LinkModel> filteredList) {
        contactParentsList = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return contactParentsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView
                txtPersonTranName,
                txtPersonTranAmount,
                txtPersonTranNationalCode,
                txtPersonTranMobileNumber,
                txtPersonTranUserId,
                txtPersonTranDescription,
                txtPersonTranPSP_Type,
                txtPersonTranPaymentLink,
                txtPersonTranMerchantId,
                txtPersonTranTerminalId,
                txtPersonTranIpgResponseCode,
                txtPersonTranTransactionDateTime,
                txtPersonTranRefNum,
                txtPersonTranResNum;
        TextView
                txtPersonTranNameHeader,
                txtPersonTranAmountHeader,
                txtPersonTranNationalCodeHeader,
                txtPersonTranMobileNumberHeader,
                txtPersonTranUserIdHeader,
                txtPersonTranDescriptionHeader,
                txtPersonTranPSP_TypeHeader,
                txtPersonTranPaymentLinkHeader,
                txtPersonTranMerchantIdHeader,
                txtPersonTranTerminalIdHeader,
                txtPersonTranIpgResponseCodeHeader,
                txtPersonTranTransactionDateTimHeader,
                txtPersonTranRefNumHeader,
                txtPersonTranResNumHeader;

        Button btnShare, btnExpand;

        ViewHolder(View view) {
            super(view);

            txtPersonTranNameHeader = view.findViewById(R.id.frag_persons_transaction_list_item_name_header);
            txtPersonTranAmountHeader = view.findViewById(R.id.frag_persons_transaction_list_item_amount_header);
            txtPersonTranNationalCodeHeader = view.findViewById(R.id.frag_persons_transaction_list_item_national_code_header);
            txtPersonTranMobileNumberHeader = view.findViewById(R.id.frag_persons_transaction_list_item_mobile_number_header);
            txtPersonTranUserIdHeader = view.findViewById(R.id.frag_persons_transaction_list_item_user_id_header);
            txtPersonTranDescriptionHeader = view.findViewById(R.id.frag_persons_transaction_list_item_description_header);
            txtPersonTranPSP_TypeHeader = view.findViewById(R.id.frag_persons_transaction_list_item_psp_type_header);
            txtPersonTranPaymentLinkHeader = view.findViewById(R.id.frag_persons_transaction_list_item_payment_link_header);
            txtPersonTranMerchantIdHeader = view.findViewById(R.id.frag_persons_transaction_list_item_merchant_id_header);
            txtPersonTranTerminalIdHeader = view.findViewById(R.id.frag_persons_transaction_list_item_terminal_id_header);
            txtPersonTranIpgResponseCodeHeader = view.findViewById(R.id.frag_persons_transaction_list_item_ipg_response_code_header);
            txtPersonTranTransactionDateTimHeader = view.findViewById(R.id.frag_persons_transaction_list_item_transaction_date_time_header);
            txtPersonTranRefNumHeader = view.findViewById(R.id.frag_persons_transaction_list_item_ref_num_code_header);
            txtPersonTranResNumHeader = view.findViewById(R.id.frag_persons_transaction_list_item_res_num_code_header);

            txtPersonTranName = view.findViewById(R.id.frag_persons_transaction_list_item_name_body);
            txtPersonTranAmount = view.findViewById(R.id.frag_persons_transaction_list_item_amount_body);
            txtPersonTranNationalCode = view.findViewById(R.id.frag_persons_transaction_list_item_national_code_body);
            txtPersonTranMobileNumber = view.findViewById(R.id.frag_persons_transaction_list_item_mobile_number_body);
            txtPersonTranUserId = view.findViewById(R.id.frag_persons_transaction_list_item_user_id_body);
            txtPersonTranDescription = view.findViewById(R.id.frag_persons_transaction_list_item_description_body);
            txtPersonTranPSP_Type = view.findViewById(R.id.frag_persons_transaction_list_item_psp_type_body);
            txtPersonTranPaymentLink = view.findViewById(R.id.frag_persons_transaction_list_item_payment_link_body);
            txtPersonTranMerchantId = view.findViewById(R.id.frag_persons_transaction_list_item_merchant_id_body);
            txtPersonTranTerminalId = view.findViewById(R.id.frag_persons_transaction_list_item_terminal_id_body);
            txtPersonTranIpgResponseCode = view.findViewById(R.id.frag_persons_transaction_list_item_ipg_response_code_body);
            txtPersonTranTransactionDateTime = view.findViewById(R.id.frag_persons_transaction_list_item_transaction_date_time_body);
            txtPersonTranRefNum = view.findViewById(R.id.frag_persons_transaction_list_item_ref_num_code_body);
            txtPersonTranResNum = view.findViewById(R.id.frag_persons_transaction_list_item_res_num_code_body);


            btnShare = view.findViewById(R.id.frag_persons_transaction_list_item_btn_share1);
            btnExpand = view.findViewById(R.id.frag_persons_transaction_list_item_btn_expand);


            txtPersonTranNationalCodeHeader.setVisibility(View.GONE);
            txtPersonTranMobileNumberHeader.setVisibility(View.GONE);
//            txtPersonTranUserIdHeader.setVisibility(View.GONE);
            txtPersonTranDescriptionHeader.setVisibility(View.GONE);
            txtPersonTranPSP_TypeHeader.setVisibility(View.GONE);
            txtPersonTranPaymentLinkHeader.setVisibility(View.GONE);
            txtPersonTranMerchantIdHeader.setVisibility(View.GONE);
            txtPersonTranTerminalIdHeader.setVisibility(View.GONE);
            txtPersonTranIpgResponseCodeHeader.setVisibility(View.GONE);
            txtPersonTranRefNumHeader.setVisibility(View.GONE);
            txtPersonTranResNumHeader.setVisibility(View.GONE);

            txtPersonTranNationalCode.setVisibility(View.GONE);
            txtPersonTranMobileNumber.setVisibility(View.GONE);
//            txtPersonTranUserId.setVisibility(View.GONE);
            txtPersonTranDescription.setVisibility(View.GONE);
            txtPersonTranPSP_Type.setVisibility(View.GONE);
            txtPersonTranPaymentLink.setVisibility(View.GONE);
            txtPersonTranMerchantId.setVisibility(View.GONE);
            txtPersonTranTerminalId.setVisibility(View.GONE);
            txtPersonTranIpgResponseCode.setVisibility(View.GONE);
            txtPersonTranRefNum.setVisibility(View.GONE);
            txtPersonTranResNum.setVisibility(View.GONE);

        }
    }

    private void onBindActions(final ViewHolder viewHolder, final LinkModel personsTransModelTest, final int position) {

        final String fullName = personsTransModelTest.getFirstName()+" "+personsTransModelTest.getLastName();
        viewHolder.txtPersonTranName.setText(fullName);
        viewHolder.txtPersonTranNationalCode.setText(personsTransModelTest.getNationalCode());
        viewHolder.txtPersonTranMobileNumber.setText(personsTransModelTest.getMobileNumber());
        viewHolder.txtPersonTranUserId.setText(personsTransModelTest.getUserId());
        viewHolder.txtPersonTranDescription.setText(personsTransModelTest.getDescription());
        viewHolder.txtPersonTranPSP_Type.setText(personsTransModelTest.getPspType());
        viewHolder.txtPersonTranPaymentLink.setText(personsTransModelTest.getPaymentLink());
        viewHolder.txtPersonTranMerchantId.setText(personsTransModelTest.getMerchantId());
        viewHolder.txtPersonTranTerminalId.setText(personsTransModelTest.getTerminalId());
        viewHolder.txtPersonTranTransactionDateTime.setText(personsTransModelTest.getTransactionDateTime());
        viewHolder.txtPersonTranRefNum.setText(personsTransModelTest.getRefNum());
        viewHolder.txtPersonTranResNum.setText(personsTransModelTest.getResNum());
        viewHolder.txtPersonTranIpgResponseCode.setText(personsTransModelTest.getIpgResponseCode());
        viewHolder.txtPersonTranPaymentLink.setText("دارد");


        String string = personsTransModelTest.getAmount();

        String temp = DC.convertFNumToENum(string);
        String numberDate = temp.replaceAll("[^0-9]", "");

        if (numberDate.length() > 0) {
            DecimalFormat sdd = new DecimalFormat("#,###");
            Double doubleNumber = Double.parseDouble(numberDate);
            String format = sdd.format(doubleNumber);
            viewHolder.txtPersonTranAmount.setText(format);
//            viewHolder.txtPersonAmount.setSelection(format.length());
        }

        viewHolder.btnExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = false;
                int temp = 0;

                for (int i = 0; i < arrayList.size(); i++) {
                    if (arrayList.get(i) == position) {
                        temp = i;
                        flag = true;
                    }
                }
                if (flag) {
                    arrayList.remove(temp);


                    viewHolder.txtPersonTranNationalCodeHeader.setVisibility(View.GONE);
                    viewHolder.txtPersonTranMobileNumberHeader.setVisibility(View.GONE);
                    viewHolder.txtPersonTranUserIdHeader.setVisibility(View.GONE);
                    viewHolder.txtPersonTranDescriptionHeader.setVisibility(View.GONE);
                    viewHolder.txtPersonTranPSP_TypeHeader.setVisibility(View.GONE);
//                    viewHolder.txtPersonTranPaymentLinkHeader.setVisibility(View.GONE);
                    viewHolder.txtPersonTranMerchantIdHeader.setVisibility(View.GONE);
                    viewHolder.txtPersonTranTerminalIdHeader.setVisibility(View.GONE);
                    viewHolder.txtPersonTranIpgResponseCodeHeader.setVisibility(View.GONE);
                    viewHolder.txtPersonTranRefNumHeader.setVisibility(View.GONE);
                    viewHolder.txtPersonTranResNumHeader.setVisibility(View.GONE);

                    viewHolder.txtPersonTranNationalCode.setVisibility(View.GONE);
                    viewHolder.txtPersonTranMobileNumber.setVisibility(View.GONE);
                    viewHolder.txtPersonTranUserId.setVisibility(View.GONE);
                    viewHolder.txtPersonTranDescription.setVisibility(View.GONE);
                    viewHolder.txtPersonTranPSP_Type.setVisibility(View.GONE);
//                    viewHolder.txtPersonTranPaymentLink.setVisibility(View.GONE);
                    viewHolder.txtPersonTranMerchantId.setVisibility(View.GONE);
                    viewHolder.txtPersonTranTerminalId.setVisibility(View.GONE);
                    viewHolder.txtPersonTranIpgResponseCode.setVisibility(View.GONE);
                    viewHolder.txtPersonTranRefNum.setVisibility(View.GONE);
                    viewHolder.txtPersonTranResNum.setVisibility(View.GONE);

                    viewHolder.btnExpand.setBackground(mContext.getResources().getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp));
                }

                if (!flag) {
                    arrayList.add(position);


                    viewHolder.txtPersonTranNationalCodeHeader.setVisibility(View.VISIBLE);
                    viewHolder.txtPersonTranMobileNumberHeader.setVisibility(View.VISIBLE);
                    viewHolder.txtPersonTranDescriptionHeader.setVisibility(View.VISIBLE);
//                    viewHolder.txtPersonTranPSP_TypeHeader.setVisibility(View.VISIBLE);
//                    viewHolder.txtPersonTranPaymentLinkHeader.setVisibility(View.VISIBLE);
                    viewHolder.txtPersonTranMerchantIdHeader.setVisibility(View.VISIBLE);
                    viewHolder.txtPersonTranTerminalIdHeader.setVisibility(View.VISIBLE);
                    viewHolder.txtPersonTranIpgResponseCodeHeader.setVisibility(View.VISIBLE);
                    viewHolder.txtPersonTranTransactionDateTimHeader.setVisibility(View.VISIBLE);
                    viewHolder.txtPersonTranRefNumHeader.setVisibility(View.VISIBLE);
                    viewHolder.txtPersonTranResNumHeader.setVisibility(View.VISIBLE);


                    viewHolder.txtPersonTranNationalCode.setVisibility(View.VISIBLE);
                    viewHolder.txtPersonTranMobileNumber.setVisibility(View.VISIBLE);
                    viewHolder.txtPersonTranDescription.setVisibility(View.VISIBLE);
//                    viewHolder.txtPersonTranPSP_Type.setVisibility(View.VISIBLE);
//                    viewHolder.txtPersonTranPaymentLink.setVisibility(View.VISIBLE);
                    viewHolder.txtPersonTranMerchantId.setVisibility(View.VISIBLE);
                    viewHolder.txtPersonTranTerminalId.setVisibility(View.VISIBLE);
                    viewHolder.txtPersonTranIpgResponseCode.setVisibility(View.VISIBLE);
                    viewHolder.txtPersonTranTransactionDateTime.setVisibility(View.VISIBLE);
                    viewHolder.txtPersonTranRefNum.setVisibility(View.VISIBLE);
                    viewHolder.txtPersonTranResNum.setVisibility(View.VISIBLE);

                    viewHolder.btnExpand.setBackground(mContext.getResources().getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp));
                }

            }
        });

//        viewHolder.btnShare.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        viewHolder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                String shareBody = "نام و نام خانوادگی : " + fullName + "\n" +
                        "مبلغ : " + personsTransModelTest.getAmount() + "\n" +
                        "تاریخ تراکنش : " + personsTransModelTest.getTransactionDateTime() + "\n" +
                        "شماره پیگیری : " + personsTransModelTest.getResNum() + "\n" +
                        "شماره ترمینال : " + personsTransModelTest.getTerminalId() + "\n" +
                        "شماره مرجع : " + personsTransModelTest.getRefNum() + "\n" +
                        "نتیجه تراکنش : " + personsTransModelTest.getIpgResponseCode() + "\n";
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                mContext.startActivity(Intent.createChooser(sharingIntent,"ارسال لینک پرداخت"));
            }
        });

    }
}
