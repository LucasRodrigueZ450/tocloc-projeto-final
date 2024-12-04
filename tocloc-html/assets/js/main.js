document.addEventListener("DOMContentLoaded", function () {
    const loginForm = document.querySelector("form"); 
    if (loginForm && loginForm.id === "login-form") { 
        loginForm.addEventListener("submit", function (event) {
            event.preventDefault();
            const email = document.getElementById("email").value;
            const password = document.getElementById("password").value;
            
            if (email === "" || password === "") {
                alert("Por favor, preencha todos os campos.");
            } else {
                alert("Login realizado com sucesso!");
                window.location.href = "dashboard.html"; // Redireciona para o Dashboard
            }
        });
    }

    // Confirmação de Reserva na Página de Detalhes
    const reserveButton = document.querySelector(".reserve-btn"); // Adicione uma classe específica ao botão de reserva
    if (reserveButton) {
        reserveButton.addEventListener("click", function (event) {
            event.preventDefault();
            alert("Reserva confirmada!");
        });
    }
});
