package CardNotPresent.webservice.generate_links_inner_models;

import com.google.gson.annotations.SerializedName;

//TODO Model PSP for filling psp names list
public class Model_PSP_List
{

    @SerializedName("Id")
    private String id;

    @SerializedName("PspName")
    private String PspName;

    @SerializedName("Description")
    private String Description;


    public Model_PSP_List(String id, String pspName, String description) {
        this.id = id;
        this.PspName = pspName;
        this.Description = description;
    }

    public String getId() {
        return id;
    }

    public String getPspName() {
        return PspName;
    }

    public String getDescription() {
        return Description;
    }
}
