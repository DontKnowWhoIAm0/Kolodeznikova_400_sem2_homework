document.addEventListener("DOMContentLoaded", () => {
    const sidebarButton = document.getElementById("sidebarButton"); // кнопка открытия
    const sidebar = document.getElementById("sidebar");             // сам сайдбар
    const overlay = document.getElementById("overlay");             // затемнение

    sidebarButton.addEventListener("click", () => {
        sidebar.classList.toggle("active");
        overlay.classList.toggle("active");
    });

    overlay.addEventListener("click", () => {
        sidebar.classList.remove("active");
        overlay.classList.remove("active");
    });
});
