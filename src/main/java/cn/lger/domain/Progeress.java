package cn.lger.domain;

import javax.persistence.*;
import java.util.Map;

@Entity

public class Progeress {

    final static public String[] progressName = new String []{"报名","报名审核","付款","证书制作","完成报名","驳回"};
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String  name;
    private String  status;
    @ElementCollection
    private Map<String,String>  progresNote;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, String> getProgresNote() {
        return progresNote;
    }

    public void setProgresNote(Map<String, String> progresNote) {
        this.progresNote = progresNote;
    }
}
