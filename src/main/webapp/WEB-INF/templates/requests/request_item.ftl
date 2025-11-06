
<div class="request-item">
    <div class="profile-info">
        <img src="${creator.profileImageUrl}">
        <p class="nickname">${creator.nickname}</p>
    </div>

    <div class="request-info">
        <h3 class="sport">${request.sport.getName()}</h3>

        <p class="city-time">
            ${request.city},
            <span class="date-range">
                <#if request.timeRelevant>
                    ${request.startDate}, ${request.startTime}—${request.endTime}
                <#else>
                    ${request.startDate}—${request.endDate}
                </#if>
            </span>
        </p>

        <p class="description">${request.description}</p>
    </div>

    <div class="approve-button">
        <form action="${contextPath}/respondRequest" method="post">
            <input type="hidden" name="requestId" value="${request.id}">
            <button type="submit" class="approve">Откликнуться</button>
        </form>
    </div>
</div>