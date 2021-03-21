package CardNotPresent.All_Product.ProductStatusList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import CardNotPresent.DC;
import CardNotPresent.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Product_Transaction_Condition_Adapter extends RecyclerView.Adapter<Product_Transaction_Condition_Adapter.ViewHolder> {
    private List<Product_Model_Condition> productTransactionListModels;
    private List<Product_Model_Condition> productTransactionListModelsFull;
    private Context mContext;
    private ArrayList<Integer> arrayList = new ArrayList<>();
    private Product_Transaction_ConditionFragment product_transaction_conditionFragment;

    public Product_Transaction_Condition_Adapter(List<Product_Model_Condition> productTransactionListModels, Context mContext, Product_Transaction_ConditionFragment product_transaction_conditionFragment) {
        this.productTransactionListModels = productTransactionListModels;
        this.mContext = mContext;
        this.product_transaction_conditionFragment = product_transaction_conditionFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_product_transaction_condition, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final int pos = position;

        final ViewHolder viewHolder = holder;
        final Product_Model_Condition productModelTest = productTransactionListModels.get(position);

        onBindActions(viewHolder, productModelTest, pos);
    }

    private void onBindActions(final ViewHolder viewHolder, final Product_Model_Condition productModelTest, final int pos) {
        viewHolder.txtProductName.setText(productModelTest.getProductName());
        viewHolder.txtProductCode.setText(productModelTest.getProductCode());
        viewHolder.txtRefNum.setText(productModelTest.getRefNum());
        viewHolder.txtResNum.setText(productModelTest.getResNum());
        viewHolder.txtTransactionDateTime.setText(productModelTest.getTransactionDateTime());
        viewHolder.txtMerchantId.setText(productModelTest.getMerchantId());
        viewHolder.txtTerminalId.setText(productModelTest.getTerminalId());
        viewHolder.txtCustomerName.setText(productModelTest.getCustomerName());
        viewHolder.txtCustomerMobileNumber.setText(productModelTest.getCustomerMobileNumber());
        viewHolder.txtCustomerAddress.setText(productModelTest.getCustomerAddress());
        viewHolder.txtCustomerPostalCode.setText(productModelTest.getCustomerPostalCode());
        viewHolder.txtPspType.setText(productModelTest.getPspType());
        viewHolder.txtDescription.setText(productModelTest.getDescription());
        viewHolder.txtIpgResponseCode.setText(productModelTest.getIpgResponseCode());
        viewHolder.txtpaymentlink.setText("دارد");


        String string = productModelTest.getProductPrice();

        String temp = DC.convertFNumToENum(string);
        String numberDate = temp.replaceAll("[^0-9]", "");

        if (numberDate.length() > 0) {
            DecimalFormat sdd = new DecimalFormat("#,###");
            Double doubleNumber = Double.parseDouble(numberDate);
            String format = sdd.format(doubleNumber);
            viewHolder.txtProductPrice.setText(format);
//            viewHolder.txtPersonAmount.setSelection(format.length());
        }

        String strStatus = productModelTest.getTransactionStatus().trim().toLowerCase();
        if (strStatus.equals("true"))
        {
            viewHolder.txtTransactionStatus.setText("تسویه شده");
        } else {
            viewHolder.txtTransactionStatus.setText("تسویه نشده");
        }
        viewHolder.btnExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = false;
                int temp = 0;

                for (int i = 0; i < arrayList.size(); i++) {
                    if (arrayList.get(i) == pos) {
                        temp = i;
                        flag = true;
                    }
                }
                if (flag) {
                    arrayList.remove(temp);

                    viewHolder.txtResNumHeader.setVisibility(View.GONE);
                    viewHolder.txtTransactionDateTimeHeader.setVisibility(View.GONE);
                    viewHolder.txtIpgResponseCodeHeader.setVisibility(View.GONE);
                    viewHolder.txtMerchantIdHeader.setVisibility(View.GONE);
                    viewHolder.txtTerminalIdHeader.setVisibility(View.GONE);
                    viewHolder.txtCustomerNameHeader.setVisibility(View.GONE);
                    viewHolder.txtCustomerMobileNumberHeader.setVisibility(View.GONE);
                    viewHolder.txtCustomerAddressHeader.setVisibility(View.GONE);
                    viewHolder.txtCustomerPostalCodeHeader.setVisibility(View.GONE);
                    viewHolder.txtPspTypeHeader.setVisibility(View.GONE);
                    viewHolder.txtTransactionStatusHrader.setVisibility(View.GONE);
//                    viewHolder.txtpaymentlinkHeader.setVisibility(View.GONE);
                    viewHolder.txtDescriptionHeader.setVisibility(View.GONE);


                    viewHolder.txtResNum.setVisibility(View.GONE);
                    viewHolder.txtTransactionDateTime.setVisibility(View.GONE);
                    viewHolder.txtIpgResponseCode.setVisibility(View.GONE);
                    viewHolder.txtMerchantId.setVisibility(View.GONE);
                    viewHolder.txtTerminalId.setVisibility(View.GONE);
                    viewHolder.txtCustomerName.setVisibility(View.GONE);
                    viewHolder.txtCustomerMobileNumber.setVisibility(View.GONE);
                    viewHolder.txtCustomerAddress.setVisibility(View.GONE);
                    viewHolder.txtCustomerPostalCode.setVisibility(View.GONE);
                    viewHolder.txtPspType.setVisibility(View.GONE);
                    viewHolder.txtTransactionStatus.setVisibility(View.GONE);
//                    viewHolder.txtpaymentlink.setVisibility(View.GONE);
                    viewHolder.txtDescription.setVisibility(View.GONE);

                    viewHolder.btnExpand.setBackground(mContext.getResources().getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp));
                }

                if (!flag) {
                    arrayList.add(pos);


                    viewHolder.txtResNumHeader.setVisibility(View.VISIBLE);
                    viewHolder.txtTransactionDateTimeHeader.setVisibility(View.VISIBLE);
                    viewHolder.txtIpgResponseCodeHeader.setVisibility(View.VISIBLE);
                    viewHolder.txtMerchantIdHeader.setVisibility(View.VISIBLE);
                    viewHolder.txtTerminalIdHeader.setVisibility(View.VISIBLE);
                    viewHolder.txtCustomerNameHeader.setVisibility(View.VISIBLE);
                    viewHolder.txtCustomerMobileNumberHeader.setVisibility(View.VISIBLE);
                    viewHolder.txtCustomerAddressHeader.setVisibility(View.VISIBLE);
                    viewHolder.txtCustomerPostalCodeHeader.setVisibility(View.VISIBLE);
//                    viewHolder.txtPspTypeHeader.setVisibility(View.VISIBLE);
                    viewHolder.txtTransactionStatusHrader.setVisibility(View.VISIBLE);
//                    viewHolder.txtpaymentlinkHeader.setVisibility(View.VISIBLE);
                    viewHolder.txtDescriptionHeader.setVisibility(View.VISIBLE);


                    viewHolder.txtResNum.setVisibility(View.VISIBLE);
                    viewHolder.txtTransactionDateTime.setVisibility(View.VISIBLE);
                    viewHolder.txtIpgResponseCode.setVisibility(View.VISIBLE);
                    viewHolder.txtMerchantId.setVisibility(View.VISIBLE);
                    viewHolder.txtTerminalId.setVisibility(View.VISIBLE);
                    viewHolder.txtCustomerName.setVisibility(View.VISIBLE);
                    viewHolder.txtCustomerMobileNumber.setVisibility(View.VISIBLE);
                    viewHolder.txtCustomerAddress.setVisibility(View.VISIBLE);
                    viewHolder.txtCustomerPostalCode.setVisibility(View.VISIBLE);
//                    viewHolder.txtPspType.setVisibility(View.VISIBLE);
                    viewHolder.txtTransactionStatus.setVisibility(View.VISIBLE);
//                    viewHolder.txtpaymentlink.setVisibility(View.VISIBLE);
                    viewHolder.txtDescription.setVisibility(View.VISIBLE);

                    viewHolder.btnExpand.setBackground(mContext.getResources().getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp));
                }

            }
        });

        viewHolder.imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String shareBody = "نام محصول : " + productModelTest.getProductName() + "\n" +
                        "کد محصول : " + productModelTest.getProductCode() + "\n" +
                        "قیمت : " + productModelTest.getProductPrice() + "\n" +
                        "نام خریدار : " + productModelTest.getCustomerName() + "\n" +
                        "تاریخ تراکنش :" + productModelTest.getTransactionDateTime() + "\n" +
                        "شماره پیگیری : " + productModelTest.getResNum() + "\n" +
                        "نتیجه تراکنش : " + productModelTest.getIpgResponseCode() + "\n";
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                mContext.startActivity(Intent.createChooser(sharingIntent, "ارسال لینک پرداخت"));
            }
        });
    }

    public void filterList(List<Product_Model_Condition> filteredList) {
        productTransactionListModels = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return productTransactionListModels.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView
                txtProductName, txtProductCode, txtProductPrice, txtRefNum, txtResNum, txtTransactionDateTime,
                txtIpgResponseCode, txtMerchantId, txtTerminalId, txtCustomerName, txtCustomerMobileNumber,
                txtCustomerAddress, txtCustomerPostalCode, txtPspType, txtTransactionStatus, txtpaymentlink, txtDescription;


        TextView txtProductNameHeader, txtProductCodeHeader, txtProductPriceHeader, txtRefNumHeader, txtResNumHeader,
                txtTransactionDateTimeHeader, txtIpgResponseCodeHeader, txtMerchantIdHeader, txtTerminalIdHeader, txtCustomerNameHeader,
                txtCustomerMobileNumberHeader, txtCustomerAddressHeader, txtCustomerPostalCodeHeader,
                txtPspTypeHeader, txtTransactionStatusHrader, txtpaymentlinkHeader, txtDescriptionHeader;

        private Button btnExpand;
        private ImageView imgShare;


        ViewHolder(View view) {
            super(view);

            txtProductNameHeader = view.findViewById(R.id.frag_product_transaction_condition_name_header);
            txtProductCodeHeader = view.findViewById(R.id.frag_product_transaction_condition_pro_code_header);
            txtProductPriceHeader = view.findViewById(R.id.frag_product_transaction_condition_amount_header);
            txtRefNumHeader = view.findViewById(R.id.frag_product_transaction_condition_ref_number_header);
            txtResNumHeader = view.findViewById(R.id.frag_product_transaction_condition_issue_number_header);
            txtTransactionDateTimeHeader = view.findViewById(R.id.frag_product_transaction_condition_transaction_date_header);
            txtIpgResponseCodeHeader = view.findViewById(R.id.frag_product_transaction_condition_transaction_result_header);
            txtMerchantIdHeader = view.findViewById(R.id.frag_product_transaction_condition_merchanId_header);
            txtTerminalIdHeader = view.findViewById(R.id.frag_product_transaction_condition_terminal_number_header);
            txtCustomerNameHeader = view.findViewById(R.id.frag_product_transaction_condition_customer_name_header);
            txtCustomerMobileNumberHeader = view.findViewById(R.id.frag_product_transaction_condition_customer_phonenumber_header);
            txtCustomerAddressHeader = view.findViewById(R.id.frag_product_transaction_condition_customer_address_header);
            txtCustomerPostalCodeHeader = view.findViewById(R.id.frag_product_transaction_condition_code_posti_header);
            txtPspTypeHeader = view.findViewById(R.id.frag_product_transaction_condition_pspType_header);
            txtTransactionStatusHrader = view.findViewById(R.id.frag_product_transaction_condition_TransactionStatus_header);
            txtpaymentlinkHeader = view.findViewById(R.id.frag_product_transaction_condition_paymentlink_header);
            txtDescriptionHeader = view.findViewById(R.id.frag_product_transaction_condition_description_header);


            txtProductName = view.findViewById(R.id.frag_product_transaction_condition_name_body);
            txtProductCode = view.findViewById(R.id.frag_product_transaction_condition_pro_code_body);
            txtProductPrice = view.findViewById(R.id.frag_product_transaction_condition_amount_body);
            txtRefNum = view.findViewById(R.id.frag_product_transaction_condition_ref_number_body);
            txtResNum = view.findViewById(R.id.frag_product_transaction_condition_issue_number_body);
            txtTransactionDateTime = view.findViewById(R.id.frag_product_transaction_condition_transaction_date_body);
            txtIpgResponseCode = view.findViewById(R.id.frag_product_transaction_condition_transaction_result_body);
            txtMerchantId = view.findViewById(R.id.frag_product_transaction_condition_merchanId_body);
            txtTerminalId = view.findViewById(R.id.frag_product_transaction_condition_terminal_number_body);
            txtCustomerName = view.findViewById(R.id.frag_product_transaction_condition_customer_name_body);
            txtCustomerMobileNumber = view.findViewById(R.id.frag_product_transaction_condition_customer_phonenumber_body);
            txtCustomerAddress = view.findViewById(R.id.frag_product_transaction_condition_customer_address_body);
            txtCustomerPostalCode = view.findViewById(R.id.frag_product_transaction_condition_code_posti_body);
            txtPspType = view.findViewById(R.id.frag_product_transaction_condition_pspType_body);
            txtTransactionStatus = view.findViewById(R.id.frag_product_transaction_condition_TransactionStatus_body);
            txtpaymentlink = view.findViewById(R.id.frag_product_transaction_condition_paymentlink_body);
            txtDescription = view.findViewById(R.id.frag_product_transaction_condition_description_body);


            btnExpand = view.findViewById(R.id.btn_expand_item_recycler_product_transaction_condition);
            imgShare = view.findViewById(R.id.img_share_item_recycler_product_transaction_condition);

            txtRefNumHeader.setVisibility(View.GONE);
            txtResNumHeader.setVisibility(View.GONE);
            txtTransactionDateTimeHeader.setVisibility(View.GONE);
            txtIpgResponseCodeHeader.setVisibility(View.GONE);
            txtMerchantIdHeader.setVisibility(View.GONE);
            txtTerminalIdHeader.setVisibility(View.GONE);
            txtCustomerNameHeader.setVisibility(View.GONE);
            txtCustomerMobileNumberHeader.setVisibility(View.GONE);
            txtCustomerAddressHeader.setVisibility(View.GONE);
            txtCustomerPostalCodeHeader.setVisibility(View.GONE);
            txtPspTypeHeader.setVisibility(View.GONE);
            txtTransactionStatusHrader.setVisibility(View.GONE);
            txtpaymentlinkHeader.setVisibility(View.GONE);
            txtDescriptionHeader.setVisibility(View.GONE);


            txtRefNum.setVisibility(View.GONE);
            txtResNum.setVisibility(View.GONE);
            txtTransactionDateTime.setVisibility(View.GONE);
            txtIpgResponseCode.setVisibility(View.GONE);
            txtMerchantId.setVisibility(View.GONE);
            txtTerminalId.setVisibility(View.GONE);
            txtCustomerName.setVisibility(View.GONE);
            txtCustomerMobileNumber.setVisibility(View.GONE);
            txtCustomerAddress.setVisibility(View.GONE);
            txtCustomerPostalCode.setVisibility(View.GONE);
            txtPspType.setVisibility(View.GONE);
            txtTransactionStatus.setVisibility(View.GONE);
            txtpaymentlink.setVisibility(View.GONE);
            txtDescription.setVisibility(View.GONE);
        }

    }
}
