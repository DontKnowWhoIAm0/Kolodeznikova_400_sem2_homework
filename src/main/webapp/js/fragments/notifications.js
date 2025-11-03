document.addEventListener("DOMContentLoaded", () => {
    const notificationsButton = document.getElementById("notificationsButton");
    const notifications = document.getElementById("notifications");

    notificationsButton.addEventListener("click", () => {
        notifications.classList.toggle("active");
    });
});