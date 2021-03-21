package CardNotPresent.All_Persons.Person_link;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import CardNotPresent.DC;
import CardNotPresent.Home.HomePanel;
import CardNotPresent.All_Persons.Persons.PersonsModel;
import CardNotPresent.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PersonLinkAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<PersonsModel> contactParentsList;
    private Context mContext;

    ArrayList<Integer> arrayList = new ArrayList<>();

    HomePanel personalPanel_activityTemp;

    PersonLinkAdapter(List<PersonsModel> contactParentsList, Context mContext) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_persons_payment_link, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        final PersonsModel personModelTest = contactParentsList.get(position);

        onBindActions(viewHolder, personModelTest, position);

    }

    @Override
    public int getItemCount() {
        return contactParentsList.size();
    }

    public void filterList(List<PersonsModel> filteredList) {
        contactParentsList = filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView
                txtPersonName, txtPersonFamilyName, txtPersonUserId,

        txtPersonNationalCode, txtPersonMobileNumber, txtPersonAmount,

        txtPersonInsertDate, txtPersonPaymentLink, txtPersonPSP, txtPersonDescription;

        TextView
                txtPersonNameHeader, txtPersonFamilyNameHeader, txtPersonUserIdHeader,

        txtPersonNationalCodeHeader, txtPersonMobileNumberHeader, txtPersonAmountHeader,

        txtPersonInsertDateHeader, txtPersonPaymentLinkHeader, txtPersonPSPHeader, txtPersonDescriptionHeader;


        Button btnExpand,btnShare;

        FrameLayout frameShare;

        LinearLayout linearLayout,linear;


        ViewHolder(View view) {
            super(view);

            txtPersonNameHeader = view.findViewById(R.id.frag_persons_transaction_list_name_header);
            txtPersonFamilyNameHeader = view.findViewById(R.id.frag_persons_transaction_list_family_name_header);
            txtPersonUserIdHeader = view.findViewById(R.id.frag_persons_person_user_id_header);
            txtPersonNationalCodeHeader = view.findViewById(R.id.frag_persons_transaction_list_national_code_header);
            txtPersonMobileNumberHeader = view.findViewById(R.id.frag_persons_transaction_list_phone_number_header);
            txtPersonAmountHeader = view.findViewById(R.id.frag_persons_transaction_list_amount_header);
            txtPersonInsertDateHeader = view.findViewById(R.id.frag_persons_transaction_list_insert_date_time_header);
            txtPersonPaymentLinkHeader = view.findViewById(R.id.frag_persons_transaction_list_payment_link_header);
            txtPersonPSPHeader = view.findViewById(R.id.frag_persons_transaction_list_psp_type_header);
            txtPersonDescriptionHeader = view.findViewById(R.id.frag_persons_transaction_list_description_header);

            txtPersonName = view.findViewById(R.id.frag_persons_transaction_list_name_body);
            txtPersonFamilyName = view.findViewById(R.id.frag_persons_transaction_list_family_name_body);
            txtPersonUserId = view.findViewById(R.id.frag_person_person_user_id_body);
            txtPersonNationalCode = view.findViewById(R.id.frag_persons_transaction_list_national_code_body);
            txtPersonMobileNumber = view.findViewById(R.id.frag_persons_transaction_list_phone_number_body);
            txtPersonAmount = view.findViewById(R.id.frag_persons_transaction_list_amount_body);
            txtPersonInsertDate = view.findViewById(R.id.frag_persons_transaction_list_insert_date_time_body);
            txtPersonPaymentLink = view.findViewById(R.id.frag_persons_transaction_list_payment_link_body);
            txtPersonPSP = view.findViewById(R.id.frag_persons_transaction_list_psp_type_body);
            txtPersonDescription = view.findViewById(R.id.frag_persons_description_note_date_body);


            btnShare = view.findViewById(R.id.btn_share_link_person);
            frameShare = view.findViewById(R.id.frame_share_link_person);
            frameShare.setVisibility(View.VISIBLE);
            btnExpand = view.findViewById(R.id.frag_person_item_btn_expand);

            linearLayout = view.findViewById(R.id.linear_item_person);
            linear = view.findViewById(R.id.lineare_persons);
            linearLayout.setVisibility(View.GONE);
            linear.setVisibility(View.GONE);

            txtPersonUserIdHeader.setVisibility(View.GONE);
            txtPersonDescriptionHeader.setVisibility(View.GONE);
            txtPersonMobileNumberHeader.setVisibility(View.GONE);
            txtPersonAmountHeader.setVisibility(View.GONE);
            txtPersonInsertDateHeader.setVisibility(View.GONE);
            txtPersonPaymentLinkHeader.setVisibility(View.GONE);
            txtPersonPSPHeader.setVisibility(View.GONE);

            txtPersonUserId.setVisibility(View.GONE);
            txtPersonDescription.setVisibility(View.GONE);
            txtPersonAmount.setVisibility(View.GONE);
            txtPersonInsertDate.setVisibility(View.GONE);
            txtPersonPaymentLink.setVisibility(View.GONE);
            txtPersonMobileNumber.setVisibility(View.GONE);
            txtPersonPSP.setVisibility(View.GONE);

        }
    }

    public void onBindActions(final ViewHolder viewHolder, final PersonsModel personModelTest, final int position) {
        viewHolder.txtPersonName.setText(personModelTest.getFirstName());
        viewHolder.txtPersonFamilyName.setText(personModelTest.getLastName());
//        viewHolder.txtPersonUserId.setText(personModelTest.getUserId());
        viewHolder.txtPersonNationalCode.setText(personModelTest.getNationalCode());
        viewHolder.txtPersonMobileNumber.setText(personModelTest.getMobileNumber());
        viewHolder.txtPersonPaymentLink.setText("دارد");
        viewHolder.txtPersonInsertDate.setText(personModelTest.getInsertDateTime());
        viewHolder.txtPersonPSP.setText(personModelTest.getPspType());
        viewHolder.txtPersonDescription.setText(personModelTest.getDescription());

        String string = personModelTest.getAmount();

        String temp = DC.convertFNumToENum(string);
        String numberDate = temp.replaceAll("[^0-9]", "");

        if (numberDate.length() > 0) {
            DecimalFormat sdd = new DecimalFormat("#,###");
            Double doubleNumber = Double.parseDouble(numberDate);


            String format = sdd.format(doubleNumber);
            viewHolder.txtPersonAmount.setText(format);
//            viewHolder.txtPersonAmount.setSelection(format.length());
        }
        viewHolder.btnExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                    viewHolder.txtPersonDescriptionHeader.setVisibility(View.GONE);
//                    viewHolder.txtPersonNationalCodeHeader.setVisibility(View.GONE);
                    viewHolder.txtPersonMobileNumberHeader.setVisibility(View.GONE);
                    viewHolder.txtPersonAmountHeader.setVisibility(View.GONE);
                    viewHolder.txtPersonInsertDateHeader.setVisibility(View.GONE);
//                    viewHolder.txtPersonPaymentLinkHeader.setVisibility(View.GONE);
//                    viewHolder.txtPersonPSPHeader.setVisibility(View.GONE);

//                    viewHolder.txtPersonNationalCode.setVisibility(View.GONE);
                    viewHolder.txtPersonDescription.setVisibility(View.GONE);
                    viewHolder.txtPersonAmount.setVisibility(View.GONE);
                    viewHolder.txtPersonInsertDate.setVisibility(View.GONE);
//                    viewHolder.txtPersonPaymentLink.setVisibility(View.GONE);
                    viewHolder.txtPersonMobileNumber.setVisibility(View.GONE);
                    viewHolder.txtPersonPSP.setVisibility(View.GONE);

                    viewHolder.btnExpand.setBackground(mContext.getResources().getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp));
                }

                if (!flag) {
                    arrayList.add(position);

                    viewHolder.txtPersonDescriptionHeader.setVisibility(View.VISIBLE);
//                    viewHolder.txtPersonNationalCodeHeader.setVisibility(View.VISIBLE);
                    viewHolder.txtPersonMobileNumberHeader.setVisibility(View.VISIBLE);
                    viewHolder.txtPersonAmountHeader.setVisibility(View.VISIBLE);
                    viewHolder.txtPersonInsertDateHeader.setVisibility(View.VISIBLE);
//                    viewHolder.txtPersonPaymentLinkHeader.setVisibility(View.VISIBLE);
//                    viewHolder.txtPersonPSPHeader.setVisibility(View.VISIBLE);

                    viewHolder.txtPersonDescription.setVisibility(View.VISIBLE);
//                    viewHolder.txtPersonNationalCode.setVisibility(View.VISIBLE);
                    viewHolder.txtPersonAmount.setVisibility(View.VISIBLE);
                    viewHolder.txtPersonInsertDate.setVisibility(View.VISIBLE);
//                    viewHolder.txtPersonPaymentLink.setVisibility(View.VISIBLE);
//                    viewHolder.txtPersonUserId.setVisibility(View.VISIBLE);
                    viewHolder.txtPersonMobileNumber.setVisibility(View.VISIBLE);
//                    viewHolder.txtPersonPSP.setVisibility(View.VISIBLE);

                    viewHolder.btnExpand.setBackground(mContext.getResources().getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp));
                }

            }
        });


        viewHolder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String shareBody = personModelTest.getPaymentLink();
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Payment Link:");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                mContext.startActivity(Intent.createChooser(sharingIntent, "ارسال لینک پرداخت"));

            }
        });
    }
}
