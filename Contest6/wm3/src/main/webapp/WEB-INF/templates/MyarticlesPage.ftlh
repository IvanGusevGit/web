<#import "common.ftl" as c>

<@c.page>

<div class="datatable articles">
    <div class="caption">Users</div>
    <table>
        <thead>
        <tr>
            <th>Id</th>
            <th>Title</th>
            <th>Visibility</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td colspan="3" class="no-data">No data</td>
        </tr>
        </tbody>
    </table>
</div>


<template class="articles-tr">
    <tr>
        <td class="id"></td>
        <td class="title"></td>
        <td class="visibility">
            <div class="button-field">
                <input type="submit" class=setVisibility>
            </div>
        </td>
    </tr>
</template>

<script>
    $(function () {
        var $noData = $(".articles .no-data");
        $noData.text("Loading...");
        $.post("", {action: "find"}, function (articles) {
            var $tbody = $(".articles tbody");
            if (articles.length > 0) {
                $tbody.empty();
                articles.forEach(function (article) {
                    var $tr = cloneTemplate($("template.articles-tr"));
                    $tr.find(".id").text(article.id);
                    $tr.find(".title").text(article.title);
                    if (article.hidden) {
                        $tr.find(".setVisibility").attr("value", "show");
                    } else {
                        $tr.find(".setVisibility").attr("value", "hide");
                    }
                    var articleId = $tr.find(".id").html();
                    $tr.find(".setVisibility").click(function () {
                        var $button = $(this);
                        var previousOption = this.value;
                        $button.attr("value", "wait");
                        $button.attr("disabled", "disabled");
                        alert(previousOption);
                        var newOption = (previousOption === "show" ? "hide" : "show");
                        alert(newOption);
                        $.post("", {action: "setVisibility", articleId: articleId}, function (response) {
                            if (response["success"]) {
                                $button.attr("value", newOption.toString());
                            }
                            $button.removeAttr("disabled");
                        });
                        return false;
                    });
                    $tbody.append($tr);
                });
            } else {
                $noData.text("No data");
            }
        })
    });
</script>
</@c.page>