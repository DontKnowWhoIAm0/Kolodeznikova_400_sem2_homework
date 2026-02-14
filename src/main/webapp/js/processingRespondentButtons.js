document.addEventListener('DOMContentLoaded', () => {
    const respondentList = document.querySelector('.respondent_list');

    if (!respondentList) return;

    respondentList.addEventListener('click', async (e) => {

        const item = e.target.closest('.responded_item');
        const respondentId = item.dataset.respondentId;
        const requestId = item.dataset.requestId;

        if (e.target.classList.contains('cancel_button')) {
            if (!respondentId || !requestId) return;

            try {
                const response = await fetch(`${contextPath}/deleteRespondent`, {
                    method: 'POST',
                    headers: {'Content-Type': 'application/json'},
                    body: JSON.stringify({
                        requestId: requestId,
                        respondentId: respondentId
                    })
                });

                if (response.ok) {
                    item.remove();
                } else {
                    alert('Ошибка при удалении откликнувшегося');
                }
            } catch (err) {
                alert('Ошибка при удалении откликнувшегося');
            }
        }

        if (e.target.classList.contains('confirm_button')) {
            if (!confirm('Подтвердить этого участника и создать тренировку?')) return;

            try {
                const response = await fetch(`${contextPath}/confirmRespondent`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({
                        requestId: requestId,
                        respondentId: respondentId
                    })
                });

                if (response.ok) {
                    window.location.href = `${contextPath}/requests`;
                } else {
                    alert('Ошибка при подтверждении участника');
                }
            } catch (err) {
                alert('A');
            }
        }
    });
});
