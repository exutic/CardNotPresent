package CardNotPresent.webservice.generate_links_inner_models;

import com.google.gson.annotations.SerializedName;

//TODO Model Terminal for filling terminal list ==> Generating links
public class Model_Terminal_List {

    @SerializedName("UserId")
    private String UserId;
    @SerializedName("‫‪MerchantId")
    private String MerchantId;
    @SerializedName("TerminalId")
    private String TerminalId;
    @SerializedName("PspType")
    private String PspType;
    @SerializedName("IpgUsername")
    private String IpfUsername;
    @SerializedName("IpgPassword")
    private String IpgPassword;
    @SerializedName("Id")
    private String id;

    public Model_Terminal_List(String id, String userId, String terminalId, String merchantId, String pspType, String ipfUsername, String ipgPassword) {
        this.id = id;
        this.UserId = userId;
        this.TerminalId = terminalId;
        this.MerchantId = merchantId;
        this.PspType = pspType;
        this.IpfUsername = ipfUsername;
        this.IpgPassword = ipgPassword;
    }

    public Model_Terminal_List(String id, String terminalId, String pspType) {
        this.id = id;
        TerminalId = terminalId;
        PspType = pspType;
    }

    public String getId() {
        return id;
    }
    public String getUserId() {
        return UserId;
    }
    public String getTerminalId() {
        return TerminalId;
    }
    public String getMerchantId() {
        return MerchantId;
    }
    public String getPspType() {
        return PspType;
    }
    public String getIpfUsername() {
        return IpfUsername;
    }
    public String getIpgPassword() {
        return IpgPassword;
    }
}
