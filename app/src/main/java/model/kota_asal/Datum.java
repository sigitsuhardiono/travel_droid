
package model.kota_asal;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Datum {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("id_kota")
    @Expose
    private String idKota;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("is_bandara")
    @Expose
    private String isBandara;

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The idKota
     */
    public String getIdKota() {
        return idKota;
    }

    /**
     * 
     * @param idKota
     *     The id_kota
     */
    public void setIdKota(String idKota) {
        this.idKota = idKota;
    }

    /**
     * 
     * @return
     *     The nama
     */
    public String getNama() {
        return nama;
    }

    /**
     * 
     * @param nama
     *     The nama
     */
    public void setNama(String nama) {
        this.nama = nama;
    }

    /**
     * 
     * @return
     *     The isBandara
     */
    public String getIsBandara() {
        return isBandara;
    }

    /**
     * 
     * @param isBandara
     *     The is_bandara
     */
    public void setIsBandara(String isBandara) {
        this.isBandara = isBandara;
    }

}
