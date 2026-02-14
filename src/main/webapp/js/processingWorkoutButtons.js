document.addEventListener('DOMContentLoaded', () => {

    const workoutList = document.querySelector('.workout_list');
    if (!workoutList) return;

    workoutList.addEventListener('click', async (e) => {
        const item = e.target.closest('.workout_item');
        if (!item) return;

        const workoutId = item.dataset.workoutId;
        if (!workoutId) return;

        if (e.target.classList.contains('cancel_button')) {
            if (!confirm('Отменить тренировку?')) return;

            try {
                const response = await fetch(`${contextPath}/updateWorkoutStatus`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({
                        workoutId: workoutId,
                        status: 'CANCELED'
                    })
                });

                if (response.ok) {
                    item.querySelector('.status_block').innerHTML = '<p class="status_text cancelled">Отменена</p>';
                } else {
                    alert('Не удалось отменить тренировку');
                }
            } catch (err) {
                alert('Не удалось отменить тренировку');
            }
        }

        if (e.target.classList.contains('confirm_button')) {
            if (!confirm('Подтвердить тренировку?')) return;

            try {
                const response = await fetch(`${contextPath}/updateWorkoutStatus`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({
                        workoutId: workoutId,
                        status: 'COMPLETED'
                    })
                });

                if (response.ok) {
                    item.querySelector('.status_block').innerHTML = '<p class="status_text completed">Завершена</p>';
                } else {
                    alert('Не удалось подтвердить тренировку');
                }
            } catch (err) {
                alert('Не удалось подтвердить тренировку');
            }
        }
    });
});