<#assign pageCss=["css/request_form.css"]>

<#macro content>
    <div>
        <a href="javascript:history.back()"><</a>
        <div class="request-container">
            <div>
                <h2>Запрос на тренировку</h2>
            </div>

            <#if error??>
                <div class="error">${error}</div>
            </#if>

            <form method="post" action="createRequest">

                <select name="sport" required>
                    <option value="" disabled selected>Выберите спорт</option>
                    <#list sportsList as s>
                        <option value="${s}">${s.getName()!s}</option>
                    </#list>
                </select>

                <input type="text" name="city" placeholder="Город" required>

                <textarea name="description" placeholder="Описание тренировки" rows="4"></textarea>

                <label>Дата начала:</label>
                <input type="date" name="startDate" id="startDate" required>

                <label>Дата окончания:</label>
                <input type="date" name="endDate" id="endDate" required>

                <div id="timeFields" style="display: none;">
                    <label>Время начала:</label>
                    <input type="time" name="startTime">

                    <label>Время окончания:</label>
                    <input type="time" name="endTime">
                </div>

                <input type="hidden" name="isTimeRelevant" id="isTimeRelevant" value="false">

                <button type="submit">Создать запрос</button>
            </form>
        </div>
    </div>

    <script src="${contextPath}/js/checkRequestTimeRelevant.js"></script>
</#macro>

<#include "/WEB-INF/templates/base.ftl">
