package by.kudko.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User extends BEntity {

    private String name;
    private String surname;
    @Column(name = "nickname")
    private String nickName;

    public User(int id, String name, String surname, String nickName) {
        super(id);
        this.name = name;
        this.surname = surname;
        this.nickName = nickName;
    }
}

