const API_URL = "http://localhost:8081/api/orders";

document.addEventListener("DOMContentLoaded", () => {
    loadOrders();

    document.getElementById("orderForm").addEventListener("submit", e => {
        e.preventDefault();
        saveOrder();
    });
});

function loadOrders() {
    showLoading(true);
    fetch(API_URL)
        .then(res => res.json())
        .then(data => populateTable(data))
        .catch(err => showError("Failed to load orders"))
        .finally(() => showLoading(false));
}

function populateTable(orders) {
    const tbody = document.getElementById("order-table-body");
    tbody.innerHTML = "";

    if (orders.length === 0) {
        const row = `<tr><td colspan="7" style="text-align:center;">No orders found</td></tr>`;
        tbody.innerHTML = row;
        return;
    }

    orders.forEach(order => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${order.id}</td>
            <td>${order.flavor}</td>
            <td>${order.shape}</td>
            <td>${order.message}</td>
            <td>${order.deliveryDate}</td>
            <td>${order.status}</td>
            <td>
                <button onclick="editOrder(${JSON.stringify(order).replace(/"/g, '&quot;')})">Edit</button>
                <button onclick="deleteOrder(${order.id})">Delete</button>
            </td>
        `;
        tbody.appendChild(row);
    });
}

function editOrder(order) {
    document.getElementById("orderId").value = order.id;
    document.getElementById("flavor").value = order.flavor;
    document.getElementById("shape").value = order.shape;
    document.getElementById("message").value = order.message;
    document.getElementById("deliveryDate").value = order.deliveryDate;
    document.getElementById("status").value = order.status;
}

function saveOrder() {
    const id = document.getElementById("orderId").value;
    const order = {
        flavor: document.getElementById("flavor").value,
        shape: document.getElementById("shape").value,
        message: document.getElementById("message").value,
        deliveryDate: document.getElementById("deliveryDate").value,
        status: document.getElementById("status").value
    };

    const method = id ? "PUT" : "POST";
    const url = id ? `${API_URL}/${id}` : `${API_URL}/public`; // adjust /public if your API expects different

    fetch(url, {
        method,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(order)
    })
        .then(res => {
            if (!res.ok) throw new Error("Failed to save order");
            return res.json();
        })
        .then(() => {
            document.getElementById("orderForm").reset();
            loadOrders();
        })
        .catch(err => showError("Failed to save order"));
}

function deleteOrder(id) {
    if (!confirm("Are you sure you want to delete this order?")) return;

    fetch(`${API_URL}/${id}`, { method: "DELETE" })
        .then(res => {
            if (!res.ok) throw new Error("Failed to delete");
            loadOrders();
        })
        .catch(() => showError("Failed to delete order"));
}

function searchOrders() {
    const keyword = document.getElementById("searchInput").value.toLowerCase();
    if (!keyword) return loadOrders();

    fetch(API_URL)
        .then(res => res.json())
        .then(data => {
            const filtered = data.filter(order =>
                order.flavor.toLowerCase().includes(keyword) ||
                order.shape.toLowerCase().includes(keyword)
            );
            populateTable(filtered);
        });
}

function showLoading(visible) {
    document.getElementById("loading").style.display = visible ? "block" : "none";
}

function showError(message) {
    const error = document.getElementById("error");
    error.textContent = message;
    error.style.display = "block";
}
