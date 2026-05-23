<html>
<head>
    <title>Чат</title>
    <script type="text/javascript" src="/webjars/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="/webjars/sockjs-client/sockjs.min.js"></script>
    <script type="text/javascript" src="/webjars/stomp-websocket/stomp.min.js"></script>
</head>
<body>

<h2>Общий чат</h2>

<p><a href="/chat/my">Мои сообщения</a> | <a href="/chat/public">Публичная история</a></p>

<div id="chat-box">
    <#if messages?size == 0>
        <p>Сообщений пока нет.</p>
    <#else>
        <#list messages?reverse as msg>
            <div>
                <strong>${msg.authorUsername}</strong>
                <small>(${msg.sentAt})</small>:
                ${msg.content}
            </div>
        </#list>
    </#if>
</div>

<div>
    <input type="text" id="message-input" placeholder="Введите сообщение..."/>
    <button id="send-btn">Отправить</button>
</div>

<script type="text/javascript">
    const currentUsername = '${currentUsername}';
    let stompClient = null;

    function connect() {
        const socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);

            stompClient.subscribe('/topic/messages', function (message) {
                const msg = JSON.parse(message.body);
                appendMessage(msg);
            });
        }, function (error) {
            console.error('STOMP error: ' + error);
        });
    }

    function appendMessage(msg) {
        const chatBox = document.getElementById('chat-box');
        const div = document.createElement('div');
        div.innerHTML = '<strong>' + msg.authorUsername + '</strong> <small>(' + msg.sentAt + ')</small>: ' + msg.content;
        chatBox.appendChild(div);
        chatBox.scrollTop = chatBox.scrollHeight;
    }

    document.getElementById('send-btn').addEventListener('click', function () {
        const input = document.getElementById('message-input');
        const content = input.value.trim();
        if (content && stompClient) {
            stompClient.send('/app/send', {}, JSON.stringify({ content: content }));
            input.value = '';
        }
    });

    document.getElementById('message-input').addEventListener('keydown', function (e) {
        if (e.key === 'Enter') {
            document.getElementById('send-btn').click();
        }
    });

    connect();
</script>

</body>
</html>