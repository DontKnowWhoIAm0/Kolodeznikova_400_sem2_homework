<div class="notification_item">
    <div class="notification_info">
        <p class="notification_text">${notification.text}</p>

        <p class="notification_date">
            <#if notification.createdDate??>
                <#assign createdDate = notification.createdDate?date("yyyy-MM-dd")>
                ${notification.createdDate?string("d MMM y 'г.'")}
            </#if>
        </p>
    </div>
</div>