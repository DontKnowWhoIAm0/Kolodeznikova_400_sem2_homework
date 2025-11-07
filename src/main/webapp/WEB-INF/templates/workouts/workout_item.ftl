<div class="workout_item">

    <div class="workout_info">
        <h3 class="sport">${workout.sport.getName()}</h3>

        <p class="city">${workout.city}</p>

        <p class="participants">
            <strong>Участники:</strong>
            <#list workout.participants as participant>
                ${participant.name}<#if participant_has_next>, </#if>
            </#list>
        </p>

        <div class="status_block">
            <#if workout.status.name() == "PENDING">
                <div class="buttons">
                    <button class="confirm_button">Подтвердить</button>
                    <button class="cancel_button">Отменить</button>
                </div>
            <#elseif workout.status.name() == "COMPLETED">
                <p class="status_text completed">Завершена</p>
            <#elseif workout.status.name() == "CANCELED">
                <p class="status_text cancelled">Отменена</p>
            </#if>
        </div>
    </div>
</div>