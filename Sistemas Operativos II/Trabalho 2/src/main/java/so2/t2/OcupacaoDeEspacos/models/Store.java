package so2.t2.OcupacaoDeEspacos.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


/* Entidade que representa uma loja */
@Entity
public class Store{
    @Id
    private String address;
    @Column(nullable = false)
    private String location;

    @ManyToOne
    @JoinColumn(name = "id") // Nome da coluna associada
    private Corporation corp;

    @Column(unique = true)
    private Double latidude;

    @Column(unique = true)
    private Double longitude;

    protected Store() {};

    public Store(String address, String location, Corporation corp, Double latitude, Double longitude) {
        this.address = address;
        this.location = location;
        this.corp = corp;
        this.latidude = latitude;
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public String getLocation() {
        return location;
    }

    public Corporation getCorporation() {
        return corp;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public Double getLatidude() {
        return this.latidude;
    }

    public void setLatidude(Double latidude) {
        this.latidude = latidude;
    }


    public Double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    
}