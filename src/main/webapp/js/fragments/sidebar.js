document.addEventListener("DOMContentLoaded", () => {
    const sidebarButton = document.getElementById("sidebarButton");
    const sidebar = document.getElementById("sidebar");

    sidebarButton.addEventListener("click", () => {
        sidebar.classList.toggle("active");
    });
});