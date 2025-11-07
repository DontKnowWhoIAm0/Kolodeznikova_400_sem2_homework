<#macro request_list>
    <div class="request_list">
        <#if requests?has_content>
            <#list requests as dto>
                <#assign request = dto.request>
                <#assign creator = dto.creator>
                <#include "../requests/request_item.ftl">
            </#list>
        <#else>
            <p class="no_requests">Активных запросов нет.</p>
        </#if>
    </div>

    <a href="${contextPath}/createRequest" class="new_request">+</a>
</#macro>
