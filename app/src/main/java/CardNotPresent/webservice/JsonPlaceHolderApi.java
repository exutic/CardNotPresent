package CardNotPresent.webservice;

import CardNotPresent.All_AbsentPayment.AbsentPaymentLinkList.AbsentLinkListModel;
import CardNotPresent.All_AbsentPayment.AbsentPaymentTransaction.Link_ResultStatus_Model;
import CardNotPresent.All_AbsentPayment.AbsentPaymentStatus.AbsentStatusModel;
import CardNotPresent.Home.ui.Profile.ModelParentProfile;
import CardNotPresent.Main.Forget_Password_Model;
import CardNotPresent.Main.Post;
//import com.example.totan_project_2_1.settings.change_password.Change_Password_Model;
//import com.example.totan_project_2_1.ui.persons_payment_link.Model_ResultStatus;
//import com.example.totan_project_2_1.ui.persons_payment_link.PersonsModel;
//import com.example.totan_project_2_1.ui.persons_transaction_condition.Model_ResultStatus_Per_Conditions;
//import com.example.totan_project_2_1.ui.persons_transaction_list.Model_ResultStatus_Per_Trans;
//import com.example.totan_project_2_1.ui.product_payment_link.Model_ResulrStatus_product;
//import com.example.totan_project_2_1.ui.product_payment_link.ProductModel;
//import com.example.totan_project_2_1.ui.product_transaction_condition.Model_ResucltStatus_transaction_Condition;
//import com.example.totan_project_2_1.ui.product_transaction_list.Model_ResucltStatus_transaction_List;
//import com.example.totan_project_2_1.ui.profile.ModelParentProfile;
//import com.example.totan_project_2_1.ui.profile.ProfileModel;
import CardNotPresent.All_Persons.PersonStatusList.Model_ResultStatus_Per_Conditions;
import CardNotPresent.All_Persons.Persons.Model_ResultStatus;
import CardNotPresent.All_Persons.Persons.PersonsModel;
import CardNotPresent.All_Product.ProductStatusList.Model_ResucltStatus_transaction_Condition;
import CardNotPresent.All_Product.ProductTransaction.Model_ResucltStatus_transaction_List;
import CardNotPresent.All_Product.Products.Model_ResulrStatus_product;
import CardNotPresent.All_Product.Products.ProductModel;
import CardNotPresent.Settings.Change_Password_Model;
import CardNotPresent.All_Persons.PersonsTransaction.Model_ResultStatus_Per_Trans;
import CardNotPresent.webservice.generate_links_inner_models.ModelParent;
import CardNotPresent.webservice.generate_links_inner_models.ModelSendIntegerTerminal;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {
    //TODO Post Request through server via retrofit library

    @POST("GetToken")
    Call<Post> retrieveTokenInterface(@Body Post post);

    @POST("ChangePassword")
    Call<Change_Password_Model> changePasswordWithAccessToken(@Body Change_Password_Model change_password_model);

    @POST("ForgotPassword")
    Call<Forget_Password_Model> forgetPasswordWithAccessToken(@Body Forget_Password_Model forget_password_model);

    @POST("Login")
    Call<Post> loginWithAccessToken(@Body Post post);

    @POST("GenerateLink")
    Call<ModelParent> generateLink(@Body ModelParent modelParent);

    @POST("GeneratePersonalPaymentLink")
    Call<ModelParent> generatePersonalPaymentLink(@Body ModelSendIntegerTerminal modelSendIntegerTerminal);

    @POST("GenerateProductPaymentLink")
    Call<ModelParent> generateProductPaymentLink(@Body ModelSendIntegerTerminal modelSendIntegerTerminal);

    @POST("MerchantProfile")
    Call<ModelParentProfile> retrieveProfileData(@Body ModelParentProfile modelParentProfile);

    @POST("DeletePerson")
    Call<PersonsModel> deleteUserData(@Body PersonsModel personsModel);

    @POST("DeleteProduct")
    Call<ProductModel> deleteProductrData(@Body ProductModel productModel);

    @POST("AddPersonal")
    Call<PersonsModel> addPerson(@Body PersonsModel personsModel);

    @POST("PersonalList")
    Call<Model_ResultStatus> getPersonalListData(@Body Model_ResultStatus model_resultStatus);

    @POST("PersonalTransactionLogList")
    Call<Model_ResultStatus_Per_Trans> getPersonalTransactionListData(@Body Model_ResultStatus_Per_Trans PersonsModel);

    @POST("PersonalTransactionLogStatusList")
    Call<Model_ResultStatus_Per_Conditions> getPersonalConditionListData(@Body Model_ResultStatus_Per_Conditions model_resultStatus_per_conditions);

    @POST("AddProduct")
    Call<ProductModel> addProduct(@Body ProductModel productModel);

    @POST("ProductList")
    Call<Model_ResulrStatus_product> getProductListData(@Body Model_ResulrStatus_product model_resulrStatus_product);

    @POST("ProductTransactionLogList")
    Call<Model_ResucltStatus_transaction_List> getProductTransactionListData(@Body Model_ResucltStatus_transaction_List model_resulrStatus_product);

    @POST("ProductTransactionLogStatusList")
    Call<Model_ResucltStatus_transaction_Condition> getProductTransactionConditionData(@Body Model_ResucltStatus_transaction_Condition PersonsModel);




    @POST("AbsentPaymentLinksList")
    Call<AbsentLinkListModel> getAllLinks(@Body AbsentLinkListModel model_resultStatus);

    @POST("AbsentPaymentTransactionLogList")
    Call<Link_ResultStatus_Model> getLinkTransactionData(@Body Link_ResultStatus_Model model_resultStatus);

    @POST("AbsentPaymentTransactionLogStatusList")
    Call<AbsentStatusModel> getPaymentLinkStatus(@Body AbsentStatusModel absentStatusModel);
}
