package by.kudko.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Hotel extends BEntity {

    private String name;
    private Integer stars;

    public Hotel(int id, String name, Integer stars) {
        super(id);
        this.name = name;
        this.stars = stars;
    }
}

