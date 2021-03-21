package CardNotPresent.All_Product.ProductStatusList;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Model_ResucltStatus_transaction_Condition
{
    @SerializedName("ResultStatus")
    private String usernameValidation;
    @SerializedName("ProductTransactionLogStatusList")
    private List<Product_Model_Condition> productModelConditions;
    @SerializedName("UserId")
    private String UserId;
    @SerializedName("Description")
    private String Description;

    public Model_ResucltStatus_transaction_Condition(String usernameValidation, List<Product_Model_Condition> productTransactionListModels, String userId) {
        this.usernameValidation = usernameValidation;
        this.productModelConditions = productTransactionListModels;
        UserId = userId;
    }

    public Model_ResucltStatus_transaction_Condition(String userId) {
        UserId = userId;
    }

    public Model_ResucltStatus_transaction_Condition() {
    }

    public List<Product_Model_Condition> getProductModelConditions() {
        return productModelConditions;
    }

    public String getUsernameValidation() {
        return usernameValidation;
    }

    public void setUsernameValidation(String usernameValidation) {
        this.usernameValidation = usernameValidation;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
