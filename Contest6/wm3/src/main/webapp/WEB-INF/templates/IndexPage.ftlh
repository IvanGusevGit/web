<#-- @ftlvariable name="message" type="java.lang.String" -->

<#import "common.ftl" as c>

<@c.page>
    <#if message??>
        <p>${message}</p>
    </#if>

    <div class="articles">
        <div class="status"></div>
    </div>

    <template class="article-template">
        <div class="title"></div>
        <div class="information"></div>
        <div class="body"></div>
        <div class="footer">
            <div class="left">
                <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                <span class="positive-score">+173</span>
                <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
            </div>
            <div class="right">
                <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                <div class="creation-time"></div>
                <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
            </div>
        </div>
    </template>

    <script>
        $(function () {
            var $status = $(".articles status");
            $status.text("Updating list of articles...");
            var $articlesArea = $(".articles");
            $.post("", {action: "loadArticles"}, function (articles) {
                if (articles.length > 0) {
                    articles.forEach(function (article) {
                        var $newArticle = cloneTemplate($(".article-template"));
                        $newArticle.find(".title").text(article.title);
                        $newArticle.find(".information").text("By " + article.author);
                        $newArticle.find(".body").text(article.text);
                        $newArticle.find(".title").text(article.title);
                        $newArticle.find(".creation-time").text(article.creationTime);
                        $articlesArea.append($newArticle);
                    });
                } else  {
                    $status.text("No articles were published");
                }
            });
        })
    </script>

</@c.page>
