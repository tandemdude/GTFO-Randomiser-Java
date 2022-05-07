package io.github.tandemdude.gtforandomiser.models.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Loadout {
    private String primary;
    private String secondary;
    private String tool;
    private String melee;
}
