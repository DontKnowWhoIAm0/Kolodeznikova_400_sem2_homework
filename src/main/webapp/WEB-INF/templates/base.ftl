<!DOCTYPE html>
<html lang="ru">
    <head>
        <meta charset="UTF-8">
        <title>${title!''}</title>

        <style>
            body {
                font-family: Arial, sans-serif;
                background: #e2f7df;
            }
            .error {
                color: red;
                margin-bottom: 10px;
                text-align: center;
            }
        </style>

        <#if pageCss??>
            <link rel="stylesheet" href="${pageCss}">
        </#if>

    </head>

    <body>
        <@content></@content>
    </body>
</html>
