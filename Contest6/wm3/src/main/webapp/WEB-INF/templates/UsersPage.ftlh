<#-- @ftlvariable name="users" type="ru.itmo.webmail.model.domain.User[]" -->
<#import "common.ftl" as c>

<@c.page>
<div class="datatable users">
    <div class="caption">Users</div>
    <table>
        <thead>
        <tr>
            <th>#</th>
            <th>Login</th>
            <th>Rating</th>
            <th hidden="" class="adminField">Admin</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td colspan="3" class="no-data">No data</td>
        </tr>
        </tbody>
    </table>
</div>

<template class="users-tr">
    <tr>
        <td class="id">{{#}}</td>
        <td class="login">{{login}}</td>
        <td class="rating"></td>
        <td hidden="" class="adminField">
            <p class="status"></p>
            <input type="submit" class="setAdmin" value="Switch">
            <p class="error"></p>
        </td>
    </tr>
</template>
<script>
    $(function () {
        var $noData = $(".users .no-data");
        $.post("", {action: "isAdmin"}, function (isAdmin) {
            if (isAdmin) {
                $(document).find(".adminField").removeAttr("hidden");
                $(".no-data").attr("colspan", 4);
            } else {
                $(document).find(".adminField").attr("hidden", "");
            }

            $noData.text("Loading...");
            $.post("", {action: "find"}, function (users) {
                var $tbody = $(".users tbody");
                if (users.length > 0) {
                    var logins = [];
                    $tbody.empty();
                    alert("DONE st")
                    users.forEach(function (user) {
                        var $tr = cloneTemplate($("template.users-tr"));
                        $tr.find(".id").text(user.id);
                        $tr.find(".login").text(user.login);
                        $tr.find("tr").attr("id", "user_" + user.login.toLowerCase());
                        if (!isAdmin) {
                            $tr.find(".adminField").remove();
                        } else {
                            $tr.find(".adminField").removeAttr("hidden");
                            $tr.find(".adminField").find(".status").text(user.admin);
                            if (user.admin) {
                                $tr.find(".adminField").find(".setAdmin").attr("value", "Disable");
                            } else {
                                $tr.find(".adminField").find(".setAdmin").attr("value", "Enable");
                            }
                        }
                        var $status = $tr.find(".adminField").find(".status");
                        var $error = $tr.find(".adminField").find(".error");
                        $tr.find(".setAdmin").click(function () {
                            var $button = $(this);
                            var currentOption = this.value;
                            $button.attr("value", "Wait");
                            $button.attr("disabled", "");
                            $.post("", {action: "switchAdminMode", userId : user.id}, function (response) {
                                if (response["success"]) {
                                    if (currentOption.toString() === "Enable") {
                                        $button.attr("value", "Disable");
                                        $status.text("true");
                                    } else {
                                        $button.attr("value", "Enable");
                                        $status.text("false");
                                    }
                                } else {
                                    $button.attr("value", currentOption);
                                    $error.text(response["message"]);
                                }
                                alert("I'm here");
                                $button.removeAttr("disabled");
                            });
                            return false;
                        });
                        logins.push(user.login);
                        $tbody.append($tr);
                    });
                    $.ajax({
                        url: "http://codeforces.com/api/user.info",
                        jsonp: "jsonp",
                        dataType: "jsonp",
                        data: {
                            handles: logins.join(";")
                        },
                        success: function(response) {
                            if (response["status"] === "OK") {
                                response["result"].forEach(function (user) {
                                    $("#user_" + user.handle.toLowerCase()).find(".rating").text(user["rating"]);
                                })
                            }
                        }
                    })
                } else {
                    $noData.text("No data");
                }
            })

        });
    });
</script>
</@c.page>
