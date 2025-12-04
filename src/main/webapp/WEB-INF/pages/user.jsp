<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:pageTemplate pageTitle="Manage Users">

<h1>Manage User Permissions</h1>

<div class="container-fluid mt-4" style="min-height: 600px;">
    <c:if test="${empty users}">
        <div class="alert alert-info">No users found.</div>
    </c:if>

    <c:if test="${not empty users}">
        <div class="row h-100">
            <!-- Users List on Left -->
            <div class="col-md-4">
                <div class="card">
                    <div class="card-header bg-primary text-white">
                        <h5 class="mb-0">Users</h5>
                    </div>
                    <div class="list-group list-group-flush" style="max-height: 700px; overflow-y: auto;">
                        <c:forEach var="user" items="${users}" varStatus="loop">
                            <button type="button" class="list-group-item list-group-item-action text-start"
                                    onclick="selectUser('${user.username}', '${user.email}', ${loop.index})"
                                    id="userBtn${loop.index}">
                                <div class="fw-bold">${user.username}</div>
                                <small class="text-muted">${user.email}</small>
                            </button>
                        </c:forEach>
                    </div>
                </div>
            </div>

            <!-- Permissions on Right -->
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header bg-success text-white">
                        <h5 class="mb-0">Permissions for <span id="selectedUsername">Select a user</span></h5>
                    </div>
                    <div class="card-body">
                        <div id="permissionsContainer" style="display: none;">
                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <div class="form-check form-switch">
                                        <input class="form-check-input permission-checkbox" type="checkbox"
                                               id="perm_READ_CARS" data-permission="READ_CARS">
                                        <label class="form-check-label" for="perm_READ_CARS">
                                            READ_CARS
                                        </label>
                                    </div>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <div class="form-check form-switch">
                                        <input class="form-check-input permission-checkbox" type="checkbox"
                                               id="perm_WRITE_CARS" data-permission="WRITE_CARS">
                                        <label class="form-check-label" for="perm_WRITE_CARS">
                                            WRITE_CARS
                                        </label>
                                    </div>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <div class="form-check form-switch">
                                        <input class="form-check-input permission-checkbox" type="checkbox"
                                               id="perm_READ_USERS" data-permission="READ_USERS">
                                        <label class="form-check-label" for="perm_READ_USERS">
                                            READ_USERS
                                        </label>
                                    </div>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <div class="form-check form-switch">
                                        <input class="form-check-input permission-checkbox" type="checkbox"
                                               id="perm_WRITE_USERS" data-permission="WRITE_USERS">
                                        <label class="form-check-label" for="perm_WRITE_USERS">
                                            WRITE_USERS
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <button type="button" class="btn btn-success mt-4" onclick="savePermissions()">
                                Save Changes
                            </button>
                        </div>
                        <div id="noUserSelected" class="alert alert-info" role="alert">
                            Please select a user from the list on the left to manage their permissions.
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
</div>

<!-- Hidden data for JavaScript -->
<script>
    // Store user permissions data from server
    const userPermissionsData = {
        <c:forEach var="user" items="${users}" varStatus="loop">
        '${user.username}': [<c:forEach var="perm" items="${userPermissions[user.username]}" varStatus="ploop">'${perm}'<c:if test="${!ploop.last}">,</c:if></c:forEach>]<c:if test="${!loop.last}">,</c:if>
        </c:forEach>
    };

    let currentSelectedUsername = '';

    function selectUser(username, email, index) {
        currentSelectedUsername = username;

        // Update UI
        document.getElementById('selectedUsername').textContent = username;
        document.getElementById('noUserSelected').style.display = 'none';
        document.getElementById('permissionsContainer').style.display = 'block';

        // Update button selection
        const buttons = document.querySelectorAll('[id^="userBtn"]');
        buttons.forEach(btn => btn.classList.remove('active'));
        document.getElementById('userBtn' + index).classList.add('active');

        // Load permissions for selected user
        loadPermissions(username);
    }

    function loadPermissions(username) {
        const permissions = userPermissionsData[username] || [];
        const checkboxes = document.querySelectorAll('.permission-checkbox');

        checkboxes.forEach(checkbox => {
            checkbox.checked = permissions.includes(checkbox.getAttribute('data-permission'));
        });
    }

    function savePermissions() {
        if (!currentSelectedUsername) {
            alert('Please select a user first');
            return;
        }

        const checkboxes = document.querySelectorAll('.permission-checkbox');
        const selectedPermissions = [];
        const currentPermissions = userPermissionsData[currentSelectedUsername] || [];

        checkboxes.forEach(checkbox => {
            if (checkbox.checked) {
                selectedPermissions.push(checkbox.getAttribute('data-permission'));
            }
        });

        // Find permissions to grant and revoke
        const toGrant = selectedPermissions.filter(p => !currentPermissions.includes(p));
        const toRevoke = currentPermissions.filter(p => !selectedPermissions.includes(p));

        // Submit each change via form
        toGrant.forEach(permission => {
            submitPermissionChange('grant', permission);
        });

        toRevoke.forEach(permission => {
            submitPermissionChange('revoke', permission);
        });

        if (toGrant.length === 0 && toRevoke.length === 0) {
            alert('No changes made');
        } else {
            // Delay redirect to allow forms to submit
            setTimeout(() => {
                window.location.reload();
            }, 500);
        }
    }

    function submitPermissionChange(action, permission) {
        const form = document.createElement('form');
        form.method = 'POST';
        form.action = '${pageContext.request.contextPath}/User';

        const usernameInput = document.createElement('input');
        usernameInput.type = 'hidden';
        usernameInput.name = 'username';
        usernameInput.value = currentSelectedUsername;

        const permissionInput = document.createElement('input');
        permissionInput.type = 'hidden';
        permissionInput.name = 'permission';
        permissionInput.value = permission;

        const actionInput = document.createElement('input');
        actionInput.type = 'hidden';
        actionInput.name = 'action';
        actionInput.value = action;

        form.appendChild(usernameInput);
        form.appendChild(permissionInput);
        form.appendChild(actionInput);
        document.body.appendChild(form);
        form.submit();
    }

    // Auto-select first user if exists
    window.addEventListener('load', function() {
        const firstButton = document.getElementById('userBtn0');
        if (firstButton) {
            firstButton.click();
        }
    });
</script>

</t:pageTemplate>

