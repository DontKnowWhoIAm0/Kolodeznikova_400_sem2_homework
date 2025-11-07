<#include "/WEB-INF/templates/main/all_requests_list.ftl">

<#macro my_request_list>
    <div class="requests_page">

        <div class="responded_requests">
            <h2>Мои отклики</h2>
            <#assign requests = respondedRequests>
            <@request_list/>
        </div>

        <div class="my_requests">
            <h2>Мои запросы</h2>
            <#assign requests = userRequests>
            <@request_list/>
        </div>

    </div>
</#macro>
