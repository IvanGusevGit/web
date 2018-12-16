<#import "common.ftl" as c>

<@c.page>
<div class="article creation">
    <form method="post" action="/article">
        <input type="hidden" name="action" value="submitArticle">
        <div class="mainHeader">Create an article</div>
        <div class="field">
            <div class="title">
                <div class="title header">Title</div>
                <div class="value">
                    <input id="title" name="title" value="${title!}">
                </div>
            </div>
        </div>
        <div class="field">
            <div class="content">
                <div class="content header">Content</div>
                <div class="value">
                    <textarea name="content">${content!}</textarea>
                </div>
            </div>
        </div>
        <div class="submit-button">
            <input type="submit" value="Submit">
        </div>

        <div class="error"></div>
    </form>
</div>
<script>
    $(function() {
        $(".article form").submit(function () {
            var title = $(this).find("input[name='title']").val();
            var content = $(this).find("textarea[name='content']").val();
            var $error = $(this).find(".error").text("");
            $.post("", {action: "submitArticle", title: title, content: content}, function (response) {
                if (response["success"]) {
                    setMessage("You have successfully submitted an article");
                    document.location.href = "/";
                } else {
                    $error.text(response["error"]);
                }
            });
            return false;
        });
    })
</script>
</@c.page>

