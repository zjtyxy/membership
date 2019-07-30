package cn.lger.domain;

import javax.persistence.*;
import java.util.Map;
@Entity
public class DayTrip {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private  String title;
    @ElementCollection
    private Map<String,String> content;
}
