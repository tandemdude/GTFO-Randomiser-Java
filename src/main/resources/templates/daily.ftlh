<!DOCTYPE html>
<html lang="en">

<#import "components.ftlh" as components>

<head>
    <@components.header/>
    <script src="js/jquery.min.js"></script>
    <script src="js/countdown.js"></script>
    <script src="js/submission.js"></script>
</head>

<body id="page-top">
<#if userId??>
    <div id="overlay-form">
        <div class="inner-form">
            <form action="/api/v1/daily/submission" method="POST" style="color: #99aab5;" id="submissionForm">
                <b style="font-size: 25px; color: #5865F2">Complete the form below to submit your time to the
                    leaderboard:</b><br><br>
                Time to complete:<br>
                <input class="form-input" type="number" id="hours" name="hours" min="0" max="23" required><label
                        for="h">h</label>
                <input class="form-input" type="number" id="minutes" name="minutes" min="0" max="59" required><label
                        for="m">m</label>
                <input class="form-input" type="number" id="seconds" name="seconds" min="0" max="59" required><label
                        for="s">s</label><br>
                Evidence URL:<br>
                <input class="form-input" type="url" id="evidenceUrl" name="evidenceUrl" required><label for="evidenceUrl"></label>
                <input type="hidden" id="userId" name="userId" value="${userId}">
                <br>
                <div class="btn-group" role="group" style="margin: auto; width: 80%">
                    <button class="btn btn-dark" type="button" onclick="hideform()">Cancel</button>
                    <input type="submit" value="Submit" class="btn btn-dark">
                </div>
            </form>
        </div>
    </div>
</#if>
<@components.nav userId=userId path="daily"/>
<section class="features-boxed" style="padding: 95px 0 20px;">
    <div class="container">
        <div class="intro">
            <h3 class="text-center">DAILY CHALLENGE</h3>
        </div>
        <h5 class="text-center" id="stage" style="color: #99aab5;padding-top: 10px;">STAGE: <span
                    style="color: #18bc9c;">${dailyRun.getStage().getStage()}</span>
            <br>DIFFICULTY: <span style="color: #18bc9c;">${dailyRun.getStage().getDifficulty()}</span>
        </h5>
        <div class="row justify-content-center features">
            <div class="col-sm-6 col-md-5 col-lg-4 item">
                <div class="box">
                    <h3 class="name">Player 1</h3>
                    <p class="description" id="1">Primary: <span style="color: #18bc9c;">${dailyRun.getLoadout().getPlayer1().getPrimary()}</span>
                        <br>Secondary: <span style="color: #18bc9c;">${dailyRun.getLoadout().getPlayer1().getSecondary()}</span>
                        <br>Tool: <span style="color: #18bc9c;">${dailyRun.getLoadout().getPlayer1().getTool()}</span>
                        <br>Melee: <span style="color: #18bc9c;">${dailyRun.getLoadout().getPlayer1().getMelee()}</span>
                    </p>
                </div>
            </div>
            <div class="col-sm-6 col-md-5 col-lg-4 item">
                <div class="box">
                    <h3 class="name">Player 2</h3>
                    <p class="description" id="2">Primary: <span style="color: #18bc9c;">${dailyRun.getLoadout().getPlayer2().getPrimary()}</span>
                        <br>Secondary: <span style="color: #18bc9c;">${dailyRun.getLoadout().getPlayer2().getSecondary()}</span>
                        <br>Tool: <span style="color: #18bc9c;">${dailyRun.getLoadout().getPlayer2().getTool()}</span>
                        <br>Melee: <span style="color: #18bc9c;">${dailyRun.getLoadout().getPlayer2().getMelee()}</span>
                    </p>
                </div>
            </div>
            <div class="col-sm-6 col-md-5 col-lg-4 item">
                <div class="box">
                    <h3 class="name">Player 3</h3>
                    <p class="description" id="3">Primary: <span style="color: #18bc9c;">${dailyRun.getLoadout().getPlayer3().getPrimary()}</span>
                        <br>Secondary: <span style="color: #18bc9c;">${dailyRun.getLoadout().getPlayer3().getSecondary()}</span>
                        <br>Tool: <span style="color: #18bc9c;">${dailyRun.getLoadout().getPlayer3().getTool()}</span>
                        <br>Melee: <span style="color: #18bc9c;">${dailyRun.getLoadout().getPlayer3().getMelee()}</span>
                    </p>
                </div>
            </div>
            <div class="col-sm-6 col-md-5 col-lg-4 item">
                <div class="box">
                    <h3 class="name">Player 4</h3>
                    <p class="description" id="4">Primary: <span style="color: #18bc9c;">${dailyRun.getLoadout().getPlayer4().getPrimary()}</span>
                        <br>Secondary: <span style="color: #18bc9c;">${dailyRun.getLoadout().getPlayer4().getSecondary()}</span>
                        <br>Tool: <span style="color: #18bc9c;">${dailyRun.getLoadout().getPlayer4().getTool()}</span>
                        <br>Melee: <span style="color: #18bc9c;">${dailyRun.getLoadout().getPlayer4().getMelee()}</span>
                    </p>
                </div>
            </div>
        </div>
    </div>
</section>
<h3 style="color: #99aab5; margin: auto; text-align: center; padding-bottom: 20px">
    Next Daily In: <span id="countdown" style="color: #18bc9c;">...</span>
</h3>
<hr style="width: 80%;">
<#if userId??>
    <div class="btn-group" role="group">
        <button class="btn btn-dark" type="button" onclick="showform()">Submit a Run</button>
    </div>
<#else>
    <h5 class="text-center">You must be logged in to submit a run.</h5>
</#if>
<h3 class="text-center" style="padding: 20px">LEADERBOARD</h3>
<div style="overflow-x:auto; padding-bottom: 50px;">
    <table>
        <tr>
            <th class="table-header">Submitter</th>
            <th class="table-header">Completion Time</th>
            <th class="table-header">Evidence</th>
            <th class="table-header">Verified</th>
            <th class="table-header">ID</th>
        </tr>
        <#if submissions?has_content>
        <#list submissions as submission>
        <tr>
            <th>${submission.getSubmittedBy().getUsername()}#${submission.getSubmittedBy().getDiscriminator()}</th>
            <th>${submission.getFormattedTime()}</th>
            <th><a href="${submission.getEvidenceUrl()}" target="_blank">Click Here</a></th>
            <th>${submission.isVerified()?then("Yes", "No")}</th>
            <th>${submission.getId()}</th>
        </tr>
        </#list>
        <#else>
        <tr>
            <th>No Data</th>
            <th>00:00:00</th>
            <th><a href="https://www.youtube.com/watch?v=dQw4w9WgXcQ" target="_blank">Click Here</a></th>
            <th>Yes</th>
            <th>No Data</th>
        </tr>
        </#if>
    </table>
</div>
<hr style="width: 80%;">
<h3 class="text-center" style="padding: 20px">YESTERDAY'S WINNER</h3>
<h6 class="text-center" style="color: #99aab5; padding-bottom: 50px">
    <#if yesterdaysBest??>
        Run ID ${yesterdaysBest.getId()} by
        ${yesterdaysBest.getSubmittedBy().getUsername()}#${yesterdaysBest.getSubmittedBy().getDiscriminator()}
        with a time of ${yesterdaysBest.getFormattedTime()}
        <br><a href="${yesterdaysBest.getEvidenceUrl()}" target="_blank">Evidence</a>
    <#else>
        Run ID 69420 by No Winner :( with a time of 00:00:00
    </#if>
</h6>
<@components.footer/>
</body>
</html>