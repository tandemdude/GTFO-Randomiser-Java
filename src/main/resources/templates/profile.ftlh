<!DOCTYPE html>
<html lang="en" style="height: 100%;">

<#import "components.ftlh" as components>

<head>
    <@components.header/>
</head>

<body id="page-top" style="display: flex; flex-direction: column; height: 100%;">
<div style="flex: 1 0 auto;">
    <@components.nav userId=user.getId() path="profile"/>
    <section class="features-boxed" style="padding: 95px 0 20px;">
        <div class="container">
            <div class="intro">
                <h3 class="text-center">PROFILE</h3><br>
                <h6 class="text-center" style="color: #99aab5;">Logged In As: <span
                            style="color: #5865F2;">${user.getUsername()}#${user.getDiscriminator()}</span></h6>
                <h6 class="text-center" style="color: #99aab5;">Not you? <a href="/logout">Log Out</a></h6>
            </div>
        </div>
    </section>
    <hr style="width: 80%;">
    <h3 style="margin: auto; text-align: center; padding: 20px">Your Daily Runs</h3>
    <div style="overflow-x:auto; padding-bottom: 50px">
        <table>
            <tr>
                <th class="table-header">Date</th>
                <th class="table-header">Completion Time</th>
                <th class="table-header">Evidence</th>
                <th class="table-header">Verified</th>
                <th class="table-header">ID</th>
            </tr>
            <#list submissions as submission>
                <tr>
                    <th>${submission.getSubmittedFor().getId()}</th>
                    <th>${submission.getFormattedTime()}</th>
                    <th><a href="${submission.getEvidenceUrl()}" target="_blank">Click Here</a></th>
                    <th>${submission.isVerified()?then("Yes", "No")}</th>
                    <th>${submission.getId()}</th>
                </tr>
            </#list>
        </table>
    </div>
</div>
<@components.footer/>
</body>
</html>