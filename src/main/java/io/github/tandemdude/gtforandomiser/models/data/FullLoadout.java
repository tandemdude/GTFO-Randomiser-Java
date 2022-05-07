package io.github.tandemdude.gtforandomiser.models.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullLoadout {
    private Loadout player1;
    private Loadout player2;
    private Loadout player3;
    private Loadout player4;
}
