<!--${currentDate}-->
<!--Generisano na osnovu sablona: main.ftl-->

<!DOCTYPE html>

<html>
    <head>
        <title>${r"#{get"} 'title' /}</title>
        <meta charset="${r"${_response_encoding}"}">
        <link rel="stylesheet" media="screen" href="@{'/public/stylesheets/main.css'}">
        <link rel="stylesheet" media="screen" href="@{'/public/stylesheets/bootstrap.css'}">
        <link rel="stylesheet" media="screen" href="@{'/public/stylesheets/carousel.css'}">
        <link rel="stylesheet" media="screen" href="@{'/public/stylesheets/navBar_help.css'}">
        ${r"#{get"} 'moreStyles' /}
        <link rel="shortcut icon" type="image/png" href="@{'/public/images/favicon.png'}">

        <script src="@{'/public/javascripts/jquery-3.1.1.min.js'}" type="text/javascript" charset="${r"${_response_encoding}"}"></script>
        <script src="https://npmcdn.com/tether@1.2.4/dist/js/tether.min.js"></script>
    	<script src="@{'/public/javascripts/bootstrap.min.js'}"></script>
    	${r"#{get"} 'moreScripts' /}
    </head>
    <body>
        ${r"#{doLayout /}"}
    </body>
</html>
