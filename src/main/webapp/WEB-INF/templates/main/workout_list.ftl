<#macro workout_list>
    <div class="workout_list">
        <#if workouts?has_content>
            <#list workouts as workout>
                <#assign user = usersMap[workout.creatorId?string]!>
                <#include "../workouts/workout_item.ftl">
            </#list>
        <#else>
            <p class="no_workouts">Тренировок пока что нет</p>
        </#if>
    </div>
</#macro>
