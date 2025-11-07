document.addEventListener("DOMContentLoaded", () => {
    const notificationsButton = document.getElementById("notifications_button");
    const notifications = document.getElementById("notifications");

    notificationsButton.addEventListener("click", () => {
        notifications.classList.toggle("active");
    });
});