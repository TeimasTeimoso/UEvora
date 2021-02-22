package so2.t2.OcupacaoDeEspacos.models;

import java.time.LocalDate;
import java.time.LocalTime;

/* Objeto usado para guardar um registo
de ocupacao apos um join entre o a tabela de registos
e a tabela de empresas (obtendo assim o nome) */
public class RecordPOJO {
    
    private Long entryId;
    private String userId;
    private String corpName;
    private String locat;
    private String level;
    private LocalDate day;
    private LocalTime hours;

    public RecordPOJO() {}

    public RecordPOJO(Long entryId, String userId, String corpName, String locat, String level, LocalDate day, LocalTime hours) {
        this.entryId = entryId;
        this.userId = userId;
        this.corpName = corpName;
        this.locat = locat;
        this.level = level;
        this.day = day;
        this.hours = hours;
    }

    public Long getEntryId() {
        return this.entryId;
    }

    public void setEntryId(Long entryId) {
        this.entryId = entryId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCorpName() {
        return this.corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
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
}