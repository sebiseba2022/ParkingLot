<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:pageTemplate pageTitle="Manage Users">

<h1>Manage User Permissions</h1>

<div class="container mt-4">
    <c:if test="${empty users}">
        <div class="alert alert-info">No users found.</div>
    </c:if>

    <c:if test="${not empty users}">
        <div class="accordion" id="userAccordion">
            <c:forEach var="user" items="${users}" varStatus="loop">
                <div class="accordion-item">
                    <h2 class="accordion-header" id="heading${loop.index}">
                        <button class="accordion-button ${loop.first ? '' : 'collapsed'}" type="button" data-bs-toggle="collapse" data-bs-target="#collapse${loop.index}" aria-expanded="${loop.first ? 'true' : 'false'}" aria-controls="collapse${loop.index}">
                            <strong>${user.username}</strong> (${user.email})
                        </button>
                    </h2>
                    <div id="collapse${loop.index}" class="accordion-collapse collapse ${loop.first ? 'show' : ''}" aria-labelledby="heading${loop.index}" data-bs-parent="#userAccordion">
                        <div class="accordion-body">
                            <h5>Permissions</h5>
                            <div class="row">
                                <div class="col-md-6">
                                    <p><strong>Available Permissions:</strong></p>
                                    <form method="POST" action="${pageContext.request.contextPath}/User">
                                        <input type="hidden" name="username" value="${user.username}">
                                        <input type="hidden" name="action" value="grant">
                                        <div class="btn-group-vertical w-100" role="group">
                                            <button type="submit" name="permission" value="READ_CARS" class="btn btn-outline-primary text-start">
                                                <i class="bi bi-plus-circle"></i> READ_CARS
                                            </button>
                                        </div>
                                    </form>
                                    <form method="POST" action="${pageContext.request.contextPath}/User" class="mt-2">
                                        <input type="hidden" name="username" value="${user.username}">
                                        <input type="hidden" name="action" value="grant">
                                        <div class="btn-group-vertical w-100" role="group">
                                            <button type="submit" name="permission" value="WRITE_CARS" class="btn btn-outline-primary text-start">
                                                <i class="bi bi-plus-circle"></i> WRITE_CARS
                                            </button>
                                        </div>
                                    </form>
                                    <form method="POST" action="${pageContext.request.contextPath}/User" class="mt-2">
                                        <input type="hidden" name="username" value="${user.username}">
                                        <input type="hidden" name="action" value="grant">
                                        <div class="btn-group-vertical w-100" role="group">
                                            <button type="submit" name="permission" value="READ_USERS" class="btn btn-outline-primary text-start">
                                                <i class="bi bi-plus-circle"></i> READ_USERS
                                            </button>
                                        </div>
                                    </form>
                                    <form method="POST" action="${pageContext.request.contextPath}/User" class="mt-2">
                                        <input type="hidden" name="username" value="${user.username}">
                                        <input type="hidden" name="action" value="grant">
                                        <div class="btn-group-vertical w-100" role="group">
                                            <button type="submit" name="permission" value="WRITE_USERS" class="btn btn-outline-primary text-start">
                                                <i class="bi bi-plus-circle"></i> WRITE_USERS
                                            </button>
                                        </div>
                                    </form>
                                </div>
                                <div class="col-md-6">
                                    <p><strong>Assigned Permissions:</strong></p>
                                    <c:forEach var="perm" items="${userPermissions[user.username]}">
                                        <form method="POST" action="${pageContext.request.contextPath}/User" class="mb-2">
                                            <input type="hidden" name="username" value="${user.username}">
                                            <input type="hidden" name="permission" value="${perm}">
                                            <input type="hidden" name="action" value="revoke">
                                            <button type="submit" class="btn btn-danger w-100 text-start">
                                                <i class="bi bi-x-circle"></i> ${perm}
                                            </button>
                                        </form>
                                    </c:forEach>
                                    <c:if test="${empty userPermissions[user.username]}">
                                        <div class="alert alert-info" role="alert">
                                            No permissions assigned yet.
                                        </div>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:if>
</div>


</t:pageTemplate>

