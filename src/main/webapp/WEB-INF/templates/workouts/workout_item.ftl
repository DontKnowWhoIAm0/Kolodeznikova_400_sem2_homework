<div class="workout_item" data-workout-id="${workout.id}">

    <div class="workout_info">
        <h3 class="sport">${workout.sport.getName()}</h3>

        <p class="city">${workout.city}</p>

        <p class="participants">
            <strong>Участники:</strong>
            ${usersMap[workout.creatorId?string].name}, ${usersMap[workout.participantId?string].name}
        </p>

        <div class="status_block">
            <#if workout.status.name() == "PENDING">
                <#if userId == workout.creatorId>
                    <div class="buttons">
                        <button class="cancel_button">Отменить</button>
                        <button class="confirm_button">Подтвердить</button>
                    </div>
                <#else>
                    <p class="status_text pending">В ожидании</p>
                </#if>
            <#else>
                <#if workout.completedDate?has_content>
                    <#assign completedDate = workout.completedDate?date("yyyy-MM-dd")>
                    <p class="status_text ${workout.status?lower_case}">
                        ${workout.status.getStatus()} — ${completedDate?string("d MMM y 'г.'")}
                    </p>
                <#else>
                    <p class="status_text ${workout.status?lower_case}">
                        ${workout.status.getStatus()}
                    </p>
                </#if>
            </#if>
        </div>
    </div>
</div>