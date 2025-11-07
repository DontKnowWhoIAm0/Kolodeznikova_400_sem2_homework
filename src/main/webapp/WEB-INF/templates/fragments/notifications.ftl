<div id="notifications" class="notifications">
    <div class="notification_header">
        <span>Уведомления</span>
    </div>

    <ul class="notification_list">
        <#if notifications?? && notifications?size > 0>
            <#list notifications[0..2] as notification>
                <li class="notification_item">
                    ${notification.text}
                </li>
            </#list>
        <#else>
            <li class="notification_item empty">Нет уведомлений</li>
        </#if>
    </ul>
</div>
