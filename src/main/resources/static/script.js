// Replace with your backend base URL
const baseURL = "http://localhost:8080/customer";

async function registerUser(event) {
    event.preventDefault();
    const name = document.getElementById("reg-name").value;
    const email = document.getElementById("reg-email").value;
    const password = document.getElementById("reg-password").value;

    const response = await fetch(`${baseURL}/create`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ name, email, password })
    });

    if (response.ok) {
        alert("Registration successful!");
        window.location.href = "login.html";
    } else {
        alert("Registration failed!");
    }
}

async function loginUser(event) {
    event.preventDefault();
    const email = document.getElementById("login-email").value;
    const password = document.getElementById("login-password").value;

    const res = await fetch(`${baseURL}/all`);
    const users = await res.json();

    const found = users.find(u => u.email === email && u.password === password);
    if (found) {
        localStorage.setItem("loggedUser", JSON.stringify(found));
        window.location.href = "dashboard.html";
    } else {
        alert("Invalid credentials!");
    }
}

function showDashboard() {
    const user = JSON.parse(localStorage.getItem("loggedUser"));
    if (!user) {
        window.location.href = "login.html";
        return;
    }

    document.getElementById("user-info").innerHTML = `
        <strong>ID:</strong> ${user.id}<br>
        <strong>Name:</strong> ${user.name}<br>
        <strong>Email:</strong> ${user.email}
    `;
}
