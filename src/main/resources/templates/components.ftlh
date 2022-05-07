<#macro header>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>GTFO Randomiser</title>
    <meta name="description" content="Loadout and stage randomiser with daily challenges and leaderboards for the popular hardcore horror game GTFO">
    <meta name="keywords" content="GTFO, randomiser, leaderboards, daily challenges">
    <meta name="google-site-verification" content="lIl1fAAc25njR2l-8ABjI-berpd6Ws12nE6imKAGdP0"/>
    <meta property="og:title" content="GTFO Loadout Randomiser">
    <meta property="og:description" content="Loadout and stage randomiser for the popular hardcore horror game GTFO">
    <meta property="og:url" content="https://gtfo-randomiser.herokuapp.com/">
    <meta property="og:site_name" content="GTFO Loadout Randomiser">
    <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat:400,700">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lato:400,700,400italic,700italic">
    <link rel="stylesheet" href="fonts/font-awesome.min.css">
    <link rel="stylesheet" href="css/Features-Boxed-1.css">
    <link rel="stylesheet" href="css/Features-Boxed.css">
    <link rel="stylesheet" href="css/untitled.css">
</#macro>

<#macro navLinkFor path label>
    <li class="nav-item mx-0 mx-lg-1"><a class="nav-link py-3 px-0 px-lg-3 rounded js-scroll-trigger" href="${path}">${label}</a></li>
</#macro>

<#macro nav path userId="">
    <nav class="navbar navbar-light navbar-expand-lg fixed-top bg-secondary text-uppercase" id="mainNav">
        <div class="container"><a class="navbar-brand js-scroll-trigger" href="#page-top">GTFO Randomiser</a><button data-toggle="collapse" data-target="#navbarResponsive" class="navbar-toggler text-white bg-primary navbar-toggler-right text-uppercase rounded" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation"><i class="fa fa-bars"></i></button>
            <div class="collapse navbar-collapse" id="navbarResponsive">
                <ul class="navbar-nav ml-auto">
                    <#if path == "daily">
                        <@navLinkFor path="/" label="Home"/>
                    <#else>
                        <@navLinkFor path="/daily" label="Daily"/>
                    </#if>
                    <@navLinkFor path="https://www.youtube.com/watch?v=dQw4w9WgXcQ" label="Weekly"/>
                    <#if !userId?has_content>
                        <@navLinkFor path="/oauth/discord/authRedirect" label="Log In"/>
                    <#elseif path="profile">
                        <@navLinkFor path="/" label="Home"/>
                    <#else>
                        <@navLinkFor path="/profile" label="Profile"/>
                    </#if>
                </ul>
            </div>
        </div>
    </nav>
    <header class="text-center text-white bg-primary masthead" style="padding: 0 0 50px; background-color: #313437"></header>
</#macro>

<#macro footer>
    <div class="text-center text-white copyright py-4">
        <div class="container">
            <small style="opacity: 0.50;">
                "GTFO" is a registered trademark or trademark of 10 Chambers AB in various territories worldwide.<br>
                This website is not directly affiliated with 10 Chambers AB in any way.<br>
                <br>Join the <a href="https://discord.gg/bQn6DYyt5X" target="_blank">Discord Server</a> to chat with other
                players.<br>
            </small>
        </div>
    </div>
    <script src="bootstrap/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js"></script>
    <script src="js/freelancer.js"></script>
</#macro>