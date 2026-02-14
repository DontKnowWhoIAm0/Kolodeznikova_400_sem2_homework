const startDate = document.getElementById('startDate');
const endDate = document.getElementById('endDate');
const timeFields = document.getElementById('timeFields');
const isTimeRelevant = document.getElementById('isTimeRelevant');

function checkDates() {
    if (startDate.value && endDate.value) {
        if (startDate.value === endDate.value) {
            timeFields.style.display = 'block';
            isTimeRelevant.value = 'true';
        } else {
            timeFields.style.display = 'none';
            isTimeRelevant.value = 'false';
        }
    }
}

startDate.addEventListener('change', checkDates);
endDate.addEventListener('change', checkDates);