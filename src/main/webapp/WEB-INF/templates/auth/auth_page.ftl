<#assign pageCss="css/auth.css">

<#include "/WEB-INF/templates/auth/signup_form.ftl">
<#include "/WEB-INF/templates/auth/login_form.ftl">

<#macro content>

    <div class="auth-container">


        <#if title == "Регистрация">
            <@signupForm />
        <#else>
            <@loginForm />
        </#if>

    </div>

</#macro>

<#include "/WEB-INF/templates/base.ftl">


