package by.kudko.SpringBatch.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BEntity {

    private String name;
    private String surname;
    private String nickName;

    public User(int id, String name, String surname, String nickName) {
        super(id);
        this.name = name;
        this.surname = surname;
        this.nickName = nickName;
    }
}

