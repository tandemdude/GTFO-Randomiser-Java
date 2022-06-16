<!DOCTYPE html>
<html lang="en">

<#import "components.ftlh" as components>

<head>
    <@components.header/>
    <script src="js/get_data.js"></script>
    <#if message??>
    <script>
        setTimeout(function() {alert("${message}");}, 600)
    </script>
    </#if>
</head>

<body id="page-top">
<@components.nav userId=userId path="root"/>
<section class="features-boxed" style="padding: 95px -2px;padding-bottom: 50px;">
    <div class="container">
        <div class="intro">
            <h3 class="text-center">GENERATE A RANDOM LOADOUT</h3>
        </div>
        <h5 class="text-center" id="stage" style="color: #99aab5;padding-top: 10px;">STAGE: ...<br>DIFFICULTY: ...</h5>
        <div class="row justify-content-center features">
            <div class="col-sm-6 col-md-5 col-lg-4 item">
                <div class="box">
                    <h3 class="name">Player 1</h3>
                    <p class="description" id="1">Primary: ...<br>Secondary: ...<br>Tool: ...<br>Melee: ...</p>
                </div>
            </div>
            <div class="col-sm-6 col-md-5 col-lg-4 item">
                <div class="box">
                    <h3 class="name">Player 2</h3>
                    <p class="description" id="2">Primary: ...<br>Secondary: ...<br>Tool: ...<br>Melee: ...<br></p>
                </div>
            </div>
            <div class="col-sm-6 col-md-5 col-lg-4 item">
                <div class="box">
                    <h3 class="name">Player 3</h3>
                    <p class="description" id="3">Primary: ...<br>Secondary: ...<br>Tool: ...<br>Melee: ...<br></p>
                </div>
            </div>
            <div class="col-sm-6 col-md-5 col-lg-4 item">
                <div class="box">
                    <h3 class="name">Player 4</h3>
                    <p class="description" id="4">Primary: ...<br>Secondary: ...<br>Tool: ...<br>Melee: ...<br></p>
                </div>
            </div>
        </div>
    </div>
    <div class="btn-group" role="group">
        <button class="btn btn-dark" type="button" onclick="get_loadout(1)">Rundown 1</button>
        <button class="btn btn-dark" type="button" onclick="get_loadout(2)">Rundown 2</button>
        <button class="btn btn-dark" type="button" onclick="get_loadout(3)">Rundown 3</button>
        <button class="btn btn-dark" type="button" onclick="get_loadout(4)">Rundown 4</button>
        <button class="btn btn-dark" type="button" onclick="get_loadout(5)">Rundown 5</button>
        <button class="btn btn-dark" type="button" onclick="get_loadout(6)">Rundown 6</button>
        <button class="btn btn-dark" type="button" onclick="get_loadout(7)">Rundown 6</button>
    </div>
</section>
<hr style="width: 80%">
<h3 class="text-center">HANDICAPS</h3>
<h6 class="text-center">Game too easy? use a handicap to make it more of a challenge</h6>
<h3 class="text-center" style="padding-top: 20px;">Current Handicap:</h3>
<h6 class="text-center" style="color: #99aab5" id="handicap">...</h6>
<div class="btn-group" role="group" style="padding: 20px">
    <button class="btn btn-dark" type="button" onclick="get_handicap()" style="max-width: 30%; margin: auto;">Reroll</button>
</div>
<@components.footer/>
</body>
</html>