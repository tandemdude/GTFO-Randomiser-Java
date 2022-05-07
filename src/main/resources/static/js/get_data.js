function get_loadout(rundown_id) {
    fetch(`/api/v1/rundown/${rundown_id}/fullLoadoutWithStage`)
        .then(response => response.json())
        .then(
            data => {
                for (let key of ["player1", "player2", "player3", "player4"]) {
                    document.getElementById(key.substr(6)).innerHTML = `
                        Primary: <span style="color: #18bc9c;">${data.loadouts[key].primary}</span>
                        <br>Secondary: <span style="color: #18bc9c;">${data.loadouts[key].secondary}</span>
                        <br>Tool: <span style="color: #18bc9c;">${data.loadouts[key].tool}</span>
                        <br>Melee: <span style="color: #18bc9c;">${data.loadouts[key].melee}</span>
                    `;
                }
                document.getElementById("stage").innerHTML = `
                    STAGE: <span style="color: #18bc9c;">${data.stage.stage}</span>
                    <br>DIFFICULTY: <span style="color: #18bc9c;">${data.stage.difficulty}</span>
                `
            }
        )
}

function get_handicap() {
    fetch("/api/v1/rundown/handicap")
        .then(response => response.json())
        .then(
            data => {
                document.getElementById("handicap").innerHTML = data.handicap
            }
        )
}
