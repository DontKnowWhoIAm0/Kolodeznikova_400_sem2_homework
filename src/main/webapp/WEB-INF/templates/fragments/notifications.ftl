<div id="notifications" class="notifications">
    <div class="notification-header">
        <span>Уведомления</span>
    </div>

    <ul class="notification-list">
        <#if notifications?? && notifications?size > 0>
            <#list notifications[0..2] as notification>
                <li class="notification-item">
                    ${notification.text}
                </li>
            </#list>
        <#else>
            <li class="notification-item empty">Нет уведомлений</li>
        </#if>
    </ul>
</div>
