<div class="request-item">
    <div class="avatar"></div>

    <div class="workout-info">
        <h3 class="sport">${request.sport.getName()}</h3>

        <p class="city-time">
            ${request.city}
            <span class="date-range">
                ${request.startDate}
                <#if request.timeRelevant>
                    , ${request.startTime} - ${request.endTime}
                <#else>
                    - ${request.endDate}
                </#if>
            </span>
        </p>

        <p class="description">${request.description}</p>
        <p class="nickname">${creator.nickname}</p>
    </div>
</div>