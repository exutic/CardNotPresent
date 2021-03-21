package CardNotPresent.All_Product.ProductTransaction;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Model_ResucltStatus_transaction_List
{
    @SerializedName("ResultStatus")
    private String usernameValidation;
    @SerializedName("ProductTransactionLogList")
    private List<Product_Transaction_List_Model> productTransactionListModels;
    @SerializedName("UserId")
    private String UserId;

    public Model_ResucltStatus_transaction_List(String usernameValidation, List<Product_Transaction_List_Model> productTransactionListModels, String userId) {
        this.usernameValidation = usernameValidation;
        this.productTransactionListModels = productTransactionListModels;
        UserId = userId;
    }

    public Model_ResucltStatus_transaction_List(String userId) {
        UserId = userId;
    }

    public Model_ResucltStatus_transaction_List() {
    }

    public String getUsernameValidation() {
        return usernameValidation;
    }

    public void setUsernameValidation(String usernameValidation) {
        this.usernameValidation = usernameValidation;
    }

    public List<Product_Transaction_List_Model> getProductTransactionListModels() {
        return productTransactionListModels;
    }

    public void setProductTransactionListModels(List<Product_Transaction_List_Model> productTransactionListModels) {
        this.productTransactionListModels = productTransactionListModels;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
