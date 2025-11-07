document.addEventListener('DOMContentLoaded', () => {

    let deleteRequestId = null;

    const confirmOverlay = document.getElementById('delete_confirm');
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
            confirmOverlay.style.display = "flex";
            e.preventDefault();
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

                confirmOverlay.style.display = "none";
                deleteRequestId = null;
            } else {
                alert('Ошибка удаления');
            }
        });
    });

    confirmNo.addEventListener('click', () => {
        confirmOverlay.style.display = "none";
        deleteRequestId = null;
    });

});