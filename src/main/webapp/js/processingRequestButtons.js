document.addEventListener('DOMContentLoaded', () => {

    let deleteRequestId = null;

    const overlay = document.getElementById('overlay');
    const confirm = document.getElementById('confirm_box');
    const confirmYes = document.getElementById('confirm_delete_yes');
    const confirmNo = document.getElementById('confirm_delete_no');

    document.body.addEventListener('click', (e) => {
        if (e.target.classList.contains('approve')) {
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
        }

        if (e.target.classList.contains('cancel_response')) {
            const requestId = e.target.dataset.requestId;
            fetch(`${contextPath}/cancelResponse`, {
                method: 'POST',
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                body: `requestId=${requestId}`
            }).then(response => {
                if (response.ok) {
                    e.target.textContent = "Откликнуться";
                    e.target.classList.remove('cancel_response');
                    e.target.classList.add('approve');
                }
            });
            e.preventDefault();
        }

        if (e.target.classList.contains('delete_request')) {
            const requestId = e.target.dataset.requestId;
            deleteRequestId = requestId;
            overlay.classList.add('active');
            confirm.style.display = "flex";
            e.preventDefault();
        }

        const request = e.target.closest('.request_item');
        if (request && request.closest('.my_requests') && !e.target.closest('.delete_request')) {
            const request_item = e.target.closest('.request_item');
            if (!request_item) return;
            if (!request_item.closest('.my_requests')) return;
            const requestId = request_item.dataset.requestId;
            window.location.href = `${contextPath}/requestRespondents?requestId=${requestId}`;
        }

    });

    confirmYes.addEventListener('click', () => {
        if (!deleteRequestId) return;

        fetch(`${contextPath}/deleteRequest`, {
            method: 'POST',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            body: `requestId=${deleteRequestId}`
        }).then(response => {
            if (response.ok) {

                const btn = document.querySelector(`.delete_request[data-request-id="${deleteRequestId}"]`);
                const requestItem = btn.closest('.request_item');
                if (requestItem) requestItem.remove();

                confirm.style.display = "none";
                overlay.classList.remove('active');
                deleteRequestId = null;
            } else {
                alert('Ошибка удаления');
            }
        });
    });

    confirmNo.addEventListener('click', () => {
        confirm.style.display = "none";
        overlay.classList.remove('active');
        deleteRequestId = null;
    });

    overlay.addEventListener('click', () => {
        confirm.style.display = "none";
        overlay.classList.remove('active');
        deleteRequestId = null;
    });

});