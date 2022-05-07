package io.github.tandemdude.gtforandomiser.models.db;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "users", schema = "randomiser")
public class User {
    @Id
    private long id;

    private String username;
    private String discriminator;

    protected User() {}

    public User(long id, String username, String discriminator) {
        this.id = id;
        this.username = username;
        this.discriminator = discriminator;
    }

    @Override
    public String toString() {
        return String.format(
                "User[%s, %s#%s]",
                id, username, discriminator
        );
    }
}
