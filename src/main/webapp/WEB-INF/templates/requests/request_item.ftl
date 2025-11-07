<div class="request_item"
     data-request-id="${request.id}"
>
    <div class="profile_info">
        <img src="${creator.profileImageUrl!'/images/default_profile.jpg'}"
             alt="${creator.nickname}" class="profile_image">
        <p class="nickname">${creator.nickname}</p>
    </div>

    <div class="request_info">
        <h3 class="sport">${request.sport.getName()}</h3>

        <p class="city_time">
            ${request.city},
            <span class="date_range">
                <#if request.timeRelevant>
                    ${request.startDate}, ${request.startTime}—${request.endTime}
                <#else>
                    ${request.startDate}—${request.endDate}
                </#if>
            </span>
        </p>

        <p class="description">${request.description}</p>
    </div>

    <div class="action_button">

        <#if request.creatorId == userId>
            <button class="delete_request" data-request-id="${request.id}">
                Удалить
            </button>

        <#elseif dto.hasResponded>
            <button class="cancel_response" data-request-id="${request.id}">
                Отменить отклик
            </button>

        <#else>
            <button class="approve" data-request-id="${request.id}">
                Откликнуться
            </button>
        </#if>

    </div>
</div>