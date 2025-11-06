document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('.approve').forEach(button => {
        button.addEventListener('click', (e) => {
            const requestId = e.target.dataset.requestId;

            fetch(`${contextPath}/respondRequest`, {
                method: 'POST',
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                body: `requestId=${requestId}`
            }).then(response => {
                if (response.ok) {
                    e.target.textContent = "Вы откликнулись";
                    e.target.disabled = true;
                }
            });

            e.preventDefault();
        });
    });
});