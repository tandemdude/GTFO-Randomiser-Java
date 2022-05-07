package io.github.tandemdude.gtforandomiser.models.db;

import io.github.tandemdude.gtforandomiser.models.data.FullLoadout;
import io.github.tandemdude.gtforandomiser.models.data.Stage;
import io.github.tandemdude.gtforandomiser.repositories.FullLoadoutConverter;
import io.github.tandemdude.gtforandomiser.repositories.StageConverter;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "dailies", schema = "randomiser")
public class Daily {
    @Id
    @Column(length = 10)
    private String id;

    @Convert(converter = FullLoadoutConverter.class)
    @Column(columnDefinition = "text")
    private FullLoadout loadout;
    @Convert(converter = StageConverter.class)
    private Stage stage;

    protected Daily() {}

    public Daily(String id, FullLoadout loadout, Stage stage) {
        this.id = id;
        this.loadout = loadout;
        this.stage = stage;
    }

    @Override
    public String toString() {
        return String.format(
                "Daily[%s, %s]",
                id, stage.getStage()
        );
    }
}
