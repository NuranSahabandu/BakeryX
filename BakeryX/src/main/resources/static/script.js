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

    if (email === "admin@bakery.com" && password === "admin123") {
        localStorage.setItem("admin", "true");
        window.location.href = "admin.html";
        return;
    }

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

async function getAllUsers() {
    const res = await fetch(`${baseURL}/all`);
    const users = await res.json();

    const output = users.map(user =>
        `ID: ${user.id}, Name: ${user.name}, Email: ${user.email}`
    ).join("<br>");

    document.getElementById("admin-output").innerHTML = output;
}

async function getUserById() {
    const id = document.getElementById("search-id").value;
    if (!id) return;

    const res = await fetch(`${baseURL}/${id}`);
    if (res.ok) {
        const user = await res.json();
        document.getElementById("admin-output").innerHTML =
            `ID: ${user.id}, Name: ${user.name}, Email: ${user.email}`;
    } else {
        document.getElementById("admin-output").innerHTML = "User not found.";
    }
}


function logoutUser() {
    localStorage.clear(); // works for both user/admin
    window.location.href = "login.html";
}


document.addEventListener("DOMContentLoaded", function () {
    const logoutBtn = document.getElementById("logout-btn");
    if (logoutBtn) {
        logoutBtn.addEventListener("click", logoutUser);
    }
});


async function updateUser(event) {
    event.preventDefault();

    const user = JSON.parse(localStorage.getItem("loggedUser"));
    if (!user) {
        alert("No logged-in user.");
        return;
    }

    const id = user.id;
    const name = document.getElementById("update-name").value;
    const email = document.getElementById("update-email").value;
    const password = document.getElementById("update-password").value;

    const response = await fetch(`${baseURL}/update/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ name, email, password })
    });

    if (response.ok) {
        const updatedUser = await response.json();
        localStorage.setItem("loggedUser", JSON.stringify(updatedUser));
        alert("Information updated successfully!");
        showDashboard(); // Refresh updated info on screen
    } else {
        alert("Update failed.");
    }
}


function deleteUser() {
    const user = JSON.parse(localStorage.getItem("loggedUser"));
    const id = user.id;

    fetch(`http://localhost:8080/customer/${id}`, {
        method: "DELETE"
    })
        .then(response => {
            if (response.ok) {
                alert("Customer deleted successfully!");
                localStorage.removeItem("loggedUser");
                window.location.href = "login.html";
            } else {
                alert("Failed to delete customer.");
            }
        })
        .catch(error => {
            console.error("Error:", error);
            alert("An error occurred.");
        });
}







