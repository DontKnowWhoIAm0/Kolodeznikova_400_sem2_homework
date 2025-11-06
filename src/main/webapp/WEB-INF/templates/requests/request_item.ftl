
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

    <div class="action-button">

        <#if request.creatorId == userId>
            <button class="delete-request"
                    onclick="location.href='/requests/delete/${request.id}'">
                Удалить
            </button>

        <#elseif dto.hasResponded>
            <button class="cancel-response"
                    onclick="location.href='/requests/cancel/${request.id}'">
                Отменить отклик
            </button>

        <#else>
            <button class="approve" data-request-id="${request.id}">
                Откликнуться
            </button>
        </#if>

    </div>
</div>