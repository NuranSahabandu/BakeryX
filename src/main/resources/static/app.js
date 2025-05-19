// Simple JS for confirmation and dynamic UI
function confirmDelete(name) {
    return confirm('Are you sure you want to delete ' + name + '?');
}
function showMessage(msg) {
    alert(msg);
}

// Fetch and render staff data for the dashboard
function loadStaffDashboard() {
    fetch('/staff/api/list')
        .then(res => res.json())
        .then(data => {
            const tbody = document.getElementById('staff-table-body');
            tbody.innerHTML = '';
            data.forEach(staff => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${staff.id}</td>
                    <td>${staff.name}</td>
                    <td>${staff.email}</td>
                    <td>${staff.role}</td>
                    <td><a class="link" href="/static/staff-profile.html?id=${staff.id}">View</a></td>
                `;
                tbody.appendChild(tr);
            });
        });
}

// Fetch and render staff data for the admin panel
function loadAdminPanel() {
    fetch('/staff/api/list')
        .then(res => res.json())
        .then(data => {
            const tbody = document.getElementById('admin-staff-table-body');
            tbody.innerHTML = '';
            data.forEach(staff => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${staff.id}</td>
                    <td>${staff.name}</td>
                    <td>${staff.email}</td>
                    <td>${staff.role}</td>
                    <td>
                        <form onsubmit="return assignRole(event, '${staff.id}')">
                            <select name="role">
                                <option value="ADMIN" ${staff.role === 'ADMIN' ? 'selected' : ''}>Admin</option>
                                <option value="BAKER" ${staff.role === 'BAKER' ? 'selected' : ''}>Baker</option>
                                <option value="DELIVERY" ${staff.role === 'DELIVERY' ? 'selected' : ''}>Delivery</option>
                            </select>
                            <button type="submit">Assign</button>
                        </form>
                    </td>
                    <td>
                        <button onclick="deleteStaff('${staff.id}', '${staff.name}')">Delete</button>
                    </td>
                `;
                tbody.appendChild(tr);
            });
        });
}

function assignRole(event, id) {
    event.preventDefault();
    const role = event.target.role.value;
    fetch('/staff/assign-role', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: `id=${encodeURIComponent(id)}&role=${encodeURIComponent(role)}`
    }).then(() => loadAdminPanel());
    return false;
}

function deleteStaff(id, name) {
    if (!confirmDelete(name)) return;
    fetch(`/staff/delete/${id}`, { method: 'POST' })
        .then(() => loadAdminPanel());
}

// On dashboard page load
if (window.location.pathname.endsWith('staff-dashboard.html')) {
    document.addEventListener('DOMContentLoaded', loadStaffDashboard);
}

// Add staff
if (window.location.pathname.endsWith('admin-panel.html')) {
    document.addEventListener('DOMContentLoaded', () => {
        loadAdminPanel();
        document.getElementById('add-staff-form').onsubmit = function(e) {
            e.preventDefault();
            const form = e.target;
            fetch('/staff/add', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: `name=${encodeURIComponent(form.name.value)}&email=${encodeURIComponent(form.email.value)}&role=${encodeURIComponent(form.role.value)}`
            }).then(() => {
                form.reset();
                loadAdminPanel();
            });
        };
    });
}
