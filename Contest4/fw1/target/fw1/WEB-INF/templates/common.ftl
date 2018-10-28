
<#macro page>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Codeforces</title>
    <link rel="stylesheet" type="text/css" href="/css/normalize.css">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link rel="stylesheet" type="text/css" href="/css/form.css">
    <link rel="stylesheet" type="text/css" href="/css/datatable.css">
</head>
<body>
<header>
    <a href="/"><img src="/img/logo.png" alt="Codeforces" title="Codeforces"/></a>
    <div class="languages">
        <a href="#"><img src="/img/gb.png" alt="In English" title="In English"/></a>
        <a href="#"><img src="/img/ru.png" alt="In Russian" title="In Russian"/></a>
    </div>
    <#if username??>
        <div class="login-logout-box">
            <a href="/users">${username}</a>
            |
            <a href="/addNews">Submit news</a>
            |
            <a href="/logout">Logout</a>
        </div>
    <#else>
        <div class="enter-or-register-box">
            <a href="/enter">Enter</a>
            |
            <a href="/register">Register</a>
        </div>
    </#if>
    <nav>
        <ul>
            <li><a href="/">Home</a></li>
            <li><a href="/top">Top</a></li>
            <li><a href="/contests">Contests</a></li>
            <li><a href="/gym">Gym</a></li>
            <li><a href="/problemset">Problemset</a></li>
            <li><a href="/groups">Groups</a></li>
            <li><a href="/rating">Rating</a></li>
        </ul>
    </nav>
</header>
<div class="middle">
    <aside>
        <section>
            <div class="header">
                Pay attention
            </div>
            <div class="body">
                Lorem ipsum dolor sit amet, consectetur adipisicing elit. Cupiditate ducimus enim facere impedit nobis,
                nulla placeat quam suscipit unde voluptatibus.
            </div>
            <div class="footer">
                <a href="#">View all</a>
            </div>
        </section>
        <section class="newsList">
            <div class="header">
                Recent news
            </div>
            <div class="body">
                <#list newsList as news>
                    <div class="newsSection">
                        <div class="header">${userService.findById(news.userId).login}</div>
                        <div class="article">${news.text}</div>
                    </div>
                </#list>
            </div>
        </section>
    </aside>
    <main>
        <#nested/>
    </main>
</div>
<footer>
    <a href="#">Codeforces</a> &copy; 2010-2018 by Mike Mirzayanov ${text!}
    <div class="userCount">
        Website is used by <a href="/users">${userCount} users</a>.
    </div>
</footer>
</body>
</html>
</#macro>
