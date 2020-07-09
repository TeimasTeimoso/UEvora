package so2.t2.OcupacaoDeEspacos.models;


import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/* Entidade que representa uma um Registo de Ocupação */
@Entity
public class Occupation {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long entryId;
    @Column(nullable = false)
    private String userId;
    @ManyToOne
    @JoinColumn(name = "id")
    private Corporation corpId;
    @Column(nullable = false)
    private String locat;
    @Column(nullable =  false)
    private String level;
    @Column(nullable = false)
    private LocalDate day;
    @Column(nullable = false)
    private LocalTime hours;

    public Occupation(Corporation corpId, String userId, String locat, String level, LocalDate day, LocalTime hours) {
        this.corpId = corpId;
        this.userId = userId;
        this.locat = locat;
        this.level = level;
        this.day = day;
        this.hours = hours;
    }

    public Occupation () {}


    public Long getEntryId() {
        return this.entryId;
    }

    public Corporation getId() {
        return this.corpId;
    }

    public void setId(Corporation corpId) {
        this.corpId = corpId;
    }

    public String getLocat() {
        return this.locat;
    }

    public void setLocat(String locat) {
        this.locat = locat;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public LocalDate getDay() {
        return this.day;
    }

    public void setDay(LocalDate day) {
        this.day = day; 
    }

    public LocalTime getHours() {
        return this.hours;
    }

    public void setHours(LocalTime hours) {
        this.hours = hours;
    }


    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}