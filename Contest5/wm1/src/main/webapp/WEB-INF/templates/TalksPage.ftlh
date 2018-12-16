<#import "common.ftl" as c>

<@c.page>
<div class="talkArea">
    <form method="post" action="/talks">
        <div class="newTalkArea">
            <input type="hidden" name="action" value="submitTalk">
            <p>Select receiver</p>
            <div class="targetSelect">
                <select name="targetSelect" id="targetSelect">
                    <option value="0">...</option>
                    <#list users as user>
                        <option value="${user.id}">${user.login}</option>
                    </#list>
                </select>
            </div>
            <p>Print your message</p>
            <div class="textEditing">
                <textarea name="messageText">Enter your message here</textarea>
                <input id="submitButton" type="submit" value="Submit">
            </div>
        </div>
    </form>
    <div class="talkList">
        <div class="datatable">
            <div class="caption">Messages</div>
            <table>
                <thead>
                <tr>
                    <th>id</th>
                    <th>From</th>
                    <th>To</th>
                    <th>Content</th>
                    <th>Time</th>
                </tr>
                </thead>
                <tbody>
                    <#list messages as message>
                    <tr>
                        <td>
                            ${message.id}
                        </td>
                        <td>
                            ${message.from}
                        </td>
                        <td>
                            ${message.to}
                        </td>
                        <td>
                            ${message.content}
                        </td>
                        <td>
                            ${message.time}
                        </td>
                    </tr>
                    </#list>
            </table>
        </div>
    </div>
</div>
</@c.page>
