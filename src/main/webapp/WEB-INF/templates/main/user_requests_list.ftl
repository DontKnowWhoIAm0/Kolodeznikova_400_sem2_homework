<#include "/WEB-INF/templates/main/all_requests_list.ftl">

<#macro my_request_list>
    <div class="requests-page">

        <div class="responded-requests">
            <h2>Мои отклики</h2>
            <#assign requests = respondedRequests>
            <@request_list/>
        </div>

        <div class="my-requests">
            <h2>Мои запросы</h2>
            <#assign requests = userRequests>
            <@request_list/>
        </div>

    </div>
</#macro>
