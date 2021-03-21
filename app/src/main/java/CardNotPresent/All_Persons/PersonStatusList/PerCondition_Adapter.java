package CardNotPresent.All_Persons.PersonStatusList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import CardNotPresent.DC;
import CardNotPresent.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PerCondition_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PersonsCondition_Model> contactParentsList;
    private Context mContext;
    private List<PersonsCondition_Model> contactParentsListFull;
    private List<PersonsCondition_Model> list;

    private ArrayList<Integer> arrayList = new ArrayList<>();
    private Persons_Transaction_ConditionFragment persons_transaction_listFragment;

    PerCondition_Adapter(List<PersonsCondition_Model> contactParentsList, Context mContext, Persons_Transaction_ConditionFragment fragment) {
        this.contactParentsList = contactParentsList;
        this.contactParentsListFull = contactParentsList;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_persons_condition_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        final ViewHolder viewHolder = (ViewHolder) holder;
        final PersonsCondition_Model personsConditionModel = (PersonsCondition_Model) contactParentsList.get(position);

        onBindActions(viewHolder, personsConditionModel, position);
    }

    @Override
    public int getItemCount() {
        return contactParentsList.size();
    }

    public void filterList(List<PersonsCondition_Model> filteredList) {
        contactParentsList = filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView
                txtPersonConditionsName,
                txtPersonConditionsAmount,
                txtPersonConditionsNationalCode,
                txtPersonConditionsMobileNumber,
                txtPersonConditionsUserId,
                txtPersonConditionsDescription,
                txtPersonConditionsPSP_Type,
                txtPersonConditionsPaymentLink,
                txtPersonConditionsMerchantId,
                txtPersonConditionsTerminalId,
                txtPersonConditionsIpgResponseCode,
                txtPersonConditionsTransactionDateTime,
                txtPersonConditionsTransactionStatus,
                txtPersonConditionsRefNum,
                txtPersonConditionsResNum;
        TextView
                txtPersonConditionsNameHeader,
                txtPersonConditionsAmountHeader,
                txtPersonConditionsNationalCodeHeader,
                txtPersonConditionsMobileNumberHeader,
                txtPersonConditionsUserIdHeader,
                txtPersonConditionsDescriptionHeader,
                txtPersonConditionsPSP_TypeHeader,
                txtPersonConditionsPaymentLinkHeader,
                txtPersonConditionsMerchantIdHeader,
                txtPersonConditionsTerminalIdHeader,
                txtPersonConditionsIpgResponseCodeHeader,
                txtPersonConditionsTransactionDateTimHeader,
                txtPersonConditionsTransactionStatusHeader,
                txtPersonConditionsRefNumHeader,
                txtPersonConditionsResNumHeader;

        Button btnShare, btnExpand;

        LinearLayout linearLayout;


        ViewHolder(View view) {
            super(view);

            //need edit
            txtPersonConditionsNameHeader = view.findViewById(R.id.frag_persons_conditions_list_item_name_header);
            txtPersonConditionsAmountHeader = view.findViewById(R.id.frag_persons_conditions_list_item_amount_header);
            txtPersonConditionsNationalCodeHeader = view.findViewById(R.id.frag_persons_conditions_list_item_national_code_header);
            txtPersonConditionsMobileNumberHeader = view.findViewById(R.id.frag_persons_conditions_list_item_mobile_number_header);
            txtPersonConditionsUserIdHeader = view.findViewById(R.id.frag_persons_conditions_list_item_user_id_header);
            txtPersonConditionsDescriptionHeader = view.findViewById(R.id.frag_persons_conditions_list_item_description_header);
            txtPersonConditionsPSP_TypeHeader = view.findViewById(R.id.frag_persons_conditions_list_item_psp_type_header);
            txtPersonConditionsPaymentLinkHeader = view.findViewById(R.id.frag_persons_conditions_list_item_payment_link_header);
            txtPersonConditionsMerchantIdHeader = view.findViewById(R.id.frag_persons_conditions_list_item_merchant_id_header);
            txtPersonConditionsTerminalIdHeader = view.findViewById(R.id.frag_persons_conditions_list_item_terminal_id_header);
            txtPersonConditionsIpgResponseCodeHeader = view.findViewById(R.id.frag_persons_conditions_list_item_ipg_response_code_header);
            txtPersonConditionsTransactionDateTimHeader = view.findViewById(R.id.frag_persons_conditions_list_item_transaction_date_time_header);
            txtPersonConditionsTransactionStatusHeader = view.findViewById(R.id.frag_persons_conditions_list_item_transaction_status_header);
            txtPersonConditionsRefNumHeader = view.findViewById(R.id.frag_persons_conditions_list_item_ref_num_code_header);
            txtPersonConditionsResNumHeader = view.findViewById(R.id.frag_persons_conditions_list_item_res_num_code_header);

            txtPersonConditionsName = view.findViewById(R.id.frag_persons_conditions_list_item_name_body);
            txtPersonConditionsAmount = view.findViewById(R.id.frag_persons_conditions_list_item_amount_body);
            txtPersonConditionsNationalCode = view.findViewById(R.id.frag_persons_conditions_list_item_national_code_body);
            txtPersonConditionsMobileNumber = view.findViewById(R.id.frag_persons_conditions_list_item_mobile_number_body);
            txtPersonConditionsUserId = view.findViewById(R.id.frag_persons_conditions_list_item_user_id_body);
            txtPersonConditionsDescription = view.findViewById(R.id.frag_persons_conditions_list_item_description_body);
            txtPersonConditionsPSP_Type = view.findViewById(R.id.frag_persons_conditions_list_item_psp_type_body);
            txtPersonConditionsPaymentLink = view.findViewById(R.id.frag_persons_conditions_list_item_payment_link_body);
            txtPersonConditionsMerchantId = view.findViewById(R.id.frag_persons_conditions_list_item_merchant_id_body);
            txtPersonConditionsTerminalId = view.findViewById(R.id.frag_persons_conditions_list_item_terminal_id_body);
            txtPersonConditionsIpgResponseCode = view.findViewById(R.id.frag_persons_conditions_list_item_ipg_response_code_body);
            txtPersonConditionsTransactionDateTime = view.findViewById(R.id.frag_persons_conditions_list_item_transaction_date_time_body);
            txtPersonConditionsTransactionStatus = view.findViewById(R.id.frag_persons_conditions_list_item_transaction_status_body);
            txtPersonConditionsRefNum = view.findViewById(R.id.frag_persons_conditions_list_item_ref_num_code_body);
            txtPersonConditionsResNum = view.findViewById(R.id.frag_persons_conditions_list_item_res_num_code_body);


            btnShare = view.findViewById(R.id.frag_persons_conditions_list_item_btn_share1);
            btnExpand = view.findViewById(R.id.frag_persons_conditions_list_item_btn_expand);


            txtPersonConditionsNationalCodeHeader.setVisibility(View.GONE);
            txtPersonConditionsMobileNumberHeader.setVisibility(View.GONE);
            txtPersonConditionsUserIdHeader.setVisibility(View.GONE);
            txtPersonConditionsDescriptionHeader.setVisibility(View.GONE);
            txtPersonConditionsPSP_TypeHeader.setVisibility(View.GONE);
            txtPersonConditionsPaymentLinkHeader.setVisibility(View.GONE);
            txtPersonConditionsMerchantIdHeader.setVisibility(View.GONE);
            txtPersonConditionsTerminalIdHeader.setVisibility(View.GONE);
            txtPersonConditionsIpgResponseCodeHeader.setVisibility(View.GONE);
            txtPersonConditionsTransactionStatusHeader.setVisibility(View.GONE);
            txtPersonConditionsRefNumHeader.setVisibility(View.GONE);
            txtPersonConditionsResNumHeader.setVisibility(View.GONE);

            txtPersonConditionsNationalCode.setVisibility(View.GONE);
            txtPersonConditionsMobileNumber.setVisibility(View.GONE);
            txtPersonConditionsUserId.setVisibility(View.GONE);
            txtPersonConditionsDescription.setVisibility(View.GONE);
            txtPersonConditionsPSP_Type.setVisibility(View.GONE);
            txtPersonConditionsPaymentLink.setVisibility(View.GONE);
            txtPersonConditionsMerchantId.setVisibility(View.GONE);
            txtPersonConditionsTerminalId.setVisibility(View.GONE);
            txtPersonConditionsIpgResponseCode.setVisibility(View.GONE);
            txtPersonConditionsTransactionStatus.setVisibility(View.GONE);
            txtPersonConditionsRefNum.setVisibility(View.GONE);
            txtPersonConditionsResNum.setVisibility(View.GONE);

        }
    }

    private void onBindActions(final ViewHolder viewHolder, final PersonsCondition_Model personsConditionModelTest, final int position) {

        final String fullName = personsConditionModelTest.getFirstName()+" "+personsConditionModelTest.getLastName();

        viewHolder.txtPersonConditionsName.setText(fullName);
        viewHolder.txtPersonConditionsNationalCode.setText(personsConditionModelTest.getNationalCode());
        viewHolder.txtPersonConditionsMobileNumber.setText(personsConditionModelTest.getMobileNumber());
        viewHolder.txtPersonConditionsUserId.setText(personsConditionModelTest.getUserId());
        viewHolder.txtPersonConditionsDescription.setText(personsConditionModelTest.getDescription());
        viewHolder.txtPersonConditionsPSP_Type.setText(personsConditionModelTest.getPspType());
        viewHolder.txtPersonConditionsPaymentLink.setText(personsConditionModelTest.getPaymentLink());
        viewHolder.txtPersonConditionsMerchantId.setText(personsConditionModelTest.getMerchantId());
        viewHolder.txtPersonConditionsTerminalId.setText(personsConditionModelTest.getTerminalId());
        viewHolder.txtPersonConditionsTransactionDateTime.setText(personsConditionModelTest.getTransactionDateTime());
        viewHolder.txtPersonConditionsRefNum.setText(personsConditionModelTest.getRefNum());
        viewHolder.txtPersonConditionsResNum.setText(personsConditionModelTest.getResNum());
        viewHolder.txtPersonConditionsIpgResponseCode.setText(personsConditionModelTest.getIpgResponseCode());
        viewHolder.txtPersonConditionsPaymentLink.setText("دارد");



        String string = personsConditionModelTest.getAmount();

        String temp = DC.convertFNumToENum(string);
        String numberDate = temp.replaceAll("[^0-9]", "");

        if (numberDate.length() > 0) {
            DecimalFormat sdd = new DecimalFormat("#,###");
            Double doubleNumber = Double.parseDouble(numberDate);


            String format = sdd.format(doubleNumber);
            viewHolder.txtPersonConditionsAmount.setText(format);
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


                    viewHolder.txtPersonConditionsNationalCodeHeader.setVisibility(View.GONE);
                    viewHolder.txtPersonConditionsMobileNumberHeader.setVisibility(View.GONE);
//                    viewHolder.txtPersonConditionsUserIdHeader.setVisibility(View.GONE);
                    viewHolder.txtPersonConditionsDescriptionHeader.setVisibility(View.GONE);
                    viewHolder.txtPersonConditionsPSP_TypeHeader.setVisibility(View.GONE);
//                    viewHolder.txtPersonConditionsPaymentLinkHeader.setVisibility(View.GONE);
                    viewHolder.txtPersonConditionsMerchantIdHeader.setVisibility(View.GONE);
                    viewHolder.txtPersonConditionsTerminalIdHeader.setVisibility(View.GONE);
                    viewHolder.txtPersonConditionsIpgResponseCodeHeader.setVisibility(View.GONE);
                    viewHolder.txtPersonConditionsTransactionStatusHeader.setVisibility(View.GONE);
                    viewHolder.txtPersonConditionsRefNumHeader.setVisibility(View.GONE);
                    viewHolder.txtPersonConditionsResNumHeader.setVisibility(View.GONE);

                    viewHolder.txtPersonConditionsNationalCode.setVisibility(View.GONE);
                    viewHolder.txtPersonConditionsMobileNumber.setVisibility(View.GONE);
//                    viewHolder.txtPersonConditionsUserId.setVisibility(View.GONE);
                    viewHolder.txtPersonConditionsDescription.setVisibility(View.GONE);
                    viewHolder.txtPersonConditionsPSP_Type.setVisibility(View.GONE);
//                    viewHolder.txtPersonConditionsPaymentLink.setVisibility(View.GONE);
                    viewHolder.txtPersonConditionsMerchantId.setVisibility(View.GONE);
                    viewHolder.txtPersonConditionsTerminalId.setVisibility(View.GONE);
                    viewHolder.txtPersonConditionsIpgResponseCode.setVisibility(View.GONE);
                    viewHolder.txtPersonConditionsTransactionStatus.setVisibility(View.GONE);
                    viewHolder.txtPersonConditionsRefNum.setVisibility(View.GONE);
                    viewHolder.txtPersonConditionsResNum.setVisibility(View.GONE);

                    viewHolder.btnExpand.setBackground(mContext.getResources().getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp));
                }

                if (!flag) {
                    arrayList.add(position);

                    viewHolder.txtPersonConditionsNationalCodeHeader.setVisibility(View.VISIBLE);
                    viewHolder.txtPersonConditionsMobileNumberHeader.setVisibility(View.VISIBLE);
//                    viewHolder.txtPersonConditionsUserIdHeader.setVisibility(View.VISIBLE);
                    viewHolder.txtPersonConditionsDescriptionHeader.setVisibility(View.VISIBLE);
//                    viewHolder.txtPersonConditionsPSP_TypeHeader.setVisibility(View.VISIBLE);
//                    viewHolder.txtPersonConditionsPaymentLinkHeader.setVisibility(View.VISIBLE);
                    viewHolder.txtPersonConditionsMerchantIdHeader.setVisibility(View.VISIBLE);
                    viewHolder.txtPersonConditionsTerminalIdHeader.setVisibility(View.VISIBLE);
                    viewHolder.txtPersonConditionsIpgResponseCodeHeader.setVisibility(View.VISIBLE);
                    viewHolder.txtPersonConditionsTransactionStatusHeader.setVisibility(View.VISIBLE);
                    viewHolder.txtPersonConditionsRefNumHeader.setVisibility(View.VISIBLE);
                    viewHolder.txtPersonConditionsResNumHeader.setVisibility(View.VISIBLE);

                    viewHolder.txtPersonConditionsNationalCode.setVisibility(View.VISIBLE);
                    viewHolder.txtPersonConditionsMobileNumber.setVisibility(View.VISIBLE);
//                    viewHolder.txtPersonConditionsUserId.setVisibility(View.VISIBLE);
                    viewHolder.txtPersonConditionsDescription.setVisibility(View.VISIBLE);
//                    viewHolder.txtPersonConditionsPSP_Type.setVisibility(View.VISIBLE);
//                    viewHolder.txtPersonConditionsPaymentLink.setVisibility(View.VISIBLE);
                    viewHolder.txtPersonConditionsMerchantId.setVisibility(View.VISIBLE);
                    viewHolder.txtPersonConditionsTerminalId.setVisibility(View.VISIBLE);
                    viewHolder.txtPersonConditionsIpgResponseCode.setVisibility(View.VISIBLE);
                    viewHolder.txtPersonConditionsTransactionStatus.setVisibility(View.VISIBLE);
                    viewHolder.txtPersonConditionsRefNum.setVisibility(View.VISIBLE);
                    viewHolder.txtPersonConditionsResNum.setVisibility(View.VISIBLE);

                    viewHolder.btnExpand.setBackground(mContext.getResources().getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp));
                }

            }
        });

        String strStatus = personsConditionModelTest.getTransactionStatus().trim().toLowerCase();
        if (strStatus.equals("true"))
        {
            viewHolder.txtPersonConditionsTransactionStatus.setText("تسویه شده");
        } else {
            viewHolder.txtPersonConditionsTransactionStatus.setText("تسویه نشده");
        }

        //need edit
//        viewHolder.btnShare.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//

        viewHolder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                String shareBody = "نام و نام خانوادگی : " + fullName + "\n" +
                        "مبلغ : " + personsConditionModelTest.getAmount() + "\n" +
                        "تاریخ تراکنش : " + personsConditionModelTest.getTransactionDateTime() + "\n" +
                        "شماره پیگیری : " + personsConditionModelTest.getResNum() + "\n" +
                        "نتیجه تراکنش : " + personsConditionModelTest.getIpgResponseCode() + "\n";
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Payment Link");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                mContext.startActivity(Intent.createChooser(sharingIntent, "ارسال لینک پرداخت"));
            }
        });

    }

}
