<#macro request_list>
    <div class="request_list">
        <#if requests?has_content>
            <#list requests as request>
                <#assign creator = creatorsMap[request.creatorId?string]!>
                <#include "../requests/request_item.ftl">
            </#list>
        <#else>
            <p>Активных запросов нет.</p>
        </#if>
    </div>

    <a href="${contextPath}/createRequest" class="new-request">+</a>
</#macro>