<#import "common.ftl" as c>

<@c.page>
<form method="post" action="/addNews">
    <div class="newsArea">
        <input type="hidden" name="action" value="submitNews">
        <p>News editing</p>
        <div class="textEditing">
            <textarea name="newsText">Enter your news here</textarea>
        </div>
        <input type="submit" value="Submit">
    </div>
</form>
</@c.page>