<#assign pageCss=["css/request_item.css", "css/fragments/back_button.css"]>

<#macro content>
    <a href="javascript:history.back()"><</a>

    <div class="request">
        <#assign request=request>
        <#assign creator=creator>
        <#include "request_item.ftl">
    </div>

        <div class="respondent_list">
            <#if respondents?has_content>
                <#list respondents as respondent>
                    <#include "respondent_item.ftl">
                </#list>
            <#else>
                <p>Пока что никто не откликнулся.</p>
            </#if>
        </div>

</#macro>

<#include "/WEB-INF/templates/base.ftl">