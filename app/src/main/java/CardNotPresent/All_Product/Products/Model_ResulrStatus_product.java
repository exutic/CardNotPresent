package CardNotPresent.All_Product.Products;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Model_ResulrStatus_product
{
    @SerializedName("ResultStatus")
    private String usernameValidation;
    @SerializedName("ProductList")
    private List<ProductModel> productModelList;
    @SerializedName("UserId")
    private String UserId;

    public Model_ResulrStatus_product(String usernameValidation, List<ProductModel> productModelList, String userId) {
        this.usernameValidation = usernameValidation;
        this.productModelList = productModelList;
        UserId = userId;
    }

    public Model_ResulrStatus_product(String userId) {
        UserId = userId;
    }

    public Model_ResulrStatus_product() {
    }

    public String getUsernameValidation() {
        return usernameValidation;
    }

    public void setUsernameValidation(String usernameValidation) {
        this.usernameValidation = usernameValidation;
    }

    public List<ProductModel> getProductModelList() {
        return productModelList;
    }

    public void setProductModelList(List<ProductModel> productModelList) {
        this.productModelList = productModelList;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
