<#assign pageCss=["css/request_item.css", "css/respondent_list.css", "css/fragments/back_button.css"]>

<#macro content>
    <a href="javascript:history.back()"><</a>
    <div class="page_layout">
        <div class="request">
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

        <div class="respondent_list">
            <#if respondents?has_content>
                <#list respondents as respondent>
                    <#include "respondent_item.ftl">
                </#list>
            <#else>
                <p class="no_respondents">Пока что никто не откликнулся.</p>
            </#if>
        </div>
    </div>

    <script>
        const contextPath = "${contextPath}";
    </script>
    <script src="${contextPath}/js/processingRespondentButtons.js"></script>

</#macro>

<#include "/WEB-INF/templates/base.ftl">
