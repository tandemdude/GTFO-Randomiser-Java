<!DOCTYPE html>
<html lang="en" style="height: 100%;">

<#import "components.ftlh" as components>

<head>
    <@components.header/>
    <script>
        setTimeout(
            function() {
                window.location.href = "/";
            }, 2000
        )
    </script>
</head>

<body id="page-top" style="display: flex; flex-direction: column; height: 100%;">
    <div style="flex: 1 0 auto;">
        <@components.nav userId="" path="logout"/>
        <section class="features-boxed" style="padding: 95px 0 20px;">
            <div class="container">
                <div class="intro">
                    <h3 class="text-center">YOU HAVE BEEN LOGGED OUT</h3>
                </div>
            </div>
        </section>
        <h6 style="margin: auto; text-align: center; color: #99aab5; padding: 20px;">If you are not redirected automatically, press the button below.</h6>
        <div class="btn-group" role="group">
            <button class="btn btn-dark" type="button" onclick="window.location.href = '/'">Return Home</button>
        </div>
    </div>
    <@components.footer/>
</body>
</html>