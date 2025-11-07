<#assign pageCss=["css/auth.css"]>

<#include "/WEB-INF/templates/auth/signup_form.ftl">
<#include "/WEB-INF/templates/auth/login_form.ftl">

<#macro content>

    <div class="auth_container">
        <#if title == "Регистрация">
            <@signupForm />
        <#else>
            <@loginForm />
        </#if>
    </div>

</#macro>

<#include "/WEB-INF/templates/base.ftl">

<#if title == "Регистрация">
    <script src="https://code.jquery.com/jquery-latest.min.js"></script>
    <script>
        window.contextPath = "${contextPath}";
    </script>
    <script src="${contextPath}/js/checkUniqueness.js"></script>
</#if>