var start_of_week = null;
var end_of_week = null;

function buildCalendar() {
    var table = document.getElementById('calendar-body');
    table.innerHTML = '';
    var row = table.insertRow();
    for (var day = new Date(start_of_week); day <= end_of_week; day.setDate(day.getDate() + 1)) {
        var cell = row.insertCell();
        cell.innerHTML = day.getDate();
        if (day.toDateString() === new Date().toDateString()) {
            cell.classList.add('today');
        }
        if (day.getDay() === 6) {
            row = table.insertRow();
        }
    }
    var monthYear = document.getElementById('month-year');
    monthYear.innerHTML = start_of_week.toLocaleString('default', { month: 'long' }) + ' ' + start_of_week.getFullYear();
}

function previousWeek() {
    start_of_week = new Date(start_of_week.getFullYear(), start_of_week.getMonth(), start_of_week.getDate() - 7);
    end_of_week = new Date(end_of_week.getFullYear(), end_of_week.getMonth(), end_of_week.getDate() - 7);
    buildCalendar();
}

function nextWeek() {
    start_of_week = new Date(start_of_week.getFullYear(), start_of_week.getMonth(), start_of_week.getDate() + 7);
    end_of_week = new Date(end_of_week.getFullYear(), end_of_week.getMonth(), end_of_week.getDate() + 7);
    buildCalendar();
}

function init() {
    var today = new Date();
    start_of_week = new Date(today.getFullYear(), today.getMonth(), today.getDate() - today.getDay());
    end_of_week = new Date(today.getFullYear(), today.getMonth(), today.getDate() + (6 - today.getDay()));
    buildCalendar();
}

init();