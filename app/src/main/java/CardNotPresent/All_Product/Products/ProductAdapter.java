package CardNotPresent.All_Product.Products;


import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import CardNotPresent.DC;
import CardNotPresent.Home.HomePanel;
import CardNotPresent.PspTerminal_Activity.PspTerminalActivity;
import CardNotPresent.R;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CLIPBOARD_SERVICE;
import static CardNotPresent.All_Product.Products.Product_Payment_linkFragment.REQUEST_CODE;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ProductModel> contactParentsList;
    private Context mContext;
    private List<ProductModel> contactParentsListFull;
    private ArrayList<Integer> arrayList = new ArrayList<>();
    private Product_Payment_linkFragment product_payment_linkFragment;

    private HomePanel personalPanel_activityTemp;

    ProductAdapter(List<ProductModel> contactParentsList, Context mContext, Product_Payment_linkFragment fragment) {
        this.contactParentsList = contactParentsList;
        contactParentsListFull = new ArrayList<>(contactParentsList);
        this.mContext = mContext;
        this.product_payment_linkFragment = fragment;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_product_payment_link, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder holder, final int position) {
        final int pos = position;

        final ViewHolder viewHolder = (ViewHolder) holder;
        final ProductModel productModelTest = (ProductModel) contactParentsList.get(position);
        personalPanel_activityTemp = new HomePanel();
        onBindActions(viewHolder, productModelTest, pos);

    }

    @Override
    public int getItemCount() {
        return contactParentsList.size();
    }

    public void filterList(List<ProductModel> filteredList) {
        contactParentsList = filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView
                txtProductName, txtProductCode, txtProductPrice,
                txtInsertDateTime, txtUserId, txtPaymentLink,
                txtPspType, txtDescription;

        private TextView txtProductNameHeader, txtProductCodeHeader, txtProductPriceHeader,
                txtInsertDateTimeHeader, txtUserIdHeader, txtPaymentLinkHeader,
                txtPspTypeHeader, txtDescriptionHeader;

        private ImageView imgAvailableLink;

        private Button btnExpand, btnCreatelink,btnShare,btnDelete,btnDelete2;

        private LinearLayout linearLayout,linear,linearShare;


        ViewHolder(View view)
        {
            super(view);

            txtProductNameHeader = view.findViewById(R.id.frag_product_item_txt_name_header);
            txtProductCodeHeader = view.findViewById(R.id.frag_product_item_txt_code_header);
            txtProductPriceHeader = view.findViewById(R.id.frag_product_item_txt_price_header);
            txtInsertDateTimeHeader = view.findViewById(R.id.frag_product_payment_item_txt_insert_datetime_header);
            txtUserIdHeader = view.findViewById(R.id.frag_product_payment_item_txt_userId_header);
            txtPaymentLinkHeader = view.findViewById(R.id.frag_product_item_txt_payment_link_header);
            txtPspTypeHeader = view.findViewById(R.id.frag_product_item_txt_pspType_header);
            txtDescriptionHeader = view.findViewById(R.id.frag_product_item_txt_description_header);

            txtProductName = view.findViewById(R.id.frag_product_item_txt_name_body);
            txtProductCode = view.findViewById(R.id.frag_product_item_txt_code_body);
            txtProductPrice = view.findViewById(R.id.frag_product_item_txt_price_body);
            txtInsertDateTime = view.findViewById(R.id.frag_product_payment_item_txt_insert_datetime_body);
            txtUserId = view.findViewById(R.id.frag_product_payment_item_txt_userId_body);
            txtPaymentLink = view.findViewById(R.id.frag_product_item_txt_payment_link_body);
            txtPspType = view.findViewById(R.id.frag_product_item_txt_pspType_body);
            txtDescription = view.findViewById(R.id.frag_product_item_txt_description_body);

            imgAvailableLink = view.findViewById(R.id.frag_product_item_btn_available_link);
            btnDelete = view.findViewById(R.id.frag_product_item_imgv_delete);
            btnDelete2 = view.findViewById(R.id.frag_product_item_btn_delete2);
            btnShare = view.findViewById(R.id.frag_product_item_imgv_share);


            btnExpand = view.findViewById(R.id.frag_product_item_btn_expand);
            btnCreatelink = view.findViewById(R.id.frag_product_item_txt_create_payment_link);
            linearLayout = view.findViewById(R.id.frag_product_item_l_layout_product);
            linear = view.findViewById(R.id.lineare_product);
            linearShare = view.findViewById(R.id.linearSharePro);


            linearLayout.setVisibility(View.GONE);
            linear.setVisibility(View.GONE);

            txtInsertDateTimeHeader.setVisibility(View.GONE);
            txtUserIdHeader.setVisibility(View.GONE);
            txtPaymentLinkHeader.setVisibility(View.GONE);
            txtPspTypeHeader.setVisibility(View.GONE);
            txtDescriptionHeader.setVisibility(View.GONE);

            txtInsertDateTime.setVisibility(View.GONE);
            txtUserId.setVisibility(View.GONE);
            txtPaymentLink.setVisibility(View.GONE);
            txtPspType.setVisibility(View.GONE);
            txtDescription.setVisibility(View.GONE);

        }

    }

    private void onBindActions(final ViewHolder viewHolder, final ProductModel productModelTest, final int pos) {

        viewHolder.txtProductName.setText(productModelTest.getProductName());
        viewHolder.txtProductCode.setText(productModelTest.getProductCode());
        viewHolder.txtInsertDateTime.setText(productModelTest.getInsertDateTime());
        viewHolder.txtUserId.setText(productModelTest.getUserId());
        viewHolder.txtPspType.setText(productModelTest.getPspType());
        viewHolder.txtDescription.setText(productModelTest.getDescription());


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

                    viewHolder.linearLayout.setVisibility(View.GONE);

                    viewHolder.txtInsertDateTimeHeader.setVisibility(View.GONE);
//                    viewHolder.txtUserIdHeader.setVisibility(View.GONE);
                    viewHolder.txtPaymentLinkHeader.setVisibility(View.GONE);
//                    viewHolder.txtPspTypeHeader.setVisibility(View.GONE);
                    viewHolder.txtDescriptionHeader.setVisibility(View.GONE);

                    viewHolder.txtInsertDateTime.setVisibility(View.GONE);
//                    viewHolder.txtUserId.setVisibility(View.GONE);
                    viewHolder.txtPaymentLink.setVisibility(View.GONE);
//                    viewHolder.txtPspType.setVisibility(View.GONE);
                    viewHolder.txtDescription.setVisibility(View.GONE);
                    viewHolder.linearLayout.setVisibility(View.GONE);
                    viewHolder.linear.setVisibility(View.GONE);

                    viewHolder.btnExpand.setBackground(mContext.getResources().getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp));
                }

                if (!flag) {
                    arrayList.add(pos);


                    viewHolder.txtInsertDateTimeHeader.setVisibility(View.VISIBLE);
//                    viewHolder.txtUserIdHeader.setVisibility(View.VISIBLE);
                    viewHolder.txtPaymentLinkHeader.setVisibility(View.VISIBLE);
//                    viewHolder.txtPspTypeHeader.setVisibility(View.VISIBLE);
                    viewHolder.txtDescriptionHeader.setVisibility(View.VISIBLE);

                    viewHolder.txtInsertDateTime.setVisibility(View.VISIBLE);
//                    viewHolder.txtUserId.setVisibility(View.VISIBLE);
                    viewHolder.txtPaymentLink.setVisibility(View.VISIBLE);
//                    viewHolder.txtPspType.setVisibility(View.VISIBLE);
                    viewHolder.txtDescription.setVisibility(View.VISIBLE);


                    if (productModelTest.getPaymentLink() != null)
                    {
                        viewHolder.linear.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        viewHolder.linearLayout.setVisibility(View.VISIBLE);
                    }


                    viewHolder.btnExpand.setBackground(mContext.getResources().getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp));
                }
            }
        });


        if (productModelTest.getPaymentLink() != null)
        {
            viewHolder.txtPaymentLink.setText("دارد");
            viewHolder.imgAvailableLink.setBackgroundResource(R.drawable.ic_done_black_24dp);
            viewHolder.btnShare.setEnabled(true);
            viewHolder.btnShare.setBackgroundResource(R.drawable.share_icon);
            viewHolder.linearLayout.setVisibility(View.GONE);
        }else
            {
                viewHolder.txtPaymentLink.setText("ندارد");
                viewHolder.imgAvailableLink.setBackgroundResource(R.drawable.ic_close_black_24dp);
                viewHolder.btnShare.setEnabled(false);
                viewHolder.btnShare.setBackgroundResource(R.drawable.share_icon_off);
                viewHolder.linear.setVisibility(View.GONE);
            }

        viewHolder.btnDelete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product_payment_linkFragment.deleteProduct(productModelTest);            }
        });

        viewHolder.linearShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String shareBody = productModelTest.getPaymentLink();

//                Uri uri = Uri.parse(personModelTest.getPaymentLink());
//                Html.fromHtml(shareBody);
//                try {
//                    URL myURL = new URL(shareBody);
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                }
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Payment Link:");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                mContext.startActivity(Intent.createChooser(sharingIntent, "ارسال لینک پرداخت"));
            }
        });


        viewHolder.btnCreatelink.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(mContext, PspTerminalActivity.class);
                intent.putExtra("productId",productModelTest.getProductCode());
                intent.putExtra("turn",2);
                product_payment_linkFragment.startActivityForResult(intent,REQUEST_CODE);
            }
        });

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product_payment_linkFragment.deleteProduct(productModelTest);
            }
        });
        viewHolder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(CLIPBOARD_SERVICE);
                String shareBody = productModelTest.getPaymentLink();
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                mContext.startActivity(Intent.createChooser(sharingIntent,"ارسال لینک پرداخت"));
            }
        });
    }

}


