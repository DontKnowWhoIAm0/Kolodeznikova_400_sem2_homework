document.addEventListener("DOMContentLoaded", () => {
    const sidebarButton = document.getElementById("sidebar_button");
    const sidebar = document.getElementById("sidebar");
    const overlay = document.getElementById("overlay");

    sidebarButton.addEventListener("click", () => {
        sidebar.classList.toggle("active");
        overlay.classList.add("active");
    });

    overlay.addEventListener("click", () => {
        sidebar.classList.remove("active");
        overlay.classList.remove("active");
    });
});
